package com.akira.kioku.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装给前端的分页数据信息
 * @author Kripath
 * @date Created in 21:55 2019/2/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pager {

    /** 总页数 */
    private Long count;

    /** 当前页，初始默认值为1 */
    private int currentPage;

    /** 初始的底部页码，如[1, 2, 3, 4, 5] */
    private int[] pages;
}
