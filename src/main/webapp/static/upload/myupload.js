$(function(){
    initWebuploader();
    initimageuploader();
    initfileuploader();
})
function initWebuploader() {
    var uploader = WebUploader.create({
        // swf文件路径
        swf: 'js/Uploader.swf',
        // 文件接收服务端。
        server: '/uploadflv/upload',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: {
            id:'#videopicker',
            innerHTML:'选择视频',
            multiple:true
        },
        accept: {
            title: 'video',
            extensions: 'avi,wma,rmvb,rm,flash,mp4,mid,3gp',
            mimeTypes: 'video/*'
        },
        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        resize: false
    });
    uploader.on('fileQueued', function (file) {
        $("#video").textbox('setValue',file.name);
    })
    uploader.on("uploadSuccess", function (file) {
        top.$.messager.alert("系统提示", "上传操作成功！", "info");
        $("#video").textbox('setValue',file.name);
        $("#videopicker").remove();
        $("#videoBtn").remove();
    });
    uploader.on("uploadFinished", function () {
        uploader.destroy();
        initWebuploader();
    });
    $("#videoBtn").on('click',function () {
        var files = uploader.getFiles();
        for (var i = 0; i < files.length; i++) {
            if (files[i].name!=$("#video").textbox('getValue')) {
                uploader.removeFile(files[i],true)
            }
        }
        uploader.upload();
    })
}
function initimageuploader() {
    var uploader = WebUploader.create({
        // swf文件路径
        swf: 'js/Uploader.swf',
        // 文件接收服务端。
        server: '/uploadflv/upload',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: {
            id:'#imagepicker',
            innerHTML:'选择图片',
            multiple:true
        },
        accept: {
            title: 'image',
            extensions: 'bmp,jpg,tiff,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw',
            mimeTypes: 'image/*'
        },
        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        resize: false
    });
    uploader.on('fileQueued', function (file) {
        var $li = $(
            '<div id="img" class="file-item thumbnail">' +
            '<img>' +
            '</div>'
            ),
            $img = $li.find('img');
        // $list为容器jQuery实例
        $("#img").remove();
        $("#imagepicker").append($li);
        $("#image").textbox('setValue',file.name);
        // 创建缩略图
        // 如果为非图片文件，可以不用调用此方法。
        // thumbnailWidth x thumbnailHeight 为 100 x 100
        uploader.makeThumb(file, function (error, src) {
            if (error) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }

            $img.attr('src', src);
        }, 300, 100);

    });
    uploader.on("uploadSuccess", function (file) {
        top.$.messager.alert("系统提示", "上传操作成功！", "info");
        $("#image").textbox('setValue',file.name);
        $("#imagepicker").remove();
        $("#imageBtn").remove();
    });
    uploader.on("uploadFinished", function () {
        uploader.destroy();
        initimageuploader();
    });
    $("#imageBtn").on('click',function () {
        var files = uploader.getFiles();
        for (var i = 0; i < files.length; i++) {
            if (files[i].name!=$("#image").textbox('getValue')) {
                uploader.removeFile(files[i],true)
            }
        }
        uploader.upload();
    })
}
function initfileuploader() {
    var uploader = WebUploader.create({
        // swf文件路径
        swf: 'js/Uploader.swf',
        // 文件接收服务端。
        server: '/uploadflv/upload',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: {
            id:'#filepicker',
            innerHTML:'选择文件',
            multiple:true
        },
        accept: {
            title: 'file',
            extensions: 'doc,docx,xls,xlsx,pdf,zip,rar',
            mimeTypes: '*/*'
        },
        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        resize: false
    });
    uploader.on('fileQueued', function (file) {
        $("#file").textbox('setValue',file.name);
    });
    uploader.on("uploadSuccess", function (file) {
        top.$.messager.alert("系统提示", "上传操作成功！", "info");
        $("#file").textbox('setValue',file.name);
        $("#filepicker").remove();
        $("#fileBtn").remove();
    });
    uploader.on("uploadFinished", function () {
        uploader.destroy();
        initfileuploader();
    });
    $("#fileBtn").on('click',function () {
        var files = uploader.getFiles();
        for (var i = 0; i < files.length; i++) {
            if (files[i].name!=$("#file").textbox('getValue')) {
                uploader.removeFile(files[i],true)
            }
        }
        uploader.upload();
    })
}
