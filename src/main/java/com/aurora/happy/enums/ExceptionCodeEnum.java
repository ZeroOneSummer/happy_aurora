package com.aurora.happy.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public enum ExceptionCodeEnum {

    /**
     * 通用结果
     */
    ERROR(-1, "网络错误"),
    NO_PERMISSION(0, "无操作权限"),
    SUCCESS(200, "成功"),

    /**
     * 用户登录
     */
    NEED_LOGIN(900, "用户未登录"),

    /**
     * 参数校验
     */
    ERROR_PARAM(10000, "参数错误"),
    EMPTY_PARAM(10001, "参数为空"),
    ERROR_PARAM_LENGTH(10002, "参数长度错误");
    ;

    private final Integer code;
    private final String desc;

    ExceptionCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static final Map<Integer, ExceptionCodeEnum> ENUM_CACHE;

    static {
        ENUM_CACHE = Arrays.stream(ExceptionCodeEnum.values()).collect(Collectors.toMap(ExceptionCodeEnum::getCode, v -> v));
    }

    public static String getDesc(Integer code) {
        return Optional.ofNullable(ENUM_CACHE.get(code))
                .map(ExceptionCodeEnum::getDesc)
                .orElseThrow(() -> new IllegalArgumentException("invalid exception code!"));
    }
}