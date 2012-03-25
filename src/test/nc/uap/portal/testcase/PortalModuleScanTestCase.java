package nc.uap.portal.testcase;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


import nc.bs.framework.common.NCLocator;
import nc.bs.framework.common.RuntimeEnv;
import nc.bs.framework.test.AbstractTestCase;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.portal.constant.PortalEnv;
import nc.uap.portal.service.itf.IPortalDeployService;
import sun.net.www.protocol.jar.URLJarFile;

/**
 * Portalģ��ɨ���������
 * 
 * @author licza
 * 
 */
public class PortalModuleScanTestCase extends AbstractTestCase {

	/**
	 * ����ɨ��ģ��
	 */
	public void testScanModule() {
		String dir = RuntimeEnv.getInstance().getNCHome() + "/modules";
		File f = new File(dir);
		File[] fs = f.listFiles();
		for (int i = 0; i < fs.length; i++) {
			File file = fs[i];
			if (file.isDirectory()) {
				String fn = file.getName();
				URL url = Thread.currentThread().getContextClassLoader()
						.getResource(fn + "/portalspec/portal.xml");
				 if(url != null)
					 scanf(fn);
			}
		}
		IPortalDeployService pds = NCLocator.getInstance().lookup(IPortalDeployService.class);
//		pds.deployAll();
	}

	
	public void scanf(String fn) {
		String pkg = fn + ".portalspec";
		Set<String> resources = resourceScaner(pkg);
		
		//String dir = RuntimeEnv.getInstance().getNCHome() + PortalEnv.PORTAL_HOME_DIR;
		//copyTmpFiles(dir, new ArrayList<String>(resources));
	}
	
	
	/**
	 * �����ļ�����ʳ�ļ���
	 * 
	 * @param loader
	 * @param basePath
	 * @param tmpDir
	 * @param props
	 */
	protected void copyTmpFiles(String tmpDir, List<String> props) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		File f = new File(tmpDir + "/");
		if (!f.exists())
			f.mkdirs();
	
		for (int i = 1; i < props.size(); i++) {
			String classpath =  props.get(i);
			int index = classpath.lastIndexOf(".");
			String path =   classpath.substring(0,index ).replace(".", "/")+classpath.substring(index);
			if (path != null) {
				InputStream input = null;
				OutputStream fout = null;
				try {
					input = loader.getResourceAsStream( path);
					if (input != null) {
						String filePath = tmpDir + "/"  + path;
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
	public static Set<String> resourceScaner(String packageName) {
		// ��һ��class��ļ���
		Set<String> classes = new LinkedHashSet<String>();
		boolean recursive = true;
		String packageDirName = packageName.replace('.', '/');
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(
					packageDirName);
			// ѭ��������ȥ
			while (dirs.hasMoreElements()) {
				// ��ȡ��һ��Ԫ��
				URL url = dirs.nextElement();
				// �õ�Э�������
				String protocol = url.getProtocol();
				// ��������ļ�����ʽ�����ڷ�������
				if ("file".equals(protocol)) {
					// ��ȡ��������·��
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// ���ļ��ķ�ʽɨ���������µ��ļ� ����ӵ�������
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, classes);
				}else{
					
					Enumeration<JarEntry> entries = null;
					try {
						if("jar".equals(protocol)){
							// �����jar���ļ�
							// ����һ��JarFile
							JarFile  jar = ((JarURLConnection) url.openConnection()).getJarFile();
							// �Ӵ�jar�� �õ�һ��ö����
							entries = jar.entries();
						}
						
						// ---Modify by licza start--- ������weblogic��was���޷���ȡjar����bug
						if("zip".equals(protocol) || "wsjar".equals(protocol)){
							String filepath = url.getFile();
							int idx = filepath.indexOf("!");
							//ȥ������Ϣ  ����Weblogic�ᱨFileNotFoundException
							if(idx > 0)
								filepath = filepath.substring(0,filepath.indexOf("!"));
							//ͨ��java.io.File ���jar��
							URLJarFile ufj = new URLJarFile(new File(filepath));
							entries = ufj.entries();
						}
						// ---Modify by licza end---
						if(entries != null){
							while (entries.hasMoreElements()) {
								// ��ȡjar���һ��ʵ�� ������Ŀ¼ ��һЩjar����������ļ� ��META-INF���ļ�
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
										packageName = name.substring(0, idx)
												.replace('/', '.');
									}
									// ������Ե�����ȥ ������һ����
									if ((idx != -1) || recursive) {
										// �����һ��.class�ļ� ���Ҳ���Ŀ¼
										if (!entry.isDirectory()) {
											try {
												// ��ӵ�classes
												classes.add(name.replace("/", "."));

											} catch (Exception e) {
												LfwLogger.error(e.getMessage(), e);
											}
										}
									}
								}
							}
						}
					} catch (IOException e) {
						LfwLogger.error(e.getMessage(), e);
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
	public static void findAndAddClassesInPackageByFile(String packageName,
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
					classes.add(packageName + '.' + className);
				} catch (Exception e) {
					LfwLogger.error(e.getMessage(), e);
				}
			}
		}
	}

	 
}
