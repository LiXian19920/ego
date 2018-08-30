var TTCart = {
	load : function(){ // 加载购物车数据
		
	},
	itemNumChange : function(){
		$(".increment").click(function(){//＋
			var _thisInput = $(this).siblings("input");
			_thisInput.val(eval(_thisInput.val()) + 1);
			$.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".action",function(data){
				TTCart.refreshTotalPrice();
			});
		});
		$(".decrement").click(function(){//-
			var _thisInput = $(this).siblings("input");
			if(eval(_thisInput.val()) == 1){
				return ;
			}
			_thisInput.val(eval(_thisInput.val()) - 1);
			$.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".action",function(data){
				TTCart.refreshTotalPrice();
			});
		});
		$(".quantity-form .quantity-text").rnumber(1);//限制只能输入数字
		$(".quantity-form .quantity-text").change(function(){
			var _thisInput = $(this);
			$.post("/service/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val(),function(data){
				TTCart.refreshTotalPrice();
			});
		});
	},
	refreshTotalPrice : function(){ //重新计算总价
		var total = 0;
		$(".quantity-form .quantity-text").each(function(i,e){
			var _this = $(e);
			total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
		});
		$(".totalSkuPrice").html(new Number(total/100).toFixed(2)).priceFormat({ //价格格式化插件
			 prefix: '￥',
			 thousandsSeparator: ',',
			 centsLimit: 2
		});
	}
};

$(function(){
	TTCart.load();
	TTCart.itemNumChange();
	
	$(".mycart_remove").click(function(){
		var $a =$(this);
//		使用attr属性取得到herf的链接地址！
		var href=$(this).attr("href");
//		异步请求
		$.post(href,function(data){
			if(data.status==200){
				//parent()当前标签的父标签
				$a.parent().parent().parent().remove();
				TTCart.refreshTotalPrice();
			}
		})
		return false;
	})

	
	//对复选框添加点击事件
	$(".checkbox").click(function(){

		var total = 0;
		$(".quantity-form .quantity-text").each(function(i,e){
			var _this = $(e);
			if(_this.parent().parent().siblings(".p-checkbox").children().eq(0)[0].checked){
				total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
			}
		});
		$(".totalSkuPrice").html(new Number(total/100).toFixed(2)).priceFormat({ //价格格式化插件
			 prefix: '￥',
			 thousandsSeparator: ',',
			 centsLimit: 2
		});
	})

	$("#toSettlement").click(function(){
		var param = "";
//		n:循环当前的对象
		$.each($(".checkbox:checked"),function(i,n){
			//alert($(n).val());
			param+="id="+$(n).val();
			if(i<$(".checkbox:checked").length-1){
				param+="&";
			}
		});
		//alert(param);
		location.href=$(this).attr("href")+"?"+param;
		return false;
	});

	
	
	
});