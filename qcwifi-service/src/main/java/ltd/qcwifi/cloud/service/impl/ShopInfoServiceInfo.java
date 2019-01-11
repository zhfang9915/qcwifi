package ltd.qcwifi.cloud.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ltd.qcwifi.cloud.service.ShopInfoService;
import ltd.qcwifi.comm.utils.RandCodeUtil;
import ltd.qcwifi.dao.cloud.ShopInfoDao;
import ltd.qcwifi.dao.cloud.ShopMarkDao;
import ltd.qcwifi.dao.cloud.UserBindingDao;
import ltd.qcwifi.dao.system.RoleDao;
import ltd.qcwifi.dao.system.UserDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.param.ShopInfoParam;
import ltd.qcwifi.model.entity.Role;
import ltd.qcwifi.model.entity.User;
import ltd.qcwifi.model.entity.cloud.platform.ShopInfo;
import ltd.qcwifi.model.entity.cloud.platform.SubAccount;

@Service
public class ShopInfoServiceInfo implements ShopInfoService {

	@Autowired
	ShopInfoDao shopInfoDao;
	
	@Autowired
	ShopMarkDao shopMarkDao;
	
	@Autowired
    UserDao userDao;
    
    @Autowired
    RoleDao roleDao;
    
    @Autowired
    UserBindingDao userBindingDao;
	
	@Override
	public List<ShopInfo> getShopInfos(ShopInfoParam param) {
		// 查询数据
		List<ShopInfo> shops = shopInfoDao.selectShopInfo(param);
		return shops;
	}
	
	@Override
	public ShopInfo getShopInfo(Long id, Long createBy) {
		ShopInfo info = shopInfoDao.selectShopInfoById(id, createBy);
		return info;
	}
	
	@Override
	public ShopInfo createShop(ShopInfo shop) {
		//创建商铺信息
		int count = shopInfoDao.createShop(shop);
		if (count == 1) {//创建成功
			//如果存在的话，执行插入新的标签 
			if (StringUtils.isNotBlank(shop.getMark())) {
				//关联商铺和标签信息
				List<String> marks = Arrays.asList(shop.getMark().split(","));
				shopMarkDao.createShopMarkRelation(shop.getShopId(), marks);
			}
			return shop;
		}
		return null;
	}
	
	@Override
	public boolean updateShop(ShopInfo info) {
		int count = 0;
		if (StringUtils.isNotBlank(info.getShopName())) {
			count = shopInfoDao.updateShop(info);
		}else {
			count = shopInfoDao.updateRemark(info);
		}
		if (count == 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean deleteShop(Long shopId) {
		int count = shopInfoDao.deleteShop(shopId);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	@Transactional
	@Override
	public boolean createShopSubAccount(SubAccount sa) {
		User user = new User();
		user.setNickname(sa.getNickname());
		user.setPassword(sa.getPassword());
		user.setSalt(sa.getSalt());
		user.setUsername(sa.getUsername());
		user.setType(1);//二级用户
		
		userDao.insert(user);
		List<Role> roles = new ArrayList<>();
		Role role = new Role();
		role.setId(5L);
		roles.add(role);
		roleDao.correlationRoles(user.getId(), roles);
		
		//创建连锁账户
		sa.setUserId(user.getId());
		int count = shopInfoDao.createSubAccount(sa);
		
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<SubAccount> getSubAccounts(Long createBy) {
		// 查询数据
		List<SubAccount> sas = shopInfoDao.selectSubAccount(createBy);
		return sas;
	}
	
	@Override
	@Transactional
	public boolean updateSubAccount(String filed, Object value, Long id, Long userId, Long parent) {
		int count = shopInfoDao.updateSubAccount(filed, value, id, userId, parent);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public SubAccount getSubAccountById(Long id, Long createBy) {
		return shopInfoDao.selectSubAccountById(id, createBy);
	}
	
	@Override
	@Transactional
	public boolean delSubAccount(Long id, Long userId, Long createBy) {
		int count = shopInfoDao.deleteSubAccount(id, userId, createBy);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<ShopInfo> getSubFreeShop(Long id, Long createBy){
		return shopInfoDao.selectFreeShop(id, createBy);
	}
	
	@Override
	public List<ShopInfo> getSubCheckedShop(Long id, Long createBy){
		return shopInfoDao.selectCheckedShop(id, createBy);
	}
	
	@Override
	public boolean addShopSub(Long subId, Long createBy, @Param("shops") List<ShopInfo> shops) {
		//得到当前用户下地商铺，防止恶意修改商铺信息，从而连锁他人商铺信息
		List<Long> ss = shopInfoDao.confimShopSub(createBy, shops);
		int count = shopInfoDao.insertShopSub(subId, createBy, ss);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean removeShopSub(Long subId, Long createBy, @Param("shops") List<ShopInfo> shops) {
		int count = shopInfoDao.deleteShopSub(subId, createBy, shops);
		if (count > 0) {
			return true;
		}
		return false;
	}
}
