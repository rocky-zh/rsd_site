package nc.uap.portal.deploy;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import nc.bs.framework.common.RuntimeEnv;
import nc.bs.framework.server.ServerConfiguration;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.crud.CRUDHelper;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.servletplus.utils.ClassScan;
import nc.uap.portal.constant.PortalConst;
import nc.uap.portal.constant.PortalEnv;
import nc.uap.portal.deploy.impl.PtLookAndFeelDeploy;
import nc.uap.portal.service.PortalServiceUtil;
import nc.uap.portal.service.itf.IPtPageService;
import nc.uap.portal.service.itf.IPtPortalPageService;
import nc.uap.portal.service.itf.IPtPortletService;
import nc.uap.portal.util.PtUtil;
import nc.uap.portal.util.ToolKit;
import nc.uap.portal.vo.PtModuleVO;
import nc.vo.org.GroupVO;
import nc.vo.pub.lang.UFBoolean;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Portal����
 * 
 * @author licza
 * 
 */
public class PortalDeployer {

	/** HOTWEBS·�� **/
	//private static final String HOTWEBS = "/hotwebs";

	/** Portalģ�鶨���ļ� **/
	//private static final String PORTAL_DEF = "/WEB-INF/portal.xml";

	/** Portal�ļ����ڰ� **/
	private static final String PORTAL_SPEC = ".portalspec";

	/**
	 * ɨ��ģ�鲢����
	 */
	public void doIt() {
		String portalModuleDir = RuntimeEnv.getInstance().getNCHome()
				+ PortalEnv.PORTAL_HOME_DIR;
		ClassLoader cl = Thread.currentThread().getContextClassLoader();

		/**
		 * ����Portalģ���ļ���
		 */
		LfwLogger.error("PortalDeployer#doIt : ����Portalģ���ļ��� --- start ---");
		cleanPortalHome(portalModuleDir);
		LfwLogger.error("PortalDeployer#doIt : ����Portalģ���ļ��� --- end ---");
		/**
		 * ���Portalģ�鲢������Portalģ���ļ���
		 */
		LfwLogger.error("PortalDeployer#doIt : ���Portalģ�鲢������Portalģ���ļ��� --- start ---");

		List<String> modules = getPortalModules();
		checksum(modules);
		if (!PtUtil.isNull(modules)) {
			LfwLogger.error("PortalDeployer#doIt : ���Portalģ�� :" + modules);
			for (String name : modules)
				copyPortalModule(name, portalModuleDir, cl);
		} 
		else {
			LfwLogger.error("PortalDeployer#doIt : û�л��Portalģ�� ");
		}
		LfwLogger.error("PortalDeployer#doIt : ���Portalģ�鲢������Portalģ���ļ��� --- end ---");

		final ServerConfiguration sc = ServerConfiguration.getServerConfiguration();
		/**
		 * ��������Master�ϣ����е���ı�Ҫ��ʼ��
		 */
		if (sc.isSingle() || sc.isMaster()) {
			LfwLogger.error("PortalDeployer#doIt : �ڵ�������Master�ϣ����е���ı�Ҫ��ʼ�� --- start ---");

			LfwLogger.error("PortalDeployer#doIt : ����Portal�����ļ� --- start ---");

			/**
			 * ����Portal�����ļ�
			 */
			PortalServiceUtil.getPortalDeployService().deployAll();

			LfwLogger.error("PortalDeployer#doIt : ����Portal�����ļ� --- end ---");

			/**
			 * ����Portal����
			 */
			LfwLogger.error("PortalDeployer#doIt : ����Portal���� --- start ---");

			new PtLookAndFeelDeploy().doIt();
			LfwLogger.error("PortalDeployer#doIt : ����Portal���� --- end ---");

			/**
			 * Ϊ�����ļ���ͬ����Դ�ļ� 
			 */
			 //syncGruopResource();
			/**
			 * ��ʼ��Portalҳ�漰Portlet����
			 */
			// LfwLogger.debug("PortalDeployer#doIt : ��ʼ��Portalҳ�漰Portlet���� --- start ---");
			// initCache();
			// LfwLogger.debug("PortalDeployer#doIt : ��ʼ��Portalҳ�漰Portlet���� --- end ---");
			/**
			 * ֪ͨ�ӻ�Portal��ʼ���Ѿ����
			 */
			nodifyCluster();
			
			LfwLogger
					.error("PortalDeployer#doIt : �ڵ�������Master�ϣ����е���ı�Ҫ��ʼ�� --- end ---");
		}
	}
	
