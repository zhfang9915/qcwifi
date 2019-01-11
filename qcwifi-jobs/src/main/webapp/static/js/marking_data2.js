$(document).ready(function () {

$
		    $("#using_json").jstree({
		        "core": {
		            "data": [{
		                "text": "字典组别",
		                "state": {
		                    "opened": true
		                },
		                "children": [{"text": "必选包", "icon": "fa fa-folder", "id": "child001"},
									{"text": "可选包", "icon": "fa fa-folder", "id": "child002"},
									{"text": "促销包", "icon": "fa fa-folder", "id": "child003"},
									{"text": "销售包", "icon": "fa fa-folder", "id": "child004"}],
		                           	 "state": {"opened": true } }
		                    ]

		        }
		    });
		
var marking = [
               {
                   "Tid": "1",
                   "code": "2-V357AIZ",
                   "name": "营销预存100元-预（主套餐预存款）",
                   "createtime": "2017-3-29 16:35:34",
                   "createuser": "admin",
                   "recisetime": "2017-3-29 16:35:43",
                   "reciseuser": "张三"
               },
               {
                   "Tid": "2",
                   "code": "2-1IXTCYEG",
                   "name": "201606乐享4G打折促销活动-5折优惠（自购机）",
                   "createtime": "2017-3-29 16:35:34",
                   "createuser": "admin",
                   "recisetime": "2017-3-29 16:35:43",
                   "reciseuser": "张三"
               },
               {
                   "Tid": "3",
                   "code": "2-V357AQ7",
                   "name": "201407乐享4G201407-副卡-预",
                   "createtime": "2017-3-29 16:35:34",
                   "createuser": "admin",
                   "recisetime": "2017-3-29 16:35:43",
                   "reciseuser": "张三"
               },
               {
                   "Tid": "4",
                   "code": "2-V487RDZ",
                   "name": "201407乐享4G201407 10元副卡功能费-可选包-预",
                   "createtime": "2017-3-29 16:35:34",
                   "createuser": "admin",
                   "recisetime": "2017-3-29 16:35:43",
                   "reciseuser": "张三"
               },
               {
                   "Tid": "5",
                   "code": "2-V357AIZ",
                   "name": "营销预存100元-预（主套餐预存款）",
                   "createtime": "2017-3-29 16:35:34",
                   "createuser": "admin",
                   "recisetime": "2017-3-29 16:35:43",
                   "reciseuser": "张三"
               }];
		   
			$('#table').jqGrid({
				datatype: "local",
				data:'marking',
			   	width:document.body.clientWidth*0.76,
			   //	height:500,
	            shrinkToFit: true,
	            rowNum: 20, //每页显示记录数 
	            rowList: [10, 20, 30],
	            multiselect: true,
			colNames: ["序号", "编码", "名称", "创建时间", "创建人", "修改时间", "修改人"],
            colModel: [
                {
                    name: "Tid", 
                    index: "Tid", 
                    width: 30 
                },
                {
                    name: "code", 
                    index: "code", 
                    width: 80 
                },
                 {
                    name: "name", 
                    index: "name", 
                    width: 300 
                },
                 {
                    name: "createtime", 
                    index: "createtime", 
                    width: 100 
                },
                 {
                    name: "createuser", 
                    index: "createuser", 
                    width: 50 
                },
                 {
                    name: "recisetime", 
                    index: "recisetime", 
                    width: 100 
                },
                 {
                    name: "reciseuser", 
                    index: "reciseuser", 
                    width: 50 
                },
               
            ], 
	            viewrecords: true, 
	            caption: "", 
	            pager : '#pager5'
			});
	var _H=$("body").height();
        $('.ui-jqgrid-bdiv').css("height",(_H - 100) + "px");
  $('#using_json').bind("activate_node.jstree", function (obj, e) {
	 var currentNode = e.node;
	 var urlNum = currentNode.id.substr(currentNode.id.length - (-2,2));

    if(currentNode.id == ('child0' + urlNum)){
      jQuery("#table").setGridParam({url:'js/json/marking_data'+urlNum+'.json'}).trigger("reloadGrid");
    }
   
});


});
$("sales_update_btn").click(function(){
	
})