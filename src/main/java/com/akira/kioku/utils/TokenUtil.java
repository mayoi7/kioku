package com.akira.kioku.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 生成加密凭证
 * @author Kripath
 * @date Created in 19:46 2019/2/2
 */
public class TokenUtil {
    /**
     * 加密方式
     */
    private final static String HASH_ALGORITHM = "md5";

    /**
     * 散列次数
     */
    private final static int HASH_TIMES = 1;

    /**
     * 进行凭证的加密
     * @param credential 待加密的凭证
     * @param salt 盐值
     * @return 返回加密后的对象
     */
    public static SimpleHash makeTEncryptTokenBySaltedMd5(String credential, String salt) {
        return new SimpleHash(HASH_ALGORITHM, credential, salt, HASH_TIMES);
    }
}
