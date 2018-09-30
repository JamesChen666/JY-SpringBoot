$(function () {
    $(".person_info").find("a,img").click(function () {
        addTab("person", "个人信息", "/Manager/Person/Info", true, true)
    })
    $(window).resize(function () {
        //$("#west,#east").height($(window).height() - 70); //减去的70为30的header高度和40的footer高度 
        //$("#east").width($(window).width() - $("#west").width())
        //$("#west_wrapper").height($(window).height() - 290)
        var height = $(window).height() - 70
        $("#west").height(height)
        $("#west_wrapper").height(height - 190)
    }).resize();
    $('#tabs_container').tabs({
        tabsLeftScroll: 'tabs_left_scroll',
        tabsRightScroll: 'tabs_right_scroll',
        panelsContainer: 'main',
        secondTabsContainer: 'funcbar_left'
    });
    
    $("#exit").click(function () {
        if (confirm("您确定要退出迎新系统吗？")) {
            window.location.href = $(this).attr("rel")
        }
    })
})
addTab = function (id, title, url, closable, selected, style) {
    if (!id) return;
    closable = (typeof (closable) == 'undefined') ? true : closable;
    selected = (typeof (selected) == 'undefined') ? true : selected;


    var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);

    var fHeight = $('#header').is(':visible') ? $('#header').outerHeight() : 0;

    var cHeight = wHeight - fHeight - $('#footer').outerHeight() - $('#logo').outerHeight();

    var wWidth = (window.document.documentElement.clientWidth || window.document.body.clientWidth || window.innerHeight);

    //ABC.loadPanel.showWait("正在加载" + title + ";请稍后") 

    $('#tabs_container').tabs('add', {
        id: id,
        title: title,
        closable: closable,
        iconCls: 'iconCls',
        style: style,
        selected: selected,
        content: '<iframe id="tabs_' + id + '_iframe" name="tabs_' + id + '_iframe" allowTransparency= "true" src="' + url + '" onload="IframeLoaded(\'' + id + '\');" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" style="height:' + (cHeight - 50) + 'px"></iframe>'
    });
}

function closeTab() {
    var div = $("#tabs_container").find("div.selected")
    var id = div.attr("id").replace("tabs_", "")
    $('#tabs_container').tabs('close', id)
}

function IframeLoaded(id) {
    var iframe = window.frames['tabs_' + id + '_iframe'];
    if (iframe && $('tabs_link_' + id) && $('tabs_link_' + id).innerText == '') {
        $('tabs_link_' + id).innerText = !iframe.document.title ? "无标题" : iframe.document.title; //"无标题"
    }
    jQuery(function ($) {
        $("#loading_" + id).hide();
        $("#Sloading_" + id).hide();
    });
}