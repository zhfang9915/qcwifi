<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
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
				<div class="in-toptit">
                	<div class="in-text">资料认证</div>
                </div>
                
                <!-- 需要认证 -->
                <c:if test="${empty userInfo}">               
	                <div class="in-single">
	                	<form id="userInfoForm" class="layui-form layui-single-form" lay-filter="user-info-div">
	                		<!-- 选择类型 -->
							<div class="layui-form-item">
								<div class="layui-input-block">
									<input type="radio" class="layui-radio" name="userType" value="1" title="企业" checked>
									<input type="radio" class="layui-radio" name="userType" value="2" title="个人" >
								</div>
							</div>
							
							<!-- 企业名称、真实姓名 -->
							<div class="layui-form-item">
								<label id="company-label" class="layui-form-label">企业名称：</label>
								<label id="personal-label" class="layui-form-label" style="display:none;">真实姓名：</label>
								<div class="layui-input-block">
									<input type="text" id="name" name="company" lay-verify="required" autocomplete="off" class="layui-input">
		                            <div id="company-prompt-div" class="layui-form-tips"><span class="text-red">*</span>请填写企业名称，1-32字</div>
		                            <div id="personal-prompt-div" class="layui-form-tips" style="display:none;"><span class="text-red">*</span>请填写真实姓名，1-16字</div>
	                            </div>
							</div>
							
							<!-- 身份证号码 -->
							<div id="idCard-div" class="layui-form-item" style="display:none;">
	                            <label class="layui-form-label">身份证号码：</label>
	                            <div class="layui-input-block">
	                                <input type="text" id="idCard" name="idCard" autocomplete="off" class="layui-input">
	                                <div class="layui-form-tips"><span class="text-red">*</span>请填写真实身份证号码</div>
	                            </div>
	                        </div>
	                        
	                       	<!-- 照片选择 -->
	                       	<div class="layui-form-item">
	                       		<label id="company-pic-label" class="layui-form-label">企业工商营业执照：</label>
	                       		<label id="personal-pic-label" class="layui-form-label" style="display:none;">手持身份证照片：</label>
	                       		<div class="layui-input-block">
                                    <div class="weui-uploader">
                                        <div class="weui-uploader__bd">
                                            <ul class="weui-uploader__files" id="uploaderFiles">
                                            </ul>
                                            <div class="weui-uploader__input-box">
                                                <input id="uploaderInput" name="id_pic_file" class="weui-uploader__input" type="file" lay-verify="required" />上传图片
                                            </div>
                                        </div>
                                    </div>
                                    <div id="company-pic-prompt" class="uptips">
	                                        只支持中国大陆工商局或市场监督管理局颁发的工商营业执照，且必须在有效期内。<br>格式要求：原件照片，扫描件或复印件加盖企业公章后的扫描件，支持.jpg .jpeg .bmp .gif .png格式照片，大小不超过1M。
	                                    </div>
                                    <div id="personal-pic-prompt" class="uptips" style="display:none;">支持.jpg .jpeg .bmp .gif.png格式照片，大小不超过1M。</div>
	                              </div>
	                       	</div>
	                       	
	                       	<!-- 业务负责人 -->
	                       	<div id="company-ower-div" class="layui-form-item">
	                            <label class="layui-form-label">业务负责人：</label>
	                            <div class="layui-input-block">
	                                <input type="text" id="companyOwer" name="realName" lay-verify="required" autocomplete="off" class="layui-input">
	                                <div class="layui-form-tips"><span class="text-red">*</span>请填写业务负责人名称，1-16字</div>
	                            </div>
	                        </div>
	                        
	                        <!-- 业务负责人手机 -->
	                        <div id="company-phone-div" class="layui-form-item">
	                        	<label class="layui-form-label">业务负责人手机号：</label>
	                        	<div class="layui-input-block">
	                        		<input type="text" id="phoneNum" name="phoneNum" lay-verify="required" autocomplete="off" class="layui-input">
	                                <div class="layui-form-tips"><span class="text-red">*</span>请填写业务负责人手机号</div>
	                        	</div>
	                        </div>
							
							<!-- 服务区域 -->
							<div class="layui-form-item">
								<label class="layui-form-label">服务区域</label>
								<div class="layui-input-block">
	                            	<select id="add-province" name="province" lay-verify="required" lay-filter="add-province"></select>
	                            	<select id="add-city" name="city" lay-verify="required" lay-filter="add-city"></select>
	                            	<select id="add-area" name="area" lay-verify="required" lay-filter="add-area"></select>
	                      		</div>
							</div>
							
							<!-- qq -->
							<div class="layui-form-item">
								<label class="layui-form-label">QQ号码：</label>
								<div class="layui-input-block">
									<input type="text" name="qq" lay-verify="required" autocomplete="off" class="layui-input">
	                                <div class="layui-form-tips"><span class="text-red">*</span>请填写QQ号码</div>
								</div>
							</div>
							
							<div class="layui-form-item layui-form-button">
								<div class="layui-input-block">
									<button class="layui-btn layui-lg-btn" lay-submit lay-filter="submit-info">提交</button>
	                                <span class="layui-check">
	                                    <input type="checkbox" id="agreeProtocol" lay-verify="required"><span>我同意并遵守《小楼无线账户资料认证服务协议》</span>
	                                </span>
	                            </div>
	                        </div>
						</form>
					</div>
				</c:if>
				
				<!-- 认证中 -->
				<c:if test="${not empty userInfo && userInfo.status==0}">
					<div class="uptips">
						<p>您提交的资料正在审核中，请耐心等待。</p>
					</div>
				</c:if>
				
				<!-- 认证成功 -->
				<c:if test="${not empty userInfo && userInfo.status==1}">
					<div class="layui-qiye">
						<div class="layui-form layui-basic-form">
							<c:if test="${userInfo.userType==1}">
								<div class="layui-form-item">
									<label class="layui-form-label">企业名称：</label>
									<div class="layui-input-block">
		                            	<div class="layui-text">${userInfo.company}</div>
		                          	</div>
	                        	</div>
							</c:if>
							<c:if test="${userInfo.userType==2}">
								<div class="layui-form-item">
									<label class="layui-form-label">真实姓名：</label>
									<div class="layui-input-block">
		                            	<div class="layui-text">${userInfo.realName}</div>
		                          	</div>
	                        	</div>
							</c:if>
	                        
	                        <c:if test="${userInfo.userType==1}">
	                        	<div class="layui-form-item">
									<label class="layui-form-label">负责人：</label>
									<div class="layui-input-block">
		                            	<div class="layui-text">${userInfo.realName}</div>
		                          	</div>
	                        	</div>
	                        </c:if>
	                        <c:if test="${userInfo.userType==2}">
	                        	<div class="layui-form-item">
									<label class="layui-form-label">身份证号码：</label>
									<div class="layui-input-block">
		                            	<div class="layui-text">${userInfo.idCard}</div>
		                          	</div>
	                        	</div>
	                        </c:if>
	                        
	                        
	                        <c:if test="${userInfo.userType==1}">
	                        	<div class="layui-form-item">
									<label class="layui-form-label">联系电话：</label>
									<div class="layui-input-block">
		                            	<div class="layui-text">${userInfo.phoneNum}</div>
		                          	</div>
	                        	</div>
	                        </c:if>
	                        
                        	<div class="layui-form-item">
								<label class="layui-form-label">服务区域：</label>
								<div class="layui-input-block">
	                            	<div class="layui-text">${userInfo.province}${userInfo.city}${userInfo.area}</div>
	                          	</div>
                        	</div>
                        	<div class="layui-form-item">
								<label class="layui-form-label">QQ：</label>
								<div class="layui-input-block">
	                            	<div class="layui-text">${userInfo.qq}</div>
	                          	</div>
                        	</div>
                        </div>
                    </div>
				</c:if>
			</div>
		</div>
		
		<!--footer-->
        <%@include file="../common/footer.jsp"%>
    </div>
    
<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script src="${qc.nginx }cloud/layui.js"></script>
<script src="${qc.nginx }cloud/layer/layer.js"></script>

<script type="text/javascript">
	var IDs=["add-province", "add-city", "add-area"];
</script>

<script type="text/javascript" src="${qc.nginx}script/cloud/places.js"></script>
<script type="text/javascript">
	_init_area(IDs);
</script>
<script rootURL=${qc.rootPath} src="${qc.nginx}script/cloud/personal/userInfoAuth.js"></script>
</body>
</html>