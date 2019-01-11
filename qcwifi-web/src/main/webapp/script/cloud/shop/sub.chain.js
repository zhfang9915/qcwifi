var rootURL = $("script[rootURL]").attr('rootURL');

layui.use('table', function() {
	var table = layui.table,
		form = layui.form;
	
	var subId = $('input[name=sub-id]').val();
	var freeIns = table.render({
		elem : '#free-shop-table',
		url : rootURL + 'shop/sub/chain/free/' + subId,
		method: 'POST',
		page : {
			limit: 10,
			limits: [30,50,100],
			theme : '#6161d1'
		},
		width : 500,
		height : 600,
		skin: 'nob',
		size:'lg',
		cols : [ [ 
		{
			type:'checkbox'
		}, {
			field : 'shopName',
			title : '可选商铺',
			edit  : 'text'
		}] ]
	});
	
	var checkedIns = table.render({
		elem : '#checked-shop-table',
		url : rootURL + 'shop/sub/chain/checked/' + subId,
		method: 'POST',
		page : {
			limit: 10,
			limits: [30,50,100],
			theme : '#6161d1'
		},
		width : 500,
		height : 600,
		skin: 'nob',
		size:'lg',
		cols : [ [ 
		{
			type:'checkbox'
		}, {
			field : 'shopName',
			title : '已选商铺',
			edit  : 'text'
		}] ]
	});
	
	form.on('submit(add-sub-shop)', function(data) {
		var checkStatus = table.checkStatus('free-shop-table');
		if (checkStatus.data.length > 0) {
			var load = layer.load(2);
			$.ajax({
				type : 'POST',
				data : JSON.stringify(checkStatus.data),
				dataType : 'json',
				contentType: "application/json; charset=utf-8",
				url : rootURL + "shop/sub/chains/add/" + subId,
				success : function(result) {
					if (result['success']) {
						freeIns.reload();
						checkedIns.reload();
					} else {
						layer.msg(result['msg'], function() {});
					}
				},
				complete : function() {
					layer.close(load);
				}
			});
		}
		return false;
	});
	
	form.on('submit(remove-sub-shop)', function(data) {
		var checkStatus = table.checkStatus('checked-shop-table');
		if (checkStatus.data.length > 0) {
			var load = layer.load(2);
			$.ajax({
				type : 'POST',
				data : JSON.stringify(checkStatus.data),
				dataType : 'json',
				contentType: "application/json; charset=utf-8",
				url : rootURL + "shop/sub/chains/remove/" + subId,
				success : function(result) {
					if (result['success']) {
						freeIns.reload();
						checkedIns.reload();
					} else {
						layer.msg(result['msg'], function() {});
					}
				},
				complete : function() {
					layer.close(load);
				}
			});
		}
		return false;
	});
	

});