	/**
	 * ֪ͨ�ӻ�Portal��ʼ���Ѿ����
	 */
	private void nodifyCluster(){
		List<String> modules = new ArrayList<String>();
		List<String> folders = new ArrayList<String>();
//		Map<String, String> folderMap = PortalServiceUtil.getPortletRegistryService().findModuleFolders();
//		Set<String> st = folderMap.keySet();
//		for(String key : st){
//			modules.add(key);
//			folders.add(folderMap.get(key));
//		}
//		
		PtModuleVO moduleQryVO = new PtModuleVO();
		moduleQryVO.setActiveflag(UFBoolean.TRUE);
		try {
			PtModuleVO[] vos = CRUDHelper.getCRUDService().queryVOs(moduleQryVO, null, null, null, null);
			if(vos != null && vos.length > 0){
				for(PtModuleVO vo : vos){
					modules.add(vo.getModuleid());
					folders.add(vo.getModuleid());
				}
			}
		} catch (Exception e) {
			LfwLogger.error(e.getMessage(),e);
		}
		
		PortalDeployMessage pdm = new PortalDeployMessage();
		pdm.addHeader("dsname", LfwRuntimeEnvironment.getDatasource());
		pdm.addHeader("folders", StringUtils.join(folders.iterator(), ","));
		pdm.addHeader("modules", StringUtils.join(modules.iterator(), ","));
		String rootPath = LfwRuntimeEnvironment.getRootPath();
		pdm.addHeader("rootpath", rootPath);
		PortalServiceUtil.getPortalStatusService().signPortalCoreStartComplete(pdm);
	}
	
	/**
	 * Ϊ�����ļ���ͬ����Դ
	 */
	 public void syncGruopResource(){
		 try {
			 GroupVO[] groups = CpbServiceFacility.getCpGroupQry().queryAllGroupVOs();
			 if(PtUtil.isNull(groups))
				 return;
			 for(GroupVO gg : groups){
				String pk_group = gg.getPk_group();
			 	PortalServiceUtil.getPageService().sync(pk_group);
			 	PortalServiceUtil.getPortletService().sync(pk_group);
//				CpbServiceFacility.getCpResourceBill().resourceSynchronize(pk_group);
			 }
		 } catch (Exception e) {
			 LfwLogger.error("����ͬ��ʧ��",e);
		 }
	 }
	/**
	 * ��ʼ��Portalҳ�漰Portlet����
	 */
	// private void initCache(){
	// PortalServiceUtil.getRegistryService().loadPortalPages();
	// PortalServiceUtil.getPortletRegistryService().loadPortlets();
	// PortalServiceUtil.getConfigRegistryService().init();
	// }
	/**
	 * ����Portalģ���ļ���
	 */
	public static void cleanPortalHome(String portalModuleDir) {
		File pmd = new File(portalModuleDir);
		if (pmd.exists()) {
			try {
				FileUtils.cleanDirectory(pmd);
			} catch (IOException e) {
				LfwLogger.error("Portalģ������ʧ��!", e);
			}
		}
	}

