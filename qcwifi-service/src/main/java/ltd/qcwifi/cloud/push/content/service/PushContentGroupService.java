package ltd.qcwifi.cloud.push.content.service;

import java.util.List;

import ltd.qcwifi.model.entity.cloud.push.content.PushContentGroup;

/**
 * 广告主服务接口
 * @ClassName: PushContentGroupService
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年1月14日 上午9:56:22
 */
public interface PushContentGroupService {

	/**
	 * 创建广告主
	 * @Title: create
	 * @author: zhfang
	 * @Description: TODO
	 * @param g
	 * @return
	 * @return: boolean
	 */
	boolean create(PushContentGroup g);
	
	/**
	 * 更具用户Id获取广告主列表
	 * @Title: getByUserId
	 * @author: zhfang
	 * @Description: TODO
	 * @param createBy
	 * @return
	 * @return: List<PushContentGroup>
	 */
	List<PushContentGroup> getByUserId(Long createBy);
	
	/**
	 * 根据groupId获取广告主信息
	 * @Title: getByGroupId
	 * @author: zhfang
	 * @Description: TODO
	 * @param groupId
	 * @return
	 * @return: PushContentGroup
	 */
	PushContentGroup getByGroupId(Long groupId);
}
