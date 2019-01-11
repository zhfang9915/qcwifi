var rootURL = $("script[rootURL]").attr('rootURL');

layui.use('table', function() {
	var table = layui.table,
		form = layui.form;

	table.render({
		elem : '#sub-account-table',
		url : rootURL + 'shop/sub/list',
		method: 'POST',
		page : {
			limit: 15,
			limits: [30,50,100],
			theme : '#6161d1'
		},
		skin: 'nob',
		size:'lg',
		cols : [ [ 
		{
			field : 'username',
			title : '连锁管理账号',
			width : 150,
			fixed : 'left'
		}, {
			field : 'nickname',
			title : '连锁管理名称',
			width : 220,
			edit  : 'text'
		}, {
			field : 'name',
			title : '联系人',
			width : 100,
			align : 'center',
			edit  : 'text'
		}, {
			field : 'phone',
			title : '联系电话',
			width : 120,
			align : 'center',
			edit  : 'text'
		}, {
			field : 'shopCount',
			title : '商铺数量',
			width : 90,
			align : 'center',		
		}, {
			field : 'locked',
			title : '账号状态',
			width : 90,
			align : 'center',
			templet : '#sub-locked',
			event : 'lock'
		}, {
			fixed : 'right',
			align : 'left',
			toolbar : '#sub-tool'
		} ] ]
	});
	

	table.on('tool(sub-account-table)', function(obj) {
		if (obj.event === 'shop') {
			window.location.href = rootURL + 'shop/sub/chain/' + obj.data.id;
		} else if (obj.event === 'pass') { //删除
			window.location.href = rootURL + 'shop/sub/pass/' + obj.data.id;
		} 
		else if (obj.event === 'del') { //编辑
			layer.confirm('真的删除该账号吗', function(index) {
				var load = layer.load(2);
				$.ajax({
					type : 'POST',
					data : jQuery.param(obj.data),
					dataType : 'json',
					url : rootURL + "shop/sub/del",
					success : function(result) {
						if (result['success']) {
							obj.del();
							layer.close(index);
						}else{
							layer.msg(result['msg'], function() {});
						}
					},
					complete : function(){
						layer.close(load);
					}
				});
			});

		}
		
	});
	
	table.on('edit(sub-account-table)', function(obj) {
		var data = {};
		data.value = obj.value;
		data.filed = obj.field;
		data.id = obj.data.id;
		data.userId = obj.data.userId;
		$.ajax({
			type : 'POST',
			data : jQuery.param(data),
			dataType : 'json',
			url : rootURL + "shop/sub/edit/" + obj.data.id,
			success : function(result) {
				if (!result['success']) {
					layer.msg(result['msg'], function() {});
				}
			}
		});
	});
	
	form.on('switch(sub-locked-switch)', function(obl) {
		var data = {};
		data.value = obl.elem.checked;
		data.filed = obl.elem.name;
		table.on('tool(sub-account-table)', function(obj) {
			if (obj.event === 'lock') {
				data.userId = obj.data.userId;
				$.ajax({
					type : 'POST',
					data : jQuery.param(data),
					dataType : 'json',
					url : rootURL + "shop/sub/edit/" + obj.data.id,
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