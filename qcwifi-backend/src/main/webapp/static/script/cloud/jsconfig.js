var rootURL = $("script[rootURL]").attr('rootURL');
var jsconfig = {
		init: function() {
			$('#jscode-table').bootstrapTable({
				url : rootURL + "js/table", // 请求后台的URL（*）
				method : 'post', // 请求方式（*）
				queryParams : jsconfig.queryParams,
				cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, // 是否显示分页（*）
				sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
				pageSize : 10, // 每页的记录行数（*）
				pageList : [10, 25, 50, 100], // 可供选择的每页的行数（*）
				minimumCountColumns : 2, // 最少允许的列数
				toolbar : "#toolbar",
				showColumns : true, // 是否显示所有的列
				showRefresh : true, // 是否显示刷新按钮
				showToggle : true, // 是否显示详细视图和列表视图的切换按钮
				showExport : true,
				detailView : true,
				detailFormatter : jsconfig.detailFormatter,
				columns : [ {
	                checkbox: true,
	                align: 'center',
	                valign: 'middle'
				}, {
					field : 'codeId',
					title : '源码编码'
				}, {
					field : 'codeName',
					title : '源码名称'
				}, {
					field : 'isDefaule',
					align: 'center',
					title : '是否默认',
					formatter : function(value, row, index) {
						if (row.isDefault == 1) {
							return '<span class="glyphicon glyphicon-ok text-success" aria-hidden="true"></span>';
						} else {
							return '<span class="glyphicon glyphicon-remove text-default" aria-hidden="true"></span>';
						}
					}
				}, {
					field : 'serverIp',
					title : '服务器域名/IP'
				}, {
					field : 'serverPort',
					title : '服务器端口'
				}, {
					field : 'createTime',
					title : '创建时间'
				}, {
					title : '操作',
					align: 'center',
					formatter : function(value, row, index) {
						return [
				            '<shiro:hasPermission name="/js/update"><button type="button" class="btn btn-link btn-xs" onClick="jsconfig.editJscodeEvent('+row.id+')">编辑</button></shiro:hasPermission>',
							'<shiro:hasPermission name="/js/delete"><button type="button" class="btn btn-link btn-xs" onClick="jsconfig.deleteJscodeEvent('+row.id+')">删除</button></shiro:hasPermission>'
				        ].join('');
					}
				} ]
			});
		},
		
		/*表格查询参数*/
		queryParams : function(params) {			
			var para = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				pageNumber : this.pageNumber,  
                pageSize : this.pageSize, 
				codeId : $("#search-codeId").val(),
				codeName : $("#search-codeName").val()
			};			
			return para;
		},
		
		detailFormatter : function(index, row) {
	        var html = '<form class="form-horizontal">' +
		        			'<div class="col-md-12">' +
						        '<div class="form-group">' +
					            '<label class="col-sm-1 control-label">备注：</label>' +
					            '<div class="col-sm-11">' +
					                '<p class="form-control-static">'+row.remark+'</p>' +
					            '</div>' +
				            '</div>' +
				            '<div class="col-md-12">' +
					            '<div class="form-group">' +
					            '<label class="col-sm-1 control-label">源码：</label>' +
					            '<div class="col-sm-11">' +
					                '<p class="form-control-static"><pre style="width:95%;height:auto;">'+row.code+'</pre></p>' +
					            '</div>' +
					        '</div>' +
					'</form>';
	        return html;
	    },
	    
	    createJscodeEvent: function() {
	    	$('#createJscodeForm')[0].reset();
			//显示弹出层
			$('#jscode-create-modal').modal({
				show:true,
	            backdrop:'static',
	            keyboard:false
			});
	    },
	    
	    createJscode: function() {
	    	/* var data = $("#createJscodeForm").serialize(); */
			var data = {};
			data.id = $("#create-id").val();
			// 名称
			data.codeName = $("#create-codeName").val();
			if ($.trim(data.codeName) == '') {
				return layer.tips('名称不能为空', '#create-codeName', {tips: [3, '#000']});
			}
			// 域名/IP
			var serverIp = $.trim($("#create-serverIp").val());
			if (serverIp == '') {
				return layer.tips('服务器域名/IP不能为空', '#create-serverIp', {tips: [3, '#000']});
			}
			if (serverIp.length > 20) {
				return layer.tips('服务器域名/IP限定在20字符内', '#create-serverIp', {tips: [3, '#000']});
			}
			data.serverIp = serverIp;
			// 端口
			var serverPort = $.trim($("#create-serverPort").val());
			if (serverPort == '') {
				return layer.tips('服务器端口不能为空', '#create-serverPort', {tips: [3, '#000']});
			}
			if (serverPort.length > 6) {
				return layer.tips('服务器端口限定在6字符内', '#create-serverPort', {tips: [3, '#000']});
			}
			data.serverPort = serverPort;
			// 源码
			data.code = $("#create-code").val();
			if ($.trim(data.code) == '') {
				return layer.tips('源码不能为空', '#create-code', {tips: [3, '#000']});
			}
			
			data.remark = $("#create-remark").val();
			data.isDefault = $('input:radio[name="create-isDefault"]:checked').val();
			
			var load = layer.load(2);
			$.post(rootURL + "js/create", data, function(result) {
				layer.close(load);
				if (result['success']) {
					$('#jscode-create-modal').modal("hide");
					$('#jscode-table').bootstrapTable("refresh");
					layer.msg(result['data']);
				} else {
					layer.msg(result['msg'], function(){});
				}
			}, "json");		
	    },
	    
	    /**
		 * 删除单个JS源码（点击某行后面的'删除'按钮）
		 */
		deleteJscodeEvent : function(codeId, isDefault) {			
			layer.confirm('确定删除这个JS源码吗？', {
			    btn: ['确定删除','取消'], //按钮
			    shade: false //不显示遮罩
			}, function(){
				var load = layer.load(2);
				$.post(rootURL + "js/delete/" + codeId, function(result) {
					layer.close(load);
					if (result['success']) {
						$('#jscode-table').bootstrapTable("refresh");
						layer.msg(result['data']);
					} else {
						layer.msg(result['msg'], function(){});
					}
				}, "json");
			}, function(){});
		},
		
		deleteMultiJscode: function() {
			var arr = $('#jscode-table').bootstrapTable('getSelections');
			if (arr.length > 0) {
				layer.confirm('确定删除这' + arr.length + '个JS吗？', {
				    btn: ['确定删除','取消'],
				    shade: false //不显示遮罩
				}, function() {
					$.ajax({
				        type: "post",
				        contentType: "application/json; charset=utf-8",
				        dataType: "json",     //类型
				        url: rootURL + "js/delete/batch",
				        data: JSON.stringify(arr),
				        success : function(result) {
							if (result['success']) {
								$('#jscode-table').bootstrapTable("refresh");
								layer.msg(result['data']);
							} else {
								layer.msg(result['msg'], function(){});
							}
						}
				    });
				}, function(){});
			} else {
				layer.msg("请选择要删除的数据", function(){});
			}
		},

		/**
		 * 弹出编辑JS源码页面（点击某行后面的'编辑'按钮）
		 */
		editJscodeEvent : function(id) {
			$('#editJscodeForm')[0].reset();
			var load = layer.load(2);
			$.post(rootURL + "js/search/" + id, function(result) {
				layer.close(load);
				if (result['success']) {
					var p = result['data'];
					$("#edit-id").val(id);
					$("#edit-codeName").val(p.codeName);
					$("#edit-serverIp").val(p.serverIp);
					$("#edit-serverPort").val(p.serverPort);
					$("#edit-code").val(p.code);
					$("#edit-remark").val(p.remark);
					if (p.isDefault == 1) {
						$("#edit-isDefault-true").attr("checked","checked");
						$("#edit-isDefault-false").removeAttr("checked");
//						$("input[name=edit-isDefault][value=1]").removeAttr("checked");
//						$("input[name=edit-isDefault][value=0]").attr("checked", "checked");
					} else {
						$("#edit-isDefault-true").removeAttr("checked");
						$("#edit-isDefault-false").attr("checked","checked");
//						$("input[name=edit-isDefault][value=0]").removeAttr("checked");
//						$("input[name=edit-isDefault][value=1]").attr("checked", "checked");
					}
					//显示弹出层
					$('#jscode-edit-modal').modal({
						show:true,//显示弹出层
			            backdrop:'static',//禁止位置关闭
			            keyboard:false//关闭键盘事件
					});
				} else {
					layer.msg(result['msg'], function(){});
				}
			}, "json");
		},
		
		editJscode: function() {
			var data = {};
			data.id = $("#edit-id").val();
			// 名称
			data.codeName = $("#edit-codeName").val();
			if ($.trim(data.codeName) == '') {
				return layer.tips('名称不能为空', '#edit-codeName', {tips: [3, '#000']});
			}
			// 域名/IP
			var serverIp = $.trim($("#edit-serverIp").val());
			if (serverIp == '') {
				return layer.tips('服务器域名/IP不能为空', '#edit-serverIp', {tips: [3, '#000']});
			}
			if (serverIp.length > 20) {
				return layer.tips('服务器域名/IP限定在20字符内', '#edit-serverIp', {tips: [3, '#000']});
			}
			data.serverIp = serverIp;
			// 端口
			var serverPort = $.trim($("#edit-serverPort").val());
			if (serverPort == '') {
				return layer.tips('服务器端口不能为空', '#edit-serverPort', {tips: [3, '#000']});
			}
			if (serverPort.length > 6) {
				return layer.tips('服务器端口限定在6字符内', '#edit-serverPort', {tips: [3, '#000']});
			}
			data.serverPort = serverPort;
			// 源码
			data.code = $("#edit-code").val();
			if ($.trim(data.code) == '') {
				return layer.tips('源码不能为空', '#edit-code', {tips: [3, '#000']});
			}
			
			data.remark = $("#edit-remark").val();
			data.isDefault = $('input:radio[name="edit-isDefault"]:checked').val();
			
			var load = layer.load(2);
			$.post(rootURL + "js/update", data, function(result) {
				layer.close(load);
				if (result['success']) {
					$('#jscode-edit-modal').modal("hide");
					$('#jscode-table').bootstrapTable("refresh");
					layer.msg(result['data']);
				} else {
					layer.msg(result['msg'], function(){});
				}
			}, "json");
		}
};