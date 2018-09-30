

//字符串转换为时间
function stringToTime(val) {
    return new Date(val.replace(/-/g, "/"));
}

//时间格化
function formatShortDate(d) {
    if (d == null || d == "") return "";
    var date = new Date(parseInt(d.replace("/Date(", "").replace(")/", ""), 10));
    var month = date.getMonth() + 1;
    month = month >= 10 ? month : "0" + month;
    var currentDate = date.getDate() >= 10 ? date.getDate() : "0" + date.getDate();
    return date.getFullYear() + "-" + month + "-" + currentDate;
}

function formatDate(d) {
    if (d == null || d == "") return "";
    var date = new Date(parseInt(d.replace("/Date(", "").replace(")/", ""), 10));
    var month = date.getMonth() + 1;
    month = month >= 10 ? month : "0" + month;
    var currentDate = date.getDate() >= 10 ? date.getDate() : "0" + date.getDate();
    var hour = date.getHours() >= 10 ? date.getHours() : "0" + date.getHours();
    var minute = date.getMinutes() >= 10 ? date.getMinutes() : "0" + date.getMinutes();
    return date.getFullYear() + "-" + month + "-" + currentDate + " " + hour + ":" + minute;
};
function formatHour(d) {
    if (d == null || d == "") return "";
    var hour = d.Hours >= 10 ? d.Hours : "0" + d.Hours;
    var minute = d.Minutes >= 10 ? d.Minutes : "0" + d.Minutes
    return hour + ":" + minute
}

function formatWeekDay(d) {
    if (d == null || d == "") return "";
    switch (d) {
        case "1": return "星期一";
        case "2": return "星期二";
        case "3": return "星期三";
        case "4": return "星期四";
        case "5": return "星期五";
        case "6": return "星期六";
        case "7": return "星期日";
        default: return "";
    }
}

