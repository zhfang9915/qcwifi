package ltd.qcwifi.web.controller.cloud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.sql.parser.Token;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ltd.qcwifi.cloud.service.OperateService;
import ltd.qcwifi.cloud.service.ShopInfoService;
import ltd.qcwifi.cloud.service.ShopMarkService;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.ShopInfoParam;
import ltd.qcwifi.model.entity.cloud.platform.Operate;
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
@RequestMapping("/shop")
public class ShopController extends BaseController {
	
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
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	@RequiresPermissions("/shop/new")
	public String newShop(Model model) {
		model.addAttribute("currentHeader", "list");
		List<ShopMark> marks = shopMarkService.getMarksByUserId(TokenManager.getUserId());
		model.addAttribute("marks", marks);
		return Web.FOLDER_CLOUD_SHOP + "shop.new";
	}

	/**
	 * 商铺创建查询经营分类
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/operate", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/shop/operate")
	public ExecuteResult<List<Operate>> selectOpreate() {
		Integer level = Integer.parseInt(getRequest().getParameter("level"));
		Integer parent = Integer.parseInt(getRequest().getParameter("parent"));
		List<Operate> operates = operateService.selectOperateByLevel(level, parent);
		if (operates == null || operates.isEmpty()) {
			return new ExecuteResult<>(false, "无可用的经营分类信息");
		}
		return new ExecuteResult<List<Operate>>(operates);
	}
	
	/**
	 * 商铺创建提交
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/shop/new")
	public ExecuteResult<String> newShopSubmit(
			@RequestParam(value = "mark", required = false) String marks,ShopInfo shop) {
		//执行创建商铺  获取返回的商铺ID
		shop.setCreateBy(TokenManager.getUserId());
		shop = shopInfoService.createShop(shop);
		if (shop == null) {
			return new ExecuteResult<String>(false, "商铺创建失败");
		}
		return new ExecuteResult<String>(webAppPath() + "shop/index");
	}
	
	/**
	 * 商铺首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("/shop/index")
	public String index(Model model) {
		model.addAttribute("currentHeader", "list");
		
		return Web.FOLDER_CLOUD_SHOP + "shop.list";
	}
	
	/**
	 * 分页展示商铺信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/shop/list")
	public TableResult<ShopInfo> pageShopInfo(ShopInfoParam param) {
		param.setCreateBy(TokenManager.getUserId());
		// 设置分页
		PageHelper.startPage(param.getPageNum(),param.getPageSize());
		// 查询数据
		List<ShopInfo> shops = shopInfoService.getShopInfos(param);
		// 获取分页信息
		PageInfo<ShopInfo> pageInfo = new PageInfo<ShopInfo>(shops);
		return new TableResult<ShopInfo>(pageInfo.getPageNum(), pageInfo.getPages(), pageInfo.getTotal(), shops);
	}
	
	/**
	 * 商铺搜索条件
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/list/condition", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/shop/list/condition")
	public ExecuteResult<Map<String, Object>> searchCondition() {
		Map<String, Object> map = new HashMap<>();
		List<ShopMark> marks = shopMarkService.getMarksByUserId(TokenManager.getUserId());
		map.put("marks", marks);
		
		List<Operate> operates = operateService.selectOperateByLevel(1, 0);
		map.put("operates", operates);
		
		return new ExecuteResult<Map<String,Object>>(map);
	}
	
	/**
	 * 商铺地图展示
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/map", method = RequestMethod.GET)
	@RequiresPermissions("/shop/map")
	public String map(Model model) {
		model.addAttribute("currentHeader", "map");
		return Web.FOLDER_CLOUD_SHOP + "shop.map";
	}
	
	/**
	 * 商铺地图单独展示
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/map/alone", method = RequestMethod.GET)
	@RequiresPermissions("/shop/map")
	public String mapAlone(Model model) {
		return Web.FOLDER_CLOUD_SHOP + "shop.map.alone";
	}
	
	/**
	 * 地图上展示商铺信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/map", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/shop/map")
	public ExecuteResult<List<ShopInfo>> mapShopInfo(ShopInfoParam param) {
		param.setCreateBy(TokenManager.getUserId());
		// 查询数据
		List<ShopInfo> shops = shopInfoService.getShopInfos(param);
		if (shops == null || shops.isEmpty()) {
			return new ExecuteResult<List<ShopInfo>>(false, "暂无商铺");
		}
		return new ExecuteResult<List<ShopInfo>>(shops);
	}
	
	
}
