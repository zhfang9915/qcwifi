package ltd.qcwifi.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

/**
 * 环境变量
 * 
 * @author 张芳
 *
 */
@Component
public class Qc {

	@Value("${qcwifi.root_path}")
	private String rootPath;

	@Value("${qcwifi.nginx}")
	private String nginx;

	@Value("${common.taobao}")
	private String taobao;
	/**
	 * FTP 插件文件存放位置
	 */
	@Value("${qcwifi.ftp.path.plugin}")
	private String ftpPluginPath;
	/**
	 * FTP 固件文件存放位置
	 */
	@Value("${qcwifi.ftp.path.firmware}")
	private String ftpFirmwarePath;
	/**
	 * FTP 广告资源文件存放位置
	 */
	@Value("${qcwifi.ftp.path.advert}")
	private String ftpAdvertPath;

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getNginx() {
		return nginx;
	}

	public void setNginx(String nginx) {
		this.nginx = nginx;
	}

	public String getTaobao() {
		return taobao;
	}

	public void setTaobao(String taobao) {
		this.taobao = taobao;
	}

	public String getFtpPluginPath() {
		return ftpPluginPath;
	}

	public void setFtpPluginPath(String ftpPluginPath) {
		this.ftpPluginPath = ftpPluginPath;
	}

	public String getFtpFirmwarePath() {
		return ftpFirmwarePath;
	}

	public void setFtpFirmwarePath(String ftpFirmwarePath) {
		this.ftpFirmwarePath = ftpFirmwarePath;
	}

	public String getFtpAdvertPath() {
		return ftpAdvertPath;
	}

	public void setFtpAdvertPath(String ftpAdvertPath) {
		this.ftpAdvertPath = ftpAdvertPath;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}

}
