package com.ossean.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by houxiang on 15/9/12.
 */
public class Configuration {
    private static ApplicationContext aContext;
    static {
        aContext = new ClassPathXmlApplicationContext(
                "classpath:/spring/applicationContext*.xml");
    }

    public static ApplicationContext getConfig(){
        return aContext;
    }
}
