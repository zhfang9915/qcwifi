<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>推广管理-广告主</title>
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
									<li><a href="#">今日</a></li>
									<li><a href="#">昨日</a></li>
									<li><a href="#">最近七日</a></li>
									<li><a href="#">最近30日</a></li>
									<li><a href="#">至<input type="text" class="layui-input datatime" id="date01"><i class="layui-icon"></i></a></li>
								</ul>
							</div>
						</div>
					</div>
					<div class="layui-label-totle">
						<a href="#" class="layui-btn layui-btn-newbtn"><i class="layui-icon">&#xe654;</i>新建推广内容</a>
						<div class="layui-form-select">
							<div class="layui-select-title" style="margin-top: 60px">
								<input type="text" placeholder="搜索广告主" value="" class="layui-input layui-search-right">
								<i class="layui-icon layui-searchbtn">&#xe615;</i>
							</div>
						</div>
					</div>

				</form>
			</div>

			<div class="layui-body-content">
				<div class="layui-row">
					<table class="layui-table" lay-skin="line" lay-size="lg">
						<thead>
							<tr>
								<th>时间</th>
								<th>展示量</th>
								<th>点击量</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>2017/12/26</td>
								<td>0</td>
								<td>0</td>
							</tr>
						</tbody>
					</table>
					<div class="pages">
						<a href="" class="prev"><span></span></a>
						<span class="page-info">1/3</span>
						<a href="" class="next"><span></span></a>
						<input type="text" class="pages-input" value="1">
						<button class="btn-jump">跳转</button>
					</div>
				</div>

			</div>
		</div>
		<!--footer-->
		<%@include file="../common/footer.jsp"%>
	</div>
	
<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script src="${qc.nginx }cloud/layui.js"></script>
<script src="${qc.nginx }cloud/layer/layer.js"></script>
<script rootURL=${qc.rootPath } staticURL=${qc.nginx } src="${qc.nginx }script/cloud/push/owner.js"></script>
</body>

</html>
