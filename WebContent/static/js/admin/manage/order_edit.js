require(["jquery", "order_edit_bsAlert", "csrfToken", "validator"], function ($, order_edit_bsAlert, csrfTokenHeader) {
	
	//1. 点击 delete 时, 弹出 确定是要删除 xx 的信息吗 ? 
	// 若确定, 执行删除, 若不确定, 则取消
	$(".editOrder").click(function(){
		var order_id = $(this).parent().children(".orderid").val();
		order_edit_bsAlert(order_id);
		
		
				
	});
});