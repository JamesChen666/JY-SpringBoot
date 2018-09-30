package com.boot.util;

import com.boot.model.Loginaccount;
import com.boot.model.Role;
import org.apache.shiro.SecurityUtils;

public class ShiroUtils {

    private ShiroUtils() {
    }

    private static class getShiroUtils{
        public static ShiroUtils shiroUtils= new ShiroUtils();
    }

    public static ShiroUtils getInstence(){return getShiroUtils.shiroUtils;}

    public Loginaccount getUser(){
        Loginaccount user = (Loginaccount)SecurityUtils.getSubject().getSession().getAttribute("User");
        return   user;
    }

    public  Role getRole(){
        Role role = (Role)SecurityUtils.getSubject().getSession().getAttribute("Role");
        return   role;
    }

}
