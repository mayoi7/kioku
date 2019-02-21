package com.akira.kioku.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于超时验证的对象
 * @author Kripath
 * @date Created in 20:52 2019/2/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpireVerify {

    /** 凭证到期时间 */
    private Long expireDate;

    /** 密钥（随机字符串） */
    private String secretKey;
}
