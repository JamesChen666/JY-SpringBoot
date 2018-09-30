var _PANEL = top.window.dialogbox;
$.fn.formSerialize = function () {
    var o = {};
    var a = $(this).serializeArray();
    $.each(a, function (i) {
        o[a[i].name] = a[i].value || '';
    });
    return o;
};

$.fn.ex_grid = function (options) {
    var _this = $(this);
    var buttons = [];
    if ($.Uri && $.Uri.List) {
        var buttonList = eval('(' + $.Uri.List + ')')
        $.each(buttonList, function (index, item) {
            for (var i = 0; i < options.buttons.length; i++) {
                var btnCode = ""
                if (options.buttons[i].btnCode) {
                    btnCode = options.buttons[i].btnCode
                    if (btnCode == item.ButtonCode) {
                        var btn = options.buttons[i];
                        btn.btnName = item.FullName
                        buttons.push(btn);
                    }
                } else {
                    buttons.push(options.buttons[i]);
                }
            }
        });
    }
    else {
        buttons = options.buttons;
    }
    options.buttons = buttons;
    var sizeArr = getGridTableSize();
    _this.jqGrid(options = $.extend({
        mtype: "POST",
        datatype: "json",
        toolbar: "#search",
        rownumbers: true,
        viewrecords: true,
        rowNum: 20,
        hoverrows: true,
        rowList: [20, 50, 100, 500, 1000],
        autowidth: true,
        shrinkToFit: false,
        sortable: true,
        sortname: "" + options.sortName + "",
        sortorder: "asc",
        singleSeach: true,
        columnSeacrh: false,
        multiselect: true,
        navigateOption: null,
        idField: "Id",
        loadComplete: function () {
            jqLoadComplete(this);
            if (options.callback) {
                options.callback()
            }
        },
        jsonReader: {
            root: "rows",
            page: "page",
            total: "total",
            records: "records",
            repeatitems: false,
            id: options.idField
        }
    }, options));
    if (options.multiSearch) {
        _this.searchGrid({multipleSearch: true, showQuery: false});
    }
    if (options.columnSeacrh) {
        _this.filterToolbar({stringResult: true, searchOnEnter: true, defaultSearch: "cn", searchOperators: true});
    }
    if (options.singleSeach) {
        _this.navGrid(options.pager,
            $.extend({edit: false, add: false, del: false, search: true, refresh: true}, options.navigateOption),
            {},
            {},
            {reloadAfterSubmit: true},
            {multipleSearch: true, multipleGroup: false, showQuery: false});
    }
    _this.bindEventForBtn(options, function (cmd, arg1, arg2, arg3) {
        return _this.jqGrid(cmd, arg1, arg2, arg3);
    });
}
$.fn.ex_treeGrid = function (options) {
    var _this = $(this);
    var buttons = [];
    if ($.Uri && $.Uri.List) {
        var buttonList = eval('(' + $.Uri.List + ')')
        $.each(buttonList, function (index, item) {
            for (var i = 0; i < options.buttons.length; i++) {
                var btnCode = ""
                if (options.buttons[i].btnCode) {
                    btnCode = options.buttons[i].btnCode
                    if (btnCode == item.ButtonCode) {
                        var btn = options.buttons[i];
                        btn.btnName = item.FullName
                        buttons.push(btn);
                    }
                } else {
                    buttons.push(options.buttons[i]);
                }
            }
        });
    }
    else {
        buttons = options.buttons;
    }
    //options.buttons = buttons;
    var sizeArr = getGridTableSize();
    _this.jqGrid(options = $.extend({
        mtype: "POST",
        idField: "Id",
        loadComplete: function () {
            jqLoadComplete(this);
            $(".pager_operation").hide();
            if (options.callback) {
                options.callback()
            }
        },
        datatype: "json",
        treedatatype: "json",
        height: sizeArr.vHeight,
        width: sizeArr.vWidth,
        shrinkToFit: false,
        treeGrid: true,
        treeGridModel: "adjacency",
        multiselect: false,
        autowidth: true,
        jsonReader: {
            repeatitems: false,
            id: options.idField
        }
    }, options)).navGrid(options.pager,
        {edit: false, add: false, del: false, search: false, refresh: true},
        {},
        {},
        {reloadAfterSubmit: true});
    _this.bindEventForBtn(options, function (cmd, arg1, arg2, arg3) {
        return _this.jqGrid(cmd, arg1, arg2, arg3);
    });
}
$.fn.bindEventForBtn = function (options, callback) {
    if (options.toolbar) {
        $("#btnSearch").click(function () {
            var args = $('#search input,#search select').formJsonSerialize();
            $("#list").jqGrid('setGridParam', {
                page: 1,
                postData: args
            }).trigger('reloadGrid');

        })
    }

    function getRow() {
        var rows = []
        return rows
    }

    function getIds() {
        var rowids = [];
        if (!options.multiselect) {
            rowid = callback("getGridParam", "selrow");
            if (rowid != null)
                rowids[0] = rowid;
        }
        else {
            var selected = callback("getGridParam", "selarrrow");
            for (var i = 0; i < selected.length; i++) {
                rowids[i] = selected[i]
            }
        }
        return rowids;
    };

    if (options.buttons) {
        $.each(options.buttons, function (index) {
            var item = options.buttons[index];
            var showPanel;
            if (!item.fit)
                showPanel = top.$(_PANEL);
            else {
                var _ThisPanel = document.createElement("div");
                showPanel = $(_ThisPanel);
            }
            if (item.type == "Custom") {
                callback("navButtonAdd", options.pager, {
                    caption: item.btnName,
                    onClickButton: function () {
                        var arr = [];
                        if (item.NeedIds) {
                            var ids = getIds();
                            if (ids == null || ids.length == 0) {
                                top.$.messager.alert("系统提示", "请选择一条记录.", "error");
                                return;
                            }
                            for (var i = 0; i < ids.length; i++) {
                                arr[i] = ids[i];
                            }
                        }
                        if (item.ClickFun)
                            item.ClickFun(arr);
                        else
                            alert("请添加点击事件");
                    }
                });
            }
            else if (item.type == "Del") {

                callback("navButtonAdd", options.pager, {
                    caption: item.btnName,
                    onClickButton: function () {
                        var args = {}
                        if (!item.NotIds) {
                            var ids = getIds();
                            if (ids == null || ids.length == 0) {
                                top.$.messager.alert("系统提示", "请选择一条记录.", "error");
                                return;
                            }
                            var obj = {}
                            for (var i = 0; i < ids.length; i++) {
                                obj[i] = ids[i];
                            }
                            args["ids"] = obj;
                        }
                        var msg = "确定要删除选中记录？"
                        if (item.confirm)
                            msg = item.confirm;
                        if (item.args) {
                            args = $.extend(item.args, args)
                        }
                        top.$.messager.confirm("系统提示", msg, function (flag) {
                            if (flag) {
                                School.waitPanel.showWait("", 99999);
                                $.post(item.save, args, function (result) {
                                    if (result.flag) {
                                        top.$.messager.alert("系统提示", (result.msg == null || result.msg == "") ? "操作成功！" : result.msg, "info");
                                        $(item.listId).trigger("reloadGrid");
                                    }
                                    else {
                                        top.$.messager.alert("系统提示", result.msg == null ? "操作失败，对此抱歉！" : result.msg, "error");
                                        if (item.afterErrorMsg)
                                            item.afterErrorMsg();
                                    }


                                    School.waitPanel.closeWait()
                                });
                            }
                        });
                    }
                });
            }
            else if (item.type == "Examine") {

                callback("navButtonAdd", options.pager, {
                    caption: item.btnName,
                    onClickButton: function () {
                        var args = {}
                        if (!item.NotIds) {
                            var ids = getIds();
                            if (ids == null || ids.length == 0) {
                                top.$.messager.alert("系统提示", "请选择一条记录.", "error");
                                return;
                            }

                            var obj = {}
                            for (var i = 0; i < ids.length; i++) {
                                obj[i] = ids[i];
                            }
                            args["ids"] = obj;
                        }
                        var msg = "";
                        if (item.confirm)
                            msg = item.confirm;
                        if (item.args) {
                            args = $.extend(item.args, args)
                        }
                        top.$.messager.prompt('系统提示', msg, function (r) {
                            var fa = args.flag;
                            var id = args.ids;
                            School.waitPanel.showWait("", 99999);
                            $.post(item.save, {flag: fa, opinion: r, id: id}, function (result) {
                                if (result.flag) {
                                    top.$.messager.alert("系统提示", (result.msg == null || result.msg == "") ? "操作成功！" : result.msg, "info");
                                    $(item.listId).trigger("reloadGrid");
                                }
                                else {
                                    top.$.messager.alert("系统提示", result.msg == null ? "操作失败，对此抱歉！" : result.msg, "error");
                                    if (item.afterErrorMsg)
                                        item.afterErrorMsg();
                                }
                                School.waitPanel.closeWait()
                            });
                        });
                    }
                });
            }
            else if (item.type == "AddOrUpdate") {
                callback("navButtonAdd", options.pager, {
                    caption: item.btnName,
                    onClickButton: function () {
                        var _option = $.extend({
                            cache: false,
                            width: 700,
                            height: 300,
                            resizable: false,
                            zIndex: 9000,
                            maximizable: false,
                            collapsible: false,
                            listId: "#list",
                            onClose: function () {
                                showPanel.dialog("destroy");
                            },
                            fit: false,
                            modal: true,
                            buttons: item.isShowOK != false ? [
                                {
                                    iconCls: 'icon-ok', text: "确定", handler: function () {
                                        if (item.validateing) {
                                            if (item.validateing(item) == false)
                                                return;
                                        }
                                        if (item.save) {
                                            if (showPanel.form('validate')) {
                                                var args = showPanel.find("input,textarea,select").formSerialize();

                                                if (item.submit) {
                                                    args = item.submit(args);
                                                }
                                                School.waitPanel.showWait("", 99999);
                                                $.post(item.save, args, function (result) {
                                                    School.waitPanel.closeWait()
                                                    if (result.flag) {
                                                        if (item.callback) {
                                                            item.callback(result)
                                                        }
                                                        top.$.messager.alert("系统提示", (!result.msg || result.msg == "" || result.msg == null) ? "操作成功！" : result.msg, "info");

                                                        $(_option.listId).trigger("reloadGrid");
                                                        showPanel.dialog("close");
                                                    }
                                                    else {
                                                        top.$.messager.alert("系统提示", result.msg == null ? "操作失败，对此抱歉！" : result.msg, "error");
                                                    }
                                                });
                                            }
                                        } else {
                                            showPanel.dialog("close");
                                        }
                                    }
                                },
                                {
                                    iconCls: 'icon-cancel', text: "取消", handler: function () {
                                        if (item.onClose) {
                                            item.onClose();
                                        }
                                        showPanel.dialog("close");
                                    }
                                }
                            ] : [{
                                iconCls: 'icon-cancel', text: "取消", handler: function () {

                                    if (item.onClose) {
                                        item.onClose();
                                    }
                                    showPanel.dialog("close");
                                }
                            }]
                        }, item);
                        if (_option.href.indexOf("{id}") != -1) {
                            var rowids = getIds();
                            if (rowids == null || rowids.length == 0) {
                                top.$.messager.alert("系统提示", "请选择一条记录.", "error");
                                return;
                            } else if (rowids[1]) {
                                top.$.messager.alert("系统提示", "只能选择一条记录.请更正！", "error");
                                return;
                            }
                            _option.href = _option.href.replace("{id}", rowids[0]);
                        }
                        if (_option.href.indexOf("{parentid}") != -1) {
                            var rowids = getIds();
                            var parentid = 0;
                            if (rowids == null || rowids.length == 0) {
                                parentid = 0
                            } else if (rowids[1]) {
                                parentid = 0
                            } else {
                                parentid = rowids[0]
                            }
                            _option.href = _option.href.replace("{parentid}", parentid);
                        }
                        if (options.textField && _option.title && _option.title.indexOf("{0}") != -1) {
                            var rowid = callback("getGridParam", "selrow");
                            var obj = callback("getRowData", rowid)[options.textField]
                            _option.title = _option.title.replace("{0}", obj)
                        }
                        showPanel.dialog(_option);
                    }
                });
            }
            else if (item.type == "Export") {
                callback("navButtonAdd", options.pager, {
                    caption: item.btnName,
                    onClickButton: function () {
                        var ids = getIds().toString();
                        $.post(item.save, {ids: ids}, function (data) {
                            if (data.flag) {
                                top.$.messager.alert("系统提示", data.msg, "info");
                            } else {
                                top.$.messager.alert("系统提示", data.msg, "error");

                            }
                        })
                    }
                })
            }
            else if (item.type == "Import") {
                callback("navButtonAdd", options.pager, {
                    caption: item.btnName,
                    onClickButton: function () {
                        var _option = $.extend({
                            cache: false,
                            width: 400,
                            height: 250,
                            resizable: false,
                            zIndex: 9000,
                            maximizable: false,
                            collapsible: false,
                            iconCls: "icon-import",
                            title: '数据导入',
                            content: "<input type='file' id='file' name='fileName'>",
                            onClose: function () {
                                showPanel.dialog("destroy");
                            },
                            fit: false,
                            modal: true,
                            buttons: [
                                {
                                    text: '确定', iconCls: 'icon-save', handler: function () {
                                        var file = top.$("#file")[0].files[0];
                                        var formData = new FormData();
                                        formData.append('file', file);
                                        $.ajax({
                                            url: item.save,
                                            type: 'POST',
                                            cache: false,
                                            data: formData,
                                            processData: false,
                                            contentType: false,
                                            success: function (data) {
                                                if (data.flag) {
                                                    top.$.messager.alert("系统提示", data.msg, "info");
                                                } else {
                                                    top.$.messager.alert("系统提示", data.msg, "error");
                                                }
                                                showPanel.dialog("close");
                                            }
                                        })
                                    }
                                },
                                {
                                    text: '取消', iconCls: 'icon-cancel', handler: function () {
                                        showPanel.dialog("close");
                                    }
                                }
                            ]
                        }, item);
                        showPanel.dialog(_option);
                    }
                });
            }
            else if (item.type == "Obsolete") {
                callback("navButtonAdd", options.pager, {
                    caption: item.btnName,
                    onClickButton: function () {
                        const ids = getIds();
                        if (ids == null || ids.length == 0) {
                            top.$.messager.alert("系统提示", "请选择一条记录.", "error");
                            return;
                        } else if (ids[1]) {
                            top.$.messager.alert("系统提示", "只能选择一条记录.请更正！", "error");
                            return;
                        }
                        var _option = $.extend({
                            cache: false,
                            width: 500,
                            height: 250,
                            resizable: false,
                            zIndex: 9000,
                            maximizable: false,
                            collapsible: false,
                            content: "<textarea id='text' style='width:95%;height:85%'></textarea>",
                            iconCls: "icon-edit",
                            listId: "#list",
                            fit: false,
                            modal: true,
                            buttons: [
                                {
                                    text: '确定', iconCls: 'icon-save', handler: function () {
                                        var value = top.$("#text").val();
                                        $.post(item.save, {id: ids.toString(), text: value}, function (data) {
                                            if (data.flag) {
                                                top.$.messager.alert("系统提示", data.msg, "info");
                                            } else {
                                                top.$.messager.alert("系统提示", data.msg, "error");
                                            }
                                            $(_option.listId).trigger("reloadGrid");
                                            showPanel.dialog("close");
                                        })
                                    }
                                },
                                {
                                    text: '取消', iconCls: 'icon-cancel', handler: function () {
                                        showPanel.dialog("close");
                                    }
                                }
                            ]
                        }, item);
                        showPanel.dialog(_option);
                    }
                })
            }
            else if (item.type == "SetManager") {
                callback("navButtonAdd", options.pager, {
                    caption: item.btnName,
                    onClickButton: function () {
                        var ids = getIds();
                        if (ids == null || ids.length == 0) {
                            top.$.messager.alert("系统提示", "请选择一条记录.", "error");
                            return;
                        } else if (ids[1]) {
                            top.$.messager.alert("系统提示", "只能选择一条记录.请更正！", "error");
                            return;
                        }

                        function content() {
                            var tbl = '<div data-options="region:"center",border:false">';
                            tbl += '<form id="fileform" method="post" enctype="multipart/form-data">';
                            ;
                            tbl += '<table id="tabBase" style="margin:40px 0 0 50px">';
                            tbl += '<tr>';
                            tbl += '<td class="width80 right">' + item.label + '：</td>';
                            tbl += '<td><input id="fileInput" name="fileInput" type="text" /></td>';
                            tbl += '</tr>';
                            tbl += '</table>';
                            tbl += '</form>';
                            tbl += '</div>';
                            return tbl;
                        }

                        var _option = $.extend({
                            cache: false,
                            width: 500,
                            height: 250,
                            resizable: false,
                            zIndex: 9000,
                            maximizable: false,
                            collapsible: false,
                            content: content(),
                            iconCls: "icon-edit",
                            listId: "#list",
                            onOpen: function () {
                                $.post(item.load, {id: ids}, function (data) {
                                    top.$("#fileInput").combotreegrid({
                                        value: '' + data + '',
                                        width: '200%',
                                        panelWidth: 300,
                                        multiple: true,
                                        labelPosition: 'top',
                                        url: '/teacher/combotreeList',
                                        idField: 'Id',
                                        treeField: 'Name',
                                        columns: [[
                                            {field: 'Name', title: '名称', width: 300}
                                        ]]
                                    })
                                })
                            },
                            fit: false,
                            modal: true,
                            buttons: [
                                {
                                    text: '确定', iconCls: 'icon-save', handler: function () {
                                        var value = top.$("#fileInput").val();
                                        $.post(item.save, {id: ids, manager: value}, function (data) {
                                            if (data.flag) {
                                                top.$.messager.alert("系统提示", data.msg, "info");
                                            } else {
                                                top.$.messager.alert("系统提示", data.msg, "error");
                                            }
                                            $(_option.listId).trigger("reloadGrid");
                                            showPanel.dialog("close");
                                        })
                                    }
                                },
                                {
                                    text: '取消', iconCls: 'icon-cancel', handler: function () {
                                        showPanel.dialog("close");
                                    }
                                }
                            ]
                        }, item);
                        showPanel.dialog(_option);
                    }
                });
            }
            else if (item.type == "combotreegrid") {
                callback("navButtonAdd", options.pager, {
                    caption: item.btnName,
                    onClickButton: function () {
                        var ids = getIds();
                        if (ids == null || ids.length == 0) {
                            top.$.messager.alert("系统提示", "请选择一条记录.", "error");
                            return;
                        } else if (ids[1]) {
                            top.$.messager.alert("系统提示", "只能选择一条记录.请更正！", "error");
                            return;
                        }

                        function content() {
                            var tbl = '<div data-options="region:"center",border:false">';
                            tbl += '<form id="fileform" method="post" enctype="multipart/form-data">';
                            ;
                            tbl += '<table id="tabBase" style="margin:40px 0 0 50px">';
                            tbl += '<tr>';
                            tbl += '<td class="width80 right">' + item.label + '：</td>';
                            tbl += '<td><input id="fileInput" name="fileInput" type="text" /></td>';
                            tbl += '</tr>';
                            tbl += '</table>';
                            tbl += '</form>';
                            tbl += '</div>';
                            return tbl;
                        }

                        var _option = $.extend({
                            cache: false,
                            width: 500,
                            height: 250,
                            resizable: false,
                            zIndex: 9000,
                            maximizable: false,
                            collapsible: false,
                            content: content(),
                            iconCls: "icon-edit",
                            listId: "#list",
                            onOpen: function () {
                                $.post(item.load, {id: ids}, function (data) {
                                    top.$("#fileInput").combotreegrid({
                                        value: '' + data + '',
                                        width: '200%',
                                        panelWidth: 300,
                                        multiple: true,
                                        labelPosition: 'top',
                                        url: item.treeUrl,
                                        idField: item.idField,
                                        treeField: item.treeField,
                                        columns: item.columns
                                    })
                                })
                            },
                            fit: false,
                            modal: true,
                            buttons: [
                                {
                                    text: '确定', iconCls: 'icon-save', handler: function () {
                                        var value = top.$("#fileInput").val();
                                        $.post(item.save, {id: ids, data: value}, function (data) {
                                            if (data.flag) {
                                                top.$.messager.alert("系统提示", data.msg, "info");
                                            } else {
                                                top.$.messager.alert("系统提示", data.msg, "error");
                                            }
                                            $(_option.listId).trigger("reloadGrid");
                                            showPanel.dialog("close");
                                        })
                                    }
                                },
                                {
                                    text: '取消', iconCls: 'icon-cancel', handler: function () {
                                        showPanel.dialog("close");
                                    }
                                }
                            ]
                        }, item);
                        showPanel.dialog(_option);
                    }
                });
            }
            else if (item.type == "AddTab") {
                callback("navButtonAdd", options.pager, {
                    caption: item.btnName,
                    onClickButton: function () {
                        var tabId = "schoolCalendar_current";
                        var href = item.href
                        if (href.indexOf("{id}") != -1) {
                            var rowids = getIds();
                            if (rowids == null || rowids.length == 0) {
                                top.$.messager.alert("系统提示", "请选择一条记录.", "error");
                                return;
                            } else if (rowids[1]) {
                                top.$.messager.alert("系统提示", "只能选择一条记录.请更正！", "error");
                                return;
                            }
                            href = href.replace("{id}", rowids[0]);
                            tabId = "schoolCalendar_" + rowids[0]
                        }
                        top.addTab(tabId, item.title, href, true, true)
                    }
                });
            }
            else if (item.type == "Batch") {
                callback("navButtonAdd", options.pager, {
                    caption: item.btnName,
                    onClickButton: function () {
                        var ids = getIds();
                        if (ids == null || ids.length == 0) {
                            top.$.messager.alert("系统提示", "请选择一条记录.", "error");
                            return;
                        }

                        function content() {
                            var tbl = '<div data-options="region:"center",border:false">';
                            tbl += '<form id="fileform" method="post" enctype="multipart/form-data">';
                            ;
                            tbl += '<table id="tabBase" style="margin:40px 0 0 50px">';
                            tbl += '<tr>';
                            tbl += '<td class="width80 right">' + item.label + '：</td>';
                            tbl += '<td><input id="fileInput" name="fileInput" type="text" /></td>';
                            tbl += '</tr>';
                            tbl += '</table>';
                            tbl += '</form>';
                            tbl += '</div>';
                            return tbl;
                        }

                        var _option = $.extend({
                            cache: false,
                            width: 500,
                            height: 250,
                            resizable: false,
                            zIndex: 9000,
                            maximizable: false,
                            collapsible: false,
                            content: content(),
                            iconCls: "icon-edit",
                            listId: "#list",
                            onOpen: function () {
                                if (item.typeId == 1) { //国标字典
                                    top.$("#fileInput").combobox({
                                        url: "/Manager/Tools/DictionaryListByTypeCode?typecode=" + item.typeCode,
                                        valueField: 'MemberValue',
                                        textField: "DisplayText",
                                        required: true
                                    })
                                }
                                else if (item.typeId == 2) {//院系 
                                    top.$("#fileInput").combotree({
                                        url: "/area/combotreeList",
                                        textField: 'AreaName',
                                        label: '设置管理员:',
                                        required: true,
                                        onClick: function (node) {
                                            console.log(node)
                                            var pnote = top.$('#fileInput').combotree("tree").tree('isLeaf', node.target);
                                            if (!pnote) {
                                                top.$('#fileInput').combotree('clear');
                                                top.$('#fileInput').combotree("showPanel");
                                                top.$('#fileInput').combotree("tree").tree('toggle', node.target);
                                            } else {
                                                top.$("#fileInput").val(node.id);
                                            }
                                            var attributes = eval('(' + node.attributes + ')')
                                            if (attributes.type != "faculty") {
                                                top.$('#fileInput').combotree('clear');
                                                top.$('#fileInput').combotree("showPanel");
                                            }
                                        }
                                    })
                                }
                                else if (item.typeId == 3) { //班级状态
                                    top.$("#fileInput").combobox({
                                        url: "/Manager/Basic/Class_StateList",
                                        valueField: 'Value',
                                        textField: "Text",
                                        required: true
                                    })
                                }
                            },
                            onClose: function () {
                                showPanel.dialog("destroy");
                            },
                            fit: false,
                            modal: true,
                            buttons: [
                                {
                                    iconCls: 'icon-ok', text: "保存", handler: function () {
                                        if (item.validateing) {
                                            if (item.validateing(item) == false)
                                                return;
                                        }

                                        if (item.save) {
                                            if (showPanel.form('validate')) {
                                                var args = showPanel.find("input,textarea,select").formSerialize();
                                                var obj = {}
                                                for (var i = 0; i < ids.length; i++) {
                                                    obj[i] = ids[i];
                                                }
                                                args["ids"] = obj;
                                                if (item.submit) {
                                                    args = item.submit(args);
                                                }
                                                School.waitPanel.showWait("", 99999);
                                                $.post(item.save, args, function (result) {
                                                    School.waitPanel.closeWait()
                                                    if (result.flag) {
                                                        if (item.callback) {
                                                            item.callback(result)
                                                        }
                                                        top.$.messager.alert("系统提示", (!result.msg || result.msg == "" || result.msg == null) ? "操作成功！" : result.msg, "info");

                                                        $(_option.listId).trigger("reloadGrid");
                                                        showPanel.dialog("close");
                                                    }
                                                    else {
                                                        top.$.messager.alert("系统提示", result.msg == null ? "操作失败，对此抱歉！" : result.msg, "error");
                                                    }
                                                });
                                            }
                                        } else {
                                            showPanel.dialog("close");
                                        }
                                    }
                                },
                                {
                                    iconCls: 'icon-cancel', text: "取消", handler: function () {
                                        if (item.onClose) {
                                            item.onClose();
                                        }
                                        showPanel.dialog("close");
                                    }
                                }
                            ]
                        }, item);

                        showPanel.dialog(_option);
                    }
                });
            }
            else if (item.type == "AddOrUpdateHasFile") {
                callback("navButtonAdd", options.pager, {
                    caption: item.btnName,
                    onClickButton: function () {
                        var _option = $.extend({
                            cache: false,
                            width: 700,
                            height: 300,
                            resizable: false,
                            zIndex: 9000,
                            maximizable: false,
                            collapsible: false,
                            listId: "#list",
                            onClose: function () {
                                showPanel.dialog("destroy");
                            },
                            fit: false,
                            modal: true,
                            onLoad: function () {
                                showPanel.find("form").form({
                                    url: item.save,
                                    success: function (data) {
                                        School.waitPanel.closeWait();
                                        if (!data || data == "" || data == null) {
                                            showInfo("未知错误", false);
                                            return;
                                        }
                                        var result = eval("(" + data + ")");
                                        if (result.flag) {
                                            if (item.callback) {
                                                item.callback(result)
                                            }
                                            top.$.messager.alert("系统提示", (!result.msg || result.msg == "" || result.msg == null) ? "操作成功！" : result.msg, "info");

                                            $(_option.listId).trigger("reloadGrid");
                                            showPanel.dialog("close");
                                        }
                                        else {
                                            top.$.messager.alert("系统提示", result.msg == null ? "操作失败，对此抱歉！" : result.msg, "error");
                                        }
                                    },
                                    onSubmit: function (args) {
                                        if (item.validateing) {
                                            if (item.validateing(item) == false) {
                                                School.waitPanel.closeWait();
                                                return false;
                                            }

                                        }
                                        return true
                                    }
                                })
                            },
                            buttons: item.isShowOK != false ? [
                                {
                                    iconCls: 'icon-ok', text: "确定", handler: function () {
                                        if (showPanel.find("form").form("validate")) {

                                            School.waitPanel.showWait("", 99999);
                                            showPanel.find("form").submit();
                                        }
                                    }
                                },
                                {
                                    iconCls: 'icon-cancel', text: "取消", handler: function () {
                                        if (item.onClose) {
                                            item.onClose();
                                        }
                                        showPanel.dialog("close");
                                    }
                                }
                            ] : [{
                                iconCls: 'icon-cancel', text: "取消", handler: function () {

                                    if (item.onClose) {
                                        item.onClose();
                                    }
                                    showPanel.dialog("close");
                                }
                            }]
                        }, item);
                        if (_option.href.indexOf("{id}") != -1) {
                            var rowids = getIds();
                            if (rowids == null || rowids.length == 0) {
                                top.$.messager.alert("系统提示", "请选择一条记录.", "error");
                                return;
                            } else if (rowids[1]) {
                                top.$.messager.alert("系统提示", "只能选择一条记录.请更正！", "error");
                                return;
                            }
                            _option.href = _option.href.replace("{id}", rowids[0]);
                        }
                        if (_option.href.indexOf("{parentid}") != -1) {
                            var rowids = getIds();
                            var parentid = 0;
                            if (rowids == null || rowids.length == 0) {
                                parentid = 0
                            } else if (rowids[1]) {
                                parentid = 0
                            } else {
                                parentid = rowids[0]
                            }
                            _option.href = _option.href.replace("{parentid}", parentid);
                        }
                        if (options.textField && _option.title && _option.title.indexOf("{0}") != -1) {
                            var rowid = callback("getGridParam", "selrow");
                            var obj = callback("getRowData", rowid)[options.textField]
                            _option.title = _option.title.replace("{0}", obj)
                        }
                        showPanel.dialog(_option);
                    }
                });
            }
            else if (item.type == "Audit") {
                callback("navButtonAdd", options.pager, {
                    caption: item.btnName,
                    onClickButton: function () {
                        var ids = getIds();
                        if (ids == null || ids.length == 0) {
                            top.$.messager.alert("系统提示", "请选择一条记录.", "error");
                            return;
                        } else if (ids[1]) {
                            top.$.messager.alert("系统提示", "只能选择一条记录.请更正！", "error");
                            return;
                        }
                        var _option = $.extend({
                            cache: false,
                            width: 500,
                            height: 300,
                            resizable: false,
                            zIndex: 9000,
                            maximizable: false,
                            collapsible: false,
                            content: "<textarea id='opinion' style='width:95%;height:85%'></textarea>",
                            iconCls: "icon-edit",
                            listId: "#list",
                            fit: false,
                            modal: true,
                            buttons: [
                                {
                                    text: '通过', iconCls: 'icon-save', handler: function () {
                                        var rowDatas = top.$("#opinion").val();
                                        $.post(item.save, {
                                            ids: ids.toString(),
                                            status: 1,
                                            data: rowDatas
                                        }, function (data) {
                                            if (data.flag) {
                                                top.$.messager.alert("系统提示", data.msg, "info");
                                            } else {
                                                top.$.messager.alert("系统提示", data.msg, "error");
                                            }
                                            $(_option.listId).trigger("reloadGrid");
                                            showPanel.dialog("close");
                                        })
                                    }
                                }, {
                                    text: '拒绝', iconCls: 'icon-remove', handler: function () {

                                        var rowDatas = top.$("#opinion").val();
                                        $.post(item.save, {
                                            ids: ids.toString(),
                                            status: 2,
                                            data: rowDatas
                                        }, function (data) {
                                            if (data.flag) {
                                                top.$.messager.alert("系统提示", data.msg, "info");
                                            } else {
                                                top.$.messager.alert("系统提示", data.msg, "error");
                                            }
                                            $(_option.listId).trigger("reloadGrid");
                                            showPanel.dialog("close");
                                        })
                                    }
                                },
                                {
                                    text: '取消', iconCls: 'icon-cancel', handler: function () {
                                        showPanel.dialog("close");
                                    }
                                }
                            ]
                        }, item);
                        showPanel.dialog(_option);
                    }
                });
            }
            else if (item.type == "Reply") {
                callback("navButtonAdd", options.pager, {
                    caption: item.btnName,
                    onClickButton: function () {
                        var ids = getIds();
                        var _option = $.extend({
                            cache: false,
                            width: 500,
                            height: 300,
                            resizable: false,
                            zIndex: 9000,
                            maximizable: false,
                            collapsible: false,
                            content: "<textarea id='opinion' style='width:95%;height:85%'></textarea>",
                            iconCls: "icon-edit",
                            listId: "#list",
                            fit: false,
                            modal: true,
                            buttons: [
                                {
                                    text: '确定', iconCls: 'icon-save', handler: function () {
                                        var rowDatas = top.$("#opinion").val();
                                        $.post(item.save, {ids: ids.toString(), data: rowDatas}, function (data) {
                                            if (data.flag) {
                                                top.$.messager.alert("系统提示", data.msg, "info");
                                            } else {
                                                top.$.messager.alert("系统提示", data.msg, "error");
                                            }
                                            $(_option.listId).trigger("reloadGrid");
                                            showPanel.dialog("close");
                                        })
                                    }
                                },
                                {
                                    text: '取消', iconCls: 'icon-cancel', handler: function () {
                                        showPanel.dialog("close");
                                    }
                                }
                            ]
                        }, item);
                        showPanel.dialog(_option);
                    }
                });
            }
        });
    }
}

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
        page: gopage_val
    }).trigger('reloadGrid');
}

