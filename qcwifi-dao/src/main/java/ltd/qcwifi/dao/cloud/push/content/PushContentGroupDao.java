package ltd.qcwifi.dao.cloud.push.content;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.model.entity.cloud.push.content.PushContentGroup;

/**
 * 广告主持久化接口
 * @author 张芳
 *
 */
public interface PushContentGroupDao {

	/**
	 * 创建广告主
	 * @return
	 */
	int insert(PushContentGroup g);
	
	/**
	 * 根据用户获取广告主信息
	 * @return
	 */
	List<PushContentGroup> selectByUserId(@Param("createBy") Long createBy);
	
	/**
	 * 根據ID獲取廣告主信息
	 * @Title: selectByGroupId
	 * @author: zhfang
	 * @Description: TODO
	 * @param createBy
	 * @return
	 * @return: PushContentGroup
	 */
	PushContentGroup selectByGroupId(@Param("groupId") Long createBy);
	
	
}
