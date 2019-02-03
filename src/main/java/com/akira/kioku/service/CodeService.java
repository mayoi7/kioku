package com.akira.kioku.service;

import com.akira.kioku.po.Code;

/**
 * 关于邀请码的检验相关的方法
 * @author Kripath
 * @date Created in 11:06 2019/2/3
 */
public interface CodeService {

    /**
     * 查询指定邀请码
     * @param code
     * @return
     */
    Code findByCode(String code);
}
