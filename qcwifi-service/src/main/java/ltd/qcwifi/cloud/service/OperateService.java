package ltd.qcwifi.cloud.service;

import java.util.List;
import ltd.qcwifi.model.entity.cloud.platform.Operate;

/**
 * 商铺信息
 * @author Administrator
 *
 */
public interface OperateService {

	/**
	 * 查询经营分类
	 * @param param
	 * @return
	 */
	List<Operate> selectOperateByLevel(Integer level, Integer parent);
}
