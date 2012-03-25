package nc.uap.portal.integrate.message.listener;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.lfw.core.InteractionUtil;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.ScriptEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.event.listener.ScriptServerListener;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.page.ViewModels;
import nc.uap.lfw.core.serializer.impl.SuperVO2DatasetSerializer;
import nc.uap.lfw.login.vo.LfwSessionBean;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.integrate.message.MCConstant;
import nc.uap.portal.integrate.message.MessageCenter;
import nc.uap.portal.integrate.message.MessageQueryParam;
import nc.uap.portal.integrate.message.itf.IPortalMessage;
import nc.uap.portal.integrate.message.vo.PtMessageVO;
import nc.uap.portal.service.PortalServiceUtil;
import nc.uap.portal.util.PtUtil;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;

/**
 * �������������˼�����
 * 
 * @author licza
 * 
 */
public class ToolBarServerListener extends ScriptServerListener {

	public ToolBarServerListener(LfwPageContext pageCtx, String widgetId) {
		super(pageCtx, widgetId);
	}

	@Override
	public void handlerEvent(ScriptEvent event) {
		String cmd = getGlobalContext().getParameter("cmd");
		if (!PtUtil.isNull(cmd)) {
			try {
				MethodUtils.invokeMethod(this, cmd, null);
			} catch (InvocationTargetException e) {
				/**
				 * ֱ���׳�����ʱ�쳣
				 */
				if (e.getTargetException() instanceof RuntimeException) {
					RuntimeException ne = (RuntimeException) e.getTargetException();
					throw ne;
				}
				LfwLogger.error(e.getMessage(), e);
				throw new LfwRuntimeException(e.getTargetException().getMessage());
			} catch (Exception e) {
				LfwLogger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * ׫д��Ϣ
	 * @throws CpbBusinessException 
	 */
	public void compose() throws CpbBusinessException {
		getPortalMessagePlugin().execute(null, "compose", getGlobalContext());
	}

	/**
	 * ɾ����Ϣ
	 */
	public void del() {		
		LfwWidget widget = getGlobalContext().getPageMeta().getWidget("main");
		Dataset navds = widget.getViewModels().getDataset("navds");
		Row navrow = navds.getSelectedRow();
		String syscode = null;
		if (navrow != null)
			syscode = navrow.getString(navds.nameToIndex("syscode"));
		 
		Dataset ds = widget.getViewModels().getDataset("msgds");
		Row[] rs = ds.getSelectedRows();

		LfwSessionBean ses = LfwRuntimeEnvironment.getLfwSessionBean();
		if (ses == null)
			return;

		/**
		 * ɾ��ȷ��
		 */
		if (PtUtil.isNull(rs))
			throw new LfwRuntimeException("�빴ѡҪɾ������Ϣ");

		StringBuffer confirmmsg = new StringBuffer("ȷ��ɾ��������Ϣ?</br>");
		int length = rs.length;
		for (int i = 0; i < (length > 4 ? 4 : length); i++) {
			Row row = rs[i];
			String title = PtUtil.maxStr(row.getString(ds.nameToIndex("title")), 25);
			confirmmsg.append(title + "</br>");
		}
		if (length > 4)
			confirmmsg.append("��" + length + "����Ϣ?");

		boolean cfm = InteractionUtil.showConfirmDialog("ȷ��", confirmmsg.toString());
		if (!cfm)
			return;
		// �ռ�������Ϣ����
		List<String> pks = new ArrayList<String>();
		// ����������Ϣ����
		List<String> sentpks = new ArrayList<String>();
		for (Row row : rs) {
			String pk_user = row.getString(ds.nameToIndex("pk_user"));
			String pk_sender = row.getString(ds.nameToIndex("pk_sender"));
			String pk_message = row.getString(ds.nameToIndex("pk_message"));
			if (ses.getPk_user().equals(pk_user))
				pks.add(pk_message);
			if (ses.getPk_user().equals(pk_sender))
				sentpks.add(pk_message);
		}
		if(StringUtils.equals(MCConstant.TYPE_TRASH.getSyscode(), syscode)){
			/**
			 * �������е���Ϣ ����ɾ��
			 */
			try {
				PortalServiceUtil.getMessageService().delete(pks.toArray(new String[0]), true);
				PortalServiceUtil.getMessageService().delete(sentpks.toArray(new String[0]), false);
			}catch (PortalServiceException e) {
				LfwLogger.error(e.getMessage(),e);
			}
		}
		else{
			try {
				getPortalMessagePlugin().execute(pks.toArray(new String[0]), "delInbox", getGlobalContext());
				getPortalMessagePlugin().execute(sentpks.toArray(new String[0]), "delSent", getGlobalContext());
			} catch (CpbBusinessException e) {
				LfwLogger.error(e.getMessage(),e);
			}
		}
		for (Row row : rs) {
			ds.removeRow(row);
		}
	}

	/**
	 * ����Ϣ
	 * @throws CpbBusinessException 
	 */
	public void reply() throws CpbBusinessException {
		getPortalMessagePlugin().execute(null, "reply", getGlobalContext());
	}

	/**
	 * ת����Ϣ
	 * @throws CpbBusinessException 
	 */
	public void fwd() throws CpbBusinessException {
		getPortalMessagePlugin().execute(null, "fwd", getGlobalContext());
	}
	
	/**
	 * ִ������
	 */
	public void exec(){
		command("exec");
	}
	
	public void view(){
		command("view");
	}
	/**
	 * ִ������
	 * @param cmd
	 */
	public void command(String cmd){
		ViewModels vms = getGlobalContext().getPageMeta().getWidget("main")	.getViewModels();
		Dataset ds = vms.getDataset("msgds");
		Row row = ds.getSelectedRow();
		if(row == null)
			throw new LfwRuntimeException("�빴ѡҪ��������!");
		String pk = row.getString(ds.nameToIndex("pk_message"));
		try {
			getPortalMessagePlugin().execute(new String[]{pk},cmd, getGlobalContext());
		} catch (CpbBusinessException e) {
			LfwLogger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * ������Ϣ
	 */
	public void receive() {
		ViewModels vms = getGlobalContext().getPageMeta().getWidget("main")	.getViewModels();
		Dataset msgds = vms.getDataset("msgds");
		PaginationInfo pg = msgds.getCurrentRowSet().getPaginationInfo();
		pg.setPageIndex(0);
		pg.setRecordsCount(0);
		MessageQueryParam param = MessageCenter.getMessageQueryParam();;
		PtMessageVO[] vos = MessageCenter.query(param, pg);
		if (vos != null) {
			new SuperVO2DatasetSerializer().serialize(vos, msgds,Row.STATE_NORMAL);
		}
	}

	/**
	 * �����Ѷ�
	 */
	public void includeReaded() {
		String state = getGlobalContext().getParameter("state");
		Boolean includeReaded =  "true".equals(state);
		getGlobalContext().getWebSession().setAttribute(MCConstant.INCLUDE_READED_MESSAGE, includeReaded);
 		ViewModels vms = getGlobalContext().getPageMeta().getWidget("main")	.getViewModels();
		Dataset msgds = vms.getDataset("msgds");
		MessageQueryParam param = MessageCenter.getMessageQueryParam();
		if(includeReaded){
			param.setStates(new String[]{"0","1"});
		}else{
			param.setStates(new String[]{"0"});
		}
		MessageCenter.processMessageQry(param, msgds, true);
	}
	 
	
	/**
	 * ����ʱ���
	 */
	public void setTimeSection() {
		String section = getGlobalContext().getParameter("section");
		if (!PtUtil.isNumbic(section))
			return;
		int timesection = Integer.parseInt(section);
 		ViewModels vms = getGlobalContext().getPageMeta().getWidget("main")	.getViewModels();
		Dataset msgds = vms.getDataset("msgds");
		MessageQueryParam param = MessageCenter.getMessageQueryParam();
		param.setTimeSection(timesection);
		MessageCenter.processMessageQry(param, msgds, true);
	}

	/**
	 * ������ѯ����
	 */
	public void select() {
		
	}
 	
	/**
	 * �����Ϣ���
	 * @return
	 * @throws CpbBusinessException
	 */
	private IPortalMessage getPortalMessagePlugin() throws CpbBusinessException{
		return MCPageComm.getPortalMessagePlugin(getGlobalContext());
	}
}
