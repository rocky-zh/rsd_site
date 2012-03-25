package nc.portal.search;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import nc.uap.lfw.core.exception.LfwBusinessException;

public class SearchParams implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2586631096250444601L;
	public static final String QUERY = "q"; // ��ѯ��������
	public static final String RETURN = "fl"; // �����ֶ�ָ������
	public static final String START = "start";
	public static final String Page = "page";
	public static final String ROWS = "rows";
	public static final String FILTER_QUERY = "fq";
	public static final String SORT = "sort"; // �������
	public static final String FACET = "facet";
	public static final String FACETFIELD = "facet.field";

	public static final char SPLITC = ':';

	/**
	 * ��ѯ�ַ������������ ��ʽ��fieldName:queryValue ֧�� ���ֶβ�ѯ fieldName1:queryValue1
	 * [fieldName2:queryValue2] ֧�� �ֶζ�ֵ��ѯ fieldName:queryValue1 [queryValue2]
	 * ֧�ַ�Χ��ѯ��fieldName:[0 TO *] ֧��ģ����ѯ��roam~ "jakerta apache"~ ֧��ͨ�����ѯ�����ַ���
	 * te?t,���ַ���test*��te*t ע��:�ֶμ�ո�Ĭ������Ϊ OR������AND ����Ҫ��ȷд�� ��
	 * fieldName1:queryValue1 AND fieldName2:queryValue2
	 */
	private String queryString;
	/**
	 * ��ѯ����������� ��ʽ����ʽͬqueryString
	 */
	private String filterQuery;
	/**
	 * ָ����Ҫ���ص��ֶ� ��ʽ��field[,fiedl2,field]
	 */
	private String returnFields;
	/**
	 * ���ص�һ����¼������ҵ�����е�ƫ������0��ʼ����ҳʹ��
	 * 
	 */
	private Long start;
	/**
	 * ���ؽ������ж�������¼�����start��ʵ�ַ�ҳ,Ĭ��Ϊ10
	 */
	private Long rows = (long)10;
	
	/**
	 * ��ʾ��Ҫ��ѯ��ҳ��
	 */
	private Long pageNum;
	
	/**
	 * ������ֶ� ��ʽ�� fieldName [desc|asc][,fieldName [desc|asc]]
	 */
	private String sortFields;

	/**
	 * ������� field[,fiedl2,field]
	 */
	private String facedFields;// 

	private String userPermissions; // �����û���Ȩ��

	private String category; // �������ݵķ���

	/**
	 * ���������ֶ�ת����map
	 * 
	 * @return
	 * @throws LfwBusinessException
	 */
	public Map toMap() throws LfwBusinessException {
		this.formatQueryString();
		
		Map map = new HashMap();		
		this.setToMap(map, QUERY, queryString, true);
		this.setToMap(map, FILTER_QUERY, filterQuery, false);		
		this.setToMap(map, SORT, sortFields, false);
		this.setToMap(map, Page, pageNum+"", false);
		this.setToMap(map, ROWS, rows+"", false);
		
		return map;
	}

	/**
	 * ���ֶ�ֵ��ӵ�map��
	 * 
	 * @param map
	 *            �洢������map
	 * @param key
	 *            ȡ�����涨��ľ�̬����
	 * @param value
	 *            ȡ���ֶε�ֵ
	 * @param isRequired
	 *            // ���ֶ��Ƿ��Ǳ����
	 * @throws LfwBusinessException
	 */
	private void setToMap(Map map, String key, String value,boolean isRequired) throws LfwBusinessException {
		if (value != null && !value.equals("")) {
			map.put(key, value);
		} else {
			if (isRequired) {
				throw new LfwBusinessException(key+ " can't be null or blank!");
			}
		}
	}

	/**
	 * �����û���Ȩ��
	 * @throws LfwBusinessException 
	 */
	public void setUserPermissions(String[] userPermissions) {
		StringBuffer sb = new StringBuffer("");
		if (userPermissions.length == 1) {
			sb.append(SearchIndexVO.ROLES).append(SPLITC).append(userPermissions[0]);
		}
		if (userPermissions.length > 1) {
			for (int i = 0; i < userPermissions.length; i++) {
				sb.append(SearchIndexVO.ROLES).append(SPLITC).append(userPermissions[i]);
				if (i < userPermissions.length - 1) {
					sb.append(" OR ");
				}
			}
		}
		
		if(sb.length()>0)
			this.userPermissions = "(" + sb.append(" OR ").append(SearchIndexVO.ROLES).append(SPLITC).append(SearchIndexVO.PUBLIC_ROLE).toString() + ")";
		else 
			this.userPermissions = "";

	}

	/**
	 * ��ʽ����ѯ�ַ���������Ȩ�� �� ����
	 * @throws LfwBusinessException 
	 */
	private void formatQueryString() throws LfwBusinessException {
		if(queryString == null || queryString.trim().equals("")){
			throw new LfwBusinessException("queryString can't be null");
		}
		
		//queryString = (new StringBuffer()).append("(").append(queryString).append(")").toString();
		
		
//		// �û�Ȩ�����
//		if(userPermissions!=null && !userPermissions.trim().equals("")){ // ����Ȩ�޿���
//			queryString = (new StringBuffer()).append(queryString).append(" AND ").append(userPermissions).toString();
//		}
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public void setFilterQuery(String filterQuery) {
		this.filterQuery = filterQuery;
	}

	public void setReturnFields(String returnFields) {
		this.returnFields = returnFields;
	}

	public void setRows(Long rows) {
		this.rows = rows;
		this.setStart();
	}
	
	public void setPageNum(Long pageNum) {
		this.pageNum = pageNum;
		this.setStart();
	}
	
	private void setStart(){
		if(pageNum!=null && rows!=null){
			this.start = (pageNum-1)*this.rows;
		}else{
			start = null;
		}
	}
	public void setSortFields(String sortFields) {
		this.sortFields = sortFields;
	}

	public void setFacedFields(String facedFields) {
		this.facedFields = facedFields;
	}

	
	public void setCategory(String category) {
		this.category = category;
	}

	public String getQueryString() {
		return queryString;
	}

	public String getFilterQuery() {
		return filterQuery;
	}

	public String getReturnFields() {
		return returnFields;
	}

	public Long getStart() {
		return start;
	}

	public Long getRows() {
		return rows;
	}

	public String getSortFields() {
		return sortFields;
	}

	public String getFacedFields() {
		return facedFields;
	}

	public String getUserPermissions() {
		return userPermissions;
	}
	public String getCategory() {
		return category;
	}
}
