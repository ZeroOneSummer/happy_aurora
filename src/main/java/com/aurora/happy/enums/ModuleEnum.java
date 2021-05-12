package com.aurora.happy.enums;

import lombok.Getter;

/**
 * 系统模块枚举类
 * 可以根据自己系统的实际情况增添模块
 */
@Getter
public enum ModuleEnum {
    /**
     * 课程
     */
    COURSE("课程"),

    /**
     * 用户
     */
    USER("用户"),

    /**
     * 消息
     */
    MESSAGE("消息");

    private final String moduleCode;

    ModuleEnum(String moduleCode) {
        this.moduleCode = moduleCode;
    }
}