function getGridTableSize() {
    var size = {};
    size.vHeight = $(window).height() - $('.data-operation').outerHeight(true) - $('.pager_operation').outerHeight(true) - 50;
    size.vWidth = $(".data-list").width();
    return size;
}

function formatDate(d) {
    if (d == null || "") return "";
    var date = new Date(parseInt(d.replace("/Date(", "").replace(")/", ""), 10));
    var month = date.getMonth() + 1;
    month = month >= 10 ? month : "0" + month;
    var currentDate = date.getDate() >= 10 ? date.getDate() : "0" + date.getDate();
    var hour = date.getHours() >= 10 ? date.getHours() : "0" + date.getHours();
    var minute = date.getMinutes() >= 10 ? date.getMinutes() : "0" + date.getMinutes();
    return date.getFullYear() + "-" + month + "-" + currentDate + " " + hour + ":" + minute;
};
var showInfo = function (msg, type) {
    var msgClass = type == true ? "textstyle2" : "textstyle1";
    top.$("#result").removeClass();
    top.$("#result").addClass(msgClass);
    top.$("#result").html(msg);
}

var validateSelectRowsObj = function (listId, isMultiSelect, callback) {
    var Id = listId;
    var MultiSelect = isMultiSelect;

    function getRowids() {
        if (isMultiSelect) {
            return $("#" + Id).jqGrid("getGridParam", "selarrrow");
        }
        else {
            return $("#" + Id).jqGrid("getGridParam", "selrow");
        }
    }

    this.SelectOneOnly = function () {
        if (MultiSelect) {
            var ids = getRowids();
            if (ids == null || ids.length == 0) {
                top.$.messager.alert("系统提示", "请选择要操作的数据！", "info");
                return false;
            }
            else {
                if (ids.length > 1) {
                    top.$.messager.alert("系统提示", "只能选择一条数据进行操作，请更正！", "info");
                    return false;
                }
                else {
                    callback(ids[0]);
                    return;
                }
            }
        }
        else {
            var id = getRowids();
            if (id == null) {
                top.$.messager.alert("系统提示", "请选择要操作的数据！", "info");
                return false;
            }
            else {
                callback(id);
                return;
            }
        }
    }

    this.SelectMore = function () {
        if (MultiSelect) {
            var ids = getRowids();
            if (ids == null || ids.length == 0) {
                top.$.messager.alert("系统提示", "请选择要操作的数据！", "info");
                return false;
            }
            else {
                callback(ids);
                return;
            }
        }
        else {
            var id = getRowids();
            if (id == null) {
                top.$.messager.alert("系统提示", "请选择要操作的数据！", "info");
                return false;
            }
            else {
                callback(id);
                return;
            }
        }
    }
}

$(function () {
    $("#search input").keydown(function () {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if (e.keyCode == 13) {
            $("#btnSearch").click();
        }
    });
    $("#search button[type=reset]").click(function () {
        $('#search input,#search select').val('');
    })
    $(window).resize(function () {
        var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
        var fHeight = $('#header').is(':visible') ? $('#header').outerHeight() : 0;
        var cHeight = wHeight - fHeight - $('#footer').outerHeight() - $('#logo').outerHeight();
        $("iframe").height((cHeight - 50))
    });
});
function viewDetail(id, realname) {
    const url = this.location.pathname+"/view/"+id;
    School.showDivHasSave({
        title: realname+"-详细信息",
        btnName: "详细信息",
        href: url,
        fit:true
    })
}

