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
                  <a href="${qc.rootPath }shop/sub/index">连锁管理列表</a> > 创建连锁帐号
                </div>
            </div>
            <form class="layui-form layui-creat-form" lay-filter="create-sub-account-form">
              <div class="layui-form-item">
                <label class="layui-form-label">连锁管理名称</label>
                <div class="layui-input-block">
                  <input type="text" name="nickname" lay-verify="required" placeholder="管理账号名称" autocomplete="off" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">连锁管理帐号</label>
                <div class="layui-input-block">
                  <input type="text" name="username" lay-verify="required|username" placeholder="请输入4-20位数字、字母(创建后不能修改)" autocomplete="off" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">联系人</label>
                <div class="layui-input-block">
                  <input type="text" name="name" lay-verify="required" placeholder="请输入联系人" autocomplete="off" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">联系手机</label>
                <div class="layui-input-block">
                  <input type="text" name="phone" lay-verify="required|phone" placeholder="请输入能随时联系的手机号" autocomplete="off" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">登录密码</label>
                <div class="layui-input-block">
                  <input type="password" name="password" lay-verify="required|pass" placeholder="请输入6-18位数字、字母、字符" autocomplete="off" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">确认密码</label>
                <div class="layui-input-block">
                  <input type="password" name="repassword" lay-verify="required|repass" placeholder="请输入6-18位数字、字母、字符" autocomplete="off" class="layui-input">
                </div>
              </div>
              <div class="layui-input-block">
                  <button class="layui-btn layui-lg-btn" lay-submit lay-filter="submit-sub-account">提交</button>
                  <button type="reset" class="layui-btn layui-cancel-btn">取消</button>
                </div>
            </form>
        </div>
        <!--footer-->
        <%@include file="../common/footer.jsp"%>
    </div>



<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script src="${qc.nginx }cloud/layui.js"></script>
<script src="${qc.nginx }cloud/layer/layer.js"></script>
<script rootURL=${qc.rootPath } staticURL=${qc.nginx } src="${qc.nginx }script/cloud/shop/sub.new.js"></script>
</body>
</html>
