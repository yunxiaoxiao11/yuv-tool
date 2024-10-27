package com.example.yuv.tool.model;


import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public enum TaskStatus {
    INIT("init"),
    RUNNING("running"),
    PENDING("pending"),
    STOPPED("stopped"),
    FAILED("failed"),
    SUCCEEDED("succeeded"),
    CANCELLED("cancelled"),
    PAUSED("paused");

    private final String name;

    TaskStatus(String name) {
        this.name = name;
    }

    private static final List<TaskStatus> COMPLETED_FIELD =
            Collections.unmodifiableList(Arrays.asList(SUCCEEDED, STOPPED, FAILED, CANCELLED, PAUSED));

    // 获取进行中状态的列表
    private static List<TaskStatus> ProgressStatuses() {
        return Arrays.asList(PENDING, RUNNING);
    }

    // 获取完成状态的列表
    private static List<TaskStatus> CompletedStatuses() {
        return Arrays.asList(SUCCEEDED, STOPPED, FAILED, CANCELLED, PAUSED);
    }
}
