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
                  <a href="${qc.rootPath }shop/sub/index">连锁管理列表</a> > 管理商铺
                </div>
                <div class="layout-right">当前连锁管理名称：${sub_nickname }</div>
            </div>
       		<form class="layui-form layui-full-form">
            	<c:if test="${result }">
            		<input type="hidden" name="sub-id" value="${sub_id }"/>
					<table style="width: 100%;">
						<tbody>
							<tr>
								<td style="float: left;">
									<table id="free-shop-table" class="layui-table" lay-filter="free-shop-table"></table>
								</td>
								<td>
									<button class="layui-btn layui-btn-sm" lay-submit lay-filter="add-sub-shop">
										<i class="layui-icon">&#xe65b;</i>
									</button>
									<button class="layui-btn layui-btn-sm" lay-submit lay-filter="remove-sub-shop" style="margin-top: 10px; position: absolute;">
										<i class="layui-icon">&#xe65a;</i>
									</button>
								</td>
								<td style="float: right;">
									<table id="checked-shop-table" class="layui-table" lay-filter="checked-shop-table"></table>
								</td>
							</tr>
						</tbody>
					</table>
            	</c:if>
            	<c:if test="${!result }">
	            	<div class="layout-error">
	            		您账号下无此连锁账号信息
	            	</div>
            	</c:if>
           	</form>
        </div>
        <!--footer-->
        <%@include file="../common/footer.jsp"%>
    </div>


<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script src="${qc.nginx }cloud/layui.js"></script>
<script src="${qc.nginx }cloud/layer/layer.js"></script>
<script rootURL=${qc.rootPath } staticURL=${qc.nginx } src="${qc.nginx }script/cloud/shop/sub.chain.js"></script>

</body>
</html>

