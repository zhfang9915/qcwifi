var schieberflag = false;

$(function() {
	var rquestTime = (new Date()).getTime();
	var begintime = $("#begintime11").val();
	if (rquestTime - begintime < 60000) {
		var timing = $.cookie('timing')
		$('#huqucode').attr('disabled', 'disabled')
		if (timing > 0) {
			$('#huqucode').addClass("msgs1");
			$('#huqucode').removeAttr("onclick");
			var t = setInterval(function() {
				timing--;
				$.cookie("timing", timing, {
					path : '/',
					expires : (1 / 86400) * timing
				});
				$('#huqucode').html(timing + "秒");
				if (timing == 0) {
					$('#huqucode').removeAttr("disabled");
					clearInterval(t);
					$('#huqucode').html("重新获取");
					validCode = true;
					$('#huqucode').removeClass("msgs1");
					$('#huqucode').attr("onclick");
				}
			}, 1000)
		}
	}
});

/**
 * 手机验证码倒计时
 */
$(function() {
	// 获取短信验证码
	var validCode = true;
	$("#huqucode").click(
			function() {
				if (schieberflag) {
					var fflag = true;
					var urlcs = GetQueryString("channel");
					$("#urlcs").val(urlcs);
					$("#cwxx").text("");
					var phone = $("#phone").val();
					var pwd = $("#password").val();
					hash = hex_md5(pwd);
					var smsCode = $("#smsCode").val();
					if (!checkMobile(phone)) {
						$("#cwxx").text("请输入正确的手机号");
					} else if (!checkPassword(pwd)) {
						$("#cwxx").text("密码不能少于六位");
					} else {
						if (fflag) {
							$.ajax({
								type : "POST",
								url : "login",
								data : {
									"phone" : phone,
									"password" : pwd,
									"smsCode" : smsCode
								},
								dataType : "json",
								success : function(data) {

									fflag = true;
									var random = Math.random();
									$("#shuaxinsmsCode").attr("src",
											'captcha?id=' + random);
									if (data.userInfostatus == "0") {
										$("#cwxx").text(data.error003);
										return;
									} else if (data.tpyzmstatus == "0") {
										$("#cwxx").text(data.error004);
										return;
									} else if (data.hqzmstatus == "0") {
										$("#cwxx").text(data.error003);
										return;
									} else if (data.zmstatus == "0") {
										$("#cwxx").text(data.error002);
										return;
									} else if (data.dxstatus == "0") {
										$("#cwxx").text(data.error001);
									} else if (data.ysmmstatus == "1") {

										$("#cwxx").text("");
										$("#smsCode").val("");
										$('#huqucode').attr('disabled',
												'disabled')
										var time = 60;
										var code = $('#huqucode');
										if (validCode) {
											validCode = false;
											code.addClass("msgs1");
											$(this).unbind();
											$(this).attr('disabled', true);
											var t = setInterval(function() {
												time--;
												$.cookie("timing", time, {
													path : '/',
													expires : (1 / 86400)
															* time
												});
												code.html(time + "秒");
												if (time == 0) {
													$('#huqucode').removeAttr("disabled");
													clearInterval(t);
													code.html("重新获取");
													validCode = true;
													code.removeClass("msgs1");
													$(this).bind().click();
												}
											}, 1000)

										}
									} else if (data.yzcsgd == "td") {
										$("#cwxx").text("请5分钟后重试");
										var time = 300;
										var code = $('#huqucode');
										$('#huqucode').attr('disabled',
												'disabled')
										if (validCode) {
											validCode = false;
											code.addClass("msgs1");
											var t = setInterval(function() {
												time--;
												$.cookie("timing", time, {
													path : '/',
													expires : (1 / 86400)
															* time
												});
												code.html(time + "秒");
												if (time == 0) {
													$('#huqucode').removeAttr(
															"disabled");
													clearInterval(t);
													code.html("重新获取");
													validCode = true;
													code.removeClass("msgs1");
												}
											}, 1000)
										}
									}
								}
							});
						}
					}
				} else {
					$("#cwxx").text("请先通过滑块验证")
				}
			});

});

/**
 * 刷新验证码
 */

$("#shuaxinsmsCode").click(function() {
	var random = Math.random();
	this.src = 'captcha?d=' + random;
})

/**
 * 获取URL上的参数
 */

function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

/**
 * 登录验证
 * 
 */
