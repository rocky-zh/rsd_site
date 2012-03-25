package nc.uap.portal.integrate.message.listener;

import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.lfw.core.WebSession;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.ViewModels;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.integrate.message.MessageCenter;
import nc.uap.portal.integrate.message.MessageQueryParam;
import nc.uap.portal.integrate.message.itf.IPortalMessage;

/**
 * ��Ϣ����ҳ�泣�÷���
 * @author licza
 *
 */
public class MCPageComm {

	/**
	 * ��ò��ID
	 * @param ctx
	 * @return
	 */
	public static String getPluginID(LfwPageContext ctx){
		ViewModels vms = ctx.getPageMeta().getWidget("main")	.getViewModels();
		Dataset ds = vms.getDataset("navds");
		Row row = ds.getSelectedRow();
		String pluginid = null;
		/**
		 * �ӵ�����Ѱ��
		 */
		if(row != null){
			pluginid = row.getString(ds.nameToIndex("pluginid"));
		}
		/**
		 * ��session��Ѱ��
		 */
		if(pluginid == null){
			WebSession  ses = ctx.getWebSession();
			MessageQueryParam param = MessageCenter.getMessageQueryParam();
			if(param != null)
				pluginid = param.getPluginid();
		}
		/**
		 * ����Ϣѡ������Ѱ��
		 */
		if(pluginid == null){
			  ds = vms.getDataset("msgds");
			  row = ds.getSelectedRow();
			  if(row != null){
					String systemcode = row.getString(ds.nameToIndex("systemcode"));
					pluginid = MessageCenter.getSys2PluginDic().get(systemcode);
			  }
		}
		return pluginid;
	}
	/**
	 * ��ò��ID
	 * @param ctx
	 * @return
	 */
	public static String getSystemCode(LfwPageContext ctx){
		ViewModels vms = ctx.getPageMeta().getWidget("main")	.getViewModels();
		Dataset ds = vms.getDataset("navds");
		Row row = ds.getSelectedRow();
		String syscode = null;
		/**
		 * �ӵ�����Ѱ��
		 */
		if(row != null){
			syscode = row.getString(ds.nameToIndex("syscode"));
		}
		/**
		 * ��session��Ѱ��
		 */
		if(syscode == null){
			WebSession  ses = ctx.getWebSession();
			MessageQueryParam param = MessageCenter.getMessageQueryParam();
			if(param != null)
				syscode = param.getCategory();
		}
		/**
		 * ����Ϣѡ������Ѱ��
		 */
		if(syscode == null){
			  ds = vms.getDataset("msgds");
			  row = ds.getSelectedRow();
			  if(row != null){
				  syscode = row.getString(ds.nameToIndex("systemcode"));
			  }
		}
		return syscode;
	}
	/**
	 * �����Ϣ���
	 * @return
	 * @throws PortalServiceException
	 */
	public static IPortalMessage getPortalMessagePlugin(LfwPageContext ctx) throws CpbBusinessException{
		String pluginid = getPluginID(ctx);
		if(pluginid == null || pluginid.length() <= 0)
			throw new LfwRuntimeException("��ѡ�����!");
		return MessageCenter.lookup(pluginid);
	}
}
