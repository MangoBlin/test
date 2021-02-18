$(document).ready(function(){

    function exportExcel() {
        console.log("hello");
        //获取表单区域所有值
        var form = layui.form;
        var data1 = form.val("myForm4");
        console.log(data1);
        var maxNum = null;
        $.ajax({
            type: "POST",
            contentType : 'application/json',
            dataType:'json',
            url: countUrl,
            data: JSON.stringify(queryTempData),
            success: function(data){
                maxNum=data;
                data1.pageNum=1;
                data1.pageSize=maxNum;
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    dataType:'json',
                    url: temptUrl,
                    data: JSON.stringify(data1),
                    success: function(data){

                        if (data.data==null){
                            layer.msg("查询数据为空,无法导出");
                        }else {
                            var idList =[];
                            var listLength = data.data.length;
                            for (var i=0;i<listLength;i++){
                                idList[i]=data.data[i].id;
                            }
                            console.log("idList"+idList);
                            // var exportUrl = '/exportExcel3?idList='+idList;
                            // window.open(exportUrl);

                            exportExcel9(idList);
                        }
                    }
                });
                console.log("count+++++"+data);
            }
        });
    }

    function exportReceipts() {
        //获取表单区域所有值
        var form = layui.form;
        var data1 = form.val("myForm4");
        console.log(data1);

        var maxNum = null;
        $.ajax({
            type: "POST",
            contentType : 'application/json',
            dataType:'json',
            url: countUrl,
            data: JSON.stringify(queryTempData),
            success: function(data){
                maxNum=data;
                data1.pageNum=1;
                data1.pageSize=maxNum;
                $.ajax({
                    type: "POST",
                    contentType : 'application/json',
                    dataType:'json',
                    url: temptUrl,
                    data: JSON.stringify(data1),
                    success: function(data){

                        if (data.data==null){
                            layer.msg("查询数据为空,无法导出");
                        }else {
                            var idList =[];
                            var listLength = data.data.length;
                            for (var i=0;i<listLength;i++){
                                idList[i]=data.data[i].id;
                            }
                            console.log("idList"+idList);
                            // var exportReceiptUrl = '/receiptScan/downReceipt?idList='+idList;
                            // var exportReceiptUrl = '/receiptScan/downReceipt2?idList='+idList;
                            // window.open(exportReceiptUrl);
                            exportReceipt8(idList);
                        }
                    }
                });
                console.log("count+++++"+data);
            }
        });
    }

   $("#exportBtn").click(function () {
       exportExcel();
   });

    $("#exportReceiptBtn").click(function () {
        exportReceipts();
        setTimeout(exportExcel,1500);
    });
});

function exportExcel9(idList){
    var form = $("<form>");
    form.attr('style', 'display:none');
    form.attr('target', '');
    form.attr('method', 'post');
    form.attr('action', '/exportExcel3');

    var input1 = $('<input>');
    input1.attr('type', 'hidden');
    input1.attr('name', 'idList');
    input1.attr('value', idList);      /* JSON.stringify($.serializeObject($('#searchForm'))) */

    $('body').append(form);
    form.append(input1);

    form.submit();
    form.remove();
}

function exportReceipt8(idList){
    var form = $("<form>");
    form.attr('style', 'display:none');
    form.attr('target', '');
    form.attr('method', 'post');
    form.attr('action', '/receiptScan/downReceipt2');

    var input1 = $('<input>');
    input1.attr('type', 'hidden');
    input1.attr('name', 'idList');
    input1.attr('value', idList);      /* JSON.stringify($.serializeObject($('#searchForm'))) */

    $('body').append(form);
    form.append(input1);

    form.submit();
    form.remove();
}

