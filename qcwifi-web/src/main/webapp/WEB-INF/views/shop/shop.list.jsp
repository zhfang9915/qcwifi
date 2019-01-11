<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>商铺列表</title>
    <link rel="shortcut icon" href="${qc.nginx }favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${qc.nginx }cloud/css/layui.css">
    <link rel="stylesheet" href="${qc.nginx }cloud/css/css.css">
    <style type="text/css">
    	.f-active{
    		background-color: lightgray;
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
                <div class="layout-shop-box">
                	<!--统计-->
                	<div class="layout-data">
                		<ul class="layui-clear">
                			<li class="lay-online">
                				<div class="data-box">
                					<div class="data-number">0</div>
                					<div class="data-text">在线人数</div>
                				</div>
                			</li>
                			<li class="lay-Snum">
                				<div class="data-box">
                					<div class="data-number">0</div>
                					<div class="data-text">在线商铺</div>
                				</div>
                			</li>
                			<li class="lay-Tnum">
                				<div class="data-box">
                					<div class="data-number">0</div>
                					<div class="data-text">商铺总数</div>
                				</div>
                			</li>
                			<li class="lay-Mnum">
                				<div class="data-box">
                					<div class="data-number">0</div>
                					<div class="data-text">昨日认证人数</div>
                				</div>
                			</li>
                			<li class="lay-Nnum">
                				<div class="data-box">
                					<div class="data-number">0</div>
                					<div class="data-text">昨日认证总数</div>
                				</div>
                			</li>
                		</ul>
                	</div>
                	<!--搜索-->
                	<div class="search-inbox">
	                	<div class="shop-search">
	                		<input type="text" class="shop-search-input" name="shopName" placeholder="输入关键字查询" />
	                		<button class="layui-btn btn-sobar" id="search-by-name">搜索</button>
	                	</div>
	                </div>
                	<!--筛选-->
                	<div class="filter-wrap">
                		<div class="filter-row">
		   					<div class="hd">状态：</div>
		   					<div class="bd">
		   						<a href="javascript:;" class="filter-item f-active">全部</a>
		   						<a href="javascript:;" class="filter-item">在线</a>
		   						<a href="javascript:;" class="filter-item">离线</a>
		   						<a href="javascript:;" class="filter-item">未绑定设备</a>
		   					</div>
		   				</div>
		   				<div class="filter-row">
		   					<div class="hd">行业：</div>
		   					<div class="bd" id="condition-operate">
		   						<a href="javascript:;" class="filter-item f-active">全部</a>
		   					</div>
		   				</div>
		   				<div class="filter-row">
		   					<div class="hd">标签：</div>
		   					<div class="bd" id="condition-mark">
		   						<a href="javascript:;" class="filter-item f-active">全部</a>
		   					</div>
		   				</div>
                	</div>
                	<!--商铺-->
                	<div class="shop-list">
                		<ul id="shopUL"></ul>
                	</div>
                	<div id="paged" style="float: right; margin-bottom: 45px;"></div>
                </div>
            </div>
        </div>
        <!--footer-->
        <%@include file="../common/footer.jsp"%>
    </div>


<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script src="${qc.nginx }cloud/layui.js"></script>
<script src="${qc.nginx }cloud/layer/layer.js"></script>
<script rootURL=${qc.rootPath } staticURL=${qc.nginx } src="${qc.nginx }script/cloud/shop/shop.list.js"></script>
</body>
</html>