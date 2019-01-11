package ltd.qcwifi.web.controller.cloud;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ltd.qcwifi.cloud.service.ShopRouterService;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.cloud.platform.ShopRouter;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.web.Web;
import ltd.qcwifi.web.controller.base.BaseController;

/**
 * 商铺路由器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/shop/router")
public class ShopRouterController extends BaseController {
	
	@Autowired
	ShopRouterService routerService;
	
	@Autowired
	RedisTemplate<String, Serializable> redisTemplate;
	
	/**
	 * 商铺绑定首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{shopId}", method = RequestMethod.GET)
	public String index(@PathVariable("shopId")Long shopId, Model model) {
		model.addAttribute("currentHeader", "list");
		ShopRouter router = routerService.getRouterByShop(shopId, TokenManager.getUserId());
		if (router == null) {
			model.addAttribute("binding_status", false);
			return Web.FOLDER_CLOUD_SHOP + "shop.router";
		}
		model.addAttribute("binding_status", true);
		model.addAttribute("router", router);
		model.addAttribute("shopId", shopId);
		return Web.FOLDER_CLOUD_SHOP + "shop.router";
	}
	
	/**
	 * 商铺绑定
	 * @Title: binding
	 * @author: zhfang
	 * @Description: TODO
	 * @param shopId
	 * @param param
	 * @return
	 * @return: ExecuteResult<String>
	 */
	@RequestMapping(value = "/{shopId}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> binding(@PathVariable("shopId")Long shopId, @RequestBody Map<String, String> param) {
		String code = param.get("code");//设备序列号
		//从redis中获取路由器信息
		ShopRouter r = (ShopRouter) redisTemplate.opsForValue().get(code);
		if (r == null) {
			return new ExecuteResult<>(false, "无效的序列码");
		}
		r.setCreateBy(TokenManager.getUserId());
		r.setShopId(shopId);
		try {
			boolean result = routerService.bindingRouter(r);
			if (result) {
				return new ExecuteResult<>("绑定成功");
			}
		} catch (Exception e) {
			return new ExecuteResult<>(false, "系统异常，请稍后重试");
		}
		return new ExecuteResult<>(false, "绑定失败，请重试");
	}
	
	/**
	 * 解绑
	 * @Title: unbinding
	 * @author: zhfang
	 * @Description: TODO
	 * @param shopId
	 * @param param
	 * @return
	 * @return: ExecuteResult<String>
	 */
	@RequestMapping(value = "/{shopId}", method = RequestMethod.PUT,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> unbinding(@PathVariable("shopId")Long shopId, @RequestBody Map<String, String> param) {
		String devNo = param.get("devNo");//设备序列号
		try {
			boolean result = routerService.unBindingRouter(devNo, TokenManager.getUserId(), shopId);
			if (result) {
				return new ExecuteResult<>("解绑成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ExecuteResult<>(false, "系统异常，请稍后重试");
		}
		return new ExecuteResult<>(false, "解绑失败，请重试");
	}
	
	
	
	
	
}