$("#loginVality").click(
		function() {
			var phone = $("#phone").val();
			var pwd = $("#password").val();
			var smsCode = $("#smsCode").val();
			var khdxsmsCode = $("#khdxsmsCode").val();
			var urlcs = GetQueryString("channel");
			if (!checkMobile(phone)) {
				$("#cwxx").text("请输入正确的手机号");
			} else if (!checkPassword(pwd)) {
				$("#cwxx").text("密码不能少于六位");
			} else {
				$.ajax({
					type : "POST",
					url : "loginValid",
					data : {
						"phone" : phone,
						"password" : pwd,
						"smsCode" : smsCode,
						"khdxsmsCode" : khdxsmsCode,
						"channel" : urlcs
					},
					dataType : "json",
					success : function(data) {
						if (data.smsCodestatus == "0") {
							$("#cwxx").text(data.error004);
							$("#smsCode").val("");
							$("#khdxsmsCode").val("");
							return;
						} else if (data.zmstatus == "0") {
							$("#cwxx").text(data.error001);
							$("#smsCode").val("");
							$("#khdxsmsCode").val("");
							return;
						} else if (data.dyzmstatus == "0") {
							$("#cwxx").text(data.error001);
							$("#smsCode").val("");
							$("#khdxsmsCode").val("");
							return;
						} else if (data.dxzmstatus == "0") {
							$("#cwxx").text(data.error002);
							$("#smsCode").val("");
							$("#khdxsmsCode").val("");
							return;
						} else if (data.goodStatus == "1")
							$.cookie("timing", null, {
								path : '/',
								expires : (1 / 86400) * (-1)
							});
						window.location.href = data.backUrl + "?channel="
								+ data.channel + "&token=" + data.token;
					}
				});
			}
		});

/**
 * 验证密码位数不能小于6
 */
function checkPassword(pass) {
	var reg = /^.{6,}$/;
	var flag = true;
	if (!reg.test(pass)) {
		return flag = false;
	}
	return flag;
}

/**
 * 验证手机号
 * 
 * @param phone
 * @returns {Boolean}
 */
function checkMobile(str) {
	var re = /^1\d{10}$/;
	var flag = true;
	if (!re.test(str)) {
		return flag = false;
	}
	return flag;
}

/**
 * 滑块验证码
 */
$.fn.drag = function(options) {
	var x, drag = this, isMove = false, defaults = {};
	var options = $.extend(defaults, options);
	var handler = drag.find('.handler');
	var drag_bg = drag.find('.drag_bg');
	var text = drag.find('.drag_text');
	var maxWidth = drag.width() - handler.width(); // 能滑动的最大间距

	// 鼠标按下时候的x轴的位置
	handler.mousedown(function(e) {
		isMove = true;
		x = e.pageX - parseInt(handler.css('left'), 10);
	});

	// 鼠标指针在上下文移动时，移动距离大于0小于最大间距，滑块x轴位置等于鼠标移动距离
	$(document).mousemove(function(e) {
		var phone = $("#phone").val();
		var pwd = $("#password").val();
		var _x = e.pageX - x;// _x = e.pageX - (e.pageX -
								// parseInt(handler.css('left'), 10)) = x
		if (isMove) {
			if (_x > 0 && _x <= maxWidth) {
				handler.css({
					'left' : _x
				});
				drag_bg.css({
					'width' : _x
				});
			} else if (_x > maxWidth) { // 鼠标指针移动距离达到最大时清空事件
				$.ajax({
					type : "POST",
					url : "schieberVality",
					data : {
						"phone" : phone,
						"password" : pwd
					},
					dataType : "json",
					cache : false,
					async : false, // 同步
					success : function(data) {
						if (data.flag == "error") {
							top.location.reload();
						}
						schieberflag = true;
						$("#cwxx").text("");
					}
				})
				dragOk();
			}
		}
	}).mouseup(function(e) {
		isMove = false;
		var _x = e.pageX - x;
		if (_x < maxWidth) { // 鼠标松开时，如果没有达到最大距离位置，滑块就返回初始位置
			$("#loginVality").attr("disabled", true);
			handler.css({
				'left' : 0
			});
			drag_bg.css({
				'width' : 0
			});
		}
	});

	// 清空事件
	function dragOk() {
		handler.removeClass('handler_bg').addClass('handler_ok_bg');
		text.removeClass('slidetounlock').text('验证通过').css({
			'color' : '#fff'
		}); // modify
		$("#loginVality").attr("disabled", false);
		// drag.css({'color': '#fff !important'});
		handler.css({
			'left' : maxWidth
		}); // add
		drag_bg.css({
			'width' : maxWidth
		}); // add

		handler.unbind('mousedown');
		$(document).unbind('mousemove');
		$(document).unbind('mouseup');

	}
}
