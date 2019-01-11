package ltd.qcwifi.model.entity.cloud.push.content;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 推广内容实体类
 * 
 * @author Administrator
 *
 */
public class AdsContent implements Serializable {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1809068838802407662L;

	/**
	 * 广告ID
	 */
	private String id;
	/**
	 * 权重 默认1
	 */
	private int weight = 1;
	/**
	 * 广告总数 N单个 Y多个
	 */
	private String isMore;
	/**
	 * 模版
	 */
	private String temp;
	/**
	 * 循环体
	 */
	private String tempFor;
	/**
	 * 模版所需JS
	 */
	private String tempJs;
	/**
	 * 推广内容的URL地址 多个以,隔开
	 */
	private String imgs;
	/**
	 * 推广内容URL跳转地址 多个以,隔开
	 */
	private String urls;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getIsMore() {
		return isMore;
	}

	public void setIsMore(String isMore) {
		this.isMore = isMore;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getTempFor() {
		return tempFor;
	}

	public void setTempFor(String tempFor) {
		this.tempFor = tempFor;
	}

	public String getTempJs() {
		return tempJs;
	}

	public void setTempJs(String tempJs) {
		this.tempJs = tempJs;
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
