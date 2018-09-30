package com.boot.system;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.Properties;
/**
 * @author chenjiang
 */
@Configuration
@Lazy
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        com.google.code.kaptcha.impl.DefaultKaptcha defaultKaptcha = new com.google.code.kaptcha.impl.DefaultKaptcha();
        Properties properties = new Properties();
        //边框
        properties.setProperty("kaptcha.border", "yes");
        //边框颜色
        properties.setProperty("kaptcha.border.color", "black");
        //背景色从。。。到
        properties.setProperty("kaptcha.background.clear.from", "white");
        properties.setProperty("kaptcha.background.clear.to", "yellow");
        //图片尺寸
        properties.setProperty("kaptcha.image.width", "110");
        properties.setProperty("kaptcha.image.height", "40");
        //验证码取值
        properties.setProperty("kaptcha.textproducer.char.string", "1234567890");
        //字体尺寸
        properties.setProperty("kaptcha.textproducer.font.size", "20");
        //字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        //验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        //验证码字体
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        //干扰色
        properties.setProperty("kaptcha.noise.color", "green");
        /*
        干扰样式
        水纹com.google.code.kaptcha.impl.WaterRipple
        鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
        阴影com.google.code.kaptcha.impl.ShadowGimpy
        */
        properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
        //session key
        properties.setProperty("kaptcha.session.key", "code");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }
}
