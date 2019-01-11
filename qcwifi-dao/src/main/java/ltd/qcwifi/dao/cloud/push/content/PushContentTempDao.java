package ltd.qcwifi.dao.cloud.push.content;

import java.util.List;

import ltd.qcwifi.model.entity.cloud.push.content.PushContentTemp;

/**
 * 广告模版持久化类
 * @ClassName: PushContentTempDao
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年1月14日 下午8:20:44
 */
public interface PushContentTempDao {

	/**
	 * 查询列表
	 * @Title: select
	 * @author: zhfang
	 * @Description: TODO
	 * @return
	 * @return: List<PushContentTemp>
	 */
	List<PushContentTemp> select();
}
