define("user_edit_bsAlert", ["jquery", "bootstrap"], function($){
     function bsAlert(content){
         if(!$("#alert-modal").length) {
             var html = '<div class="modal fade" id="alert-modal" tabindex="-1" role="dialog"> ' +
                 '<div class="modal-dialog modal-sm"> <div class="modal-content"> <div class="modal-header"> ' +
                 '<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
                 '<spanaria-hidden="true">&times;</span></button> <h4 class="modal-title center">用户编辑</h4> ' +
                 '</div> <div class="modal-body">  <p id="edit_username"></p> <span>用户评级：</span> <input type="hidden" id="userId"> ' + 
                 '<select id="userLevel">' +
                 '<option value ="1">优秀</option>' +
                 '<option value="2">良好</option>' +
                 '<option value="3">不良</option>' +
                 '</select> </div> <div class="modal-footer"> ' +
                 '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button> <button type="button" class="btn btn-info" id="commitEdit" data-dismiss="modal">确认</button> </div> ' +
                 '</div> </div> </div>';
             $("body").append(html);
         }
         var a = content.split("/");
         var userId = a[0];
         var username = a[1];
         $("#userId").val(userId);
         $("#edit_username").html("<span>用 户 名 ：</span>" + username);
         $("#alert-modal").modal();
         $("#commitEdit").click(function() {
        	 var userId = $("#userId").val();
        	 var userLevel = $("#userLevel").val();
        	 console.log(userId);
        	 console.log(userLevel);
        	 $.ajax({  
                 type : "get",  
                 url : "user-update?id=" + userId + "&userlevel=" +userLevel,  
                 async : false,  
                 success : function(data){  
                	 console.log(data);
                	 if(data = 1) {
                		 document.getElementById("userInfo").click();
                	 }
                 	}  
                 }); 
        	 
	     });
     	
     }
    return bsAlert;
});