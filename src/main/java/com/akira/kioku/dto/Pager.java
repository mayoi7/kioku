package com.akira.kioku.dto;

import lombok.Data;

/**
 * 封装给前端的分页数据信息
 * @author Kripath
 * @date Created in 21:55 2019/2/13
 */
@Data
public class Pager {

    /** 总页数 */
    private Long count;

    /** 当前页 */
    private Long currentPage = 1L;

    /** 初始的底部页码，如[1, 2, 3, 4, 5] */
    private int[] pages;
}
