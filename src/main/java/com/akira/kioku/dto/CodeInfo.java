package com.akira.kioku.dto;

import com.akira.kioku.po.Code;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 邀请码的信息
 * @author Kripath
 * @date Created in 19:01 2019/2/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeInfo {

    /** 编号 */
    private Long id;

    /** 6位邀请码 */
    private String code;

    /** 使用者用户名，如果没有被使用则为null */
    private String user;

    /** 使用时间，如果没有被使用则为null */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;

    /**
     * 从新建的Code创建对象
     * @param code 新建的Code
     */
    public CodeInfo(Code code) {
        this.id = code.getId();
        this.code = code.getCode();

        user = null;
        date = null;
    }
}
