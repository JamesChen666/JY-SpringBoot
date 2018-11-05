$(function(){
    $(".x-com-re .c95").click(
        function () {
            $(this).parent().find('.sushe-mtkbg').show();

        }
    );
    $(".sushe-mtk>div").click(
        function () {

            $(this).parent().parent().parent().find('input').attr('value',$(this).html());
            $(this).parent().parent().hide();
        }
    )
    // 模态框全局点击隐藏
    $('.sushe-mtkbg').click(function () {
        $(this).hide().fadeOut(500);
    });
    // 迎新下拉二级菜单
    $(".main-xiala").click(function () {
        if($(this).find("ul").is(':hidden')){
            $(this).find("ul").slideDown(400);
        }else {
            $(this).find("ul").slideUp(400);
        }
    });
});
