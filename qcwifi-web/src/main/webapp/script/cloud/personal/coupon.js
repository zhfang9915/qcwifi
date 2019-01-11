var rootURL = $("script[rootURL]").attr('rootURL');

$(function(){
	// 查询所有的优惠券
	queryAllCoupons();
	
	//打开兑换弹框
    $('#exchangeAction').click(function(){
        $('#layuiExchange').show();
        $('.layui-layer-shade').show();
    });

});

//获取全部的优惠券
function queryAllCoupons(){
	var load = layer.load(2);
	$.ajax({
		type : 'GET',  
		dataType:'json',
        url: rootURL + "personal/center/coupon/list",    //提交的页面，方法名
        success : function(result) {
        	console.log(result);
        	layer.close(load);
			if (result['success']) {
				var arr = result['data'];
				var coupons1 = new Array();
				var coupons2 = new Array();
				
				for (var i = 0; i < arr.length; i++) {
					var coupon = arr[i];
					var listID;
					var status;
					var statusClass = '';
					if (coupon['type'] == '1') {
						coupons1.push(coupon);
						listID = '#couponType1List';
					} else {
						coupons2.push(coupon);
						listID = '#couponType2List';
					}
					if (coupon['status'] == '0') {
						// 已使用
						status = '已使用';
						statusClass = 'coupon-box-disable';
					} else {
						// 未使用
						status = '未使用';
					}
					// 判断是否过期
					var nowDate = new Date();
					var endDate = new Date(coupon['endDate']);
					if (endDate < nowDate) {
						// 已过期
						status = '已过期';
						statusClass = 'coupon-box-disable';
					}
					$(listID).append('<li><a href="javascript:;" class="coupon-box ' + statusClass + '">' +
							'<div class="coupon-intro"><div class="coupon-icon"><img src="images/10.png" alt="" /></div>' +
							'<div class="coupon-desc">' + '本卷有效期至：' + coupon['endDate'] + '<br>' + coupon['remark'] + '</div></div>' +
							'<div class="coupon-footer"><span>' + status + '</span></div>' +
							'</a></li>');
				}
				console.log(coupons1);
				$('#couponType1').text("抵用券（" + coupons1.length + "）");
				$('#couponType2').text("优惠券（" + coupons2.length + "）");
				
			}else{
				layer.msg(result['msg'], function(){});
			}
		},
		complete : function(){
			console.log('aaaaaaaaaaaaassssssssssssssss');
			layer.close(load);
		}
    });
}