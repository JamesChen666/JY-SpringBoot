//查询出首页友情链接
$(function(){
    $("#link").empty();
    var  html = "";
    $.ajax({
        url : '/home/linktypeList',
        data:{},
        type : "POST",
        dataType :"",
        success : function (data){
            for (var i = 0; i <data.length ; i++) {
                html += '<select name="menu1" onchange="javascript:window.open(this.options[this.selectedIndex].value)">\n' +
                    '            <option value="#">'+data[i].TypeName+'</option>\n';
                $.ajaxSettings.async = false;
                $.post('/home/linkList',{typeCode:data[i].TypeCode}, function (dat) {
                    for (var j = 0; j < dat.length; j++) {
                        html +='<option value="'+dat[j].RedirectUrl+'">'+dat[j].Title+'</option>\n ';
                    }
                });
                html += '</select>';
                $.ajaxSettings.async = true;
            }
            $("#link").append(html);
        }
    });
});


//查询出首页网站栏目
$(function(){
    $("#nav").empty();
    var  html = "";
    $.ajax({
        url : '/home/navList',
        data:{page:1,rows:50,sidx:"Id",sord:"asc"},
        type : "POST",
        dataType :"",
        success : function (data){
            for (var i = 0; i <data.rows.length ; i++) {
                var tag = "";
                if(data.rows[i].RedirectTypeId == 2){
                    tag = "_blank";
                }else{
                    tag = "_top";
                }
                html += '<li>\n' +
                    '          <a href="'+data.rows[i].Url+'" target="'+tag+'">'+data.rows[i].Title+'</a>\n';
                if( data.rows[i].mapList != undefined && data.rows[i].mapList != "data.rows[i].mapList" && data.rows[i].mapList.length>0) {
                    html += '<ul class="index-xiala">';
                    for (var j = 0; j < data.rows[i].mapList.length; j++) {
                        html += '<li><a href="'+data.rows[i].mapList[j].Url+'">'+data.rows[i].mapList[j].Title+'</a></li>';
                    }
                    html += '</ul>';
                }
                html += '     </li>';
            }
            $("#nav").append(html);
            $(".nav-li>li").mouseover(function (){
                debugger
                //$(this).parents("ul").removeClass("index-xiala");//移除所有.handle的active类
                $(this).children(".index-xiala").show();
                //$(".index-xiala").show();
            }).mouseout(function (){
                debugger
                //$(".index-xiala").hide();
                //$(this).parents("ul").addClass("index-xiala");
                $(this).children(".index-xiala").hide();
            });
        }
    });
});

/**
 * 转换日期格式
 * @param obj
 * @returns {string}
 */
function fmtDate(obj){
    var date =  new Date(obj);
    var y = 1900+date.getYear();
    var m = "0"+(date.getMonth()+1);
    var d = "0"+date.getDate();
    return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
}