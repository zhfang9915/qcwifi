<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>个人中心-安全中心-修改绑定邮箱-验证修改-邮箱</title>
    <link rel="shortcut icon" href="${qc.nginx }favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${qc.nginx }cloud/css/layui.css">
    <link rel="stylesheet" href="${qc.nginx }cloud/css/css.css">
</head>
<body class="layout-body">
    <div class="layout-wrap">
        <div class="layout-header">
            <%@include file="../common/header.jsp"%>
        </div>
        <div class="layout-main layout-lg-main layui-clear">
            <div class="layout-in-main">
                <div class="layui-tab layui-tab-brief">
                    <ul class="layui-tab-title">
                        <c:if test="${changeType=='phone' }">
                        	<li class="layui-this">修改绑定手机</li>
                       	</c:if>
                       	<c:if test="${changeType=='email' }">
                        	<li class="layui-this">修改绑定邮箱</li>
                       	</c:if>
                    </ul>
                </div>
                <!-- step -->
                <div class="step-list">
                    <ul class="layui-clear">
                        <li>
                            <div class="st-text">STEP1</div>
                            <div class="st-box">1、选择验证方式</div>
                        </li>
                        <li class="active">
                            <div class="st-text">STEP2</div>
                            <div class="st-box">2、验证/修改</div>
                        </li>
                        <li>
                            <div class="st-text">STEP3</div>
                            <div class="st-box">3、修改完成</div>
                        </li>
                    </ul>
                </div>
                
                <c:if test="${byType=='email' }">
	                <div class="st-tips">您正在通过“绑定邮箱+邮箱验证码”方式进行验证/修改</div>
	                <div class="verify-main verify-tmain">
	                    <div class="verify-hd">
	                        <img src="${qc.nginx }cloud/images/warning.png" alt="">
	                        <span>为了您的账户安全，请先验证邮箱</span>
	                    </div>
	                    <div class="verify-bd">
	                        <form class="layui-form" action="">
	                            <div class="verify-form">
	                                <div class="v-row">
	                                    <div class="hd">您绑定的邮箱</div>
	                                    <div class="bd">
	                                        <div class="v-row-txt">${email }</div>
	                                        <input type="hidden" name="changeType" value="${changeType }"/>
	                                        <input type="hidden" name="byType" value="${byType }"/>
	                                    </div>
	                                </div>
	                                <div class="v-row">
	                                    <div class="hd">　</div>
	                                    <div class="bd">
	                                    	<input type="button" class="btn-erweim" style="background-color: white;float: left; border: 1px solid #bfbfbf;" value="获取邮箱验证码"/>
	                                    </div>
	                                </div>
	                                <div class="v-row">
	                                    <div class="hd">邮箱验证码</div>
	                                    <div class="bd">
	                                        <input type="text" name="code"  class="v-input v-md-input" lay-verify="required|number" maxlength="6"/>
	                                    </div>
	                                </div>
	                                <div class="v-row_ft">
	                                    <button class="layui-btn layui-btn-next" lay-submit lay-filter="changeBinding">下一步</button>
	                                    <c:if test="${changeType=='phone' }">
		                                    <a href="${qc.nginx }personal/center/safe/change/phone/choose" class="layui-v-text">上一步</a>
				                       	</c:if>
				                       	<c:if test="${changeType=='email' }">
		                                    <a href="${qc.nginx }personal/center/safe/change/email/choose" class="layui-v-text">上一步</a>
				                       	</c:if>
	                                </div>
	                            </div>
	                        </form>
	                    </div>
	                </div>
                </c:if>
                <c:if test="${byType=='phone' }">
	                <div class="st-tips">您正在通过“手机号+短信验证码”方式进行验证/修改</div>
	                <div class="verify-main verify-tmain">
	                    <div class="verify-hd">
	                        <img src="${qc.nginx }cloud/images/warning.png" alt="">
	                        <span>为了您的账户安全，请先验证手机</span>
	                    </div>
	                    <div class="verify-bd">
	                        <form class="layui-form" action="">
	                            <div class="verify-form">
	                                <div class="v-row">
	                                    <div class="hd">您绑定的手机</div>
	                                    <div class="bd">
	                                        <div class="v-row-txt">${phone }</div>
	                                        <input type="hidden" name="changeType" value="${changeType }"/>
	                                        <input type="hidden" name="byType" value="${byType }"/>
	                                    </div>
	                                </div>
	                                <div class="v-row">
	                                    <div class="hd">　</div>
	                                    <div class="bd">
	                                        <input type="button" class="btn-erweim" style="background-color: white;float: left; border: 1px solid #bfbfbf;" value="获取短信证码"/>
	                                    </div>
	                                </div>
	                                <div class="v-row">
	                                    <div class="hd">短信验证码</div>
	                                    <div class="bd">
	                                        <input type="text" name="code" class="v-input v-md-input" lay-verify="required|number" >
	                                    </div>
	                                </div>
	                                <div class="v-row_ft">
	                                    <button class="layui-btn layui-btn-next" lay-submit lay-filter="changeBinding">下一步</button>
	                                    <c:if test="${changeType=='phone' }">
		                                    <a href="${qc.nginx }personal/center/safe/change/phone/choose" class="layui-v-text">上一步</a>
				                       	</c:if>
				                       	<c:if test="${changeType=='email' }">
		                                    <a href="${qc.nginx }personal/center/safe/change/email/choose" class="layui-v-text">上一步</a>
				                       	</c:if>
	                                </div>
	                            </div>
	                        </form>
	                    </div>
	                </div>
                </c:if>
               
                
                
            </div>
        </div>
        <!--footer-->
        <%@include file="../common/footer.jsp"%>
    </div>



<script src="${qc.nginx }cloud/layui.js"></script>
<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script src="${qc.nginx }script/cloud/personal/randcode.js"></script>
<script rootURL=${qc.rootPath } src="${qc.nginx }script/cloud/personal/settings.binding.change.js"></script>
</body>
</html>


