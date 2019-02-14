package com.akira.kioku.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 封装用户的详细信息
 * @author Kripath
 * @date Created in 22:09 2019/2/13
 */
@Data
public class UserDetail {

    /** 主键 */
    private Long id;

    /** 用户名 */
    private String username;

    /** 用户权限代码 */
    private Integer role;

    /** 用户创建的日记总数 */
    private Long noteCount;

    /** 用户注册时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date regDate;

    /** 注册时使用的邀请码 */
    private String code;
}
