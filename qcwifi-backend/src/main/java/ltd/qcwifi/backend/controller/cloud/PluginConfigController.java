package ltd.qcwifi.backend.controller.cloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.annotation.Resource;
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
import ltd.qcwifi.cloud.platform.service.impl.PluginServiceImpl;
import ltd.qcwifi.comm.oss.OSS;
import ltd.qcwifi.comm.utils.sftp.FileUtil;
import ltd.qcwifi.model.dto.ExecuteResult;
import ltd.qcwifi.model.dto.TableResult;
import ltd.qcwifi.model.dto.param.PluginParam;
import ltd.qcwifi.model.entity.cloud.platform.Plugin;
import ltd.qcwifi.shiro.token.manager.TokenManager;
import net.sf.json.JSONObject;

/**
 * 云平台 插件 配置
 * @author 张芳
 *
 */
@RequestMapping("/plugin")
@Controller
public class PluginConfigController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="pluginService")
	PluginServiceImpl pluginService;
	
	@Autowired
	FileUploadAndFtpService uploadService;
	
	@Resource(name="firmwareService")
	FirmwareServiceImpl firmwareService;
	
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
	@RequiresPermissions("/plugin/query")
	public ModelAndView toPluginConfigPage(Model model) {
		logger.debug(">>>进入插件 配置页....");
		model.addAttribute("pluginActive", "active");
		return new ModelAndView(Web.FOLDER_CLOUD_PLATFORM + "pluginConfig");
	}
	
	/**
	 * 表格展示插件列表
	 * @return
	 */
	@RequestMapping(value = "/table", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/plugin/query")
	public TableResult<Plugin> table(@RequestBody String requestJson) {
		System.out.println(requestJson);
		PluginParam param = (PluginParam) JSONObject.toBean(JSONObject.fromObject(requestJson), PluginParam.class);
		return pluginService.tables(param);
	}
	
	/**
	 * 根据ID获取插件信息
	 * @return
	 */
	@RequestMapping(value = "/search/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/plugin/query")
	public ExecuteResult<Plugin> searchPluginById(@PathVariable("id")Long id) {
		logger.debug("search plugin-->" + id);
		return pluginService.searchById(id);
	}
	
	/**
	 * 批量删除插件
	 * @return
	 */
	@RequestMapping(value = "/delete/batch", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/plugin/delete")
	public ExecuteResult<String> batchDeletePlugin(@RequestBody List<Plugin> plugins) {
		logger.debug("batch delete plugin-->" + super.toJson(plugins));
		List<Plugin> temp = new ArrayList<>();
		//删除FTP上的源文件
		for (Plugin plugin : plugins) {
			boolean flag = oss.remove4Oss(OSS.SYSTEM_BUCKET, "plugin/"+plugin.getName());
			if (flag) {
				temp.add(plugin);
			}
		}
		if (temp.size() == 0) {
			return new ExecuteResult<String>("删除远程服务器插件文件失败");
		}
		return pluginService.batchDelete(temp);
	}
	
	/**
	 * 删除插件
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresPermissions("/plugin/delete")
	public ExecuteResult<String> deletePlugin(@PathVariable("id")Long id) {
		logger.debug("delete plugin-->" + id);
		//获取插件信息
		Plugin plugin = pluginService.searchById(id).getData();
		if (plugin == null) {
			return new ExecuteResult<String>("未查询到此插件信息，无法删除");
		}
		//删除OSS上的源文件
		boolean flag = oss.remove4Oss(OSS.SYSTEM_BUCKET, "plugin/"+plugin.getName());
		if (!flag) {
			return new ExecuteResult<String>("删除远程服务器插件文件失败");
		}
		return pluginService.delete(id);
	}
	
	/**
	 * 更新权限信息
	 * @param permission
	 * @return
	 */
//	@RequestMapping(value = "/update", method = RequestMethod.POST,
//			produces = {"application/json;charset=UTF-8"})
//	@ResponseBody
//	@RequiresPermissions("/plugin/update")
//	public ExecuteResult<String> updatePlugin(Plugin plugin) {
//		logger.debug("update plugin-->" + plugin);
//		return pluginService.update(plugin);
//	}
	
	/**
	 * 创建插件
	 * @param request
	 * @param plugin
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ExecuteResult<String> createPlugin(MultipartHttpServletRequest request, Plugin plugin,@RequestParam("plugin_file")MultipartFile file) throws IOException {
		ExecuteResult<String> result = null;
		if (file.isEmpty()) {
			result = new ExecuteResult<>(false, "请选择插件文件后操作！");
			return result;
		}
		String newFileName = FileUtil.createNewName(file.getOriginalFilename());
		plugin.setName(newFileName);
		BufferedReader reader = null;
		String header = null;
		try {
            //读取头部
            reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            header = reader.readLine();
		} catch (Exception e) {
			e.printStackTrace();
			return new ExecuteResult<String>("插件读取失败");
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		logger.info("上传文件的header：{}", header);
		if (header.startsWith("###!!!;;;") && header.endsWith(";;;")) {
			String arr[] = header.substring(9, header.length()-3).split(";");
			//###!!!;;;  + 版本号; + gcc版本; + md5 + ;;;
			String version = arr[0];
			String crosstool = arr[1];
			String pmd5 = arr[2];
			//判断改插件是否已经存在于服务器
			//获取文件的MD5
			if (pluginService.selectPluginByMD5(pmd5).isSuccess()) {
				result = new ExecuteResult<>(false, "该插件已存在，请不要重复创建！");
				return result;
			}
			ExecuteResult<List<String>> rc = firmwareService.searchCrosstool();
			if (rc.isSuccess()) {
				List<String> cross = rc.getData();
				if (!cross.contains(crosstool)) {
					return new ExecuteResult<String>("没有配置此交叉编译版本的固件，请先配置固件信息");
				}
			}
			plugin.setVersion(version);
			plugin.setCrosstool(crosstool);
			plugin.setMd5(pmd5);
			//计算下载链接
			String downloadUrl = super.webAppPath() + "plugin/download/" + pmd5;
			plugin.setDownloadUrl(downloadUrl);
		}else {
			return new ExecuteResult<String>("插件格式错误，请检查后重新上传");
		}
		
		try {
			Future<ExecuteResult<String>> d = executor.submit(new Callable<ExecuteResult<String>>() {
				@Override
				public ExecuteResult<String> call() throws Exception {
					logger.info("上传包含头部信息的插件");
					//上传包含头部信息的固件
					return oss.upload2Oss(OSS.SYSTEM_BUCKET, "plugin/"+newFileName, file);
				}
			});
			//上传包含头部信息的固件
			if (!d.get().isSuccess()) {
				logger.info("上传包含头部信息的插件 success");
				return d.get();
			}
			plugin.setResPath(d.get().getData());
		} catch (Exception e) {
			e.printStackTrace();
			return new ExecuteResult<>(false, "文件上传异常，请稍后再试");
		}
		plugin.setCreateBy(TokenManager.getUserName());
		return pluginService.create(plugin);
	}
//	public ExecuteResult<String> createPlugin(MultipartHttpServletRequest request, Plugin plugin,@RequestParam("plugin_file")MultipartFile file) throws IOException {
//		ExecuteResult<String> result = null;
//		if (file.isEmpty()) {
//			result = new ExecuteResult<>(false, "请选择插件文件后操作！");
//			return result;
//		}
//		//判断改插件是否已经存在于服务器
//		//获取文件的MD5
//		String md5 = FileMD5Util.getMd5ByInputStream(file.getInputStream());
//		if (pluginService.selectPluginByMD5(md5).isSuccess()) {
//			result = new ExecuteResult<>(false, "该创建已存在，请不要重复创建！");
//			return result;
//		}
//				
//		String newFileName = FileUtil.createNewName(file.getOriginalFilename());
//		String dir = qc.getFtpPluginPath() + newFileName;
//		//上传FTP方法
//		boolean flag = uploadService.uploadFTPByInputStream(file.getInputStream(), dir, 60*1000);
//		if (!flag) {
//			result = new ExecuteResult<>(false, "插件上传失败，请重试");
//			return result;
//		}
//		plugin.setResPath(dir);
//		plugin.setName(newFileName);
//		plugin.setMd5(md5);
//		//计算下载链接
//		String downloadUrl = super.webAppPath() + "plugin/download/" + md5;
//		plugin.setDownloadUrl(downloadUrl);
//		plugin.setCreateBy(TokenManager.getUserName());
//		return pluginService.create(plugin);
//	}
	
	/**
	 * 插件下载
	 * @param md5
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/download/{md5}", method = RequestMethod.GET)
	public void downloadPlugin(@PathVariable("md5")String md5, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		Plugin pu = pluginService.selectPluginByMD5(md5).getData();
		if (pu == null) {
			throw new BaseControllerException("未查询到下载资源");
		}
		String userAgent = request.getHeader("User-Agent");
		byte[] bytes = userAgent.contains("MSIE") ? pu.getName().getBytes() : pu.getName().getBytes("UTF-8");
		String fileName = new String(bytes, "ISO-8859-1");
		response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
//		uploadService.downloadFromFTP(response.getOutputStream(), pu.getResPath(), 60*1000);
		oss.download(OSS.SYSTEM_BUCKET, "plugin/"+pu.getName(), response);
	}
	
	
}
