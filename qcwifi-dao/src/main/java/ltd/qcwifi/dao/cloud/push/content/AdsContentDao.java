package ltd.qcwifi.dao.cloud.push.content;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ltd.qcwifi.model.entity.cloud.push.content.AdsContent;
import ltd.qcwifi.model.entity.cloud.push.content.AdsSeq;

/**
 * 推送广告内容 Dao
 * @ClassName: AdsContentDao
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年3月10日 下午9:18:49
 */
public interface AdsContentDao {

	/**
	 * 
	 * @Title: queryAdsByCreateBy
	 * @author: zhfang
	 * @Description: TODO
	 * @param createBy
	 * @return
	 * @return: List<AdsContent>
	 */
	List<AdsContent> queryAdsByCreateBy(@Param("createBy")Long createBy);
	
	/**
	 * 查询前辰名下所有可被推送的广告资源
	 * @Title: queryAdsForQcwifi
	 * @author: zhfang
	 * @Description: TODO
	 * @return
	 * @return: List<AdsContent>
	 */
	List<AdsContent> queryAdsForQcwifi();
	
	/**
	 * 保存广告流水信息
	 * @Title: insertAdsSeq
	 * @author: zhfang
	 * @Description: TODO
	 * @param seq
	 * @return
	 * @return: int
	 */
	int insertAdsSeq(AdsSeq seq);
	
	int updateAdsSeq(AdsSeq seq);
	
}
