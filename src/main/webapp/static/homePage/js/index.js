$(function () {

/*    // 迎新下拉二级菜单
    $(".nav-li>li").hover(function () {
        $(this).find("ul").slideDown(400);
    },function(){
        $(this).find("ul").slideUp(400);
    });
    // tabs切换active1
    $('.in-video>.text-head>div').click(
        function () {
            $(this).addClass("active1").siblings().removeClass('active1');
        }
    );
    $('#btn1').click(
        function () {
            $('.video-list1').show();
            $('.video-list2').hide();

        }
    )
    $('#btn2').click(
        function () {
            $('.video-list2').css('display','flex');
            $('.video-list1').hide();
        }
    )*/


    // 迎新下拉二级菜单
    $(".nav-li>li").hover(function () {
        $(this).find("ul").slideDown(400);
    },function(){
        $(this).find("ul").slideUp(400);
    });
    // tabs切换active1
    $('.in-video>.text-head>div').click(
        function () {
            $(this).addClass("active1").siblings().removeClass('active1');
            var index = $(this).index();
            $('.video-list').addClass('hide').eq(index).removeClass('hide');

        }
    );

    //查询出首页通知公告信息
    $(function(){
        var  html = "";
        var url = '/home/noticeList';
        $.post(url,{TypeCode:"dm001",page:1,rows:10,sidx:"Id",sord:"asc"}, function (data) {
            for (var i = 0; i < data.length; i++) {
                html +=' <a href="/home/details?code=1&id='+data[i].Id+'" class="clear">\n' +
                                '<p class="title bt-l" style="width:265px"><i></i>'+data[i].Title+'</p>\n' +
                                '<div class="bt-r c95">'+fmtDate(data[i].CreateDate)+'</div>\n' +
                          '</a>';
            }
            $('#tzgg').append(html);
        })
    });
    //查询出首页自行联系信息
    $(function(){
        var  html = "";
        var url = '/home/noticeList';
        $.post(url,{TypeCode:"dm002"}, function (data) {
            for (var i = 0; i < data.length; i++) {
                html +=' <a href="/home/comList" class="clear">\n' +
                    '<p class="title bt-l" style="width:265px"><i></i>'+data[i].Title+'</p>\n' +
                    '<div class="bt-r c95">'+fmtDate(data[i].CreateDate)+'</div>\n' +
                    '</a>';
            }
            $('#zxlx').append(html);
        })
    });
    //查询出首页历年来校单位信息
    $(function(){
        var  html = "";
        var url = '/home/noticeList';
        $.post(url,{TypeCode:"dm003"}, function (data) {
            for (var i = 0; i < data.length; i++) {//data[i].CreateDate
                html +='<tr>\n' +
                    '         <td  class="onepai pl20" width="100" >成都易科士信息产业有限公司</td>\n' +
                    '         <td width="100">2018-08-28</td>\n' +
                    '         <td width="87">成都郫都区</td>\n' +
                    '         <td><a href="/home/comList"> 查看</a></td>\n' +
                    '  </tr> ';
            }
            $('#lxdw').append(html);
        })
    });

    //查询出首页就业指导栏目信息
    $(function(){
        var  html = "";
        var url = '/home/noticeList';
        $.post(url,{TypeCode:"dm006"}, function (data) {
            for (var i = 0; i < data.length; i++) {//data[i].CreateDate
                html +=' <a href="/home/details?code=6&id='+data[i].Id+'" class="clear">\n' +
                    '<p class="title bt-l" style="width:450px"><i></i>'+data[i].Title+'</p>\n' +
                    '<div class="bt-r c95">'+fmtDate(data[i].CreateDate)+'</div>\n' +
                    '</a>';
            }
            $('#jyzdlm').append(html);
        })
    });

    //查询出首页毕业联系信息
    $(function(){
        var  html = "";
        var url = '/home/downloadList';
        $.post(url,{TypeCode:"xxzl"}, function (data) {
            for (var i = 0; i < data.length; i++) {
                html +=' <a href="'+data[i].Url+'" class="clear">\n' +
                    '<p class="title bt-l" style="width:265px"><i></i>'+data[i].Title+'</p>\n' +
                    '<div class="bt-r c95">'+fmtDate(data[i].CreateDate)+'</div>\n' +
                    '</a>';
            }
            $('#bylx').append(html);
        })
    });
    //查询出首页相关下载信息
    $(function(){
        var  html = "";
        var url = '/home/downloadList';
        $.post(url,{TypeCode:"xgxz"}, function (data) {
            for (var i = 0; i < data.length; i++) {
                html +=' <a href="'+data[i].Url+'" class="clear">\n' +
                    '<p class="title bt-l" style="width:265px"><i></i>'+data[i].Title+'</p>\n' +
                    '<div class="bt-r c95">'+fmtDate(data[i].CreateDate)+'</div>\n' +
                    '</a>';
            }
            $('#xzxx').append(html);
        })
    });
    //查询出就业指导视频
    $(function(){
        shipin("sp001",'#jyzdsp');
    });

    $("#btn1").click(function(){
        shipin("sp001",'#jyzdsp');
    });
    $("#btn2").click(function(){
        shipin("sp002",'#zpdwsp');
    });
    $("#btn3").click(function(){
        shipin("sp003",'#yxcysp');
    });

    function shipin(code,id){
        $("#gdsp").attr("href","/home/videoList?code="+code);
        var  html = "";
        var url = '/home/noticeList';
        $.post(url,{TypeCode:code}, function (data) {
            $(id).empty();
            for (var i = 0; i < data.length; i++) {
                html +=' <div class="marginleft10" style="text-align:center;">' +
                    '<a href="/home/videoDetails?id='+data[i].Id+'" >\n' +
                    '     <img  src="'+data[i].Cover+'" alt="" style="width:100%; height:100%; object-fit: fill">\n' +
                    '</a>'+
                   /* '<video src="'+data[i].VideoUrl +'" controls="true" controlslist="nodownload" style="width:100%; height:100%; object-fit: fill">\n' +
                    '\t</video>' +*/
                    '<div style="height: 20px;width: 100%;overflow:hidden;">'+data[i].Title+'</div>'+
                    '</div>';
            }
            $(id).append(html);
        })
    }




    //查询出首页广告信息
    $(function(){
        $.ajax({
            url : '/home/advertList',
            data:{page:1,rows:50,sidx:"Id",sord:"asc"},
            type : "POST",
            dataType :"",
            success : function (data){
                    if(data != null){
                        for (var i = 0; i <data.length ; i++) {
                            var dz = "";
                            var tp = data[i].DefaultCover;
                            for (var j = 0; j <data[i].list.length ; j++) {
                                if(data[i].list[0].RedirectUrl!=null && data[i].list[0].RedirectUrl !=undefined
                                    && data[i].list[0].RedirectUrl !="undefined"&& data[i].list[0].RedirectUrl !=""){
                                    dz = data[i].list[0].RedirectUrl;
                                }
                                if(data[i].list[0].Cover!=null && data[i].list[0].Cover!=undefined
                                    && data[i].list[0].Cover !="undefined"&& data[i].list[0].Cover !=""){
                                    tp = data[i].list[0].Cover;
                                }
                            }
                            if(data[i].Title === "第一块广告位"){
                                $("#kjrk1").attr("href",dz);
                                $("#kjrk1").empty();
                                $("#kjrk1").append('<img class="margintop20" src="'+tp+'" alt="" style="width: 270px;height: 90px;">');
                            }
                            if(data[i].Title === "第二块广告位"){
                                $("#kjrk2").attr("href",dz);
                                $("#kjrk2").empty();
                                $("#kjrk2").append('<img class="margintop20" src="'+tp+'" alt="" style="width: 270px;height: 90px;">');
                            }
                            if(data[i].Title === "第三块广告位"){
                                $("#kjrk3").attr("href",dz);
                                $("#kjrk3").empty();
                                $("#kjrk3").append('<img class="margintop20" src="'+tp+'" alt="" style="width: 270px;height: 90px;">');
                            }
                        }
                    }
            }
        });
    });



});

