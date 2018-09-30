$(function () {

    $("#first-menu>li>a").click(function () {
        $(this).parents("li").toggleClass("expend");
        scroll(0);
    });
    $("#first-menu").find("[data-tag='menu']").click(function () {
        var _this = $(this)
        var url = _this.data("url")
        if (url && url != null && url != undefined && url != "") {
            var title = _this.data("title")
            var id = _this.data("id")
            if (title == "迎新流程设计" || title == "学生菜单管理")
                addTab(id, title, url, true, true, "padding:0px 10px 0 0")
            else
                addTab(id, title, url, true, true)
        }
    })

    var west = $("#west_wrapper");
    var up = $(".scroll-up");
    var ele = $(".first-menu");
    var down = $(".scroll-down");
    var stop = null;

    function scroll(v) {
        var margin = parseInt(ele.css("marginTop").replace("px", ""));
        var top = margin + v;
        var height = (west.height() - ele.height());

        if (top < 0 && west.height() < ele.height()) {
            if (top < height) {
                ele.css({ marginTop: margin = height });
            } else {
                ele.css({ marginTop: margin = top });
            }
        } else {
            ele.css({ marginTop: margin = 0 });
        }

        if (margin < 0) {
            up.show();
        } else {
            up.hide();

            if (stop) {
                clearInterval(stop);
                stop = null;
            }
        }

        if (margin <= height) {
            down.hide();

            if (stop) {
                clearInterval(stop);
                stop = null;
            }
        } else {
            down.show();
        }
    }

    west.mousewheel(function (event, delta, deltaX, deltaY) {
        scroll(delta * 65);
    });

    down.mousedown(function () {
        stop = setInterval(function () {
            scroll(-10);
        }, 20);
    }).mouseup(function () {
        clearInterval(stop);
    });

    up.mousedown(function () {
        stop = setInterval(function () {
            scroll(10);
        }, 20);
    }).mouseup(function () {
        clearInterval(stop);
    });
    window.onresize = function () {
        scroll(0);
    }
    scroll(0);

})

