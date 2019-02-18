package com.akira.kioku.utils;

import java.util.*;

/**
 * @author Kripath
 * @date Created in 20:01 2019/2/16
 */
public class CodeUtil {

    /** 邀请码位数 */
    private static final int CODE_NUM = 6;

    /** 随机字符池 */
    private static final String CHAR_POOL
            = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";

    /** 随机数类 */
    private static final Random RANDOM = new Random();

    /**
     * 生成一个随机的邀请码
     * @return 一个6位随机邀请码
     */
    private static String makeRandomCode() {
        StringBuilder str = new StringBuilder();
        int len =CHAR_POOL.length();

        for (int i = 0; i < CODE_NUM; i++) {
            str.append(CHAR_POOL.charAt(RANDOM.nextInt(len)));
        }
        return str.toString();
    }

    /**
     * 生成指定数量的随机邀请码
     * @param num 要生成的邀请码的数量
     * @return 字符串集合
     */
    public static List<String> makeSomeRandomCodes(int num) {
        // 为了保证不重复使用HashSet
        List<String> list = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            list.add(makeRandomCode());
        }
        return list;
    }
}
