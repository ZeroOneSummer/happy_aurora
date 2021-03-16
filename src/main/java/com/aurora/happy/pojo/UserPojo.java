package com.aurora.happy.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ApiModel("用户POJO")
public class UserPojo extends BasePojo {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "ID", dataType = "Long", required = true)
    private Long id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", dataType = "String")
    private String name;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄", dataType = "Integer")
    private Integer age;

    /**
     * 用户类型
     */
    private Integer userType;

    @Override
    public String toString() {
        return "UserPojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", userType=" + userType +
                '}';
    }
}