package com.aurora.happy.utils;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class EnumUtil {
    private static final Map<Object, Object> key2EnumMap = new ConcurrentHashMap<>();
    private static final Set<Class> enumSet = ConcurrentHashMap.newKeySet();

    /**
     * 获取枚举（带缓存）
     */
    public static <T extends java.lang.Enum<T>, R> Optional<T> getEnumWithCache(Class<T> enumType,
                                           Function<T, R> enumToValueMapper, Object key) {
        if (!enumSet.contains(enumType)) {
            // 不同的枚举类型互相不影响
            synchronized (enumType) {
                if (!enumSet.contains(enumType)) {
                    // 添加枚举
                    enumSet.add(enumType);
                    // 缓存枚举键值对
                    for (T enumConstant : enumType.getEnumConstants()) {
                        // enumToValueMapper.apply()表示从枚举中获得value。但不同枚举可能value相同，因此getKe()进一步加工，为value拼接路径做前缀
                        String mapKey = getKey(enumType, enumToValueMapper.apply(enumConstant));
                        key2EnumMap.put(mapKey, enumConstant);
                    }
                }
            }
        }

        return Optional.ofNullable((T) key2EnumMap.get(getKey(enumType, key)));
    }


    /**
     * 获取key
     * 注：带上枚举类名作为前缀，避免不同枚举的Key重复
     */
    public static <T extends java.lang.Enum<T>, R> String getKey(Class<T> enumType, R key) {
        return enumType.getName().concat(key.toString());
    }

    /**
     * 获取枚举（不缓存）
     */
    public static <T extends java.lang.Enum<T>, R> Optional<T> getEnum(Class<T> enumType,
                                  Function<T, R> enumToValueMapper, Object key) {
        for (T enumThis : enumType.getEnumConstants()) {
            if (enumToValueMapper.apply(enumThis).equals(key)) {
                return Optional.of(enumThis);
            }
        }
        return Optional.empty();
    }
}