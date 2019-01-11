package ltd.qcwifi.cloud.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.model.dto.param.ShopInfoParam;
import ltd.qcwifi.model.entity.cloud.platform.ShopInfo;
import ltd.qcwifi.model.entity.cloud.platform.SubAccount;

/**
 * 商铺信息
 * @author Administrator
 *
 */
public interface ShopInfoService {

	/**
	 * 查询商铺信息
	 * 
	 * @param param
	 * @return
	 */
	List<ShopInfo> getShopInfos(ShopInfoParam param);
	
	/**
	 * 根据ID获取商铺信息
	 * @Title: getShopInfo
	 * @author: zhfang
	 * @Description: TODO
	 * @param id
	 * @param createBy
	 * @return
	 * @return: ShopInfo
	 */
	ShopInfo getShopInfo(Long id, Long createBy);
	
	/**
	 * 创建商铺
	 * @param shop
	 * @return
	 */
	ShopInfo createShop(ShopInfo shop);
	
	/**
	 * 更新商铺
	 * @Title: updateShop
	 * @author: zhfang
	 * @Description: TODO
	 * @param info
	 * @return
	 * @return: boolean
	 */
	boolean updateShop(ShopInfo info);
	
	/**
	 * 创建连锁账户
	 * @return
	 */
	boolean createShopSubAccount(SubAccount sa);
	
	/**
	 * 查询当前账号下的连锁账号
	 * @param createBy
	 * @return
	 */
	List<SubAccount> getSubAccounts(Long createBy);
	
	/**
	 * 修改连锁账户信息
	 * @param filed
	 * @param value
	 * @param id
	 * @param parent
	 * @return
	 */
	boolean updateSubAccount(String filed, Object value, Long id, Long userId, Long parent);
	
	/**
	 * 根据ID查询连锁账号
	 * @param id
	 * @param createBy
	 * @return
	 */
	SubAccount getSubAccountById(Long id, Long createBy);
	
	/**
	 * 删除连锁账号
	 * @param id
	 * @param userId
	 * @param createBy
	 * @return
	 */
	boolean delSubAccount(Long id, Long userId, Long createBy);
	
	/**
	 * 连锁账户可选的商铺信息
	 * @param id
	 * @param createBy
	 * @return
	 */
	List<ShopInfo> getSubFreeShop(Long id, Long createBy);
	
	/**
	 * 连锁账户已选的商铺信息
	 * @param id
	 * @param createBy
	 * @return
	 */
	List<ShopInfo> getSubCheckedShop(Long id, Long createBy);
	
	/**
	 * 添加连锁商铺
	 * @param subId
	 * @param userId
	 * @param createBy
	 * @param shops
	 * @return
	 */
	boolean addShopSub(Long subId, Long createBy, List<ShopInfo> shops);
	
	/**
	 * 删除连锁商铺
	 * @param subId
	 * @param createBy
	 * @param shops
	 * @return
	 */
	boolean removeShopSub(Long subId, Long createBy, List<ShopInfo> shops);

	/**
	 * 删除商铺
	 * @Title: deleteShop
	 * @author: zhfang
	 * @Description: TODO
	 * @param shopId
	 * @param createBy
	 * @return
	 * @return: boolean
	 */
	boolean deleteShop(Long shopId);
}
