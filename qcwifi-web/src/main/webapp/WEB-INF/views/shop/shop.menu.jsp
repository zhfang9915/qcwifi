<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="layout-side layout-m-side">
	<div class="layui-shops">
		<img src="${qc.nginx }cloud/images/shops.png" alt="">
		<a href="javascript:;" id="a_shop_name">${shop.shopName }</a><br/>
		<button class="layui-btn shopbtn">商铺认证</button>
	</div>
	<ul class="side-nav">
		<li><a href="${qc.rootPath }shop/info/${shopId }">商铺信息</a></li>
		<li><a href="${qc.rootPath }shop/router/${shopId }">设备管理</a></li>
		<li><a href="#">页面管理</a></li>
		<li><a href="#">认证管理</a></li>
		<li><a href="#">数据详情</a></li>
		<li><a href="#">流量主</a></li>
		<li><a href="#">商家服务</a></li>
		<li><a href="#">商家权限</a></li>
		<li><a href="#">名片小程序</a></li>
	</ul>
</div>