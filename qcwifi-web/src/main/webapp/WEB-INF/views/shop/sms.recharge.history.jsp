<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>短信充值</title>
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
                <div class="in-header">充值记录</div>
                <div class="in-content in-pat-40">
                    <div class="layui-form layui-txt-form" action="" style="width:500px">
                        <input type="text" class="layui-input layui-lg-date" readonly id="historyDate" value="" placeholder="请选择查询日期" lay-key="7">
                        <i class="layui-icon layui-icon-date">&#xe637;</i>
                        <div class="layui-input-text">默认查询近30天内的充值记录</div>
                    </div>
                    <!--充值记录-->
                    <div class="table-wrap layout-center">
                        <table class="layui-table" lay-size="lg" lay-skin="line">
                            <thead>
                                <tr>
                                    <th>日期</th>
                                    <th>充值金额（元）</th>
                                    <th>充值数量</th>
                                </tr>
                            </thead>
                            <tbody id="historyBody"></tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!--footer-->
        <%@include file="../common/footer.jsp"%>
    </div>



<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script src="${qc.nginx }cloud/layui.js"></script>
<script src="${qc.nginx }cloud/layer/layer.js"></script>
<script rootURL=${qc.rootPath } src="${qc.nginx }script/cloud/shop/sms.recharge.history.js"></script>
</body>
</html>

