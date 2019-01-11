package ltd.qcwifi.web.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ltd.qcwifi.comm.oss.OSS;
import ltd.qcwifi.comm.utils.MD5;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import ltd.qcwifi.web.controller.base.BaseController;

@Controller
@RequestMapping("/img")
public class UploadImg2OssController extends BaseController {
	
	@Autowired
	OSS oss;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ExecuteResult<String> upload(MultipartHttpServletRequest request, MultipartFile file) {
		//判断文件是否选择
		if (file.isEmpty()) {
			return new ExecuteResult<>(false, "未检测到文件信息");
		}
		String realName = file.getOriginalFilename();//文件的源文件名称
		String filePath = "content/" + TokenManager.getUserId() + "/" + MD5.getMD5(realName);//上传路径   content/+userid/+源文件md5民称
		filePath += realName.substring(realName.indexOf('.'));//文件后缀
		try {
			ExecuteResult<String> ur = oss.upload2Oss(OSS.IMAGES_BUCKET, filePath, file);
			return ur;
		} catch (Exception e) {
			e.printStackTrace();
			return new ExecuteResult<>(false, "文件上传异常，请稍后再试");
		}
		
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ExecuteResult<List<String>> list() {
		try {
			String prefix = "content/" + TokenManager.getUserId() + "/";
			List<String> imgs = oss.list(OSS.IMAGES_BUCKET, prefix);
			if (imgs.isEmpty()) {
				return new ExecuteResult<>(false, "暂无文件信息");
			}
			return new ExecuteResult<List<String>>(imgs);
		} catch (Exception e) {
			return new ExecuteResult<>(false, "获取文件信息出错，请稍后再试");
		}
	}
	
}
