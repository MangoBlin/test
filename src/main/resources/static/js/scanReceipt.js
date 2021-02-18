var loginUserName = null;
function openImage(PicPath) {
    if (PicPath == 0 || PicPath == undefined || PicPath == "null" || PicPath == "undefined") {
        PicPath = null;
    }
    if (!PicPath) {
        layer.msg("暂无图片");
        return;
    }
    $("#myViewer").html("");
    var result = PicPath.split(",");
    for (var i = 0; i < result.length; i++) {
        var strurl = result[i].replace(/\\/g, "\/");
        var img = "<img src='" + strurl + "' alt='图片'/>";
        $("#myViewer").append(img);
    }

    var viewer = new Viewer(document.getElementById('myViewer'), {
        url: 'src',
        zIndex: 999999,
        hidden: function () {
        }
    });
    $("#myViewer>img")[0].click();
}

// layer.open({
//     type: 0,
//     title:'<font style="font-size: large;color: red">扫描说明</font>',
//     content: '1.点击页面上的"开始扫描"按钮进入扫描状态'+'<br>'+'<br>'+
//              '2.将需要扫描的发票背面朝上放入扫描仪中按下扫描仪中的"scan"键,等待打印机扫描完成'+'<br>'+'<br>'+
//              '3.等待扫描仪扫描完完所有的发票之后,等待3到5秒,点击页面上的"扫描完成"按钮'+'<br>'+'<br>'+
//              '4.扫描完成后可以点击表格中的发票信息查看发票信息是否正确'+'<br>'+'<br>'+
//              '5.确认无误后点击右上方的"上传"按钮(重复的发票会在上传时自动删除,其他发票则会录入)'+'<br>'+'<br>'+
//              '6.上传完成后的发票会在发票管理页面显示'+'<br>',
//     area: ['500px', '500px'],
//     shade: 0,
//     fixed: true,
//     offset: 'rb'
// });

