<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>系统设置</title>
	<meta name="HandheldFriendly" content="True" />
	<link rel="shortcut icon" href="${qc.nginx }static/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="${qc.nginx }static/bootstrap/css/bootstrap.min.css">
	
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
		<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<table class="table">
				<tbody>
					<shiro:hasPermission name="/system/flushDB">
						<tr>
							<td>
								<h4>刷新 DB 缓存<small>此操作会刷新本平台所有命名空间的数据库缓存，谨慎操作！</small></h4>
							</td>
							<td>
								<button class="btn btn-default" onclick="system.flushDB();" aria-label="刷新DB缓存">
									<span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>
								</button>
							</td>
						</tr>
					</shiro:hasPermission>
				</tbody>
			</table>
		</div>
		
		
		
		
	</div>
	
	<script src="${qc.nginx }static/jquery-1.12.4.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/bootstrap.min.js"></script>
	<script  src="${qc.nginx }static/layer/layer.js"></script>
	<script rootURL=${qc.rootPath } src="${qc.nginx }static/script/system/system.js"></script>
</body>
</html>