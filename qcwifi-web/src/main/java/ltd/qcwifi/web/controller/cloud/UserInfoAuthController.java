package ltd.qcwifi.web.controller.cloud;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ltd.qcwifi.cloud.service.impl.UserInfoAuthServiceImpl;
import ltd.qcwifi.comm.utils.FileMD5Util;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.entity.User;
import ltd.qcwifi.model.entity.UserInfoAuth;
import ltd.qcwifi.web.Qc;
import ltd.qcwifi.web.Web;
import ltd.qcwifi.web.file.upload.FileUploadAndFtpService;
import ltd.qcwifi.shiro.token.manager.TokenManager;

/*
 * 用户资料认证
 */
@Controller
public class UserInfoAuthController extends AbstractPersonalCenterController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserInfoAuthServiceImpl authService;
	
	@Autowired
	Qc qc;
	
	@Autowired
	FileUploadAndFtpService uploadService;
	
	/**
	 * 资料认证视图
	 * @return
	 */
	@RequestMapping(value = "/userInfoAuth/index", method = RequestMethod.GET)
	public String userInfoAuth(Model model) {
		model.addAttribute("currentPage", "userInfoAuth");
		// 传参数给页面，以便在页面上能通过参数来显示不同的视图
		User user = TokenManager.getToken();
		UserInfoAuth userInfo = authService.queryInfoById(user.getId());
		model.addAttribute("userInfo", userInfo);
		
		return Web.PERSONAL_CENTER + "userInfoAuth";
	}
	
	/**
	 * 用户资料认证
	 * @return
	 */
	@RequestMapping(value = "/userInfoAuth/index", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> authUserInfo(MultipartHttpServletRequest request, UserInfoAuth userInfo, @RequestParam("id_pic_file")MultipartFile file) throws IOException {
		logger.debug("will auth-->" + userInfo);
		// 判断文件是否为空
		if (file.isEmpty()) {
			if (userInfo.getUserType() == 1) {
				return new ExecuteResult<>(false, "请选择营业执照！");
			} else {
				return new ExecuteResult<>(false, "请选择个人手持身份证照片！");
			}
		}
		System.out.println("file = " + file);
		String newFileName = System.currentTimeMillis() + file.getOriginalFilename();
		System.out.println("file name = " + newFileName);
		String dir = qc.getFtpFirmwarePath() + newFileName;
		System.out.println("dir = " + dir);
		boolean flag = uploadService.uploadFTPByInputStream(file.getInputStream(), dir, 60*1000);
		if (!flag) {
			return new ExecuteResult<>(false, "图片上传失败，请重试");
		}
		//获取文件的MD5
		String md5 = FileMD5Util.getMd5ByInputStream(file.getInputStream());
		//计算下载链接
		String downloadUrl = super.webAppPath() + "firmware/download/" + md5;
		System.out.println("file download url = " + downloadUrl);
		userInfo.setPapers(downloadUrl);
		// 获取当前用户信息
		User user = TokenManager.getToken();
		userInfo.setUserId(user.getId());
		return authService.willAuth(userInfo);
	}
	
	/**
	 * 用户资料更新
	 * @return
	 */
	@RequestMapping(value = "/userInfo/update", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> updateUserInfo(UserInfoAuth userInfo) {
		return null;
	}
}
