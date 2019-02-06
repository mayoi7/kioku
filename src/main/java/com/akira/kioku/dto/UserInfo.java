package com.akira.kioku.dto;

import lombok.Data;

/**
 * 给前端展示的用户信息
 * @author Kripath
 * @date Created in 10:19 2019/2/6
 */
@Data
public class UserInfo {

    private Long id;

    private String username;

    private String role;
}
