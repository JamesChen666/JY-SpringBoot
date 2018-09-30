function show() {
    var a = $("#upload-file").val();
    if(a !="" && a != undefined){
        $("#sc").val(a);
    }
    var fileObj = document.getElementById("upload-file").files[0]; // js 获取文件对象
    if (typeof (fileObj) == "undefined" || fileObj.size <= 0) {
        alert("请选择文件");
        return;
    }
    var formFile = new FormData();
    formFile.append("action", "UploadVMKImagePath");
    formFile.append("file", fileObj); //加入文件对象
    $.ajax({
        url: "/uploadflv/upload",
        data: formFile,
        type: "POST",
        dataType: 'json',
        cache: false,//上传文件无需缓存
        processData: false,//用于对data参数进行序列化处理 这里必须false
        contentType: false, //必须
        success: function (result) {
            if(result.entity.path !=null && result.entity.path != undefined && result.entity.path != "undefined"){
                $('#wjdz').textbox('setValue',"/"+result.entity.path);
                $("#cs").attr('href',"/"+result.entity.path);//显示
                $("#yl").attr("src","/"+result.entity.path);
            }
        },
        error:function(){
            alert("错误");
        },
        /*xhr: function() {
            var xhr = $.ajaxSettings.xhr();
            if (onprogress && xhr.upload) {
                xhr.upload.addEventListener("progress", onprogress, false);
                return xhr;
            }
        }*/
    });
}


function onprogress(evt){
    $("#jd").empty();
    var loaded = evt.loaded;     //已经上传大小情况
    var tot = evt.total;      //附件总大小
    var per = Math.floor(100*loaded/tot);  //已经上传的百分比
    var html = '<h2>当前上传进度为：'+per+'%</h2>';
    $("#jd").append(html);
}


function show1() {
    var a = $("#upload-file1").val();
    if(a !="" && a != undefined){
        $("#sc").val(a);
    }
    var fileObj = document.getElementById("upload-file1").files[0]; // js 获取文件对象
    if (typeof (fileObj) == "undefined" || fileObj.size <= 0) {
        alert("请选择文件");
        return;
    }
    var formFile = new FormData();
    formFile.append("action", "UploadVMKImagePath");
    formFile.append("file", fileObj); //加入文件对象
    $.ajax({
        url: "/uploadflv/upload",
        data: formFile,
        type: "POST",
        dataType: 'json',
        cache: false,//上传文件无需缓存
        processData: false,//用于对data参数进行序列化处理 这里必须false
        contentType: false, //必须
        success: function (result) {
            if(result.entity.path !=null && result.entity.path != undefined && result.entity.path != "undefined"){
                $('#fmtp').textbox('setValue',"/"+result.entity.path);
                $("#cs1").attr('href',"/"+result.entity.path);
                $("#yl1").attr("src","/"+result.entity.path);
            }
        },
        error:function(){
            alert("错误");
        }
    });
}