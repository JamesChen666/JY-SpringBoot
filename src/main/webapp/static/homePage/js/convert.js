function convertStatus(value,row,index){
    if(value == 1){
        return "已通过";
    }else if(value == 2){
        return "未通过"
    }else{
        return "未审核";
    }
}


function queryPosition(value,row,index) {
    var name = "";
    $.ajax({
        url : '/advertPosition/queryPosition',
        data:{PositionId:value},
        cache : false,
        async : false,
        type : "POST",
        dataType :"",
        success : function (data){
            if(data.length > 0){
                name = data[0].title;
            }
        }
    });
    return name;
}

function fmtDate(value,row,index){
    if(value != null){
        var date =  new Date(value);
        var y = 1900+date.getYear();
        var m = "0"+(date.getMonth()+1);
        var d = "0"+date.getDate();
        return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
    }else{
        return "";
    }
}


function queryFileType(value,row,index) {
    var name = "";
    $.ajax({
        url : '/downloadType/queryCode',
        data:{TypeCode:value},
        cache : false,
        async : false,
        type : "POST",
        dataType :"",
        success : function (data){
            if(data.length > 0){
                name = data[0].TypeName;
            }
        }
    });
    return name;
}


function queryLinkType(value,row,index) {
    var name = "";
    $.ajax({
        url : '/friendlinktype/queryCode',
        data:{typeCode:value},
        cache : false,
        async : false,
        type : "POST",
        dataType :"",
        success : function (data){
            if(data.length > 0){
                name = data[0].TypeName;
            }
        }
    });
    return name;
}


function queryNoticeType(value,row,index) {
    var name = "";
    $.ajax({
        url : '/noticeType/queryCode',
        data:{TypeCode:value},
        cache : false,
        async : false,
        type : "POST",
        dataType :"",
        success : function (data){
            if(data.length > 0){
                name = data[0].TypeName;
            }
        }
    });
    return name;
}


function redirect(value,row,index) {
    if(value == 1){
        return '本页面跳转';
    }else{
        return '新标签打开';
    }
}

function columnType(value,row,index) {
    if(value == 1){
        return '图文';
    }else{
        return '外部地址';
    }
}

/**
 * 查询国标字典表信息
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function queryDictionary(value,row,index) {
    var name = "";
    $.ajax({
        url : '/dictionary/dictionaryCode/'+value,
        data:{},
        cache : false,
        async : false,
        type : "POST",
        dataType :"",
        success : function (data){
            if(data.length > 0){
                name = data[0].title;
            }
        }
    });
    return name;
}