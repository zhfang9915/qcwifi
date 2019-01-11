package ltd.qcwifi.api.controller;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import ltd.qcwifi.api.annotation.AopLog;
import ltd.qcwifi.api.rabbit.mq.RouterProducer;
import ltd.qcwifi.cloud.platform.service.FirmwareService;
import ltd.qcwifi.cloud.platform.service.JsService;
import ltd.qcwifi.cloud.platform.service.PluginService;
import ltd.qcwifi.cloud.service.ShopRouterService;
import ltd.qcwifi.comm.utils.RandCodeUtil;
import ltd.qcwifi.comm.utils.SourceCodeFormat;
import ltd.qcwifi.comm.utils.SysDbPkUtil;
import ltd.qcwifi.model.dto.ApiResult;
import ltd.qcwifi.model.entity.cloud.platform.Firmware;
import ltd.qcwifi.model.entity.cloud.platform.Jscode;
import ltd.qcwifi.model.entity.cloud.platform.Plugin;
import ltd.qcwifi.model.entity.cloud.platform.ShopRouter;

/**
 * @ClassName: RouterController
 * @Description: 路由器相关API
 * @author: zhfang
 * @date: 2018年2月3日 下午7:31:57
 */
@RestController
@RequestMapping("/router")
public class RouterController {
	
	private static final Logger logger = LoggerFactory.getLogger(RouterController.class);
	/**
	 * 路由器缓存失效时间
	 */
	private static final Long ROUTER_CACHE_TIMEOUT = 5L;
	
	@Autowired
	RedisTemplate<String, Serializable> redisTemplate;
	
	@Autowired
    RouterProducer routerProducer;
	
	@Autowired
	ShopRouterService routerService;
	
	@Autowired
	PluginService pluginService;
	
	@Autowired
	FirmwareService firmwareService;
	
	@Autowired
	JsService jsService;
	
	/**
	 * 路由器上报设备信息
	 * @Title: infomation
	 * @author: zhfang
	 * @Description: TODO
	 * @param router
	 * @return
	 * @return: ApiResult<String>
	 */
	@RequestMapping(value = "/infomation", method = RequestMethod.POST,
			produces = {"application/json;charset=utf8"})
	@AopLog("/router/infomation")
	public ApiResult<String> infomation(@RequestBody ShopRouter router) {
		try {
			if (router == null) {
				return new ApiResult<>(10007);
			}
			if (StringUtils.isBlank(router.getMac())) {
				return new ApiResult<>(10008);
			}
			logger.debug("设备上传信息 --> {}", router.toString());
			//如果存在设备编码devNo,则是已注册的设备。反之则无注册
			if (StringUtils.isBlank(router.getDevNo())) {
				//如果没有设备编码，验证mac是否分配，如果已分配则返回，否则新增
				String devNo = routerService.getRouterNoByMac(router.getMac());
				if (StringUtils.isNotBlank(devNo)) {
					router.setDevNo(devNo);
				}else {
					devNo = SysDbPkUtil.createPk("DEV");
					logger.debug("设备未绑定，分配设备编码 --> {}", devNo);
					router.setDevNo(devNo);
				}
			}
			routerProducer.sendRouterInfoToQueue(router);
			return new ApiResult<>(router.getDevNo());
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("设备信息上报出错：{}", e.getCause());
			return new ApiResult<>(10001);
		}
	}

	/**
	 * 路由器绑定
	 * 	接收路由器的设备型号，版本，mac地址
	 * 	成功后返回一个序列码
	 * 	重复请求 上一个序列码失效
	 * @Title: binding
	 * @author: zhfang
	 * @Description: TODO
	 * @return
	 * @return: ApiResult<String>
	 */
	@RequestMapping(value = "/binding", method = RequestMethod.POST,
			produces = {"application/json;charset=utf8"})
	@AopLog("/router/binding")
	public ApiResult<String> binding(@RequestBody ShopRouter router) {
		try {
			if (router == null) {
				return new ApiResult<>(10007);
			}
			if (StringUtils.isBlank(router.getDevNo())) {
				return new ApiResult<>(10009);
			}
			logger.info("设备绑定信息：{}",  router);
			//生成序列码
			String sequenceCode = RandCodeUtil.generateVerifyCode(6);
			while (true) {
				Object obj = redisTemplate.opsForValue().get(sequenceCode);
				if (obj != null) {
					sequenceCode = RandCodeUtil.generateVerifyCode(6);
				}else {
					break;
				}
			}
			
			//将序列码和设备编码绑定
			//判断缓存中是否存在该mac的序列码，存在则使其失效
			String seqCache = (String) redisTemplate.opsForValue().get(router.getDevNo());//原序列码
			if (seqCache != null) {
				//原来的信息失效
				redisTemplate.delete(router.getDevNo());
				redisTemplate.delete(seqCache);
			}
			//存放新的缓存数据
			redisTemplate.opsForValue().set(router.getDevNo(), sequenceCode, ROUTER_CACHE_TIMEOUT, TimeUnit.MINUTES);
			redisTemplate.opsForValue().set(sequenceCode, router, ROUTER_CACHE_TIMEOUT, TimeUnit.MINUTES);
			return new ApiResult<>(sequenceCode);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResult<>(10001);
		}
	}
	
