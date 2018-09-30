/*对EasyUI验证的扩展*/
top.$.extend(top.$.fn.datebox.defaults.rules, {
    //验证datebox控件开始时间必须小于或等于结束时间
    EndLessbeginTime: {
        validator: function (value, param) {
            var temp = $(param[0]).val();
            try {
                temp = $(param[0]).datebox('getValue');
            } catch (e) {
                temp = $(param[0]).val();
            }
            if (temp == "")
                return true;
            var beginTime = stringToTime(temp);
            var endTime = stringToTime(value);
            return endTime > beginTime;
        },
        message: "结束时间必须大于开始时间"
    },
    //验证datebox控件开始时间必须小于结束时间
    beginLessEndTime: {
        validator: function (value, param) {
            try {
                temp = $(param[0]).datebox('getValue');
            } catch (e) {
                temp = $(param[0]).val();
            }
            if (temp == "")
                return true;
            var endTime = stringToTime(temp);
            var beginTime = stringToTime(value);
            return beginTime < endTime;
        },
        message: "开始时间必须小于结束时间"
    },
    /*
    *比较当前datebox控件值与指定datebox控件值
    *   调用方法：
    *   validType="CompareDate['要进行对比的指定控件ID,如果传入now表示与当前系统日期进行比较','比较方式,如><=','指定控件描述,用于错误信息提示']"
    *   例如 validType="CompareDate['SBSJ','>','上报日期']"
    */
    CompareDate: {
        validator: function (value, param) {
            if (value == '') return true;
            if (param.length < 4) return true;
            var targetValue;
            var currentTime = stringToTime(value);
            var targetTime;
            var msg = "";
            if (param[0] != 'now') {
                try {
                    targetValue = $(param[0]).datebox('getValue');
                } catch (e) {
                    targetValue = $(param[0]).val();
                }
                if (targetValue == '') return true;
                targetTime = stringToTime(targetValue);
            } else
                targetTime = new Date();
            switch (param[1]) {
                case ">":
                    if (currentTime > targetTime) return true;
                    break;
                case ">=":
                    if (currentTime >= targetTime) return true;
                    break;
                case "<":
                    if (currentTime < targetTime) return true;
                    break;
                case "<=":
                    if (currentTime <= targetTime) return true;
                    break;
                case "=":
                    if (currentTime = targetTime) return true;
                    break;
                case "!=":
                    if (currentTime != targetTime) return true;
                    break;
            }
            return false;
        },
        message: "所选时间必须{3}{2}!"
    },
    BetweenData: {
        validator: function (value, param) {
            if (value == '') return true;
            if (param.length < 2) return true;
            if (stringToTime(value) >= stringToTime(param[0]) && stringToTime(value) < stringToTime(param[1]))
                return true;
            return false;
        },
        message: "所选时间必须在{0}-{1}之间!"
    },
    //扩展重复密码验证
    equalTo: {
        validator: function (value, param) { 
            return top.$(param[0]).val() == value;
        },
        message: '当前输入与[{1}]字段不匹配'
    },
    //验证只能输入中文
    validChinese: {
        validator: function (value) {
            var ret = true;
            for (var i = 0; i < value.length; i++)
                ret = ret && (value.charCodeAt(i) >= 10000);
            return ret;
        },
        message: "请输入中文"
    },
    validBytesLength: {
        validator: function (value, param) {
            value = $.trim(value);
            if (value.length == 0)
                return false;
            else
                return stringToBytes(value).length <= param[0];
        },
        message: '输入字节超过最大字节{0}或全为空白字符.注:字母数字占1个字节,中文占2个字节.'
    },
    validBytesMinLength: {
        validator: function (value, param) {
            value = $.trim(value);
            if (value.length == 0)
                return false;
            else
                return stringToBytes(value).length >= param[0];
        },
        message: '输入字节不能少于字节{0}或全为空白字符.注:字母数字占1个字节,中文占2个字节.'
    },
    validDropDownValueLength: {
        validator: function (value, param) {
            if (param[0] == "tree")
                value = $("#" + param[1]).combotree("getValue");
            else if (param[0] == "hidden")
                value = $("#" + param[1]).val();
            else if (param[0] == "box")
                value = $("#" + param[1]).combobox("getValue");
            else if (param[0] == "grid")
                value = $("#" + param[1]).combogrid("getValue");
            return stringToBytes(value).length <= param[2];
        },
        message: '所选数据值超过最大字节{2},请检查修改相应国标代码.注:字母数字占1个字节,中文占2个字节.'
    },
    //验证只能输入字母或数字
    flowcode: {
        validator: function (value) {
            var reg = /^[A-Za-z0-9]*$/;
            return reg.test(value);
        },
        message: '只能输入A-Z或0-9字符.'
    },
    //验证存数字
    number: {
        validator: function (value) {
            var reg = /^[0-9]*$/;
            return reg.test(value);
        },
        message: '只能输入A-Z或0-9字符.'
    },
    numberCheck: {
        validator: function (value) {
            var reg = /^(-?\d+)(\.\d{1,2})?$/;
            return reg.test(value);
        },
        message: '请输入数字，保留两位小数'
    },
    letter: {
        validator: function (value) {
            var reg = /^[A-Za-z]*$/;
            return reg.test(value);
        },
        message: '只能输入A-Z字母.'
    },
    letternumber: {
        validator: function (value) {
            var reg = /^[0-9A-Za-z]+$/;
            return reg.test(value);
        },
        message: '只能输入字母或者数字.'
    },
    unSpecialCharacter: {
        validator: function (value) {
            reg = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;
            return !reg.test(value);
        },
        message: '不能输入除中文,字母,数字外的特殊字符.'
    },
    phone: {
        validator: function (value) {
            var reg = /^1[3,4,5,7,8]{1}[0-9]{1}[0-9]{8}$|^(0[0-9]{2,3})?(\d{6,8})$/;
            return reg.test(value);
        },
        message: '手机或电话号码格式不正确,座机号码请去掉‘-’.'
    },
    zipcode: {
        validator: function (value) {
            var reg = /^[\d]{6}$/;
            return reg.test(value);
        },
        message: '邮政编码格式不正确.'
    },
    url: {
        validator: function (value) {
            var reg = /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;
            return reg.test(value);
        },
        message: '网址格式错误.'
    },
    QQ: {
        validator: function (value) {
            var reg = /[1-9][0-9]{4,}/;
            return reg.test(value);
        },
        message: 'QQ号码格式错误.'
    },
    username: {
        validator: function (value) {
            var reg = /^[a-zA-Z0-9_]{6,16}$/;
            return reg.test(value);
        },
        message: '账号格式错误,必须为数字或字母，且字符长度不能小于6位'
    },
    password: {
        validator: function (value) {
            var reg = /^[a-zA-Z0-9_]{6,16}$/;
            return reg.test(value);
        },
        message: '密码格式错误,必须为数字或字母且长度在6-16.'
    },
    passCompare: {
        validator: function (value, param) {
            return $('#' + param[0]).val() == value;
        },
        message: '两次密码输入不一致.'
    },
    maxLength: {
        validator: function (value, param) {
            return value.length <= param[0];
        },
        message: '输入字符超过最大字符 {0}.'
    },
    column: {
        validator: function (value) {
            var regex = /^[a-zA-Z][A-Za-z0-9]+$/
            return regex.test(value)
        },
        message: "字段代码格式不正确.请使用字母开头，仅支持字母、数字"
    },
    fixedLength: {
        validator: function (value, param) {
            return value.length == param[0];
        },
        message: '只能输入{0}位字符!'
    },
    EmailNull: {
        validator: function (value) {
            if (value != "") {
                var reg = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i;
                return reg.test(value);
            } else { return false; }

        },
        message: '电子邮箱格式不正确'
    },
    UrlNull: {
        validator: function (value) {
            if (value != "") {
                var reg = /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;
                return reg.test(value);
            } else { return false; }
        },
        message: 'URL格式不正确'
    },
    IdCard: {
        validator: function (value, param) { 
            if (value == "") {
                param[0] = "身份证号码不能为空！"
                return false;
            }
            var num = value.toUpperCase();
            //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
            if (!(/(^\d{17}([0-9]|X)$)/.test(num))) {
                param[0] = '输入的身份证号长度不对，或者号码不符合规定！请输入18位的二代身份证号码。';
                return false;
            }

            //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
            //下面分别分析出生日期和校验位 
            var len, re; len = num.length;
            if (len == 15) {
                re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
                var arrSplit = num.match(re);
                //检查生日日期是否正确 
                var yyear = '19' + arrSplit[2];
                var ymonth = arrSplit[3];
                var yday = arrSplit[4];
                var dtmBirth = new Date(yyear + '/' + ymonth + '/' + yday);
                var bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) &&
                ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) &&
                (dtmBirth.getDate() == Number(arrSplit[4]));
                if (!bGoodDay) {
                    param[0] = '输入的身份证号里出生日期不对！';
                    return false
                }
                if (dtmBirth > new Date()) {
                    param[0] = '输入的身份证号里出生日期不应大于当前日期！';
                    return false;
                }
                else {
                    //将15位身份证转成18位 //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。           
                    var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
                    var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
                    var nTemp = 0, i;
                    num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
                    for (i = 0; i < 17; i++) {
                        nTemp += num.substr(i, 1) * arrInt[i];
                    }
                    num += arrCh[nTemp % 11];
                    return true
                }
            }
            if (len == 18) {
                re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
                var arrSplit = num.match(re);
                //检查生日日期是否正确
                var tyear = arrSplit[2];
                var tmonth = arrSplit[3];
                var tday = arrSplit[4];
                var birth = tyear + "/" + tmonth + "/" + tday;
                var dtmBirth = new Date(birth);
                var bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) &&
                ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) &&
                (dtmBirth.getDate() == Number(arrSplit[4]));
                if (!bGoodDay) {
                    param[0] = ('输入的身份证号里出生日期不对！');
                    return false
                }
                if (tyear < 1900) {
                    param[0] = ('输入的身份证号里出生年份不应小于1900年！');
                    return false
                }
                if (dtmBirth > new Date()) {
                    param[0] = ('输入的身份证号里出生日期不应大于当前日期！');
                    return false
                }
                else {
                    //检验18位身份证的校验码是否正确。 
                    //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
                    var valnum;
                    var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
                    var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
                    var nTemp = 0, i;
                    for (i = 0; i < 17; i++) {
                        nTemp += num.substr(i, 1) * arrInt[i];
                    }
                    valnum = arrCh[nTemp % 11];
                    if (valnum != num.substr(17, 1)) {
                        param[0] = ('18位身份证的最后一位校验码不正确！应该为：' + valnum);
                        return false
                    }
                    return true
                }
            }
            return true;
        },
        message: "{0}"

    }
});

