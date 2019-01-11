package ltd.qcwifi.web.controller.cloud;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ltd.qcwifi.cloud.service.OperateService;
import ltd.qcwifi.cloud.service.ShopInfoService;
import ltd.qcwifi.cloud.service.ShopMarkService;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.cloud.platform.ShopInfo;
import ltd.qcwifi.model.entity.cloud.platform.ShopMark;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.web.Web;
import ltd.qcwifi.web.controller.base.BaseController;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/shop/info")
public class ShopInfoController extends BaseController {
	
	@Autowired
	ShopInfoService shopInfoService;
	
	@Autowired
	ShopMarkService shopMarkService;
	
	@Autowired
	OperateService operateService;
	
	/**
	 * 商铺创建首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{shopId}", method = RequestMethod.GET)
	public String index(@PathVariable("shopId")Long shopId, Model model) {
		model.addAttribute("currentHeader", "list");
		ShopInfo shop = shopInfoService.getShopInfo(shopId, TokenManager.getUserId());
		if (shop == null) {
			return "error/404";
		}
		model.addAttribute("shop", shop);
		model.addAttribute("shopId", shopId);
		return Web.FOLDER_CLOUD_SHOP + "shop.manager.info";
	}
	
	/**
	 * 更新商铺备注
	 * @Title: updateRemark
	 * @author: zhfang
	 * @Description: TODO
	 * @param shopId
	 * @param param
	 * @return
	 * @return: ExecuteResult<String>
	 */
	@RequestMapping(value = {"/{shopId}/remark"}, method = RequestMethod.PUT,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> updateRemark(@PathVariable("shopId")Long shopId, @RequestBody Map<String, String> param) {
		ShopInfo info = new ShopInfo();
		info.setShopId(shopId);
		info.setCreateBy(TokenManager.getUserId());
		info.setRemark(param.get("remark"));
		if (StringUtils.isBlank(info.getRemark())) {
			return new ExecuteResult<>(false, "请输入商铺备注后重试");
		}
		boolean r = shopInfoService.updateShop(info);
		if (r) {
			return new ExecuteResult<String>("成功");
		}
		return new ExecuteResult<>(false, "更新失败，请重试");
	}
	
	/**
	 * 修改商铺
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update/{shopId}", method = RequestMethod.GET)
	public String updateShop(@PathVariable("shopId")Long shopId, Model model) {
		model.addAttribute("currentHeader", "list");
		ShopInfo shop = shopInfoService.getShopInfo(shopId, TokenManager.getUserId());
		if (shop == null) {
			return "error/404";
		}
		model.addAttribute("shop", shop);
		model.addAttribute("shopId", shopId);
		return Web.FOLDER_CLOUD_SHOP + "shop.update";
	}
	
	/**
	 * 更新商铺信息
	 * @Title: newShopSubmit
	 * @author: zhfang
	 * @Description: TODO
	 * @param marks
	 * @param shop
	 * @return
	 * @return: ExecuteResult<String>
	 */
	@RequestMapping(value = "/update/{shopId}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> updateShop(
			@PathVariable("shopId")Long shopId,
			@RequestParam(value = "mark", required = false) String marks,ShopInfo shop) {
		shop.setShopId(shopId);
		shop.setCreateBy(TokenManager.getUserId());
		boolean result = shopInfoService.updateShop(shop);
		if (result) {
			return new ExecuteResult<String>(webAppPath() + "shop/info/" + shopId);
		}
		return new ExecuteResult<String>(false, "商铺更新失败");
	}
	
	/**
	 * 删除商铺信息
	 * @Title: deleteShop
	 * @author: zhfang
	 * @Description: TODO
	 * @param shopId
	 * @return
	 * @return: ExecuteResult<String>
	 */
	@RequestMapping(value = "/delete/{shopId}", method = RequestMethod.DELETE)
	@ResponseBody
	public ExecuteResult<String> deleteShop(@PathVariable("shopId")Long shopId) {
		//查询判断是否当前用户的商铺信息
		ShopInfo info = shopInfoService.getShopInfo(shopId, TokenManager.getUserId());
		if (info == null) {
			return new ExecuteResult<String>(false, "无此商铺信息");
		}
		boolean result = shopInfoService.deleteShop(shopId);
		if (result) {
			return new ExecuteResult<String>(webAppPath() + "shop/index");
		}
		return new ExecuteResult<String>(false, "商铺更新失败");
	}
	
	
	
}
