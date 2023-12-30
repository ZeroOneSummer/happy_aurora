package com.aurora.happy.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.aurora.happy.annotation.Table;
import lombok.*;

/**
 * 2021-03-17
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("t_user")
public class User extends BaseRowModel {

    private Long id;

    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ExcelProperty(value = "年龄", index = 1)
    private Integer age;
}