package com.boot.util;

import com.boot.system.Constant;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author chenjiang
 */
public class Md5Util {

    private static class singleMd5Util {
        public static final Md5Util Md5Util = new Md5Util();
    }

    private Md5Util() {
    }

    public static Md5Util getInstance() {
        return singleMd5Util.Md5Util;
    }

    /**
     * @return 盐值
     */
    public String getSalt() {
        String salt = Constant.SALT;
        char[] chars = salt.toCharArray();
        StringBuffer StringBuffer = new StringBuffer();
        for (int i = 1; i < 5; i++) {
            int v = (int) (Math.random() * salt.length());
            for (int j = 0; j < chars.length; j++) {
                if (v == j) {
                    StringBuffer.append(chars[j]);
                }
            }
        }
        return StringBuffer.toString();
    }

    /**
     * @param password 密码
     * @param salt     盐值
     * @return
     */
    public String MD5(String password, String salt) {
        SimpleHash SimpleHash = new SimpleHash("md5", password, salt, 1024);
        return SimpleHash.toString();
    }
}
