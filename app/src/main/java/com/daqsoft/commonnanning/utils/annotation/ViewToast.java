package com.daqsoft.commonnanning.utils.annotation;

/**
 * 数据审核判断
 *
 * @author MouJunFeng
 * @time 2018-4-8.
 * @version 1.0.0
 * @since JDK 1.8
 */

public @interface ViewToast {
    int id();

    String emptyToast() default "";

    String[] regex() default "";

    String regexToast() default "";

    String method() default "";

    String methodToast() default "";

    int order();
}
