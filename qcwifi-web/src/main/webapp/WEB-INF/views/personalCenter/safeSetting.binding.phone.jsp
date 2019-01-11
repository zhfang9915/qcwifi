<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>个人中心</title>
    <link rel="shortcut icon" href="${qc.nginx }favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${qc.nginx }cloud/css/layui.css">
    <link rel="stylesheet" href="${qc.nginx }cloud/css/css.css">
</head>
<body class="layout-body">
    <div class="layout-wrap">
        <div class="layout-header">
            <%@include file="../common/header.jsp"%>
        </div>
        <div class="layout-main layui-clear">
            <%@include file="../common/menu-left.jsp"%>
            <div class="layout-in-main">
                <div class="in-toptit">
                    <a href="${qc.nginx }personal/center/safe/settings" class="in-back">返回安全中心</a>
                	<div class="in-text">绑定手机</div>
                </div>
                <div class="in-single">
                	<form class="layui-form layui-single-form" action="">
                        <div class="layui-form-item">
                          <label class="layui-form-label">手机号</label>
                          <div class="layui-input-block">
                            <input type="text" name="phone" lay-verify="required|phone" placeholder="请输入手机号" autocomplete="off" class="layui-input">
                          </div>
                        </div>
                        <div class="layui-form-item">
                          <label class="layui-form-label">图片验证码</label>
                          <div class="layui-input-block">
                            <input type="text" name="imgCode" lay-verify="required" maxlength="4" placeholder="请输入下图中的验证码" autocomplete="off" class="layui-input">
                            <div class="layui-yzm">
                                <span class="yzm-pic"><img class="img-randconde" src="${qc.rootPath }base/randcode?"+(new Date()).valueOf() alt="" style="width: 90px;margin-bottom: -10px;"></span>
                                <a href="javascript:;" onclick="clickImage();" class="yzm-lk">看不清，换一张</a>
                            </div>
                          </div>
                        </div>
                        <div class="layui-form-item">
                          <label class="layui-form-label">验证码</label>
                          <div class="layui-input-block">
                            <input type="text" name="smsCode" lay-verify="required|number" placeholder="请输入您手机接收到的验证码" autocomplete="off" class="layui-input">
                            <input type="button" class="layui-btn-code" style="background-color: white;" value="获取短信验证码"/>
                          </div>
                        </div>
                        <div class="layui-form-item layui-form-button">
                          <div class="layui-input-block">
                            <button class="layui-btn layui-lg-btn" lay-submit lay-filter="bindingPhone">确认绑定</button>
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
<script src="${qc.nginx }script/cloud/personal/randcode.js"></script>
<script rootURL=${qc.rootPath } src="${qc.nginx }script/cloud/personal/settings.binding.phone.js"></script>
</body>
</html>

