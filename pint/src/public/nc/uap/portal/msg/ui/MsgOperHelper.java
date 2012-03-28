package nc.uap.portal.msg.ui;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.WebSession;
import nc.uap.lfw.core.cmd.base.FromWhereSQL;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.ViewContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.jsp.uimeta.UITabComp;
import nc.uap.portal.msg.model.MsgCategory;
import nc.uap.portal.msg.provide.IMsgProvide;
import nc.uap.portal.plugins.PluginManager;

/**
 * ��Ϣ������
 * 
 * @author licza
 * 
 */
public class MsgOperHelper {
	/**
	 * ���ڵ����ݼ���id
	 */
	public static final String TREE_DS = "cateds";
	/**
	 * ��Ϣ�е�TabID
	 */
	public static final String MSG_BOX_TAB = "msgBoxTab";
	/**
	 * ���һ����Ϣ�б��
	 */
	public static final String LAST_ITEM_INDEX = "1";
	/**
	 * �ڶ�����Ϣ�����ݼ�ID
	 */
	public static final String BOX2_DS = "sentds";
	/**
	 * ��һ����Ϣ�����ݼ�ID
	 */
	public static final String BOX1_DS = "msgds";

	/**
	 * �����Ϣ���
	 * 
	 * @param pluginid
	 * @param pm
	 */
	public static IMsgProvide getMsgProvide(String pluginid) {
		PluginManager pm = PluginManager.newIns();
		try {
			return pm.getExtension(pluginid).newInstance(IMsgProvide.class);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * ��õ�ǰ����Ϣ����
	 * 
	 * @return
	 */
	public static int getCurrentMsgBoxIndex() {
		/**
		 * ������Ϣ�е����Ƶ�״̬��,��ѡ�е�һ��
		 */
		UITabComp boxTab = getBoxTab();

		String currentItem = boxTab.getCurrentItem();

		if (LAST_ITEM_INDEX.equals(currentItem)) {
			return 1;
		}
		return 0;
	}

	/**
	 * ��View�������л�ȡ��Ϣ������
	 * 
	 * @return
	 */
	public static UITabComp getBoxTab() {
		ViewContext viewCtx = AppLifeCycleContext.current().getWindowContext().getViewContext("main");
		// return
		// (UITabComp)UIElementFinder.findElementById(viewCtx.getUIMeta(),
		// MSG_BOX_TAB);
		return (UITabComp) viewCtx.getUIMeta().findChildById(MSG_BOX_TAB);
	}

	/**
	 * ��õ�ǰ��dataset
	 * 
	 * @return
	 */
	public static Dataset getCurrentDataset(int idx) {
		
		String dsName = BOX1_DS;
		if (idx == 1) {
			dsName = BOX2_DS;
		} else if (idx == 2)
			dsName = TREE_DS;
		ViewContext viewCtx = AppLifeCycleContext.current().getWindowContext().getViewContext("main");

		return viewCtx.getView().getViewModels().getDataset(dsName);
	}
	
	/**
	 * ��õ�ǰ����
	 * @return
	 */
	public static MsgCategory getCurrentCategory() {
		ViewContext viewCtx = AppLifeCycleContext.current().getWindowContext().getViewContext("cate");
		Dataset ds = viewCtx.getView().getViewModels().getDataset("cateds");
		Row currentRow = ds.getSelectedRow();

		int pluginidIdx = ds.nameToIndex(MsgCategory.PLUGINID);
		int idIdx = ds.nameToIndex(MsgCategory.ID);

		String id = currentRow.getString(idIdx);
		String pluginid = currentRow.getString(pluginidIdx);

		/**
		 * ���ݲ��ID��õ�ǰ��Ϣ���
		 */
		IMsgProvide provide = MsgOperHelper.getMsgProvide(pluginid);

		/**
		 * ���ҵ�ǰ��Ϣ����
		 */
		return MsgDataTranslator.findCategoryById(id, provide.getCategory());
	}
	public static FromWhereSQL getQryParam() {
		WebSession ws = LfwRuntimeEnvironment.getWebContext().getWebSession();
		FromWhereSQL qryParam = (FromWhereSQL) ws.getAttribute("MSG_QRY_PARAM");
		return qryParam;
	}
}
