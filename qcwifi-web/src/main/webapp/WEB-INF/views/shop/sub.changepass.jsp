<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>连锁管理</title>
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
        <div class="layout-main layout-lg-main layui-clear">
            <div class="in-header layui-clear">
                <div class="layout-left">
                  <a href="${qc.rootPath }shop/sub/index">连锁管理列表</a> > 修改连锁帐号登录密码
                </div>
            </div>
            	<c:if test="${result }">
	           		<form class="layui-form layui-creat-form">
		              <div class="layui-form-item">
		                <label class="layui-form-label">连锁管理账号</label>
		                <div class="layui-input-block">
		                  <input type="text" name="username" class="layui-input" readonly="readonly" value="${sub_username }">
		                  <input type="hidden" name="id"  value="${sub_id }">
		                </div>
		              </div>
		              <div class="layui-form-item">
		                <label class="layui-form-label">旧密码</label>
		                <div class="layui-input-block">
		                  <input type="password" name="oldpassword" lay-verify="required|pass" placeholder="请输入原登录密码" autocomplete="off" class="layui-input">
		                </div>
		              </div>
		              <div class="layui-form-item">
		                <label class="layui-form-label">新密码</label>
		                <div class="layui-input-block">
		                  <input type="password" name="password" lay-verify="required|pass" placeholder="请输入您的新密码(6-16位数字、字母)" autocomplete="off" class="layui-input">
		                </div>
		              </div>
		              <div class="layui-form-item">
		                <label class="layui-form-label">确认新密码</label>
		                <div class="layui-input-block">
		                  <input type="password" name="repassword" lay-verify="required|repass" placeholder="请再次输入您地新密码" autocomplete="off" class="layui-input">
		                </div>
		              </div>
		              <div class="layui-input-block">
		                  <button class="layui-btn layui-lg-btn" lay-submit lay-filter="change-pass-sub-account">确认修改</button>
		                  <button type="reset" class="layui-btn layui-cancel-btn">取消</button>
		                </div>
		            </form>
	            </c:if>
	            <c:if test="${!result }">
	            	<form class="layui-form layui-full-form">
		            	<div class="layout-error">
		            		您账号下无此连锁账号信息
		            	</div>
	            	</form>
	            </c:if>
        </div>
        <!--footer-->
        <%@include file="../common/footer.jsp"%>
    </div>



<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script src="${qc.nginx }cloud/layui.js"></script>
<script src="${qc.nginx }cloud/layer/layer.js"></script>
<script rootURL=${qc.rootPath } staticURL=${qc.nginx } src="${qc.nginx }script/cloud/shop/sub.changepass.js"></script>

</body>
</html>
