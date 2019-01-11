package ltd.qcwifi.cloud.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ltd.qcwifi.cloud.service.ShopRouterService;
import ltd.qcwifi.dao.cloud.ShopRouterDao;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.JscodeParam;
import ltd.qcwifi.model.dto.param.RouterParam;
import ltd.qcwifi.model.entity.cloud.platform.Jscode;
import ltd.qcwifi.model.entity.cloud.platform.ShopRouter;
import ltd.qcwifi.model.entity.cloud.push.content.PushContent;

@Service
public class ShopRouterServiceImpl implements ShopRouterService {

	@Autowired
	ShopRouterDao routerDao;
	
	@Override
	public ShopRouter getRouterByShop(Long shopId, Long createBy) {
		return routerDao.selectByshop(shopId, createBy);
	}
	
	@Override
	public ShopRouter getRouterByNo(String devNo) {
		return routerDao.selectBydevNo(devNo);
	}
	
	@Override
	public String getRouterNoByMac(String mac) {
		return routerDao.selectNoByMac(mac);
	}

	@Override
	public boolean bindingRouter(ShopRouter router) {
		int result = routerDao.binding(router);
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean unBindingRouter(String devNo, Long createBy, Long shopId) {
		int result = routerDao.unbindingShop(devNo, createBy, shopId);
		if (result >= 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public TableResult<ShopRouter> tables(RouterParam param) {
		TableResult<ShopRouter> result = new TableResult<>();
		// 设置分页
		PageHelper.startPage(param.getPageNumber(), param.getPageSize());
		// 查询数据
		List<ShopRouter> routers = routerDao.selectByPage(param);
		// 获取分页信息
		PageInfo<ShopRouter> pageInfo = new PageInfo<ShopRouter>(routers);
		result.setRows(routers);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

}
