var editData = null;

function eject(queryData) {

    console.log("aaa");
    console.log(queryData);
    if (queryData.isCertification==='已认证'){
        queryData.isCertification='1'
    } else if(queryData.isCertification==='未认证'){
        queryData.isCertification='2'
    } else if(queryData.isCertification==='-'){
        queryData.isCertification='0'
    }

    if (queryData.receiptType==='专票'){
        queryData.receiptType='0'
    } else if(queryData.receiptType==='普票'){
        queryData.receiptType='1';
        queryData.isCertification="2"
    }

    if (queryData.fixedAssets==='非固定资产'){
        queryData.fixedAssets='0'
    } else if(queryData.fixedAssets==='固定资产'){
        queryData.fixedAssets='1';
    } else if(queryData.fixedAssets==='无形资产'){
        queryData.fixedAssets='2';
    }

    var diaIndex =layer.open({
        type:1,
        title:"编辑发票",
        //content:"<div style='height: 200px;width: 200px;'>Hello</div>"
        content:
        '<form class="layui-form" lay-filter="formInit" action="" method="POST" id="myDialog" style="width: auto">'+

            '<div class="layui-form-item">'+
                '<div class="layui-inline">'+
                    '<label class="layui-form-label">发票代码</label>'+
                    '<div class="layui-input-inline">'+
                        '<input type="text" name="receiptCode" required lay-verify="receiptCode" autocomplete="off" class="layui-input"> '+
                    '</div>'+
                '</div>'+

                '<div class="layui-inline">'+
                    '<label class="layui-form-label">发票号码</label>'+
                    '<div class="layui-input-inline">'+
                        '<input type="text" name="receiptNum" required lay-verify="receiptNum" autocomplete="off" class="layui-input"> '+
                    '</div>'+
                '</div>'+

                '<div class="layui-inline">'+
                    '<label class="layui-form-label">销售方名称</label>'+
                    '<div class="layui-input-inline">'+
                        '<input type="text" name="sellerUnitName" required lay-verify="sellerUnitName" autocomplete="off" class="layui-input"> '+
                    '</div>'+
                '</div>'+
            '</div>'+

            '<div class="layui-form-item">'+
                '<div class="layui-inline">'+
                    '<label class="layui-form-label">开票日期</label>'+
                    '<div class="layui-input-inline">'+
                        '<input type="text" name="receiptTime" required lay-verify="receiptTime" autoComplete="off" class="layui-input" id="myDate5"> '+
                    '</div>'+
                '</div>'+

                '<div class="layui-inline">'+
                    '<label class="layui-form-label">校验码(后六位)</label>'+
                    '<div class="layui-input-inline">'+
                        '<input type="text" name="checkDigit" required lay-verify="checkDigit"  autoComplete="off" class="layui-input"> '+
                    '</div>'+
                '</div>'+

                '<div class="layui-inline">'+
                    '<label class="layui-form-label">发票类别</label>'+
                    '<div class="layui-input-inline">'+
                        '<input type="text" name="receiptCategory" autoComplete="off" class="layui-input"> '+
                    '</div>'+
                '</div>'+
            '</div>'+

            '<div class="layui-form-item">'+
                '<div class="layui-inline">'+
                    '<label class="layui-form-label">发票内容</label>'+
                    '<div class="layui-input-inline">'+
                        '<input type="text" name="receiptContent" autoComplete="off" class="layui-input"> '+
                    '</div>'+
                '</div>'+

                '<div class="layui-inline">'+
                    '<label class="layui-form-label">金额</label>'+
                    '<div class="layui-input-inline">'+
                        '<input type="text" name="money" required lay-verify="money" autoComplete="off" class="layui-input"> '+
                    '</div>'+
                '</div>'+

                '<div class="layui-inline">'+
                    '<label class="layui-form-label">税率</label>'+
                    '<div class="layui-input-inline">'+
                        '<input type="text" name="rate" autoComplete="off" class="layui-input"> '+
                    '</div>'+
                '</div>'+
            '</div>'+

            '<div class="layui-form-item">'+
                '<div class="layui-inline">'+
                    '<label class="layui-form-label">税额</label>'+
                    '<div class="layui-input-inline">'+
                        '<input type="text" name="tax" autoComplete="off" class="layui-input"> '+
                    '</div>'+
                '</div>'+

                '<div class="layui-inline">'+
                    '<label class="layui-form-label">价税合计</label>'+
                    '<div class="layui-input-inline">'+
                        '<input type="text" name="leviedTotal" autoComplete="off" class="layui-input"> '+
                    '</div>'+
                '</div>'+

                '<div class="layui-inline">'+
                    '<label class="layui-form-label">专票/普票</label>'+
                    '<div class="layui-input-inline" style="margin-top: 10px">'+
                        '<div style="display: inline"><input type="radio" name="receiptType" value="1" lay-ignore>普票</div>'+
                        '<div style="display: inline;margin-left: 10px"><input type="radio" name="receiptType" value="0" lay-ignore>专票</div>'+
                    '</div>'+
                '</div>'+
            '</div>'+

            '<div class="layui-form-item">'+
                '<div class="layui-inline">'+
                    '<label class="layui-form-label">报销人员</label>'+
                    '<div class="layui-input-inline">'+
                        '<input type="text" name="submitAccountStaff" autoComplete="off" class="layui-input"> '+
                    '</div>'+
                '</div>'+

                '<div class="layui-inline">'+
                    '<label class="layui-form-label">公司/项目</label>'+
                    '<div class="layui-input-inline" style="margin-top: 10px">'+
                        '<div style="display: inline"><input type="radio" name="compProj" value="公司" lay-ignore>公司</div>'+
                        '<div style="display: inline;margin-left: 10px"><input type="radio" name="compProj" value="项目" lay-ignore>项目</div>'+
                    '</div>'+
                '</div>'+

                '<div class="layui-inline">'+
                    '<label class="layui-form-label">部门</label>'+
                    '<div class="layui-input-block">'+
                        '<select name="department" required lay-verify="department">'+
                            '<option value=""></option>'+
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


                // '<div class="layui-inline">'+
                //     '<label class="layui-form-label">是否固定资产</label>'+
                //     '<div class="layui-input-inline" style="margin-top: 10px">'+
                //     '<div style="display: inline"><input type="radio" name="fixedAssets" value="1" lay-ignore>是</div>'+
                //         '<div style="display: inline;margin-left: 10px"><input type="radio" name="fixedAssets" value="0" lay-ignore>否</div>'+
                //     '</div>'+
                // '</div>'+

                '<div class="layui-inline">'+
                    '<label class="layui-form-label">录入时间</label>'+
                    '<div class="layui-input-inline">'+
                        '<input type="text" name="entryTime" autoComplete="off" class="layui-input" id="myDate7"> '+
                    '</div>'+
                '</div>'+

                '<div class="layui-inline">'+
                    '<label class="layui-form-label">固定资产</label>'+
                    '<div class="layui-input-block">'+
                        '<select name="fixedAssets" id="fixedAssets1" required lay-verify="fixedAssets"  lay-filter="fixedAssetsSelect">'+
                        '<option value="0">非固定资产</option>'+
                        '<option value="1">固定资产</option>'+
                        '<option value="2">无形资产</option>'+
                        '</select>'+
                    '</div>'+
                '</div>'+

                '<div class="layui-inline myformItem3 fixAssertClass" style="display: none">'+
                    '<label class="layui-form-label">资产分类</label>'+
                    '<div class="layui-input-block">'+
                        '<input type="text" id="tree1" name="fixAssertClass" lay-filter="tree1" class="layui-input">'+
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
            '</div>'+
            '<div class="layui-form-item">'+
                '<div class="layui-inline">'+
                    '<label class="layui-form-label">备注</label>'+
                    '<div class="layui-input-inline">'+
                        '<input type="text" name="remarks" autoComplete="off" class="layui-input"> '+
                    '</div>'+
                '</div>'+
            '</div>'+

            '<div class="layui-form-item">'+
            '<div class="layui-inline isCertificationClass1 isCertification">'+
            '<label class="layui-form-label">是否认证</label>'+
            '<div class="layui-input-inline" style="margin-top: 10px" id="certificationId">'+
            '<div style="display: inline"><input type="radio" name="isCertification" value="1" lay-ignore>已认证</div>'+
            '<div style="display: inline;margin-left: 10px"><input type="radio" name="isCertification" value="2" lay-ignore>未认证</div>'+
            '</div>'+
            '</div>'+

            '<div class="layui-inline isCertificationClass1 certificationTime">'+
            '<label class="layui-form-label">认证时间</label>'+
            '<div class="layui-input-inline">'+
            '<input type="text" name="certificationTime" autoComplete="off" class="layui-input" id="myDate6"> '+
            '</div>'+
            '</div>'+


            '<div class="layui-inline isCertificationClass1 voucherNum">'+
            '<label class="layui-form-label">凭证号</label>'+
            '<div class="layui-input-inline">'+
            '<input type="text" name="voucherNum" autoComplete="off" class="layui-input"> '+
            '</div>'+
            '</div>'+

            '</div>'+

            '<div class="layui-form-item" id="item2">'+
                '<div class="layui-input-inline">'+
                    '<button class="layui-btn mybutton" lay-submit lay-filter="formSubbmitDia">确认</button>'+
                    // '<button class="layui-btn layui-btn-primary" id="cancelDia">关闭</button>'+
                '</div>'+
            '</div>'+

        '</form>',
        area:['1050px','600px'],
        // area:auto,
        scrollbar: false
    });

    if (queryData.fixedAssets=="0"){
        $(".fixAssertClass").hide();
        $(".IntangibleAssetClass").hide();
    }else if(queryData.fixedAssets=="1"){
        $(".fixAssertClass").show();
        $(".IntangibleAssetClass").hide();
    }else if(queryData.fixedAssets=="2"){
        $(".fixAssertClass").hide();
        $(".IntangibleAssetClass").show();
    }
    $("#cancelDia").click(function () {
        layer.close();
    });

    if (queryData.receiptType=="1"){
        $(".certificationTime").hide();
        $(".voucherNum").hide();
        $(".isCertification").hide();
    }

    $('input[type=radio][name=receiptType]').change(function() {
        if (this.value=="1"){
            $(".certificationTime").hide();
            $(".isCertification").hide();
            $(".voucherNum").hide();
        }else if(this.value=="0"){
            $(".certificationTime").show();
            $(".isCertification").show();
            $(".voucherNum").show();
        }
    });

    layui.use('laydate',function() {
        var laydate = layui.laydate;
        laydate.render({
            elem:'#myDate5',
            type:"date"
        });
        laydate.render({
            elem:'#myDate6',
            type:"date"
        });
        laydate.render({
            elem:'#myDate7',
            type:"datetime"
        });
        laydate.render({
            elem:'#myDate9',
            type:"date"
        });
    });

    var nodeName ={
        parentname:null,
        id:null,
        name:null
    };

    layui.use(['treeSelect','form'], function () {

        var treeSelect= layui.treeSelect;
        var form =layui.form;

        treeSelect.render({
            // 选择器
            elem: '#tree1',
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
                var assertClassFirdir=queryData.assertClassFirdir;
                var assertClassSecdir=queryData.assertClassSecdir;
                var treeId =judgeIdByName(assertClassFirdir,assertClassSecdir);
                if (treeId!="-1"&&treeId!=null&&treeId!=""){
                    treeSelect.checkNode('tree1', treeId);
                }
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
            receiptCode: function (value, item) {
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
            fixedAssets: function(value, item){
                var IntangibleAsset = $('#IntangibleAssetClassId1 option:selected').val();
                // return "false";
                if (value=="2"&&(IntangibleAsset==null||IntangibleAsset==""||IntangibleAsset==undefined)){
                    return "请选择无形资产分类"
                }else if(value=="1"){

                    var treeNode3 = $("#tree1").val();
                    if (treeNode3==""||treeNode3==undefined||treeNode3==null){
                        return "请选择资产分类";
                    }
                }
            }
        });

        var trueFondData ="";
        if (queryData.fixedAssets=="0"){
            trueFondData="";
        }else if(queryData.fixedAssets=="2"){
            trueFondData=queryData.assertClassFirdir;
        }

        form.val("formInit", {
            "receiptCode":queryData.receiptCode,
            "receiptNum":queryData.receiptNum,
            "sellerUnitName":queryData.sellerUnitName,
            "receiptTime":queryData.receiptTime,
            "checkDigit":queryData.checkDigit,
            "receiptCategory":queryData.receiptCategory,
            "receiptContent":queryData.receiptContent,
            "money":queryData.money,
            "rate":queryData.rate,
            "tax":queryData.tax,
            "leviedTotal":queryData.leviedTotal,
            "receiptType":queryData.receiptType,
            "submitAccountStaff":queryData.submitAccountStaff,
            "compProj":queryData.compProj,
            "department":queryData.department,
            "remarks":queryData.remarks,
            // "certificationTime":queryData.certificationTime,
            "entryTime":queryData.entryTime,
            "isCertification":queryData.isCertification,
            "fixedAssets":queryData.fixedAssets,
            "voucherNum":queryData.voucherNum,
            "certificationTime":queryData.certificationTime,
            "IntangibleAssetClass":trueFondData,
        });

        form.on('submit(formSubbmitDia)', function(data){

            editData=form.val("formInit");
            if (editData.receiptType=="1"){
                editData.isCertification="0"
            }
            editData.id=queryData.id;


            if (editData.fixedAssets=="0"){
                editData.assertClassFirdir="";
                editData.assertClassSecdir="";
            }else if(editData.fixedAssets=="1"){
                if(nodeName.parentname!=null&&nodeName.parentname!=""&&nodeName.parentname!=undefined){
                    editData.assertClassFirdir=nodeName.parentname;
                    editData.assertClassSecdir=nodeName.name;
                }else {
                    editData.assertClassFirdir=nodeName.name;
                    editData.assertClassSecdir="";
                }
            }else if(editData.fixedAssets=="2"){
                editData.assertClassFirdir=editData.IntangibleAssetClass;
                editData.assertClassSecdir="";
            }

            var queryEditData=JSON.stringify(editData);
            var editUrl = '/receipt/editReceipt';

            $.ajax({
                type: "POST",
                contentType : 'application/json',
                dataType:'json',
                url: editUrl,
                data: queryEditData,
                success: function(data){
                    layer.msg(data.msg);
                    if (data.code!=-1){
                        layer.close(diaIndex);
                        var table = layui.table;
                        table.reload('demo02', {
                            url: temptUrl,
                            where: {}
                        });
                    }
                }
            });
            return false;
        });
    });


}