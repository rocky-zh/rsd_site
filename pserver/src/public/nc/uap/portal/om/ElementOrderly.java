package nc.uap.portal.om;

/**
 * �������ӿ�. Layout Portletʵ�ִ˽ӿ�.
 * 
 * @author licza
 * 
 */

public interface ElementOrderly extends  Comparable<ElementOrderly>{
	/**
	 * ������ڵ���.
	 * 
	 * @return ������.
	 */
	public Integer getColumn();
	public void setColumn(Integer column);
	public String getName()  ;
}
