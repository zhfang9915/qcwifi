<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<div class="layout-side layout-m-side">
	<div class="lay-avator">
		<img src="${qc.nginx }cloud/images/mhead.png" alt="" />
		<div class="lay-avtit">
			<a href="">申请成为服务商</a>
		</div>
	</div>
	<ul class="side-nav">
		<li <c:if test="${currentPage == 'accountSetting'}"> class="active" </c:if>><a href="${qc.rootPath }personal/center/account/settings">账户设置</a></li>
		<li <c:if test="${currentPage == 'userInfoAuth'}"> class="active" </c:if>><a href="${qc.rootPath}personal/center/userInfoAuth/index">资料认证</a></li>
		<li <c:if test="${currentPage == 'assetManagement'}"> class="active" </c:if>><a href="${qc.rootPath }personal/center/assetManagement">资产管理</a></li>
		<li <c:if test="${currentPage == 'messages'}"> class="active" </c:if>><a href="###">消息中心</a></li>
		<li <c:if test="${currentPage == 'orders'}"> class="active" </c:if>><a href="###">订单中心</a></li>
		<li <c:if test="${currentPage == 'safeSetting'}"> class="active" </c:if>><a href="${qc.rootPath }personal/center/safe/settings">安全中心</a></li>
		<li <c:if test="${currentPage == 'wechatNotices'}"> class="active" </c:if>><a href="###">微信通知</a></li>
		<li <c:if test="${currentPage == 'coupon'}"> class="active" </c:if>><a href="${qc.rootPath}personal/center/coupon/index">优惠卡卷</a></li>
	</ul>
</div>