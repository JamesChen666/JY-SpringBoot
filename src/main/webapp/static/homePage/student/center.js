var swiper = new Swiper('.swiper-container', {
    slidesPerView: 6,
    spaceBetween: 25,
    pagination: {
        el: '.swiper-pagination',
        clickable: true,
    },
    navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
    },
});
//-----------------------------------------------------------------------------------
//退出
function exit() {
    if (confirm("您确定要退出迎新系统吗？")) {
        window.location.href = "/loginOut"
    }
}
//加载初始化信息
$(function () {
    queryTzgg(1, 1)
    $(".btn-slide").click(function () {
        if ($(this).next().is(':hidden')) {
            var btn = '<img src="/static/homePage/img/cen-up.png" alt=""> 收起内容';
            $(this).html(btn);
        } else {
            btn = '<img src="/static/homePage/img/cen-bottom.png" alt=""> 展开更多';
            $(this).html(btn);
        }
        $(this).next().slideToggle(500);
    });

    $('.mtk-show').click(function () {
        $('.sushe-mtkbg').height($('html,body').height)
        $('.sushe-mtkbg').fadeIn()

    });
    $('.close').click(function () {
        $('.sushe-mtkbg').fadeOut()
    });
    // 在线时间
    var hour = 0, minutes = 0, second = 0;
    setInterval(function () {
        second++;
        if (second == 60) {
            minutes++;
            second = 0;
        }
        if (minutes == 60) {
            hour++;
            minutes = 0;
        }
        $("#online").html("在线时间：" + hour + "小时" + minutes + "分" + second + "秒");
    }, 1000)
});
//-----------------------------------------------------------------------------------
//通知公告
function queryTzgg(code, page) {
    $("#newsList").empty();
    var html = "";
    $.ajax({
        type: "post",
        dataType: "json",
        url: '/home/homeNoticeList',
        data: {"code": code, "page": page, "rows": "5", "sidx": "Id", "sord": "asc"},
        success: function (data) {
            if (data != null) {
                for (var i = 0; i < data.rows.length; i++) {
                    html += '<div class="clear">\n' +
                        '                    <div class="text-r bt-l ">\n' +
                        '                        <p >' + fmtDateMT(data.rows[i].CreateDate) + '</p>\n' +
                        '                        <p>' + fmtDateY(data.rows[i].CreateDate) + '</p>\n' +
                        '                    </div>\n' +
                        '                    <div class="bt-l">\n' +
                        '                        <a href="javascript:;" class="onepai" onclick="xq(' + data.rows[i].Id + ')">' + data.rows[i].Title + '</a>\n' +
                        '                    </div>\n' +
                        '                    <div class="bt-l">\n' +
                        '                        <span><u>【类别：通知公告】</u></span>\n' +
                        '                        <span><u>【发布：' + shieldNull(data.rows[i].Resource) + '】</u></span>\n' +
                        '                    </div>\n' +
                        '                </div>';
                }
                html += '<p class="news-tabs c32 text-c" id="fy"></p>';
                $("#newsList").append(html);
                fy(data.total, data.page, code);
            } else {
                $("#newsList").append("暂无数据!");
            }
        }
    });
}
// 通知公告详情
function xq(id) {
    $("#xq").empty();
    var html = "";
    $.ajax({
        type: "post",
        dataType: "json",
        url: '/home/queryDetails',
        data: {id: id},
        success: function (data) {
            if (data != null) {
                for (var i = 0; i < data.length; i++) {
                    html += '<p class="center-title text-c" style="font-weight: bold">' + shieldNull(data[0].Title) + '</p>\n' +
                        '                <p class="text-c c95 margintop10">\n' +
                        '                    <span class="marginr30">日期：' + fmtDate(data[0].CreateDate) + '</span>\n' +
                        '                    <span class="marginleft10">浏览次数：666</span>\n' +
                        '                </p>\n' +
                        '                <div class=" text-bgt" style="background: none;padding-top: 0">\n' +
                        '                    <div style="width: 100%">\n' +
                        '                        <p class="margintop30">' + decodeURIComponent(data[0].Content) + '</p>\n' +
                        '                    </div>\n' +
                        '                </div>';
                }
                $("#xq").append(html);
            } else {
                $("#xq").append("暂无数据!");
            }
        }
    });
    $("#grzx").hide();//影藏
    $("#nr").attr("class", "tab_content bt-box-1024");
    $("#nr").show();//显示
}
// 通知公告详情隐藏显示
function zyxs() {
    $("#nr").hide();//影藏
    $("#grzx").show();//显示
}
//-----------------------------------------------------------------------------------
//修改基础信息
function jcxg() {
    $("#xgxx").empty();
    $.post("/examine/studentList", function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#xgxx").append(" <p class=\"onepai cge f15 margintop10\">修改" + data[i].CnTitle + "</p>\n" +
                "                <p class=\"c95 margintop10\">\n" +
                "                    <span class=\"marginr30\">修改时间 : " + data[i].CreateDate + "</span>\n" +
                "                    <span class=\"marginr30\">修改人 : " + data[i].RealName + "</span>\n" +
                "                    <a href=\"javascript:;\" class=\"marginr30\" onclick='xgls(" + data[i].Id + ")'><u>【查询详情】</u></a>\n" +
                "                </p>")
        }
    })
}
//基础信息修改历史详细
function xgls(id) {
    $("#xgxx").empty();
    $.post("/examine/view/" + id, function (data) {
        $("#xgxx").append(
            "<table class=\"ts-table f15 margintop20\">\n" +
            "    <thead>\n" +
            "    <tr class=\"c95\">\n" +
            "        <td width=\"150\">字段名称</td>\n" +
            "        <td width=\"110\">修改前名称</td>\n" +
            "        <td width=\"80\">修改后名称</td>\n" +
            "        <td width=\"100\">修改人</td>\n" +
            "        <td width=\"100\">修改时间</td>\n" +
            "        <td width=\"140\">审核状态</td>\n" +
            "        <td width=\"140\">审核人</td>\n" +
            "        <td width=\"140\">审核时间</td>\n" +
            "        <td width=\"140\">审核意见</td>\n" +
            "    </tr>\n" +
            "    </thead>\n" +
            "    <tbody>\n" +
            "     <tr>\n" +
            "        <td width=\"100\">" + data.CnTitle + "</td>\n" +
            "        <td width=\"150\">" + data.BeforeValue + "</td>\n" +
            "        <td width=\"150\">" + data.AfterValue + "</td>\n" +
            "        <td width=\"150\">" + data.operater + "</td>\n" +
            "        <td width=\"100\">" + data.CreateDate + "</td>\n" +
            "        <td width=\"100\">" + fmtState(data.Status) + "</td>\n" +
            "        <td width=\"100\">" + shieldNull(data.ApprovalUserId) + "</td>\n" +
            "        <td width=\"100\">" + shieldNull(data.ApprovalDate) + "</td>\n" +
            "        <td width=\"200\">" + shieldNull(data.Opinion) + "</td>\n" +
            "    </tr>\n" +
            "    </tbody>\n" +
            "</table>\n")
    })
}
//-----------------------------------------------------------------------------------
//留言列表
function lylb() {
    $("#leaveList").empty();
    $.post("/feedback/myList", function (data) {
        var html = "";
        for (var i = 0; i < data.length; i++) {
            html += "<div class=\"grxx-huifu clear\">\n" +
                " <div class=\"name text-c bt-l\">" + data[i].RealName + "</div>\n" +
                "  <div class=\"huifu-con bt-l\">\n" +
                "   <p class=\"onepai cge f15\">" + data[i].Title + "</p>\n" +
                "    <p class=\"c95 margintop15\">留言时间 ：" + data[i].CreateDate + "</p>\n" +
                "    <p class=\"jianjie onepai margintop20\">" + data[i].Content + "</p>\n" +
                "    <div class=\"margintop20 clear\">\n" +
                "     <div class=\"bt-l\" style=\"width: 700px\">\n" +
                "    <p><span>回复 ：" + shieldNull(data[i].ReplyContent) + "</span></p>\n" +
                "    <p class=\"c95 margintop10\">回复时间 ：" + shieldNull(data[i].ReplyDate) + "</p>\n" +
                "      </div>\n" +
                "      </div>\n" +
                "      </div>\n" +
                "    <div class=\"bt-r text-r\">\n" +
                "      <p class=\"c95\">\n" +
                "       <img src=\"/static/homePage/img/iPhone_03.png\"\n" +
                "         style=\"vertical-align: middle;margin-right: 5px\" alt=\"\">\n" +
                "         联系方式 ：" + shieldNull(data[i].ContactPhone) + "" +
                "      </p>\n" +
                "    </div>\n" +
                "   </div>";
        }
        $("#leaveList").append(html);
    })
}
//留言提交
function lytj() {
    var title = $("#leaveTitle").val();
    var content = $("#leaveContent").val();
    var contact = $("#leaveContact").val();
    $.post("/feedback/save", {Title: title, Content: content, Contact: contact}, function (data) {
        if (data.flag) {
            alert("留言成功");
            $("#leaveTitle").val("");
            $("#leaveContent").val("");
            $("#leaveContact").val("");
            $("#lygl").click();
        } else {
            alert("留言失败");
        }
    })
}
// 留言搜索
function lyss() {
    $("#leaveList").empty();
    var search = $("#sszd").val();
    $.post("/feedback/searchList", {content: search}, function (data) {
        var html = "";
        for (var i = 0; i < data.length; i++) {
            html += "<div class=\"grxx-huifu clear\">\n" +
                " <div class=\"name text-c bt-l\">" + data[i].RealName + "</div>\n" +
                "  <div class=\"huifu-con bt-l\">\n" +
                "   <p class=\"onepai cge f15\">" + data[i].Title + "</p>\n" +
                "    <p class=\"c95 margintop15\">留言时间 ：" + data[i].CreateDate + "</p>\n" +
                "    <p class=\"jianjie onepai margintop20\">" + data[i].Content + "</p>\n" +
                "    <div class=\"margintop20 clear\">\n" +
                "     <div class=\"bt-l\" style=\"width: 700px\">\n" +
                "    <p><span>回复 ：" + shieldNull(data[i].ReplyContent) + "</span></p>\n" +
                "    <p class=\"c95 margintop10\">回复时间 ：" + shieldNull(data[i].ReplyDate) + "</p>\n" +
                "      </div>\n" +
                "      </div>\n" +
                "      </div>\n" +
                "    <div class=\"bt-r text-r\">\n" +
                "      <p class=\"c95\">\n" +
                "       <img src=\"/static/homePage/img/iPhone_03.png\"\n" +
                "         style=\"vertical-align: middle;margin-right: 5px\" alt=\"\">\n" +
                "         联系方式 ：" + shieldNull(data[i].ContactPhone) + "" +
                "      </p>\n" +
                "    </div>\n" +
                "   </div>";
        }
        $("#leaveList").append(html);
    })
}
//-----------------------------------------------------------------------------------
// 投诉列表
function tslb() {
    $("#complaintList").empty();
    $.post("/complaint/myList", function (data) {
        var html = "";
        for (var i = 0; i < data.length; i++) {
            html += "<tr>\n" +
                "<td width=\"150\">" + data[i].CorpName + "</td>\n" +
                "<td width=\"100\">" + data[i].jobName + "</td>\n" +
                "<td width=\"120\">" + data[i].typeName + "</td>\n" +
                "<td width=\"150\">" + data[i].Reason + "</td>\n" +
                "<td width=\"170\">" + data[i].CreateDate + "</td>\n" +
                "<td width=\"90\" class=\"cge\">" + data[i].statusName + "</td>\n" +
                "<td width=\"100\">" + data[i].auditUserName + "</td>\n" +
                "<td width=\"170\">" + data[i].AuditReamrks + "</td>\n" +
                "<td width=\"150\">" + data[i].AuditDate + "</td>\n" +
                "</tr>"
        }
        $("#complaintList").append(html);
    })
}
// 投诉搜索
function tsss() {
    $("#complaintList").empty();
    var search = $("#tszd").val();
    $.post("/complaint/searchList", {content: search}, function (data) {
        var html = "";
        for (var i = 0; i < data.length; i++) {
            html += "<tr>\n" +
                "<td width=\"150\">" + data[i].CorpName + "</td>\n" +
                "<td width=\"100\">" + data[i].jobName + "</td>\n" +
                "<td width=\"120\">" + data[i].typeName + "</td>\n" +
                "<td width=\"150\">" + data[i].Reason + "</td>\n" +
                "<td width=\"170\">" + data[i].CreateDate + "</td>\n" +
                "<td width=\"90\" class=\"cge\">" + data[i].statusName + "</td>\n" +
                "<td width=\"100\">" + data[i].auditUserName + "</td>\n" +
                "<td width=\"170\">" + data[i].AuditReamrks + "</td>\n" +
                "<td width=\"150\">" + data[i].AuditDate + "</td>\n" +
                "</tr>"
        }
        $("#complaintList").append(html);
    })
}
//-----------------------------------------------------------------------------------
// 问卷列表
function wjlb() {
    $("#questionList").empty();
    $.post("/survey/questionList", function (data) {
        var html = "";
        for (var i = 0; i < data.length; i++) {
            html += "<div class=\"margintop20 question clear\">\n" +
                "  <img class=\"bt-l \" src=\"/static/homePage/img/question_03.png\" alt=\"\">\n" +
                "  <div class=\"bt-l marginleft20\">\n" +
                "      <p class=\"onepai cge f15 margintop10\">" + data[i].survey_name + "</p>\n" +
                "      <p class=\"c95 margintop10\">\n" +
                "          <span class=\"marginr30\">修改时间 ：" + data[i].create_date + "</span>\n" +
                "          <a href=\"/survey/toAnswer?sid="+data[i].sid+"\" target='_blank' class=\"marginr30\" ><u>【查询详情】</u></a>\n" +
                "      </p>\n" +
                "  </div>\n" +
                "  <img class=\"bt-r margintop20\" src=\"/static/homePage/img/center-index_43-56.png\" alt=\"\">\n" +
                "                </div>\n" +
                "                <div class=\"line3 margintop20\"></div>"
        }
        $("#questionList").append(html);
    })
}
// 问卷搜索
function wjss() {
    $("#questionList").empty();
    var search = $("#wjzd").val();
    $.post("/survey/searchList", {content: search}, function (data) {
        var html = "";
        for (var i = 0; i < data.length; i++) {
            html += "<div class=\"margintop20 question clear\">\n" +
                "  <img class=\"bt-l \" src=\"/static/homePage/img/question_03.png\" alt=\"\">\n" +
                "  <div class=\"bt-l marginleft20\">\n" +
                "      <p class=\"onepai cge f15 margintop10\">问卷调查-" + data[i].survey_name + "</p>\n" +
                "      <p class=\"c95 margintop10\">\n" +
                "          <span class=\"marginr30\">修改时间 ：" + data[i].create_date + "</span>\n" +
                "          <a href=\"/survey/toAnswer?sid="+data[i].sid+"\" target='_blank' class=\"marginr30\" ><u>【查询详情】</u></a>\n" +
                "      </p>\n" +
                "  </div>\n" +
                "  <img class=\"bt-r margintop20\" src=\"/static/homePage/img/center-index_43-56.png\" alt=\"\">\n" +
                "   </div>\n" +
                "    <div class=\"line3 margintop20\"></div>"
        }
        $("#questionList").append(html);
    })
}
//-----------------------------------------------------------------------------------
//社会招聘（自行联系）
function shlb() {
    $("#shxx").empty();
    $.post("/corp/zxlx", function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#shxx").append(" <tr>\n" +
                "    <td width=\"150\">" + data[i].CorpName + "</td>\n" +
                "    <td width=\"110\">" + shieldNull(data[i].IndustryCode) + "</td>\n" +
                "    <td width=\"80\">" + shieldNull(data[i].NatureCdoe) + "</td>\n" +
                "    <td width=\"100\">" + shieldNull(data[i].ScaleCode) + "</td>\n" +
                "    <td width=\"100\">" + shieldNull(data[i].CapitalCode) + "</td>\n" +
                "    <td width=\"100\">" + shieldNull(data[i].OrginCode) + "</td>\n" +
                "    <td width=\"140\">" + shieldNull(data[i].Contactor) + "</td>\n" +
                "    <td width=\"140\">" + shieldNull(data[i].ContactPhone) + "</td>\n" +
                "    <td width=\"140\">" + shieldNull(data[i].Email) + "</td>\n" +
                "    <td width=\"140\">" + shieldNull(data[i].Address) + "</td>\n" +
                "</tr>")
        }
    })
}
//-----------------------------------------------------------------------------------
//专场招聘
function zclb() {
    $("#zcxx").empty();
    $.post("/recruit/stuList", function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#zcxx").append(" <tr>\n" +
                "    <td width=\"150\">" + data[i].CorpName + "</td>\n" +
                "    <td width=\"110\">" + shieldNull(data[i].dwhy) + "</td>\n" +
                "    <td width=\"80\">" + shieldNull(data[i].dwxz) + "</td>\n" +
                "    <td width=\"100\">" + shieldNull(data[i].dwgm) + "</td>\n" +
                "    <td width=\"100\">" + shieldNull(data[i].dwdz) + "</td>\n" +
                "    <td width=\"140\">" + data[i].placeName + "</td>\n" +
                "    <td width=\"140\">" + data[i].Address + "</td>\n" +
                "    <td width=\"140\">" + fmtDateMT(data[i].UseDate) + "</td>\n" +
                "    <td width=\"140\">" + data[i].StartHour + "~" + data[i].EndHour + "</td>\n" +
                "</tr>")
        }
    })
}
//-----------------------------------------------------------------------------------
//简历投递
function jllb() {
    $("#jlxx").empty();
    $.post("/resumePost/ytjl", function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#jlxx").append(" <tr>\n" +
                "    <td width=\"150\">" + data[i].CorpName + "</td>\n" +
                "    <td width=\"110\">" + shieldNull(data[i].Title) + "</td>\n" +
                "    <td width=\"80\">" + shieldNull(data[i].Functions) + "</td>\n" +
                "    <td width=\"100\">" + shieldNull(data[i].WorkAddress) + "</td>\n" +
                "    <td width=\"100\">" + shieldNull(data[i].PeopleCount) + "</td>\n" +
                "    <td width=\"140\">" + shieldNull(data[i].Salary) + "</td>\n" +
                "    <td width=\"140\">" + shieldNull(data[i].Contactor) + "</td>\n" +
                "    <td width=\"140\">" + shieldNull(data[i].ContactPhone) + "</td>\n" +
                "</tr>")
        }
    })
}
//-----------------------------------------------------------------------------------
//双选会
function sxlb() {
    $("#sxxx").empty();
    $.post("/election/stuList", function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#sxxx").append(" <tr>\n" +
                "    <td width=\"150\">" + data[i].Title + "</td>\n" +
                "    <td width=\"110\">" + shieldNull(data[i].Address) + "</td>\n" +
                "    <td width=\"80\">" + fmtDateMT(data[i].RunDate) + "</td>\n" +
                "    <td width=\"100\">" + shieldNull(data[i].SignEndDate) + "</td>\n" +
                "    <td width=\"100\">" + shieldNull(data[i].Description) + "</td>\n" +
                "</tr>")
        }
    })
}
//-----------------------------------------------------------------------------------
//签约
function sign() {
    var args = {};
    $("#qygl").find('select').each(function (index, ele) {
        var key = $(ele).attr('name');
        var value = $(ele).val();
        if (key != undefined && value != undefined && key != null && value != null && key != "" && value != "") {
                args[key] = value;
        }
    })
    $("#qygl").find('input').each(function (index, ele) {
        var key = $(ele).attr('name');
        var value = $(ele).val();
        if (key != undefined && value != undefined && key != null && value != null && key != "" && value != "") {
            args[key] = value;
        }
    })
    const id = $("#signStudentId").data("value");
    args['StudentId']=id;
    console.log(args)
    $.ajax({
        type: 'POST',
        url: '/student/saveSign',
        dataType: 'JSON',
        data: args,
        success: function (data) {
            if (data.flag) {
                alert(data.msg)
            } else {
                alert(data.msg);
                signview();
            }
        }

    })
}
//回显签约信息
function signview() {
    const id = $("#signStudentId").data("value");
    $.post("/student/sign/" + id, function (data) {
        $("#qygl").find("input").each(function (index, ele) {
            var code = $(ele).attr("name")
            $(ele).val(data['' + code + '']);
        })
        $("#qygl").find("select").each(function (index, ele) {
            var code = $(ele).attr("name")
            $(ele).val(data['' + code + '']);
        })
        $("#qygl").find("#CorpOrginCode").empty();
        if (data.CorpOrginCode == null || data.CorpOrginCode == "" || data.CorpOrginCode == undefined) {
            $.post("/area/parentList", function (data) {
                var html = "<select name='CorpOrginCode' onchange='corpchild(this)' class='top'><option value=''>--请选择--</option>";
                for (var i = 0; i < data.length; i++) {
                    if (data[i].parentId == 0) {
                        html += "<option value='" + data[i].areaCode + "'>" + data[i].areaName + "</option>";
                    }
                }
                html += "</select>"
                $("#qygl").find("#CorpOrginCode").html(html);
            })
        } else {
            $.post("/area/findSame", {AreaCode: data.CorpOrginCode}, function (data1) {
                if (data1 != null && data1.length > 0) {
                    var html = "<select name='CorpOrginCode'>";
                    for (var i = 0; i < data1.length; i++) {
                        if (data.CorpOrginCode == data1[i].areaCode){
                            html += "<option value='" + data1[i].areaCode + "' selected>" + data1[i].areaName + "</option>";
                        }
                        html += "<option value='" + data1[i].areaCode + "'>" + data1[i].areaName + "</option>";
                    }
                    html += "</select><button class=\"center-btn\" style='width: 100px;' onclick='changecorparea()'>修改地区</button>";
                    $("#qygl").find("#CorpOrginCode").append(html);
                }
            })
        }
    })
}
//级联地区
function corpchild(t) {
    var code = $(t).val();
    if ($(t).attr("class") == 'top') {
        $("#qygl").find(".parent").remove();
        $("#qygl").find(".children").remove();
    }
    if ($(t).attr("class") == 'parent') {
        $("#qygl").find(".children").remove();
    }
    $.post("/area/findChildren", {AreaCode: code}, function (data) {
        if (data != null && data.length > 0) {
            var html = "<select name='CorpOrginCode' onchange='corpchild(this)' class='children'><option value=''>--请选择--</option>";
            for (var i = 0; i < data.length; i++) {
                html += "<option value='" + data[i].areaCode + "'>" + data[i].areaName + "</option>";
            }
            html += "</select>";
            $("#qygl").find(".children").removeAttr("name");
            $("#qygl").find(".top").removeAttr("name");
            $("#qygl").find(".children").attr("class", "parent");
            $("#qygl").find(".children").remove();
            $("#qygl").find("#CorpOrginCode").append(html);
        }
    })
}
//修改地区
function changecorparea() {
    $("#qygl").find("#CorpOrginCode").empty();
    $.post("/area/parentList", function (data) {
        var html = "<select name='CorpOrginCode' onchange='corpchild(this)' class='top'><option value=''>--请选择--</option>";
        for (var i = 0; i < data.length; i++) {
            if (data[i].parentId == 0) {
                html += "<option value='" + data[i].areaCode + "'>" + data[i].areaName + "</option>";
            }
        }
        html += "</select>"
        $("#qygl").find("#CorpOrginCode").html(html);
    })
}
//-----------------------------------------------------------------------------------
//工作证明
function work() {
    var args = {};
    $("#gzzm").find('select').each(function (index, ele) {
        var key = $(ele).attr('name');
        var value = $(ele).val();
        if (key != undefined && value != undefined && key != null && value != null && key != "" && value != "") {
            args[key] = value;
        }
    })
    $("#gzzm").find('input').each(function (index, ele) {
        var key = $(ele).attr('name');
        var value = $(ele).val();
        if (key != undefined && value != undefined && key != null && value != null && key != "" && value != "") {
            args[key] = value;
        }
    })
    const id = $("#signStudentId").data("value");
    args['StudentId']=id;
    console.log(args)
    $.ajax({
        type: 'POST',
        url: '/student/saveWork',
        dataType: 'JSON',
        data: args,
        success: function (data) {
            if (data.flag) {
                alert(data.msg)
            } else {
                alert(data.msg);
                workview();
            }
        }

    })
}
//回显工作证明
function workview() {
    const id = $("#signStudentId").data("value");
    $.post("/student/work/" + id, function (data) {
        if (data.GraduationWhereAboutCode==11||data.GraduationWhereAboutCode==12) {
            //签劳动合同形式就业,其他录用形式就业
            $("#gzzm").find("#HighGradeTypeCode,#WaitingTypeCode").attr("style","display:none")
        }else if (data.GraduationWhereAboutCode==27||data.GraduationWhereAboutCode==50||data.GraduationWhereAboutCode==51){
            //科研助理,国家基层项目,地方基层项目
            $("#gzzm").find("#HighGradeTypeCode,#CorpNatureCdoe,#CorpIndustryCode," +
                "#UnderDepartmentCode,#CorpOrganizationCode,#JobTypeCode,#WaitingTypeCode")
                .attr("style","display:none")
        }else if (data.GraduationWhereAboutCode==46){
            //征兵
            $("#gzzm").find("#HighGradeTypeCode,#CorpNatureCdoe,#CorpIndustryCode,#WaitingTypeCode," +
                "#UnderDepartmentCode,#CorpOrganizationCode,#JobTypeCode,#CorporationName,#CorpArea")
                .attr("style","display:none")
        }else if (data.GraduationWhereAboutCode==70||data.GraduationWhereAboutCode==71||data.GraduationWhereAboutCode==72){
            //待就业,拟升学,其他暂不就业
            $("#gzzm").find("#HighGradeTypeCode,#CorpNatureCdoe,#CorpIndustryCode," +
                "#UnderDepartmentCode,#CorpOrganizationCode,#JobTypeCode,#CorporationName,#CorpArea")
                .attr("style","display:none")
        }else if (data.GraduationWhereAboutCode==75){
            //自主创业
            $("#gzzm").find("#HighGradeTypeCode,#CorpNatureCdoe,#UnderDepartmentCode," +
                "#CorpOrganizationCode,#JobTypeCode,#WaitingTypeCode")
                .attr("style","display:none")
        }else if (data.GraduationWhereAboutCode==76){
            //自由职业
            $("#gzzm").find("#HighGradeTypeCode,#CorpNatureCdoe,#UnderDepartmentCode," +
                "#CorpOrganizationCode,#JobTypeCode,#WaitingTypeCode,#CorpIndustryCode")
                .attr("style","display:none")
        }else if (data.GraduationWhereAboutCode==80){
            //升学
            $("#gzzm").find("#CorpNatureCdoe,#UnderDepartmentCode,#CorpArea," +
                "#CorpOrganizationCode,#JobTypeCode,#WaitingTypeCode,#CorpIndustryCode")
                .attr("style","display:none")
        }else if (data.GraduationWhereAboutCode==85){
            //出国出境
            $("#gzzm").find("#HighGradeTypeCode,#CorpNatureCdoe,#UnderDepartmentCode,#CorpArea," +
                "#CorpOrganizationCode,#JobTypeCode,#WaitingTypeCode,#CorpIndustryCode")
                .attr("style","display:none")
        }
        $("#gzzm").find("input").each(function (index, ele) {
            var code = $(ele).attr("name")
            $(ele).val(data['' + code + '']);
        })
        $("#gzzm").find("select").each(function (index, ele) {
            var code = $(ele).attr("name")
            $(ele).val(data['' + code + '']);
        })
        $("#gzzm").find("#CorpOrginCode").empty();
        if (data.CorpOrginCode == null || data.CorpOrginCode == "" || data.CorpOrginCode == undefined) {
            $.post("/area/parentList", function (data) {
                var html = "<select name='CorpOrginCode' onchange='workchild(this)' class='top'><option value=''>--请选择--</option>";
                for (var i = 0; i < data.length; i++) {
                    if (data[i].parentId == 0) {
                        html += "<option value='" + data[i].areaCode + "'>" + data[i].areaName + "</option>";
                    }
                }
                html += "</select>"
                $("#gzzm").find("#CorpOrginCode").html(html);
            })
        } else {
            $.post("/area/findSame", {AreaCode: data.CorpOrginCode}, function (data1) {
                if (data1 != null && data1.length > 0) {
                    var html = "<select name='CorpOrginCode'>";
                    for (var i = 0; i < data1.length; i++) {
                        if (data.CorpOrginCode == data1[i].areaCode){
                            html += "<option value='" + data1[i].areaCode + "' selected>" + data1[i].areaName + "</option>";
                        }
                        html += "<option value='" + data1[i].areaCode + "'>" + data1[i].areaName + "</option>";
                    }
                    html += "</select><button class=\"center-btn\" style='width: 100px;' onclick='changeworkarea()'>修改地区</button>";
                    $("#gzzm").find("#CorpOrginCode").append(html);
                }
            })
        }
    })
}
//级联地区
function workchild(t) {
    var code = $(t).val();
    if ($(t).attr("class") == 'top') {
        $("#gzzm").find(".parent").remove();
        $("#gzzm").find(".children").remove();
    }
    if ($(t).attr("class") == 'parent') {
        $("#gzzm").find(".children").remove();
    }
    $.post("/area/findChildren", {AreaCode: code}, function (data) {
        if (data != null && data.length > 0) {
            var html = "<select name='CorpOrginCode' onchange='workchild(this)' class='children'><option value=''>--请选择--</option>";
            for (var i = 0; i < data.length; i++) {
                html += "<option value='" + data[i].areaCode + "'>" + data[i].areaName + "</option>";
            }
            html += "</select>";
            $("#gzzm").find(".children").removeAttr("name");
            $("#gzzm").find(".top").removeAttr("name");
            $("#gzzm").find(".children").attr("class", "parent");
            $("#gzzm").find(".children").remove();
            $("#gzzm").find("#CorpOrginCode").append(html);
        }
    })
}
//修改地区
function changeworkarea() {
    $("#gzzm").find("#CorpOrginCode").empty();
    $.post("/area/parentList", function (data) {
        var html = "<select name='CorpOrginCode' onchange='workchild(this)' class='top'><option value=''>--请选择--</option>";
        for (var i = 0; i < data.length; i++) {
            if (data[i].parentId == 0) {
                html += "<option value='" + data[i].areaCode + "'>" + data[i].areaName + "</option>";
            }
        }
        html += "</select>"
        $("#gzzm").find("#CorpOrginCode").html(html);
    })
}
//毕业去向选择
function byqxxz(t) {
    var choice = $(t).val();
    $("#gzzm").children().each(function () {
        $(this).attr("style","");
    })
    if (choice==11||choice==12) {
        //签劳动合同形式就业,其他录用形式就业
        $("#gzzm").find("#HighGradeTypeCode,#WaitingTypeCode").attr("style","display:none")
    }else if (choice==27||choice==50||choice==51){
        //科研助理,国家基层项目,地方基层项目
        $("#gzzm").find("#HighGradeTypeCode,#CorpNatureCdoe,#CorpIndustryCode," +
            "#UnderDepartmentCode,#CorpOrganizationCode,#JobTypeCode,#WaitingTypeCode")
            .attr("style","display:none")
    }else if (choice==46){
        //征兵
        $("#gzzm").find("#HighGradeTypeCode,#CorpNatureCdoe,#CorpIndustryCode,#WaitingTypeCode," +
            "#UnderDepartmentCode,#CorpOrganizationCode,#JobTypeCode,#CorporationName,#CorpArea")
            .attr("style","display:none")
    }else if (choice==70||choice==71||choice==72){
        //待就业,拟升学,其他暂不就业
        $("#gzzm").find("#HighGradeTypeCode,#CorpNatureCdoe,#CorpIndustryCode," +
            "#UnderDepartmentCode,#CorpOrganizationCode,#JobTypeCode,#CorporationName,#CorpArea")
            .attr("style","display:none")
    }else if (choice==75){
        //自主创业
        $("#gzzm").find("#HighGradeTypeCode,#CorpNatureCdoe,#UnderDepartmentCode," +
            "#CorpOrganizationCode,#JobTypeCode,#WaitingTypeCode")
            .attr("style","display:none")
    }else if (choice==76){
        //自由职业
        $("#gzzm").find("#HighGradeTypeCode,#CorpNatureCdoe,#UnderDepartmentCode," +
            "#CorpOrganizationCode,#JobTypeCode,#WaitingTypeCode,#CorpIndustryCode")
            .attr("style","display:none")
    }else if (choice==80){
        //升学
        $("#gzzm").find("#CorpNatureCdoe,#UnderDepartmentCode,#CorpArea," +
            "#CorpOrganizationCode,#JobTypeCode,#WaitingTypeCode,#CorpIndustryCode")
            .attr("style","display:none")
    }else if (choice==85){
        //出国出境
        $("#gzzm").find("#HighGradeTypeCode,#CorpNatureCdoe,#UnderDepartmentCode,#CorpArea," +
            "#CorpOrganizationCode,#JobTypeCode,#WaitingTypeCode,#CorpIndustryCode")
            .attr("style","display:none")
    }
}
//-----------------------------------------------------------------------------------
//改签
function changesign() {
    var args = {};
    $("#gqgl").find('select').each(function (index, ele) {
        var key = $(ele).attr('name');
        var value = $(ele).val();
        if (key != undefined && value != undefined && key != null && value != null && key != "" && value != "") {
            args[key] = value;
        }
    })
    $("#gqgl").find('input').each(function (index, ele) {
        var key = $(ele).attr('name');
        var value = $(ele).val();
        if (key != undefined && value != undefined && key != null && value != null && key != "" && value != "") {
            args[key] = value;
        }
    })
    const id = $("#signStudentId").data("value");
    args['StudentId']=id;
    console.log(args)
    $.ajax({
        type: 'POST',
        url: '/changesign/save',
        dataType: 'JSON',
        data: args,
        success: function (data) {
            if (data.flag) {
                alert(data.msg)
            } else {
                alert(data.msg);
                changesignview();
            }
        }

    })
}
//回显改签信息
function changesignview() {
    const id = $("#signStudentId").data("value");
    $.post("/changesign/studentSign/" + id, function (data) {
        $("#gqgl").find("input").each(function (index, ele) {
            var code = $(ele).attr("name")
            $(ele).val(data['' + code + '']);
        })
        $("#gqgl").find("select").each(function (index, ele) {
            var code = $(ele).attr("name")
            $(ele).val(data['' + code + '']);
        })
        $("#gqgl").find("#CorpOrginCode").empty();
        if (data.CorpOrginCode == null || data.CorpOrginCode == "" || data.CorpOrginCode == undefined) {
            $.post("/area/parentList", function (data) {
                var html = "<select name='CorpOrginCode' onchange='changesignchild(this)' class='top'><option value=''>--请选择--</option>";
                for (var i = 0; i < data.length; i++) {
                    if (data[i].parentId == 0) {
                        html += "<option value='" + data[i].areaCode + "'>" + data[i].areaName + "</option>";
                    }
                }
                html += "</select>"
                $("#gqgl").find("#CorpOrginCode").html(html);
            })
        } else {
            $.post("/area/findSame", {AreaCode: data.CorpOrginCode}, function (data1) {
                if (data1 != null && data1.length > 0) {
                    var html = "<select name='CorpOrginCode'>";
                    for (var i = 0; i < data1.length; i++) {
                        if (data.CorpOrginCode == data1[i].areaCode){
                            html += "<option value='" + data1[i].areaCode + "' selected>" + data1[i].areaName + "</option>";
                        }
                        html += "<option value='" + data1[i].areaCode + "'>" + data1[i].areaName + "</option>";
                    }
                    html += "</select><button class=\"center-btn\" style='width: 100px;' onclick='changesignarea()'>修改地区</button>";
                    $("#gqgl").find("#CorpOrginCode").append(html);
                }
            })
        }
    })
}
//级联地区
function changesignchild(t) {
    var code = $(t).val();
    if ($(t).attr("class") == 'top') {
        $("#gqgl").find(".parent").remove();
        $("#gqgl").find(".children").remove();
    }
    if ($(t).attr("class") == 'parent') {
        $("#gqgl").find(".children").remove();
    }
    $.post("/area/findChildren", {AreaCode: code}, function (data) {
        if (data != null && data.length > 0) {
            var html = "<select name='CorpOrginCode' onchange='changesignchild(this)' class='children'><option value=''>--请选择--</option>";
            for (var i = 0; i < data.length; i++) {
                html += "<option value='" + data[i].areaCode + "'>" + data[i].areaName + "</option>";
            }
            html += "</select>";
            $("#gqgl").find(".children").removeAttr("name");
            $("#gqgl").find(".top").removeAttr("name");
            $("#gqgl").find(".children").attr("class", "parent");
            $("#gqgl").find(".children").remove();
            $("#gqgl").find("#CorpOrginCode").append(html);
        }
    })
}
//修改地区
function changesignarea() {
    $("#gqgl").find("#CorpOrginCode").empty();
    $.post("/area/parentList", function (data) {
        var html = "<select name='CorpOrginCode' onchange='changesignchild(this)' class='top'><option value=''>--请选择--</option>";
        for (var i = 0; i < data.length; i++) {
            if (data[i].parentId == 0) {
                html += "<option value='" + data[i].areaCode + "'>" + data[i].areaName + "</option>";
            }
        }
        html += "</select>"
        $("#gqgl").find("#CorpOrginCode").html(html);
    })
}
//-----------------------------------------------------------------------------------
// 补办报到证
function bdsq() {
    const studentid =  $("#signStudentId").data("value");
    const typecode = $("#TypeCode").val();
    $.ajax({
        type: 'POST',
        url: '/makeupregistercard/save',
        dataType: 'JSON',
        data: {StudentId: studentid, TypeCode: typecode},
        success: function (data) {
            if (data.flag) {
                alert(data.msg)
            } else {
                alert(data.msg);
            }
            bdxx();
        }

    })
}
//报到证补办详细
function bdxx() {
    $("#bbxx").empty();
    const id =  $("#signStudentId").data("value");
    $.post("/makeupregistercard/studentList/" + id, function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#bbxx").append( "<tr>" +
                " <td width=\"100\">" + data[i].TypeCode + "</td>\n" +
                " <td width=\"100\">" + data[i].Applyer + "</td>\n" +
                " <td width=\"150\">" + data[i].CreateDate + "</td>\n" +
                " <td width=\"100\">" + fmtState(data[i].Status) + "</td>\n" +
                " <td width=\"100\">" + shieldNull(data[i].Approvaler) + "</td>\n" +
                " <td width=\"100\">" + shieldNull(data[i].ExamineDate) + "</td>\n" +
                " <td width=\"200\">" + shieldNull(data[i].ExamineRemark) + "</td>\n" +
                "  </tr>")
        }
    })
}

