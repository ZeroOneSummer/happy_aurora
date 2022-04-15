package com.aurora.happy.interceptor;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 布隆过滤器：hash函数+bit数组。绝对不存在、相对存在。解决缓存穿透、黑名单拦截等问题
 */
public class BloomFilterDemo {
    public static void main(String[] args) {
        BloomFilter<CharSequence> bloomFilter
                = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 20_0000, 1E-7);
        bloomFilter.put("test");
        boolean isExsist = bloomFilter.mightContain("test");
        System.out.println(isExsist);
    }
}
