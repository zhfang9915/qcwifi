var rootURL = $("script[rootURL]").attr('rootURL');
var staticURL = $("script[staticURL]").attr('staticURL');

//分页参数设置 这些全局变量关系到分页的功能
var limit = 15;//每页显示数据条数
var currPage = 1;//当前页数
var total = 0;//数据总条数

$(function() {
	searchConditions();//搜索条件
	
	getShopInfos();

	toPage();//分页初始化

	// 点击搜索时 搜索数据
	$("#search-by-name").click(function() {
		getShopInfos();
		currPage = 1; // 当点击搜索的时候，应该回到第一页
		toPage();// 然后进行分页的初始化

	});
	
	$(".layout-body").on('click',".filter-wrap a",function(){
        $(this).siblings('a').removeClass('f-active');  // 删除其他兄弟元素的样式
        $(this).addClass('f-active');                            // 添加当前元素的样式

    });
	
});

// 查询商铺信息
function getShopInfos() {
	$.ajax({
		type : "post",
		async : false,
		url : rootURL + "shop/list",
		data : {
			pageNum : currPage,
			pageSize : limit,
			name : $('input[name=shopName]').val()
		},
		success : function(result) {
			renderTable(result['rows']);
			total = result['total'];// 数据总条数
		}
	});
}

function searchConditions(){
	$.ajax({
		type : "post",
		url : rootURL + "shop/list/condition",
		success : function(result) {
			if (result['success']) {
				var operates = result['data'].operates;
				var opbd = '';
				$.each(operates, function(v, o) {
					opbd += '<a href="javascript:;" class="filter-item" data-value="'+o.id+'">'+o.name+'</a>';
				});
				$('#condition-operate').append(opbd);
				var marks = result['data'].marks;
				var mkbd = '';
				$.each(marks, function(v, o) {
					mkbd += '<a href="javascript:;" class="filter-item" data-value="'+o.id+'">'+o.name+'</a>';
				});
				$('#condition-mark').append(mkbd);
			}
		}
	});
}



function renderTable(rows) {
	if (rows.length > 0) {
		var ul = '';
		$.each(rows, function(v, o) {
			var state = '<p><a href="javascript:;" class="st-link st-red">未绑定</a></p>';
			var bname='设备绑定';
			if (!!o.devNo) {
				state = '<p><a href="javascript:;" class="st-link st-green">已绑定</a></p>';
				bname='管理设备';
			}
			ul += '<li id="shop_' + o.shopId + '">'
					+ '<div class="in-shop-box">'
					+ '<img src="' + staticURL + 'cloud/images/shop_smpic.png" class="in-shop-ico" alt="" />'
					+ '<div class="box-intro">'
						+ '<div class="in-shop-name">' + o.shopName + '</div>'
						+ '<div class="in-shop-ads">' + o.province + o.city + o.area + o.address + '</div>'
						+ '</div>'
				+ '</div>'
				+ '<div class="in-shop-link">'
					+ '<a href="" class="in-link">在线设备数</a>'
					+ '<a href="" class="in-link">当前链接人数</a>'
					+ '<a href="" class="in-link">今日认证</a>'
				+ '</div>'
				+ '<div class="in-shop-right">'
					+ '<div class="in-status">'
						+ '<p><a href="javascript:;" class="st-link">在线状态</a></p>'
						+ state
					+ '</div>'
					+ '<div class="in-button">'
						+ '<a href="'+rootURL+'shop/info/'+o.shopId+'" class="layui-btn layui-btn-solid">管理商铺</a>'
						+ '<a href="'+rootURL+'shop/router/'+o.shopId+'" class="layui-btn">'+bname+'</a>'
					+ '</div>'
				+ '</div>'
			+ '</li>';
		});
		$("#shopUL").html(ul);
	} else {
		$("#paged").hide();
		$("#shopUL").html('<ul><h3 style="text-align: center;font-size: 20px;">暂无商铺</h3></ul>');
	}

}

function toPage(){
	layui.use('laypage', function() {
		//调用分页
		layui.laypage.render({
			elem: 'paged',
			layout:['count','prev', 'page', 'next','limit'],
			count: total, //这个是后台返回的数据的总条数
			limit: limit,   //每页显示的数据的条数,layui会根据count，limit进行分页的计算
			limits:[15, 30, 50],
			theme:'#6161d1',
			curr: currPage,
			skip: true,
			jump: function(obj, first){
				console.log(obj);
				currPage = obj.curr;
				limit = obj.limit;
				if(!first){ //一定要加此判断，否则初始时会无限刷新
					getShopInfos();//一定要把翻页的ajax请求放到这里，不然会请求两次。
				}
			}
		});
	});
}