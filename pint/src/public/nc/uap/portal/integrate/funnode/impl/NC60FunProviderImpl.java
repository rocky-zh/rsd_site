package nc.uap.portal.integrate.funnode.impl;

import org.apache.commons.lang.math.RandomUtils;

import nc.uap.portal.integrate.funnode.IFunIntegrationProvider;
import nc.uap.portal.util.PortalRenderEnv;
import nc.uap.portal.util.PtUtil;

/**
 * NC6.0���ܼ���
 * @author licza
 *
 */
public class NC60FunProviderImpl implements IFunIntegrationProvider {

	@Override
	public String getDetail() {
		return "û������Ϣ";
	}

	@Override
	public String getIcon() {
		String resourcePath = PtUtil.getResourcePath(PortalRenderEnv.getCurrentPage());
		return resourcePath + "/images/function_icon/nc.png";
	}

	@Override
	public String getId() {
		return "nc60";
	}

	@Override
	public Integer getStat() {
		return RandomUtils.nextInt(120);
	}

	@Override
	public String getTitle() {
		return "NCϵͳ";
	}

	@Override
	public Boolean isVisibility() {
		return true;
	}
	public Integer getRank(){
		return 1;
	}
}
