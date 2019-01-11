<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>云平台-JS配置</title>
	<meta name="keywords" content="云平台-JS配置">
	<meta name="description" content="云平台-JS配置" />
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
	<script rootURL=${qc.rootPath} src="${qc.nginx}static/script/cloud/jsconfig.js"></script>
	<script type="text/javascript">
		$(function(){
			<shiro:hasPermission name="/js/query">
				// 初始化表格
				jsconfig.init();
				
				// 查询指定条件的JS列表（点击表格上方的'查询'按钮）
				$('#jscode-search-button').click(function() {
					$('#jscode-table').bootstrapTable("refresh");
				});
				
			</shiro:hasPermission>
			
			<shiro:hasPermission name="/js/create">
				// 点击'新增'按钮弹出编辑框
				$('#jscode-create-button').click(function() {
					jsconfig.createJscodeEvent();
				});
				
				// 在弹出的视图里点击「新增」按钮
				$('#jscode-create-submit').click(function() {
					jsconfig.createJscode();		 
				});
			</shiro:hasPermission>
			
			<shiro:hasPermission name="/js/delete">
				// 删除多个JS源码（点击表格上方的'删除'按钮）
				$('#jscode-multi_delete-button').click(function() {
					jsconfig.deleteMultiJscode();
				});
			</shiro:hasPermission>
			
			<shiro:hasPermission name="/js/update">
				// 在弹出的视图里点击「保存」按钮
				$('#jscode-edit-submit').click(function() {
					jsconfig.editJscode();
				});
			</shiro:hasPermission>
		});
	</script>
	
</head>

