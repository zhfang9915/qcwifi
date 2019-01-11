<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>角色列表</title>
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
						<div class="input-group">
							<input type="text" class="form-control" id="search-text" placeholder="请输入标识或描述搜索">
							<span class="input-group-btn">
								<button class="btn btn-default" type="button" id="role-search">查询</button>
							</span>
						</div>
						<button type="button" class="btn btn-default" id="role-create">
							<span class="glyphicon glyphicon-plus"></span>创建角色
						</button>
						<button type="button" class="btn btn-default" id="role-delete">
							<span class="glyphicon glyphicon-remove"></span>删除
						</button>
					</form>
				</div>
				<table class="table" id="role-table"></table>
			    
		</div>
		
		<!-- 弹窗：创建角色 -->
		<div id="role-create-modal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">创建角色</h4>
					</div>
					<div class="modal-body">
						<form id="role-create-form" class="form-horizontal">
							<div class="form-group">
								<label for="create-role" class="col-sm-3 control-label">角色标识</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="create-role" placeholder="必填！由数字和字母组成" />
								</div>
							</div>
							<div class="form-group has-feedback">
								<label for="create-description" class="col-sm-3 control-label">角色描述</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="create-description" placeholder="必填" /> 
								</div>
							</div>
							<div class="form-group">
								<label for="create-available" class="col-sm-3 control-label">状态</label>
								<div class="col-sm-9">
									<label class="radio-inline">
								        <input type="radio" checked="checked" value="true" name="create-available">启用</label>
								    <label class="radio-inline">
								        <input type="radio" value="false" name="create-available">禁用</label>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="role-create-submit">创建</button>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- 弹窗：更新角色 -->
		<div id="role-edit-modal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">编辑角色</h4>
					</div>
					<div class="modal-body">
						<form id="role-edit-form" class="form-horizontal">
							<div class="form-group">
								<label for="edit-role" class="col-sm-3 control-label">角色标识</label>
								<div class="col-sm-9">
									<input type="hidden" id="edit-id"/>
									<input type="text" class="form-control" id="edit-role" placeholder="必填！由数字和字母组成" />
								</div>
							</div>
							<div class="form-group has-feedback">
								<label for="edit-description" class="col-sm-3 control-label">角色描述</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="edit-description" placeholder="必填" /> 
								</div>
							</div>
							<div class="form-group">
								<label for="edit-available" class="col-sm-3 control-label">状态</label>
								<div class="col-sm-9">
									<label class="radio-inline">
								        <input type="radio" value="true" name="edit-available" id="edit-available-true">启用</label>
								    <label class="radio-inline">
								        <input type="radio" value="false" name="edit-available" id="edit-available-false">禁用</label>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="role-edit-submit">保存</button>
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
	<script rootURL=${qc.rootPath } src="${qc.nginx }static/script/system/role.js"></script>
</body>
</html>