package com.daqsoft.commonnanning.utils.annotation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检验数据，不合格弹出相应的提示
 *
 * @author MouJunFeng
 * @time 2018-4-8.
 * @version 1.0.0
 * @since JDK 1.8
 */

@SuppressLint("NewApi")
public class ViewToastInject {
    private static final HashSet<Class<?>> IGNORED = new HashSet<Class<?>>();

    static {
        IGNORED.add(Object.class);
        IGNORED.add(Activity.class);
        IGNORED.add(android.app.Fragment.class);
        try {
            IGNORED.add(Class.forName("android.support.v4.app.Fragment"));
            IGNORED.add(Class.forName("android.support.v4.app.FragmentActivity"));
        } catch (Throwable ignored) {
        }
    }

    public static boolean check(View view) {
        return checkValue(view, view.getClass(), new ViewFinder(view));
    }

    public static boolean check(Activity activity) {
        return checkValue(activity, activity.getClass(), new ViewFinder(activity));
    }

    public static boolean check(Object handler, View view) {
        return checkValue(handler, handler.getClass(), new ViewFinder(view));
    }

    @SuppressWarnings("ConstantConditions")
    private static boolean checkValue(Object handler, Class<?> handlerType, ViewFinder finder) {
        int order = 0;
        if (handlerType == null || IGNORED.contains(handlerType)) {
            return false;
        }
//        checkValue(handler, handlerType.getSuperclass(), finder);
        Field[] fields = handlerType.getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            if (
                    Modifier.isStatic(field.getModifiers()) ||
                            Modifier.isFinal(field.getModifiers()) ||
                            fieldType.isPrimitive() ||
                            fieldType.isArray()) {
                continue;
            }
            ViewToast inject = getViewToast(fields, order);
            if (inject != null) {
                View view = finder.findViewById(inject.id(), 0);
                String value = ((TextView) view).getText().toString();
                if (view instanceof TextView) {
                    boolean hasValue = hasValue(view, inject);
                    boolean isMacher = true;
                    boolean methodMatcher = true;
                    if (hasValue) {
                        isMacher = isMatcher(view, inject, value);
                        if (isMacher) {
                            methodMatcher = methodMacther(view, inject);
                        }
                    }
                    if (hasValue && isMacher && methodMatcher) {
                        order++;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public static ViewToast getViewToast(Field[] fields, int order) {
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            if (
                    Modifier.isStatic(field.getModifiers()) ||
                            Modifier.isFinal(field.getModifiers()) ||
                            fieldType.isPrimitive() ||
                            fieldType.isArray()) {
                continue;
            }
            ViewToast viewInject = field.getAnnotation(ViewToast.class);
            if (viewInject != null) {
                int tempOrder = viewInject.order();
                if (tempOrder == order) {
                    return viewInject;
                }
            }
        }
        return null;
    }


    private static boolean hasValue(View view, ViewToast viewInject) {
        String value = ((TextView) view).getText().toString();
        if (TextUtils.isEmpty(value)) {
            String empty = viewInject.emptyToast();
            Toast.makeText(view.getContext(), empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean isMatcher(View view, ViewToast viewInject, String value) {
        boolean isMatcher = true;
        if (viewInject.regex() != null) {
            isMatcher = false;
            for (String regex : viewInject.regex()) {
                if (regex.equals("")) {
                    isMatcher = true;
                    break;
                }
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(value);
                if (matcher.matches()) {
                    isMatcher = true;
                    break;
                }
            }
            if (!isMatcher) {
                String toast = viewInject.regexToast();
                Toast.makeText(view.getContext(), toast, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return isMatcher;
    }


    @SuppressWarnings("unchecked")
    public static boolean methodMacther(View view, ViewToast viewInject) {
        String value = ((TextView) view).getText().toString();
        boolean isMatcher = true;
        if (viewInject.method() != null && !TextUtils.isEmpty(viewInject.method())) {
            String method = viewInject.method();
            String classPath = method.substring(0, method.lastIndexOf("."));
            String methodValue = method.substring(method.lastIndexOf(".") + 1);
            try {
                Class clazz = Class.forName(classPath);
                Method methodResult = clazz.getDeclaredMethod(methodValue, String.class);
                isMatcher = (Boolean) methodResult.invoke(clazz, value);
                if (!isMatcher) {
                    Toast.makeText(view.getContext(), viewInject.methodToast(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return isMatcher;
            }
        }
        return isMatcher;
    }
}
