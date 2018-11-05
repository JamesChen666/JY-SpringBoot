$(function () {
    var url = $("#from").attr("action");
    $("#from").form({
        onLoadSuccess:function (data) {
             if (typeof window["initFormCallback"]==="function"){
                 initFormCallback(data)
             }
        }
    })
    $("#from").form('load', url );
})
