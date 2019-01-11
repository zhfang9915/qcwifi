package ltd.qcwifi.dao.cloud;

import java.util.List;

import ltd.qcwifi.base.dao.BaseDao;
import ltd.qcwifi.model.entity.Coupon;

public interface CouponDao extends BaseDao<Coupon, String> {
	
	/*
	 * 查询所有的优惠券
	 * @return
	 */
	List<Coupon> queryAllCoupons();
	
	/*
	 * 序列号兑换优惠券
	 * @param id 优惠券id
	 * @return
	 */
	int exchangeCoupon(String id);

	/*
	 * 更新优惠券状态
	 * @param id 优惠券id
	 * @return
	 */
	int updateCoupon(String id);
}
