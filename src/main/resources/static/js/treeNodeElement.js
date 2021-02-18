function judgeIdByName(assertClassFirdir, assertClassSecdir) {
    var idMap = new Map();
    idMap.set(1,"01-房屋及建筑物");
    idMap.set(4,"02-办公设备");
    idMap.set(5,"021-办公家具");
    idMap.set(6,"022-空调");
    idMap.set(333,"023-电视机");
    idMap.set(334,"024-弱电系统");
    idMap.set(7,"03-仪器设备");
    idMap.set(8,"031-测绘仪器");
    idMap.set(9,"032-勘测设备");
    idMap.set(11,"04-交通工具");
    idMap.set(15,"05-电子设备");
    idMap.set(16,"051-电脑");
    idMap.set(17,"052-打印机");
    idMap.set(18,"053-办公电子类");
    idMap.set(19,"054-监控系统");
    idMap.set(20,"055-系统集成");

    var tempindex=null;
    if(assertClassSecdir==null||assertClassSecdir==""||assertClassSecdir==undefined){
        idMap.forEach(function (element, index, array) {
            if (element===assertClassFirdir){
                tempindex=index;
            }
        });
    }else {
        idMap.forEach(function (element, index, array) {
            if (element===assertClassSecdir){
                tempindex=index;
            }
        });
    }
    return tempindex;
}

function judgeDirClass(assertClassDir) {

    var idMap = new Map();
    idMap.set(1,"01-房屋及建筑物");
    idMap.set(4,"02-办公设备");
    idMap.set(5,"021-办公家具");
    idMap.set(6,"022-空调");
    idMap.set(333,"023-电视机");
    idMap.set(334,"024-弱电系统");
    idMap.set(7,"03-仪器设备");
    idMap.set(8,"031-测绘仪器");
    idMap.set(9,"032-勘测设备");
    idMap.set(11,"04-交通工具");
    idMap.set(15,"05-电子设备");
    idMap.set(16,"051-电脑");
    idMap.set(17,"052-打印机");
    idMap.set(18,"053-办公电子类");
    idMap.set(19,"054-监控系统");
    idMap.set(20,"055-系统集成");

    var tempindex=null;
    idMap.forEach(function (element, index, array) {
        if (element===assertClassDir){
            tempindex=index;
        }
    });

    if (tempindex==1||tempindex==4||tempindex==7||tempindex==11||tempindex==15){
        return "1";
    }else if(tempindex==5||tempindex==6||tempindex==333||tempindex==334||tempindex==8||tempindex==9||tempindex==16||tempindex==17||tempindex==18||tempindex==19||tempindex==20) {
        return "2";
    }else {
        return "-1";
    }
}