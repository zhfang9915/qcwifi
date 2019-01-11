<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>个人中心-安全中心</title>
    <link rel="shortcut icon" href="${qc.nginx }favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${qc.nginx }cloud/css/layui.css">
    <link rel="stylesheet" href="${qc.nginx }cloud/css/css.css">
</head>
<body class="layout-body">
    <div class="layout-wrap">
        <div class="layout-header">
            <%@include file="../common/header.jsp"%>
        </div>
        <div class="layout-main layout-lg-main layui-clear">
            <div class="layout-in-main">
                <div class="layui-tab layui-tab-brief">
                    <ul class="layui-tab-title">
                        <c:if test="${changeType=='phone' }">
                        	<li class="layui-this">修改绑定手机</li>
                       	</c:if>
                       	<c:if test="${changeType=='email' }">
                        	<li class="layui-this">修改绑定邮箱</li>
                       	</c:if>
                    </ul>
                </div>
                <!-- step -->
                <div class="step-list">
                    <ul class="layui-clear">
                        <li>
                            <div class="st-text">STEP1</div>
                            <div class="st-box">1、选择验证方式</div>
                        </li>
                        <li>
                            <div class="st-text">STEP2</div>
                            <div class="st-box">2、验证/修改</div>
                        </li>
                        <li class="active">
                            <div class="st-text">STEP3</div>
                            <div class="st-box">3、修改完成</div>
                        </li>
                    </ul>
                </div>
                <div class="st-tips"></div>
                <div class="verify-main verify-tmain">
                    <div class="verify-hd">
                        <img src="${qc.nginx }cloud/images/sucess_box2.png" alt="">
                        <span>修改成功！</span>
                    </div>
                    <div class="verify-bd verify-bd-end" style="padding-left: 200px;">
                        <div class="verify-form">
                        	<c:if test="${changeType=='phone' }">
	                        	<div class="v-row">
	                                <div class="hd" style="width: 130px;">您绑定的手机号</div>
	                                <div class="bd">
	                                    <div class="v-row-txt">${phone }</div>
	                                </div>
	                            </div>
	                       	</c:if>
	                       	<c:if test="${changeType=='email' }">
	                        	<div class="v-row">
	                                <div class="hd" style="width: 130px;">您绑定的邮箱</div>
	                                <div class="bd">
	                                    <div class="v-row-txt">${email }</div>
	                                </div>
	                            </div>
	                       	</c:if>
	                       	<div class="v-row_ft" style="text-align: left;padding-left: 0px;">
                                <a href="${qc.nginx }personal/center/safe/settings" class="layui-v-text">返回安全中心</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--footer-->
        <%@include file="../common/footer.jsp"%>
    </div>



<script src="${qc.nginx }cloud/layui.js"></script>
<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
</body>
</html>