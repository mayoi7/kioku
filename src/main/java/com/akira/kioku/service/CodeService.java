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
     * @param code 邀请码
     * @return 查询到的邀请码，如果为空表示不存在这样的邀请码，如果其uid属性不为空表示已被使用
     */
    Code findByCode(String code);

    /**
     * 给邀请码设置使用者（表明已被使用）
     * @param code 邀请码
     * @param uid 使用该邀请码的用户
     */
    void setUsed(String code, Long uid);
}
