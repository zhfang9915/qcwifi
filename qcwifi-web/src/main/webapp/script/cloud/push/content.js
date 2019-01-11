var rootURL = $("script[rootURL]").attr('rootURL');

layui.use(['form', 'laydate', 'table'], function() {
	var form = layui.form; //表单
	var table = layui.table;
	var laydate = layui.laydate;
	laydate.render({ //日期
		elem: '#date01',
		type: 'date'
	});
	
	var contentTable = table.render({
		elem : '#content-table',
		url : rootURL + 'push/content/list',
		method: 'POST',
		page : {
			limit: 15,
			limits: [30,50,100],
			theme : '#6161d1'
		},
		where : {name : $('input[name=content-name]').val()},
		skin: 'nob',
		size:'lg',
		cols : [ [ 
		{
			field : 'name',
			title : '推广内容名称',
			width : 250,
			fixed : 'left',
			templet: '<div><a href="'+rootURL+'push/content/detail/{{d.id}}" class="layui-table-link">{{d.name}}</a></div>'
		}, {
			field : 'showCount',
			title : '展示量',
			width : 120,
			align : 'center'
		}, {
			field : 'clickCount',
			title : '点击量',
			width : 120,
			align : 'center'
		}, {
			field : 'dayLimit',
			title : '日限额',
			width : 120,
			align : 'center'
		}, {
			field : 'totalLimit',
			title : '总限额',
			width : 120,
			align : 'center'		
		}, {
			field : 'weight',
			title : '推送权重',
			width : 120,
			align : 'center'
		}, {
			field : 'status',
			title : '推送状态',
			align : 'center',
			templet : '#content-status',
			event : 'lock'
		} ] ]
	});
	
	$('#content-search').click(function(){
		contentTable.reload({
		  where: {name : $('input[name=content-name]').val()}
		});
	});
	
	$('.layui-time ul li a').click(function() {
		$(this).addClass('active').parent().siblings().find('a').removeClass('active');
	});
	
	form.on('switch(content-status-switch)', function(obl) {
		var data = {};
		data.value = obl.elem.checked;
		data.filed = obl.elem.name;
		table.on('tool(content-table)', function(obj) {
			if (obj.event === 'lock') {
				$.ajax({
					type : 'POST',
					data : jQuery.param(data),
					dataType : 'json',
					url : rootURL + "push/content/update/" + obj.data.id + "/status",
					success : function(result) {
						if (!result['success']) {
							layer.msg(result['msg'], function() {});
						}
					}
				});
			}
		});
	});
	
});