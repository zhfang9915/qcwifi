package ltd.qcwifi.model.entity.cloud.platform;

import java.io.Serializable;

import ltd.qcwifi.model.BaseEntity;
import net.sf.json.JSONObject;

/**
 * 插件实体类
 * 
 * @author 张芳
 *
 */
public class Plugin extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9180620184094433776L;

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 插件名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 版本
	 */
	private String version;
	/**
	 * 交叉编译版本
	 */
	private String crosstool;
	/**
	 * 插件存放目录
	 */
	private String resPath;
	/**
	 * 下载链接
	 */
	private String downloadUrl;
	/**
	 * 文件MD5
	 */
	private String md5;
	/**
	 * 状态
	 */
	private Boolean available = Boolean.FALSE;
	/**
	 * 备注
	 */
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCrosstool() {
		return crosstool;
	}

	public void setCrosstool(String crosstool) {
		this.crosstool = crosstool;
	}

	public String getResPath() {
		return resPath;
	}

	public void setResPath(String resPath) {
		this.resPath = resPath;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}

}
