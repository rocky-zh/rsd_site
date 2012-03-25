package nc.uap.portal.integrate.itf;

import nc.uap.portal.plugins.model.IDynamicalPlugin;

/**
 * ���ɽӿ�
 * <pre>Portal���м�������ĳ���,ͳһʵ��IBaseIntegrate�ӿ�</pre>
 * @author licza
 * 
 */
public interface IBaseIntegrate extends IDynamicalPlugin{
	/**
	 * ��ò������
	 * 
	 * @return
	 */
	public String getSystemName();

	/**
	 * ��ò�����ʻ�����
	 * 
	 * @return
	 */
	public String getI18nname();
	
	/**
	 * �����sso-prop.xml�����õļ���ϵͳ����
	 * 
	 * <pre>
	 * �������Ҫ���㼯��,ֱ�ӷ���NULL
	 * </pre>
	 * 
	 * @return ϵͳ����
	 */
	public String getSystemCode();
	
	/**
	 * �õ�������
	 * 
	 * @return
	 */
	public Integer getSharelevel();
	
	/**
	 * �Ƿ��ⲿ����ϵͳ
	 * @return
	 */
	public boolean isIntegrateSystem();
	/**
	 * �û��Ƿ������Ӧ��
	 * @return
	 */
	public boolean isGiveUp();
 }
