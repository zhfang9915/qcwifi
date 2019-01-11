package ltd.qcwifi.dao.cloud;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.model.dto.param.RouterParam;
import ltd.qcwifi.model.entity.cloud.platform.ShopRouter;

/**
 * 商铺路由器信息
 * @author Administrator
 *
 */
public interface ShopRouterDao {

	/**
	 * 插入设备信息
	 * @Title: insert
	 * @author: zhfang
	 * @Description: TODO
	 * @param sr
	 * @return
	 * @return: int
	 */
	int insert(ShopRouter sr);
	
	/**
	 * 根据商铺查询设备信息
	 * @Title: selectByshop
	 * @author: zhfang
	 * @Description: TODO
	 * @param shopId
	 * @return
	 * @return: ShopRouter
	 */
	ShopRouter selectByshop(@Param("shopId")Long shopId, @Param("createBy")Long createBy);
	
	/**
	 * 根据id获取设备信息
	 * @Title: selectBydevNo
	 * @author: zhfang
	 * @Description: TODO
	 * @param devNo
	 * @return
	 * @return: ShopRouter
	 */
	ShopRouter selectBydevNo(@Param("devNo")String devNo);
	
	/**
	 * 根据mac获取设备编码
	 * @Title: selectBydevMac
	 * @author: zhfang
	 * @Description: TODO
	 * @param mac
	 * @return
	 * @return: ShopRouter
	 */
	String selectNoByMac(@Param("mac")String mac);
	
	/**
	 * 设备绑定
	 * @Title: binding
	 * @author: zhfang
	 * @Description: TODO
	 * @param sr
	 * @return
	 * @return: int
	 */
	int binding(ShopRouter sr);
	
	/**
	 * 取消绑定
	 * @Title: unbindingShop
	 * @author: zhfang
	 * @Description: TODO
	 * @param devNo
	 * @param createBy
	 * @param shopId
	 * @return
	 * @return: int
	 */
	int unbindingShop(@Param("devNo")String devNo, 
			@Param("createBy")Long createBy, 
			@Param("shopId")Long shopId);
	
	/**
	 * 分页查询
	 * @Title: selectByPage
	 * @author: zhfang
	 * @Description: TODO
	 * @param param
	 * @return
	 * @return: List<ShopRouter>
	 */
	List<ShopRouter> selectByPage(RouterParam param);
}
