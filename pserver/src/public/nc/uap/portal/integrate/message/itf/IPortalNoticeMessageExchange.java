package nc.uap.portal.integrate.message.itf;

import nc.uap.lfw.core.event.ctx.LfwPageContext;

/**
 * Portal֪ͨ��Ϣ�������
 * 
 * @author licza
 * 
 */
public interface IPortalNoticeMessageExchange extends IPortalMessage {
	/**
	 * ��չ��
	 */
	public static final String PLUGIN_ID = "PortalNoticeMessage";

	/**
	 * ����Ϣ
	 * 
	 * @param pk
	 */
	public void read(String pk);

	/**
	 * ������Ϣ
	 * 
	 * @param ctx
	 */
	public void compose(LfwPageContext ctx);

	/**
	 * ����Ϣ
	 */
	public void reply(LfwPageContext ctx);

	/**
	 * ת����Ϣ
	 */
	public void fwd(LfwPageContext ctx);

	/**
	 * ɾ���ռ�����Ϣ
	 * 
	 * @param pks
	 */
	public void delInbox(String[] pks);

	/**
	 * ɾ����������Ϣ
	 * 
	 * @param sentpks
	 */
	public void delSent(String[] sentpks);
	
	/**
	 * �鿴��Ϣ
	 * @param pk
	 * @param ctx
	 */
	public void view(String pk ,LfwPageContext ctx);
}
