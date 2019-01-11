<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>推广内容详情</title>
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
			<div class="layout-in-main" style="min-height: 260px;">
				<div class="in-toptit">
					<button class="layui-btn layui-btn-lie" style="margin-left: 10px;background: cadetblue;" id="edit-btn">编辑</button>
					<button class="layui-btn layui-btn-lie" style="background: crimson;" id="delete-btn">删除</button>
                	<div class="in-text" style="font-size: 15px;"><a href="${qc.rootPath }push/content/index" style="color: blue;">推广内容列表</a> > 详情</div>
                </div>
                <input type="hidden" id="contentId" value="${content.id }"/>
                <div class="in-single" style="margin-top: 35px;">
                	<table class="layui-table" lay-size="lg">
					  <colgroup>
					    <col width="150">
					    <col width="300">
					    <col width="150">
					    <col>
					  </colgroup>
					  <tbody>
					    <tr>
					      <td>推广内容名称</td>
					      <td>${content.name }</td>
					      <td>投放推广位</td>
					      <td>${content.choose.templateName }</td>
					    </tr>
					    <tr>
					      <td>当前状态</td>
					      <td>
					      	<c:if test="${content.status == true }">
					      		<span style="color: green;font-weight: bolder;">投放中</span>
					      	</c:if>
					      	<c:if test="${content.status != true }">
					      		<span style="color: red;font-weight: bolder;">暂停中</span>
					      	</c:if>
					      </td>
					      <td>限额</td>
					      <td>${content.dayLimit }次/每日(总限额${content.totalLimit }次)</td>
					    </tr>
					    <tr>
					      <td>推送权重</td>
					      <td>${content.weight }</td>
					      <td>所属广告主</td>
					      <td>${content.group.name }</td>
					    </tr>
					  </tbody>
					</table>
                </div>
            </div>

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

				</form>
			</div>
			<div class="layui-body-content">

				<div class="layui-row">
					<div class="layui-listitme layui-bg-z-blue">
						<h2>展示量</h2>
						<p>0</p>
					</div>
					<div class="layui-listitme layui-bg-z-green">
						<h2>点击量</h2>
						<p>0</p>
					</div>
				</div>
				<div class="layui-row">
					<div class="layui-label-t">
						<span>关键指标趋势图</span>
						<form class="layui-form layui-ftotle" action="">
							<div class="layui-form-item">
								<div class="layui-input-inline">
									<select name="city" lay-verify="required">
									<option value=""></option>
									<option value="0">展示量</option>
									<option value="1">点击量</option>
									<option value="2">地域分布</option>
								  </select>
								</div>
							</div>
						</form>
					</div>

					<div class="layui-dataarea">
						<div class="layui-databox">
							暂无数据
						</div>
					</div>


					<div class="layui-label-t">
						<span>关键指标趋势图</span>
						<button class="btn layui-btn layui-btn-lan">导出excel</button>
					</div>


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
<script rootURL=${qc.rootPath } staticURL=${qc.nginx } src="${qc.nginx }script/cloud/push/content.detail.js"></script>
</body>

</html>
