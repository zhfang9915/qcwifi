<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
						<div class="progress-label">
							2.投放选择
						</div>
						<div class="progress-label progress-label-active">
							3.定向设置
						</div>
						<div class="progress-label">
							4.确认提交
						</div>
					</div>
				</div>
				<div class="lay-content">

					<form class="layui-form" action="">
						<div class="lay-title">
							<p>投放时间</p>
							<hr class="layui-bg-gray">
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">投放日期</label>
							<div class="layui-input-block">
								<input type="radio" lay-filter="radio-pushDate" name="pushDate" value="0" title="不限" checked/>
								<input type="radio" lay-filter="radio-pushDate" name="pushDate" value="1" title="自定义"/>
								<div class="layui-inline select-date" id="pushDate-items" style="display:none;">
									<input type="text" class="layui-input" id="pushDate">
									<i class="layui-icon layui-icon-date"></i>
									<input type="hidden" id="pushDate-start"/>
									<input type="hidden" id="pushDate-end"/>
								</div>
							</div>
						</div>

						<div class="lay-title">
							<p>投放定向</p>
							<hr class="layui-bg-gray">
						</div>

						<div class="layui-form-item">
							<label class="layui-form-label">投放区域</label>
							<div class="layui-input-block">
								<input type="radio" lay-filter="radio-city" name="area" value="0" title="不限" checked>
								<input type="radio" lay-filter="radio-city" name="area" value="1" title="自定义">
							</div>
						</div>
						<div class="checklabel" id="city-items" style="display:none;">
							<div class="checklist checkbox">
								<p>省市</p>
								<ul>
									<li><i class="layui-icon">&#xe654;</i><span>上海市</span></li>
									<li><i class="layui-icon">&#xe654;</i><span>广东省</span></li>
									<li><i class="layui-icon">&#xe654;</i><span>四川省</span></li>
									<li><i class="layui-icon">&#xe654;</i><span>重庆市</span></li>
								</ul>
							</div>
							<div class="checkbtn">
								<button class="layui-btn  layui-btn-primary  layui-btn-radius">添加</button>
							</div>
							<div class="checked checkbox">
								<p>已选择<span style="color:red">0</span>个</p>
								<ul>
									<li><i class="layui-icon">&#xe654;</i><span>上海市</span></li>
								</ul>
							</div>
						</div>

						<div class="layui-form-item">
							<label class="layui-form-label">投放行业</label>
							<div class="layui-input-block">
								<input type="radio" lay-filter="radio-category" name="category" value="0" title="不限" checked>
								<input type="radio" lay-filter="radio-category" name="category" value="1" title="自定义">
							</div>
						</div>
						<div class="checklabel" id="category-items" style="display:none;">
							<div class="checklist checkbox">
								<p>用户场景</p>
								<ul>
									<li><i class="layui-icon">&#xe654;</i><span>汽车</span></li>
									<li><i class="layui-icon">&#xe654;</i><span>购物</span></li>
									<li><i class="layui-icon">&#xe654;</i><span>运动健身</span></li>
									<li><i class="layui-icon">&#xe654;</i><span>旅游景点</span></li>
									<li><i class="layui-icon">&#xe654;</i><span>文化广场</span></li>
								</ul>
							</div>
							<div class="checkbtn">
								<button class="layui-btn  layui-btn-primary  layui-btn-radius">添加</button>
							</div>
							<div class="checked checkbox">
								<p>已选择<span style="color:red">0</span>个</p>
							</div>
						</div>


						<div class="layui-form-item">
							<label class="layui-form-label">指定商铺</label>
							<div class="layui-input-block">
								<input type="radio" lay-filter="radio-shop" name="shop" value="0" title="不限" checked>
								<input type="radio" lay-filter="radio-shop" name="shop" value="1" title="自定义">
							</div>
						</div>
						<div class="checklabel" id="shop-items" style="display:none;">
							<div class="checklist checkbox">
								<p>指定商铺（共<span style="color: red">3</span>个）</p>
								<ul>
									<div class="layui-input-inline">
										<i class="layui-icon layui-icon-search">&#xe615;</i>
										<input type="text" name="" placeholder="输入商铺名称查询" autocomplete="off" class="layui-input">
									</div>
									<li><i class="layui-icon">&#xe654;</i><span>上海市交通大学闵行校区（一点点奶茶店）</span></li>
									<li><i class="layui-icon">&#xe654;</i><span>肯德基（洪湖店）</span></li>
									<li><i class="layui-icon">&#xe654;</i><span>肯德基（洪湖店）</span></li>
								</ul>
							</div>
							<div class="checkbtn">
								<button class="layui-btn  layui-btn-primary  layui-btn-radius">添加</button>
							</div>
							<div class="checked checkbox">
								<p>已选择<span style="color:red">0</span>个</p>
							</div>
						</div>


<!-- 						<div class="layui-form-item"> -->
<!-- 							<label class="layui-form-label">指定渠道</label> -->
<!-- 							<div class="layui-input-block"> -->
<!-- 								<input type="radio" name="channel" value="0" title="不限" checked> -->
<!-- 								<input type="radio" name="channel" value="1" title="仅微信"> -->
<!-- 								<input type="radio" name="channel" value="2" title="非微信"> -->
<!-- 							</div> -->
<!-- 						</div> -->
						<div class="layui-form-item">
							<label class="layui-form-label">用户终端</label>
							<div class="layui-input-block terminal">
								<input lay-filter="radio-terminal" type="radio" name="terminal" value="0" title="不限" checked>
								<input lay-filter="radio-terminal" type="radio" name="terminal" value="1" title="自定义">
								<div class="checkbox checkdevice" id="terminal-items" style="display: none">
									<label for=""><input type="checkbox" name="" checked><span>移动端</span></label>
									<label for=""><input type="checkbox" name=""><span>PC端</span></label>
								</div>
							</div>
						</div>
						<div class="next-btn">
							<div class="layui-form-item">
								<a class="layui-btn layui-btn-primary" href="${qc.rootPath }push/content/create/step1">上一步</a>
								<button class="layui-btn lay-submit" lay-submit lay-filter="submit-content-set">下一步</button>
							</div>
						</div>
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
<script rootURL=${qc.rootPath } staticURL=${qc.nginx } src="${qc.nginx }script/cloud/push/content.create.step3.js"></script>
</body>

</html>
