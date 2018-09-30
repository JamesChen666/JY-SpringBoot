$(function () {
    $(document).on('click', ".btn-group>.dropdown-menu>li", function () {
        $(this).find("a").attr("href", "javascript:;");
        var buttonObj = $(this).parent().parent().find("button[class*='btn']").first();
        $(this).parent().parent().find("input:first").val($(this).find('span:first').attr('data_value'));
        var buttonHtml = buttonObj.html();
        var buttonText = buttonHtml.replace('<span class="caret"></span>', '');
        var selectedText = $(this).find("a").html();
        buttonObj.html(buttonHtml.replace(buttonText, selectedText));
        var cloneObj = $(this).clone(true);
        $(cloneObj).html(cloneObj.html().replace(selectedText, buttonText));
        $(this).parent().append($(cloneObj));
        $(this).remove();
        if (buttonObj.attr("id") == 'pager-selbox') {
            var selectObj = $(".ui-pg-selbox");
            if (selectObj && selectObj.length == 1) {
                selectObj.val(selectedText.replace("条", ''));
                selectObj.change();
            }
        }
    });
    $(window).resize(function () {
        window.setTimeout(resizeJQGrid, 200);
    });
});

function resizeJQGrid() {
    sizeArr = getGridTableSize();
    $("#list").jqGrid('setGridHeight', sizeArr.vHeight);
    $("#list").jqGrid('setGridWidth', sizeArr.vWidth - 100)
    $("#list").jqGrid('setGridWidth', sizeArr.vWidth);
    $("#sorry").css('display', 'none');
}

function do_search(obj) {
    $("#list").jqGrid('setGridParam', {
        url: $('#gopage_url').val(),
        page: 1,
        postData: obj
    }).trigger('reloadGrid');
}

$("#search").keydown(function () {
    var e = window.event || arguments.callee.caller.arguments[0];
    if (e.keyCode == 13) {
        $("#do_search").click();
    }
});

function gopager() {
    var gopage_val = parseInt($('#gopage').val());
    if (isNaN(gopage_val) || gopage_val <= 0 || parseInt($('#sp_1_pager').html()) < gopage_val) {
        alert(sprintf(td_lang.general.workflow.msg_89, parseInt($('#sp_1_pager').html())));
        $('#gopage').val('');
        return false;
    }
    $('.ui-pg-input').val(gopage_val);
    $("#list").jqGrid('setGridParam', {
        url: $('#gopage_url').val(),
        page: gopage_val+1
    }).trigger('reloadGrid');
}

function jqLoadComplete(_this, otherHeight) {
    if (otherHeight == undefined)
        otherHeight = 0;
    //toolbar容器
    $(".button-operation").html("");
    //查询面板
    $(".button-operation").before($($(_this).getGridParam("toolbar")));

    $("#pager_left td").each(function (i, e) {
        var btn = $("<button>");
        btn.attr("type", "button");
        btn.attr("class", "btn btn-info");
        if (e.id != "search_list") {
            if (e.id == "refresh_list") {
                $(btn).removeClass("btn-info");
                $(btn).addClass("btn-primary");
                btn.html("刷新");
            }
            else {
                if ($(e).text().indexOf("删除") != -1 || $(e).text().indexOf("清空") != -1 || $(e).text().indexOf("禁用") != -1 || $(e).text().indexOf("不通过") != -1) {
                    $(btn).removeClass("btn-info");
                    $(btn).addClass("btn-danger");
                }
                btn.html($(e).text());
            }
            btn.click(function () {
                e.click();
            });
            $(".button-operation").append(btn);
        }
    });
    $("#pager_left").hide()

    if ($(_this).getGridParam("pager") != null) {
        $('#current_page').html($(_this).getGridParam("page"));
        $('#gopage_url').val($(_this).getGridParam("url"));
        $('#gopage').val($(_this).getGridParam("page"));
        var page = $('#gopage').val();
        $('#gopage').val(parseInt(page));
        $('#total_page').html($('#sp_1_pager').html());
        $('#total_records').html($(_this).getGridParam("records"));
        if ($(_this).getGridParam("records") == 0) {
            $("#sorry").attr({style: "margin-top:55px; display:block;"});
            $("#list").jqGrid('setGridHeight', 0);
        } else {
            resizeJQGrid();
        }
    } else {
        $(".pager_operation").hide();
        resizeJQGrid();
    }

    $("select.ui-pg-selbox").change(function () {
        rowNum_perpage = $(_this).val();
    });
    $("td[aria-describedby$='create_time'],td[aria-describedby$='prcs_name'],td[aria-describedby$='prcs_time']").find('.ui-icon').css({
        "display": "inline-block",
        "margin-left": "4px",
        "cursor": "pointer"
    });
    $("td[aria-describedby$='create_time'],td[aria-describedby$='prcs_name'],td[aria-describedby$='prcs_time']").find('.ui-icon').click(function () {
        var detail_block = $(_this).parent().find('.detail-block');
        if (detail_block.css('display') == "block") {
            detail_block.css('display', 'none');
            $(_this).removeClass("ui-icon-minus");
            $(_this).addClass("ui-icon-plus");
        } else {
            detail_block.css('display', 'block');
            $(_this).removeClass("ui-icon-plus");
            $(_this).addClass("ui-icon-minus");
        }
        return false;
    });
    $("td[aria-describedby$='create_time'],td[aria-describedby$='prcs_name'],td[aria-describedby$='prcs_time']").find('.text_expand_icon').click(function () {
        var detail_block = $(_this).parent().find('.detail-block');
        if (detail_block.css('display') == "block") {
            detail_block.css('display', 'none');
            $(_this).parent().find('.ui-icon').removeClass("ui-icon-minus");
            $(_this).parent().find('.ui-icon').addClass("ui-icon-plus");
        } else {
            detail_block.css('display', 'block');
            $(_this).parent().find('.ui-icon').removeClass("ui-icon-plus");
            $(_this).parent().find('.ui-icon').addClass("ui-icon-minus");
        }
        return false;
    });
    loadPager();
}