	/**
	 * 获取路由器插件升级信息
	 * @Title: getPlugin
	 * @author: zhfang
	 * @Description: TODO
	 * @param router
	 * @return
	 * @return: ApiResult<String>
	 */
	@RequestMapping(value = "/plugin", method = RequestMethod.POST,
			produces = {"application/json;charset=utf8"})
	@AopLog("/router/plugin")
	public ApiResult<String> getPlugin(@RequestBody ShopRouter r) {
		try {
			if (StringUtils.isBlank(r.getDevNo())) {
				return new ApiResult<>(10013);
			}
			if (StringUtils.isBlank(r.getGccv())) {
				return new ApiResult<>(10014);
			}
			if (StringUtils.isBlank(r.getPv())) {
				return new ApiResult<>(10015);
			}
			//获取设备信息
			ShopRouter router = routerService.getRouterByNo(r.getDevNo());
			if (router == null) {
				logger.info("设备 {} 不存在", r.getDevNo());
				return new ApiResult<>(10010);
			}
			//获取交叉编译版本对应的插件
			Plugin plugin = pluginService.getPluginByGccv(r.getGccv());
			if (plugin == null) {
				logger.info("设备 {} 的交叉版本 {} 没有对应的插件", r.getDevNo(), r.getGccv());
				return new ApiResult<>(10011);
			}
			//版本一致则为最新版本
			if (plugin.getVersion().equals(r.getPv())) {
				logger.info("设备 {} 插件是最新版本", r.getDevNo());
				return new ApiResult<>(10012);
			}
			//返回下载链接和插件的MD5
			logger.info("设备 {} 获取到最新版本插件 {}", r.getDevNo(), plugin.toString());
			return new ApiResult<>(plugin.getDownloadUrl());
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResult<>(10001);
		}
	}
	
	/**
	 * 获取固件升级信息
	 * @Title: getFirmwar
	 * @author: zhfang
	 * @Description: TODO
	 * @param r
	 * @return
	 * @return: ApiResult<JSONObject>
	 */
	@RequestMapping(value = "/firmware", method = RequestMethod.POST,
			produces = {"application/json;charset=utf8"})
	@AopLog("/router/firmware")
	public ApiResult<String> getFirmwar(@RequestBody ShopRouter r) {
		try {
			if (StringUtils.isBlank(r.getDevNo())) {
				return new ApiResult<>(10013);
			}
			if (StringUtils.isBlank(r.getFwv())) {
				return new ApiResult<>(10016);
			}
			if (StringUtils.isBlank(r.getFwid())) {
				return new ApiResult<>(10017);
			}
			//获取设备信息
			ShopRouter router = routerService.getRouterByNo(r.getDevNo());
			if (router == null) {
				logger.info("设备 {} 不存在", r.getDevNo());
				return new ApiResult<>(10010);
			}
			//获取固件信息
			Firmware fm = firmwareService.selectById(r.getFwid());
			if (fm == null) {
				logger.info("设备 {} 的固件ID {} 没有对应的插件", r.getDevNo(), r.getFwid());
				return new ApiResult<>(10018);
			}
			//版本一致则为最新版本
			if (fm.getVersion().equals(r.getFwv())) {
				logger.info("设备 {} 固件是最新版本", r.getDevNo());
				return new ApiResult<>(10019);
			}
			//返回下载链接和插件的MD5
			logger.info("设备 {} 获取到最新版本固件 {}", r.getDevNo(), fm.toString());
			return new ApiResult<>(fm.getDownloadUrl());
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResult<>(10001);
		}
	}
	
	@RequestMapping(value = "/js/{devNo}", method = RequestMethod.GET)
	@AopLog("/router/js")
	public ApiResult<JSONObject> getJS(@PathVariable("devNo")String devNo) {
		try {
			//获取设备信息
			ShopRouter router = routerService.getRouterByNo(devNo);
			if (router == null) {
				logger.info("设备 {} 不存在", devNo);
				return new ApiResult<>(10010);
			}
			//获取JS模版
			Jscode js = jsService.getDefaultJS();
			if (js == null) {
				logger.info("设备 {} 未查询到默认的JS", devNo);
				return new ApiResult<>(10020);
			}
			//返回下载链接和插件的MD5
			logger.info("设备 {} 获取到最新版本JS");
			JSONObject obj = new JSONObject();
			obj.put("host", js.getServerIp());
			obj.put("port", js.getServerPort());
			obj.put("js", js.getCode());
			obj.put("mark", "mm=" + SysDbPkUtil.createSeq());
			return new ApiResult<>(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResult<>(10001);
		}
	}
	
}
