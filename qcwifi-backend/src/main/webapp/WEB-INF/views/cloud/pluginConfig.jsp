<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>云平台-插件管理</title>
	<meta name="keywords" content="云平台-插件管理">
	<meta name="description" content="云平台-插件管理" />
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
	<script  src="${qc.nginx }static/layer/layer.js"></script>
	<script rootURL=${qc.rootPath } src="${qc.nginx }static/script/cloud/plugin.js"></script>
	<script type="text/javascript">
		$(function(){
			<shiro:hasPermission name="/plugin/query">
				//表格初始化
				$('#plugin-table').bootstrapTable({
					url : rootURL + "plugin/table", // 请求后台的URL（*）
					method : 'post', // 请求方式（*）
					queryParams : plugin.queryParams,// 传递参数（*）
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
					showExport : true,
					columns : [ {
		                checkbox: true,
		                align: 'center',
		                valign: 'middle'
					}, {
						field : 'name',
						title : '文件名'
					}, {
						field : 'description',
						title : '描述'
					}, {
						field : 'version',
						title : '版本'
					}, {
						field : 'crosstool',
						title : '交叉编译版本'
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
						field : 'createBy',
						title : '创建人'
					}, {
						field : 'createTime',
						title : '创建时间'
					}, {
						title : '操作',
						align: 'center',
						formatter : function(value, row, index){
							var jsonstr = JSON.stringify(row);
							jsonstr = jsonstr.split("\"").join("'");
							return [
					            '<shiro:hasPermission name="/plugin/query"><button type="button" class="btn btn-link btn-xs" onClick="window.open(\''+row.downloadUrl+'\')">下载</button></shiro:hasPermission>',
					            '<shiro:hasPermission name="/plugin/query"><button type="button" class="btn btn-link btn-xs" onClick="plugin.detail('+jsonstr+')">详情</button></shiro:hasPermission>',
								'<shiro:hasPermission name="/plugin/delete"><button type="button" class="btn btn-link btn-xs" onClick="plugin.deletePlugin('+row.id+')">删除</button></shiro:hasPermission>'
					        ].join('');
						}
					} ]
				});
			
				//点击搜索
				$("#plugin-search").click(function(){
					$('#plugin-table').bootstrapTable("refresh");
				});
			</shiro:hasPermission>
			
			<shiro:hasPermission name="/plugin/create">
				$("#uploadBtn").attr('disabled', true);//创建插件按钮
			</shiro:hasPermission>
			
		});
	
	</script>
</head>

