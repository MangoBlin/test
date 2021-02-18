layui.use('laydate',function(){
  var laydate = layui.laydate;
  laydate.render({
    elem:'#myDate1',
    type:"date"
  });
  laydate.render({
      elem:'#myDate2',
      type:"date"
  });
  laydate.render({
      elem:'#myDate3',
      type:"datetime"
  });
  laydate.render({
      elem:'#myDate4',
      type:"datetime"
  });
});

