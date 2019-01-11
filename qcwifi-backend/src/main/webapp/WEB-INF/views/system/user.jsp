<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>会员列表</title>
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
						<input type="text" class="form-control" id="search-username" placeholder="请输入会员名">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" id="search-nickname" placeholder="请输入会员昵称">
					</div>
					<div class="form-group">
						<select class="form-control" id="search-locked" placeholder="请选择会员状态">
							<option value="" selected="selected">全部</option>
							<option value="0">启用</option>
							<option value="1">冻结</option>
						</select> 
					</div>
					<button type="button" class="btn btn-default" id="user-search">
						<span class="glyphicon glyphicon-search"></span>查询
					</button>
					<button type="button" class="btn btn-default" id="user-create">
						<span class="glyphicon glyphicon-plus"></span>创建会员
					</button>
					<button type="button" class="btn btn-default" id="user-delete">
						<span class="glyphicon glyphicon-remove"></span>删除
					</button>
				</form>
			</div>
			<table class="table" id="user-table"></table>
			    
		</div>
		
		<!-- 弹窗：创建会员 -->
		<div id="user-create-modal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">创建会员</h4>
					</div>
					<div class="modal-body">
						<form id="user-create-form" class="form-horizontal">
							<div class="form-group">
								<label for="create-username" class="col-sm-3 control-label">会员名</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="create-username" placeholder="必填：由数字、字母、基本符号组成" />
								</div>
							</div>
							<div class="form-group">
								<label for="create-nickname" class="col-sm-3 control-label">会员昵称</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" id="create-nickname" placeholder="必填" /> 
								</div>
							</div>
							<div class="form-group">
								<label for="create-password" class="col-sm-3 control-label">登录密码</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="create-password" placeholder="必填：至少8位" /> 
								</div>
							</div>
							<div class="form-group">
								<label for="create-passwordr" class="col-sm-3 control-label">重复密码</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="create-passwordr" placeholder="必填：至少8位" /> 
								</div>
							</div>
							<div class="form-group">
								<label for="create-locked" class="col-sm-3 control-label">状态</label>
								<div class="col-sm-9">
									<label class="radio-inline">
								        <input type="radio" checked="checked" value="false" name="create-locked">启用</label>
								    <label class="radio-inline">
								        <input type="radio" value="true" name="create-locked">禁用</label>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="user-create-submit">创建会员</button>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- 弹窗：更新会员 -->
		<div id="user-edit-modal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">更新会员账号信息</h4>
					</div>
					<div class="modal-body">
						<form id="user-edit-form" class="form-horizontal">
							<div class="form-group">
								<label for="edit-nickname" class="col-sm-3 control-label">会员昵称</label>
								<div class="col-sm-9">
									<input type="hidden" id="edit-id"/>
									<input type="text" class="form-control" id="edit-nickname" placeholder="必填" />
								</div>
							</div>
							<div class="form-group">
								<label for="edit-locked" class="col-sm-3 control-label">状态</label>
								<div class="col-sm-9">
									<label class="radio-inline">
								        <input type="radio" value="true" name="edit-locked" id="edit-locked-true">启用</label>
								    <label class="radio-inline">
								        <input type="radio" value="false" name="edit-locked" id="edit-locked-false">禁用</label>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="user-edit-submit">保存</button>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- 弹窗：修改密码 -->
		<div id="user-changepwd-modal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">修改密码</h4>
					</div>
					<div class="modal-body">
						<form id="user-changepwd-form" class="form-horizontal">
							<div class="form-group">
								<label for="change-old-pwd" class="col-sm-3 control-label">原密码</label>
								<div class="col-sm-9">
									<input type="hidden" id="change-id"/>
									<input type="password" class="form-control" id="change-old-pwd" placeholder="必填：至少8位" />
								</div>
							</div>
							<div class="form-group">
								<label for="change-new-pwd" class="col-sm-3 control-label">新密码</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="change-new-pwd" placeholder="必填：至少8位" /> 
								</div>
							</div>
							<div class="form-group">
								<label for="change-new-pwdr" class="col-sm-3 control-label">重复新密码</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="change-new-pwdr" placeholder="必填：至少8位" /> 
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="user-changepwd-submit">确认修改</button>
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
	<script rootURL=${qc.rootPath } src="${qc.nginx }static/script/system/user.js"></script>
</body>
</html>