package com.aurora.happy.pojo;

import com.aurora.happy.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 2021-03-17
 */
@Table("t_jpa_user")
@Data
@AllArgsConstructor
public class User {
    private String name;
    private Integer age;
}