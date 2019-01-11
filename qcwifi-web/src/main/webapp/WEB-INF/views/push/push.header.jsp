<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="layout-navbar">
	<div class="layout-nav layui-clear">
		<ul class="nav">
			<li <c:if test="${currentHeader == 'report'}"> class="active" </c:if>><a href="${qc.rootPath }push/report/index">报表统计</a></li>
			<li <c:if test="${currentHeader == 'owner'}"> class="active" </c:if>><a href="${qc.rootPath }push/owner/index">广告主</a></li>
			<li <c:if test="${currentHeader == 'content'}"> class="active" </c:if>><a href="${qc.rootPath }push/content/index">推广内容</a></li>
		</ul>
		<a href="${qc.rootPath }push/content/create/step1" class="layui-btn layui-btn-big btn-creat">新建推广内容</a>
	</div>
</div>