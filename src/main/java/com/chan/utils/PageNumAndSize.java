package com.chan.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Chan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageNumAndSize implements Serializable {

    private int pageNum;
    private int pageSize;

    /**
     * ElasticSearch专用分页工具类
     */
    public static PageNumAndSize checkPage0(int pageNum, int pageSize) {
        return PageNumAndSize.builder()
                .pageNum(pageNum == 1 ? 0 : (pageNum - 1) * pageSize)
                .pageSize(pageSize == 0 ? 20 : pageSize)
                .build();
    }

    /**
     * 数组专用分页工具类
     */
    public static PageNumAndSize checkPage1(int pageNum, int pageSize) {
        return PageNumAndSize.builder()
                .pageNum(pageNum == 0 ? 1 : pageNum)
                .pageSize(pageSize == 0 ? 20 : pageSize)
                .build();
    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);

        System.out.println(Long.MAX_VALUE);
        System.out.println(Long.MIN_VALUE);
    }

}
