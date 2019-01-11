package ltd.qcwifi.backend.controller.cloud;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import ltd.qcwifi.backend.Qc;
import ltd.qcwifi.backend.Web;
import ltd.qcwifi.backend.controller.BaseController;
import ltd.qcwifi.backend.exception.BaseControllerException;
import ltd.qcwifi.backend.file.upload.FileUploadAndFtpService;
import ltd.qcwifi.cloud.platform.service.impl.FirmwareServiceImpl;
import ltd.qcwifi.comm.oss.OSS;
import ltd.qcwifi.comm.utils.FileMD5Util;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.FirmwareParam;
import ltd.qcwifi.model.entity.cloud.platform.Firmware;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import net.sf.json.JSONObject;

/**
 * 云平台 固件 配置
 * @author 张芳
 *
 */
@RequestMapping("/firmware")
@Controller
public class FirmwareConfigController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	FirmwareServiceImpl firmwareService;
	
	@Autowired
	FileUploadAndFtpService uploadService;
	
	@Autowired
	Qc qc;
	
	@Autowired
	ThreadPoolTaskExecutor executor;
	
	@Autowired
	OSS oss;
	
	/**
	 * 固件 配置视图页面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("/firmware/query")
	public ModelAndView toFirmwareConfigPage(Model model) {
		logger.debug(">>>进入固件 配置页....");
		model.addAttribute("firmwareActive", "active");
		return new ModelAndView(Web.FOLDER_CLOUD_PLATFORM + "firmwareConfig");
	}
	
	/**
	 * 固件 列表
	 * @return
	 */
	@RequestMapping(value = "/table", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/firmware/query")
	public TableResult<Firmware> table(@RequestBody String requestJson) {
		System.out.println(requestJson);
		FirmwareParam param = (FirmwareParam) JSONObject.toBean(JSONObject.fromObject(requestJson), FirmwareParam.class);
		return firmwareService.tables(param);
	}
	
	/**
	 * 固件创建
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/firmware/create")
	public ExecuteResult<String> create(MultipartHttpServletRequest request, Firmware fm, @RequestParam("firmware_file")MultipartFile file) throws IOException {
		ExecuteResult<String> result = null;
		if (file.isEmpty()) {
			result = new ExecuteResult<>(false, "请选择固件文件后操作！");
			return result;
		}
		//判断固件是否上传过
		String bakMd5 = FileMD5Util.getMd5ByInputStream(file.getInputStream());
		logger.info("上传文件的MD5：{}", bakMd5);
		if (firmwareService.selectFirmwareByBakMD5(bakMd5)) {
			return new ExecuteResult<String>("固件重复，可能存在同一固件文件！");
		}
			
		String fileName = file.getOriginalFilename();
		//本地生成临时文件
		File tempDir = new File(qc.getTemp()); 
		if (!tempDir.exists()) {
			tempDir.mkdirs();
		}
		File tempFile = new File(qc.getTemp() + fileName);
		FileOutputStream fout = null;
		BufferedInputStream bufferin = null;
		BufferedOutputStream bufferout= null;
		StringBuffer sb = new StringBuffer();
		try {
			fout = new FileOutputStream(tempFile);
			bufferin = new BufferedInputStream(file.getInputStream());
			bufferout = new BufferedOutputStream(fout);
			
            int tempbyte;
            int line = 0;
            while ((tempbyte = bufferin.read()) != -1) {
            	if (line == 0 && tempbyte == '\n') {
            		line ++;
				}else if (line > 0) {
					bufferout.write(tempbyte);
				}else {
					sb.append((char)tempbyte);
				}	
            }
            bufferout.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return new ExecuteResult<String>("固件上传失败");
		} finally {
			try {
				fout.close();
				bufferin.close();
				bufferout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String header = sb.toString();
		logger.info("上传文件的header：{}", header);
		if (header.startsWith("###!!!;;;") && header.endsWith(";;;")) {
			String arr[] = header.substring(9, header.length()-3).split(";");
			String fid = arr[0];
			String fv = arr[1];
			String gcc = arr[2];
			String omd5 = arr[3];
			System.out.println("fid---->" + fid);
			System.out.println("fv----->" + fv);
			System.out.println("gcc---->" + gcc);
			System.out.println("omd5--->" + omd5);
			String nmd5 = FileMD5Util.getMd5ByFile(tempFile);
			System.out.println("nmd5--->" + nmd5);
			System.out.println(nmd5.equals(omd5));
			if (nmd5.equals(omd5)) {
				fm.setId(fid);
				fm.setVersion(fv);
				fm.setCrosstool(gcc);
				fm.setMd5(nmd5);
			}else {
				return new ExecuteResult<String>("固件验证错误，请检查后重新上传");
			}
		}else {
			return new ExecuteResult<String>("固件格式错误，请检查后重新上传");
		}
		
		try {
			Future<ExecuteResult<String>> s = executor.submit(new Callable<ExecuteResult<String>>() {
				@Override
				public ExecuteResult<String> call() throws Exception {
					logger.info("上传源固件文件");
					//上传源固件文件
					return oss.upload2OssByInputStream(OSS.SYSTEM_BUCKET, "firmware/"+fileName, new FileInputStream(tempFile));
				}
			});
			Future<ExecuteResult<String>> d = executor.submit(new Callable<ExecuteResult<String>>() {
				@Override
				public ExecuteResult<String> call() throws Exception {
					logger.info("上传包含头部信息的固件");
					//上传包含头部信息的固件
					return oss.upload2Oss(OSS.SYSTEM_BUCKET, "firmware/bak/"+fileName, file);
				}
			});
			if (!s.get().isSuccess()) {
				logger.info("上传源固件文件 success");
				return s.get();
			}
			fm.setResPath(s.get().getData());
			
			//上传包含头部信息的固件
			if (!d.get().isSuccess()) {
				logger.info("上传包含头部信息的固件 success");
				return d.get();
			}
			fm.setBakPath(d.get().getData());
		} catch (Exception e) {
			e.printStackTrace();
			return new ExecuteResult<>(false, "文件上传异常，请稍后再试");
		}
		
		
		//上传源文件
//		boolean isource = uploadService.uploadFTPByInputStream(new FileInputStream(tempFile), source, 60*1000);
//		if (!isource) {
//			return new ExecuteResult<>(false, "固件上传失败，请重试");
//		}
		
//		boolean flag = uploadService.uploadFTPByInputStream(file.getInputStream(), bak, 60*1000);
//		if (!flag) {
//			result = new ExecuteResult<>(false, "固件上传失败，请重试");
//			return result;
//		}
		fm.setName(fileName);
		fm.setBakMd5(bakMd5);
		
		//计算下载链接
		String downloadUrl = super.webAppPath() + "firmware/download/s/" + fm.getMd5();
		String bakUrl = super.webAppPath() + "firmware/download/d/" + fm.getMd5();
		fm.setDownloadUrl(downloadUrl);
		fm.setBakUrl(bakUrl);
		fm.setCreateBy(TokenManager.getUserName());
		if (tempFile.exists()) {
			tempFile.delete();
		}
		if (firmwareService.createFirmware(fm)) {
			logger.info("创建固件成功");
			return new ExecuteResult<String>("创建固件成功");
		}
		else {
			logger.info("创建固件失败");
			//删除FTP上的源文件
			boolean d = oss.remove4Oss(OSS.SYSTEM_BUCKET, "firmware/bak/"+fm.getName());
			boolean s = oss.remove4Oss(OSS.SYSTEM_BUCKET, "firmware/"+fm.getName());
			return new ExecuteResult<String>("创建插件失败！");
		}
	}
//	@RequestMapping(value = "/create", method = RequestMethod.POST,
//			produces = {"application/json;charset=UTF-8"})
//	@ResponseBody
//	@RequiresPermissions("/firmware/create")
//	public ExecuteResult<String> create(MultipartHttpServletRequest request, Firmware firmware, @RequestParam("firmware_file")MultipartFile file) throws IOException {
//		if (firmwareService.searchCrosstoolByVersion(firmware.getVersion()) != null) {
//			return new ExecuteResult<>(false, "已存在该固件版本，请删除原先版本重试！");
//		}
//		
//		ExecuteResult<String> result = null;
//		if (file.isEmpty()) {
//			result = new ExecuteResult<>(false, "请选择固件文件后操作！");
//			return result;
//		}
//		String newFileName = System.currentTimeMillis() + file.getOriginalFilename();
//		String dir = qc.getFtpFirmwarePath() + newFileName;
//		boolean flag = uploadService.uploadFTPByInputStream(file.getInputStream(), dir, 60*1000);
//		if (!flag) {
//			result = new ExecuteResult<>(false, "固件上传失败，请重试");
//			return result;
//		}
//		firmware.setResPath(dir);
//		firmware.setName(newFileName);
//		//获取文件的MD5
//		String md5 = FileMD5Util.getMd5ByStream(file.getInputStream());
//		firmware.setMd5(md5);
//		if (firmwareService.selectFirmwareByMD5(md5)) {
//			//删除FTP上的源文件
//			uploadService.deleteFromFTP(qc.getFtpFirmwarePath(), firmware.getName(), 60*1000);
//			return new ExecuteResult<String>("md5值重复，可能存在同一固件文件！");
//		}
//		
//		//计算下载链接
//		String downloadUrl = super.webAppPath() + "firmware/download/" + md5;
//		firmware.setDownloadUrl(downloadUrl);
//		firmware.setCreateBy(TokenManager.getUserName());
//		if (firmwareService.createFirmware(firmware)) {
//			return new ExecuteResult<String>("创建插件成功");
//		}
//		else {
//			//删除FTP上的源文件
//			uploadService.deleteFromFTP(qc.getFtpFirmwarePath(), firmware.getName(), 60*1000);
//			return new ExecuteResult<String>("创建插件失败！");
//		}
//	}
	
	/**
	 * 根据ID获取固件信息
	 * @return
	 */
	@RequestMapping(value = "/search/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<Firmware> searchById(@PathVariable("id")Long id) {
		logger.debug("search firmware-->" + id);
		return firmwareService.searchById(id);
	}
	
	/**
	 * 获取GCC版本信息
	 * @return
	 */
	@RequestMapping(value = "/search/crosstool", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<List<String>> searchCrosstool() {
		logger.debug("search crosstool-->");
		return firmwareService.searchCrosstool();
	}
	
	/**
	 * 批量删除固件
	 * @return
	 */
	@RequestMapping(value = "/delete/batch", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/firmware/delete")
	public ExecuteResult<String> batchDelete(@RequestBody List<Firmware> firmwares) {
		logger.debug("batch delete firmware-->" + super.toJson(firmwares));
		for (int i = 0; i < firmwares.size(); i ++) {
			//删除FTP上的源文件
			Firmware firmware = firmwares.get(i);
//			boolean flag = uploadService.deleteFromFTP(qc.getFtpFirmwarePath(), firmware.getName(), 60*1000);
			boolean d = oss.remove4Oss(OSS.SYSTEM_BUCKET, "firmware/bak/"+firmware.getName());
			boolean s = oss.remove4Oss(OSS.SYSTEM_BUCKET, "firmware/"+firmware.getName());
			if (!d || !s) {
				return new ExecuteResult<String>("删除远程服务器固件文件失败");
			}
			
			if (firmwareService.deleteFirwmare(firmware.getId()) == false) {
				return new ExecuteResult<String>("删除远程服务器固件文件失败");
			}
		}
		
		return new ExecuteResult<String>("删除远程服务器固件成功");
	}
	
	/**
	 * 删除固件
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/firmware/delete")
	public ExecuteResult<String> delete(@PathVariable("id")String id) {
		logger.debug("delete firmware-->" + id);
		//获取固件信息
		Firmware firmware = firmwareService.searchById(id).getData();
		if (firmware == null) {
			return new ExecuteResult<String>("未查询到此插件信息，无法删除");
		}
		//删除FTP上的源文件
//		boolean flag = uploadService.deleteFromFTP(qc.getFtpFirmwarePath(), firmware.getName(), 60*1000);
		boolean d = oss.remove4Oss(OSS.SYSTEM_BUCKET, "firmware/bak/"+firmware.getName());
		boolean s = oss.remove4Oss(OSS.SYSTEM_BUCKET, "firmware/"+firmware.getName());
		if (!d || !s) {
			return new ExecuteResult<String>("删除远程服务器固件文件失败");
		}
		return firmwareService.delete(id);
	}

	/**
	 *  固件下载
	 * @param md5
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/download/{type}/{md5}", method = RequestMethod.GET)
	public void download(@PathVariable("type")String type, @PathVariable("md5")String md5, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		Firmware fu = firmwareService.selectByMD5(md5).getData();
		if (fu == null) {
			throw new BaseControllerException("无下载资源");
		}
		byte[] bytes;
		String userAgent = request.getHeader("User-Agent");
		if (userAgent != null) {
			if (userAgent.contains("MSIE")) {
				bytes = fu.getName().getBytes();
			}
			else {
				bytes = fu.getName().getBytes("UTF-8");
			}
		}
		else {
			bytes = fu.getName().getBytes("UTF-8");
		}
		String fileName = new String(bytes, "ISO-8859-1");
		response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
//		uploadService.downloadFromFTP(response.getOutputStream(), fu.getResPath(), 60*1000);
		if ("s".equals(type)) {
			oss.download(OSS.SYSTEM_BUCKET, "firmware/"+fu.getName(), response);
		}else {
			oss.download(OSS.SYSTEM_BUCKET, "firmware/bak/"+fu.getName(), response);
		}
	}
	
}
