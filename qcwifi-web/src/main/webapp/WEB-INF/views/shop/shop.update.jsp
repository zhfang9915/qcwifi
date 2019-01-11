<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>更新商铺</title>
    <link rel="shortcut icon" href="${qc.nginx }favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${qc.nginx }cloud/css/layui.css">
    <link rel="stylesheet" href="${qc.nginx }cloud/css/css.css">
    <style type="text/css">
    	#shopMap{
    		position: absolute;
    		width: 93%;
    		height: 100%;
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
            <div class="in-header layui-clear">
                <div class="layout-left">
                    编辑商铺
                </div>
            </div>
            <div class="inner-tips">请务必提供真实与准确点的商铺信息，以便后续更好的提供服务。</div>
            <form class="layui-form layui-shop-form" lay-filter="create-shop-form">
              <div class="layui-form-item">
                <label class="layui-form-label"><span class="text-red">*</span>商铺名称</label>
                <div class="layui-input-block">
                  <input type="text" name="shopName" lay-verify="required" placeholder="请输入您的店铺名；如：肯德基(人民广场店)" autocomplete="off" class="layui-input layui-lg-input left" value="${shop.shopName }">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label"></label>
                <div class="layui-input-block">
                  <div class="in-map" style="height:300px;">
                  	<div id="shopMap"></div>
                  </div>
                </div>
              </div>
				<div class="layui-form-item">
                <label class="layui-form-label"><span class="text-red">*</span>地址</label>
                <div class="layui-input-block layui-md-group">
                    <select id="province" name="province" lay-filter="province" lay-verify="required"></select>
                    <select id="city" name="city" lay-filter="city" lay-verify="required"></select>
                    <select id="area" name="area" lay-filter="area" lay-verify="required"></select>
                    <input type="text" id="address" name="address" lay-verify="required" placeholder="请输入详细地址，例：街道/门牌号" autocomplete="off" class="layui-input layui-sm-input" value="${shop.address }"/>
                    <input type="hidden" id="hidden_shopId" name="lng" value="${shop.shopId }"/>
					<input type="hidden" id="hidden_lng" name="lng" value="${shop.lng }"/>
					<input type="hidden" id="hidden_lat" name="lat" value="${shop.lat }"/>
					<input type="hidden" id="hidden_province" name="lat" value="${shop.province }"/>
					<input type="hidden" id="hidden_city" name="lat" value="${shop.city }"/>
					<input type="hidden" id="hidden_area" name="lat" value="${shop.area }"/>
					<input type="hidden" id="hidden_primaryCategory" name="lat" value="${shop.primaryCategory }"/>
					<input type="hidden" id="hidden_secondaryCategory" name="lat" value="${shop.secondaryCategory }"/>
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label"><span class="text-red">*</span>经营类目</label>
                <div class="layui-input-block layui-xs-select">
                    <select name="primaryCategory" id="primaryCategory" lay-verify="required" lay-filter="primaryCategory">
                    </select>
                    <select name="secondaryCategory" id="secondaryCategory" lay-verify="required" lay-filter="secondaryCategory">
                    </select>
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label"><span class="text-red">*</span>联系电话</label>
                <div class="layui-input-block">
                  <input type="text" name="phone" lay-verify="required|phone" placeholder="请输入店铺联系手机号" autocomplete="off" class="layui-input" style="width: 380px" maxlength="11" value="${shop.phone }">
                </div>
              </div>
              <c:if test="${not empty marks}">
	              <div class="layui-form-item">
	                <label class="layui-form-label">店铺标签</label>
	                <div class="layui-input-block layui-xs-select">
	                	<c:forEach items="${marks }" var="mark">
		                    <input type="checkbox" name="mark" title="${mark.name }" value="${mark.id }">
	                	</c:forEach>
	                </div>
	              </div>
              </c:if>
              <div class="layui-input-block">
                  <button class="layui-btn layui-lg-btn" lay-submit lay-filter="submit-shop-info">提交</button>
                  <button type="reset" class="layui-btn layui-cancel-btn">取消</button>
                </div>
            </form>
        </div>
        <!--footer-->
        <%@include file="../common/footer.jsp"%>
    </div>


<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=XlwywgLBmWY54EzsPyNuUCm8hhnbsAGG"></script>
<script src="${qc.nginx }cloud/layui.js"></script>
<script type="text/javascript">
	var adds=["province","city","area"];
</script>
<script type="text/javascript" src="${qc.nginx }script/cloud/places.js"></script>
<script type="text/javascript">
	_init_area(adds);
</script>
<script rootURL=${qc.rootPath } src="${qc.nginx }script/cloud/shop/shop.update.js"></script>
</body>
</html>