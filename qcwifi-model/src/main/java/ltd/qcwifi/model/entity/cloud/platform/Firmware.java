package ltd.qcwifi.model.entity.cloud.platform;

import java.io.Serializable;
import java.sql.Date;

import ltd.qcwifi.model.BaseEntity;
import net.sf.json.JSONObject;

/**
 * 插件实体类
 * 
 * @author 蔡习文
 *
 */
public class Firmware extends BaseEntity implements Serializable {
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = -6582276990794910079L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 名称
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
	 * 固件存放目录
	 */
	private String resPath;
	/**
	 * 备份目录
	 */
	private String bakPath;
	/**
	 * 固件md5值
	 */
	private String md5;
	/**
	 * 备份文件md5
	 */
	private String bakMd5;
	/**
	 * 固件下载链接
	 */
	private String downloadUrl;
	/**
	 * 备份URL
	 */
	private String bakUrl;
	/**
	 * 状态
	 */
	private Boolean available = Boolean.FALSE;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date create_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
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

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getBakPath() {
		return bakPath;
	}

	public void setBakPath(String bakPath) {
		this.bakPath = bakPath;
	}

	public String getBakMd5() {
		return bakMd5;
	}

	public void setBakMd5(String bakMd5) {
		this.bakMd5 = bakMd5;
	}

	public String getBakUrl() {
		return bakUrl;
	}

	public void setBakUrl(String bakUrl) {
		this.bakUrl = bakUrl;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}
