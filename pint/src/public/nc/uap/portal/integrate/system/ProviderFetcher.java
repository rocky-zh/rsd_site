package nc.uap.portal.integrate.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import nc.bs.logging.Logger;
import nc.uap.portal.cache.PortalCacheManager;
import nc.uap.portal.cache.PortalCacheProxy;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.integrate.IWebAppFunNodesService;
import nc.uap.portal.integrate.IWebAppLoginService;
import nc.uap.portal.integrate.cache.SSOProviderCache;
import nc.vo.ml.NCLangRes4VoTransl;

/**
 * @update lkp ���ӻ�ȡProviderʵ������֤��ķ�����ʹ�����Ϊ�����ȡssoProviderVO��Ϣ��Ψһ�ӿڣ������ⲿ��ȡsso��Ϣ�Ĵ�������
 * @author yzy
 */
public class ProviderFetcher {

	
	/*��¼IuserProvider��ʵ�������������Ϊ�գ�����Ϊ��portaluserProvider*/
	//private String loginProvider; 
	
	private ProviderFetcher() {
		
	}
	
	/**
	 * ����systemcode��ȡSSOProviderVO
	 * @param systemCode
	 * @return
	 */
	public SSOProviderVO getProvider(String systemCode){
		if(Logger.isDebugEnabled())
			Logger.debug("===ProviderFetcher��getProvider����:��ȡsystemCode=" + systemCode + "��SSOProviderVO��Ϣ��");
		
		SSOProviderVO provider = (SSOProviderVO)getProvidersPool().get(systemCode);
		if(provider == null)
		{
			if(Logger.isDebugEnabled())
				Logger.debug("===ProviderFetcher��getProvider����:systemCode=" + systemCode + " ��SSOProviderVO�����ڣ�");
			return null;
		}
		 
		if(!provider.isEnableMapping())
			return provider;
		
		provider = (SSOProviderVO)provider.clone();
		return SsoProviderMappingUtil.mapping(provider);
	}
	
	/**
	 * ��ȡ��ǰϵͳ���õ�����ProviderVO�б�
	 * @return
	 */
	public SSOProviderVO[] listProvider()
	{
		ArrayList<SSOProviderVO> al = new ArrayList<SSOProviderVO>(getProvidersPool().values());
		Logger.debug("===ProviderFetcher��listProvider����:��ȡ��ǰ���е�SSOProviderVO�б�!");
		
		List<SSOProviderVO> enableMappingList = new ArrayList<SSOProviderVO>();
		List<SSOProviderVO> noEnableMappingList = new ArrayList<SSOProviderVO>();
		
		for(int i = 0; i < al.size(); i++)
		{
			if(al.get(i).isEnableMapping())
				enableMappingList.add((SSOProviderVO)al.get(i).clone());
			else
				noEnableMappingList.add(al.get(i));
		}
		
		SSOProviderVO[] processedVOs = SsoProviderMappingUtil.mapping(enableMappingList.toArray(new SSOProviderVO[0]));
		if(processedVOs != null)
			for(int i = 0;i < processedVOs.length; i++)
				noEnableMappingList.add(processedVOs[i]);
		
		return (SSOProviderVO[])noEnableMappingList.toArray(new SSOProviderVO[0]);
	}
	
	/**
	 * ��ȡ������Ҫ����ƾ֤��provider����
	 * 
	 * @return
	 * @throws PortalServiceException
	 */
	public SSOProviderVO[] listCredentialProvider() 
	{
		SSOProviderVO[] providers = listProvider();
		List<SSOProviderVO> list = new ArrayList<SSOProviderVO>();
		if(providers != null && providers.length != 0)
		{
			String authClass = null;
			Class authClazz = null;
			for(int i = 0; i < providers.length; i++)
			{
				authClass = providers[i].getAuthClass();
				if(authClass != null)
				{
					try {
						authClazz = Class.forName(authClass);
						// ����Ǽ̳��Ըó�����,����Ҫ����ƾ֤,���û�����ҳǩҲ����Ҫ��ʾ
					    if(!SimpleWebAppLoginService.class.isAssignableFrom(authClazz))
					    	list.add(providers[i]);
					} catch(ClassNotFoundException e) {
						 Logger.error("===ProviderFetcher��listCredentialProvider����:�ڻ�ȡ������Ҫ����ƾ֤��ssoProviderVOʱ������֤���޷��ҵ�����:" + e);
					}
				}
			}
		}
		sortCredentialProvider(list);
		// log����Ҫ����ƾ֤������ϵͳ
		if(Logger.isDebugEnabled())
		{	
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < list.size(); i++)
				sb.append(list.get(i).getSystemCode()).append(",");
			Logger.debug("===ProviderFetcher��listCredentialProvider����:��Ҫ����ƾ֤��ϵͳ=" + sb.toString());
		}	
		return list.toArray(new SSOProviderVO[0]);
	}
	
	private void sortCredentialProvider(List<SSOProviderVO> list)
	{
		Collections.sort(list, new Comparator<SSOProviderVO>()
		{
			public int compare(SSOProviderVO vo1, SSOProviderVO vo2)
			{
				if(vo1.getSystemCode().equals("NC") && !vo2.getSystemCode().equals("NC"))
					return -1; 
				if(vo2.getSystemCode().equals("NC") && !vo1.getSystemCode().equals("NC"))
					return 1;
				return vo1.getSystemCode().compareTo(vo2.getSystemCode());
			}
		});
	}
	
	
