<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="layout-nav layui-clear">
    <ul class="nav">
        <li <c:if test="${currentHeader == 'list'}"> class="active" </c:if>><a href="${qc.rootPath }shop/index">商铺列表</a></li>
<%--         <li <c:if test="${currentHeader == 'upgrade'}"> class="active" </c:if>><a href="10.24商铺升级.html">商铺升级</a></li> --%>
        <li <c:if test="${currentHeader == 'sub'}"> class="active" </c:if>><a href="${qc.rootPath }shop/sub/index">连锁管理</a></li>
<%--         <li <c:if test="${currentHeader == 'statical'}"> class="active" </c:if>><a href="数据统计-商铺信息.html">数据统计</a></li> --%>
<%--         <li <c:if test="${currentHeader == 'bupdate'}"> class="active" </c:if>><a href="###">批量修改</a></li> --%>
        <li <c:if test="${currentHeader == 'sms'}"> class="active" </c:if>><a href="${qc.rootPath }shop/sms/recharge">短信业务</a></li>
<%--         <li <c:if test="${currentHeader == 'wechat'}"> class="active" </c:if>><a href="微信授权.html">微信授权</a></li> --%>
        <li <c:if test="${currentHeader == 'map'}"> class="active" </c:if>><a href="${qc.rootPath }shop/map">商铺地图</a></li>
    </ul>
    <a href="${qc.rootPath }shop/new" class="layui-btn layui-btn-big btn-creat">创建商铺</a>
</div>