package nc.portal.search;

import java.net.MalformedURLException;
import java.util.List;

import nc.uap.lfw.core.exception.LfwBusinessException;

/**
 * @author renxh
 * 
 */
public interface ILfwIndexService{	
	
	/**
	 * ���ݲ������������в�ѯ
	 * @param params
	 * @return
	 * @throws LfwBusinessException
	 */
	
	public SearchResultVO search(SearchParams searchParams) throws LfwBusinessException;	
	
}
