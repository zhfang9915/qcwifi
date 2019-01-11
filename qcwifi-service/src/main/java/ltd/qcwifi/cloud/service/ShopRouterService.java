package ltd.qcwifi.cloud.service;

import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.JscodeParam;
import ltd.qcwifi.model.dto.param.RouterParam;
import ltd.qcwifi.model.entity.cloud.platform.Jscode;
import ltd.qcwifi.model.entity.cloud.platform.ShopRouter;

/**
 * 商铺路由器信息
 * @ClassName: ShopRouterService
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年2月28日 下午9:01:41
 */
public interface ShopRouterService {

	/**
	 * 根据商铺获取路由器信息
	 * @Title: getRouterByShop
	 * @author: zhfang
	 * @Description: TODO
	 * @param shopId
	 * @param createBy
	 * @return
	 * @return: ShopRouter
	 */
	ShopRouter getRouterByShop(Long shopId, Long createBy);	
	
	/**
	 * 根据编码获取设备信息
	 * @Title: getRouterByNo
	 * @author: zhfang
	 * @Description: TODO
	 * @param devNo
	 * @return
	 * @return: ShopRouter
	 */
	ShopRouter getRouterByNo(String devNo);
	
	/**
	 * 设备绑定
	 * @Title: bindingRouter
	 * @author: zhfang
	 * @Description: TODO
	 * @param router
	 * @return
	 * @return: boolean
	 */
	boolean bindingRouter(ShopRouter router);

	/**
	 * 根据MAC地址获取设备信息
	 * @Title: getRouterByMac
	 * @author: zhfang
	 * @Description: TODO
	 * @param mac
	 * @return
	 * @return: ShopRouter
	 */
	String getRouterNoByMac(String mac);

	/**
	 * 解绑
	 * @Title: unBindingRouter
	 * @author: zhfang
	 * @Description: TODO
	 * @param devNo
	 * @param createBy
	 * @param shopId
	 * @return
	 * @return: boolean
	 */
	boolean unBindingRouter(String devNo, Long createBy, Long shopId);
	
	/**
	 * 表格展示
	 * @Title: tables
	 * @author: zhfang
	 * @Description: TODO
	 * @param param
	 * @return
	 * @return: TableResult<ShopRouter>
	 */
	TableResult<ShopRouter> tables(RouterParam param);
}
