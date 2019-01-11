package ltd.qcwifi.system.service;

/**
 * 系统服务
 * @author 张芳
 *
 */
public interface SystemService {

	/**
	 * 刷新数据库缓存
	 * @return
	 */
	boolean flushDBCache();
}
