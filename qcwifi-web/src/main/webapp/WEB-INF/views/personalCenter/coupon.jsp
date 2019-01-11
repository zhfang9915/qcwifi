<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>个人中心</title>
    <link rel="shortcut icon" href="${qc.nginx }favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${qc.nginx }cloud/css/modules/layer/default/layer.css">
    <link rel="stylesheet" href="${qc.nginx }cloud/css/layui.css">

    <link rel="stylesheet" href="${qc.nginx }cloud/css/css.css">
    <link rel="stylesheet" href="${qc.nginx }cloud/css/account.settings.css">
</head>
<body class="layout-body">
    <div class="layout-wrap">
        <div class="layout-header">
            <%@include file="../common/header.jsp"%>
        </div>
        <div class="layout-main layui-clear">
            <%@include file="../common/menu-left.jsp"%>
            <div class="layout-in-main">
            	<div class="layui-tab layui-tab-brief">
                	<button id="exchangeAction" class="layui-btn layui-btn-lie" data-method="offset" data-type="auto" >序列号兑换</button>
				  	<ul class="layui-tab-title">
				    	<li id="couponType1" class="layui-this">抵用劵（0）</li>
				    	<li id="couponType2">优惠劵（0）</li>
				 	</ul>
				 	<div class="layui-tab-content">
				    	<div class="layui-tab-item layui-shows layui-show">
				    		<ul id="couponType1List" class="coupon-list">
								
				    		</ul>
				    	</div>
				    	
				    	<div class="layui-tab-item">
				    		<ul id="couponType2List" class="coupon-list">

				    		</ul>
				    	</div>
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
<script>
	//注意：选项卡 依赖 element 模块，否则无法进行功能性操作
	layui.use('element', function(){
		var element = layui.element;
	});
</script>

<!--对话列表-->
<div class="layui-layer layui-layer-page" id="layuiExchange" style="display: none;">
  <div class="layui-layer-title" style="cursor: move;">序列号兑换</div>
  <div id="layerDemoauto" class="layui-layer-content layui-poup-cont">
    	<div class="layui-in-form">
    		<div class="hd">请输入序列号</div>
    		<div class="bd">
    			<input type="text" class="layui-input" name="" id="" value="" />
    		</div>
    	</div>
  </div>
  <span class="layui-layer-setwin">
    <a class="layui-layer-ico layui-layer-close layui-layer-close1" href="javascript:;"></a>
  </span>
  <div class="layui-layer-btn-c">
    <a class="layui-btn layui-btn-comfirm">确定</a>
  </div>
</div>

<script>
	$('.layui-btn-lie').click(function(){
		$('#layui-layer5').show();
	})
	$('.layui-layer-close').click(function(){
		$('#layui-layer5').hide();
	})
</script>
<script rootURL=${qc.rootPath } src="${qc.nginx }script/cloud/personal/coupon.js"></script>
</body>
</html>