$.ajax({
    type: "GET",
    contentType : 'application/json',
    dataType:'json',
    url: '/user/testCookieValue',
    success: function(data) {
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

updatePass();
// function myFunction() {
//     var delTempUrl = '/receiptScan/delAllScanReceipt';
//     $.ajax({
//         type: "GET",
//         contentType : 'application/json',
//         dataType:'json',
//         url: delTempUrl,
//         success: function(data) {
//             console.log(data);
//         }
//     });
// }

findSubbmitAccout();
function findSubbmitAccout() {
    var array =null;
    var selectSubbmitUrl ='/receiptScan/selectSubbmitList';
    $.ajax({
        type: "GET",
        contentType: 'application/json',
        dataType: 'json',
        url: selectSubbmitUrl,
        success: function (data) {
            array=data;

            var autoComplete = new AutoComplete("isSubmitaccountStaff", "auto", array);
            //autoComplete.init();
            $(window).on('keyup', function (event) {
                autoComplete.start(event);
            });
        }
    });
}

$("#receiptManage1").attr("href","./receipt_manage.html");
$("#personal2").attr("href","./personal.html");
$("#scanReceipt1").attr("href","./scan_receipt.html");

var uploadData=null;
var queryData =null;

var receiptTimem = null;
function initUploadVal(uploadData) {

    for (var key in uploadData){
        if (uploadData[key].id==queryData.id){
            uploadData[key].receiptCode= $("input[name='receiptCode']").val();
            uploadData[key].receiptNum=$("input[name='receiptNum']").val();
            uploadData[key].sellerUnitName=$("input[name='sellerUnitName']").val();
            // uploadData[key].receiptTime=receiptTimem;
            uploadData[key].checkDigit=$("input[name='checkDigit']").val();
            uploadData[key].receiptCategory=$("input[name='receiptCategory']").val();
            uploadData[key].receiptContent=$("input[name='receiptContent']").val();
            uploadData[key].money=$("input[name='money']").val();
            uploadData[key].rate=$("input[name='rate']").val();
            uploadData[key].tax=$("input[name='tax']").val();
            uploadData[key].leviedTotal=$("input[name='leviedTotal']").val();
            uploadData[key].receiptType=$("input[name='receiptType']:checked").val();
            uploadData[key].fixedAssets=$("select[name='fixedAssets'] option:selected").val();
            uploadData[key].remarks=$("input[name='remarks']").val();
            if (uploadData[key].fixedAssets==="0"){
                uploadData[key].assertClassFirdir=null;
                uploadData[key].assertClassSecdir=null;
            }else if(uploadData[key].fixedAssets==="1"){
            }else if(uploadData[key].fixedAssets==="2"){
                uploadData[key].assertClassFirdir=$("#IntangibleAssetClassId").val();
                uploadData[key].assertClassSecdir=null;
            }
        }
    }
}
layui.use('laydate',function() {
    var laydate = layui.laydate;

    laydate.render({
        elem: '#scanDate1',
        type: "date",
        done: function(value, date, endDate){
            receiptTimem=value;
            console.log(receiptTimem);
            initUploadVal(uploadData);
        }
    });
});

$("#scanReceiptMsg1").hide();
$("#scanReceiptMsg2").show();

var jsMap = new Map();
layui.use(['treeSelect','form','table'], function(){
    var nodeName ={
        parentname:null,
        id:null,
        name:null
    };
    var assertClassFirdir=null;
    var assertClassSecdir=null;
    var treeSelect= layui.treeSelect;
    var form =layui.form;

    treeSelect.render({
        // 选择器
        elem: '#tree',
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

            jsMap.set(queryData.id,nodeName);

            nodeName ={
                parentname:null,
                id:null,
                name:null
            };
        },
        success: function (d) {
        }

    });

    form.on('select(fixedAssetsSelect)', function(data){
        initUploadVal(uploadData);
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


    var table = layui.table;

    var countScanUrl  = '/receiptScan/countScanReceiptNum';
    $.ajax({
        type: "GET",
        contentType : 'application/json',
        dataType:'json',
        url: countScanUrl,
        success: function(data){
            var countData = data;
            var tempRequestUrl = '/receiptScan/selectScanReceipts?pageNum='+1+'&pageSize='+countData;
            $.ajax({
                type: "GET",
                contentType : 'application/json',
                dataType:'json',
                url: tempRequestUrl,
                success: function(data){

                    if (data.code==-1){
                        layer.msg("扫描识别失败");
                    }
                    uploadData=data.data;
                }
            });
        }
    });

    var requestUrl = '/receiptScan/selectScanReceipts';
    table.render({
        elem: '#scanReceipt',
        // height:500,
        //width:500,
        url: requestUrl,
        // contentType : 'application/json',
        page: true,
        method:'get',
        cols: [[ //表头
            {field: 'serialNumber', title: '序号', width:60, fixed: 'left'},
            {field: 'receiptNum', title: '发票号码', width:100},
            {field: 'errorCode', title: '错误代码',hide:true},
            {field:'errorMsg',title:'错误信息(空表示无错误)',templet :function (d) {

                    if (d.errorCode=='0'){
                        d.errorMsg="";
                    }else if(d.errorCode=='1'){
                        d.errorMsg="发票重复";
                    }else if(d.errorCode=='2'){
                        d.errorMsg="购买方名称错误";
                    }else if(d.errorCode=='3'){
                        d.errorMsg="发票号码扫描错误";
                    }else if(d.errorCode=='4'){
                        d.errorMsg="地址,电话错误";
                    }else if(d.errorCode=='5'){
                        d.errorMsg="开户行及账号错误";
                    }else if(d.errorCode=='6'){
                        d.errorMsg="纳税人识别号错误";
                    }
                    return d.errorMsg;
                }},
            {field:'img',title:'', toolbar: '#barDemo',width:50}
        ]],
        request: {
            pageName: 'pageNum',
            limitName: 'pageSize'
        },
        height: 'full-200',
        done:function (res, curr, count) {

            // uploadData=res.data;

            for (var i in res.data){
                var recetiptId = res.data[i];
                if (recetiptId.errorCode=='1'){
                    $("tr[data-index='"+ i +"']").attr({"style":"color:red"});
                }else if(recetiptId.errorCode=='2'||recetiptId.errorCode=='4'||recetiptId.errorCode=='5'||recetiptId.errorCode=='6'){
                    $("tr[data-index='"+ i +"']").attr({"style":"color:#ff9900"})
                }
            }
        }
    });

    table.on('tool(scanReceiptFilter)', function(obj){
        var receiptNum = obj.data.receiptNum;
        var tempImgUrl;
        if (obj.data.errorCode=="1"){
            tempImgUrl = '/file/'+receiptNum+'.jpg';
            openImage(tempImgUrl);
        }else {
            tempImgUrl = '/file/'+receiptNum+'.jpg';
            openImage(tempImgUrl);
        }
    });

    table.on('row(scanReceiptFilter)', function(obj){

        $("#scanReceiptMsg1").show();
        $("#scanReceiptMsg2").hide();
        treeSelect.revokeNode('tree');
        var id=obj.data.id;
        var treeNode = jsMap.get(id);
        if (treeNode!=null||treeNode!=undefined){
            treeSelect.checkNode('tree', treeNode.id);
        }

        for (var key in uploadData){
            if (uploadData[key].id==id){
                queryData=uploadData[key];

                var fixedAssetsValue =uploadData[key].fixedAssets;
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
            }
        }
        var errorCode = queryData.errorCode;


        $("#scanReceiptMsg1 input").bind("input propertychange change",function(event){
            initUploadVal(uploadData);
        });


        if (errorCode=='1'){
            $("#scanForm1").hide();
            $("#scanTable1").show();
            $("#scanReceiptMsg1").css("width","600px");
            $("#errorMsg").css("color","red");
            $("#errorMsg").text("发票重复报销提示");

            var repeatUrl = '/receiptScan/selectReapetReceipt?receiptNum='+queryData.receiptNum;
            var resData=null;

            $.ajax({
              type: "GET",
              contentType : 'application/json',
              dataType:'json',
              url: repeatUrl,
              // data: queryData.receiptNum,
              success: function(data){
                  resData=data;
                  $("#tb1").html(resData.submitAccountStaff);
                  $("#tb2").html(resData.receiptCode);
                  $("#tb3").html(resData.receiptNum);
                  $("#tb4").html(resData.sellerUnitName);
                  $("#tb5").html(resData.receiptTime);
                  $("#tb6").html(resData.money);
                  $("#tb7").html(resData.entryTime);

              }
            });

        }else {
            $("#scanReceiptMsg1").css("width","900px");
            $("#scanForm1").show();
            $("#scanTable1").hide();

            layui.use('form', function() {
                var form = layui.form;
                console.log(queryData.receiptTime);

                form.val("scanFormFilter", {
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
                    "remarks":queryData.remarks,
                    "fixedAssets":queryData.fixedAssets
                    // "assertClassFirdir":queryData.assertClassFirdir,
                    // "assertClassSecdir":queryData.assertClassSecdir
                });


            });

            if (queryData.errorCode=='2'){
                $("#errorMsg").css("color","#ff9900");
                $("#errorMsg").text("购买方信息不规范,请先确认是否正确后再上传系统");
            }else if(queryData.errorCode=='0'){
                $("#errorMsg").css("color","#33ff00");
                $("#errorMsg").text("关键信息正确");
            }else if(queryData.errorCode=='4'){
                $("#errorMsg").css("color","#ff9900");
                $("#errorMsg").text("地址,电话信息不规范,请先确认是否正确后再上传系统");
            }else if(queryData.errorCode=='5'){
                $("#errorMsg").css("color","#ff9900");
                $("#errorMsg").text("开户行及账号信息不规范,请先确认是否正确后再上传系统");
            }else if(queryData.errorCode=='6'){
                $("#errorMsg").css("color","#ff9900");
                $("#errorMsg").text("纳税人识别号信息不规范,请先确认是否正确后再上传系统");
            }
        }
    });

    $("#delScanReceipt").click(function () {
        var delScanUrl ='/receiptScan/delScanReceipt?id='+queryData.id;

        layer.confirm('确认删除?', function(index) {
            layer.close(index);
            $.ajax({
                type: "GET",
                contentType : 'application/json',
                dataType:'json',
                url: delScanUrl,
                // data: queryData.id,
                success: function(data){
                    layer.msg("删除成功");
                    table.reload('scanReceipt',{
                        'url':requestUrl
                    });
                    $("#scanReceiptMsg1").hide();
                    $("#scanReceiptMsg2").show();
                    for (var key in uploadData){
                        if (uploadData[key].id==queryData.id){
                            uploadData.splice(key,1);
                        }
                    };

                    var delFileUrl = '/receiptScan/delScanReceipt';
                    $.ajax({
                        type: "GET",
                        contentType: 'application/json',
                        dataType: 'json',
                        url: delFileUrl,
                        success: function (data) {
                        }

                    });
                },
                error:function () {
                    layer.msg("删除失败");
                }
            });

        });
        return false;
    });

    $("#delScanReceipt2").click(function () {
        var delScanUrl ='/receiptScan/delScanReceipt?id='+queryData.id;

        layer.confirm('确认删除?', function(index) {
            layer.close(index);
            $.ajax({
                type: "GET",
                contentType : 'application/json',
                dataType:'json',
                url: delScanUrl,
                // data: queryData.id,
                success: function(data){
                    layer.msg("删除成功");
                    table.reload('scanReceipt',{
                        'url':requestUrl
                    });
                    $("#scanReceiptMsg1").hide();
                    $("#scanReceiptMsg2").show();
                    for (var key in uploadData){
                        if (uploadData[key].id==queryData.id){
                            uploadData.splice(key,1);
                        }
                    }
                },
                error:function () {
                    layer.msg("删除失败");
                }
            });

        });
        return false;
    });


    var IntervalIndex ; //轮询序列
    function SerchInfo(type) {
        if(type)
        {
            IntervalIndex=  setInterval(function () {

                table.reload('scanReceipt',{
                    'url':requestUrl
                });

                var findScanUrl = '/receiptScan/selectScanReceipts?pageNum='+1+'&pageSize='+10;

                $.ajax({
                    type: "GET",
                    contentType : 'application/json',
                    dataType:'json',
                    url: findScanUrl,
                    success: function(data){
                        if (data.code==-1){
                            layer.msg("扫描失败");
                        }
                    },
                    error:function () {
                        layer.msg("扫描失败");
                    }
                });

                $("#scanReceiptMsg1").hide();
                $("#scanReceiptMsg2").show();
            },3000);
        }
        else{
            clearInterval(IntervalIndex);
        }
    }


    $("#scanOver").click(function () {
        if($(this).attr("stype") == "on")
        {
            SerchInfo(true);
            $(this).text("扫描完成");
            $("#loadingId").show();
            $(this).css("background-color","red");
            $(this).attr("background-color","aquamarine");
            $(this).attr("stype","off");
            table.reload('scanReceipt',{
                'url':requestUrl
            });

        }
        else if($(this).attr("stype") == "off"){
            SerchInfo(false);
            $(this).text("开始扫描");
            $(this).css("background-color","#009688");

            $("#scanReceipt").addClass("layui-disabled");
            $("#loadingId").hide();
            $(this).attr("stype", "on");
            // location.reload(true);
            // table.reload('scanReceipt',{
            //     'url':requestUrl
            // });

            var countScanUrl  = '/receiptScan/countScanReceiptNum';
            $.ajax({
                type: "GET",
                contentType : 'application/json',
                dataType:'json',
                url: countScanUrl,
                success: function(data){
                    var countData = data;
                    var tempRequestUrl = '/receiptScan/selectScanReceipts?pageNum='+1+'&pageSize='+countData;
                    $.ajax({
                        type: "GET",
                        contentType : 'application/json',
                        dataType:'json',
                        url: tempRequestUrl,
                        success: function(data){

                            if (data.code==-1){
                                layer.msg("扫描识别失败");
                            }
                            uploadData=data.data;
                        }
                    });
                }
            });

        }

        return false;

    });
    $("#uploadReceipts").click(function () {

        for (var key in uploadData){
            if (uploadData[key].receiptType==="1"){
                uploadData[key].isCertification="0";
            }else if(uploadData[key].receiptType==="0"){
                uploadData[key].isCertification="2";
            }

            var fixedAssetsValue = uploadData[key].fixedAssets;
            if (fixedAssetsValue=="0"){
                assertClassFirdir=null;
                assertClassSecdir=null;
            }else if(fixedAssetsValue=="1"){
                jsMap.forEach(function (element, index, array) {
                    if (index==uploadData[key].id){
                        if (element.parentname!=null&&element.parentname!=""&&element.parentname!=undefined){
                            assertClassFirdir=element.parentname;
                            assertClassSecdir=element.name;
                        }else {
                            assertClassFirdir=element.name;
                            assertClassSecdir=null;
                        }
                    }
                });

            }else if(fixedAssetsValue=="2"){
                assertClassFirdir=$("#IntangibleAssetClassId").val();
                assertClassSecdir=null;
            }
            uploadData[key].assertClassFirdir=assertClassFirdir;
            uploadData[key].assertClassSecdir=assertClassSecdir;

        }

        for (var key in uploadData){
            if (uploadData[key].errorCode=='1'){
                uploadData.splice(key,1);
            }
        }

        var compProj = $('input:radio[name="compProj"]:checked').val();
        var department = $("#isSelect").val();
        var submitaccountStaff= $("#isSubmitaccountStaff").val();

        if (submitaccountStaff==""||compProj==undefined||department==""){
            layer.msg("上传项没有填写完整");
        }else {
            for (var key in uploadData){
                uploadData[key].compProj=compProj;
                uploadData[key].department=department;
                uploadData[key].submitAccountStaff=submitaccountStaff;
                uploadData[key].handlerUser=loginUserName;
            }

            var handlerUrl='/receiptScan/handleScanReceipts';
            var submitUploadData =JSON.stringify(uploadData);

            $.ajax({
                type: "POST",
                contentType : 'application/json',
                dataType:'json',
                url: handlerUrl,
                data: submitUploadData,
                success: function(data){

                    table.reload('scanReceipt',{
                        'url':requestUrl
                    });

                    $("#scanReceiptMsg1").hide();
                    $("#scanReceiptMsg2").show();
                    $('#scanForm2')[0].reset();
                    layer.msg("上传成功");

                },
                error:function () {
                    layer.msg("上传失败");
                }
            });
        }

        return false;
    })


});