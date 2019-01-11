<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>短信充值</title>
    <link rel="shortcut icon" href="${qc.nginx }favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${qc.nginx }cloud/css/layui.css">
    <link rel="stylesheet" href="${qc.nginx }cloud/css/css.css">
</head>
<body class="layout-body">
    <div class="layout-wrap">
        <div class="layout-header">
            <%@include file="../common/header.jsp"%>
            <div class="layout-navbar">
                <%@include file="shop.header.jsp"%>
            </div>
        </div>
        <div class="layout-main layui-clear">
            <%@include file="sms.menu.jsp"%>
            <div class="layout-in-main">
                <div class="in-header">告警通知</div>
                <div class="in-content">
                    <form class="layui-form layui-txt-form" action="" autocomplete="off">
                        <div class="layui-form-item">
                            <label class="layui-form-label">开启通知</label>
                            <div class="layui-input-block">
                                <input type="checkbox" name="switch" lay-skin="switch" lay-text="开启|关闭" lay-filter="alarmSwitch" <c:if test="${ss.alarmSwitch}"> checked </c:if> />
                                <div class="layui-unselect layui-form-switch" lay-skin="_switch"><i></i></div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">当前短信剩余</label>
                            <div class="layui-input-block">
                                <div class="layui-input-text">
                                	<span class="text-less-num">${ss.count }</span> 条，马上
                                	<a href="${qc.rootPath }shop/sms/recharge" class="layui-v-text"><span class="text-less-num">去充值</span></a>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">当短信余额低于</label>
                            <div class="layui-input-block">
                                <input type="text" value="${ss.alarmMin }" name="alarmMin" lay-verify="required|number" class="layui-input layui-input-num">
                                <div class="layui-input-text layout-left">条时，发送短信通知到手机号</div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">通知到手机号</label>
                            <div class="layui-input-block">
                                <div class="layui-input-text">${bindingPhone }</div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn layui-lang-btn" lay-submit lay-filter="submitSwitch">确定</button>
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
<script rootURL=${qc.rootPath } src="${qc.nginx }script/cloud/shop/sms.alarm.js"></script>
</body>
</html>
