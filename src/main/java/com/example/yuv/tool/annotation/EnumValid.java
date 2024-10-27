package com.example.yuv.tool.annotation;

import java.lang.annotation.*;

/*
 * 对枚举类型字段校验
 * 对不同场景下枚举值可用范围进行约束
 * 注解提供三种方式进行验证
 * 1. 允许字符串列表，手动指定{"xx","yy"}
 * 2. 允许值字段，字段类型为允许值集合"fieldName"
 * 2. 允许值方法，方法返回值为允许值集合"methodName"
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumValid {
    /**
     * 允许的枚举值列表
     *
     * @return 字符串列表
     */
    String[] allowedValues() default {};

    /**
     * 获取允许值列表字段
     *
     * @return 字符串列表
     */
    String[] allowedListFieldName() default {};

    /**
     * 获取允许值列表的方法
     *
     * @return 字符串
     */
    String allowedListMethodName() default "";

    /**
     * 验证失败消息
     *
     * @return 失败消息
     */
    String message() default "Invalid value";
}
