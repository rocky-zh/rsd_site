package nc.uap.portal.integrate.message.itf;

import java.util.List;

import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.portal.integrate.message.vo.PtMessageVO;
import nc.uap.portal.integrate.message.vo.PtMessagecategoryVO;
import nc.uap.portal.plugins.model.IDynamicalPlugin;

/**
 * Portal��Ϣ���
 * 
 * @author licza
 * 
 */
public interface IPortalMessage extends IDynamicalPlugin {
	
	/**
	 * �����Ϣ
	 * @param category ����
	 * @param timeSection ʱ���(1= һ�� ,2= 30���� ,3=��������)
	 * @param states ��Ϣ״̬ (0 =δ��,1=�Ѷ�,-1=��ɾ��,-2=����ɾ��)
	 * @param pinfo ��ҳ��Ϣ
	 * @return
	 */
	public PtMessageVO[] getMessage(String category, int timeSection, String[] states ,PaginationInfo pi);
	
	/**
	 * ���֧�ֵķ����б�
	 * @return
	 */
	public List<PtMessagecategoryVO> getCategory();
	
	/**
	 * ������Ϣ���������Ϣ
	 * 
	 * @param pk_user
	 * @return
	 */
	public PtMessageVO getMessage(String pk);
	
	/**
	 * ��Ϣ����
	 * 
	 * @param pk ��Ϣ����
	 * @param cmd ����
	 * @param ctx ��ǰҳ��������
	 */
	public void execute(String[] pk,String cmd,LfwPageContext ctx);
	
	/**
	 * �������Ϣ������
	 * @return
	 */
	public Integer getNewMessageCount(String category);

}
