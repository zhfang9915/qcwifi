package ltd.qcwifi.dao.cloud;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import ltd.qcwifi.model.entity.cloud.platform.Operate;

/**
 * 商铺信息
 * @author Administrator
 *
 */
public interface OperateDao {

	/**
	 * 查询经营分类信息
	 * @return
	 */
	List<Operate> selectOperateByLevel(@Param("level")Integer level, @Param("parent")Integer parent);
	
}
