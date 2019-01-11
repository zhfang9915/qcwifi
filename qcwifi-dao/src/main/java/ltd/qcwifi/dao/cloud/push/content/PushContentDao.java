package ltd.qcwifi.dao.cloud.push.content;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.model.entity.cloud.push.content.PushContent;

/**
 * @ClassName: PushContentDao
 * @Description: 广告内容 Dao
 * @author: zhfang
 * @date: 2018年1月21日 下午8:29:56
 */
public interface PushContentDao {

	/**
	 * @Title: insertContent
	 * @author: zhfang
	 * @Description: 插入广告内容基本信息
	 * @param content
	 * @return
	 * @return: int
	 */
	int insert(PushContent content);
	
	/**
	 * @Title: selects
	 * @author: zhfang
	 * @Description: 查询推广内容列表
	 * @param createBy
	 * @param contentName
	 * @return
	 * @return: List<PushContent>
	 */
	List<PushContent> selects(@Param("createBy")Long createBy, @Param("name")String contentName);
	
	/**
	 * 根据id查询推广内容
	 * @Title: selectDetail
	 * @author: zhfang
	 * @Description: TODO
	 * @param createBy
	 * @param id
	 * @return
	 * @return: PushContent
	 */
	PushContent selectDetail(@Param("createBy")Long createBy, @Param("id")String id);
	
	/**
	 * 删除广告
	 * @Title: delete
	 * @author: zhfang
	 * @Description: TODO
	 * @param createBy
	 * @param id
	 * @return
	 * @return: int
	 */
	int delete(@Param("createBy")Long createBy, @Param("id")String id);
	
	/**
	 * 更新广告信息
	 * @Title: update
	 * @author: zhfang
	 * @Description: TODO
	 * @param content
	 * @return
	 * @return: int
	 */
	int update(PushContent content);
	
	/**
	 * 更新上下架
	 * @Title: updateStatus
	 * @author: zhfang
	 * @Description: TODO
	 * @param content
	 * @return
	 * @return: int
	 */
	int updateStatus(PushContent content);
	
}
