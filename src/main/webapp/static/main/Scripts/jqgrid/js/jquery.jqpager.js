var td_lang = {};
td_lang.global = {
    page_up: "上一页",
    page_down: "下一页",
    songti: "宋体",
    delete_1: "删除",
    select: "选择",
    total: "合计",
    yes: "是",
    no: "否",
    reply: "回复",
    error: "错误：",
    close: "关闭",
    regist: "注册",
    first_page: "首页",
    before_page: "上页",
    next_page: "下页",
    last_page: "末页",
    refresh_1: "刷新",
    right: "正确",
    print: "打印",
    print_preview: "打印预览",
    clear: "清空",
    details: "详情",
    loading: "加载中..."
};

function loadPager() {
    var selectObj = $(".ui-pg-selbox");
    if (selectObj && selectObj.length == 1) {
        var buttonHtml = selectObj.val() + "条" + '<span class="caret"></span>';
        $('#pager-selbox').html(buttonHtml);
        $(".pager-selbox-menu").html('');
        selectObj.find("option").each(function (i) {
            if ($(this).text() == selectObj.val()) {
                return true;
            }
            var liHtml = '<li><a href="#">';
            liHtml += $(this).text() + "条";
            liHtml += '</a></li>';
            $(".pager-selbox-menu").append(liHtml);
        });
    }
    var currentPage = Math.min(parseInt($("#list").getGridParam("page")), parseInt($("#total_page").html()));
    var options = {
        currentPage: currentPage == 0 ? 1 : currentPage,
        totalPages: $("#total_page").html() == 0 ? 1 : $("#total_page").html(),
        onPageClicked: function (e, originalEvent, type, page) {
            $('.ui-pg-input').val(page);
            var event_e = $.Event("keypress");
            event_e.keyCode = 13;
            $('.ui-pg-input').trigger(event_e);
        }
    };
    $('#work-pager-block').bootstrapPaginator(options);
}