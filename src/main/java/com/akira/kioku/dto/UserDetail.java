package com.akira.kioku.dto;

import com.akira.kioku.po.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 封装用户的详细信息
 * @author Kripath
 * @date Created in 22:09 2019/2/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail implements Serializable {

    /** 主键 */
    private Long id;

    /** 用户名 */
    private String username;

    /** 用户权限代码 */
    private Integer role;

    /** 用户创建的日记总数 */
    private Long noteCount;

    /** 用户注册时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date regDate;

    /** 注册时使用的邀请码 */
    private String code;

    public UserDetail(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.regDate = user.getGmtCreate();
        this.code = user.getCode();
    }
}
