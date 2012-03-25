package nc.uap.portal.integrate.message.impl;

import java.util.ArrayList;
import java.util.List;

import nc.uap.lfw.core.InteractionUtil;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.portal.integrate.message.MCConstant;
import nc.uap.portal.integrate.message.vo.PtMessagecategoryVO;

/**
 * Эͬ������Ϣ
 * @author licza
 *
 */
public class WflNoticeMessageExchange extends PortalNoticeMessageExchange {

	@Override
	public List<PtMessagecategoryVO> getCategory() {
		PtMessagecategoryVO task = new PtMessagecategoryVO("taskmessage",
				MCConstant.TYPE_NOTICE.getSyscode(), "Эͬ����", "WorkFlowMessage",
				"wflnoticemessage");
		List<PtMessagecategoryVO> mcl = new ArrayList<PtMessagecategoryVO>();
		mcl.add(task);
		return mcl;
	}

	@Override
	public void compose(LfwPageContext ctx) {
		InteractionUtil.showMessageDialog("Эͬ������Ϣ��֧�ִ˲���!");
	}

	@Override
	public void reply(LfwPageContext ctx) {
		InteractionUtil.showMessageDialog("Эͬ������Ϣ��֧�ִ˲���!");

	}

	@Override
	public void fwd(LfwPageContext ctx) {
		InteractionUtil.showMessageDialog("Эͬ������Ϣ��֧�ִ˲���!");
	}
}
