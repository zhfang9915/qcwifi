//fId
fId = "";
//var rowData = null;
var lastsel;

//开启任务
function openTask(fid){//当fid不是number时,fid是rowObject一个行对象tr,
	var id = "";
	if(typeof(fid)=='object'){//属于对象
		id = $(fid).attr("id");//如果fid是Object则获取其id属性值
	}else if(typeof(fid)=='number'){//属于number
		id = fid;
	}
	$.ajax({
		type:"post",
		url:"jobs/updateJobs",
		data:{"fId":id,"fCurrentStatus":"1"},
		dataType:"json",
		success:function(data){
			if(data=="1"){
				layer.alert("任务启动成功");
			}else{
				layer.alert("任务启动失败");
			}

			refreshTable();
		}
	});
}

//停止任务
function stopTask(fid){////当fid不是number时,fid是rowObject一个行对象tr,
	var id="";
	if(typeof(fid)=='object'){//属于对象
		id = $(fid).attr("id");//如果fid是Object则获取其id属性值
	}else if(typeof(fid)=='number'){//属于number
		id = fid;
	}
	$.ajax({
		type:"post",
		url:"jobs/updateJobs",
		data:{fId:id,fCurrentStatus:"0"},
		dataType:"json",
		success:function(data){
			if(data=="1"){
				layer.alert("任务已停止");
			}else{
				layer.alert("任务停止失败");
			}
			refreshTable();
		}
	});
}

$.jgrid.defaults.styleUI = "Bootstrap";
$("#table_list_1").jqGrid({
	url: "jobs/searchJobs",
	datatype: "json",
	mtype: "POST",
	height: 500,
	//autowidth: true,
	shrinkToFit: true,
	width:document.body.clientWidth*1,
	pager: "#pager_list_1",
    viewrecords: true,
	rowNum: 15,
	rowList: [15, 20, 30],
	//multiselect: true,
	colNames:["主键","任务名称", "间隔时间", "状态", "接口别名", "任务描述","操作"],
	colModel: [{
		name: "fId", //主键
		index: "fId",
		width:120,
		align:"center",
		key:true,//表示主键，双击修改时传到后台
	},{
		name: "fName",//任务品名称
		index: "fName",
		width:70,
		align:"center",
		editable : true

	},{
		name: "fSchedualTime",//时间
		index: "fSchedualTime",
		width: 60,
		align:"center"
	},{
		name: "fCurrentStatus",//状态
		index: "fCurrentStatus",
		width: 60,
		align:"center",
		formatter: "select", 
		editoptions:{value:"1:<font color='green'>执行中</font>;0:<font color='red'>未开启</font>;"}
	},{
		name: "fClassName",//任务接口别名
		index: "fClassName",
		width: 75,
		align:"center",
		editable : true
	},{
		name: "fJobDesc",//任务描述
		index: "fJobDesc",
		width: 100,
		align:"center",
		editable : true
	},

	{
		label: '操作', name: 'flag', index: 'flag', width: 45, align: 'center',
		formatter: function (cellvalue, options, rowObject) {
			if(rowObject!=undefined){
				if (rowObject.fCurrentStatus=='1'){
					return "<button type='button' class='btn'  data-target='#myModal3' data-toggle='modal' style='margin-right:3px;' onclick='getTaskFid("+rowObject.fId+")'>设置</button><button type='button' class='btn red' onclick='stopTask("+rowObject.fId+")'>停止</button>";
				} 
				if (rowObject.fCurrentStatus=='0'){
					return "<button type='button' class='btn'  data-target='#myModal3' data-toggle='modal' style='margin-right:3px;' onclick='getTaskFid("+rowObject.fId+")'>设置</button><button type='button' class='btn green' onclick='openTask("+rowObject.fId+")'>开启</button>";
				} 
			}
		},
	}],

	pager: "#pager_list_1",
	viewrecords: true,
	caption: "",
	hidegrid: false,
	ondblClickRow: function(id){  
		//if(id && id !== lastsel){  //重复双击
			var rowData = $("#table_list_1").jqGrid("getRowData", id);   
			$('#table_list_1').jqGrid('restoreRow',lastsel); 
			lastsel = id;
			$('#table_list_1').jqGrid('editRow',id,{  
				keys : true,        //这里按[enter]保存  
				url: "jobs/updateJobs",  
				mtype : "POST",  
				restoreAfterError: true,  
				extraparam: {  //获取输入框的参数
					"fId": rowData.fId,  
					"fName": $("#"+id+"_fName").val(),  
					"fJobsDesc": $("#"+id+"_fJobsDesc").val(),  
					"fClassName": $("#"+id+"_fClassName").val() 
				},  
				oneditfunc: function(rowid){  //编辑时执行
					//console.log(rowid);  
				},  
				aftersavefunc:function(rowid,response){//请求后台后执行
					if(response.responseText=='"nullClassName"'){
						layer.alert("任务类名不能为空");
						//$('#table_list_1').jqGrid('restoreRow',lastsel); 
						refreshTable();
						//$('#table_list_1').jqGrid('restoreRow',lastsel); 
					}
					if(response.responseText=='"nullDesc"'){
						layer.alert("任务描述不能为空");
						refreshTable();
					}
					if(response.responseText=='"nullName"'){
						layer.alert("任务名称不能为空");
						refreshTable();
						//$('#table_list_1').jqGrid('restoreRow',lastsel); 
					}
					if(response.responseText=='"reName"'){
						layer.alert("任务名称不能相同");
						refreshTable();
						//$('#table_list_1').jqGrid('restoreRow',lastsel); 
					}
					if(response.responseText=='"1"'){
						layer.alert("修改成功");
					}else if(response.responseText=='"0"'){
						layer.alert("修改失败");
						refreshTable();
					}
				},
				succesfunc: function(response){  
					if(response.responseText=='"nullClassName"'){
						layer.alert("任务类名不能为空");
						//$('#table_list_1').jqGrid('restoreRow',lastsel); 
					}if(response.responseText=='"nullName"'){
						layer.alert("任务名称不能为空");
						//$('#table_list_1').jqGrid('restoreRow',lastsel); 
					}else{
						return true;  
					} 

				},  
				errorfunc: function(rowid, res){  
					//console.log(rowid);  
					//console.log(res);  
				}  
			});  
		//}  
	} ,
	onSelectRow: function (rowid) {
		$('#table_list_1').jqGrid('restoreRow',lastsel); 
	}


//	beforeSelectRow: handleMultiSelect 
}).jqGrid('hideCol', 'cb');

