<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>云平台-推送模版管理</title>
	<meta name="keywords" content="云平台-推送模版管理">
	<meta name="description" content="云平台-推送模版管理" />
	<meta name="HandheldFriendly" content="True" />
	<link rel="shortcut icon" href="${qc.nginx }static/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="${qc.nginx }static/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${qc.nginx }static/bootstrap/css/bootstrap-table.min.css">
	
	<script src="${qc.nginx }static/jquery-1.12.4.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/bootstrap-table.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
	<script  src="${qc.nginx }static/layer/layer.js"></script>
	<script rootURL=${qc.rootPath } src="${qc.nginx }static/script/cloud/extension.template.js"></script>
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
		<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	
	<script type="text/javascript">
		$(function(){
			
			<shiro:hasPermission name="/plugin/query">
				$('#extensionTemplate-table').bootstrapTable({
					url : rootURL + "extension/template/table", // 请求后台的URL（*）
					method : 'post', // 请求方式（*）
					queryParams : extensionTemplate.queryParams,// 传递参数（*）
					cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination : true, // 是否显示分页（*）
					sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
					pageSize : 10, // 每页的记录行数（*）
					pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
					minimumCountColumns : 2, // 最少允许的列数
					toolbar : "#toolbar",
					showColumns : true, // 是否显示所有的列
					showRefresh : true, // 是否显示刷新按钮
					showToggle : true, // 是否显示详细视图和列表视图的切换按钮
					columns : [ {
		                checkbox: true,
		                align: 'center',
		                valign: 'middle'
					}, {
						field : 'name',
						title : '模版名称'
					}, {
						field : 'available',
						align: 'center',
						title : '状态',
						formatter : function(value, row, index){
							if (value) {
								return '<span class="glyphicon glyphicon-ok text-success" aria-hidden="true"></span>';
							}else {
								return '<span class="glyphicon glyphicon-remove text-default" aria-hidden="true"></span>';
							}
						}
					}, {
						field : 'isMore',
						align: 'center',
						title : '是否多资源',
						formatter : function(value, row, index){
							if (value) {
								return '<span class="text-success">多资源</span>';
							}else {
								return '<span class="text-primary">单资源</span>';
							}
						}
					}, {
						field : 'createBy',
						title : '创建人'
					}, {
						field : 'createTime',
						align: 'center',
						title : '创建时间'
					}, {
						title : '操作',
						align: 'center',
						formatter : function(value, row, index){
							return [
					            '<shiro:hasPermission name="/extension/template/query"><button type="button" class="btn btn-link btn-xs" onClick="extensionTemplate.previewByPhone('+row.id+')">手机预览</button></shiro:hasPermission>',
					            '<shiro:hasPermission name="/extension/template/query"><button type="button" class="btn btn-link btn-xs" onClick="extensionTemplate.previewByPc('+row.id+')">PC预览</button></shiro:hasPermission>',
					            '<shiro:hasPermission name="/extension/template/update"><button type="button" class="btn btn-link btn-xs" onClick="extensionTemplate.openUpdateExtensionTemplateModal('+row.id+')">编辑</button></shiro:hasPermission>',
								'<shiro:hasPermission name="/extension/template/delete"><button type="button" class="btn btn-link btn-xs" onClick="extensionTemplate.deleteExtensionTemplate('+row.id+')">删除</button></shiro:hasPermission>'
					        ].join('');
						}
					} ]
				});
				
				//点击搜索
				$("#extensionTemplate-search").click(function(){
					$('#extensionTemplate-table').bootstrapTable("refresh");
				});
			</shiro:hasPermission>
			
			
			
		});
	</script>
</head>

