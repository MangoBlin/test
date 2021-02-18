
var loginUserName = null;
$.ajax({
    type: "GET",
    contentType : 'application/json',
    dataType:'json',
    url: '/user/testCookieValue',
    success: function(data) {
        console.log(data);
        loginUserName=data.user.username;
        if (loginUserName==null||loginUserName==undefined){
            window.location.href='../index.html';
        }
        $("#loginUser2").text(loginUserName);
        if (loginUserName=="王夏玲"){
            $("#scanReceipt1").show();
        }
    },
    error:function () {
        window.location.href='../index.html';
    }
});

$("#editPassword").click(function () {
    console.log("11111");
});

$("#receiptManage1").attr("href","./receipt_manage.html");
$("#personal2").attr("href","./personal.html");
$("#scanReceipt1").attr("href","./scan_receipt.html");

updatePass();

layui.use('form', function() {
    var form = layui.form;

    var queryData = loginUserName;
    var queryUserUrl='/user/selectByUsername';

    $.ajax({
        type: "POST",
        contentType : 'application/json',
        dataType:'json',
        url: queryUserUrl,
        data: queryData,
        success: function(data){
            console.log(data);

            form.val("saveForm", {
                "phone": data.phone,
                "name": data.name,
                "password": data.password,
                "sex": data.sex,
                "userDept": data.userDept,
                "role":data.role,
                "userStatus":data.userStatus
            });

            var data1 = form.val("formTest");
        }
    });

    form.on('submit(saveUser)', function(data){
        var submitData = data.field;
        submitData.username=queryData;
        if ('on'===submitData.userStatus){
            submitData.userStatus='1';
        }else if('off'===submitData.userStatus){
            submitData.userStatus='0';
        }

        var updateUserUrl = '/user/updateUser';

        $.ajax({
            type: "POST",
            contentType: 'application/json',
            dataType: 'json',
            url: updateUserUrl,
            data: JSON.stringify(submitData),
            success: function (data) {
                layer.msg("保存成功");
                console.log(data);
            }
        });
        console.log(submitData);

        return false;
    });
});