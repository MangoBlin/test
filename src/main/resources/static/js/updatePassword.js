function updatePass() {
    var layerDialog=null;
    $("#editPassword").click(function () {
        console.log("11111");
        layerDialog=layer.open({
            type: 1,
            title:'<font style="font-size: large;">修改密码</font>',
            content:
            '<form class="layui-form" lay-filter="passwordInit" id="passwordDialog" >'+

            '<div class="layui-form-item" style="margin-top: 20px;margin-bottom: 5px">'+
            '<div class="layui-inline">'+
            '<label class="layui-form-label">旧密码</label>'+
            '<div class="layui-input-inline" style="width: 250px">'+
            '<input type="password" required lay-verify="olderPassword" name="olderPassword" placeholder="请输入原密码"  autocomplete="off" class="layui-input"> '+
            '</div>'+
            '</div>'+
            '</div>'+

            '<div class="layui-form-item" style="margin-top: 5px;margin-bottom: 5px">'+
            '<div class="layui-inline">'+
            '<label class="layui-form-label">新密码</label>'+
            '<div class="layui-input-inline" style="width: 250px">'+
            '<input type="password" required lay-verify="newPassword1" name="newPassword1"   placeholder="请输入新密码" autocomplete="off" class="layui-input"> '+
            '</div>'+
            '</div>'+
            '</div>'+

            '<div class="layui-form-item" style="margin-top: 5px;margin-bottom: 5px">'+
            '<div class="layui-inline" >'+
            '<label class="layui-form-label">新密码</label>'+
            '<div class="layui-input-inline" style="width: 250px">'+
            '<input type="password" required lay-verify="newPassword2" name="newPassword2" placeholder="请再次输入新密码" autocomplete="off" class="layui-input"> '+
            '</div>'+
            '</div>'+
            '</div>'+

            '<div class="layui-form-item" id="item2" style="margin-top: 20px;margin-left: 50px;margin-right: 50px">'+
            '<div class="layui-input-block">'+
            '<button class="layui-btn mybutton" lay-submit lay-filter="passwordSubbmitDia">确认</button>'+
            '</div>'+
            '</div>'+
            '</form>',

            area: ['400px', '300px']
        });
    });

    layui.use('form', function() {
        var form = layui.form;

        form.verify({
            olderPassword: function (value, item) {
                if (value==null||value=="") {
                    return "请输入原密码";
                }
            },
            newPassword1: function (value, item) {
                if (value==null||value=="") {
                    return "请输入新密码";
                }
            },
            newPassword2: function (value, item) {
                if (value==null||value=="") {
                    return "请输入确认新密码";
                }
            }
        });

        form.on('submit(passwordSubbmitDia)', function(data) {
            console.log(data.field);

            var newPassword1=data.field.newPassword1;
            var newPassword2=data.field.newPassword2;
            var olderPassword=data.field.olderPassword;

            if (newPassword1!=newPassword2){
                layer.msg("两次输入的新密码不一致");
            }else {

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

                        var trueOrderPassword = data.password;
                        if (trueOrderPassword!=olderPassword){
                            layer.msg("原密码输入错误");
                        }else {

                            var submitPassword = newPassword1;
                            var editPasswordUrl = '/user/updatePassword';
                            var tempUser = {
                                username:loginUserName,
                                password:submitPassword
                            };
                            $.ajax({
                                type: "POST",
                                contentType : 'application/json',
                                dataType:'json',
                                url: editPasswordUrl,
                                data: JSON.stringify(tempUser),
                                success: function(data){
                                    console.log(data);
                                    layer.msg("更新成功");
                                    layer.close(layerDialog);
                                },
                                error:function () {
                                    layer.msg("更新失败");
                                }
                            });
                        }

                    }
                });
            }

            return false;
        });
    });
}