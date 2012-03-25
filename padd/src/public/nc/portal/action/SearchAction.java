package nc.portal.action;


import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import nc.portal.search.ILfwIndexService;
import nc.portal.search.IndexImplHelper;
import nc.portal.search.SearchParams;
import nc.portal.search.SearchResultVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.log.LfwLogger;

import nc.uap.lfw.servletplus.annotation.Action;
import nc.uap.lfw.servletplus.annotation.Servlet;
import nc.uap.lfw.servletplus.core.impl.BaseAction;
import nc.uap.lfw.util.StringUtil;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.util.freemarker.FreeMarkerTools;

/**
 * @author renxh
 * ȫ�������Ĳ�ѯaction
 */
@Servlet(path = "/search")
public class SearchAction extends BaseAction {

	private long newPageTurnNum = 0; // Ĭ�Ϸ�ҳ��ҳ��
	private long rowsNum = 10; // Ĭ�ϵ��������
	

	/**
	 * ��ѯ������
	 * @throws MalformedURLException 
	 * @throws LfwBusinessException
	 */
	@Action
	public void doSearch() throws PortalServiceException {		
		ILfwIndexService indexService;
		try {
			indexService = IndexImplHelper.getIndexServiceforPortal();
		} catch (MalformedURLException e2) {
				LfwLogger.error(e2);
				throw new PortalServiceException(e2);
		}
		long currows = rowsNum;
		if(LfwRuntimeEnvironment.getBrowserInfo().isIpad())
			currows = 5;
		long currentPage = request.getParameter("currentPage") == null ? newPageTurnNum : Long.parseLong(request.getParameter("currentPage"));
		currentPage -= 1;
		// ��õ�ǰҳ�룬�����next�򣬵�ǰҳ���1�������front�����1��
		// �����first���ǵ�һ����last��ʾ���һ��
		// ������������ʾҳ��
		// �����null����ʾ���ν��� Ĭ��Ϊ0
		String pageTurn = request.getParameter("pageTurn"); // ҳ��������һ��Ϊnull
		newPageTurnNum = this.getNextPage(currentPage, pageTurn);

		// ��λ�����ҳ������
		String queryString = this.transCode(request.getParameter("queryString"), "iso-8859-1", "UTF-8"); // ��ѯ����
		
		String pagename = request.getParameter("pageName");
		if(null == pagename)
			pagename = "";
		String pagemodule = request.getParameter("pageModule");
		if(null == pagemodule)
			pagemodule = "";
		
		SearchResultVO result = null;
		if(queryString == null || queryString.equals("") || queryString.trim().equals("")){
			result = new SearchResultVO();
		}
		else{
	//		��������Щ����"+ - && || ! ( ) { } [ ] ^ " ~ * ? : \"ת��
			queryString = queryString.replaceAll("([\\+\\-\\!\\(\\)\\{\\}\\[\\]\\\\\\\"\\*\\?~\\^\\:\\&\\|])", "\\\\$1");
			
			SearchParams params = new SearchParams();			
			params.setPageNum(newPageTurnNum);//�����µ�ҳ��
			params.setRows(currows); // ����ÿҳ������
			params.setFacedFields("category"); // ��������
			params.setQueryString(queryString);			
			
			try {
				result = indexService.search(params);							
			} catch (LfwBusinessException e1) {
				LfwLogger.error(e1.getMessage());
				alert("�������̷����쳣!");
				return;
			}
			//result = new SearchResultVO();
		}
		
		String ftlName = "nc/portal/ftl/portalsearch.ftl";	
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("currentPage", result.getCurrentPage());
		root.put("totalPage", result.getTotalPage());
		root.put("indexVOList", result.getList());		
		root.put("numFound", result.getNumFound());
		root.put("queryTime", result.getQTime());
		root.put("queryString", queryString);
		root.put("ispad", LfwRuntimeEnvironment.getBrowserInfo().isIpad());
		root.put("ispage", true);
		
		root.put("CUR_PAGE_NAME", pagename);
		root.put("CUR_PPAGE_MODULE", pagemodule);
		
		print(FreeMarkerTools.contextTemplatRender(ftlName, root));
	}

	/**
	 * ����ֵת������StringתΪint����,ת�Ͳ��ɹ�����Ĭ��ֵ
	 * @param rawValue ��Ҫת����ֵ
	 * @param defValue Ĭ��ֵ
	 * @return
	 */
	public int transToNewValue(String rawValue, int defValue) {
		if (rawValue != null && rawValue.trim().matches("[0-9]+")) {
			return Integer.parseInt(rawValue);
		}
		return defValue;
	}

	/**
	 * �����һ����ҳ��ҳ��
	 * @param currentPage
	 * @param pageTurn
	 * @return
	 */
	public long getNextPage(long currentPage, String pageTurn) {
		if (pageTurn != null) {
			if (pageTurn.equals("first")) {// ��ҳ
				newPageTurnNum = 1;
			} else if (pageTurn.equals("next")) { // ��һҳ
				newPageTurnNum = currentPage + 1;
			} else if (pageTurn.equals("front")) { // ��һҳ
				newPageTurnNum = currentPage - 1;
			} else if (pageTurn.matches("[1-9][0-9]*")) { // �����ĳһҳ
				newPageTurnNum = Integer.parseInt(pageTurn) -1;
			}
		}
		return newPageTurnNum;
	}
	
	/**
	 * �����ַ������ת��
	 * @param str
	 * @param srcCharSet
	 * @param destCharSet
	 * @return
	 */
	public String transCode(String str,String srcCharSet,String destCharSet){
		if(str == null || str== "")
			return "";
		try {
			return new String(str.getBytes(srcCharSet),destCharSet);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

}
