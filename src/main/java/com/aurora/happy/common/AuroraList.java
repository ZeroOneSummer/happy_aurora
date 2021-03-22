package com.aurora.happy.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by pijiang on 2021/3/22.
 */
public class AuroraList<T> {

    private List<T> list = new ArrayList<>();

    public boolean add(T t){
        return list.add(t);
    }

    public AuroraList<T> filter(Predicate<T> predicate){
        AuroraList<T> auroraList = new AuroraList<>();
        for (T t : list) {
            if (predicate.test(t)){
                auroraList.add(t);
            }
        }
        return auroraList;
    }

    public <R> AuroraList<R> map(Function<T, R> function){
        AuroraList<R> auroraList = new AuroraList<>();
        for (T t : list) {
            auroraList.add(function.apply(t));
        }
        return auroraList;
    }
}