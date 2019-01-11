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

			<div class="layui-body-content">
				<div class="layui-row">
					<div class="layui-progressbar">
						<div class="progress-label">
							1.基本信息
						</div>
						<div class="progress-label">
							2.投放选择
						</div>
<!-- 						<div class="progress-label"> -->
<!-- 							3.定向设置 -->
<!-- 						</div> -->
						<div class="progress-label progress-label-active">
							3.确认提交
						</div>
					</div>
				</div>
				<div class="layui-content">

					<form class="layui-form information" action="">
						<div class="lay-title">
							<p>基本信息</p>
							<input type="hidden" id="contentId" value="${content.id }">
							<hr class="layui-bg-gray">
						</div>
						<div class="layui-form-item half-margin-bottom">
							<label class="layui-form-label">广告主</label>
							<div class="layui-input-block">
								<span>${group.name }</span>
							</div>
						</div>
						<div class="layui-form-item half-margin-bottom">
							<label class="layui-form-label">推广内容名称</label>
							<div class="layui-input-block">
								<span>${content.name }</span>
							</div>
						</div>
						<div class="layui-form-item half-margin-bottom">
							<label class="layui-form-label">每日展示限额</label>
							<div class="layui-input-block">
								<c:if test="${empty content.dayLimit}">
									<span>不限</span>
								</c:if>
								<c:if test="${!empty content.dayLimit}">
									<span>${content.dayLimit }次</span>
								</c:if>
							</div>
						</div>
						<div class="layui-form-item half-margin-bottom">
							<label class="layui-form-label">总限额</label>
							<div class="layui-input-block">
								<c:if test="${empty content.totalLimit }">
									<span>不限</span>
								</c:if>
								<c:if test="${!empty content.totalLimit }">
									<span>${content.totalLimit }次</span>
								</c:if>
							</div>
						</div>
						<div class="layui-form-item half-margin-bottom">
							<label class="layui-form-label">推送权重</label>
							<div class="layui-input-block">
								<span>${content.weight }</span>
							</div>
						</div>

						<div class="lay-title">
							<p>投放位置</p>
							<hr class="layui-bg-gray">
						</div>
						<div class="layui-form-item half-margin-bottom">
							<label class="layui-form-label">投放选择</label>
							<div class="layui-input-block">
								<c:if test="${content.choose.templateId=='T10001' }">
									<span>登陆中海报</span>
								</c:if>
								<c:if test="${content.choose.templateId=='T10002' }">
									<span>成功页推广</span>
								</c:if>
								<c:if test="${content.choose.templateId=='T10003' }">
									<span>底部推广条</span>
								</c:if>
							</div>
						</div>
<!-- 						<div class="layui-form-item half-margin-bottom"> -->
<!-- 							<label class="layui-form-label">投放日期</label> -->
<!-- 							<div class="layui-input-block"> -->
<!-- 								<span>不限</span> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="layui-form-item half-margin-bottom"> -->
<!-- 							<label class="layui-form-label">投放区域</label> -->
<!-- 							<div class="layui-input-block"> -->
<!-- 								<span>不限</span> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="layui-form-item half-margin-bottom"> -->
<!-- 							<label class="layui-form-label">指定行业</label> -->
<!-- 							<div class="layui-input-block"> -->
<!-- 								<span>不限</span> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="layui-form-item half-margin-bottom"> -->
<!-- 							<label class="layui-form-label">指定商铺</label> -->
<!-- 							<div class="layui-input-block"> -->
<!-- 								<span>不限</span> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="layui-form-item half-margin-bottom"> -->
<!-- 							<label class="layui-form-label">指定渠道</label> -->
<!-- 							<div class="layui-input-block"> -->
<!-- 								<span>不限</span> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="layui-form-item half-margin-bottom"> -->
<!-- 							<label class="layui-form-label">用户终端</label> -->
<!-- 							<div class="layui-input-block"> -->
<!-- 								<span>不限</span> -->
<!-- 							</div> -->
<!-- 						</div> -->
						<div class="next-btn">
							<div class="layui-form-item">
								<a class="layui-btn layui-btn-primary" href="${qc.rootPath }push/content/update/${content.id }/step2">上一步</a>
								<button class="layui-btn lay-submit" lay-submit lay-filter="submit-content">提 交</button>

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
<script rootURL=${qc.rootPath } staticURL=${qc.nginx } src="${qc.nginx }script/cloud/push/content.update.step4.js"></script>
</body>

</html>
