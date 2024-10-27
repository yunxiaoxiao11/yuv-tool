package com.example.yuv.tool.aspect;

import com.example.yuv.tool.annotation.EnumValid;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
public class EnumValidationAspect {
    @Pointcut("execution(* com.example..*(..)) && args(..)")
    public void pt() {}

    @Before("pt()")
    public void validateEnumField(JoinPoint joinPoint) throws IllegalAccessException {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg != null) {
                validateFields(arg);
            }
        }
    }

    private void validateFields(Object arg) throws IllegalAccessException {
        Field[] fields = arg.getClass().getDeclaredFields();
        for (Field field : fields) {
            EnumValid enumValid = field.getAnnotation(EnumValid.class);
            if (enumValid != null) {
                field.setAccessible(true);
                Object value = field.get(arg);
                if (value != null) {
                    validateEnumValue(field, enumValid, value);
                }
            }
        }
    }

    private void validateEnumValue(Field field, EnumValid enumValid, Object value) {
        String stringValue = value.toString();
        Set<String> allowedValues = getAllowedValues(field, enumValid);

        if (!allowedValues.contains(stringValue)) {
            throw new IllegalArgumentException(enumValid.message() + ": " + stringValue);
        }
    }

    private Set<String> getAllowedValues(Field field, EnumValid enumValid) {
        Set<String> allowedValues = new HashSet<>();
        Class<?> fieldType = field.getType();

        if (!fieldType.isEnum()) {
            throw new IllegalArgumentException(field.getName() + " is not an enum type");
        }

        // 优先使用手动指定的允许值
        String[] manualAllowedValues = enumValid.allowedValues();
        if (manualAllowedValues.length > 0) {
            allowedValues.addAll(Arrays.asList(manualAllowedValues));
        }

        // 获取允许的值列表
        addAllowedValuesFromMethod(fieldType, enumValid, allowedValues);
        addAllowedValuesFromFields(fieldType, enumValid, allowedValues);

        // 默认允许所有枚举值
        if (allowedValues.isEmpty()) {
            allowedValues.addAll(Arrays.stream(fieldType.getEnumConstants())
                    .map(enumConstant -> ((Enum<?>) enumConstant).name())
                    .collect(Collectors.toSet()));
        }

        return allowedValues;
    }

    private void addAllowedValuesFromMethod(Class<?> fieldType, EnumValid enumValid, Set<String> allowedValues) {
        String methodName = enumValid.allowedListMethodName();
        if (!methodName.isEmpty()) {
            try {
                // 获取方法对象并设置可访问
                Method method = fieldType.getDeclaredMethod(methodName);
                method.setAccessible(true);

                // 调用静态方法获取结果
                Object result = method.invoke(null);
                if (result instanceof List<?>) {
                    for (Object item : (List<?>) result) {
                        if (item instanceof Enum<?>) {
                            allowedValues.add(((Enum<?>) item).name());
                        }
                    }
                } else {
                    throw new IllegalArgumentException("Method must return a List of Enum");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Error invoking method: " + methodName, e);
            }
        }
    }

    private void addAllowedValuesFromFields(Class<?> fieldType, EnumValid enumValid, Set<String> allowedValues) {
        for (String allowedListField : enumValid.allowedListFieldName()) {
            try {
                Field enumField = fieldType.getDeclaredField(allowedListField);
                enumField.setAccessible(true);

                Object enumValue = enumField.get(null);
                if (enumValue instanceof Enum<?>) {
                    allowedValues.add(((Enum<?>) enumValue).name());
                } else if (enumValue instanceof List) {
                    for (Object item : (List<?>) enumValue) {
                        if (item instanceof Enum<?>) {
                            allowedValues.add(((Enum<?>) item).name());
                        }
                    }
                }
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException("Field not found: " + allowedListField, e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
