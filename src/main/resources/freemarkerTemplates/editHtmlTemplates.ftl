<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    ${r'<#include "../base/header.html">'}
</head>
<body>
<from id="from" action="/${className?uncap_first}/edit/${r'${id}'}" class="easyui-form">
    <input type="hidden" name="Id" value="${r'${id}'}">
    <table class="tab1" cellpadding="3">
        <tbody>
        <#list attrs as attr>
            <tr>
                <td class="left b">${attr.name}:</td>
                <td>
                    <input class="easyui-textbox" name="${attr.name?uncap_first}" data-options="required:true"/>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</from>
<script src="/static/main/EditFrom.js"></script>
</body>
</html>
