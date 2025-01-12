package io.github.pigaut.voxel.util;

import org.jetbrains.annotations.*;

import java.lang.reflect.*;

public class ReflectionUtil {

    @Nullable
    public static Field getDeclaredField(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException | SecurityException e) {
            return null;
        }
    }

    @Nullable
    public static Object accessField(Field field, Object instance) {
        if (field == null) return null;
        field.setAccessible(true);
        try {
            return field.get(instance);
        } catch (IllegalAccessException | NullPointerException | IllegalArgumentException e) {
            return null;
        }
    }

    @Nullable
    public static <T> T accessField(Field field, Class<T> type, Object instance) {
        if (field == null || type == null) return null;
        field.setAccessible(true);
        try {
            return type.cast(field.get(instance));
        } catch (IllegalAccessException | NullPointerException | IllegalArgumentException | ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public static Method getMethod(Class<?> clazz, String name) {
        try {
            return clazz.getMethod(name);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    @Nullable
    public static Parameter getMethodParameter(Method method, String parameterName) {
        for (Parameter parameter : method.getParameters()) {
            if (parameter.getName().equalsIgnoreCase(parameterName))
                return parameter;
        }
        return null;
    }

    @Nullable
    public static Integer getMethodParameterIndex(Method method, String parameterName) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getName().equalsIgnoreCase(parameterName))
                return i;
        }
        return null;
    }

    @Nullable
    public static Class<?> getInnerClass(Class<?> clazz, String name) {
        for (Class<?> innerClass : clazz.getDeclaredClasses()) {
            if (innerClass.getSimpleName().equals(name)) {
                return innerClass;
            }
        }
        return null;
    }

}
