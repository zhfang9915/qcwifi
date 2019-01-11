package ltd.qcwifi.cloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ltd.qcwifi.cloud.service.CouponService;
import ltd.qcwifi.dao.cloud.CouponDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.Coupon;
import ltd.qcwifi.service.impl.AbstractBaseServiceImpl;

@Service
public class CouponServiceImpl extends AbstractBaseServiceImpl<Coupon, String>  implements CouponService {
	
	@Autowired
	CouponDao couponDao;

	/*
	 * 查询所有的优惠券
	 * @return
	 */
	@Override
	public ExecuteResult<List<Coupon>> queryAllCoupons() {
		List<Coupon> coupons = couponDao.queryAllCoupons();
		for(int i = 0; i < coupons.size(); i++) {
			Coupon a = coupons.get(i);
			System.out.println("xuxuxuxu" + a.toString());
		}
		if (coupons != null && coupons.size() != 0) {
			return new ExecuteResult<List<Coupon>>(coupons);
		}
		return new ExecuteResult<>(false, "您还没有优惠券");
	}

	/*
	 * 序列号兑换优惠券
	 * @param id 优惠券id
	 * @return
	 */
	@Override
	public ExecuteResult<String> exchangeCoupon(String id) {
		int flag = couponDao.exchangeCoupon(id);
		if (flag > 0) {
			return new ExecuteResult<String>("兑换成功");
		}
		return new ExecuteResult<String>(false, "兑换失败，请检查序列号是否正确");
	}
	
	/*
	 * 更新优惠券状态
	 * @param id 优惠券id
	 * @return
	 */
	@Override
	public ExecuteResult<String> updateCoupon(String id) {
		int flag = couponDao.updateCoupon(id);
		if (flag > 0) {
			return new ExecuteResult<String>("更新成功");
		}
		return new ExecuteResult<String>(false, "更新失败，请重试");
	}
}
