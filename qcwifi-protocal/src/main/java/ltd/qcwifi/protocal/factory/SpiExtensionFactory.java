package ltd.qcwifi.protocal.factory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.protocal.QcwifiService;
import ltd.qcwifi.protocal.spi.ExtensionLoader;

public class SpiExtensionFactory implements ExtensionFactory {
	static final Logger log = LoggerFactory.getLogger(SpiExtensionFactory.class);

	public static final ConcurrentMap<String, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<>();

	public static final ConcurrentMap<String, Object> SERVICE_URL = new ConcurrentHashMap<>();
	
	List<String> ignoreConvertClassNameList = new ArrayList<String>();
	
	public SpiExtensionFactory() {
		ignoreConvertClassNameList.add("org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity");
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getExtensionInstances(String interfaceAliasName) {
		if (EXTENSION_INSTANCES.size() < 0) {
			return null;
		}
		return (T) EXTENSION_INSTANCES.get(interfaceAliasName);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getExtensionInstancesURL(String interfaceAliasName) {
		if (SERVICE_URL.size() < 0) {
			return null;
		}
		return (T) SERVICE_URL.get(interfaceAliasName);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <S extends ExecuteResult<?>> S execute(String interfaceAliasName, Object ...args){
		log.info("--> execute spi service {}]", interfaceAliasName);
		Gson gson = new GsonBuilder().create();
		for(int i=0;i<args.length;i++){
			try{
				if(args[i] != null && !ignoreConvertClassNameList.contains(args[i].getClass().getName())){
					String parameter = gson.toJson(args[i]);
					log.info("--> {} args[{}]: [{}]", interfaceAliasName, i , parameter);
				} else {
					log.info("--> {} args[{}]: [{}]", interfaceAliasName, i , args[i]);
				}
			} catch(Exception ex){
				log.info("--> {} args[{}]: [{}]", interfaceAliasName, i , args[i]);
			}
		}
		QcwifiService service = (QcwifiService)getExtensionInstances(interfaceAliasName);
		log.info("--> {} service is {}" , interfaceAliasName, service );
		S sResult = (S) service.execute(args);
		try{
			log.info("--> spi result for {} [{}]", interfaceAliasName, gson.toJson(sResult));
		} catch(Exception ex){
			log.info("--> spi result for {} [{}]", interfaceAliasName, sResult);
		}
		return sResult;
	}
	static {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String fullName = "META-INF/qcwifi_services/";
		try {
			Enumeration<URL> configs = loader.getResources(fullName);
			while (configs.hasMoreElements()) {
				URL url = configs.nextElement();
				try {
					String path = url.toString();
					if (path.startsWith("jar:file:")) {
						path = path.substring("jar:file:".length(),
								path.length() - ("/"+fullName).length() - 1);
						JarFile jarfile = new JarFile(path);
						Enumeration<JarEntry> em = jarfile.entries();
						while (em.hasMoreElements()) {
							String name = null;
							try {
								JarEntry jarEntry = em.nextElement();
								name = jarEntry.getName();
								if (name.startsWith(fullName) && name.length() > fullName.length()) {
									System.out.println(name.substring(fullName.length()));
									Class<?> clazz = Class.forName(name.substring(fullName.length()));
									log.info("--> load {}", clazz.getName());
									ExtensionLoader.load(clazz);
								}
							} catch (Throwable ex) {
								if (name != null) {
									log.debug("load service {} failed", name);
								}
							}
						}
						jarfile.close();
					} else {
						path = path.substring("file:".length());
						File folder = new File(path);
						if (folder.isDirectory()) {
							String[] files = folder.list();
							for (String file : files) {
								try {
									Class<?> clazz = Class.forName(file.toString());
									log.info("--> load {}", clazz.getName());
									ExtensionLoader.load(clazz);
								} catch (Throwable ex) {
									log.debug("load service {} failed", file);
								}
							}
						}
					}
				} catch (Exception ex) {
					log.warn("load service from {} failed", url);
					log.warn("",ex);
				}
			}
		} catch (IOException ex) {
			log.info("can not load {}", fullName);
		}
	}

	@Deprecated
	/**
	 * 加载过程移至static区块，避免方法误用引起反复加载无数次
	 */
	public void loadExtensions() {
		//do nothing
	}
}
