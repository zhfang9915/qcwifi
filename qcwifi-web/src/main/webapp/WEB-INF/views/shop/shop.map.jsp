<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>商铺地图</title>
    <link rel="shortcut icon" href="${qc.nginx }favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${qc.nginx }cloud/css/layui.css">
    <link rel="stylesheet" href="${qc.nginx }cloud/css/css.css">
	<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
	<style type="text/css">
#shopsMap {
    position: absolute;
    width: 91%;
    height: 75%;
}
</style>
</head>
<body class="layout-body">
    <div class="layout-wrap">
        <div class="layout-header">
            <%@include file="../common/header.jsp"%>
            <div class="layout-navbar">
                <%@include file="shop.header.jsp"%>
            </div>
        </div>
        <div class="layout-main layout-lg-main layui-clear">
            <div class="layout-in-main">
                <div class="in-top layui-clear">
                	<div class="layout-left">
                		<div class="filter-row">
		   					<div class="hd">状态：</div>
		   					<div class="bd">
		   						<a href="" class="filter-item">全部</a>
		   						<a href="" class="filter-item">在线</a>
		   						<a href="" class="filter-item">离线</a>
		   						<a href="" class="filter-item">未绑定设备</a>
		   					</div>
		   				</div>
		   				<div class="filter-row">
		   					<div class="hd">行业：</div>
		   					<div class="bd">
		   						<a href="" class="filter-item">全部</a>
		   						<a href="" class="filter-item">茶馆</a>
		   					</div>
		   				</div>
		   				<div class="filter-row">
		   					<div class="hd">标签：</div>
		   					<div class="bd">
		   						<a href="" class="filter-item">全部</a>
		   					</div>
		   				</div>
                	</div>
                    <div class="layout-right text-right">
                        <a href="${qc.rootPath }shop/map/alone" target="_blank" class="layui-btn layui-btn-big ">单独展示</a>
                        <div class="tips-rtext">可生成连接直接访问地图，无需登录有效期24小时</div>
                    </div>
                </div>
                <div class="in-map">
                	<div id="shopsMap"></div>
                </div>
            </div>
        </div>
        <!--footer-->
        <%@include file="../common/footer.jsp"%>
    </div>

<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script src="${qc.nginx }cloud/layui.js"></script>
<script src="${qc.nginx }cloud/layer/layer.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=XlwywgLBmWY54EzsPyNuUCm8hhnbsAGG"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
<script rootURL=${qc.rootPath } staticURL=${qc.nginx } src="${qc.nginx }script/cloud/shop/shop.map.js"></script>
</body>
</html>