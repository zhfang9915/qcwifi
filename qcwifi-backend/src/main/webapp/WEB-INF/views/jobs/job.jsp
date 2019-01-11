<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>前辰运维-定时任务</title>
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
		<div class="row">
			<div id="toolbar">
				<form class="form-inline">
					<div class="form-group">
						<input type="text" class="form-control" id="search-id" placeholder="请输入任务ID">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" id="search-name" placeholder="请输入任务名称关键字">
					</div>
					<div class="form-group">
						<select class="form-control" id="search-currentStatus" placeholder="请选择任务状态">
							<option value="" selected="selected">全部</option>
							<option value="1">启动</option>
							<option value="0">停止</option>
						</select> 
					</div>
					<button type="button" class="btn btn-default" id="job-search">
						<span class="glyphicon glyphicon-search"></span>查询
					</button>
					<button type="button" class="btn btn-default" id="job-create" data-toggle="modal" data-target="#job-create-modal">
						<span class="glyphicon glyphicon-plus"></span>新建任务
					</button>
<!-- 					<button type="button" class="btn btn-default" id="job-delete"> -->
<!-- 						<span class="glyphicon glyphicon-remove"></span>删除 -->
<!-- 					</button> -->
				</form>
			</div>
			<table class="table" id="job-table"></table>
			    
		</div>
		
		<!-- 弹窗：新建任务 -->
		<div id="job-create-modal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">新建定时任务</h4>
					</div>
					<div class="modal-body">
						<form id="job-create-form" class="form-horizontal">
							<div class="form-group">
								<label for="create-name" class="col-sm-3 control-label">任务名称</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="create-name" placeholder="必填" />
								</div>
							</div>
							<div class="form-group">
								<label for="create-schedualTime" class="col-sm-3 control-label">cron</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="create-schedualTime" placeholder="必填" /> 
								</div>
							</div>
							<div class="form-group">
								<label for="create-currentStatus" class="col-sm-3 control-label">任务状态</label>
								<div class="col-sm-9">
								    <label class="radio-inline">
								        <input type="radio" checked="checked" value="0" name="create-currentStatus">停止</label>
									<label class="radio-inline">
								        <input type="radio" value="1" name="create-currentStatus">启动</label>
								</div>
							</div>
							<div class="form-group">
								<label for="create-className" class="col-sm-3 control-label">实例路径</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="create-className" placeholder="必填" /> 
								</div>
							</div>
							<div class="form-group">
								<label for="create-jobDesc" class="col-sm-3 control-label">任务描述</label>
								<div class="col-sm-9">
									<textarea class="form-control" rows="3" id="create-jobDesc" placeholder="任务描述"></textarea>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="job-create-submit">创建任务</button>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- 弹窗：设置crom -->
		<div id="job-edit-modal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">设置cron</h4>
					</div>
					<div class="modal-body">
						<form id="job-edit-form" class="form-horizontal">
							<input type="hidden" id="edit-id"/>
							<div class="form-group">
								<label for="edit-currentStatus" class="col-sm-1 control-label"></label>
								<div class="col-sm-11">
									<label class="radio-inline"><input type="radio" value="0/1 * * * * ?" name="edit-currentStatus" class="cron">每秒钟执行一次</label>
									<br>
								    <label class="radio-inline"><input type="radio" value="0 * * * * ? *" name="edit-currentStatus" class="cron">每分钟执行一次</label>
								    <br>
								    <label class="radio-inline"><input type="radio" value="0 0 * * * ? *" name="edit-currentStatus" class="cron">每小时执行一次</label>
								    <br>
								    <label class="radio-inline"><input type="radio" value="0 0 0 * * ? *" name="edit-currentStatus" class="cron">每天凌晨12点执行一次</label>
								    <br>
								    <label class="radio-inline"><input type="radio" value="0 0 0 ? * WED" name="edit-currentStatus" class="cron">每周的星期三凌晨12点执行一次</label>
								    <br>
								    <label class="radio-inline"><input type="radio" value="0 0 0 1 * ? *" name="edit-currentStatus" class="cron">每月的第一天凌晨12点执行一次</label>
								</div>
							</div>
							<div class="form-group">
								<label for="edit-currentStatus" class="col-sm-1 control-label"></label>
								<div class="col-sm-11">
									<label class="radio-inline">
										<input type="radio" value="0/1 * * * * ?" name="edit-currentStatus" id="self-control">自定义：
									</label>
									<input type="text" class="form-control" name="time" id="inp" disabled="disabled" placeholder="注意表达式格式"/>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="job-edit-submit">保存</button>
					</div>
				</div>
			</div>
		</div>
		
		
	</div>
	
	<script src="${qc.nginx }static/jquery-1.12.4.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/bootstrap-table.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
	<script type="text/javascript" src="${qc.nginx }static/bootstrap/js/bootstrap-table-export.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/tableExport.min.js"></script>
	<script  src="${qc.nginx }static/layer/layer.js"></script>
	<script rootURL=${qc.rootPath } src="${qc.nginx }static/script/jobs/job.js"></script>
</body>
</html>