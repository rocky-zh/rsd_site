package test;

import nc.bs.framework.test.AbstractTestCase;
import nc.uap.lfw.core.LfwRuntimeEnvironment;

/**
 * Portal���Գ���
 * ������Portal����Դ
 * @author licza
 *
 */
public class PortalTestCase extends AbstractTestCase{

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		LfwRuntimeEnvironment.setDatasource("design");
	}
	
}
