<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>个人中心-安全中心-修改绑定手机号-选择验证方式</title>
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
                        <li class="active">
                            <div class="st-text">STEP1</div>
                            <div class="st-box">1、选择验证方式</div>
                        </li>
                        <li>
                            <div class="st-text">STEP2</div>
                            <div class="st-box">2、验证/修改</div>
                        </li>
                        <li>
                            <div class="st-text">STEP3</div>
                            <div class="st-box">3、修改完成</div>
                        </li>
                    </ul>
                </div>
                <div class="st-tips">请先确认当前绑定手机号${phone }和邮箱${email }是否能接收信息，再选择修改方式；</div>
                <div class="verify-main">
                    <div class="verify-row">
                        <div class="verify-ico">
                            <img src="${qc.nginx }cloud/images/step_email.png" alt="">
                        </div>
                        <div class="ver-intro">
                            <h3>无法接收短信，通过“绑定邮箱+邮箱验证码”修改</h3>
                            <p>原手机号已丢失或停用，但邮箱能收到信息，请选择此方式，安全验证码将发送到您绑定的邮箱；</p>
                        </div>
                        <a href="${qc.nginx }personal/center/change/${changeType }/email" class="layui-btn layui-btn-verify">立即修改</a>
                    </div>
                    <div class="verify-row">
                        <div class="verify-ico">
                            <img src="${qc.nginx }cloud/images/step_msg.png" alt="">
                        </div>
                        <div class="ver-intro">
                            <h3>能接收短信，通过“手机号+短信验证码”修改</h3>
                            <p>手机号能正常接收短信，请选择此方式，安全验证码将发送到您绑定的手机；</p>
                        </div>
                        <a href="${qc.nginx }personal/center/change/${changeType }/phone" class="layui-btn layui-btn-verify">立即修改</a>
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

