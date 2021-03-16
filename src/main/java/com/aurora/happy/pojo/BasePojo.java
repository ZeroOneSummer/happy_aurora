package com.aurora.happy.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BasePojo {

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Boolean deleted;
}