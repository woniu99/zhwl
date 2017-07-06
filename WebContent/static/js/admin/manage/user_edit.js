require(["jquery", "user_edit_bsAlert", "csrfToken", "validator"], function ($, user_edit_bsAlert, csrfTokenHeader) {
	
	//1. 点击 delete 时, 弹出 确定是要删除 xx 的信息吗 ? 
	// 若确定, 执行删除, 若不确定, 则取消
	$(".editUser").click(function(){
		var user_id = $(this).parent().children(".userid").val();
		var user_name = $(this).parent().children(".username").val();
		var param = user_id + "/" + user_name;
		user_edit_bsAlert(param);
		
		
				
	});
});