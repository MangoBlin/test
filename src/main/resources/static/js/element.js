layui.use('element', function(){
    var element = layui.element;

    element.on('nav(filter)', function(elem){
        console.log(elem); //得到当前点击的DOM对象
    });
});