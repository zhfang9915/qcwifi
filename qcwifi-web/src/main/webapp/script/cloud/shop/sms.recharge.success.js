var rootURL = $("script[rootURL]").attr('rootURL');

layui.use('form', function(){
	var form = layui.form;
	
	
});


function toPay(orderNo){
	location.href = rootURL + 'shop/sms/recharge/pay/' + orderNo;
}