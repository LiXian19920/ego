var TT = EGOU = {
	checkLogin : function(){
		//	代表取得cookie中的值。 $.cookie：取得cookie中的数据。 TT_TOKEN：key。
		//	一张票：存在redis中。以cookie的形式存在。TT_TOKEN-key. value-要登录的对象，用一个uuid来表示.
		//  要想实现单点登录。必须结合cookie，redis缓存。将cookie中存到这个票-uuid。其他应用程序，只需要跨域访问uuid。
		var _ticket = $.cookie("TT_TOKEN");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://localhost:8084/user/token/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var username = data.data.username;
					var html = username + "，欢迎来到易购！<a href=\"http://localhost:8084/user/logout/"+_ticket+"\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
	
	$(".link-logout").live("click",function(){
//		获取标签中href属性值。
		var href=$(this).attr("href");
		$.ajax({
			url:href,
			type:'post',
			jsonp:'callback',
			dataType:'jsonp',
			jsonpCallback:'abc',
			success:function(data){
				if(data.status==200){
					$("#loginbar").html('您好！欢迎来到易购！<a href="javascript:login()">[登录]</a>&nbsp;<a href="javascript:regist()">[免费注册]</a>');
				}
			}
		});
		return false;
	})
	
});