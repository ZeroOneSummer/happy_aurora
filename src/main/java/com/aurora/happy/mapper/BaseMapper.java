package com.aurora.happy.mapper;

import com.aurora.happy.annotation.Table;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pijiang on 2021/3/17.
 */
public class BaseMapper<T> {

    private static BasicDataSource datasource;

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

    private Class<T> beanClass;

    static {
        datasource = new BasicDataSource();
        datasource.setDriverClassName("com.mysql.jdbc.Driver");
        datasource.setUrl("jdbc:mysql://localhost:3306/dimai");
        datasource.setUsername("root");
        datasource.setPassword("root");
    }

    /**
     * this -> 子类Class -> 父类Class -> 父类泛型参数(子类实际泛型参数T) -> 子类泛型T.Class
     */
    public BaseMapper() {
        //子类Class
        Class<? extends BaseMapper> aClass = this.getClass();
        //父类Class
        Type sclass = aClass.getGenericSuperclass();
        //强转成参数类型
        ParameterizedType pType = (ParameterizedType) sclass;
        //获取父类泛型参数的第一个，即子类Class泛型T的Class
        beanClass = (Class<T>) pType.getActualTypeArguments()[0];
    }

    public int add(T bean) {
        //获取子类的所有字段
        Field[] declaredFields = beanClass.getDeclaredFields();  //age、name

        //拼接sql语句  insert into t_jpa_user values(?,?)
        StringBuilder sql = new StringBuilder()
                .append("insert into ")
                .append(beanClass.getAnnotation(Table.class).value())   //没注解时，beanClass.getSimpleName()
                .append(" values(");

        for (int i = 0; i < declaredFields.length; i++) {
            sql.append("?");
            if (i < declaredFields.length - 1) {
                sql.append(",");
            }
        }
        sql.append(")");

        // 获得bean字段的值（要插入的记录）
        List<Object> paramList = new ArrayList<>();
        try {
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);   //允许访问 private
                Object o = declaredField.get(bean);  //获取bean对应的属性值
                paramList.add(o);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int size = paramList.size();
        Object[] params = paramList.toArray(new Object[size]);  //参数数组

        // 执行sql
        int num = jdbcTemplate.update(sql.toString(), params);
        System.out.println(num);
        return num;
    }
}