package com.aurora.happy.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.aurora.happy.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 2021-03-17
 */
@Table("t_jpa_user")
@Data
@AllArgsConstructor
public class User extends BaseRowModel {

    private Long id;

    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ExcelProperty(value = "年龄", index = 1)
    private Integer age;
}