<body>
	<div class="container-fluid">
		<div class="row">
				<div id="toolbar">
					<form id="queryJscodeForm" class="form-inline">
						<shiro:hasPermission name="/js/query">
							<div class="form-group">
								<input type="text" class="form-control" id="search-codeId" placeholder="JS编号">
							</div>
							<div class="form-group">
								<input type="text" class="form-control" id="search-codeName" placeholder="JS名称">
							</div>
							<button type="button" class="btn btn-default" id="jscode-search-button">
								<span class="glyphicon glyphicon-search"></span>查询
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="/js/create">
							<button type="button" class="btn btn-default" id="jscode-create-button">
								<span class="glyphicon glyphicon-plus"></span>新增
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="/js/delete">
							<button type="button" class="btn btn-default" id="jscode-multi_delete-button">
								<span class="glyphicon glyphicon-remove"></span>删除
							</button>
						</shiro:hasPermission>
					</form>
				</div>
				<shiro:hasPermission name="/js/query">
					<table class="table" id="jscode-table"></table>
				</shiro:hasPermission>
		</div>
		
		<shiro:hasPermission name="/js/create">
			<!-- 弹窗：创建JS -->
			<div id="jscode-create-modal" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
							</button>
							<h4 class="modal-title">新增JS</h4>
						</div>
						<div class="modal-body">
							<form id="createJscodeForm" class="form-horizontal">
							    <div class="form-group">
							        <label class="col-sm-3 control-label">源码名称：</label>
							        <div class="col-sm-8">
							            <input type="text" id="create-codeName" name="codeName" class="form-control" placeholder="必填"> 
							            <span class="help-block m-b-none"></span>
							        </div>
							        <span class="col-sm-1"></span>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">是否默认：</label>
							        <div class="col-sm-8">
							            <div class="col-sm-9">
								            <label class="radio-inline" for="state-off"><input type="radio" checked="checked" value="1" id="state-off" name="create-isDefault" />默认</label>
								            <label class="radio-inline" for="state-on"><input type="radio" value="0" id="state-on" name="create-isDefault" />非默认</label>
								            <span class="help-block m-b-none"></span>
								        </div>
							            <span class="help-block m-b-none text-info"><small>默认模版只能配一个，若已存在默认模版并配置当前模版为默认，则取消原有的默认模版</small></span>
							        </div>
							        <span class="col-sm-1"></span>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">服务器域名/IP：</label>
							        <div class="col-sm-8">
							            <input type="text" id="create-serverIp" name="serverIp" class="form-control" placeholder="必填：(限20字符)"> 
							            <span class="help-block m-b-none"></span>
							        </div>
							        <span class="col-sm-1"></span>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">服务器端口：</label>
							        <div class="col-sm-8">
							            <input type="text" id="create-serverPort" name="serverPort" class="form-control" placeholder="选填：(限6数字)"> 
							            <span class="help-block m-b-none"></span>
							        </div>
							        <span class="col-sm-1"></span>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">备注：</label>
							        <div class="col-sm-8">
										<textarea id="create-remark" name="remark" class="form-control" rows="3" placeholder="选填：请输入备注说明"></textarea>
										<span class="help-block m-b-none"></span>
							        </div>
							        <span class="col-sm-1"></span>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">源码：</label>
							        <div class="col-sm-8">
										<textarea id="create-code" name="code" class="form-control" rows="10" placeholder="必填：请输入可执行的JS代码"></textarea>
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
						        <div class="modal-footer">
									<button type="button" class="btn btn-primary" id="jscode-create-submit">新增</button>
								</div>
						     </form>
						</div>
						
					</div>
				</div>
			</div>
		</shiro:hasPermission>
		
		<shiro:hasPermission name="/js/update">
			<!-- 弹窗：编辑JS -->
			<div id="jscode-edit-modal" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
							</button>
							<h4 class="modal-title">编辑JS</h4>
						</div>
						<div class="modal-body">
							<form id="editJscodeForm" class="form-horizontal">
							    <div class="form-group">
							        <label class="col-sm-3 control-label">源码名称：</label>
							        <div class="col-sm-8">
							        	<input type="hidden" id="edit-id"/>
							            <input type="text" id="edit-codeName" class="form-control" placeholder="必填"> 
							            <span class="help-block m-b-none"></span>
							        </div>
							        <span class="col-sm-1"></span>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">是否默认：</label>
							        <div class="col-sm-8">
							            <div class="col-sm-9">
								            <label class="radio-inline"><input type="radio" value="1" id="edit-isDefault-true" name="edit-isDefault" />默认</label>
								            <label class="radio-inline"><input type="radio" value="0" id="edit-isDefault-false" name="edit-isDefault" />非默认</label>
								            <span class="help-block m-b-none"></span>
								        </div>
							            <span class="help-block m-b-none text-info"><small>默认模版只能配一个，若已存在默认模版并配置当前模版为默认，则取消原有的默认模版</small></span>
							        </div>
							        <span class="col-sm-1"></span>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">服务器域名/IP：</label>
							        <div class="col-sm-8">
							            <input type="text" id="edit-serverIp" class="form-control" placeholder="必填：(限20字符)"> 
							            <span class="help-block m-b-none"></span>
							        </div>
							        <span class="col-sm-1"></span>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">服务器端口：</label>
							        <div class="col-sm-8">
							            <input type="text" id="edit-serverPort" class="form-control" placeholder="选填：(限6数字)"> 
							            <span class="help-block m-b-none"></span>
							        </div>
							        <span class="col-sm-1"></span>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">备注：</label>
							        <div class="col-sm-8">
										<textarea id="edit-remark" class="form-control" rows="3" placeholder="选填：请输入备注说明"></textarea>
										<span class="help-block m-b-none"></span>
							        </div>
							        <span class="col-sm-1"></span>
							    </div>
							    <div class="form-group">
							        <label class="col-sm-3 control-label">源码：</label>
							        <div class="col-sm-8">
										<textarea id="edit-code" class="form-control" rows="10" placeholder="必填：请输入可执行的JS代码"></textarea>
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
						        <div class="modal-footer">
									<button type="button" class="btn btn-primary" id="jscode-edit-submit">保存</button>
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