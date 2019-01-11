package ltd.qcwifi.cloud.push.content.service;

import java.util.List;

import ltd.qcwifi.model.entity.cloud.push.content.PushContent;

/**
 * 广告内容服务接口
 * @ClassName: PushContentGroupService
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年1月14日 上午9:56:22
 */
public interface PushContentService {

	/**
	 * 创建广告
	 * @Title: create
	 * @author: zhfang
	 * @Description: TODO
	 * @param g
	 * @return
	 * @return: boolean
	 */
	boolean create(PushContent content);
	
	/**
	 * 查询推广内容列表
	 * @Title: getList
	 * @author: zhfang
	 * @Description: TODO
	 * @param createBy
	 * @param contentName
	 * @return
	 * @return: List<PushContent>
	 */
	List<PushContent> getList(Long createBy, String contentName);
	
	/**
	 * 根据id获取
	 * @Title: get
	 * @author: zhfang
	 * @Description: TODO
	 * @param createBy
	 * @param id
	 * @return
	 * @return: PushContent
	 */
	PushContent getById(Long createBy, String id);
	
	/**
	 * 删除广告
	 * @Title: delete
	 * @author: zhfang
	 * @Description: TODO
	 * @param createBy
	 * @param id
	 * @return
	 * @return: boolean
	 */
	boolean delete(Long createBy, String id);
	
	/**
	 * 更新广告信息
	 * @Title: update
	 * @author: zhfang
	 * @Description: TODO
	 * @param content
	 * @return
	 * @return: boolean
	 */
	boolean update(PushContent content);
	
	/**
	 * 更新上下架
	 * @Title: updateStatus
	 * @author: zhfang
	 * @Description: TODO
	 * @param content
	 * @return
	 * @return: boolean
	 */
	boolean updateStatus(PushContent content);
}
