	    
function assetManagement() {
	//时间范围,通过后台获取最小时间和最大时间
	//date_min为数据库中最小时间
	//date_max为当前时间
    var date_min = "";
    var date_max = new Date().toLocaleString();
    
    //搜索条件
    var searchStartDate = "";
    var searchEndDate = "";
    
    this.query = {
    	balance: function() {
    		$.ajax({
		        type: "post",
		        dataType: "json",
		        url: 'assetManagement/balance',
		        data: '',
		        success: function (data) {
		            if (data != "") {
		            	//alert(JSON.stringify(data));
		            	if (data['success'] == true) {
		            		var dataJson = data['data'];
		            		if (dataJson != null) {
		            			document.getElementById('availableBalance').innerHTML = dataJson['availableBalance'];
		            			document.getElementById("withdrawMinBalance").innerHTML = dataJson['withdrawMinBalance'];
		            			document.getElementById("totalBalance").innerHTML = dataJson['totalBalance'];
			            		document.getElementById("freezeBalance").innerHTML = dataJson['freezeBalance'];
			            		if (dataJson['availableBalance'] >= dataJson['withdrawMinBalance']) {
			            			document.getElementById('withdrawBtn').className = 'layui-btn';
			            			document.getElementById('withdrawBtn').href = "http://www.baidu.com";
			            		}
			            		else {
			            			document.getElementById('withdrawBtn').className = 'layui-btn layui-btn-disable';
			            			document.getElementById('withdrawBtn').href = "#";
			            		}
		            		}
		            	}
		            }
		        }
		    });
    	}
    };
    
	this.table = {
		use: function() {
			layui.use(['laydate', 'layer', 'table'], function() {
			    var table = layui.table; //表格
			    var laypage = layui.laypage; //分页
			    var laydate = layui.laydate;
				
			    var reloadId = 'tableReload';	//重载ID
			    var tableId = 'paymentsDetailsTable';	//表ID
			    var dateId = 'searchByDate'; //时间选择器ID
			    
			    //table 重新加载
			    var tableReload = {
			   		reload: function() {
			   			table.reload(reloadId, {
				      		where: { //设定异步数据接口的额外参数，任意设
				      			startTime: searchStartDate,
				      			endTime: searchEndDate
				      		}
							,page: {
							    curr: 1 //重新从第 1 页开始
							}
			   			});
			    	}
			    };
			    
			    table.render({
			    	id: reloadId
			        ,elem: '#' + tableId
			        ,method: 'POST'
			        ,url: 'assetManagement/paymentsDetails' //数据接口
			        ,where: { //设定异步数据接口的额外参数，任意设
		      			startTime: searchStartDate,
		      			endTime: searchEndDate
		      		}
			        ,page: true  //开启分页
			        ,even: false //是否开启偶数行背景
			        ,skin: 'line' //风格样式 ，可选参数 line/row/nob
			        ,limits: [5, 10, 20, 30, 40, 50, 60, 70, 80, 90]
			        ,limit: 10
			    	//,onSuccess: undefined //渲染成功后的回调
			    	//,height:315
			        ,cols: [[ //表头
			            {field:'createTime', title:'时间', width:221, style:'height:51px'}
			            ,{field:'transactionSerial', title:'交易号', width:221, style:'height:51px'}
			            ,{field:'transactionMoney', title:'交易金额', width:191, style:'height:51px'}
			            ,{field:'transactionType', title:'类型', width:161, style:'height:51px'}
			            ,{field:'remarks', title:'备注', width:164, style:'height:51px'}
			        ]]
			    });
			    
			    laydate.render({
			    	elem: '#' + dateId
			    	,range: true
			    	,type: 'date'
			    	,calendar: true//是否显示公历节日--类型：Boolean，默认值：false
			    	,min: '2017-1-1'//min/max - 最小/大范围内的日期时间值 类型：string，默认值：min: '1900-1-1'、max: '2099-12-31'
			    	,max: new Date().getDate()
			    	,ready: function(date) {//控件在打开时触发，回调返回一个参数
			    	    //console.log('ready: ' + date); //得到初始的日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
			    	 }
				    ,change: function(value, date, endDate) {//日期时间被切换后的回调
				        //console.log('change: ' + value); //得到日期生成的值，如：2017-08-18
				     }
				    ,done: function(value, date, endDate) {//控件选择完毕后的回调---点击日期、清空、现在、确定均会触发。
				        console.log('done: ' + value); //得到日期生成的值，如：2017-08-18
				        searchStartDate = "";
				        searchEndDate = "";
				        if (value != null) {
				        	searchStartDate = value.split(' ')[0] + " 0:0:0";
				        	searchEndDate = value.split(' ')[2] + " 23:59:59";
				        }
				        
				        tableReload.reload();
				     }
			    });
			});
		}
	};
}

var assetManagement = new assetManagement();
assetManagement.table.use();