$.extend($.fn.datebox.defaults.rules, {
    //验证datebox控件开始时间必须小于或等于结束时间
    EndLessbeginTime: {
        validator: function (value, param) {
            var temp = $(param[0]).val();
            try {
                temp = $(param[0]).datebox('getValue');
            } catch (e) {
                temp = $(param[0]).val();
            }
            if (temp == "")
                return true;
            var beginTime = stringToTime(temp);
            var endTime = stringToTime(value);
            return endTime > beginTime;
        },
        message: "结束时间必须大于开始时间"
    },
    //验证datebox控件开始时间必须小于结束时间
    beginLessEndTime: {
        validator: function (value, param) {
            try {
                temp = $(param[0]).datebox('getValue');
            } catch (e) {
                temp = $(param[0]).val();
            }
            if (temp == "")
                return true;
            var endTime = stringToTime(temp);
            var beginTime = stringToTime(value);
            return beginTime < endTime;
        },
        message: "开始时间必须小于结束时间"
    },
    /*
    *比较当前datebox控件值与指定datebox控件值
    *   调用方法：
    *   validType="CompareDate['要进行对比的指定控件ID,如果传入now表示与当前系统日期进行比较','比较方式,如><=','指定控件描述,用于错误信息提示']"
    *   例如 validType="CompareDate['SBSJ','>','上报日期']"
    */
    CompareDate: {
        validator: function (value, param) {
            if (value == '') return true;
            if (param.length < 4) return true;
            var targetValue;
            var currentTime = stringToTime(value);
            var targetTime;
            var msg = "";
            if (param[0] != 'now') {
                try {
                    targetValue = $(param[0]).datebox('getValue');
                } catch (e) {
                    targetValue = $(param[0]).val();
                }
                if (targetValue == '') return true;
                targetTime = stringToTime(targetValue);
            } else
                targetTime = new Date();
            switch (param[1]) {
                case ">":
                    if (currentTime > targetTime) return true;
                    break;
                case ">=":
                    if (currentTime >= targetTime) return true;
                    break;
                case "<":
                    if (currentTime < targetTime) return true;
                    break;
                case "<=":
                    if (currentTime <= targetTime) return true;
                    break;
                case "=":
                    if (currentTime = targetTime) return true;
                    break;
                case "!=":
                    if (currentTime != targetTime) return true;
                    break;
            }
            return false;
        },
        message: "所选时间必须{3}{2}!"
    },
    BetweenData: {
        validator: function (value, param) {
            if (value == '') return true;
            if (param.length < 2) return true;
            if (stringToTime(value) >= stringToTime(param[0]) && stringToTime(value) < stringToTime(param[1]))
                return true;
            return false;
        },
        message: "所选时间必须在{0}-{1}之间!"
    },
    //扩展重复密码验证
    equalTo: {
        validator: function (value, param) {
            return $(param[0]).val() == value;
        },
        message: '当前输入与[{1}]字段不匹配'
    },
    //验证只能输入中文
    validChinese: {
        validator: function (value) {
            var ret = true;
            for (var i = 0; i < value.length; i++)
                ret = ret && (value.charCodeAt(i) >= 10000);
            return ret;
        },
        message: "请输入中文"
    },
    validBytesLength: {
        validator: function (value, param) {
            value = $.trim(value);
            if (value.length == 0)
                return false;
            else
                return stringToBytes(value).length <= param[0];
        },
        message: '输入字节超过最大字节{0}或全为空白字符.注:字母数字占1个字节,中文占2个字节.'
    },
    validBytesMinLength: {
        validator: function (value, param) {
            value = $.trim(value);
            if (value.length == 0)
                return false;
            else
                return stringToBytes(value).length >= param[0];
        },
        message: '输入字节不能少于字节{0}或全为空白字符.注:字母数字占1个字节,中文占2个字节.'
    },
    validDropDownValueLength: {
        validator: function (value, param) {
            if (param[0] == "tree")
                value = $("#" + param[1]).combotree("getValue");
            else if (param[0] == "hidden")
                value = $("#" + param[1]).val();
            else if (param[0] == "box")
                value = $("#" + param[1]).combobox("getValue");
            else if (param[0] == "grid")
                value = $("#" + param[1]).combogrid("getValue");
            return stringToBytes(value).length <= param[2];
        },
        message: '所选数据值超过最大字节{2},请检查修改相应国标代码.注:字母数字占1个字节,中文占2个字节.'
    },
    //验证只能输入字母或数字
    flowcode: {
        validator: function (value) {
            var reg = /^[A-Za-z0-9]*$/;
            return reg.test(value);
        },
        message: '只能输入A-Z或0-9字符.'
    },
    numberCheck: {
        validator: function (value) {
            var reg = /^(-?\d+)(\.\d{1,2})?$/;
            return reg.test(value);
        },
        message: '请输入数字，保留两位小数'
    },
    letter: {
        validator: function (value) {
            var reg = /^[A-Za-z]*$/;
            return reg.test(value);
        },
        message: '只能输入A-Z字母.'
    },
    phone: {
        validator: function (value) {
            var reg = /^1[3,4,5,7,8,9]{1}[0-9]{1}[0-9]{8}$|^(0[0-9]{2,3})?(\d{6,7})$/;
            return reg.test(value);
        },
        message: '手机或电话号码格式不正确,座机号码请去掉‘-’.'
    },
    zipcode: {
        validator: function (value) {
            var reg = /^[\d]{6}$/;
            return reg.test(value);
        },
        message: '邮政编码格式不正确.'
    },
    url: {
        validator: function (value) {
            var reg = /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;
            return reg.test(value);
        },
        message: '网址格式错误.'
    },
    QQ: {
        validator: function (value) {
            var reg = /[1-9][0-9]{4,}/;
            return reg.test(value);
        },
        message: 'QQ号码格式错误.'
    },
    username: {
        validator: function (value) {
            var reg = /^[a-zA-Z0-9_]{6,16}$/;
            return reg.test(value);
        },
        message: '账号格式错误,必须为数字或字母，且字符长度不能小于6位'
    },
    password: {
        validator: function (value) {
            var reg = /^[a-zA-Z0-9_]{6,16}$/;
            return reg.test(value);
        },
        message: '密码格式错误,必须为数字或字母且长度在6-16.'
    },
    passCompare: {
        validator: function (value, param) {
            return $('#' + param[0]).val() == value;
        },
        message: '两次密码输入不一致.'
    },
    maxLength: {
        validator: function (value, param) {
            return value.length <= param[0];
        },
        message: '输入字符超过最大字符 {0}.'
    },
    column: {
        validator: function (value) {
            var regex = /^[a-zA-Z][A-Za-z0-9]+$/
            return regex.test(value)
        },
        message: "字段代码格式不正确.请使用字母开头，仅支持字母、数字"
    },
    fixedLength: {
        validator: function (value, param) {
            return value.length == param[0];
        },
        message: '只能输入{0}位字符!'
    },
    EmailNull: {
        validator: function (value) {
            if (value != "") {
                var reg = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i;
                return reg.test(value);
            } else { return false; }

        },
        message: '电子邮箱格式不正确'
    },
    UrlNull: {
        validator: function (value) {
            if (value != "") {
                var reg = /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;
                return reg.test(value);
            } else { return false; }
        },
        message: 'URL格式不正确'
    },
    IdCard: {
        validator: function (value, param) {
            if (value == "") {
                param[0] = "身份证号码不能为空！"
                return false;
            }
            var num = value.toUpperCase();
            //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
            if (!(/(^\d{17}([0-9]|X)$)/.test(num))) {
                param[0] = '输入的身份证号长度不对，或者号码不符合规定！请输入18位的二代身份证号码。';
                return false;
            }

            //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
            //下面分别分析出生日期和校验位 
            var len, re; len = num.length;
            if (len == 15) {
                re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
                var arrSplit = num.match(re);
                //检查生日日期是否正确 
                var yyear = '19' + arrSplit[2];
                var ymonth = arrSplit[3];
                var yday = arrSplit[4];
                var dtmBirth = new Date(yyear + '/' + ymonth + '/' + yday);
                var bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) &&
                ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) &&
                (dtmBirth.getDate() == Number(arrSplit[4]));
                if (!bGoodDay) {
                    param[0] = '输入的身份证号里出生日期不对！';
                    return false
                }
                if (dtmBirth > new Date()) {
                    param[0] = '输入的身份证号里出生日期不应大于当前日期！';
                    return false;
                }
                else {
                    //将15位身份证转成18位 //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。           
                    var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
                    var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
                    var nTemp = 0, i;
                    num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
                    for (i = 0; i < 17; i++) {
                        nTemp += num.substr(i, 1) * arrInt[i];
                    }
                    num += arrCh[nTemp % 11];
                    return true
                }
            }
            if (len == 18) {
                re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
                var arrSplit = num.match(re);
                //检查生日日期是否正确
                var tyear = arrSplit[2];
                var tmonth = arrSplit[3];
                var tday = arrSplit[4];
                var birth = tyear + "/" + tmonth + "/" + tday;
                var dtmBirth = new Date(birth);
                var bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) &&
                ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) &&
                (dtmBirth.getDate() == Number(arrSplit[4]));
                if (!bGoodDay) {
                    param[0] = ('输入的身份证号里出生日期不对！');
                    return false
                }
                if (tyear < 1900) {
                    param[0] = ('输入的身份证号里出生年份不应小于1900年！');
                    return false
                }
                if (dtmBirth > new Date()) {
                    param[0] = ('输入的身份证号里出生日期不应大于当前日期！');
                    return false
                }
                else {
                    //检验18位身份证的校验码是否正确。 
                    //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
                    var valnum;
                    var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
                    var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
                    var nTemp = 0, i;
                    for (i = 0; i < 17; i++) {
                        nTemp += num.substr(i, 1) * arrInt[i];
                    }
                    valnum = arrCh[nTemp % 11];
                    if (valnum != num.substr(17, 1)) {
                        param[0] = ('18位身份证的最后一位校验码不正确！应该为：' + valnum);
                        return false
                    }
                    return true
                }
            }
            return true;
        },
        message: "{0}"

    }
});
//string to byte
function stringToBytes(str) {
    var ch, st, re = [];
    for (var i = 0; i < str.length; i++) {
        ch = str.charCodeAt(i);
        st = [];
        do {
            st.push(ch & 0xFF);
            ch = ch >> 8;
        }
        while (ch);
        re = re.concat(st.reverse());
    }
    return re;
}