package ltd.qcwifi.cloud.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import ltd.qcwifi.cloud.service.OrderService;
import ltd.qcwifi.comm.utils.SnowFlakeId;
import ltd.qcwifi.dao.cloud.OrderDao;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.cloud.platform.OrderDetail;
import ltd.qcwifi.model.entity.cloud.platform.OrderMain;
import ltd.qcwifi.model.entity.cloud.platform.SmsPacekage;

/**
 * 订单服务接口
 * @author 张芳
 *
 */
@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderDao orderDao;

	@Override
	@Transactional
	public ExecuteResult<Long> saveSmsOrder(Long userId, SmsPacekage sp) {
		//生产订单号
		SnowFlakeId idWorker = new SnowFlakeId(0, 0);
		long orderNo = idWorker.nextId();
		OrderMain main = new OrderMain(orderNo, sp.getName(), sp.getPrice(), 101, userId);
		
		OrderDetail detail = new OrderDetail(orderNo, sp.getId(), 1);
		List<OrderDetail> details = new ArrayList<>();
		details.add(detail);
		main.setDetails(details);
		System.out.println(JSONObject.toJSONString(main));
		int count = orderDao.saveSmsOrder(main);
		if (count >= 1) {
			return new ExecuteResult<Long>(orderNo);
		}
		return new ExecuteResult<Long>(false, "订单提交失败，请重新提交");
	}
	
	@Override
	public OrderMain selectOrderByNo(Long orderNo, Long userId) {
		return orderDao.selectOrderByNo(orderNo, userId);
	}

}
