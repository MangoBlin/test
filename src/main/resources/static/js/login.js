$(document).ready(function(){

    var user={
        username:null,
        password:null
    };
    var findUserUrl = '/user/selectUser';


   $("#loginSub").click(function () {
      console.log("aaaaa");

      user.username = $("#username").val();
      user.password = $("#password").val();
      if (user.username===null||user.password===null||user.username===''||user.password===''){
          layer.msg("用户名或密码不能为空");
      }else {
          $.ajax({
              type: "POST",
              contentType : 'application/json',
              dataType:'json',
              url: findUserUrl,
              data: JSON.stringify(user),
              success: function(data) {
                  if (data.code===1){
                      var usernamee = data.user.username;

                      var setCookieUrl = '/user/setCookie?username='+usernamee;
                      $.ajax({
                          type: "GET",
                          contentType : 'application/json',
                          dataType:'json',
                          url: setCookieUrl,
                          success: function(data) {
                              console.log(data);
                          }
                      });
                      window.location.href='./page/receipt_manage.html';
                  }else {
                      console.log(data);
                      layer.msg(data.msg);
                  }
              }
          });

          console.log(user);

      }
   });
});