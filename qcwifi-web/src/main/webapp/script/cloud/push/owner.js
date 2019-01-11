var rootURL = $("script[rootURL]").attr('rootURL');

layui.use(['form', 'laydate'], function() {
	var form = layui.form; //表单
	var laydate = layui.laydate;
	laydate.render({ //日期
		elem: '#date01',
		type: 'date'
	});
});