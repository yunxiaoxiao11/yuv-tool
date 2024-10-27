package com.example.yuv.tool.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtils {
    private static final int START_INDEX = 2;

    /**
     * 根据传入的类型和替换参数列表格式化对应的字符串模板
     * 占位符可用符号为:(! @　# $ % ^ & *)
     * 模板为:!{index} (index为序号)
     *
     * @param template 待格式化的字符串模板
     * @param placeholderType 占位符类型
     * @param replacements 代替换字符串列表
     * @return 替换后结果
     */
    public static String format(String template, String placeholderType, String... replacements) {
        String regex = getPlaceholderRegex(placeholderType);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(template);

        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String group = matcher.group();
            int order = Integer.parseInt(group.substring(START_INDEX, group.length() - 1));
            if (order < replacements.length) {
                matcher.appendReplacement(result, replacements[order]);
            } else {
                matcher.appendReplacement(result, group); // 保持原样
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }

    private static String getPlaceholderRegex(String type) {
        switch (type) {
            case "!":
                return "!\\{\\d+\\}"; // 匹配 !{0}, !{1}, ...
            case "@":
                return "@\\{\\d+\\}"; // 匹配 @{0}, @{1}, ...
            case "#":
                return "#\\{\\d+\\}"; // 匹配 #{0}, #{1}, ...
            case "$":
                return "\\$\\{\\d+\\}"; // 匹配 ${0}, ${1}, ...
            case "%":
                return "%\\{\\d+\\}"; // 匹配 %{0}, %{1}, ...
            case "^":
                return "\\^\\{\\d+\\}"; // 匹配 ^{0}, ^{1}, ...
            case "&":
                return "&\\{\\d+\\}"; // 匹配 &{0}, &{1}, ...
            case "*":
                return "\\*\\{\\d+\\}"; // 匹配 *{0}, *{1}, ...
            default:
                throw new IllegalArgumentException("Unsupported placeholder type: " + type);
        }
    }

    public static void main(String[] args) {
        String template = "today is *{2}, welcome to arrive @{1}, hello *{0}";
        String result = format(template, "*", "sunday", "cloudy", "stormy");
        System.out.println(result); // 输出: today is sunday, welcome to arrive @{1}, hello #{2}
    }
}
