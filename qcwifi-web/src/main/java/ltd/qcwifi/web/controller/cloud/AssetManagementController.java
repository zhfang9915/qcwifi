package ltd.qcwifi.web.controller.cloud;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import ltd.qcwifi.cloud.service.BalanceService;
import ltd.qcwifi.cloud.service.PaymentsDetailsService;
import ltd.qcwifi.cloud.service.impl.BalanceServiceImpl;
import ltd.qcwifi.cloud.service.impl.PaymentsDetailsServiceImpl;

import ltd.qcwifi.dao.cloud.BalanceDao;
import ltd.qcwifi.dao.cloud.PaymentsDetailsDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.LayTableResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.FirmwareParam;
import ltd.qcwifi.model.dto.param.PaymentsDetailsParam;
import ltd.qcwifi.model.entity.cloud.Balance;
import ltd.qcwifi.model.entity.cloud.PaymentsDetails;
import ltd.qcwifi.model.entity.cloud.platform.DeliveryInfo;
import ltd.qcwifi.model.entity.cloud.platform.Firmware;
import ltd.qcwifi.model.entity.cloud.platform.UserBinding;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.web.Web;


@Controller
@RequestMapping("/personal/center")
public class AssetManagementController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PaymentsDetailsService mPaymentsDetailsService;
	
	@Autowired
	private BalanceService mBalanceService;
	
	/**
	 * 固件 配置视图页面
	 * @return
	 */
	@RequestMapping(value = "/assetManagement", method = RequestMethod.GET)
//	@RequiresPermissions("/assetManagement/query")
	public String toAssetManagementPage(Model model) {
		logger.debug(">>>进入资产管理页....");
		model.addAttribute("currentPage", "assetManagement");
		return Web.PERSONAL_CENTER + "assetManagement";
	}

	/**
	 * 获取金额
	 * @return
	 */
	@RequestMapping(value = "/assetManagement/balance", method = RequestMethod.POST,
			produces = {"application/json; charset=UTF-8"})
	@ResponseBody
//	@RequiresPermissions("/assetManagement/query")
	public ExecuteResult<Balance> queryBalanceInfo() {
		logger.debug(">>>获取金额信息....");
		Long userId = TokenManager.getUserId();
		try {
			return mBalanceService.selectByUserId(userId);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		logger.debug(">>>获取金额信息失败....!");
		return new ExecuteResult<>(false, "查询余额失败!");
	}
	
	/**
	 * 获取收支明细列表
	 * @return
	 */
	@RequestMapping(value = "/assetManagement/paymentsDetails", method = RequestMethod.POST,
			produces = {"application/json; charset=UTF-8"})
	@ResponseBody
//	@RequiresPermissions("/assetManagement/query")
	public LayTableResult<PaymentsDetails> queryPaymentsDetailsInfo(
			@RequestParam("page")Integer page,
			@RequestParam("limit")Integer limit,
			@RequestParam("startTime")String startTime,
			@RequestParam("endTime")String endTime) {
		logger.debug(">>>获取收支明细信息....");
		logger.debug(">>>参数: " + page + " " + limit + " " + startTime + " " + endTime);
		LayTableResult<PaymentsDetails> result = new LayTableResult<PaymentsDetails>();
		Long userId = TokenManager.getUserId();
		try {
			PaymentsDetailsParam paymentsDetailsParam = new PaymentsDetailsParam();
			paymentsDetailsParam.setUserId(userId);
			paymentsDetailsParam.setPage(page);
			paymentsDetailsParam.setOffset((page - 1) * limit);
			paymentsDetailsParam.setLimit(limit);
			paymentsDetailsParam.setStartTime(startTime);
			paymentsDetailsParam.setEndTime(endTime);
			result = mPaymentsDetailsService.selectByUserId(paymentsDetailsParam);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
	}
}
