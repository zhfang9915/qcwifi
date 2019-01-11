<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>安全中心</title>
    <link rel="shortcut icon" href="${qc.nginx }favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${qc.nginx }cloud/css/layui.css">
    <link rel="stylesheet" href="${qc.nginx }cloud/css/modules/layer/default/layer.css">
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
                	<div class="in-text">安全中心</div>
                </div>
                <div class="lay-safelist">
                	<div class="lay-safe-row">
                		<div class="s-icon"><img src="${qc.nginx }cloud/images/sucess.png" alt="" /></div>
                		<div class="s-lg-text">登录密码</div>
                		<div class="s-sm-text">互联网账号存在被盗危险，建议您定期更改密码以保护账户安全。 </div>
                		<div class="s-bar">
                			<a href="javascript:;" class="lk-update" id="changePass">更改密码</a>
                		</div>
                	</div>
                	
                	<c:if test="${not empty binding.phone}">
	                	<div class="lay-safe-row">
	                		<div class="s-icon"><img src="${qc.nginx }cloud/images/sucess.png" alt="" /></div>
	                		<div class="s-lg-text">手机号</div>
	                		<div class="s-sm-text">您当前绑定的安全手机号：${binding.phone } ,若已丢失或停用，请立即更换，避免账户被盗 </div>
	                		<div class="s-bar">
	                			<a href="${qc.nginx }personal/center/safe/change/phone/choose" class="lk-update">更换手机</a>
	                		</div>
	                	</div>
                	</c:if>
                	<c:if test="${empty binding.phone}">
	                	<div class="lay-safe-row">
	                		<div class="s-icon"><img src="${qc.nginx }cloud/images/false.png" alt="" /></div>
	                		<div class="s-lg-text">手机号</div>
	                		<div class="s-sm-text">互联网账号存在被盗危险，强烈建议您绑定安全验证手机号</div>
	                		<div class="s-bar">
	                			<a href="${qc.nginx }personal/center/binding/phone" class="layui-btn layui-btn-bind">立即绑定</a>
	                		</div>
	                	</div>
                	</c:if>
                	
                	<c:if test="${not empty binding.email}">
	                	<div class="lay-safe-row">
	                		<div class="s-icon"><img src="${qc.nginx }cloud/images/sucess.png" alt="" /></div>
	                		<div class="s-lg-text">邮箱</div>
	                		<div class="s-sm-text">您当前绑定的安全邮箱：${binding.email }</div>
	                		<div class="s-bar">
	                			<a href="${qc.nginx }personal/center/safe/change/email/choose" class="lk-update">更换邮箱</a>
	                		</div>
	                	</div>
                	</c:if>
                	<c:if test="${empty binding.email}">
	                	<div class="lay-safe-row">
	                		<div class="s-icon"><img src="${qc.nginx }cloud/images/false.png" alt="" /></div>
	                		<div class="s-lg-text">邮箱</div>
	                		<div class="s-sm-text">互联网账号存在被盗危险，强烈建议您绑定安全验证邮箱</div>
	                		<div class="s-bar">
	                			<a href="${qc.nginx }personal/center/binding/email" class="layui-btn layui-btn-bind">立即绑定</a>
	                		</div>
	                	</div>
                	</c:if>
                	
                	<div class="safe-tips">
						<p>安全服务提示</p>
						<p>1.确认您登录的是前辰无线http://www.qcwifi.ltd/,注意防范进入钓鱼网站，不要轻信各种即时通讯工具发送的商品或支付链接，谨防网购诈骗。</p>
						<p>2.建议您安装杀毒软件，并定期更新操作系统等软件补丁，确保账户及交易安全。</p>
                	</div>
                </div>
            </div>
        </div>
        <!--footer-->
        <%@include file="../common/footer.jsp"%>
    </div>
    

<!-- <script> -->
// //注意：选项卡 依赖 element 模块，否则无法进行功能性操作
// layui.use('element', function(){
//   var element = layui.element;
// });
<!-- </script> -->


<!--对话列表-->
<div class="layui-layer layui-layer-page" id="layui-password" style="display: none;">
  <div class="layui-layer-title" style="cursor: move;">密码修改</div>
  <div id="layerDemoauto" class="layui-layer-content">
    	<form id="changePwdForm" class="layui-form">
    		<div class="layui-form">
			  <div class="layui-form-item">
			    <label class="layui-form-label">原密码：</label>
			    <div class="layui-input-block">
			      <input type="password" name="oldPwd" lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">新密码：</label>
			    <div class="layui-input-block">
			      <input type="password" name="newPwd" lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">确认新密码：</label>
			    <div class="layui-input-block">
			      <input type="password" name="newPwdConfim" lay-verify="required" placeholder="请输确认新密码" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <div class="layui-button-block">
			      <button lay-submit lay-filter="change-password" class="layui-btn">确认</button>
			      <button type="reset" class="layui-btn layui-btn-reset">取消</button>
			    </div>
			  </div>
		  </div>
		</form>
  </div>
  <span class="layui-layer-setwin">
    <a class="layui-layer-ico layui-layer-close layui-layer-close1" href="javascript:;"></a>
  </span>
</div>
<div class="layui-layer-shade" style="display: none;"></div>


<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script src="${qc.nginx }cloud/layui.js"></script>
<script rootURL=${qc.rootPath } src="${qc.nginx }script/cloud/personal/settings.safe.js"></script>
</body>
</html>
