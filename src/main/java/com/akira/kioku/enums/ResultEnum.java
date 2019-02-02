package com.akira.kioku.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Kripath
 * @date Created in 10:06 2019/2/2
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {
    /**
     * 请求成功的返回码
     */
    SUCCESS(0, "请求成功"),
    /**
     * 请求失败的返回码，可能是参数异常导致
     */
    ERROR(1, "请求失败");

    private final Integer code;
    private final String msg;
}
