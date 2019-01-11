package ltd.qcwifi.cloud.push.content.service;

import java.util.List;

import ltd.qcwifi.model.entity.cloud.push.content.PushContentTemp;

/**
 * 广告模版服务接口
 * @ClassName: PushContentTempService
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年1月14日 下午8:29:58
 */
public interface PushContentTempService {

	/**
	 * 获取所有的广告模版
	 * @Title: getTemps
	 * @author: zhfang
	 * @Description: TODO
	 * @return
	 * @return: List<PushContentTemp>
	 */
	List<PushContentTemp> getTemps();
}
