package ltd.qcwifi.comm.oss;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

import ltd.qcwifi.model.dto.ExecuteResult;

@Component
public class OSS {

	Logger log = LoggerFactory.getLogger(OSS.class);
	// endpoint以杭州为例，其它region请按实际情况填写
	private final static String endpoint = "oss-cn-shenzhen.aliyuncs.com";
	// accessKey
//	private final static String accessKeyId = "LTAIeJ2IUIp6iilT";
//	private final static String accessKeySecret = "gmGl56WbNytDkACKzRxl76XzTKbDWO";
	// 空间
	public final static String SYSTEM_BUCKET = "qc-wifi";
	public final static String IMAGES_BUCKET = "qcimgs";

	@Autowired
	private OSSClient ossClient;

	public ExecuteResult<String> upload2OssByInputStream(String bucketName, String fileName, InputStream inputStream) throws Exception {
		try {
			String result = this.uploadFile2OSS(bucketName, inputStream, fileName);
			if (result == null) {
				return new ExecuteResult<>(false, "文件上传失败，请重试");
			}
			return new ExecuteResult<String>(getUrl(bucketName, fileName));
		} catch (Exception e) {
			e.printStackTrace();
			return new ExecuteResult<>(false, "文件上传异常，请稍后再试");
		}
	}
	
	/**
	 * 将文件上传值OSS服务器
	 * @param bucketName
	 * @param key
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public ExecuteResult<String> upload2Oss(String bucketName, String fileName, MultipartFile file) throws Exception {
		try {
			InputStream inputStream = file.getInputStream();
			String result = this.uploadFile2OSS(bucketName, inputStream, fileName);
			if (result == null) {
				return new ExecuteResult<>(false, "文件上传失败，请重试");
			}
			return new ExecuteResult<String>(getUrl(bucketName, fileName));
		} catch (Exception e) {
			e.printStackTrace();
			return new ExecuteResult<>(false, "文件上传异常，请稍后再试");
		} finally {
//			ossClient.shutdown();
		}
	}
	
	/**
	 * 从OSS中删除文件
	 * @param bucketName
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public boolean remove4Oss(String bucketName, String key) {
		try {
			ossClient.deleteObject(bucketName, key);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
//			ossClient.shutdown();
		}
	}
	
	/**
	 * 从OSS中获取文件流
	 * @param bucketName
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public ExecuteResult<InputStream> get4Oss(String bucketName, String key) throws Exception {
		try {
			OSSObject ossObj = ossClient.getObject(bucketName, key); 
			return new ExecuteResult<InputStream>(ossObj.getObjectContent());
		} catch (Exception e) {
			return new ExecuteResult<>(false, "OSS文件下载失败");
		} finally {
//			ossClient.shutdown();
		}
	}

	/**
	 * 上传到OSS服务器 如果同名文件会覆盖服务器上的
	 *
	 * @param instream
	 *            文件流
	 * @param fileName
	 *            文件名称 包括后缀名
	 * @return 出错返回null ,唯一MD5数字签名
	 */
	private String uploadFile2OSS(String bucketName, InputStream instream, String fileName) {
		String ret = null;
		try {
			// 创建上传Object的Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(instream.available());
			objectMetadata.setCacheControl("no-cache");
			objectMetadata.setHeader("Pragma", "no-cache");
			objectMetadata.setContentType(this.getcontentType(fileName.substring(fileName.lastIndexOf("."))));
			objectMetadata.setContentDisposition("inline;filename=" + fileName);
			// 上传文件
			PutObjectResult putResult = ossClient.putObject(bucketName, fileName, instream, objectMetadata);
			ret = putResult.getETag();//返回文件MD5值
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return null;
		} finally {
			try {
				if (instream != null) {
					instream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
//			ossClient.shutdown();
		}
		return ret;
	}
	
	/**
	 * 返回指定bucket 指定目录下的文件列表URL
	 * @Title: list
	 * @author: zhfang
	 * @Description: TODO
	 * @param bucketName
	 * @param prefix
	 * @return
	 * @return: List<String>
	 */
	public List<String> list(String bucketName, String prefix) {
		List<String> objs = new ArrayList<>();
		try {
			// 构造ListObjectsRequest请求
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

			// 递归列出fun目录下的所有文件
			listObjectsRequest.setPrefix(prefix);

			ObjectListing listing = ossClient.listObjects(listObjectsRequest);

			// 遍历所有Object
			for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
			    objs.add(getUrl(bucketName, objectSummary.getKey()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			ossClient.shutdown();
		}
		return objs;
	}
	
	public void download(String bucketName, String key, HttpServletResponse response) {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			OSSObject ossObject = ossClient.getObject(bucketName, key);
			
			in=new BufferedInputStream(ossObject.getObjectContent());  
            out=new BufferedOutputStream(response.getOutputStream());  
            
			byte[] buf = new byte[2048];
			int tempbyte;
            while ((tempbyte = in.read(buf)) != -1) {
            	out.write(buf, 0, tempbyte);
            }
            out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 判断OSS服务文件上传时文件的contentType
	 *
	 * @param FilenameExtension
	 *            文件后缀
	 * @return String
	 */
	private String getcontentType(String FilenameExtension) {
		if (FilenameExtension.equalsIgnoreCase(".bmp")) {
			return "image/bmp";
		}
		if (FilenameExtension.equalsIgnoreCase(".gif")) {
			return "image/gif";
		}
		if (FilenameExtension.equalsIgnoreCase(".jpeg") || FilenameExtension.equalsIgnoreCase(".jpg")
				|| FilenameExtension.equalsIgnoreCase(".png")) {
			return "image/jpeg";
		}
		if (FilenameExtension.equalsIgnoreCase(".html")) {
			return "text/html";
		}
		if (FilenameExtension.equalsIgnoreCase(".txt")) {
			return "text/plain";
		}
		if (FilenameExtension.equalsIgnoreCase(".vsd")) {
			return "application/vnd.visio";
		}
		if (FilenameExtension.equalsIgnoreCase(".pptx") || FilenameExtension.equalsIgnoreCase(".ppt")) {
			return "application/vnd.ms-powerpoint";
		}
		if (FilenameExtension.equalsIgnoreCase(".docx") || FilenameExtension.equalsIgnoreCase(".doc")) {
			return "application/msword";
		}
		if (FilenameExtension.equalsIgnoreCase(".xml")) {
			return "text/xml";
		}
		return "image/jpeg";
	}
	
	private String getUrl(String bucketName, String key) {
		return "http://" + bucketName + "." + endpoint + "/" + key;
	}
}
