$(function () {
    // 顶部nav图片选项卡切换方法
    var img1=["tab111.png","tab112.png","tab113.png","tab114.png","tab115.png","tab116.png","tab117.png","tab118.png"];
    var img2=["tab221.png","tab222.png","tab223.png","tab224.png","tab225.png","tab226.png","tab227.png","tab228.png"];

    $(".tab_nav").on("click", "li", function() {
        var id= $(this).children("img").attr("id");
        // alert(id+'0');
        for(var i=0;i<img1.length;i++){
            //如果是选择的那个，就换22的样式，    
            if(i==id ){
                $(this).children("img").attr("src","/static/homePage/img/"+img2[id]);
            }
            //否则用11的样式
            else {
                //console.log($("nav").find("li").innerHTML)
                $($(".tab_nav").find("li")[i]).find("img").attr("src","/static/homePage/img/"+img1[i]);  //注意这个内容
            }
        }
        var index = $(this).index();
        $('.tab_nav li').removeClass('active');
        $(this).addClass('active');
        $('.tab_content').addClass('hide').eq(index).removeClass('hide');
    });
    // 嵌套选项卡方法
    $(".grxx-tabs").on("click", "div", function() {
        var index = $(this).index();
        $('.grxx-tabs div').removeClass('active2');
        $(this).addClass('active2');
        $(this).parent().parent().find('.tab_content1').addClass('hide').eq(index).removeClass('hide');
    });
    $(".qz-tabs").on("click", "div", function() {
        var index = $(this).index();
        $('.qz-tabs div').removeClass('active2');
        $(this).addClass('active2');
        $(this).parent().parent().find('.tab_content2').addClass('hide').eq(index).removeClass('hide');
        $(this).parent().parent().find('.tab_content3').addClass('hide').eq(index).removeClass('hide');
    });
    $(".qy-tabs").on("click", "div", function() {
        var index = $(this).index();
        $('.qy-tabs div').removeClass('active2');
        $(this).addClass('active2');
        $(this).parent().parent().find('.tab_content2').addClass('hide').eq(index).removeClass('hide');
    });
    $(".gq-tabs").on("click", "div", function() {
        var index = $(this).index();
        $('.gq-tabs div').removeClass('active2');
        $(this).addClass('active2');
        $(this).parent().parent().find('.tab_content2').addClass('hide').eq(index).removeClass('hide');
    });
    $(".zz-tabs").on("click", "div", function() {
        var index = $(this).index();
        $('.zz-tabs div').removeClass('active2');
        $(this).addClass('active2');
        $(this).parent().parent().find('.tab_content2').addClass('hide').eq(index).removeClass('hide');
    });
    $(".zzz-tabs").on("click", "div", function() {
        var index = $(this).index();
        $('.zzz-tabs div').removeClass('active2');
        $(this).addClass('active2');
        $(this).parent().parent().find('.tab_content2').addClass('hide').eq(index).removeClass('hide');
    });
    // 回复信息删除方法
    /*$('.del').click(
        function () {
            var none = '<p class="text-c">' +
                '                    <img src="/static/homePage/img/center-index_78.png" alt=""><br>' +
                '                    <span class="text-c" style="font-size: 15px">暂无数据</span>' +
                '                </p>'
            $(this).parent().parent().remove();
            $("#wyly").each(function(){
                var le = $("#wyly").find(".grxx-huifu").length;
                if(le == 0){
                    $("#wyly").append(none);
                }
            });
        });*/
});
