package com.aurora.happy.common;

import com.aurora.happy.enums.ExceptionCodeEnum;
import com.github.pagehelper.Page;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {

    private Integer code;
    private String message;
    private List<T> data;

    private Integer page;
    private Integer pageSize;
    private Long total;

    private PageResult(List<T> data, Integer page, Integer pageSize, Long total) {
        this.code = ExceptionCodeEnum.SUCCESS.getCode();
        this.message = ExceptionCodeEnum.SUCCESS.getDesc();
        this.data = data;
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
    }

    /**
     * 成功实体，传入一个page对象
     */
    public static <T> PageResult<T> success(Page<T> page) {
        if (page != null) {
            return new PageResult<>(page.getResult(), page.getPageNum(), page.getPageSize(), page.getTotal());
        } else {
            return new PageResult<>(new ArrayList<>(), 0, 1, 1L);
        }
    }
}