	/**
	 * ���Portalģ��
	 * 
	 * @return
	 */
	@SuppressWarnings("restriction")
	private List<String> getPortalModules() { 
		String moduleStr = LfwRuntimeEnvironment.getServletContext().getInitParameter(PortalConst.MODULES);
		String[] modules = null;
		if(moduleStr != null && !moduleStr.equals(""))
			modules = moduleStr.split(",");
		else 
			modules = new String[0];
		List<String> modulelist = new ArrayList<String>();
		modulelist.addAll(Arrays.asList(modules));
		Set<String> moduleSet = resourceScaner("portalspec", Thread.currentThread().getContextClassLoader());
		if(moduleSet != null && !moduleSet.isEmpty()){
			Iterator<String> it = moduleSet.iterator();
			while(it.hasNext()){
				modulelist.add(it.next().replace("portalspec/", "").replace("/portal.xml", ""));
			}
		}else{
			LfwLogger.error("###Portal����ʱ�쳣###����û��ɨ�赽ģ�鶨���ļ����⽫ֻ����web.xml���õ�Ĭ��ģ��[" + moduleStr + "]��");
		}
		return modulelist;
	}
	/**
	 * ��ģ�����У��
	 * @param modules
	 */
	public static void checksum( List<String> modules){
		ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
		if(ToolKit.notNull(modules)){
			for(String module : modules){
				URL path = currentClassLoader.getResource(module + "/portalspec/portal.xml" );
				if(path == null){
					LfwLogger.error("###Portal����ʱ�쳣###�� ģ��[" + module + "]�����ļ�δ�ҵ���" );
				}else{
					URL checksumpath = currentClassLoader.getResource(module + "/portalspec/checksum/" );
					if(checksumpath == null)
						LfwLogger.error("###Portal����ʱ�쳣###�� ģ��[" + module + "]�ļ�" + path + "У��ʧ�ܣ�" );
				}
			}
			
		}
	}
	
	
	/**
	 * ɨ��Portalģ���ļ���������Portalģ���ļ���
	 * 
	 * @param name
	 * @param dir
	 * @param cl
	 */
	public static void copyPortalModule(String name, String dir, ClassLoader cl) {
		String pkg = name + PORTAL_SPEC;
		LfwLogger.info("portal module pkg : "  + pkg);
		Set<String> resources = resourceScaner(pkg, cl);
		LfwLogger.info("portal module pkg : "  + resources.toString());
		copyModuleResource(dir, new ArrayList<String>(resources), cl);
	}

	/**
	 * �����ļ���Portalģ���ļ���
	 * 
	 * @param moduleHome
	 * @param resources
	 * @param cl
	 */
	private static void copyModuleResource(String moduleHome, List<String> resources,
			ClassLoader loader) {
		File f = new File(moduleHome + "/");
		if (!f.exists())
			f.mkdirs();
		for (int i = 0; i < resources.size(); i++) {
			String path = resources.get(i);
			if (path != null) {
				InputStream input = null;
				OutputStream fout = null;
				try {
					input = loader.getResourceAsStream(path);
					if (input != null) {
						String filePath = moduleHome + "/" + path;
						String dirPath = filePath.substring(0, filePath
								.lastIndexOf("/"));
						File dir = new File(dirPath);
						if (!dir.exists())
							dir.mkdirs();
						fout = new FileOutputStream(filePath);
						byte[] bytes = new byte[1024 * 4];
						int count = input.read(bytes);
						while (count != -1) {
							fout.write(bytes, 0, count);
							count = input.read(bytes);
						}
					}
				} catch (Exception e) {
					LfwLogger.error(e.getMessage(), e);
				} finally {
					if (input != null)
						try {
							input.close();
						} catch (IOException e1) {
							LfwLogger.error(e1.getMessage(), e1);
						}
					if (fout != null)
						try {
							fout.close();
						} catch (IOException e) {
							LfwLogger.error(e.getMessage(), e);
						}
				}
			} else
				break;
		}
	}

