package nc.uap.portal.integrate.message.impl;

import java.util.ArrayList;
import java.util.List;


import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.PaginationInfo;

import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.integrate.message.MCConstant;
import nc.uap.portal.integrate.message.MessageCenter;
import nc.uap.portal.integrate.message.itf.AbstractNoticeMessageExchange;
import nc.uap.portal.integrate.message.itf.IPortalNoticeMessageExchange;
import nc.uap.portal.integrate.message.vo.PtMessageVO;
import nc.uap.portal.integrate.message.vo.PtMessagecategoryVO;
import nc.uap.portal.service.PortalServiceUtil;
import nc.uap.portal.util.PtUtil;

/**
 * Portal֪ͨ��Ϣ���
 * 
 * @author licza
 * 
 */
public class PortalNoticeMessageExchange extends AbstractNoticeMessageExchange
		implements IPortalNoticeMessageExchange {

	/**
	 * ��Ϣ�༭ҳ���ַ
	 */
	private static final String MSG_BOX_URL = "core/uimeta.um?pageId=msgeditor";

	@Override
	public void read(String pk) {
		try {
			PortalServiceUtil.getMessageService().read(pk);
		} catch (PortalServiceException e) {
			LfwLogger.error(e.getMessage(), e);
		}

	}

	@Override
	public void compose(LfwPageContext ctx) {
		ctx.openNewWindow(MSG_BOX_URL + "&cmd=compose", "����Ϣ", "660", "560");
	}

	@Override
	public void reply(LfwPageContext ctx) {
		String pk_message = MessageCenter.getSelectMessagePks(ctx)[0];
		ctx.openNewWindow(
				MSG_BOX_URL + "&cmd=reply&pk_message=" + pk_message, "����Ϣ",
				"680", "560" );
	}

	@Override
	public void fwd(LfwPageContext ctx) {
		String pk_message = MessageCenter.getSelectMessagePks(ctx)[0];
		ctx.openNewWindow(MSG_BOX_URL + "&cmd=fwd&pk_message=" + pk_message,
				"ת����Ϣ", "660", "560" );
	}

	@Override
	public void delInbox(String[] pks) {
		try {
			PortalServiceUtil.getMessageService().trash(pks, true);
		} catch (PortalServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}

	@Override
	public void delSent(String[] sentpks) {
		try {
			PortalServiceUtil.getMessageService().trash(sentpks, false);
		} catch (PortalServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}

	@Override
	public List<PtMessagecategoryVO> getCategory() {
		
		PtMessagecategoryVO person = new PtMessagecategoryVO("personmessage",
				MCConstant.TYPE_NOTICE.getSyscode(), "����", "PortalMessage",
				"noticemessage");
		List<PtMessagecategoryVO> mcl = new ArrayList<PtMessagecategoryVO>();
		mcl.add(person);
		return mcl;
	}

	@Override
	public PtMessageVO[] getMessage(String category, int timeSection,
			String[] states ,PaginationInfo pg) {
		try {
			return PortalServiceUtil.getMessageQryService().getMessage(
					getPkUser(), new String[] { category },
					Integer.valueOf(timeSection), states,pg);
		} catch (PortalServiceException e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}

	@Override
	public PtMessageVO getMessage(String pk) {
		try {
			return PortalServiceUtil.getMessageQryService().getMessageByPK(pk);
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}

	@Override
	public Integer getNewMessageCount(String category) {
		try {
			if(MCConstant.TYPE_SENT.getSyscode().equals(category))
				return  PortalServiceUtil
				.getMessageQryService().getSentMessageCount(getPkUser());
			
			return PortalServiceUtil
					.getMessageQryService()
					.getNewMessageCounts(getPkUser(), new String[] { category });
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage());
		}
	}

	@Override
	public void view(String pk, LfwPageContext ctx) {
		LfwWidget widget = ctx.getPageMeta().getWidget("main");
		Dataset ds = widget.getViewModels().getDataset("msgds");
		Row row = ds.getFocusRow();
		if(row == null)
			return;
		String targeturl = row.getString(ds.nameToIndex("targeturl"));
		if(PtUtil.isNull(targeturl))
			return;
		if(targeturl.indexOf("?") != -1){
			targeturl = targeturl + "&";
		}else{
			targeturl = targeturl + "?";
		}
		targeturl+="token="+LfwRuntimeEnvironment.getLfwSessionBean().getSsotoken();
		String title = row.getString(ds.nameToIndex("title"));
		ctx.openNewWindow(targeturl, title, "800", "600");
	}
	

}
