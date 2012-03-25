package nc.uap.portal.comm.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nc.bs.framework.execute.Executor;
import nc.uap.cpb.org.exception.CpbBusinessException;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.plugins.PluginManager;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.portal.util.PtUtil;

/**
 * Portal��ʱ��
 * 
 * @author licza
 * 
 */
public class PtTimer implements Runnable{
	/**
	 * ����Դ����
	 */
	private String dsName ;
	public static final long TIME_SPLIT = 1000L;

	public PtTimer(String dsName) {
		super();
		this.dsName = dsName;
	}

	/**
	 * ִ��
	 */
	public void doIt() {
		/**
		 * ����
		 */
		Map<String, Long> circle = new ConcurrentHashMap<String, Long>();
		Map<String, Long> modifiedSince = new ConcurrentHashMap<String, Long>();
		Map<String, Tasker> tasker = new ConcurrentHashMap<String, Tasker>();

		List<IPtSchedule> list = getPlugins();
		if (!list.isEmpty()) {
			for (IPtSchedule scl : list) {
				circle.put(scl.getID(), scl.getTimeSpan());
				tasker.put(scl.getID(), new Tasker(scl,dsName));
				modifiedSince.put(scl.getID(), System.currentTimeMillis());
			}
			while (true) {
				try {
					long currentTimeSpan = System.currentTimeMillis();
					if (!tasker.isEmpty()) {
						for (Tasker tk : tasker.values()) {
							String id = tk.getScl().getID();
							if (currentTimeSpan >= modifiedSince.get(id) + circle.get(id)) {
								new Executor(tk).start();
								modifiedSince.put(id, currentTimeSpan);
							}
						}
					}
					Thread.sleep(TIME_SPLIT);
				} catch (InterruptedException e) {
					LfwLogger.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * ��ò��
	 * 
	 * @return ʵ�����Ĳ������
	 */
	public List<IPtSchedule> getPlugins() {
		/**
		 * ʵ�����Ĳ��
		 */
		List<IPtSchedule> agentPlugin = new ArrayList<IPtSchedule>();
		/**
		 * ��ò������
		 */
		List<PtExtension> exs = PluginManager.newIns().getExtensions(
				IPtSchedule.PID);
		if (!PtUtil.isNull(exs)) {
			for (PtExtension ex : exs) {
				try {
					IPtSchedule i = ex.newInstance(IPtSchedule.class);
					agentPlugin.add(i);
				} catch (Exception e) {
					LfwLogger.error("ʵ�������ʧ��,���ID:" + ex.getId(), e);
				}
			}
		}
		return agentPlugin;
	}

	@Override
	public void run() {
		/**
		 * �����߳�����Դ
		 */
		LfwRuntimeEnvironment.setDatasource(dsName);
		/**
		 * ִ��������ȼƻ�
		 */
		doIt();
	}

}

/**
 * ����
 * 
 * @author licza
 * 
 */
class Tasker implements Runnable {
	private String dsName;
	IPtSchedule scl = null;

	public Tasker(IPtSchedule scl,String dsName) {
		this.dsName = dsName;
		this.scl = scl;
	}

	@Override
	public void run() {
		/**
		 * �����̵߳�����Դ
		 */
		LfwRuntimeEnvironment.setDatasource(dsName);
		scl.go();
	}

	public IPtSchedule getScl() {
		return scl;
	}
}