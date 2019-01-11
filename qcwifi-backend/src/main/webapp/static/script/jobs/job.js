$(function(){
	//表格初始化
	job.init();
	
	//点击搜索
	$("#job-search").click(function(){
		$('#job-table').bootstrapTable("refresh");
	});
	
	//创建会员
	$("#job-create-submit").click(function(){
		var data = {};
		data.name = $.trim($("#create-name").val());
		if(data.name == ''){
			return layer.tips('任务名称不能为空', '#create-name', {tips: [3, '#000']});
		}
		data.schedualTime = $.trim($("#create-schedualTime").val());
		if(data.schedualTime == ''){
			return layer.tips('cron不能为空', '#create-schedualTime', {tips: [3, '#000']});
		}
		data.className = $.trim($("#create-className").val());
		if(data.className == ''){
			return layer.tips('实例路径不能为空', '#create-className', {tips: [3, '#000']});
		}
		data.currentStatus = $('input:radio[name="create-currentStatus"]:checked').val();
		data.jobDesc = $.trim($("#create-jobDesc").val());
		var load = layer.load(2);
		$.post(rootURL + "job/create", data, function(result) {
			layer.close(load);
			if(result['success']){
				$('#job-create-modal').modal("hide");
				$("#job-create-form")[0].reset();
				$('#job-table').bootstrapTable("refresh");
				layer.msg(result['data']);
			}else{
				layer.msg(result['msg'], function(){});
			}
		}, "json");
	});
	
	//保存更新
	$("#job-edit-submit").click(function(){
		var flag;
		//获取设置时间
		var time;
		//验证是否选择或输入
		var checkedTime = $(".time:checked");
		if(checkedTime.length==0){
			flag = false;
			if($("#self-control").prop("checked")==false || $("#self-control").prop("checked")==undefined){
				flag = false;
			}else{
				if($("#inp").val()==""){
					flag = false;
				}else{
					time = $("#inp").val();
					flag = true;
				}
			}
		}else{
			time = checkedTime.val();
			flag = true;
		}
		if(flag == false){
			layer.msg('请选择你要执行的cron计划周期', function(){});
			return;
		}

		var load = layer.load(2);
		$.ajax({
			type:"post",
			url:rootURL + "job/cron",
			data:{"id":$('#edit-id').val(),"schedualTime":time},
			dataType:"json",
			success:function(result){
				layer.close(load);
				if(result['success']){
					$("#job-edit-modal").modal("hide");
					$('#job-table').bootstrapTable("refresh");
					layer.msg(result['data']);
				}else{
					layer.msg(result['msg'], function(){});
				}
			}
		});
	});
	
	//单选按钮选择事件
	$(".cron").bind("click",function(){
		//文本框获取焦点则不选择单选按钮
		$("#inp").prop("disabled",true);
		$("#inp").val("");
	});
	//输入框改变事件
	$("#self-control").click(function(){
		$("#inp").prop("disabled",false);
	});
});

var rootURL = $("script[rootURL]").attr('rootURL');
var job = {
	
	init : function() {
		$('#job-table').bootstrapTable({
			url : rootURL + "job/list/pages", // 请求后台的URL（*）
			method : 'post', // 请求方式（*）
			queryParams : job.queryParams,// 传递参数（*）
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1,
			pageSize : 10, // 每页的记录行数（*）
			pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
			minimumCountColumns : 2, // 最少允许的列数
			toolbar : "#toolbar",
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			showExport : true,
			columns : [ {
                checkbox: true,
                align: 'center',
                valign: 'middle'
			}, {
				field : 'id',
				title : 'ID'
			}, {
				field : 'name',
				title : '任务名称'
			}, {
				field : 'schedualTime',
				title : 'cron'
			}, {
				field : 'currentStatus',
				align: 'center',
				title : '状态',
				formatter : function(value, row, index){
					if (value == 1) {
						return '<span class="glyphicon glyphicon-ok-sign text-success" aria-hidden="true"></span>';
					}else {
						return '<span class="glyphicon glyphicon-remove-sign text-danger" aria-hidden="true"></span>';
					}
				}
			}, {
				field : 'className',
				title : '任务路径'
			}, {
				field : 'jobDesc',
				title : '描述'
			}, {
				title : '操作',
				align: 'center',
				formatter : function(value, row, index){
					if(row.currentStatus == '1'){
						return [
				            '<button type="button" class="btn btn-primary btn-xs" data-target="#job-edit-modal" data-toggle="modal" onClick="job.initModal(\''+row.id+'\')"><span class="glyphicon glyphicon-time"></span>设置cron</button>',
				            '  ',
				            '<button type="button" class="btn btn-danger btn-xs" onClick="job.stopJob(\''+row.id+'\')"><span class="glyphicon glyphicon-remove-sign"></span>停止</button>'
				        ].join('');
					}else {
						return [
				            '<button type="button" class="btn btn-primary btn-xs" data-target="#job-edit-modal" data-toggle="modal" onClick="job.initModal(\''+row.id+'\')"><span class="glyphicon glyphicon-time"></span>设置cron</button>',
				            '  ',
				            '<button type="button" class="btn btn-success btn-xs" onClick="job.startJob(\''+row.id+'\')"><span class="glyphicon glyphicon-ok-sign"></span>启动</button>'
				        ].join('');
					}
				}
			} ]
		});
	},
	
	/*表格查询参数*/
	queryParams : function(params) {
		var temp = {
			pageNumber : this.pageNumber,  
            pageSize : this.pageSize,
			id : $("#search-id").val(),
			name : $("#search-name").val(),
			currentStatus : $("#search-currentStatus").val()
		};
		console.log(temp);
		return temp;
	},
	
	initModal : function(id){
		$("#edit-id").val(id);
		//对模态框进行初始化
		if($('.cron:checked') != undefined){
			$('.cron:checked').prop("checked",false);
		}
		$("#self-control").prop("checked",false);
		
		//设置不可用
		$("#inp").prop("disabled","disabled");
	},
	
	stopJob : function (id) {
		var load = layer.load(2);
		$.ajax({
			type:"post",
			url:rootURL + "job/"+id + "/status/" + 0,
			success:function(result){
				layer.close(load);
				if(result['success']){
					$('#job-table').bootstrapTable("refresh");
					layer.msg(result['data']);
				}else{
					layer.msg(result['msg'], function(){});
				}
			}
		});
	},
	
	startJob : function (id) {
		var load = layer.load(2);
		$.ajax({
			type:"post",
			url:rootURL + "job/"+id + "/status/" + 1,
			success:function(result){
				layer.close(load);
				if(result['success']){
					$('#job-table').bootstrapTable("refresh");
					layer.msg(result['data']);
				}else{
					layer.msg(result['msg'], function(){});
				}
			}
		});
	}
	
	
	
}