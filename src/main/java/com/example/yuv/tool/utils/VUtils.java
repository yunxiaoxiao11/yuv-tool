package com.example.yuv.tool.utils;


import cn.hutool.core.util.StrUtil;
import com.example.yuv.tool.functional.BranchHandle;
import com.example.yuv.tool.functional.PresentOrElseHandler;
import com.example.yuv.tool.functional.ThrowExceptionFunction;

public class VUtils {
    /**
     * 如果参数为true抛出异常
     *
     * @param b
     * @return com.example.demo.func.ThrowExceptionFunction
     **/
    public static ThrowExceptionFunction isTure(boolean b) {
        return (errorMessage) -> {
            if (b) {
                throw new RuntimeException(errorMessage);
            }
        };
    }

    /**
     * 参数为true或false时，分别进行不同的操作
     *
     * @param b
     * @return com.example.demo.func.BranchHandle
     **/
    public static BranchHandle isTureOrFalse(boolean b) {
        return (trueHandle, falseHandle) -> {
            if (b) {
                trueHandle.run();
            } else {
                falseHandle.run();
            }
        };
    }

    /**
     * 参数为true或false时，分别进行不同的操作
     *
     * @param str
     * @return com.example.demo.func.BranchHandle
     **/
    public static PresentOrElseHandler<?> isBlankOrNoBlank(String str) {
        return (consumer, runnable) -> {
            if (StrUtil.isBlank(str)) {
                runnable.run();
            } else {
                consumer.accept(str);
            }
        };
    }
}
