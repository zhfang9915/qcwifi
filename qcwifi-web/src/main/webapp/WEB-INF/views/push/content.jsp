<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>推广管理-推广内容</title>
	<link rel="shortcut icon" href="${qc.nginx }favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${qc.nginx }cloud/css/layui.css">
    <link rel="stylesheet" href="${qc.nginx }cloud/css/css.css">
	<link rel="stylesheet" href="${qc.nginx }cloud/css/css2.css">
</head>

<body class="layout-body">
	<div class="layout-wrap">
		<div class="layout-header">
			<%@include file="../common/header.jsp"%>
			<%@include file="push.header.jsp"%>
		</div>
		<!--正文-->
		<div class="layout-main layout-lg-main layui-clear">

			<div class="layui-search-label">
				<form class="layui-form" action="">

					<div class="layui-label-totle">
						<div class="layui-form-item">
							<p class="layui-subtitle">统计时间</p>
							<div class="layui-time">
								<ul>
									<li><a href="javascript:;" class="active" data-value="0">今日</a></li>
									<li><a href="javascript:;" data-value="1">昨日</a></li>
									<li><a href="javascript:;" data-value="7">最近七日</a></li>
									<li><a href="javascript:;" data-value="30">最近30日</a></li>
									<li><a href="javascript:;" data-value="-1">至<input type="text" class="layui-input datatime" id="date01"><i class="layui-icon"></i></a></li>
								</ul>
							</div>
						</div>
					</div>
					<div class="layui-label-totle">
						<div class="layui-form-select">
							<div class="layui-select-title" style="margin-top: 12px">
								<input type="text" name="content-name" placeholder="搜索广告内容" class="layui-input layui-search-right" style="margin-right: 63px;">
								<i class="layui-icon layui-searchbtn" id="content-search">&#xe615;</i>
							</div>
						</div>
					</div>

				</form>
			</div>

			<div class="layui-body-content">
				<div class="layui-row">
					<table id="content-table" class="layui-table" lay-filter="content-table"></table>
				</div>

			</div>
		</div>
		<!--footer-->
		<%@include file="../common/footer.jsp"%>
	</div>

<script type="text/html" id="content-status">
  <input type="checkbox" name="status" value="{{d.status}}" lay-skin="switch" lay-text="上架|下架" lay-filter="content-status-switch" {{ d.status == true ? 'checked' : '' }}>
</script>
<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script src="${qc.nginx }cloud/layui.js"></script>
<script src="${qc.nginx }cloud/layer/layer.js"></script>
<script rootURL=${qc.rootPath } staticURL=${qc.nginx } src="${qc.nginx }script/cloud/push/content.js"></script>
</body>

</html>
