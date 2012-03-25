package nc.portal.search;

import java.net.MalformedURLException;
import nc.bs.framework.common.NCLocator;
import nc.uap.portal.exception.PortalServiceException;

public final class IndexImplHelper {

	/**
	 * �������ģ�����
	 * @return
	 * @throws PortalServiceException 
	 * @throws MalformedURLException 
	 */
	public static ILfwIndexService getIndexServiceforPortal() throws PortalServiceException, MalformedURLException{
		ILfwIndexService indexService = NCLocator.getInstance().lookup(ILfwIndexService.class);		
		return indexService;
	}
	
	private static ILfwIndexService getIndexService() throws PortalServiceException
	{
		ILfwIndexService indexService = NCLocator.getInstance().lookup(ILfwIndexService.class);		
		return indexService;
	}
	
	/**
	 * ����Index��ز���
	 * @param actionType
	 * @param indexVO
	 * @param indexService
	 */
	public static void DoIndex(String actionType,SearchIndexVO indexVO)
	{
		
	}
}
