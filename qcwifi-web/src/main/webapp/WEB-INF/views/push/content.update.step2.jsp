<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>推广管理-推广内容</title>
	<link rel="shortcut icon" href="${qc.nginx }favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${qc.nginx }cloud/css/layui.css">
    <link rel="stylesheet" href="${qc.nginx }cloud/css/css.css">
	<link rel="stylesheet" href="${qc.nginx }cloud/css/css2.css">
</head>

<body class="layout-body">
	<div class="layout-wrap">
		<div class="layout-header">
			<%@include file="../common/header.jsp"%>
			<%@include file="push.header.jsp"%>
		</div>
		<!--正文-->
		<div class="layout-main layout-lg-main layui-clear">

			<div class="layui-body-content">
				<div class="layui-row">
					<div class="layui-progressbar">
						<div class="progress-label">
							1.基本信息
						</div>
						<div class="progress-label progress-label-active">
							2.投放选择
						</div>
<!-- 						<div class="progress-label"> -->
<!-- 							3.定向设置 -->
<!-- 						</div> -->
						<div class="progress-label">
							3.确认提交
						</div>
					</div>
				</div>
				<div class="layui-content">
					<input type="hidden" id="contentId" value="${content.id }">
					<form class="layui-form select-area">

						<div class="title">
							选择投放推广位
						</div>
						<div class="layui-label-select">
							<c:if test="${!empty T10001 && T10001.state=='Y'}">
								<!-- 登录海报 -->
								<div class="layui-item-floor T10001">
									<div class="layui-item-titleline">
										<div class="layui-input-inline">
											<input lay-filter="area" type="radio" name="contentTemp" value="T10001" title="登陆中海报" <c:if test="${content.choose.templateId == 'T10001'}"> checked </c:if>>
										</div>
										<span class="miaoshu">WIFI连接中呈现的全屏海报推广内容</span>
										<i class="layui-icon btn-close">&#xe61a;</i>
									</div>
									<div class="layui-item-boxcont" <c:if test="${content.choose.templateId != 'T10001'}"> style="display: none" </c:if>>
										<div class="left-cont">
											<div class="layui-form-item">
												<label class="layui-form-label">海报内容</label>
												<div class="layui-input-block">
													<p class="h-cont <c:if test="${fn:length(fn:split(content.choose.imgs, ','))>1}">h-active</c:if>" data-val="3">3*5"</p>
													<p class="h-cont <c:if test="${fn:length(fn:split(content.choose.imgs, ','))==1}">h-active</c:if>" data-val="1">1*5"</p>
													<div class="h-description">15秒推广内容，3幅海报，每幅展示5秒；</div>
													<div class="h-description" style="display: none">5秒推广内容，3幅海报，每幅展示5秒；</div>
												</div>
											</div>
											<div class="layui-form-item">
												<label class="layui-form-label">海报图片</label>
												<div class="layui-input-block">
													<div class="imgarea">
														<i class="layui-icon">&#xe654;</i>
														<p>添加图片</p>
														<img src="<c:if test="${content.choose.templateId == 'T10001'}">${fn:split(content.choose.imgs, ',')[0]} </c:if>">
													</div>
													<div id="imgarea_more" 
														<c:if test="${fn:length(fn:split(content.choose.imgs, ','))>1}">style="display: inline;"</c:if> 
														<c:if test="${fn:length(fn:split(content.choose.imgs, ','))==1}">style="display: none;"</c:if>>
														<div class="imgarea">
															<i class="layui-icon">&#xe654;</i>
															<p>添加图片</p>
															<img src="<c:if test="${content.choose.templateId == 'T10001'}">${fn:split(content.choose.imgs, ',')[1]} </c:if>">
														</div>
														<div class="imgarea">
															<i class="layui-icon">&#xe654;</i>
															<p>添加图片</p>
															<img src="<c:if test="${content.choose.templateId == 'T10001'}">${fn:split(content.choose.imgs, ',')[2]} </c:if>">
														</div>
													</div>
													<div class="proposal">
														（上传图片建议宽度750px，高度1114px；格式为png/jpg/jpeg/gif；大小在2M以内）
													</div>
												</div>
											</div>
										</div>
										<div class="right-img">
											<img src="${qc.nginx }cloud/images/iPhone.png" alt="">
										</div>
									</div>
								</div>
							</c:if>

							<c:if test="${!empty T10002 && T10002.state=='Y'}">
								<!-- 成功页推广 -->
								<div class="layui-item-floor T10002">
									<div class="layui-item-titleline">
										<div class="layui-input-inline">
											<input type="radio" lay-filter="area" name="contentTemp" value="T10002" title="成功页推广" <c:if test="${content.choose.templateId == 'T10002'}"> checked </c:if>>
										</div>
										<span class="miaoshu">WIFI连接成功后跳转的页面地址</span>
										<i class="layui-icon btn-close">&#xe619;</i>
									</div>
									<div class="layui-item-boxcont" <c:if test="${content.choose.templateId == 'T10002'}"> style="display: none" </c:if>>
										<div class="layui-form-item">
											<label class="layui-form-label">链接地址</label>
											<div class="layui-input-block">
												<input type="text" name="T10002_url" placeholder="配置此链接后，在WIFI连接成功后跳转此地址" autocomplete="off" class="layui-input" style="width: 80%;" <c:if test="${content.choose.templateId != 'T10002'}">value="${content.choose.urls }"</c:if>>
											</div>
										</div>
									</div>
								</div>
							</c:if>

							<c:if test="${!empty T10003 && T10003.state=='Y'}">
								<!-- 底部推广条 -->
								<div class="layui-item-floor T10003">
									<div class="layui-item-titleline">
										<div class="layui-input-inline">
											<input type="radio" lay-filter="area" name="contentTemp" value="T10003" title="底部推广条" <c:if test="${content.choose.templateId == 'T10003'}"> checked </c:if>>
										</div>
										<span class="miaoshu">WiFi使用中底部浮现的推广条</span>
										<i class="layui-icon btn-close">&#xe619;</i>
									</div>
									<div class="layui-item-boxcont" <c:if test="${content.choose.templateId != 'T10003'}"> style="display: none" </c:if>>
										<div class="left-cont">
											<div class="layui-form-item">
												<label class="layui-form-label">时间间隔</label>
