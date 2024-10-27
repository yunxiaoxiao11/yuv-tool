package com.example.yuv.tool.utils;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtils {
    private static final int START_INDEX = 2;

    @Getter
    public enum PlaceholderType {
        EXCLAMATION("!\\{\\d+\\}"),
        AT("@\\{\\d+\\}"),
        HASH("#\\{\\d+\\}"),
        DOLLAR("\\$\\{\\d+\\}"),
        PERCENT("%\\{\\d+\\}"),
        CARET("\\^\\{\\d+\\}"),
        AMPERSAND("&\\{\\d+\\}"),
        ASTERISK("\\*\\{\\d+\\}");

        private final String regex;

        PlaceholderType(String regex) {
            this.regex = regex;
        }
    }

    /**
     * 根据传入的类型和替换参数列表格式化对应的字符串模板
     * 占位符可用符号为:(! @　# $ % ^ & *)
     * 模板为:!{index} (index为序号)
     *
     * @param template        待格式化的字符串模板
     * @param placeholderType 占位符类型
     * @param replacements    代替换字符串列表
     * @return 替换后结果
     */
    public static String format(String template, PlaceholderType placeholderType, String... replacements) {
        if (StrUtil.isBlank(template) || placeholderType == null) {
            return template;
        }
        Pattern pattern = Pattern.compile(placeholderType.getRegex());
        Matcher matcher = pattern.matcher(template);

        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String group = matcher.group();
            int order = Integer.parseInt(group.substring(START_INDEX, group.length() - 1));
            if (order < 0) {
                continue;
            }
            if (order < replacements.length) {
                matcher.appendReplacement(result, replacements[order]);
            } else {
                matcher.appendReplacement(result, group); // 保持原样
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }

    public static void main(String[] args) {
        String template = "today is *{2}, welcome to arrive @{1}, hello *{0}";
        String result = format(template, PlaceholderType.DOLLAR, "sunday", "cloudy", "stormy");
        System.out.println(result); // 输出: today is sunday, welcome to arrive @{1}, hello #{2}
    }
}