var School = {
    divdialog: top.window.dialogWindow,
    formatBIT: function (val) {
        if (val)
            return "<span style='color:green'>√</span>";
        return "<span style='color:red'>×</span>";
    },
    formatSex: function (val) {
        if (val){
            return "<span >男</span>";
        }
        return "<span>女</span>";
    },
    formatState: function (val) {
        if (val=="0"){
            return "<span style='color:deepskyblue' >待审核</span>";
        }else if (val=="1"){
            return "<span style='color:green' >已审核</span>";
        }
        return "<span style='color:red' >审核拒绝</span>";
    },
    formatStudy: function (val, text) {
        if (val == "01" || val == "05")
            return "<span style='color:green'>" + text + "</span>";
        return "<span style='color:red'>" + text + "</span>";
    },
    //等待面板
    waitPanel: {
        closeWait: function () {
            top.$(".datagrid-mask,.datagrid-mask-msg").hide();
        },
        showWait: function (msg, zindex) {
            var tempZindex, tempZindexM = "";

            if (zindex != undefined && !zindex == "") {
                tempZindex = "z-index:" + zindex;
                tempZindexM = "z-index:" + zindex + 1;
            }
            //var tempHeight = top.$("body").height();
            //if (tempHeight == 0)
            var tempHeight = top.window.innerHeight;
            top.$("<div class=\"datagrid-mask\" style='" + tempZindex + "'></div>").css({ display: "block", width: "100%", height: tempHeight }).appendTo("body");
            top.$("<div class=\"datagrid-mask-msg\"  style='" + tempZindexM + "'></div>").html((msg == undefined || msg == "") ? "正在处理，请稍候。。。" : msg).appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: (tempHeight - 45) / 2 });
        }
    },
    //提交FORM表单
    submitForm: function (val, isreload, formId, callback, submit) {

        var _this = $("#" + val);
        _this.attr('disable', 'disable');
        var form = formId == undefined ? $("form") : $("#" + formId);

        var data = form.find("input,textarea,select").formSerialize();
        if (submit) {
            data = submit(data);
        }
        //表单验证 
        if (form.form('validate')) {
            School.waitPanel.showWait('处理中...', 1000);
            $.post(form.attr("action"), data, function (result) {
                if (result.flag) {
                    if (!result.msg || result.msg.length == 0) result.msg = "保存成功"
                    top.$.messager.alert("提示", result.msg);
                    if (isreload)
                        location.reload();
                }
                else {
                    top.$.messager.alert("提示", (result.msg == "" ? "保存失败,请重试" : result.msg), "error");
                }

                _this.removeAttr('disable');
                School.waitPanel.closeWait();
                if (callback) callback(result)
            })
        }
    },
    showDiv: function (obj) {
        //弹出模式窗口 
        obj = $.extend({
            width: 700,
            height: 300,
            modal: true,
            div: School.divdialog,
            onClose: function () {
                
                top.$(obj.div).dialog("destroy");
            },
            buttons: [
                  {
                      iconCls: 'icon-cancel', text: "关闭", handler: function () {
                          top.$(obj.div).dialog("close");
                      }
                  }
            ]
        }, obj);
        return top.$(obj.div).dialog(obj);
    },
    showDivHasSave: function (obj) {
        //弹出模式窗口
        //var divDialog = document.createElement("div");
        var showPanel;
        if (!obj.fit){
            showPanel = top.$(School.divdialog);
        } else
        {
            showPanel = $(School.divdialog);
        }

        obj = $.extend({
            width: 700,
            height: 300,
            onClose: function () {
                showPanel.dialog("destroy");
            },
            modal: true,
            buttons: [
                {
                    iconCls: 'icon-save', text: (obj.ButtonText ? obj.ButtonText : "保存"), handler: function () {
                        var item = obj;
                        if (item.validateing) {
                            if (item.validateing(item) == false)
                                return;
                        }
                        if (item.save) {
                            if (showPanel.form('validate')) {
                                School.waitPanel.showWait("", 9999);
                                var args = showPanel.find("input,textarea,select").formJsonSerialize();
                                if (item.submit) {
                                    args = item.submit(args);
                                }

                                $.post(item.save, args, function (result) {
                                    if (result.flag) {
                                        if (item.callback)
                                            item.callback();
                                        $("#list").trigger("reloadGrid");
                                        School.waitPanel.closeWait()
                                        showPanel.dialog("close");
                                    }
                                    else {
                                        School.waitPanel.closeWait()
                                        top.$.messager.alert("系统提示", result.msg == null ? "操作失败，对此抱歉！" : result.msg, "error");
                                    }
                                });

                            }
                        } else {
                            showPanel.dialog("close");
                            School.waitPanel.closeWait()
                            if (!item.fit)
                                showPanel.dialog("destroy")
                        }
                    }
                },
                  {
                      iconCls: 'icon-cancel', text: "关闭", handler: function () {
                          if (obj.onCancel)
                              obj.onCancel()
                          showPanel.dialog("close");
                      }
                  }
            ]
        }, obj);
        return showPanel.dialog(obj);
    },
    showDivHasDelete: function (obj) {
        //弹出模式窗口
        //var divDialog = document.createElement("div");
        var showPanel = top.$(School.divdialog);
        obj = $.extend({
            width: 700,
            height: 300,
            onClose: function () {
                top.$(obj.div).dialog("destroy");
            },
            modal: true,
            buttons: [
                {
                    iconCls: 'icon-remove', text: "删除", handler: function () {
                        var item = obj;
                        var aa = top.$.messager.confirm("操作提示", "警告：确定要删除记录，删除后无法恢复！", function (data) {
                            if (data) {
                                School.waitPanel.showWait("", 9999);
                                $.post(item.del, function (result) {
                                    if (result.flag) {
                                        if (item.callback)
                                            item.callback();
                                        School.waitPanel.closeWait()
                                        showPanel.dialog("close");
                                        showPanel.dialog("destroy")
                                    }
                                    else {
                                        School.waitPanel.closeWait()
                                        top.$.messager.alert("系统提示", result.msg == null ? "操作失败，对此抱歉！" : result.msg, "error");
                                    }
                                });
                            }
                            else {
                                showPanel.dialog("close");
                            }
                        });
                        showPanel.dialog("close");
                    }
                },
                {
                    iconCls: 'icon-save', text: (obj.ButtonText ? obj.ButtonText : "保存"), handler: function () {
                        var item = obj;
                        if (item.validateing) {
                            if (item.validateing(item) == false)
                                return;
                        }
                        if (item.save) {
                            if (showPanel.form('validate')) {
                                School.waitPanel.showWait("", 9999);
                                var args = showPanel.find("input,textarea,select").formJsonSerialize();

                                if (item.submit) {
                                    args = item.submit(args);
                                }

                                $.post(item.save, args, function (result) {
                                    if (result.flag) {
                                        if (item.callback)
                                            item.callback();
                                        School.waitPanel.closeWait()
                                        showPanel.dialog("close");
                                        showPanel.dialog("destroy")
                                    }
                                    else {
                                        School.waitPanel.closeWait()
                                        top.$.messager.alert("系统提示", result.msg == null ? "操作失败，对此抱歉！" : result.msg, "error");
                                    }
                                });

                            }
                        } else {
                            showPanel.dialog("close");
                            School.waitPanel.closeWait()
                            if (!item.fit)
                                showPanel.dialog("destroy")
                        }
                    }
                },
                {
                    iconCls: 'icon-cancel', text: "关闭", handler: function () {
                        if (obj.onCancel)
                            obj.onCancel()
                        showPanel.dialog("close");
                    }
                }
            ]
        }, obj);
        return showPanel.dialog(obj);
    },
    showPrintDiv: function (obj) {
        //弹出模式窗口 
        var showPanel = top.$(School.divdialog);
        obj = $.extend({
            width: 700,
            height: 300,
            onClose: function () {
                top.$(obj.div).dialog("destroy");
            },
            modal: true,
            buttons: [
                {
                    iconCls: 'icon-print', text: "打印", handler: function () {
                        School.waitPanel.showWait("正在生成打印。。。。", 9999);
                        var html = $(showPanel).html();
                        obj.args["html"] = encodeURIComponent(html);
                        $.post(obj.save, obj.args, function (fileName) {
                            School.waitPanel.closeWait()
                            window.open(obj.path + fileName)
                        })
                    }
                },
                  {
                      iconCls: 'icon-cancel', text: "关闭", handler: function () {
                          if (obj.onCancel)
                              obj.onCancel()
                          showPanel.dialog("close");
                      }
                  }
            ]
        }, obj);
        return showPanel.dialog(obj);
    },
    showManager: function (obj) {
        var showPanel = top.$(School.divdialog);
        obj = $.extend({
            width: 900,
            height: 580,
            iconCls: "icon-setting",
            onClose: function () {
                showPanel.dialog("destroy");
            },
            modal: true,
            buttons: [
                {
                    iconCls: 'icon-save', text: (obj.ButtonText ? obj.ButtonText : "保存"), handler: function () {
                        var item = obj;
                        if (item.validateing) {
                            if (item.validateing(item) == false)
                                return;
                        }
                        if (item.save) {
                            if (showPanel.form('validate')) {
                                School.waitPanel.showWait("", 9999);
                                var args = showPanel.find("input,textarea,select").formJsonSerialize();
                                args["objectCode"] = obj.objectCode

                                var jobNumber = []
                                var list = showPanel.find("#selectR").find("option")
                                for (var i = 0; i < list.length; i++) {
                                    jobNumber.push($(list[i]).attr("value"))
                                }
                                args["jobNumber"] = jobNumber
                                if (item.submit) {
                                    args = item.submit(args);
                                }
                                $.post(item.save, args, function (result) {
                                    if (result.flag) {
                                        if (item.callback)
                                            item.callback();
                                        $("#list").trigger("reloadGrid");
                                        School.waitPanel.closeWait()
                                        showPanel.dialog("close");
                                    }
                                    else {
                                        School.waitPanel.closeWait()
                                        top.$.messager.alert("系统提示", result.msg == null ? "操作失败，对此抱歉！" : result.msg, "error");
                                    }
                                });

                            }
                        } else {
                            showPanel.dialog("close");
                            School.waitPanel.closeWait()
                            if (!item.fit)
                                showPanel.dialog("destroy")
                        }
                    }
                },
                  {
                      iconCls: 'icon-cancel', text: "关闭", handler: function () {
                          if (obj.onCancel)
                              obj.onCancel()
                          showPanel.dialog("close");
                      }
                  }
            ]
        }, obj);
        obj.href = "/Manager/Tools/ChooseManager?objectCode=" + obj.objectCode + "&moduleId=" + obj.moduleId
        return showPanel.dialog(obj);
    },
    showStudent: function (obj) {
        //弹出模式窗口  
        var url = '/Manager/Tools/ChooseStudent'
        if (navigator.userAgent.indexOf("Chrome") > 0) {
            window.returnCallBackValue354865588 = obj.callback;
            var winOption = "top=50,left=50,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=0";
            window.open(url, window, winOption);
        }
        else {
            var tempReturnValue = window.showModalDialog(url, window, 'edge: Raised; center: Yes; help: Yes;scroll:no; resizable: no; status: no;resizable:yes');
            fn.call(window, tempReturnValue);
        }
    },
    showClass: function (obj) {
        //弹出模式窗口  
        var url = '/Manager/Tools/ChooseClass'
        if (navigator.userAgent.indexOf("Chrome") > 0) {
            window.returnCallBackValue354865588 = obj.callback;
            var winOption = "top=50,left=50,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=0";
            window.open(url, window, winOption);
        }
        else {
            var tempReturnValue = window.showModalDialog(url, window, 'edge: Raised; center: Yes; help: Yes;scroll:no; resizable: no; status: no;resizable:yes');
            fn.call(window, tempReturnValue);
        }
    },
    showTeacher: function (obj) {
        //弹出模式窗口  
        var url = '/Manager/Tools/ChooseTeacher'
        if (navigator.userAgent.indexOf("Chrome") > 0) {
            window.returnCallBackValue354865588 = obj.callback;
            var winOption = "top=50,left=50,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=0";
            window.open(url, window, winOption);
        }
        else {
            var tempReturnValue = window.showModalDialog(url, window, 'edge: Raised; center: Yes; help: Yes;scroll:no; resizable: no; status: no;resizable:yes');
            fn.call(window, tempReturnValue);
        }
    },
    showQuestion: function (obj) {
        //弹出模式窗口  
        var url = '/Manager/Tools/ChooseQuestion'
        if (navigator.userAgent.indexOf("Chrome") > 0) {
            window.returnCallBackValue354865588 = obj.callback;
            var winOption = "top=50,left=50,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=0";
            window.open(url, window, winOption);
        }
        else {
            var tempReturnValue = window.showModalDialog(url, window, 'edge: Raised; center: Yes; help: Yes;scroll:no; resizable: no; status: no;resizable:yes');
            fn.call(window, tempReturnValue);
        }
    },
    Cookie: {
        /************************************************************************
        |    函数名称： setCookie                                                |
        |    函数功能： 设置cookie函数                                           |
        |    入口参数： name：cookie名称；value：cookie值                        | 
        *************************************************************************/
        setCookie: function (name, value) {
            var argv = this.setCookie.arguments;
            var argc = this.setCookie.arguments.length;
            var expires = (argc > 2) ? argv[2] : null;
            if (expires != null) {
                var LargeExpDate = new Date();
                LargeExpDate.setTime(LargeExpDate.getTime() + (expires * 1000 * 3600 * 24));
            }
            document.cookie = name + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + LargeExpDate.toGMTString())) + ";path=/";
        },
        /************************************************************************
        |    函数名称： getCookie                                                |
        |    函数功能： 读取cookie函数                                           |
        |    入口参数： Name：cookie名称                                         |
        *************************************************************************/
        getCookie: function (Name) {
            var search = Name + "="
            if (document.cookie.length > 0) {
                offset = document.cookie.indexOf(search)
                if (offset != -1) {
                    offset += search.length
                    end = document.cookie.indexOf(";", offset)
                    if (end == -1) end = document.cookie.length
                    return unescape(document.cookie.substring(offset, end))
                }
                else return ""
            }

        }
    },
    //审核通过
    audit: function (selectdgId, pageTitle, idField, postURL, state) {
        var rows = $("#" + selectdgId).datagrid('getSelections');
        if (rows.length <= 0) {
            top.$.messager.alert('系统提示', '至少选择一条' + pageTitle + '记录!', 'warning');
            return false;
        }
        top.$.messager.confirm('系统提示', "确定要审核通过选择的记录吗？", function (flag) {
            if (!flag)
                return false;
            else {
                var array = {};
                $.each(rows, function (i) {
                    array[i] = rows[i][idField];
                })
                $.post(postURL, { ids: array, state: state }, function (data) {
                    $("#" + selectdgId).datagrid('clearSelections');
                    if (data.flag) {
                        top.$.messager.alert('系统提示', "已成功处理您的请求。", 'warning');
                        $("#" + selectdgId).datagrid('reload')
                    }
                    else
                        top.$.messager.alert('系统提示', data.msg == "" || null ? "操作失败，对此抱歉！" : data.msg, 'error');
                });
            }
        })
        return false;
    },
    //审核不通过
    unaudit: function (selectdgId, pageTitle, idField, postURL, state) {

        var rows = $("#" + selectdgId).jqGrid("getGridParam", "selarrrow");/*.datagrid('getSelections');/*/
        if (rows.length <= 0) {
            top.$.messager.alert('系统提示', '至少选择一条' + pageTitle + '记录!', 'warning');
            return false;
        }
        var obj = {};
        //$.each(rows, function (i) {
        //    array[i] = rows[i][idField];
        //})
        for (var i = 0; i < rows.length; i += 1) {
            obj[i] = rows[i];
        }
        top.$(School.divdialog).dialog({
            width: 450, height: 220, modal: true,
            iconCls: "icon-edit",
            title: "不通过原因",
            fit: false,
            maximized: false,
            href: "",
            content: function () {
                var tbl = '<table class="tb1" id="btgTable" >';
                tbl += '<tr><td><textarea class="easyui-validatebox" id="btgyy" style="width:98% !important;height:100px !important;" required="True"></textarea></td></tr>';
                tbl += '</table><center style="color:red;"></center>';
                return tbl;
            },
            buttons: [{
                iconCls: 'icon-ok', text: "确定", handler: function () {
                    if (top.$('#btgTable').form('validate')) {
                        $.post(postURL, { ids: obj, flag: state, remark: top.$("#btgyy").val() }, function (data) {
                            if (data.flag) {
                                top.$(School.divdialog).dialog("close");
                                top.$.messager.alert('系统提示', data.msg == "" || null ? "已成功处理您的请求。" : data.msg, 'warning');
                                $("#" + selectdgId).trigger("reloadGrid");
                            } else {
                                top.$.messager.alert('系统提示', data.msg == "" || null ? "操作失败，对此抱歉！" : data.msg, 'error');
                            }
                        });
                        return false;
                    }
                }
            },
            { iconCls: 'icon-cancel', text: "取消", handler: function () { top.$(School.divdialog).dialog("close"); } }]
        });
        return false;
    }
}
function myReturnValue(value) {
    if (navigator.userAgent.indexOf("Chrome") > 0) {
        window.opener.returnCallBackValue354865588.call(window.opener, value);
    }
    else {
        window.returnValue = value;
    }
    School.waitPanel.closeWait()
}
var ImportPanel = {
    importPanel: function (item, upLoadUrl, upLoadText, innerHtml) {
        item = $.extend({
            content: function () {
                var tbl = '<div data-options="region:"center",border:false">';
                tbl += '<form id="fileform" method="post" enctype="multipart/form-data">';;
                tbl += '<table class="tb1" id="tabBase">';
                tbl += '<tr>';
                tbl += '<td class="width80 right">模版下载：</td>';
                tbl += '<td><a href="/Resource/' + upLoadUrl + '">' + upLoadText + '</a></td>';
                tbl += '</tr>';
                if (innerHtml)
                    tbl += innerHtml;
                tbl += '<tr>';
                tbl += '<td>基础数据文件:</td>';
                tbl += '<td style="padding-top:7px;">';
                tbl += '<input id="fileInput" name="fileInput" type="text" /></td>';
                tbl += '</tr>';
                tbl += '<tr>';
                tbl += '<td colspan="2"><span id="result"></span></td>'
                tbl += '</tr>';
                tbl += '</table>';
                tbl += '</form>';
                tbl += '</div>';
                return tbl;
            },
            fileExt: "*.xls;*.xlsx",
            fileDesc: "*.xls;*.xlsx",
            iconCls: "icon-import",
            btn1Text: "导入",
        }, item);
        top.$(School.divdialog).dialog($.extend({
            modal: true,
            width: item.width,
            height: item.height,
            onClose: function () {
                top.$(School.divdialog).dialog("destroy");
            },
            href: "",
            title: item.title,
            content: item.content,
            onOpen: function () {
                // 初始化上传
                top.$('#fileInput').filebox({
                    required: true
                })

                top.$('#fileform').form({
                    url: item.save,
                    success: function (data) {
                        if (!data || data == "" || data == null) {
                            showInfo("未知错误", false);
                            School.waitPanel.closeWait();
                            return;
                        }
                        var msg = "";
                        var temp = eval("(" + data + ")");
                        if (temp.flag) {
                            $("#list").trigger("reloadGrid");
                            ImportPanel.showInfo(temp.msg, true); //showInfo方法设置上传结果       
                        }
                        else {
                            if (temp.data == null || temp.data.total == 0)
                                ImportPanel.showInfo(temp.msg, false);
                            else {
                                $.each(temp.data.list, function (i, item) {
                                    msg += item;
                                })
                                ImportPanel.showInfo(msg, false);
                            }
                        }
                        School.waitPanel.closeWait();
                    }
                });
            },
            buttons: [
                {
                    iconCls: 'icon-import', text: item.btn1Text, handler: function () {
                        if (top.$('#fileform').form("validate")) {
                            School.waitPanel.showWait("导入中,请稍后......", 99999);
                            top.$('#fileform').submit();
                        }

                    }
                },
                {
                    iconCls: 'icon-cancel', text: "关闭", handler: function () {
                        top.$(School.divdialog).dialog("close");
                    }
                }]
        }, item));
        return false;
    },
    picturePanel: function (item, upLoadUrl, upLoadText, innerHtml) {
        item = $.extend({
            content: function () {
                var tbl = '<div data-options="region:"center",border:false">';
                tbl += '<form id="fileform" method="post" enctype="multipart/form-data">';;
                tbl += '<table class="tb1" id="tabBase">'; 
                if (innerHtml)
                    tbl += innerHtml;
                tbl += '<tr>';
                tbl += '<td class="width100 right b">选择压缩文件:</td>';
                tbl += '<td style="padding-top:7px;">';
                tbl += '<input id="fileInput" name="fileInput" type="text" /></td>';
                tbl += '</tr>';
                tbl += '<tr>';
                tbl += '<td colspan="2"><span id="result"></span></td>'
                tbl += '</tr>';
                tbl += '</table>';
                tbl += '</form>';
                tbl += '</div>';
                return tbl;
            },
            fileExt: "*.zip",
            fileDesc: "*.zip",
            iconCls: "icon-import",
            btn1Text: "导入"
        }, item);
        top.$(School.divdialog).dialog($.extend({
            modal: true,
            width: item.width,
            height: item.height,
            onClose: function () {
                top.$(School.divdialog).dialog("destroy");
            },
            href: "",
            title: item.title,
            content: item.content,
            onOpen: function () {
                // 初始化上传
                top.$('#fileInput').filebox({
                    required: true
                })

                top.$('#fileform').form({
                    url: item.save,
                    success: function (data) {
                        if (!data || data == "" || data == null) {
                            showInfo("未知错误", false);
                            School.waitPanel.closeWait();
                            return;
                        }
                        var msg = "";
                        var temp = eval("(" + data + ")");
                        if (temp.flag) {
                            $("#list").trigger("reloadGrid");
                            ImportPanel.showInfo(temp.msg, true); //showInfo方法设置上传结果       
                        }
                        else {
                            if (temp.data == null || temp.data.total == 0)
                                ImportPanel.showInfo(temp.msg, false);
                            else {
                                $.each(temp.data.list, function (i, item) {
                                    msg += item;
                                })
                                ImportPanel.showInfo(msg, false);
                            }
                        }
                        School.waitPanel.closeWait();
                    }
                });
            },
            buttons: [
                {
                    iconCls: 'icon-import', text: item.btn1Text, handler: function () {
                        if (top.$('#fileform').form("validate")) {
                            School.waitPanel.showWait("导入中,请稍后......", 99999);
                            top.$('#fileform').submit();
                        }

                    }
                },
                {
                    iconCls: 'icon-cancel', text: "关闭", handler: function () {
                        top.$(School.divdialog).dialog("close");
                    }
                }]
        }, item));
        return false;
    },
    fun: function (file, data, response) {
        if (!data || data == "" || data == null) {
            ImportPanel.showInfo("未知错误", false);
            School.waitPanel.closeWait();
            return;
        }
        var msg = "";
        var temp = eval("(" + data + ")");
        if (temp.flag) {
            ImportPanel.showInfo(temp.msg, true); //showInfo方法设置上传结果     
        }
        else {
            if (temp.data == null || temp.data.total == 0)
                ImportPanel.showInfo(temp.msg, false);
            else {
                $.each(temp.data.list, function (i, item) {
                    msg += item;
                })
                ImportPanel.showInfo(msg, false);
            }
        }
        School.waitPanel.closeWait();
    },
    //显示提示信息，textstyle2为绿色，即正确信息；textstyl1为红色，即错误信息
    showInfo: function (msg, type) {
        var msgClass = type == true ? "textstyle2" : "textstyle1";
        top.$("#result").removeClass();
        top.$("#result").addClass(msgClass);
        top.$("#result").html(msg);
    }
}
String.format = function () {
    if (arguments.length == 0)
        return null;
    var str = arguments[0];
    for (var i = 1; i < arguments.length; i++) {
        var re = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
        str = str.replace(re, arguments[i]);
    }
    return str;
};