/**
 * 分页
 * @param total
 * @param page
 * @param code
 */
function fy(total, page, code) {
    $("#fy").empty();
    var fy = "";
    fy += '<div class="news-tabs c32 text-c" >\n' +
        '    <a href="javascript:void(0)" onclick="previousPage(' + code + ');return false;" style="width: 80px"> < 上一页</a>\n';
    /* '    <a href="" class="news-tabs-active">1</a>\n';*/
    for (var j = 1; j <= total; j++) {
        if (page == j) {
            fy += '<a href="javascript:void(0)" onclick="clickPage(this,' + code + ');return false;"  class="news-tabs-active" id="dqy">' + j + '</a>\n';
        } else {
            fy += '<a href="javascript:void(0)" onclick="clickPage(this,' + code + ');return false;">' + j + '</a>\n';
        }
    }
    fy += '<a  href="javascript:void(0)" onclick="nextPage(' + total + ',' + code + ');return false;" style="width: 80px">下一页 > </a>\n';
    fy += '<span>共' + total + '页，到第</span>\n' +
        '<span for="">\n' +
        '  <input type="number" min="1" id="srk">\n' +
        '  页\n' +
        ' </span>\n' +
        ' <a href="javascript:void(0)" onclick="jumpPage(' + total + ',' + code + ');return false;" style="width: 70px">确 认</a>\n' +
        '</div>';
    $("#fy").append(fy);
}

//上一页
function previousPage(code) {
    var currentPage = $("#dqy").text();
    if (currentPage <= 1) {
        alert("已经是第一页了");
    } else {
        queryTzgg(code, currentPage - 1);
    }
}

//下一页
function nextPage(page, code) {
    var currentPage = parseInt($("#dqy").text());
    if (currentPage >= page) {
        alert("已经是最后一页了");
    } else {
        queryTzgg(code, currentPage + 1);
    }
}

//点击
function clickPage(obj, code) {
    var page = parseInt(obj.text);
    queryTzgg(code, page);
}

//跳转
function jumpPage(total, code) {
    var page = $("#srk").val();
    if (page > total || page < 1) {
        alert("该页码不存在，请重新输入");
    } else {
        queryTzgg(code, page);
    }
}
