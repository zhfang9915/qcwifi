<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>定时任务管理</title>
<link rel="stylesheet" href="static/css/bootstrap.min.css">
<link href="static/css/custum.css" rel="stylesheet" />
<link href="static/fontmaster/css/font-awesome.min.css" rel="stylesheet"
	data-role="global" />
<link rel="shortcut icon" href="static/images/favicon.ico"
	type="image/x-icon" />
<link rel="stylesheet" href="static/css/jqgrid/ui.jqgrid.css">
<style>
#table_list_1 .btn{padding:1px 3px}
.ui-jqgrid .ui-jqgrid-htable thead th div{padding:7px;}
</style>
</head>
<body>
	<div class="menu-top">
		<div class="quick-btn cont-btn-area pull-right">
			<button type="button" class="btn btn-sm" id="add" data-toggle="modal"
				data-target="#myModal">
				<i class="fa fa-plus-circle blue"></i>&nbsp;新增
			</button>
<!-- 			<button type="button" class="btn btn-sm"> -->
<!-- 				<i class="fa fa-search green"></i>&nbsp;搜索 -->
<!-- 			</button> -->
<!-- 			<button type="button" class="btn btn-sm" id="delete"> -->
<!-- 				<i class="fa fa-trash-o"></i>&nbsp;删除 -->
<!-- 			</button> -->
		</div>
	</div>

	<div class="order-table">
		<div class="jqGrid_wrapper" style="width: 100%">
			<table id="table_list_1" class="table-condensed">
			</table>
			 <div id="pager_list_1"></div>
		</div>

	</div>

	<!-- 模态框 修改 -->
	<div class="modal fade" id="myModal3" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">任务设置</h4>
				</div>
				<div class="modal-body">
					<div class="list_dec timelist">
						<ul>
							<!-- s m h d m w(?) y(?) -->
							<li><input type="radio" name="time" value="0/1 * * * * ?"
								class="time" /><span>每秒钟执行一次</span></li>
							<li><input type="radio" name="time" value="0 * * * * ? *"
								class="time" /><span>每分钟执行一次</span></li>
							<li><input type="radio" name="time" value="0 0 * * * ? *"
								class="time" /><span>每小时执行一次</span></li>
							<li><input type="radio" name="time" value="0 0 0 * * ? *"
								class="time" /><span>每天凌晨12点执行一次</span></li>
							<li><input type="radio" name="time" value="0 0 0 ? * WED"
								class="time" /><span>每周的星期三凌晨12点执行一次</span></li>
							<li><input type="radio" name="time" value="0 0 0 1 * ? *"
								class="time" /><span>每月的第一天凌晨12点执行一次</span></li>
							<li><hr /></li>
							<li><input type="radio" name="time" value=""
								id="self-control" /><span style="width:auto">自定义：</span><input type="text"
								name="time" value="" id="inp" disabled="disabled"
								style="margin-top:5px;" /><font color="red"
								size="2" style="margin:5px 0 0 5px;">注意表达式格式</font></li>
						</ul>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateSave">设置</button>
				</div>
			</div>
		</div>
	</div>


	<!--新增-->
	<div class="modal fade in" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">新增定时任务</h4>
				</div>
				<div class="modal-body">
					<div class="list_dec" id="add_modal">
						<ul>
							<li><span>任务名称</span> <input required="required" placeholder="必填"
								type="text"  id="fName"> <a class="red" id="c_fName"></a></li>

							<li><span>时间间隔</span> <input required="required" placeholder="必填"
								type="text" id="fSchedualTime"> <a class="red"></a></li>
							<li><span>状态</span> <select style="width: 200px; height: 25px"
							 id="fCurrentStatus">
									<option value="1">开启</option>
									<option value="0">停止</option>
							</select></li>
							<li><span>接口别名</span> <input required="required" placeholder="必填"
								type="text" id="fClassName"><a class="red"></a></li>
							<li><span>任务描述</span> <input required="required" placeholder="必填"
								type="text" id="fJobDesc"><a class="red"></a></li>
						</ul>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="save">保存</button>
				</div>
			</div>
		</div>
	</div>
	<script src="static/js/jquery-1.9.1.js"></script>
	<script
		src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="static/js/top_menu.js"></script>
	<script src="static/js/contabs.min.js"></script>
	<!-- 表格jqgrid -->
	<script src="static/js/jqgrid/jquery.jqGrid.minffe4.js"></script>
	<script src="static/js/jqgrid/grid.locale-cnffe4.js"></script>
	<script src="static/js/date/laydate.js"></script>
	<script src="static/js/jobs/jobs.js"></script>
	<script src="static/js/layer/layer.js"></script>
</body>
</html>