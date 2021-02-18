var queryData=null;
var temptUrl = '/receipt/findReceiptByMsg';
var pageParam = {
    entryEndtime:'',
    entryStartime:'',
    isCertification:'',
    receiptCategory:'',
    receiptCode:'',
    receiptContent:'',
    receiptEndtime:'',
    // receiptNum:'',
    receiptStartime:'',
    receiptType:'',
    department:'',
    fixedAssets:'',
    compProj:'',
    // sellerUnitName:''
    assertClassFirdir:'',
    assertClassSecdir:'',
    IntangibleAssetClass:''
};

$(".myDateDiv1").hide();

$("#editPassword").click(function () {
})

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

receiptContentSelect();
function receiptContentSelect() {
    var array =null;
    var selectSubbmitUrl ='/receipt/selectContentList';
    $.ajax({
        type: "GET",
        contentType: 'application/json',
        dataType: 'json',
        url: selectSubbmitUrl,
        success: function (data) {
            array=data;

            var autoComplete = new AutoComplete("receiptContent", "auto", array);
            //autoComplete.init();
            $(window).on('keyup', function (event) {
                autoComplete.start(event);
            });
        }
    });
}

var cataList =null;
var cataUrl = '/receipt/selectCategoryList';
$.ajax({
  type: "GET",
  contentType : 'application/json',
  dataType:'json',
  url: cataUrl,
  success: function(data){

    cataList=data;
    $("#selectFun").append( "<option value=-1>-</option>" );
    for (var i=0;i<cataList.length;i++){
      $("#selectFun").append( "<option value="+cataList[i]+">"+cataList[i]+"</option>" );
    }

    layui.use(['treeSelect','form'], function () {
        var form = layui.form;

        $("input[id='timeRadio1']").on("click",function () {
            $(".myDateDiv1").hide();
            $(".myDateDiv2").show();
        });

        $("input[id='timeRadio2']").on("click",function () {
            $(".myDateDiv2").hide();
            $(".myDateDiv1").show();
        });

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
                pageParam.fixAssertClass=d.current.name;
            },
            success: function (d) {
            }
        });

        $("#resetBtn").click(function () {

            $(".fixAssertClass").hide();
            $(".IntangibleAssetClass").hide();

            pageParam.assertClassFirdir="";
            pageParam.assertClassSecdir="";
            pageParam.fixAssertClass="";

            treeSelect.revokeNode('tree', function(d){
            });

            form.val("myForm4", {
                "isCertification": "",
                "receiptType": ""
            });
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
            }else {
                $(".fixAssertClass").hide();
                $(".IntangibleAssetClass").hide();
            }
        });

        //监听提交
        form.on('submit(formSubbmit)', function(data){

            data.field.fixAssertClass=pageParam.fixAssertClass;

            for(var key in pageParam){
                if(data.field[key]!=undefined && data.field[key]!=null){
                    pageParam[key] = data.field[key];
                }else{
                    pageParam[key] = '';
                }
            }
            if (pageParam.fixedAssets==="1"){
                var fixAssertClass=pageParam.fixAssertClass;
                var tempDirClass= judgeDirClass(fixAssertClass);
                console.log(tempDirClass);
                if (tempDirClass=="1"){
                    pageParam.assertClassFirdir=pageParam.fixAssertClass;
                    pageParam.assertClassSecdir="";
                }else if(tempDirClass=="2"){
                    pageParam.assertClassFirdir="";
                    pageParam.assertClassSecdir=pageParam.fixAssertClass;
                }

            }else if(pageParam.fixedAssets==="2"){
                pageParam.assertClassFirdir=pageParam.IntangibleAssetClass;
                pageParam.assertClassSecdir="";
            }

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
                where: pageParam,
                page: {
                    curr: 1
                }
            });

            return false;
        });
    });


  },
  error:function (data) {
      alert("数据请求失败");
  }
});



