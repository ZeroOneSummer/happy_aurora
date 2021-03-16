package com.aurora.happy.contanst;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum ExceptionCodeEnum {

    ERROR(-1, "网络错误"),
    NO_PERMISSION(0, "无操作权限"),
    SUCCESS(200, "成功"),
    ;

    private final Integer code;
    private final String desc;

    ExceptionCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static final Map<Integer, ExceptionCodeEnum> cache;

    static {
        cache = Arrays.stream(ExceptionCodeEnum.values()).collect(Collectors.toMap(ExceptionCodeEnum::getCode, v -> v));
    }

    public static String getDesc(Integer code) {
        ExceptionCodeEnum exceptionCodeEnum = cache.get(code);
        return exceptionCodeEnum != null ? exceptionCodeEnum.getDesc() : "";
    }
}