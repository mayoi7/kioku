package com.akira.kioku.vo;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应结果
 * @author Kripath
 * @date Created in 11:49 2019/2/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo {

    private Integer code;

    private String msg;

    private Object data;
}