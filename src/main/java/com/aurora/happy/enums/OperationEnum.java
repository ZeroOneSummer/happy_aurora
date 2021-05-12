package com.aurora.happy.enums;

import lombok.Getter;

/**
 * 操作类型枚举
 */
@Getter
public enum OperationEnum {

    /**
     * 新建
     */
    ADD(1, "新建"),

    /**
     * 修改
     */
    MODIFY(2, "修改"),

    /**
     * 删除
     */
    DELETE(3, "删除"),

    /**
     * 导入
     */
    IMPORT(4, "导入"),

    /**
     * 导出
     */
    EXPORT(5, "导出");

    private final Integer value;
    private final String operationType;

    OperationEnum(Integer value, String operationType) {
        this.value = value;
        this.operationType = operationType;
    }
}