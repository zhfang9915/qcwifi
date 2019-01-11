package ltd.qcwifi.web.controller.cloud;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ltd.qcwifi.cloud.service.impl.CouponServiceImpl;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.Coupon;
import ltd.qcwifi.web.Web;


/**
 * 优惠卡券
 * @author 许延明
 *
 */
@Controller
public class CouponController extends AbstractPersonalCenterController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CouponServiceImpl couponService;
	
	/**
	 * 优惠卡券视图
	 * @return
	 */
	@RequestMapping(value = "/coupon/index", method = RequestMethod.GET)
	public String coupon(Model model) {
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		model.addAttribute("currentPage", "coupon");
		return Web.PERSONAL_CENTER + "coupon";
	}
	
	
	/*
	 * 查询所有的优惠券
	 * @return
	 */
	@RequestMapping(value = "/coupon/list", method = RequestMethod.GET,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<List<Coupon>> queryAllCoupons() {
		return couponService.queryAllCoupons();
	}
	
	/*
	 * 序列号兑换优惠券
	 * @param id 优惠券id
	 * @return
	 */
	@RequestMapping(value = "/coupon/exchange", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> exchangeCoupon(@PathVariable("id")String id) {
		return couponService.exchangeCoupon(id);
	}
	
	/*
	 * 更新优惠券状态
	 * @param id 优惠券id
	 * @return
	 */
	@RequestMapping(value = "/coupon/update", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> updateCoupon(@PathVariable("id")String id) {
		return couponService.updateCoupon(id);
	}
}
