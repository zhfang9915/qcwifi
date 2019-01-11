package ltd.qcwifi.cloud.service;

import java.util.List;

import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.Coupon;

public interface CouponService {

	/**
	 * 查询所有的优惠券
	 * @return
	 */
	ExecuteResult<List<Coupon>> queryAllCoupons();
	
	/*
	 * 序列号兑换优惠券
	 * @param id 优惠券id
	 * @return
	 */
	public ExecuteResult<String> exchangeCoupon(String id);
	
	/*
	 * 更新优惠券状态
	 * @param id 优惠券id
	 * @return
	 */
	public ExecuteResult<String> updateCoupon(String id);
}
