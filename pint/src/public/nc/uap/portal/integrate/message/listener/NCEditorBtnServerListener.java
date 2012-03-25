package nc.uap.portal.integrate.message.listener;

import nc.bcmanage.bs.IBusiCenterManageService;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.message.vo.MessageVO;
import nc.message.vo.NCMessage;
import nc.uap.lfw.core.InteractionUtil;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.comp.ButtonComp;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.uif.listener.UifMouseListener;
import nc.uap.portal.integrate.credential.PtCredentialVO;
import nc.uap.portal.integrate.message.MessageCenter;
import nc.uap.portal.integrate.message.impl.NCNoticeMessageExchange;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;

public class NCEditorBtnServerListener extends UifMouseListener<ButtonComp>{

	public NCEditorBtnServerListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onclick(MouseEvent<ButtonComp> e) {
		/**
		 * �ύ
		 */
		if("submitbtn".equals(e.getSource().getId())){
			String currentDs = LfwRuntimeEnvironment.getDatasource();

			Dataset ds = getCurrentContext().getWidget().getViewModels().getDataset("msgds");
			Row row = ds.getSelectedRow();
			String pk_user = row.getString(ds.nameToIndex("pk_user"));
			String content = row.getString(ds.nameToIndex("content"));
			String title = row.getString(ds.nameToIndex("title"));
//			Object priorityVal = row.getValue(ds.nameToIndex("priority"));
//			int priority = priorityVal == null ? 0 : row.getInt(ds.nameToIndex("priority"));
			
			String pluginid = "ncnoticemessage";
			try {
				NCNoticeMessageExchange pm = (NCNoticeMessageExchange)MessageCenter.lookup(pluginid);
				PtCredentialVO credential = pm.getCredentialVO();
				/**
				 * ���NC������Դ
				 */
			    String bc = credential.getCredentialReference().getValue("accountcode");
				String rmtDs = pm.getLocator().lookup(IBusiCenterManageService.class).getBusiCenterByCode(bc).getDataSourceName();
				InvocationInfoProxy.getInstance().setUserDataSource(rmtDs);

				if(pk_user == null || pk_user.length() <= 0  ){
					throw new LfwRuntimeException("��ѡ���ռ���!");
				}
				String[] pks = pk_user.split(",");
				for(int i = 0 ; i < pks.length ; i++){
					NCMessage msg = new NCMessage();
					MessageVO m = new MessageVO();
					m.setIsread(UFBoolean.FALSE);
					m.setMsgsourcetype("notice");
					m.setSubject(title);
					m.setContent(content);
					m.setPriority(5);
					m.setSender(credential.getCredentialReference().getValue("ncuserpk"));
					m.setReceiver(pks[i]);
					m.setSendtime(new UFDateTime());
	//				m.setDestination(MessageVO.INBOX);
					msg.setMessage(m);
					pm.sendMessage(msg);
				}
				InteractionUtil.showMessageDialog("��Ϣ���ͳɹ�!");
			} catch (Exception e1) {
				throw new LfwRuntimeException(e1);
			}finally{
				/**
				 * �ѱ��ص�DataSource���û���
				 */
				InvocationInfoProxy.getInstance().setUserDataSource(currentDs);
			}
		}
		/**
		 * ȡ��
		 */
		else{
			getGlobalContext().closeWindow();
		}
	}
}
