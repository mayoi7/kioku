package com.akira.kioku.constant;

import org.springframework.beans.factory.annotation.Value;

/**
 * 网站的一些常量
 * @author Kripath
 * @date Created in 18:30 2019/2/21
 */
public class WebConstant {

    @Value("${website.host.name}")
    public static String HOSTNAME;
}