//	private String getUserProviderClazz() 
//	{
//		return PortalServiceFacility.getClusterCatheService().getUserProvider();
//	}
	
	/**
	 * ��ȡָ������ϵͳ�ĵ�¼��ʵ��
	 * @param systemCode
	 * @return
	 * @throws PortalServiceException
	 */
	public IWebAppLoginService getAuthService(String systemCode) throws PortalServiceException 
	{
		try {
			Logger.debug("===ProviderFetcher��getAuthService����:��ȡsystemCode=" + systemCode + ",��Ӧ��IWebAppLoginService��ʵ���ࡣ");
			SSOProviderVO provider = ProviderFetcher.getInstance().getProvider(systemCode);
			if (provider == null) return null;
			String className = provider.getAuthClass();
			Logger.debug("===ProviderFetcher��getAuthService����:��ʵ��������Ϊ=" + className);
			IWebAppLoginService webAppService = (IWebAppLoginService)Class.forName(className).newInstance();
			return webAppService;
		} catch (ClassNotFoundException e) {
			Logger.error("===ProviderFetcher��getAuthService����:Ӧ��ϵͳע�����֤����classδ�ҵ�",e);
			throw new PortalServiceException(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "ProviderFetcher-000000")/*Ӧ��ϵͳע�����֤����classδ�ҵ����޷���portal�д򿪸�ϵͳ*/);
		} catch (InstantiationException e) {
			Logger.error("===ProviderFetcher��getAuthService����:Ӧ��ϵͳע�����֤����class�޷�ʵ����",e);
			throw new PortalServiceException(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "ProviderFetcher-000001")/*Ӧ��ϵͳע�����֤����class�޷�ʵ�������޷���portal�д򿪸�ϵͳ*/);
		} catch (IllegalAccessException e) {
			Logger.error("===ProviderFetcher��getAuthService����:Ӧ��ϵͳע���class�޷�ʵ����",e);
			throw new PortalServiceException(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "ProviderFetcher-000001")/*Ӧ��ϵͳע�����֤����class�޷�ʵ�������޷���portal�д򿪸�ϵͳ*/);
		}
	}
	/**
	 * ��ȡָ������ϵͳ�Ĺ��ܽڵ�ʵ��
	 * @param systemCode
	 * @return
	 * @throws PortalServiceException
	 */
	public IWebAppFunNodesService getFunNodeService(String systemCode) throws PortalServiceException 
	{
		try {
			Logger.debug("===ProviderFetcher��getAuthService����:��ȡsystemCode=" + systemCode + ",��Ӧ��IWebAppLoginService��ʵ���ࡣ");
			SSOProviderVO provider = ProviderFetcher.getInstance().getProvider(systemCode);
			if (provider == null) return null;
			String className = provider.getAuthClass();
			Logger.debug("===ProviderFetcher��getAuthService����:��ʵ��������Ϊ=" + className);
			IWebAppFunNodesService webAppService = (IWebAppFunNodesService)Class.forName(className).newInstance();
			return webAppService;
		} catch (ClassNotFoundException e) {
			Logger.error("===ProviderFetcher��getAuthService����:Ӧ��ϵͳע�����֤����classδ�ҵ�",e);
			throw new PortalServiceException(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "ProviderFetcher-000000")/*Ӧ��ϵͳע�����֤����classδ�ҵ����޷���portal�д򿪸�ϵͳ*/);
		} catch (InstantiationException e) {
			Logger.error("===ProviderFetcher��getAuthService����:Ӧ��ϵͳע�����֤����class�޷�ʵ����",e);
			throw new PortalServiceException(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "ProviderFetcher-000001")/*Ӧ��ϵͳע�����֤����class�޷�ʵ�������޷���portal�д򿪸�ϵͳ*/);
		} catch (IllegalAccessException e) {
			Logger.error("===ProviderFetcher��getAuthService����:Ӧ��ϵͳע���class�޷�ʵ����",e);
			throw new PortalServiceException(NCLangRes4VoTransl.getNCLangRes().getStrByID("portal", "ProviderFetcher-000001")/*Ӧ��ϵͳע�����֤����class�޷�ʵ�������޷���portal�д򿪸�ϵͳ*/);
		}
	} 
	
	public static ProviderFetcher getInstance() 
	{
		return new ProviderFetcher();
	}
	
	public Map<String, SSOProviderVO> getProvidersPool() 
	{
		return (Map<String, SSOProviderVO>) PortalCacheProxy.newIns().get(new SSOProviderCache());
	}
}