<!-- 												<div class="layui-input-inline"> -->
<!-- 													<input type="text" name="title" value="3" autocomplete="off" class="layui-input layui-input-radius"> -->
<!-- 													<span class="miao">秒</span> -->
<!-- 												</div> -->
<!-- 												<p class="layui-description">设置每张图片的轮播时间间隔，1~10秒整数值</p> -->
													<p class="layui-description" style="line-height: 36px;">每张图片的轮播时间间隔为 3 秒</p>
											</div>
											<div class="layui-form-item">
												<label class="layui-form-label">底部图片</label>
												<div class="layui-input-inline">
													<button type="button" class="layui-btn layui-btn-primary layui-uploadimg" id="uplpadimg">
														<i class="layui-icon">&#xe67c;</i>上传图片
													</button>
												</div>
												<p class="layui-description">最多可传5张图，尺寸建议宽度750px，高度 114px；<br>格式为png/jpg/jpeg/gif;大小2M以内</p>
											</div>
											<div class="layui-form-item">
												<label class="layui-form-label"></label>
												<div id="img-content" class="layui-input-inline" style="width: 510px;">
													<c:if test="${content.choose.templateId == 'T10003'}">
														<c:forEach var="imgurl" varStatus="status" items="${fn:split(content.choose.imgs, ',')}">
															<div class="img-content-item">
																<i class="layui-icon">&#x1007;</i>
																<div class="item-list">
																	<img src="${imgurl }"/>
																	<input type="text" name="content_url" placeholder="必填：广告跳转链接" autocomplete="off" class="layui-input" value="${fn:split(content.choose.urls, ',')[status.index]}"/>
																	<p>*</p>
																</div>
															</div>
														</c:forEach>
													</c:if>
												</div>
											</div>
										</div>
										<div class="right-img">
											<img src="${qc.nginx }cloud/images/iphone2.png" alt="">
										</div>
									</div>
								</div>
							</c:if>

						</div>

						<div class="next-btn mt150">
							<div class="layui-form-item">
								<a class="layui-btn layui-btn-primary" href="${qc.rootPath }push/content/update/${content.id }/step1">上一步</a>
								<button class="layui-btn lay-submit" lay-submit lay-filter="submit-content-choose">下一步</button>
							</div>
						</div>
					</form>
				</div>

			</div>
		</div>
		<!--footer-->
		<%@include file="../common/footer.jsp"%>
	</div>
	
	<div id="T10001_upload_img" class="layer-open-content">
		<div class="upload_imgarea" id="localImgUpload">
			<i class="layui-icon">&#xe654;</i>
			<p>本地上传</p>
		</div>
		<div id="listImage" style="display: inline;">
		</div>
	</div>
	
<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script src="${qc.nginx }cloud/layui.js"></script>
<script src="${qc.nginx }cloud/layer/layer.js"></script>
<script rootURL=${qc.rootPath } staticURL=${qc.nginx } src="${qc.nginx }script/cloud/push/content.update.step2.js"></script>
</body>

</html>