<body>
	<div class="container-fluid">
		<div class="row">
				<div id="toolbar">
					<form class="form-inline">
						<shiro:hasPermission name="/extension/template/query">
							<div class="form-group">
								<input type="text" class="form-control" id="search-name" placeholder="名称">
							</div>
							<div class="form-group">
								<select class="form-control" id="search-isMore" placeholder="是否多资源">
									<option value="" selected="selected">全部</option>
									<option value="1">多资源</option>
									<option value="0">单资源</option>
								</select> 
							</div>
							<div class="form-group">
								<select class="form-control" id="search-available" placeholder="状态">
									<option value="" selected="selected">全部</option>
									<option value="1">启用</option>
									<option value="0">禁用</option>
								</select> 
							</div>
							<button type="button" class="btn btn-default" id="extensionTemplate-search">
								<span class="glyphicon glyphicon-search"></span>查询
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="/extension/template/create">
							<button type="button" class="btn btn-default" onClick="extensionTemplate.openCreateExtensionTemplateModal();">
								<span class="glyphicon glyphicon-plus"></span>创建
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="/extension/template/delete">
							<button type="button" class="btn btn-default" onClick="extensionTemplate.batchDeleteExtensionTemplate();">
								<span class="glyphicon glyphicon-remove"></span>删除
							</button>
						</shiro:hasPermission>
					</form>
				</div>
				<shiro:hasPermission name="/extension/template/query">
					<table class="table" id="extensionTemplate-table"></table>
				</shiro:hasPermission>
			    
		</div>
		
		<shiro:hasPermission name="/extension/template/create">
			<!-- 弹窗：创建推送模版 -->
			<div id="extensionTemplate-create-modal" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
							</button>
							<h4 class="modal-title">创建推送模版</h4>
						</div>
						<div class="modal-body">
							<form id="createExtensionTemplateForm" class="form-horizontal" method="post">
							    <div class="form-group">
							        <label class="col-sm-3 control-label">名称：</label>
							        <div class="col-sm-9">
							            <input type="text" id="create-name" class="form-control" placeholder="必填"> 
							            <span class="help-block m-b-none"></span>
							        </div>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">状态：</label>
							        <div class="col-sm-9">
							            <label class="radio-inline" for="available-off">
							                <input type="radio" checked="checked" value="0" id="available-off" name="create-available">禁用</label>
							            <label class="radio-inline" for="available-on">
							                <input type="radio" value="1" id="available-on" name="create-available">启用</label>
							            <span class="help-block m-b-none"></span>
							        </div>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">模版类型：</label>
							        <div class="col-sm-9">
							            <label class="radio-inline" for="isMore-off">
							                <input type="radio" checked="checked" value="0" id="isMore-off" name="create-isMore">单资源</label>
							            <label class="radio-inline" for="isMore-on">
							                <input type="radio" value="1" id="isMore-on" name="create-isMore">多资源</label>
							            <span class="help-block m-b-none"></span>
							        </div>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">模版主体：</label>
							        <div class="col-sm-9">
										<textarea id="create-temp" class="form-control" rows="3" placeholder="必填：请输入推送模版主体html源码"></textarea>
										<span class="help-block m-b-none"></span>
							        </div>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">资源主体：</label>
							        <div class="col-sm-9">
										<textarea id="create-tempFor" class="form-control" rows="3" placeholder="必填：请输入推送资源主体html源码"></textarea>
										<span class="help-block m-b-none"></span>
							        </div>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">所需JS脚本：</label>
							        <div class="col-sm-9">
										<textarea id="create-tempJs" class="form-control" rows="2" placeholder="选填：请输入模板依赖的脚本JS地址，若有多个，使用换行分割"></textarea>
										<span class="help-block m-b-none"></span>
							        </div>
							    </div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" onclick="extensionTemplate.createExtensionTemplate();">创建推送模版</button>
						</div>
					</div>
				</div>
			</div>
		</shiro:hasPermission>
		
		
		<shiro:hasPermission name="/extension/template/update">
			<!-- 弹窗：更新推送模版 -->
			<div id="extensionTemplate-update-modal" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
							</button>
							<h4 class="modal-title">更新推送模版</h4>
						</div>
						<div class="modal-body">
							<form id="updateExtensionTemplateForm" class="form-horizontal" method="post">
							    <div class="form-group">
							        <label class="col-sm-3 control-label">名称：</label>
							        <div class="col-sm-9">
							            <input type="hidden" id="update-id"> 
							            <input type="text" id="update-name" class="form-control" placeholder="必填"> 
							            <span class="help-block m-b-none"></span>
							        </div>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">状态：</label>
							        <div class="col-sm-9">
							            <label class="radio-inline" for="update-available-off">
							                <input type="radio" value="0" id="update-available-off" name="update-available">禁用</label>
							            <label class="radio-inline" for="update-available-on">
							                <input type="radio" value="1" id="update-available-on" name="update-available">启用</label>
							            <span class="help-block m-b-none"></span>
							        </div>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">模版类型：</label>
							        <div class="col-sm-9">
							            <label class="radio-inline" for="update-isMore-off">
							                <input type="radio" value="0" id="update-isMore-off" name="update-isMore">单资源</label>
							            <label class="radio-inline" for="update-isMore-on">
							                <input type="radio" value="1" id="update-isMore-on" name="update-isMore">多资源</label>
							            <span class="help-block m-b-none"></span>
							        </div>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">模版主体：</label>
							        <div class="col-sm-9">
										<textarea id="update-temp" class="form-control" rows="3" placeholder="必填：请输入推送模版主体html源码"></textarea>
										<span class="help-block m-b-none"></span>
							        </div>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">资源主体：</label>
							        <div class="col-sm-9">
										<textarea id="update-tempFor" class="form-control" rows="3" placeholder="必填：请输入推送资源主体html源码"></textarea>
										<span class="help-block m-b-none"></span>
							        </div>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">所需JS脚本：</label>
							        <div class="col-sm-9">
										<textarea id="update-tempJs" class="form-control" rows="2" placeholder="选填：请输入模板依赖的脚本JS地址，若有多个，使用换行分割"></textarea>
										<span class="help-block m-b-none"></span>
							        </div>
							    </div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" onclick="extensionTemplate.updateExtensionTemplate();">更新推送模版</button>
						</div>
					</div>
				</div>
			</div>
		</shiro:hasPermission>
		
		
		
		
	</div>
	
</body>
</html>