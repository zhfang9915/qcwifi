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
                    	<c:if test="${not empty order }">
	                        <div class="layui-form-item">
	                            <label class="layui-form-label">订单号</label>
	                            <div class="layui-input-block">
	                                <div class="layui-input-text"><span class="text-less-num">${order.orderNo }</span></div>
	                            </div>
	                        </div>
	                        <div class="layui-form-item">
	                            <label class="layui-form-label">订单内容</label>
	                            <div class="layui-input-block">
	                                <div class="layui-input-text"><span class="text-less-num">${order.orderTitle }</span></div>
	                            </div>
	                        </div>
	                        <div class="layui-form-item">
	                            <label class="layui-form-label">订单金额</label>
	                            <div class="layui-input-block">
	                                <div class="layui-input-text"><span class="text-less-num">${order.amount }</span>元</div>
	                            </div>
	                        </div>
	                        <div class="layui-form-item">
	                            <div class="layui-input-block">
	                                <button class="layui-btn layui-lang-btn layout-right" onClick="toPay('${order.orderNo }');">立即支付</button>
	                            </div>
	                        </div>
                    	</c:if>
                    	<c:if test="${empty order }">
                    		<div class="layui-form-item">
	                            <div class="layui-input-block">
	                                <div class="layui-input-text" style="font-size: 20px;">未查询到此订单信息，请确认订单号是否正确！</div>
	                            </div>
	                        </div>
                    	</c:if>
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
<script rootURL=${qc.rootPath } src="${qc.nginx }script/cloud/shop/sms.recharge.success.js"></script>
</body>
</html>