<body>
	<div class="container-fluid">
		<div class="row">
				<div id="toolbar">
					<form class="form-inline">
						<shiro:hasPermission name="/plugin/query">
							<div class="form-group">
								<input type="text" class="form-control" id="search-version" placeholder="插件版本">
							</div>
							<div class="form-group">
								<input type="text" class="form-control" id="search-crosstool" placeholder="交叉编译版本">
							</div>
							<div class="form-group">
								<select class="form-control" id="search-available" placeholder="状态">
									<option value="" selected="selected">全部</option>
									<option value="1">启用</option>
									<option value="0">禁用</option>
								</select> 
							</div>
							<button type="button" class="btn btn-default" id="plugin-search">
								<span class="glyphicon glyphicon-search"></span>查询
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="/plugin/create">
							<button type="button" class="btn btn-default" onClick="plugin.createPlugin();">
								<span class="glyphicon glyphicon-plus"></span>创建
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="/plugin/delete">
							<button type="button" class="btn btn-default" onClick="plugin.batchDeletePlugin();">
								<span class="glyphicon glyphicon-remove"></span>删除
							</button>
						</shiro:hasPermission>
					</form>
				</div>
				<shiro:hasPermission name="/plugin/query">
					<table class="table" id="plugin-table"></table>
				</shiro:hasPermission>
			    
		</div>
		
		<shiro:hasPermission name="/plugin/create">
			<!-- 弹窗：创建插件信息 -->
			<div id="plugin-create-modal" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
							</button>
							<h4 class="modal-title">创建插件</h4>
						</div>
						<div class="modal-body">
							<form id="createPluginForm" class="form-horizontal" method="post" enctype="multipart/form-data">
								<div class="form-group">
							        <label class="col-sm-3 control-label">选择插件：</label>
							        <div class="col-sm-8">
							            <input type="file" name="plugin_file" class="form-control" id="pluginFile" onchange="plugin.changeFile();">
							            <span class="help-block m-b-none"></span>
							        </div>
							        <span class="col-sm-1"></span>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">插件描述：</label>
							        <div class="col-sm-8">
							            <input type="text" name="description" class="form-control" placeholder="必填"> 
							            <span class="help-block m-b-none"></span>
							        </div>
							        <span class="col-sm-1"></span>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">状态：</label>
							        <div class="col-sm-8">
							            <div class="col-sm-9">
								            <label class="radio-inline" for="state-off">
								                <input type="radio" checked="checked" value="0" id="state-off" name="available">禁用</label>
								            <label class="radio-inline" for="state-on">
								                <input type="radio" value="1" id="state-on" name="available">启用</label>
								            <span class="help-block m-b-none"></span>
								        </div>
							            <span class="help-block m-b-none"></span>
							        </div>
							        <span class="col-sm-1"></span>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">备注：</label>
							        <div class="col-sm-8">
										<textarea name="remark" class="form-control" rows="3" placeholder="选填：请输入备注说明"></textarea>
										<span class="help-block m-b-none"></span>
							        </div>
							        <span class="col-sm-1"></span>
							    </div>
							    <div class="progress progress-striped active" style="display: none">
						            <div id="progressBar" class="progress-bar progress-bar-info"
						                role="progressbar" aria-valuemin="0" aria-valuenow="0"
						                aria-valuemax="100" style="width: 0%">
						            </div>
						        </div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" id="uploadBtn" onclick="plugin.submitPlugin();">创建插件</button>
						</div>
					</div>
				</div>
			</div>
		</shiro:hasPermission>
		
		
		<shiro:hasPermission name="/plugin/query">
			<!-- 弹窗：详情插件信息 -->
			<div id="plugin-detail-modal" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
							</button>
							<h4 class="modal-title">插件详情</h4>
						</div>
						<div class="modal-body">
							<form class="form-horizontal">
								<div class="form-group">
								   <label class="col-sm-2 control-label">名称</label>
								   <div class="col-sm-10">
								     <p class="form-control-static" id="detail-name"></p>
								   </div>
								</div>
								<div class="form-group">
								   <label class="col-sm-2 control-label">描述</label>
								   <div class="col-sm-10">
								     <p class="form-control-static" id="detail-description"></p>
								   </div>
								</div>
								<div class="form-group">
								   <label class="col-sm-2 control-label">版本</label>
								   <div class="col-sm-10">
								     <p class="form-control-static" id="detail-version"></p>
								   </div>
								</div>
								<div class="form-group">
								   <label class="col-sm-2 control-label">交叉版本</label>
								   <div class="col-sm-10">
								     <p class="form-control-static" id="detail-crosstool"></p>
								   </div>
								</div>
								<div class="form-group">
								   <label class="col-sm-2 control-label">资源位置</label>
								   <div class="col-sm-10">
								     <p class="form-control-static" id="detail-respath"></p>
								   </div>
								</div>
								<div class="form-group">
								   <label class="col-sm-2 control-label">状态</label>
								   <div class="col-sm-10">
								     <p class="form-control-static" id="detail-available"></p>
								   </div>
								</div>
								<div class="form-group">
								   <label class="col-sm-2 control-label">插件MD5</label>
								   <div class="col-sm-10">
								     <p class="form-control-static" id="detail-md5"></p>
								   </div>
								</div>
								<div class="form-group">
								   <label class="col-sm-2 control-label">创建人</label>
								   <div class="col-sm-10">
								     <p class="form-control-static" id="detail-createby"></p>
								   </div>
								</div>
								<div class="form-group">
								   <label class="col-sm-2 control-label">创建时间</label>
								   <div class="col-sm-10">
								     <p class="form-control-static" id="detail-createtime"></p>
								   </div>
								</div>
								<div class="form-group">
								   <label class="col-sm-2 control-label">备注</label>
								   <div class="col-sm-10">
								     <p class="form-control-static" id="detail-remark"></p>
								   </div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</shiro:hasPermission>
		
	</div>
	
	
</body>
</html>