	/**
	 * ���ݱ�����ȡ�������е���Դ
	 * 
	 * @param packageName
	 * @return
	 */
	private static Set<String> resourceScaner(String packageName, ClassLoader cl) {
		// ��Դ����
		Set<String> classes = new LinkedHashSet<String>();
		boolean recursive = true;
		String packageDirName = packageName.replace('.', '/');
		if(RuntimeEnv.isRunningInWebSphere()){
			if(!packageDirName.endsWith("/")){
				packageDirName = packageDirName + "/";
			}
		}
		Enumeration<URL> dirs;
		try {
			dirs = cl.getResources(packageDirName);
			// ѭ��������ȥ
			while (dirs.hasMoreElements()) {
				// ��ȡ��һ��Ԫ��
				URL url = dirs.nextElement();
				// �õ�Э�������
				String protocol = url.getProtocol();
				// ��������ļ�����ʽ�����ڷ�������
				if (ClassScan.URL_PROTOCOL_FILE.equals(protocol)) {
					// ��ȡ����·��
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// ���ļ��ķ�ʽɨ���������µ��ļ� ����ӵ�������
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, classes);
				} else {
					Enumeration<JarEntry> entries = null;
					JarFile jar = null;
					boolean isNewJar = false;
					// �����jar���ļ�
					try {
						if (ClassScan.URL_PROTOCOL_JAR.equals(protocol)
								|| ClassScan.URL_PROTOCOL_ZIP.equals(protocol)
								|| ClassScan.URL_PROTOCOL_WSJAR
										.equals(protocol)) {
							String filepath = url.getFile();
							int idx = filepath.indexOf("!");
							// ȥ������Ϣ ����Weblogic�ᱨFileNotFoundException
							if (idx > 0)
								filepath = filepath.substring(0, filepath
										.indexOf("!"));
							jar = ClassScan.getJarFile(filepath);
						}
						if (jar != null) {
							entries = jar.entries();
							isNewJar = true;

							if (entries != null) {
								while (entries.hasMoreElements()) {
									JarEntry entry = entries.nextElement();
									String name = entry.getName();
									// �������/��ͷ��
									if (name.charAt(0) == '/') {
										// ��ȡ������ַ���
										name = name.substring(1);
									}
									// ���ǰ�벿�ֺͶ���İ�����ͬ
									if (name.startsWith(packageDirName)) {
										int idx = name.lastIndexOf('/');
										// �����"/"��β ��һ����
										if (idx != -1) {
											// ��ȡ���� ��"/"�滻��"."
											packageName = name
													.substring(0, idx).replace(
															'/', '.');
										}
										// ������Ե�����ȥ ������һ����
										if ((idx != -1) || recursive) {
											// �����һ��.class�ļ� ���Ҳ���Ŀ¼
											if (!entry.isDirectory()) {
												try {
													// ��ӵ�classes
													classes.add(name);

												} catch (Exception e) {
													LfwLogger.error(e
															.getMessage(), e);
												}
											}
										}
									}
								}
							}
						}
					} catch (IOException e) {
						LfwLogger.error(e.getMessage(), e);
					} finally {
						if (isNewJar) {
							if (jar != null) {
								jar.close();
								jar = null;
							}
							isNewJar = false;
						}
					}
				}
			}
		} catch (IOException e) {
			LfwLogger.error(e.getMessage(), e);
		}
		return classes;
	}

	/**
	 * 
	 * 
	 * ���ļ�����ʽ����ȡ���µ�����Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	private static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, Set<String> classes) {
		// ��ȡ�˰���Ŀ¼ ����һ��File
		File dir = new File(packagePath);
		// ��������ڻ��� Ҳ����Ŀ¼��ֱ�ӷ���
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		// ������� �ͻ�ȡ���µ������ļ� ����Ŀ¼
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// �Զ�����˹��� �������ѭ��(������Ŀ¼) ��������.class��β���ļ�(����õ�java���ļ�)
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.isFile());
			}
		});

		for (File file : dirfiles) {
			// �����Ŀ¼ �����ɨ��
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "."
						+ file.getName(), file.getAbsolutePath(), recursive,
						classes);
			} else {
				String className = file.getName();
				try {
					classes
							.add(packageName.replace(".", "/") + '/'
									+ className);
				} catch (Exception e) {
					LfwLogger.error(e.getMessage(), e);
				}
			}
		}
	}
}