//页面加载事件
$(function(){
	//模态框设置（保存）按钮
	$("#updateSave").bind("click",function(){
		updateTask();
	});

	//单选按钮选择事件
	$(".time").bind("click",function(){
		//清空输入框
		fixedRadio();
	});
	//输入框改变事件
	$("#self-control").click(function(){
		//取消单选
		selfControl();
	});

	//新增
	$("#save").bind("click",function(){
		addTask();
	});
});


//设置按钮点击事件获取更改任务的fId
function getTaskFid(fid){
	//对模态框进行初始化
	if($(".time:checked")!=undefined){
		$(".time:checked").prop("checked",false);
	}
	$("#self-control").prop("checked",false);
	//设置背景颜色
	$("#inp").css({background:"rgba(0,0,0,0.3)"});
	//设置不可用
	$("#inp").prop("disabled","disabled");
	//获取fId
	var id = "";
	if(typeof(fid)=='object'){//属于对象
		id = $(fid).attr("id");//如果fid是Object则获取其id属性值
	}else if(typeof(fid)=='number'){//属于number
		id = fid;
	}
	fId = id;
}

//设置任务时间
function updateTask(){
	var flag;
	//获取设置时间
	var time;
	//验证是否选择或输入
	var checkedTime = $(".time:checked");
	if(checkedTime.length==0){
		flag = false;
		if($("#self-control").prop("checked")==false||$("#self-control").prop("checked")==undefined){
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
		return;
	}

	$.ajax({
		type:"post",
		url:"jobs/updateJobs",
		data:{"fId":fId,"fSchedualTime":time},
		dataType:"json",
		success:function(data){
			$("#myModal3").modal("hide");
			if(data=="1"){
				layer.alert("设置成功");
			}else{
				layer.alert("设置失败");
			}
			refreshTable();
		}
	});
}

//刷新表格
function refreshTable(){
	$("#table_list_1").jqGrid("setGridParam", {
		url: "jobs/searchJobs",
		mtype:"post",
		postData: {}
	}).trigger("reloadGrid");
}


//固定单选按钮选择,自定义输入框不可用
function fixedRadio(){
	//文本框获取焦点则不选择单选按钮
	$("#inp").css({background:"rgba(0,0,0,0.3)"});
	$("#inp").prop("disabled",true);
}

//自定义单选按钮选择，输入框可用
function selfControl(){
	$("#inp").css({background:"white"});
	$("#inp").prop("disabled",false);
}

//新增任务
function addTask(){
	//获取值
	var fName = $("#fName").val();
	var fSchedualTime = $("#fSchedualTime").val();
	var fCurrentStatus = $("#fCurrentStatus").val();
	var fClassName = $("#fClassName").val();
	var fJobDesc = $("#fJobDesc").val();
	//验证是否为空
	var flag = verification($("#add_modal"));
	if(flag==false){
		return;
	}
	//如果通过则验证重名
	$.ajax({
		type:"post",
		url:"jobs/searchJob",
		data:{fName:fName},
		dataType:"json",
		async:false,//同步
		success:function(data){
			if(data.length>0){//length大于0则有重名任务
				$("#c_fName").html("*任务名称不能相同");
				flag = false;
			}
		}
	});
	if(flag==false){
		return;
	}
	$.ajax({
		type:"post",
		url:"jobs/addJobs",
		data:{
			"fName":fName,
			"fSchedualTime":fSchedualTime,
			"fCurrentStatus":fCurrentStatus,
			"fClassName":fClassName,
			"fJobDesc":fJobDesc
		},
		dataType:"json",
		success:function(data){
			//隐藏模态框
			$("#myModal").modal("hide");
			//刷新表格
			refreshTable();
			if(data.result=='1'){
				layer.alert("新增成功");
			}else{
				layer.alert("新增失败");
			}
		},
		error:function(){
			//隐藏模态框
			$("#myModal").modal("hide");
			layer.alert("新增失败");
		}
	});
}

//公共验证
function verification(elem){
	var flag = true;
	elem.find('input').each(function(){
		if ($(this).attr('required') == 'required'){
			if($(this).val() == '' || $(this).val() == undefined){
				$(this).parent().find('a').html('*此项为必填项');
				flag = false;
			} else {
				$(this).parent().find('a').html('');
			}
		}
	});
	return flag;
}