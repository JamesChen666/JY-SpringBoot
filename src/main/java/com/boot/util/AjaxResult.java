package com.boot.util;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 返回结果
 * 		状态码
 * 		信息
 * 		数据
 * @author chenjiang
 *
 */
public class AjaxResult implements Serializable {

	//消息提醒
	public static final String SUCCESS = "操作成功";
	public static final String FAIL = "操作失败";
	public static final String ERROR = "操作错误";
	
	private boolean flag;
	private String msg;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public AjaxResult success(String msg) {
		this.flag = true;
		this.msg = msg;
		return this;
	}

	public AjaxResult fail(String msg) {
		this.flag = false;
		this.msg = msg;
		return this;
	}
	public AjaxResult error(String msg) {
		this.flag = false;
		this.msg = msg;
		return this;
	}

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
