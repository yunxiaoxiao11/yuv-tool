package com.example.yuv.tool.model;

import com.example.yuv.tool.annotation.EnumValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnumReq {
    @EnumValid(allowedValues = {"STOPPED", "FAILED", "SUCCEEDED"})
    private TaskStatus statusValue;

    @EnumValid(allowedListFieldName = "COMPLETED_FIELD")
    private TaskStatus statusField;

    @EnumValid(allowedListMethodName = "CompletedStatuses")
    private TaskStatus statusMethod;

    private String statusStr;

    @EnumValid
    private TaskStatus status;
}
