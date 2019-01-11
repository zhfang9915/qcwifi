package ltd.qcwifi.dao.wechat;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.base.dao.BaseDao;
import ltd.qcwifi.model.entity.wechat.ShopWechat;
import ltd.qcwifi.model.entity.wechat.WechatAccount;

/**
 * 微信
 * @author Xu
 *
 */
public interface WechatDao extends BaseDao<WechatAccount, String> {

	int updateWechatShopCount(WechatAccount account);
	int updateWechatIsOpenWifiPlugin(@Param("appId")String appId, @Param("isOpen")boolean isOpen);
	List<WechatAccount> selectByUserId(@Param("userId")Long userId);
	ShopWechat getWechatShopByShopId(@Param("shopId")Long shopId);
	
}
