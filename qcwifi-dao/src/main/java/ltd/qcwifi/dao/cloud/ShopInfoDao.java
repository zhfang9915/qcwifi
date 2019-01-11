package ltd.qcwifi.dao.cloud;

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
public interface ShopInfoDao {

	/**
	 * 分页查询商铺信息
	 * @return
	 */
	List<ShopInfo> selectShopInfo(ShopInfoParam param);
	
	/**
	 * 根据ID获取商铺信息
	 * @Title: selectShopInfoById
	 * @author: zhfang
	 * @Description: TODO
	 * @param id
	 * @param createBy
	 * @return
	 * @return: ShopInfo
	 */
	ShopInfo selectShopInfoById(@Param("shopId") Long id, @Param("createBy") Long createBy);
	
	/**
	 * 创建商铺
	 * @param shop
	 * @return
	 */
	int createShop(ShopInfo shop);
	
	/**
	 * 更新商铺
	 * @Title: updateShop
	 * @author: zhfang
	 * @Description: TODO
	 * @param info
	 * @return
	 * @return: int
	 */
	int updateShop(ShopInfo info);
	int updateRemark(ShopInfo info);
	
	/**
	 * 删除商铺
	 * @Title: deleteShop
	 * @author: zhfang
	 * @Description: TODO
	 * @param shopId
	 * @param createBy
	 * @return
	 * @return: int
	 */
	int deleteShop(@Param("shopId")Long shopId);
	
	/**
	 * 创建连锁账户
	 * @param sa
	 * @return
	 */
	int createSubAccount(SubAccount sa);
	
	/**
	 * 查询当前账号下的连锁账号
	 * @param createBy
	 * @return
	 */
	List<SubAccount> selectSubAccount(@Param("createBy")Long createBy);
	
	/**
	 * 修改连锁账户信息
	 * @param filed
	 * @param value
	 * @param id
	 * @param parent
	 * @return
	 */
	int updateSubAccount(
			@Param("filed") String filed, 
			@Param("val") Object value, 
			@Param("id") Long id,
			@Param("userId") Long userId,
			@Param("parent") Long parent);
	
	/**
	 * 根据ID查询连锁账户信息
	 * @param id
	 * @param createBy
	 * @return
	 */
	SubAccount selectSubAccountById(@Param("id") Long id, @Param("createBy") Long createBy);
	
	/**
	 * 删除连锁账号
	 * @param id
	 * @param userId
	 * @param createBy
	 * @return
	 */
	int deleteSubAccount(
			@Param("id") Long id,
			@Param("userId") Long userId,
			@Param("createBy") Long createBy);
	
	/**
	 * 连锁账户可选的商铺信息
	 * @param id
	 * @param createBy
	 * @return
	 */
	List<ShopInfo> selectFreeShop(@Param("id") Long id, @Param("createBy") Long createBy);
	
	/**
	 * 连锁账户已选的商铺信息
	 * @param id
	 * @param createBy
	 * @return
	 */
	List<ShopInfo> selectCheckedShop(@Param("id") Long id, @Param("createBy") Long createBy);
	
	/**
	 * 添加连锁商铺
	 * @param subId
	 * @param userId
	 * @param shops
	 * @return
	 */
	int insertShopSub(@Param("subId") Long subId, @Param("createBy") Long createBy, @Param("shops") List<Long> shops);
	
	/**
	 * 删除连锁商铺
	 * @param subId
	 * @param createBy
	 * @param shops
	 * @return
	 */
	int deleteShopSub(@Param("subId") Long subId, @Param("createBy") Long createBy, @Param("shops") List<ShopInfo> shops);
	
	/**
	 * 添加商铺时验证所添加地商铺是否是当前账号地商铺
	 * @param createBy
	 * @param shops
	 * @return
	 */
	List<Long> confimShopSub(@Param("createBy") Long createBy, @Param("shops") List<ShopInfo> shops);
}
