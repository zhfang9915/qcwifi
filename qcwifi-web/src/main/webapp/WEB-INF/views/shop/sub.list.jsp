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
    <style type="text/css">
			.layui-body {
				overflow-y: scroll;
			}
	</style>
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
            <div class="layout-in-main">
                <div class="in-header layui-clear"  style="border-bottom: 0">
                    <div class="layout-left">
                        <a href="${qc.rootPath }shop/sub/new" class="layui-btn layui-btn-big" style="color: #FFF;">创建连锁账号</a>
                    </div>
                    <div class="tips-text">可将多个连锁商铺用连锁账号关联起来，连锁账号可登录平台查看相关商铺数据</div>
                </div>
                <table id="sub-account-table" class="layui-table" lay-filter="sub-account-table"></table>
            </div>
        </div>
        <!--footer-->
        <%@include file="../common/footer.jsp"%>
    </div>


<script type="text/html" id="sub-tool">
	<a class="td-link" lay-event="shop">管理商铺</a>
	<a class="td-link" lay-event="pass">修改密码</a>
	<a class="td-link" lay-event="del">删除</a>
</script>
<script type="text/html" id="sub-locked">
  <input type="checkbox" name="locked" value="{{d.locked}}" lay-skin="switch" lay-text="激活|锁定" lay-filter="sub-locked-switch" {{ d.locked == false ? 'checked' : '' }}>
</script>
<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script src="${qc.nginx }cloud/layui.js"></script>
<script src="${qc.nginx }cloud/layer/layer.js"></script>
<script rootURL=${qc.rootPath } staticURL=${qc.nginx } src="${qc.nginx }script/cloud/shop/sub.list.js"></script>

</body>
</html>
