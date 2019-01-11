package ltd.qcwifi.protocal.factory;

import ltd.qcwifi.model.dto.ExecuteResult;



public interface ExtensionFactory {

	/**
	 * @param interfaceAliasName
	 *            对应接口实现类的的别名
	 * @return Instances
	 */
	<T> T getExtensionInstances(String interfaceAliasName);

	/**
	 * @param interfaceAliasName
	 *            对应接口实现类的的别名
	 * @return InstancesURL
	 */
	<T> T getExtensionInstancesURL(String interfaceAliasName);

	/**
	 * 获取对应接口实现并执行
	 * 
	 * @param interfaceAliasName
	 * 			对应接口实现类的的别名
	 * @param args
	 * 			执行所需参数
	 * @return
	 */
	<S extends ExecuteResult<?>> S execute(String interfaceAliasName, Object... args);

}
