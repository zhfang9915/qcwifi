<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>云平台-API接口日志</title>
	<meta name="keywords" content="云平台-API接口日志">
	<meta name="description" content="云平台-API接口日志" />
	<meta name="HandheldFriendly" content="True" />
	<link rel="shortcut icon" href="${qc.nginx }static/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="${qc.nginx }static/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${qc.nginx }static/bootstrap/css/bootstrap-table.min.css">
	<link rel="stylesheet" href="${qc.nginx }static/bootstrap/css/bootstrap-select.min.css">
	
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
		<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	<script src="${qc.nginx }static/jquery-1.12.4.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/bootstrap-table.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
	<script type="text/javascript" src="${qc.nginx }static/bootstrap/js/bootstrap-table-export.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/tableExport.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/bootstrap-select.min.js"></script>
	<script type="text/javascript" src="${qc.nginx }static/laydate/laydate.js"></script>
	<script rootURL=${qc.rootPath} src="${qc.nginx}static/script/logs/api.js"></script>
</head>

<body>
	<div class="container-fluid">
		<div class="row">
				<div id="toolbar">
					<form class="form-inline">
						<div class="form-group">
							<select id="search-invokeApi" class="selectpicker show-menu-arrow" title="选择要查询的接口" data-size="6">
								<option value="">全部</option>
								<option value="/router/infomation">上报路由器设备信息</option>
								<option value="/router/binding">获取路由器设备绑定序列码</option>
								<option value="/router/plugin">获取路由器插件升级信息</option>
								<option value="/router/firmware">获取路由器固件升级信息</option>
								<option value="/router/js">获取JS模版</option>
								<option value="/ads/content">获取广告资源信息</option>
							</select>
						</div>
						<div class="form-group">
							<input type="text" id="search-startTime" class="form-control layer-date" placeholder="推送起始时间">
						</div>
						<div class="form-group">
							<input type="text" id="search-endTime" class="form-control layer-date" placeholder="推送结束时间">
						</div>
						<button type="button" class="btn btn-default" id="apilog-search-button">
							<span class="glyphicon glyphicon-search"></span>查询
						</button>
					</form>
				</div>
				<table class="table" id="apilog-table"></table>
		</div>
		
	</div>
</body>
</html>