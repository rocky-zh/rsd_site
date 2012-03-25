package nc.uap.portal.integrate.message.listener;

import nc.uap.lfw.core.WebSession;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.integrate.message.MCConstant;
import nc.uap.portal.integrate.message.vo.PtMessageVO;
import nc.uap.portal.service.PortalServiceUtil;

/**
 * ��Ϣ�༭�����ݼ�������
 * @author licza
 *
 */
public class EditorDsServerListener extends nc.uap.lfw.core.event.deft.DefaultDatasetServerListener{

	public EditorDsServerListener(LfwPageContext pagemeta, String widgetId) {
		super(pagemeta, widgetId);
	}

	@Override
	public void onAfterRowSelect(DatasetEvent e) {
		super.onAfterRowSelect(e);
	}

	@Override
	public void onDataLoad(DataLoadEvent se) {
		WebSession ses =getGlobalContext().getWebSession();
		String cmd = (String)ses.getAttribute("cmd");
		String pk_message = (String)ses.getAttribute("pk_message");
		LfwWidget widget = getGlobalContext().getPageMeta().getWidget("main");
		Dataset ds = widget.getViewModels().getDataset("msgds");
		
		/**
		 * ��ʼ�����ݼ�
		 */
		ds.setCurrentKey(Dataset.MASTER_KEY);
		Row row = ds.getEmptyRow();
		ds.addRow(row);
		ds.setSelectedIndex(ds.getRowIndex(row));
		ds.setEnabled(true);
		
		/**
		 * ׫д
		 */
		if(cmd.equals("compose")){
			return;
		}
		PtMessageVO vo = null;
		try {
			vo = PortalServiceUtil.getMessageQryService().getMessageByPK(pk_message);
		} catch (PortalServiceException e) {
			LfwLogger.error(e.getMessage(),e);
		}
		
		if(vo == null || vo.getPk_message() == null)
			return;
		
		/**
		 * ת��
		 */
		
		if(cmd.equals("fwd")){
			row.setString(ds.nameToIndex("title"), "ת��:"+vo.getTitle());
			row.setString(ds.nameToIndex("content"), vo.doGetContent());
		}
		/**
		 * �ظ�
		 */
		if(cmd.equals("reply")){
			if(!MCConstant.PERSON_MESSAGE.equals(vo.getSystemcode()))
				throw new LfwRuntimeException("�޷��ظ���˽����Ϣ!");
			row.setString(ds.nameToIndex("title"), "�ظ�:"+vo.getTitle());
			row.setString(ds.nameToIndex("pk_user"), vo.getPk_sender());
			row.setString(ds.nameToIndex("username"), vo.getSendername());
		}
	}

}
