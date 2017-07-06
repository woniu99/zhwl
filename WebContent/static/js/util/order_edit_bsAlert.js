define("order_edit_bsAlert", ["jquery", "bootstrap"], function($){
     function bsAlert(content){
         if(!$("#alert-modal").length) {
             var html = '<div class="modal fade" id="alert-modal" tabindex="-1" role="dialog"> ' +
                 '<div class="modal-dialog modal-sm"> <div class="modal-content"> <div class="modal-header"> ' +
                 '<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
                 '<spanaria-hidden="true">&times;</span></button> <h4 class="modal-title center">订单编辑</h4> ' +
                 '</div> <div class="modal-body"> <span>配送状态：</span> <input type="hidden" id="orderId">  ' +
                 '<select id="bookStatus">' +
	                 '<option value ="0">待揽件</option>' +
	                 '<option value ="1">运输中</option>' +
	                 '<option value="2">派送中</option>' +
	                 '<option value="3">已签收</option>' +
	             '</select> </div> <div class="modal-footer"> ' +
                 '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button> <button type="button" class="btn btn-info" id="commitEdit" data-dismiss="modal">确认</button> </div> ' +
                 '</div> </div> </div>';
             $("body").append(html);
         }
         $("#orderId").val(content);
         $("#alert-modal").modal();
         $("#commitEdit").click(function() {
        	 var orderId = $("#orderId").val();
        	 var bookStatus = $("#bookStatus").val();
        	 console.log(orderId);
        	 console.log(bookStatus);
        	 $.ajax({  
                 type : "get",  
                 url : "order-update?id=" + orderId + "&bookStatus=" +bookStatus,  
                 async : false,  
                 success : function(data){  
                	 console.log(data);
                	 if(data = 1) {
                		 document.getElementById("orderInfo").click();
                	 }
                 	}  
                 }); 
        	 
	     });
     	
     }
    return bsAlert;
});