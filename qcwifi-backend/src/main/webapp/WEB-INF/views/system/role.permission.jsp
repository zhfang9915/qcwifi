<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>角色授权</title>
	<meta name="HandheldFriendly" content="True" />
	<link rel="shortcut icon" href="${qc.nginx }static/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="${qc.nginx }static/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${qc.nginx }static/bootstrap/css/bootstrap-table.min.css">
	
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
		<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>
<body>
	<div class="container-fluid">
		<h3>当前角色 ： <small>${role.description }</small></h3>
		<hr/>
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-3">
				<table id="table_existPermission" class="grid-table"></table>
			</div>
			<div class="col-md-2 text-center">
				<button type="button" class="btn btn-default btn-lg" 
					style="margin-top: 170px;"
					onclick="rp.uncorrelationPermission('${role.id}');">
					<span class="glyphicon glyphicon-forward"></span>
				</button>
				<hr/>
				<button type="button" class="btn btn-default btn-lg" 
					style="margin-top: 10px;"
					onclick="rp.correlationPermission('${role.id}');">
					<span class="glyphicon glyphicon-backward"></span>
				</button>
			</div>
			<div class="col-md-3">
				<table id="table_optionalPermission" class="grid-table"></table>
			</div>
			<div class="col-md-2"></div>
		</div>
		
	</div>

	<script src="${qc.nginx }static/jquery-1.12.4.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/bootstrap-table.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
	<script  src="${qc.nginx }static/layer/layer.js"></script>
	<script rootURL=${qc.rootPath } src="${qc.nginx }static/script/system/role.permission.js"></script>
	<script type="text/javascript">
		$(function() {
			rp.existPermissionInit('${role.id}');
			rp.optionalPermissionInit('${role.id}');
			
		});
	</script>
</body>
</html>