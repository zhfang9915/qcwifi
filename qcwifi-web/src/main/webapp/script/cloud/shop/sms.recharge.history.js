var rootURL = $("script[rootURL]").attr('rootURL');

layui.use('laydate', function(){
    var laydate = layui.laydate;
    rechargeHistory('','');
    //年范围
    laydate.render({
        elem: '#historyDate',
        range: true,
        max: 0,
        done: function(value, date, endDate){
            var start = value.substring(0, 10);
            var end = value.substring(13, 23);
            rechargeHistory(start,end);
          }
    });
});

function rechargeHistory(s,e){
	var data = {};
	if (s){
		data.start=s;
		data.end=e;
	}
	var load = layer.load(2);
  	$.ajax({
  		type : 'POST',  
  		data:jQuery.param(data),
  		dataType:'json',
        url: rootURL + "shop/sms/recharge/history",
        success : function(result) {
			if (result['success']) {
				var tbody = '';
				var srhs = result['data'];
				for (var i = 0; i < srhs.length; i++) {
					tbody += '<tr><td>' + srhs[i].createTime + '</td><td>' + srhs[i].amount + '元</td><td>' + srhs[i].count + '条</td></tr>';
				}
				if(srhs.length==0){
					tbody = '<tr><td colspan="3">当前日期内没有短信充值记录</td></tr>';
				}
				$('#historyBody').html(tbody);
			}else{
				layer.msg(result['msg'], function(){});
			}
		},
		complete : function(){
			layer.close(load);
		}
    });
}