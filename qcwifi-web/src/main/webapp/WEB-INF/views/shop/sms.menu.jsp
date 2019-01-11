<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="layout-side">
    <ul class="side-nav">
        <li <c:if test="${currentPage == 'recharge'}"> class="active" </c:if>><a href="${qc.rootPath }shop/sms/recharge">短信充值</a></li>
        <li <c:if test="${currentPage == 'history'}"> class="active" </c:if>><a href="${qc.rootPath }shop/sms/recharge/history">充值记录</a></li>
<%--         <li <c:if test="${currentPage == 'consume'}"> class="active" </c:if>><a href="#">使用记录</a></li> --%>
        <li <c:if test="${currentPage == 'alarm'}"> class="active" </c:if>><a href="${qc.rootPath }shop/sms/alarm">告警通知</a></li>
<%--         <li <c:if test="${currentPage == 'xxxxx'}"> class="active" </c:if>><a href="#">商家使用记录</a></li> --%>
    </ul>
</div>