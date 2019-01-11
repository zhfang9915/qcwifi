<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>商铺管理-商铺信息-设备管理</title>
	<link rel="shortcut icon" href="${qc.nginx }favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${qc.nginx }cloud/css/layui.css">
    <link rel="stylesheet" href="${qc.nginx }cloud/css/css.css">
	<link rel="stylesheet" href="${qc.nginx }cloud/css/shops.css">
</head>

<body class="layout-body">
	<div class="layout-wrap">
		<div class="layout-header">
			<%@include file="../common/header.jsp"%>
			<div class="layout-navbar">
				<%@include file="shop.header.jsp"%>
			</div>
		</div>
		<div class="layout-main layui-clear">
			<%@include file="shop.menu.jsp"%>
			<input id="shop_id" type="hidden" value="${shopId }">
			<input id="dev_no" type="hidden" value="${router.devNo }">
			<div class="layout-main-contbox">
				<c:if test="${!binding_status }">
					<div class="layui-contbox-inner">
						<div class="device-none">
							<div class="wifi-close">
								<img src="${qc.nginx }cloud/images/wifi-close.png" alt="">
								<h3>还未绑定设备，什么都看不到！</h3>
								<button class="layui-btn" id="binding">马上绑定</button>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${binding_status }">
					<div class="layui-main-title">
						<div class="main-title-text">
	
						</div>
					</div>
					<hr class="layui-bg-gray">
					<div class="layui-contbox-inner">
						<div class="binding-success-page">
							<div class="status">
								<img src="${qc.nginx }cloud/images/off-line.png" alt="">
							</div>
							<div class="device">
								<div class="title">
									<h4 class="name">Wi-Fi SSID：${router.ssid2g }、${router.ssid2gb }、${router.ssid5g }、${router.ssid5gb }</h4>
									<a href="#" id="warning">解除绑定</a>
								</div>
								<div class="cont">
	
									<p>设备已离线</p>
									<p><span>固件版本</span><span class="blue">${router.version }</span></p>
									<p><span>离线时间</span><span class="blue">2017-12-26 07:46:51</span></p>
								</div>
							</div>
<!-- 							<div class="online-device"> -->
<!-- 								<div class="title"> -->
<!-- 									在线设备情况 -->
<!-- 								</div> -->
<!-- 								<div class="devicelist"> -->
<!-- 									<ul> -->
<!-- 										<li> -->
<%-- 											<div class="name">${router.model }</div> --%>
<%-- 											<img src="${qc.nginx }cloud/images/device.png" alt=""> --%>
<!-- 											<div class="num"> -->
<!-- 												此类设备共 1 台 -->
<!-- 											</div> -->
<!-- 											<div class="version"> -->
<!-- 												0 台未升级固件最新版本 -->
<!-- 											</div> -->
<!-- 										</li> -->
	
<!-- 									</ul> -->
<!-- 								</div> -->
<!-- 							</div> -->
						</div>
					</div>
				</c:if>
			</div>
		</div>
		<!--footer-->
		<%@include file="../common/footer.jsp"%>
	</div>

	<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
	<script src="${qc.nginx }cloud/layui.js"></script>
	<script rootURL=${qc.rootPath } src="${qc.nginx }script/cloud/shop/shop.router.js"></script>
</body>
</html>

