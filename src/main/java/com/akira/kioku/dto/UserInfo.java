package com.akira.kioku.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 给前端展示的用户信息
 * @author Kripath
 * @date Created in 10:19 2019/2/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private Long id;

    private String username;

    private String role;
}
