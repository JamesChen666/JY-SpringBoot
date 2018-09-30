package com.boot.system;

import com.boot.controller.system.BaseController;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author chenjiang
 */
@WebListener
public class LogListener extends BaseController implements ServletRequestListener{

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        getHttpServletRequest(servletRequestEvent);
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        getHttpServletRequest(servletRequestEvent);

    }

    private void getHttpServletRequest(ServletRequestEvent servletRequestEvent) {
        ServletRequest servletRequest = servletRequestEvent.getServletRequest();
        try {
            servletRequest.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpServletRequest HttpServletRequest = (javax.servlet.http.HttpServletRequest) servletRequest;
        System.out.println("请求路径"+HttpServletRequest.getServletPath());
    }
}
