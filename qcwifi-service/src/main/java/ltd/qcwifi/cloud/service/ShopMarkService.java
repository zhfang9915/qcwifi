package ltd.qcwifi.cloud.service;

import java.util.List;

import ltd.qcwifi.model.entity.cloud.platform.ShopMark;

/**
 * 商铺信息
 * @author Administrator
 *
 */
public interface ShopMarkService {

	List<ShopMark> getMarksByUserId(Long userId);
}
