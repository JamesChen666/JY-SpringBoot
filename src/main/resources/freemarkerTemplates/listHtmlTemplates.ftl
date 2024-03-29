<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    <meta charset="utf-8"/>
${r'<#include "../base/header.html">'}
    <script>
        $(function () {
            $("#list").ex_grid({
                url: "/${className?uncap_first}/list",
                idField: "Id",
                textField: 'Id',
                treeGridModel: 'adjacency',
                sortName: "Id",
                colNames: ['Id', "姓名", "学号", "", "", "", "", "备注", "排序"],
                colModel: [
                    {name: 'Id', index: 'Id', hidden: true},
                    <#list attrs as attr>
                        <#if attr.name !='Id' >
                    {name: '${attr.name?cap_first}', index: '${attr.name?cap_first}', align: "center", width: 100},
                        </#if>
                    </#list>
                ],
                pager: "#pager",
                ExpandColumn: "Id",
                buttons:
                        [
                            {
                                type: "AddOrUpdate",
                                title: "${className}管理-新增${className}",
                                btnName: "新增${className}",
                                btnCode: "Add",
                                iconCls: "icon-add",
                                listId: "#list",
                                href: "/${className?uncap_first}/add",
                                save: "save",
                                fit: true
                            },
                            {
                                type: "AddOrUpdate",
                                iconCls: "icon-edit",
                                title: "${className}管理-修改${className}",
                                btnName: "修改${className}",
                                btnCode: "Edit",
                                listId: "#list",
                                href: "/${className?uncap_first}/edit?id={id}",
                                save: "save",
                                fit: true
                            },
                            {
                                type: "Del",
                                btnName: "删除",
                                btnCode: "Delete",
                                listId: "#list",
                                save: "delete"
                            },
                            {
                                type: "Import",
                                btnName: "导入",
                                btnCode: "Import",
                                listId: "#list",
                                save: "/${className?uncap_first}/import"
                            },
                            {
                                type: "Export",
                                btnName: "导出",
                                btnCode: "Export",
                                listId: "#list",
                                save: "/${className?uncap_first}/export"
                            }
                        ]
            });
        })
    </script>
</head>
<body>
<div class="search_area" id="search">
    <div class="form-search form-search-top" style="text-align:left;padding-left:10px;">
        <div class="adv-select-label">姓名/学号/...：</div>
        <input name="sm_RealName-StudentNumber" type="text" value=""/>
        <div class="adv-select-label">是否启用：</div>
        <select id="s_IsEnabled" name="s_IsEnabled">
            <option value="">==所有==</option>
            <option value="1">是</option>
            <option value="0">否</option>
        </select>
        <button type="button" class="btn btn-primary" id="btnSearch">查 询</button>
        <button type="reset" class="btn btn-info" id="btnSearch">重置查询</button>
    </div>
</div>
<div data-options="region:'center'">
    <div class="data-wrap">
        <div class="data-operation">

            <div class="button-operation">
                <!--放按钮的层11-->
            </div>

            <div class="clear"></div>
        </div>
        <div class="data-list">
            <table id="list"></table>
            <div id="pager"></div>
            <script src="/static/main/Scripts/jqgrid/js/jquery.jqpager.js"></script>
            <script src="/static/main/Scripts/jqgrid/js/bootstrap.paginator.min.js"></script>
            <script>
                var pageUnit = "条"
            </script>
            <div id="sorry" style="display: none; margin-top: 55px;" align="center">
                <small>暂无符合条件的数据.</small>
            </div>
            <div class="pager_operation">
                <div class="page-info-block">
                    共<span id="total_records"></span>条 第<span id="current_page" style="display: none;"></span><input
                        type="hidden" id="gopage_url"/><input type="text" id="gopage" name="gopage" value=""/>/<span
                        id="total_page"></span>页 <span class="pager-gopager" id="gopager-img" onclick="gopager();"
                                                       title="跳转">&nbsp;</span>
                </div>
                <div class="pagination" id="work-pager-block"></div>
                <div class="page-info-block">
                    <div class="btn-group dropup pager-selbox-div" id="pager-selbox-div">
                        <button style="border:none;padding:0px;"></button>
                        <button class="btn pager-selbox-button dropdown-toggle" id="pager-selbox" title="显示条数"
                                data-toggle="dropdown"></button>
                        <ul class="dropdown-menu pager-selbox-menu"></ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
