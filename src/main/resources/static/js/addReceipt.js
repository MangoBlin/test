var loginUserName =null;
$.ajax({
    type: "GET",
    contentType : 'application/json',
    dataType:'json',
    url: '/user/testCookieValue',
    success: function(data) {
        console.log(data);
        loginUserName=data.user.username;
    },
    error:function () {
    }
});

$(document).ready(function(){

    $("#manualAdd").click(function () {

        var diaIndex2 =layer.open({
            type:1,
            title:"手工添加发票",
            content:
            '<form class="layui-form" lay-filter="formInit2" action="#" method="POST" id="myDialog2" >'+

                '<div class="layui-form-item">'+
                    '<div class="layui-inline">'+
                        '<label class="layui-form-label"><text style="color: red;font-size: large;margin-right: 4px;text-align: center">*</text>发票代码</label>'+
                        '<div class="layui-input-inline">'+
                            '<input type="text" name="receiptCode" placeholder="请输入12位发票代码" required lay-verify="receiptCode" autocomplete="off" class="layui-input"> '+
                        '</div>'+
                    '</div>'+

                    '<div class="layui-inline">'+
                        '<label class="layui-form-label"><text style="color: red;font-size: large;margin-right: 4px;text-align: center">*</text>发票号码</label>'+
                        '<div class="layui-input-inline">'+
                            '<input type="text" name="receiptNum" placeholder="请输入8位发票号码" required lay-verify="receiptNum" autocomplete="off" class="layui-input"> '+
                        '</div>'+
                    '</div>'+

                    '<div class="layui-inline">'+
                        '<label class="layui-form-label"><text style="color: red;font-size: large;margin-right: 4px;text-align: center">*</text>销售方名称</label>'+
                        '<div class="layui-input-inline">'+
                            '<input type="text" name="sellerUnitName" placeholder="请输入销售方名称" required lay-verify="sellerUnitName" autocomplete="off" class="layui-input"> '+
                        '</div>'+
                    '</div>'+
                '</div>'+

                '<div class="layui-form-item">'+
                    '<div class="layui-inline">'+
                        '<label class="layui-form-label"><text style="color: red;font-size: large;margin-right: 4px;text-align: center">*</text>开票日期</label>'+
                        '<div class="layui-input-inline">'+
                            '<input type="text" name="receiptTime" placeholder="请输入开票日期" required lay-verify="receiptTime" autoComplete="off" class="layui-input" id="myDate8"> '+
                        '</div>'+
                    '</div>'+

                    '<div class="layui-inline">'+
                        '<label class="layui-form-label"><text style="color: red;font-size: large;margin-right: 4px;text-align: center">*</text>校验码</label>'+
                        '<div class="layui-input-inline">'+
                            '<input type="text" name="checkDigit" placeholder="请输入校验码后六位" required lay-verify="checkDigit" autoComplete="off" class="layui-input"> '+
                        '</div>'+
                    '</div>'+

                    '<div class="layui-inline">'+
                        '<label class="layui-form-label">发票类别</label>'+
                        '<div class="layui-input-inline">'+
                            '<input type="text" name="receiptCategory" placeholder="请输入发票类别" autoComplete="off" class="layui-input"> '+
                        '</div>'+
                    '</div>'+
                '</div>'+

                '<div class="layui-form-item">'+
                    '<div class="layui-inline">'+
                        '<label class="layui-form-label">发票内容</label>'+
                        '<div class="layui-input-inline">'+
                            '<input type="text" placeholder="请输入发票内容" name="receiptContent" autoComplete="off" class="layui-input"> '+
                        '</div>'+
                    '</div>'+

                    '<div class="layui-inline">'+
                        '<label class="layui-form-label"><text style="color: red;font-size: large;margin-right: 4px;text-align: center">*</text>金额</label>'+
                        '<div class="layui-input-inline">'+
                            '<input type="text" name="money" placeholder="请输入金额" lay-verify="money" autoComplete="off" class="layui-input"> '+
                        '</div>'+
                    '</div>'+

                    '<div class="layui-inline">'+
                        '<label class="layui-form-label">税率</label>'+
                        '<div class="layui-input-inline">'+
                            '<input type="text" name="rate" placeholder="请输入税率" autoComplete="off" class="layui-input"> '+
                        '</div>'+
                    '</div>'+
                '</div>'+

                '<div class="layui-form-item">'+
                    '<div class="layui-inline">'+
                        '<label class="layui-form-label">税额</label>'+
                        '<div class="layui-input-inline">'+
                            '<input type="text" name="tax" placeholder="请输入税额" autoComplete="off" class="layui-input"> '+
                        '</div>'+
                    '</div>'+

                    '<div class="layui-inline">'+
                        '<label class="layui-form-label">价税合计</label>'+
                        '<div class="layui-input-inline">'+
                            '<input type="text" name="leviedTotal" placeholder="请输入价税合计" autoComplete="off" class="layui-input"> '+
                        '</div>'+
                    '</div>'+

                    '<div class="layui-inline">'+
                        '<label class="layui-form-label"><text style="color: red;font-size: large;margin-right: 4px;text-align: center">*</text>专票/普票</label>'+
                        '<div class="layui-input-block" style="padding-top: 13px;">'+
                            '<p style="display: inline;margin-right: 5px"><input type="radio" lay-verify="receiptType" name="receiptType" value="1" lay-ignore>普票</p>'+
                            '<p style="display: inline"><input type="radio" lay-verify="receiptType" name="receiptType" value="0" lay-ignore>专票</p>'+
                        '</div>'+
                    '</div>'+
                '</div>'+

                '<div class="layui-form-item">'+
                    '<div class="layui-inline">'+
                        '<label class="layui-form-label"><text style="color: red;font-size: large;margin-right: 4px;text-align: center">*</text>报销人员</label>'+
                        '<div class="layui-input-inline">'+
                            '<input type="text" name="submitAccountStaff" placeholder="请输入报销人员姓名" required lay-verify="submitAccountStaff" autocomplete="off" class="layui-input"> '+
                        '</div>'+
                    '</div>'+

                    '<div class="layui-inline" style="margin-right: 110px">'+
                        '<label class="layui-form-label"><text style="color: red;font-size: large;margin-right: 4px;text-align: center">*</text>公司/项目</label>'+
                        '<div class="layui-input-block" style="padding-top: 13px;">'+
                            '<p style="display: inline;margin-right: 5px"><input type="radio" name="compProj" value="公司" lay-verify="compProj" lay-ignore>公司</p>'+
                            '<p style="display: inline"><input type="radio" name="compProj" value="项目" lay-verify="compProj" lay-ignore>项目</p>'+
                        '</div>'+
                    '</div>'+

                    '<div class="layui-inline">'+
                        '<label class="layui-form-label"><text style="color: red;font-size: large;margin-right: 4px;text-align: center">*</text>部门</label>'+
                        '<div class="layui-input-block">'+
                        '<select name="department" required lay-verify="department">'+
                            '<option value="">请选择部门</option>'+
                            '<option value="综合部">综合部</option>'+
                            '<option value="生产经营部">生产经营部</option>'+
                            '<option value="财务部">财务部</option>'+
                            '<option value="人力资源部">人力资源部</option>'+
                            '<option value="质量信息部">质量信息部</option>'+
                            '<option value="战略发展部">战略发展部</option>'+
                            '<option value="离退休职工服务部">离退休职工服务部</option>'+
                            '<option value="工会">工会</option>'+
                            '<option value="岩土工程院">岩土工程院</option>'+
                            '<option value="测绘院">测绘院</option>'+
                            '<option value="工程勘察院">工程勘察院</option>'+
                            '<option value="数字工程中心">数字工程中心</option>'+
                            '<option value="监测中心">监测中心</option>'+
                            '<option value="计算出版中心">计算出版中心</option>'+
                            '<option value="许昌分公司">许昌分公司</option>'+
                            '<option value="河南省山水水利工程有限公司">河南省山水水利工程有限公司</option>'+
                        '</select>'+
                        '</div>'+
                    '</div>'+
                '</div>'+

                '<div class="layui-form-item">'+
                    '<div class="layui-inline">'+
                        '<label class="layui-form-label">备注</label>'+
                        '<div class="layui-input-inline">'+
                            '<input type="text" placeholder="请输入备注" name="remarks" autoComplete="off" class="layui-input"> '+
                        '</div>'+
                    '</div>'+

                    // '<div class="layui-inline">'+
                    //     '<label class="layui-form-label">是否认证</label>'+
                    //     '<div class="layui-input-block" style="padding-top: 13px;">'+
                    //         '<p style="display: inline;margin-right: 5px"><input type="radio" name="isCertification" value="1" lay-ignore>已认证</p>'+
                    //         '<p style="display: inline"><input type="radio" checked="checked" name="isCertification" value="2" lay-ignore>未认证</p>'+
                    //     '</div>'+
                    // '</div>'+
                '<div class="layui-inline">'+
                    '<label class="layui-form-label">固定资产</label>'+
                    '<div class="layui-input-block">'+
                        '<select name="fixedAssets" id="fixedAssets1"  lay-filter="fixedAssetsSelect">'+
                            '<option value="0">非固定资产</option>'+
                            '<option value="1">固定资产</option>'+
                            '<option value="2">无形资产</option>'+
                        '</select>'+
                    '</div>'+
                '</div>'+

                '<div class="layui-inline myformItem3 fixAssertClass" style="display: none">'+
                    '<label class="layui-form-label">资产分类</label>'+
                    '<div class="layui-input-block">'+
                        '<input type="text" id="tree2" name="fixAssertClass" lay-filter="tree2" class="layui-input">'+
                    '</div>'+
                '</div>'+

                '<div class="layui-inline myformItem3 IntangibleAssetClass" style="display: none">'+
                    '<label class="layui-form-label">无形资产分类</label>'+
                    '<div class="layui-input-block">'+
                        '<select name="IntangibleAssetClass" id="IntangibleAssetClassId1">'+
                            '<option value=""></option>'+
                            '<option value="技术软件">技术软件</option>'+
                            '<option value="研发技术软件">研发技术软件</option>'+
                            '<option value="专利">专利</option>'+
                        '</select>'+
                '</div>'+

                '</div>'+

                // '<div class="layui-form-item" style="margin-top: 20px">'+
                //
                //     '<div class="layui-inline">'+
                //         '<label class="layui-form-label">凭证号</label>'+
                //         '<div class="layui-input-inline">'+
                //             '<input type="text" name="voucherNum" autoComplete="off" class="layui-input"> '+
                //         '</div>'+
                //     '</div>'+
                //
                //
                // '</div>'+
                '</div>'+
                '<div class="layui-form-item" id="item2">'+
                    '<div class="layui-input-inline">'+
                        '<button class="layui-btn mybutton" lay-submit lay-filter="formSubbmitDia2">确认</button>'+
                        // '<button class="layui-btn layui-btn-primary" id="cancelDia2">关闭</button>'+
                    '</div>'+
                '</div>'+

            '</form>',
            area:['1050px','600px'],
            success: function () {
                layui.use('form',function() {
                    var form = layui.form;
                    form.render();
                });
            }
        });

        $("#cancelDia2").click(function () {
            layer.close(layer.index);
        });

        layui.use('laydate',function() {
            var laydate = layui.laydate;
            laydate.render({
                elem:'#myDate8',
                type:"date"
            });
            laydate.render({
                elem:'#myDate9',
                type:"date"
            });
            laydate.render({
                elem:'#myDate10',
                type:"datetime"
            });
        });

        layui.use(['treeSelect','form'], function () {
            var treeSelect= layui.treeSelect;
            var form =layui.form;

            var nodeName ={
                parentname:null,
                id:null,
                name:null
            };
            treeSelect.render({
                // 选择器
                elem: '#tree2',
                // 数据
                data: 'data/data3.json',
                // 异步加载方式：get/post，默认get
                type: 'get',
                // 占位符
                placeholder: '请选择资产类别',
                // 是否开启搜索功能：true/false，默认false
                search: true,
                // 点击回调
                click: function(d){
                    var nodeId =d.current.id;
                    if (nodeId=="5"||nodeId=="6"||nodeId=="333"||nodeId=="334"){
                        nodeName.parentname="02-办公设备";
                    }else if(nodeId=="8"||nodeId=="9"){
                        nodeName.parentname="03-仪器设备";
                    }else if(nodeId=="16"||nodeId=="17"||nodeId=="18"||nodeId=="19"||nodeId=="20"){
                        nodeName.parentname="05-电子设备";
                    }else {
                        nodeName.parentname=null;
                    }
                    nodeName.id=d.current.id;
                    nodeName.name=d.current.name;

                },
                success: function (d) {
                    console.log(d.data);

                }
            });

            form.on('select(fixedAssetsSelect)', function(data){
                var fixedAssetsValue = data.value;
                if (fixedAssetsValue=="0"){
                    $(".fixAssertClass").hide();
                    $(".IntangibleAssetClass").hide();
                }else if(fixedAssetsValue=="1"){
                    $(".fixAssertClass").show();
                    $(".IntangibleAssetClass").hide();
                }else if(fixedAssetsValue=="2"){
                    $(".fixAssertClass").hide();
                    $(".IntangibleAssetClass").show();
                }

            });

            form.verify({
                receiptCode: function(value, item){
                    if(value==""||value==null){
                        return "请输入发票代码"
                    }
                    if (!/^[\s\S]{1,15}$/.test(value)) {
                        return "发票代码不能多于多于15个字符"
                    }
                },
                receiptNum:function (value, item) {
                    if (!/^\d{8}$/.test(value)){
                        return "发票号码必须为长度为8的数字"
                    };
                },

                sellerUnitName:function (value, item) {
                    if(value==""||value==null){
                        return "请填写销售方单位名称"
                    }
                    if (!/^[\s\S]{1,30}$/.test(value)){
                        return "销售方单位名称不能多于30个字符"
                    };
                },
                receiptTime:function (value, item) {
                    if (value==null){
                        return "情选择开票时间"
                    }
                },
                money:function (value, item) {
                    if (!/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9](0-9)?$)/.test(value)){
                        return "请输入正确的金钱格式()"
                    }
                },
                department:function (value, item) {
                    if (value==""||value==null||value==undefined){
                        return "请输入部门"
                    }
                },
                receiptType:function (value, item) {
                    var receiptTypee = $('input[name="receiptType"]:checked').val();
                    if(receiptTypee=""||receiptTypee==null||receiptTypee==undefined){
                        return "请选择专票/普票"
                    }
                },
                compProj:function (value, item) {
                    var compProjj = $('input[name="compProj"]:checked').val();
                    if(compProjj=""||compProjj==null||compProjj==undefined){
                        return "请选择公司/项目"
                    }
                },
                submitAccountStaff:function (value, item) {
                    if(value==""||value==null){
                        return "请填写报销人员名称"
                    }
                    if (!/^[\s\S]{1,6}$/.test(value)){
                        return "报销人员名称不能多于6个字符"
                    };
                }
            });

            form.on('submit(formSubbmitDia2)', function(data){
                var addQueryData = form.val("formInit2");
                addQueryData.handlerUser=loginUserName;

                if (addQueryData.fixedAssets=="0"){
                    addQueryData.assertClassFirdir="";
                    addQueryData.assertClassSecdir="";
                }else if(addQueryData.fixedAssets=="1"){
                    if (nodeName.parentname!=null&&nodeName.parentname!=""&&nodeName.parentname!=undefined){
                        addQueryData.assertClassFirdir=nodeName.parentname;
                        addQueryData.assertClassSecdir=nodeName.name;
                    }else {
                        addQueryData.assertClassFirdir=nodeName.name;
                        addQueryData.assertClassSecdir="";
                    }

                }else if(addQueryData.fixedAssets=="2"){
                    addQueryData.assertClassFirdir=addQueryData.IntangibleAssetClass;
                    addQueryData.assertClassSecdir="";
                }

                console.log(addQueryData);

                var manualAddUrl='/receipt/addReceipt';
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    dataType:'json',
                    url: manualAddUrl,
                    data: JSON.stringify(addQueryData),
                    success: function(data){
                        if (data.code===0){
                            console.log(data);
                            layer.msg(data.msg);
                            layer.close(diaIndex2);

                            var countUrl='/receipt/countReceiptNum';
                            var totalNum = null;
                            var totalText =null;
                            $.ajax({
                                type: "POST",
                                contentType : 'application/json',
                                dataType:'json',
                                url: countUrl,
                                data: JSON.stringify(pageParam),
                                success: function(data){
                                    totalNum=data;
                                    totalText = '共'+totalNum+'张发票';
                                    $("#receiptNums").text(totalText);
                                }
                            });

                            var table = layui.table;
                            table.reload('demo02', {
                                url: temptUrl,
                                where: {}
                            });
                        }else if(data.code===-1){
                            console.log(data.data[0]);
                            var diaIndex3 =layer.open({
                                type: 0,
                                title: "发票重复提示",
                                content: '<h3 style="text-align: center;padding-bottom: 15px">发票重复报销提示</h3>' +
                                            '<p>发票信息保存失败，该发票已经报销过，相关信息如下:</p>'+
                                            '<table class="layui-table" lay-skin="line" style="width: 300px">'+
                                            '<colgroup>'+
                                            '<col width="100">'+
                                            '<col width="150">'+
                                            '</colgroup>'+
                                            '<tbody>'+
                                            '<tr>'+
                                            '<td>报销人</td>'+
                                            '<td id="tb1"></td>'+
                                            '</tr>'+
                                            '<tr>'+
                                            '<td>发票代码</td>'+
                                            '<td id="tb2"></td>'+
                                            '</tr>'+
                                            '<tr>'+
                                            '<td>发票号码</td>'+
                                            '<td id="tb3"></td>'+
                                            '</tr>'+
                                            '<tr>'+
                                            '<td>销售方名称</td>'+
                                            '<td id="tb4"></td>'+
                                            '</tr>'+
                                            '<tr>'+
                                            '<td>开票日期</td>'+
                                            '<td id="tb5"></td>'+
                                            '</tr>'+
                                            '<tr>'+
                                            '<td>金额</td>'+
                                            '<td id="tb6"></td>'+
                                            '</tr>'+
                                            '<tr>'+
                                            '<td>录入时间</td>'+
                                            '<td id="tb7"></td>'+
                                            '</tr>'+
                                            '</tbody>'+
                                        '</table>',
                                area: ['350px', '450px'],
                                resize:false
                            });

                            $("#tb1").html(data.data[0].submitAccountStaff);
                            $("#tb2").html(data.data[0].receiptCode);
                            $("#tb3").html(data.data[0].receiptNum);
                            $("#tb4").html(data.data[0].sellerUnitName);
                            $("#tb5").html(data.data[0].receiptTime);
                            $("#tb6").html(data.data[0].money);
                            $("#tb7").html(data.data[0].entryTime);
                        }else {
                            layer.msg(data.msg);
                        }
                    }
                });

                return false;
            });
        });
    });
});