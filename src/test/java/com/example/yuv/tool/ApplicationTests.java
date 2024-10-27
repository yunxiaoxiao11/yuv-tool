package com.example.yuv.tool;

import com.example.yuv.tool.functional.VUtils;
import com.example.yuv.tool.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void test01() {
        VUtils.isTure(true)
                .throwMessage("参数为True，抛出异常");
    }

    @Test
    public void test02() {
        VUtils.isTureOrFalse(false)
                .trueOrFalseHandle(() -> System.out.println("true, 🔑"), () -> System.out.println("false, 🔒"));
    }

    @Test
    public void test03() {
        VUtils.isBlankOrNoBlank("  ")
                .presentOrElseHandle(System.out::println, () -> System.out.println("这是空字符串"));
    }

    @Test
    public void test04() {
        TaskStatus failed = TaskStatus.FAILED;
        System.out.println(failed);
    }

}
