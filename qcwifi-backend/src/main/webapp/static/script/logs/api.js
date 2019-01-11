var rootURL = $("script[rootURL]").attr('rootURL');
$(function(){
	// 初始化表格
	apilog.init();
	
	// 查询指定条件的JS列表（点击表格上方的'查询'按钮）
	$('#apilog-search-button').click(function() {
		$('#apilog-table').bootstrapTable("refresh");
	});
	
	var start = {
		elem : "#search-startTime",
		format : "YYYY-MM-DD",
		max : "2099-06-16",
		choose : function(datas) {
			end.min = datas;
			end.start = datas
		}
	};
	var end = {
		elem : "#search-endTime",
		format : "YYYY-MM-DD",
		max : "2099-06-16",
		choose : function(datas) {
			start.max = datas
		}
	};
	laydate(start);
	laydate(end);

});
var apilog = {
		init: function() {
			$('#apilog-table').bootstrapTable({
				url : rootURL + "system/log/api", // 请求后台的URL（*）
				method : 'post', // 请求方式（*）
				queryParams : apilog.queryParams,
				cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, // 是否显示分页（*）
				sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
				pageNumber:1,//首页页码
				pageSize : 15, // 每页的记录行数（*）
				pageList : [15, 30, 50, 100], // 可供选择的每页的行数（*）
				minimumCountColumns : 2, // 最少允许的列数
				toolbar : "#toolbar",
				showColumns : true, // 是否显示所有的列
				showRefresh : true, // 是否显示刷新按钮
				showToggle : true, // 是否显示详细视图和列表视图的切换按钮
				showExport : true,
				detailView : true,
				detailFormatter : apilog.detailFormatter,
				columns : [ {
					field : 'invokeApi',
					title : '请求接口',
					formatter : function(value, row, index) {
						if (row.invokeApi == "/router/infomation") return "上报路由器设备信息";
						else if (row.invokeApi == "/router/binding") return "获取路由器设备绑定序列码";
						else if (row.invokeApi == "/router/plugin") return "获取路由器插件升级信息";
						else if (row.invokeApi == "/router/firmware") return "获取路由器固件升级信息";
						else if (row.invokeApi == "/router/js") return "获取JS模版";
						else if (row.invokeApi == "/ads/content") return "获取广告资源信息";
						else return '未定义接口';
					}
				}, {
					field : 'requestDate',
					title : '请求日期'
				}, {
					field : 'ip',
					title : 'IP'
				}, {
					field : 'invokeTime',
					title : '接口耗时'
				} ]
			});
		},
		
		/*表格查询参数*/
		queryParams : function(params) {			
			var para = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				pageNumber : this.pageNumber,  
                pageSize : this.pageSize,
				invokeApi : $('#search-invokeApi option:selected').val(),
				startTime : $('#search-startTime').val(),
				endTime : $('#search-endTime').val()
			};			
			return para;
		},
		
		detailFormatter : function(index, row) {
	        var html = '<form class="form-horizontal">' +
					'<div class="col-md-12">' +
				        '<div class="form-group">' +
				            '<label class="col-sm-1 control-label">入参：</label>' +
				            '<div class="col-sm-11">' +
				                '<pre style="width:95%;height:auto;">'+JSON.stringify(JSON.parse(row.params), null, "\t")+'</pre>' +
				            '</div>' +
			            '</div>' +
		            '</div>' +
		            '<div class="col-md-12">' +
			            '<div class="form-group">' +
				            '<label class="col-sm-1 control-label">出参：</label>' +
				            '<div class="col-sm-11">' +
				                '<pre style="width:95%;height:auto;color:green;">'+JSON.stringify(JSON.parse(row.result), null, "\t")+'</pre>' +
				            '</div>' +
				        '</div>' +
			        '</div>' +
			        '<div class="col-md-12">' +
		            	'<div class="form-group">' +
				            '<label class="col-sm-1 control-label">异常：</label>' +
				            '<div class="col-sm-11">' +
				                '<pre style="width:95%;height:auto;color:red;">'+row.exception+'</pre>' +
				            '</div>' +
				        '</div>' +
			        '</div>' +
			'</form>';
		return html;
	}
};