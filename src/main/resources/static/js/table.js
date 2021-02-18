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
    compProj:''
    // sellerUnitName:''
};
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

$("#receiptManage1").attr("href","./receipt_manage.html");
$("#scanReceipt1").attr("href","./scan_receipt.html");
$("#personal2").attr("href","./personal.html");

var temptUrl = '/receipt/findReceiptByMsg';
updatePass();

// var toatl =null;
var queryTempData={
      entryEndtime: "",
      entryStartime: "",
      isCertification: "",
      receiptCategory: "",
      receiptCode: "",
      receiptContent: "",
      receiptEndtime: "",
      receiptNum: "",
      receiptStartime: "",
      receiptType:"",
      department:""
    };


var cols=[[
  //序号
  {
    field:'serialNumber',
    title:'序号',
    width:60,
    fixed:'left'
  },
  {
    field:'receiptNum',
    title:'发票号码 ',
    width:120,
    fixed:'left'
  },
  {
      field:'receiptCode',
      title:'发票代码',
      width:140
  },
  {
    field:'sellerUnitName',
    title:'销售方单位名称',
    width:250
  },
  {
    field:'receiptTime',
    title:'开票日期',
    width:120
  },
  {
    field:'checkDigit',
    title:'校验码',
    width:100
  },
  {
    field:'receiptCategory',
    title:'发票类别',
    width:120
  },
  {
    field:'receiptContent',
    title:'开票内容',
    width:140
  },
  {
    field:'money',
    title:'金额',
    width:120
  },
  {
    field:'rate',
    title:'税率',
    width:80
  },
  {
    field:'tax',
    title:'税额',
    width:120
  },
  {
    field:'leviedTotal',
    title:'价税合计',
    width:100
  },
  {
    field:'receiptType',
    title:'专票/普票',
    width:100
  },
  {
    field:'submitAccountStaff',
    title:'报销人员 ',
    width:100
  },
  {
    field:'entryTime',
    title:'录入时间 ',
    width:180
  },
  {
    field:'remarks',
    title:'备注',
    width:120
  },
  {
    field:'compProj',
    title:'公司/项目',
    width:100
  },
  {
    field:'department',
    title:'部门',
    width:150
  },
  {
    field:'isCertification',
    title:'是否认证',
    width:100,
    templet: '#titleTpl'
  },
  {
    field:'fixedAssets',
    title:'是否固定资产',
    width:130
  },
  {
    field:'assertClassFirdir',
    title:'固定资产类型',
    width:130,
    hide:true
  },
    {
        field:'assertClassSecdir',
        title:'固定资产类型',
        width:130,
        hide:true
    },
    {
        field:'assertClass',
        title:'资产类型',
        width:130,
        templet: function(d){

            if(d.fixedAssets=="无形资产"){
                if (d.assertClassFirdir!=null&&d.assertClassFirdir!=""&&d.assertClassFirdir!=undefined){
                    return d.assertClassFirdir;
                }else {
                    return "";
                }
            }else if(d.fixedAssets=="固定资产"){
                if(d.assertClassSecdir!=null&&d.assertClassSecdir!=""&&d.assertClassSecdir!=undefined){
                    if (d.assertClassSecdir!=null&&d.assertClassSecdir!=""&&d.assertClassSecdir!=undefined){
                        return d.assertClassSecdir;
                    }else {
                        return "";
                    }
                }else {
                    if (d.assertClassFirdir!=null&&d.assertClassFirdir!=""&&d.assertClassFirdir!=undefined){
                        return d.assertClassFirdir;
                    }else {
                        return "";
                    }
                }
            }else if(d.fixedAssets=="非固定资产"){
                return "-";
            }
        }
    },
  {
    field:'certificationTime',
    title:'认证时间 ',
    width:120
  },
  {
    field:'voucherNum',
    title:'凭证号',
    width:'100'
  },
  {
    field:'option',
    title:'操作',
    toolbar:'#tableBar',
    width:220,
    fixed:'right'
  }
]];

layui.use('table',function(){
  var table = layui.table;
  table.render({
    //id:"mytable01",
    elem:"#demo02",
    height:'full-237',
    url:temptUrl,
    contentType : 'application/json',
    page:true, //开启分页
    method:'post',
    cols:cols,
    request: {
      pageName: 'pageNum',
      limitName: 'pageSize'
    }
  });

  // $("#loginUser").html(theRequest.username);
  $("#personal1").attr("href","./personal.html");
  $("#receiptManage1").attr("href","./receipt_manage.html");

  table.on('tool(barFunction)', function(obj){
    var queryData=obj.data;
    var layEvent = obj.event;

      // $.ajax({
      //     type: "GET",
      //     contentType : 'application/json',
      //     dataType:'json',
      //     url: '/receipt/selectReceiptById?id='+queryData.id,
      //     //data: "id="+JSON.stringify(queryData.id),
      //     success: function(data){
      //         queryData.receiptType=data.receiptType;
      //     }
      // });

    console.log("bbbbb");
    console.log(obj);

    if(layEvent === 'cert'){ //认证
      var certUrl = '/receipt/certReceipt?id='+queryData.id;
      $.ajax({
        type: "POST",
        contentType : 'application/json',
        dataType:'json',
        url: certUrl,
        //data: "id="+JSON.stringify(queryData.id),
        success: function(data){
          layer.msg(data.msg);
          table.reload('demo02', {
            url: temptUrl,
            where: pageParam
          });
        }
      });

      // $("#doCert").css("display","none");
      // $("#cancelCert").css("display","inline");

    }else if(layEvent === 'cancelCert'){//取消认证
      var cancelUrl = '/receipt/cancelReceipt?id='+queryData.id;
      $.ajax({
        type: "POST",
        contentType : 'application/json',
        dataType:'json',
        url: cancelUrl,
        success: function(data){
          layer.msg(data.msg);
          table.reload('demo02', {
            url: temptUrl,
            where: pageParam
          });
        }
      });
    } else if(layEvent === 'del'){ //删除
      layer.confirm('确认删除?', function(index){
        layer.close(index);
        var delUrl = '/receipt/delReceipt?id='+queryData.id;
        $.ajax({
          type: "POST",
          contentType : 'application/json',
          dataType:'json',
          url: delUrl,
          success: function(data){
            layer.msg(data.msg);
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
            table.reload('demo02', {
              url: temptUrl,
              where: pageParam
            });
          }
        });
      });
    } else if(layEvent === 'edit'){ //编辑
        eject(queryData);
        table.reload('demo02', {
          url: temptUrl,
          where: pageParam
        });
    } else if(layEvent==='noCert'){

    }
  });
});




