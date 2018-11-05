//加载基础信息
$(function () {
    $("select").each(function (index, ele) {
        const code = $(ele).data("code");
        if (code != null) {
            $.post("/dictionary/dictionaryCode/" + code, function (data) {
                var HTML = "";
                for (var i = 0; i < data.length; i++) {
                    HTML += "<option value='" + data[i].memberValue + "'>" + data[i].displayText + "</option>";
                }
                $(ele).html(HTML);
            })
        }
    })
    const id = $("#StudentId").val();
    $.post("/student/edit/" + id, function (data) {
        $("#jcxx").find("input").each(function (index, ele) {
            var code = $(ele).attr("name")
            $(ele).val(data['' + code + '']);
        })
        $("#jcxx").find("select").each(function (index, ele) {
            var code = $(ele).attr("name");
            $(ele).val("" + data['' + code + ''] + "");
        })
        $("#OriginCode").empty();
        if (data.originCode == null || data.originCode == "" || data.originCode == undefined) {
            $.post("/area/parentList", function (data) {
                var html = "<select name='OriginCode' onchange='child(this)' class='top'><option value=''>--请选择--</option>";
                for (var i = 0; i < data.length; i++) {
                    if (data[i].parentId == 0) {
                        html += "<option value='" + data[i].areaCode + "'>" + data[i].areaName + "</option>";
                    }
                }
                html += "</select>"
                $("#OriginCode").html(html);
            })
        } else {
            $.post("/area/findSame", {AreaCode: data.originCode}, function (data1) {
                if (data1 != null && data1.length > 0) {
                    var html = "<select name='OriginCode'>";
                    for (var i = 0; i < data1.length; i++) {
                        if (data.originCode == data1[i].areaCode){
                            html += "<option value='" + data1[i].areaCode + "' selected>" + data1[i].areaName + "</option>";
                        }
                        html += "<option value='" + data1[i].areaCode + "'>" + data1[i].areaName + "</option>";
                    }
                    html += "</select><button class=\"center-btn\" style='width: 100px;' onclick='changearea()'>修改地区</button>";
                    $("#OriginCode").append(html);
                }
            })
        }
    })
})

//级联地区
function child(t) {
    var code = $(t).val();
    if ($(t).attr("class") == 'top') {
        $("#jcxx").find(".parent").remove();
        $("#jcxx").find(".children").remove();
    }
    if ($(t).attr("class") == 'parent') {
        $("#jcxx").find(".children").remove();
    }
    $.post("/area/findChildren", {AreaCode: code}, function (data) {
        if (data != null && data.length > 0) {
            var html = "<select name='originCode' onchange='child(this)' class='children'><option value=''>--请选择--</option>";
            for (var i = 0; i < data.length; i++) {
                html += "<option value='" + data[i].areaCode + "'>" + data[i].areaName + "</option>";
            }
            html += "</select>";
            $("#jcxx").find(".children").removeAttr("name");
            $("#jcxx").find(".top").removeAttr("name");
            $("#jcxx").find(".children").attr("class", "parent");
            $("#jcxx").find(".children").remove();
            $("#OriginCode").append(html);
        }
    })
}

function changearea() {
    $("#OriginCode").empty();
    $.post("/area/parentList", function (data) {
        var html = "<select name='OriginCode' onchange='child(this)' class='top'><option value=''>--请选择--</option>";
        for (var i = 0; i < data.length; i++) {
            if (data[i].parentId == 0) {
                html += "<option value='" + data[i].areaCode + "'>" + data[i].areaName + "</option>";
            }
        }
        html += "</select>"
        $("#OriginCode").html(html);
    })
}

function submitchange() {
    var args = {};
    $("#jcxx").find('select').each(function (index, ele) {
        var key = $(ele).attr('name');
        var value = $(ele).val();
        if (key != undefined && value != undefined && key != null && value != null && key != "" && value != "") {
            if ($(ele).data('code') != undefined) {
                args[key] = $(ele).find("option:selected").text();
            } else {
                args[key] = value;
            }
        }
    })
    $("#jcxx").find('input').each(function (index, ele) {
        var key = $(ele).attr('name');
        var value = $(ele).val();
        if (key != undefined && value != undefined && key != null && value != null && key != "" && value != "") {
            args[key] = value;
        }
    })
    $.ajax({
        type: 'post',
        url: '/examine/save',
        data: args,
        dataType: 'json',
        success: function (data) {
            alert(data.msg)
        }
    })
}
