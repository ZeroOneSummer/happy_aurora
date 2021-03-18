package com.aurora.happy.test;

import lombok.Getter;

/**
 * 伪枚举
 */
@Getter
public class WeekDay {
    public static final WeekDay MONDAY;
    public static final WeekDay TUESDAY;
    public static final WeekDay WEDNESDAY;
    public static final WeekDay THURSDAY;
    public static final WeekDay FRIDAY;
    public static final WeekDay SATURDAY;
    public static final WeekDay SUNDAY;
    private static final WeekDay[] VALUES;

    private WeekDay(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    static {
        MONDAY = new WeekDay(1, "星期一");
        TUESDAY = new WeekDay(2, "星期二");
        WEDNESDAY = new WeekDay(3, "星期三");
        THURSDAY = new WeekDay(4, "星期四");
        FRIDAY = new WeekDay(5, "星期五");
        SATURDAY = new WeekDay(6, "星期六");
        SUNDAY = new WeekDay(7, "星期日");
        VALUES = new WeekDay[]{MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};
    }

    private final Integer code;
    private final String desc;

    // 返回所有的对象
    public static WeekDay[] values() {
        return VALUES;
    }

    // 遍历对象，根据code返回code对应的desc
    public static String getDescByCode(Integer code) {
        WeekDay[] weekDays = WeekDay.values();
        for (WeekDay weekDay : weekDays) {
            if (weekDay.getCode().equals(code)) {
                return weekDay.getDesc();
            }
        }
        throw new IllegalArgumentException("Invalid Enum code:" + code);
    }


    public static void main(String[] args) {
        System.out.println(WeekDay.MONDAY.getCode());
        System.out.println(WeekDay.MONDAY.getDesc());
        System.out.println(WeekDay.getDescByCode(MONDAY.getCode()));
    }
}