
layui.use('element', function(){
  var element = layui.element;
});

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

layui.use('form', function(){
  var form = layui.form;
  
  //监听提交
  form.on('submit(formSubbmit)', function(data){
    console.log(JSON.stringify(data.field));
    return false;
  });
});