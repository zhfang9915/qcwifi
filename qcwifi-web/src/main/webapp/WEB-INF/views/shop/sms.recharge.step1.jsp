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
                <div class="in-header">短信充值</div>
                <div class="in-content">
                    <form class="layui-form layui-txt-form" action="" style="width: 618px">
                        <div class="layui-form-item">
                            <label class="layui-form-label">当前短信剩余</label>
                            <div class="layui-input-block">
                                <div class="layui-input-text"><span class="text-less-num">${smsOverPlus }</span> 条</div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">短信充值</label>
                            <div class="layui-input-block">
                                <select name="smsPackageId" id="smsPackageSelect" lay-verify="required">
                                </select>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">说明</label>
                            <div class="layui-input-block">
                                <div class="layui-input-text">商铺使用会员上网同一用户二次上网，不需要短信验证码，更节省短信费用。</div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn layui-lang-btn layout-right" lay-submit lay-filter="smsRecharge">立即充值</button>
                            </div>
                        </div>
                    </form>
                    <!--充值记录-->
                    <div class="table-wrap layout-center">
                        <table class="layui-table" lay-size="lg" lay-skin="line">
                            <thead>
                                <tr>
                                    <th>短信资费</th>
                                    <th>短信条数</th>
                                </tr>
                            </thead>
                            <tbody id="smsPackageDetail"></tbody>
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
<script rootURL=${qc.rootPath } src="${qc.nginx }script/cloud/shop/sms.recharge.js"></script>
</body>
</html>
