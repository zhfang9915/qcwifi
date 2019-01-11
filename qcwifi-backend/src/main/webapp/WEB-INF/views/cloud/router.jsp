<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>云平台-路由器设备管理</title>
	<meta name="keywords" content="云平台-插件管理">
	<meta name="description" content="云平台-插件管理" />
	<meta name="HandheldFriendly" content="True" />
	<link rel="shortcut icon" href="${qc.nginx }static/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="${qc.nginx }static/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${qc.nginx }static/bootstrap/css/bootstrap-table.min.css">
	
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
	<script  src="${qc.nginx }static/layer/layer.js"></script>
	<script rootURL=${qc.rootPath } src="${qc.nginx }static/script/cloud/router.js"></script>
</head>

<body>
	<div class="container-fluid">
		<div class="row">
				<div id="toolbar">
					<form class="form-inline">
						<shiro:hasPermission name="/router/query">
							<div class="form-group">
								<input type="text" class="form-control" id="search-version" name="version" placeholder="固件版本">
							</div>
							<div class="form-group">
								<input type="text" class="form-control" id="search-crosstool" name="crosstool" placeholder="GCC版本">
							</div>
							<div class="form-group">
								<input type="text" class="form-control" id="search-description" name="description" placeholder="描述信息">
							</div>
							<div class="form-group">
								<select class="form-control" id="search-available" name="available" placeholder="状态">
									<option value="" selected="selected">全部</option>
									<option value="1">启用</option>
									<option value="0">禁用</option>
								</select> 
							</div>
							<button type="button" class="btn btn-default" id="router-search">
								<span class="glyphicon glyphicon-search"></span>查询
							</button>
						</shiro:hasPermission>
					</form>
				</div>
				<table class="table" id="router-table"></table>
		</div>
		
	
	</div>
</body>
</html>