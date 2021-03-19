package com.aurora.happy.enums;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by pijiang on 2021/3/19.
 * 枚举的抽象方法
 */
@Getter
public enum VipEnum {

    //子类重写父类抽象方法
    GOLD(1, "黄金"){
        @Override
        protected BigDecimal toCalculateVip(BigDecimal originPrice) {
            return originPrice.multiply(BigDecimal.valueOf(0.8));
        }
    },
    SILVER(2, "白银"){
        @Override
        protected BigDecimal toCalculateVip(BigDecimal originPrice) {
            return originPrice.multiply(BigDecimal.valueOf(0.9));
        }
    },
    IRON(3, "黄铁"){
        @Override
        protected BigDecimal toCalculateVip(BigDecimal originPrice) {
            return originPrice.multiply(BigDecimal.valueOf(0.95));
        }
    }
    ;

    private Integer type;
    private String desc;

    VipEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    //抽象方法
    protected abstract BigDecimal toCalculateVip(BigDecimal originPrice);

    //根据会员类型计算折扣
    private static BigDecimal getPrice(Integer type, BigDecimal originPrice){
        VipEnum vip = Arrays.stream(VipEnum.values())
                .filter(v -> type.equals(v.getType()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("无效会员类型"));
        return vip.toCalculateVip(originPrice);
    }




    public static void main(String[] args) {
        //商品原价
        BigDecimal goodsPirce = BigDecimal.valueOf(1000);
        //黄金会员价
        BigDecimal goldPrice = VipEnum.getPrice(1, goodsPirce);
        //白银会员价
        BigDecimal silverPrice = VipEnum.getPrice(2, goodsPirce);

        System.out.println("黄金会员价: " + goldPrice.toPlainString());
        System.out.println("白银会员价: " + silverPrice.toPlainString());
    }
}