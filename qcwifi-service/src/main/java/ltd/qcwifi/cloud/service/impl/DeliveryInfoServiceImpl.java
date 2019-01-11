package ltd.qcwifi.cloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ltd.qcwifi.cloud.service.DeliveryInfoService;
import ltd.qcwifi.dao.cloud.DeliveryInfoDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.cloud.platform.DeliveryInfo;
import ltd.qcwifi.service.impl.AbstractBaseServiceImpl;

/**
 * 配送信息服务实现类
 * 
 * @author 张芳
 *
 */
@Service
public class DeliveryInfoServiceImpl
	extends AbstractBaseServiceImpl<DeliveryInfo, String> 
	implements DeliveryInfoService {

	@Autowired
	DeliveryInfoDao deliveryDao;
	
	
	@Override
	public ExecuteResult<String> create(DeliveryInfo delivery) {
		int count = deliveryDao.insert(delivery);
        if (count > 0) {
			return new ExecuteResult<String>(String.valueOf(delivery.getId()));
		}
        return new ExecuteResult<>(false, "创建配送信息失败");
	}
	
	@Override
	public ExecuteResult<String> deleteDelivery(Long id, Long userId) {
    	int count = deliveryDao.deleteDelivery(id, userId);
    	if (count > 0) {
			return new ExecuteResult<String>("删除配送信息成功");
		}
        return new ExecuteResult<String>(false, "删除配送信息失败");
	}
	
	
	@Override
	public ExecuteResult<List<DeliveryInfo>> selectByUserId(Long userId) {
		List<DeliveryInfo> deliveryInfos = deliveryDao.selectByUserId(userId);
		if (deliveryInfos != null && deliveryInfos.size() != 0) {
			return new ExecuteResult<List<DeliveryInfo>>(deliveryInfos);
		}
		return new ExecuteResult<>(false, "您还没有创建配送地址");
	}
	
	@Override
	public ExecuteResult<String> update(DeliveryInfo info) {
		int count = deliveryDao.updateDelivery(info);
		if (count > 0) {
			return new ExecuteResult<String>("修改配送信息成功");
		}
        return new ExecuteResult<String>(false, "修改配送信息失败");
	}
	
	@Override
	public ExecuteResult<String> setDefault(Long id, Long userId) {
		int count = deliveryDao.setDefault(id, userId, true);
    	if (count > 0) {
			return new ExecuteResult<String>("设置默认成功");
		}
        return new ExecuteResult<String>(false, "设置默认失败");
	}
	
	@Override
	public int hasCount(Long userId) {
		return deliveryDao.hasCount(userId);
	}
}
