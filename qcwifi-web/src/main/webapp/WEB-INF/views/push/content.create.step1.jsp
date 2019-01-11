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
	<link href="${qc.nginx }cloud/css/ion.rangeSlider.css" rel="stylesheet">
    <link href="${qc.nginx }cloud/css/ion.rangeSlider.skinFlat.css" rel="stylesheet">
</head>

<body class="layout-body">
	<div class="layout-wrap">
		<div class="layout-header">
			<%@include file="../common/header.jsp"%>
			<%@include file="push.header.jsp"%>
		</div>
		<!--正文-->
		<div class="layout-main layout-lg-main layui-clear">

			<div class="layui-body-content">
				<div class="layui-row">
					<div class="layui-progressbar">
						<div class="progress-label progress-label-active">
							1.基本信息
						</div>
						<div class="progress-label">
							2.投放选择
						</div>
<!-- 						<div class="progress-label"> -->
<!-- 							3.定向设置 -->
<!-- 						</div> -->
						<div class="progress-label">
							3.确认提交
						</div>
					</div>
				</div>
				<div class="layui-content">

					<form class="layui-form select-ads" lay-filter="submit-content-form">
						<div class="layui-form-item">
							<label class="layui-form-label">投放广告主</label>
							<div class="layui-input-block">
								<select id="content_group" name="groupId" lay-verify="required">
			                      <option value="">请选择广告主</option>
			                      <c:if test="${!empty groups}">
			                      	<c:forEach var="g" items="${groups}">
								  		<option value="${g.id }">${g.name }</option>
									</c:forEach>
			                      </c:if>
			                    </select>
		                    </div>
							<span class="colorblue create">新建广告主</span>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">推广内容名称</label>
							<div class="layui-input-block">
								<input type="text" name="name" lay-verify="required" placeholder="长度不超过30个汉字或者30个英文字母" autocomplete="off" class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">每日展示限额</label>
							<div class="layui-input-block">
								<input type="text" name="dayLimit" lay-verify="limit" placeholder="推广内容每日最大显示次数，默认不限" autocomplete="off" class="layui-input">
								<span class="unit">次</span>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">总限额</label>
							<div class="layui-input-block">
								<input type="text" name="totalLimit" lay-verify="limit|limitConfim" placeholder="推广内容总显示次数，默认不限" autocomplete="off" class="layui-input">
								<span class="unit">次</span>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">推送权重</label>
							<div class="layui-input-block">
								<input type="hidden" id="weightHidden" value="1">
								<div id="weight_range"></div>
								<span class="unit">权重值越大则被推送的概率就越大，默认为1</span>
							</div>
						</div>
						<div class="next-btn btn-left">
							<div class="layui-form-item">
								<button class="layui-btn lay-submit" lay-submit lay-filter="submit-content-base">下一步</button>
								<button class="layui-btn layui-btn-primary" type="reset">取 消</button>


							</div>
						</div>
					</form>
				</div>

			</div>
		</div>
		<!--footer-->
		<%@include file="../common/footer.jsp"%>
	</div>
	
<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script src="${qc.nginx }cloud/layui.js"></script>
<script src="${qc.nginx }cloud/layer/layer.js"></script>
<script src="${qc.nginx }cloud/ion.rangeSlider.min.js"></script>
<script rootURL=${qc.rootPath } staticURL=${qc.nginx } src="${qc.nginx }script/cloud/push/content.create.step1.js"></script>
</body>

</html>
