package ltd.qcwifi.model.entity.cloud.push.content;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 推广内容投放选择
 * @ClassName: PushContentChoose
 * @Description: TODO
 * @author: zhfang
 * @date: 2018年1月14日 下午1:16:28
 */
public class PushContentChoose implements Serializable {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1560405064777355330L;

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 广告类型（模版ID）
	 */
	private String templateId;
	/**
	 * 广告模版名称
	 */
	private String templateName;
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

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
