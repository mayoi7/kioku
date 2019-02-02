package com.akira.kioku.enums;

/**
 * 用于其他的枚举类，使其有用于工具类遍历的getCode方法
 * @author Kripath
 * @date Created in 9:58 2019/2/2
 */
public interface CodeEnum {

    /**
     * 获取code值，用于EnumUtil遍历
     * @return
     */
    Integer getCode();
}
