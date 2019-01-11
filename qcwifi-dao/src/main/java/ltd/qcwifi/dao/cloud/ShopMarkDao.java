package ltd.qcwifi.dao.cloud;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.model.entity.cloud.platform.ShopMark;

/**
 * 商铺标签信息
 * @author Administrator
 *
 */
public interface ShopMarkDao {

	/**
	 * 创建商铺标签
	 * @param shop
	 * @return
	 */
	int createShopMark(ShopMark mark);
	
	/**
	 * 查询明显所有的商铺标签
	 * @param userId
	 * @return
	 */
	List<ShopMark> selectAll(@Param("createBy")Long userId);
	
	/**
	 * 商铺标签关系
	 * @param userId
	 * @param marks
	 * @return
	 */
	int createShopMarkRelation(@Param("shopId")Long shopId, @Param("list")List<String> marks);
}
