package ltd.qcwifi.backend.file.upload;

import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import ltd.qcwifi.model.entity.cloud.platform.Firmware;

public interface FileUploadAndFtpService {

	/**
	 * 文件上传
	 * @param request
	 * @param folderPath
	 * @param fileName
	 * @return
	 */
	boolean uploadByName(HttpServletRequest request, String folderPath, String fileName);
	
	/**
	 * 文件上传
	 * @param request
	 * @param folderPath
	 * @return
	 */
	boolean upload(MultipartHttpServletRequest request, String folderPath);
	
	/**
	 * 初始化文件上传路径
	 * @param name
	 * @param folderPath
	 * @return
	 */
	String initFilePath(String name, String folderPath);
	
	/**
	 * 文件下载
	 * @param downloadfFilePath
	 * @param out
	 */
	void download(String downloadfFilePath, ServletOutputStream out);
	
	/**
	 * 上传文件流至FTP
	 * @param is
	 * @param dst
	 * @param timeout
	 * @return
	 */
	boolean uploadFTPByInputStream(InputStream is, String dst, int timeout);
	
	/**
	 * 从FTP下载文件
	 * @param out
	 * @param src
	 * @param timeout
	 */
	void downloadFromFTP(ServletOutputStream out, String src, int timeout);
	
	/**
	 * 删除指定目录下的文件
	 * @param dir
	 * @param fileName
	 * @param timeout
	 * @return
	 */
	boolean deleteFromFTP(String dir, String fileName, int timeout);

//	Firmware uploadFirmware(InputStream in, String dst, int timeout);
}
