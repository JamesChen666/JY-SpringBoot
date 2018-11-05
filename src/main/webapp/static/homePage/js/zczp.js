//查询出来校招聘单位日程信息
$(function(){
    debugger
    var code = '${code}';
    if(code == 1){
        var  html = "";
        var url = '/home/queryZczpList';
        $.post(url,{page:"1",rows:"10",sidx:"id",sord:"asc"}, function (data) {
            for (var i = 0; i < data.rows.length; i++) {//data[i].CreateDate
                html +='<div class=" margintop20" >\n' +
                    '        <div class=" news-list news-list2" style="width: 100%">\n' +
                    '            <a href="/home/zwPro" >'+data.rows[i].CorpName+'</a>\n' +
                    '            <p class="c95 margintop10 clear " style="line-height: 34px">\n' +
                    '                <span class="marginr30">招聘地点（教室） ：'+data.rows[i].Title+'</span>\n' +
                    '                <span class="marginr30">招聘日期 ：'+fmtDate(data.rows[i].UseDate)+'</span>\n' +
                    '                <span class="marginr30">招聘时间 ：'+data.rows[i].StartHour+'~'+data.rows[i].EndHour+'</span>\n' +
                    '            </p>\n' +
                    '        </div>\n' +
                    '    </div>\n' +
                    '    <div class="line margintop20" ></div>';
            }
            html +='<div class="news-tabs c32 text-c">\n' +
                '        <a href="" style="width: 80px"> < 上一页</a>\n' +
                '        <a href="" class="news-tabs-active">1</a>\n' +
                '        <a href="">2</a>\n' +
                '        <a href="">3</a>\n' +
                '        <a href="">...</a>\n' +
                '        <a href="">30</a>\n' +
                '        <a href="" style="width: 80px">下一页 > </a>\n' +
                '        <span>共5页，到第</span>\n' +
                '        <label for="">\n' +
                '            <input type="number" min="1">\n' +
                '            页\n' +
                '        </label>\n' +
                '        <a href="" style="width: 70px">确 认</a>\n' +
                '    </div>';
            $('#zczp').append(html);
        })
    }else if(code == 2){
        var  html = "";
        var url = '/home/queryZczpList';
        $.post(url,{page:"1",rows:"10",sidx:"id",sord:"asc",s_UseDate:new Date()}, function (data) {
            for (var i = 0; i < data.rows.length; i++) {//data[i].CreateDate
                html +='<div class=" margintop20" >\n' +
                    '        <div class=" news-list news-list2" style="width: 100%">\n' +
                    '            <a href="/home/zwPro" >'+data.rows[i].CorpName+'</a>\n' +
                    '            <p class="c95 margintop10 clear " style="line-height: 34px">\n' +
                    '                <span class="marginr30">招聘地点（教室） ：'+data.rows[i].Title+'</span>\n' +
                    '                <span class="marginr30">招聘日期 ：'+fmtDate(data.rows[i].UseDate)+'</span>\n' +
                    '                <span class="marginr30">招聘时间 ：'+data.rows[i].StartHour+'~'+data.rows[i].EndHour+'</span>\n' +
                    '            </p>\n' +
                    '        </div>\n' +
                    '    </div>\n' +
                    '    <div class="line margintop20" ></div>';
            }
            html +='<div class="news-tabs c32 text-c">\n' +
                '        <a href="" style="width: 80px"> < 上一页</a>\n' +
                '        <a href="" class="news-tabs-active">1</a>\n' +
                '        <a href="">2</a>\n' +
                '        <a href="">3</a>\n' +
                '        <a href="">...</a>\n' +
                '        <a href="">30</a>\n' +
                '        <a href="" style="width: 80px">下一页 > </a>\n' +
                '        <span>共5页，到第</span>\n' +
                '        <label for="">\n' +
                '            <input type="number" min="1">\n' +
                '            页\n' +
                '        </label>\n' +
                '        <a href="" style="width: 70px">确 认</a>\n' +
                '    </div>';
            $('#zczp').append(html);
        })
    }else if(code == 3){
        var  html = "";
        var url = '/home/query';
        $.post(url,{page:"1",rows:"10",sidx:"id",sord:"asc"}, function (data) {
            for (var i = 0; i < data.rows.length; i++) {//data[i].CreateDate
                html +='<div class=" margintop20" >\n' +
                    '        <div class=" news-list news-list2" style="width: 100%">\n' +
                    '            <a href="/home/zwPro" >'+data.rows[i].CorpName+'</a>\n' +
                    '            <p class="c95 margintop10 clear " style="line-height: 34px">\n' +
                    '                <span class="marginr30">招聘地点（教室） ：'+data.rows[i].Title+'</span>\n' +
                    '                <span class="marginr30">招聘日期 ：'+fmtDate(data.rows[i].UseDate)+'</span>\n' +
                    '                <span class="marginr30">招聘时间 ：'+data.rows[i].StartHour+'~'+data.rows[i].EndHour+'</span>\n' +
                    '            </p>\n' +
                    '        </div>\n' +
                    '    </div>\n' +
                    '    <div class="line margintop20" ></div>';
            }
            html +='<div class="news-tabs c32 text-c">\n' +
                '        <a href="" style="width: 80px"> < 上一页</a>\n' +
                '        <a href="" class="news-tabs-active">1</a>\n' +
                '        <a href="">2</a>\n' +
                '        <a href="">3</a>\n' +
                '        <a href="">...</a>\n' +
                '        <a href="">30</a>\n' +
                '        <a href="" style="width: 80px">下一页 > </a>\n' +
                '        <span>共5页，到第</span>\n' +
                '        <label for="">\n' +
                '            <input type="number" min="1">\n' +
                '            页\n' +
                '        </label>\n' +
                '        <a href="" style="width: 70px">确 认</a>\n' +
                '    </div>';
            $('#zczp').append(html);
        })
    }

});