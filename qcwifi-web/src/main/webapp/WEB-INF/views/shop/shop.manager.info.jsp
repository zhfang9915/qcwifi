<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>商铺管理-商铺信息-手机浏览</title>
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
			<div class="layout-main-contbox">
				<div class="layui-main-title">
					<div class="main-title-text">
						商铺信息
					</div>
				</div>
				<hr class="layui-bg-gray">
				<div class="layui-contbox-inner">
					<div class="shops-information-box">
						<div class="shops-topbox">
							<div class="shops-basedata">
								<div class="shopname">
									<div class="name displaytable">
										<h3 id="h3_shop_name">${shop.shopName }</h3>
										<a href="${qc.rootPath }shop/info/update/${shopId }">修改</a>
										<a onclick="deleteShop()" style="background: brown;">删除</a>
									</div>
									<div class="remark">
										<i class="layui-icon"><img src="${qc.nginx }cloud/images/edit.png" alt=""></i>
										<p id="p_shop_remark">
											<c:if test="${not empty shop.remark}">
												${shop.remark }
											</c:if>
											<c:if test="${empty shop.remark}">暂无备注</c:if>
										</p>
									</div>
									<div class="add">
										${shop.province } ${shop.city } ${shop.area } ${shop.address }
									</div>
									<div class="id">
										商铺ID：${shop.shopId }
										<input id="shop_id" type="hidden" value="${shop.shopId }">
									</div>
									<div class="id">
										联系电话：${shop.phone }
									</div>
									<div class="id">
										创建于：<fmt:formatDate value="${shop.createTime }" type="BOTH"/>
									</div>
								</div>
<!-- 								<div class="shopimg"> -->
<!-- 									<div class="top displaytable"> -->
<%-- 										<div class="img"><img src="${qc.nginx }cloud/images/wallet.png" alt=""></div> --%>
<!-- 										<div class="imgname"> -->
<!-- 											<small>超高分成&nbsp;&nbsp;&nbsp;&nbsp;收益惊人</small> -->
<!-- 											<p>微信流量主</p> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 									<div class="bottom"> -->
<!-- 										<div class="left">日进斗金不是梦</div> -->
<!-- 										<div class="right"> 未开通</div> -->
<!-- 									</div> -->
<!-- 								</div> -->

							</div>
							<hr class="layui-bg-gray">

							<div class="shops-table">
								<div class="store shopsitem">
									<div class="title">
										<h3>商家信息</h3>
										<a href="#" class="setup">设置</a>
									</div>
									<table class="layui-table" lay-skin="line">
										<tbody>
											<tr>
												<td>商铺账号:</td>
												<td>cxw</td>
											</tr>
											<tr>
												<td>页面管理</td>
												<td>不可修改</td>
											</tr>
											<tr>
												<td>认证管理</td>
												<td>不可修改</td>
											</tr>
											<tr>
												<td>流量主微信关注</td>
												<td>不可修改</td>
											</tr>
										</tbody>
									</table>

								</div>
								<div class="device shopsitem">
									<div class="title">
										<h3>设备信息</h3>
										<a href="#">设备管理</a>
									</div>
									<table class="layui-table" lay-skin="line">
										<tbody>
											<tr>
												<td>SSID:</td>
												<td>cxw</td>
											</tr>
											<tr>
												<td>设备状态:</td>
												<td class="red">未绑定设备</td>
											</tr>
											<tr>
												<td>固件版本:</td>
												<td></td>
											</tr>
											<tr>
												<td>在线时长:</td>
												<td class="red">未绑定设备</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="desc">
									商铺账号请登录小博管家平台（xiaolou.com）
								</div>
							</div>
						</div>

						<div class="page">
							<p>页面管理</p>
						</div>
						<div class="shops-midbox">
							<div class="leftinfo">
								<div class="title">
									商铺功能页
								</div>
								<div class="layui-item">
									<label class="layui-label">商铺功能页</label>
									<div class="result">
										行业模板
									</div>
								</div>
								<div class="layui-item">
									<label class="layui-label">页面标题</label>
									<div class="result">
										爱恋甜品店
									</div>
								</div>
								<div class="layui-item">
									<label class="layui-label">时间间隔</label>
									<div class="result">
										2秒
									</div>
								</div>
								<div class="title">
									登录中
								</div>
								<div class="layui-item">
									<label class="layui-label">海报页</label>
									<div class="result">
										1*5秒
									</div>
								</div>
								<div class="title">
									登录成功
								</div>
								<div class="layui-item">
									<label class="layui-label">登陆成功页</label>
									<div class="result">
										系统默认
									</div>
								</div>

							</div>
							<div class="right-info">
								<div class="btnline">
									<buttn class="layui-btn">在线预览</buttn>
									<span>|</span>
									<buttn class="layui-btn active">手机预览</buttn>
								</div>
								<div class="code">
									<img src="${qc.nginx }cloud/images/code03.png" alt="">
								</div>
							</div>
						</div>
						<div class="page">
							<p>页面管理</p>
							<a href="#">设置</a>
						</div>
						<div class="shops-botbox">
							<div class="class">
								<ul>
									<li>
										<img src="${qc.nginx }cloud/images/icon001.png" alt="">
										<p class="name">会员认证</p>
										<div class="status">
											<div class="off">关闭</div>
											<div class="on" style="display: none">开启</div>
										</div>
									</li>
									<li class="active">
										<img src="${qc.nginx }cloud/images/icon002.png" alt="">
										<p class="name">一键上网</p>
										<div class="status">
											<div class="off" style="display: none">关闭</div>
											<div class="on">开启</div>
										</div>
									</li>
									<li>
										<img src="${qc.nginx }cloud/images/icon003.png" alt="">
										<p class="name">微信链接WIFI</p>
										<div class="status">
											<div class="off">关闭</div>
											<div class="on" style="display: none">开启</div>
										</div>
									</li>
									<li>
										<img src="${qc.nginx }cloud/images/icon004.png" alt="">
										<p class="name">手机认证</p>
										<div class="status">
											<div class="off">关闭</div>
											<div class="on" style="display: none">开启</div>
										</div>
									</li>
									<li>
										<img src="${qc.nginx }cloud/images/icon005.png" alt="">
										<p class="name">短信认证</p>
										<div class="status">
											<div class="off">关闭</div>
											<div class="on" style="display: none">开启</div>
										</div>
									</li>
									<li>
										<img src="${qc.nginx }cloud/images/icon006.png" alt="">
										<p class="name">密码认证</p>
										<div class="status">
											<div class="off">关闭</div>
											<div class="on" style="display: none">开启</div>
										</div>
									</li>
								</ul>
							</div>
							<div class="displaytable">
								<div class="layui-item">
									<label class="layui-label">认证有效期：</label>
									<div class="result">
										6小时
									</div>
								</div>
								<div class="layui-item">
									<label class="layui-label">网址白名单：</label>
									<div class="result">
										无
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
		<!--footer-->
		<%@include file="../common/footer.jsp"%>
	</div>




	<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
	<script src="${qc.nginx }cloud/layui.js"></script>
	<script rootURL=${qc.rootPath } src="${qc.nginx }script/cloud/shop/shop.info.js"></script>
</body>

</html>
