package com.yupi.yuojbackendmodel.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题信息消息枚举
 */
public enum JudgeInfoMessageEnum {

    ACCEPTED("通过", "Accepted"),
    WRONG_ANSWER("错误答案", "Wrong answer"),
    COMPILE_ERROR("编译错误", "Compile error"),
    MEMORY_LIMIT_EXCEEDED("内存溢出", "Memory limit exceeded"),
    TIME_LIMIT_EXCEEDED("超时", "Time limit exceeded"),
    PRESENTATION_ERROR("展示错误", "Presentation error"),
    OUTPUT_LIMIT_EXCEEDED("输出溢出", "Output limit exceeded"),
    WAITING("等待中", "Waiting"),
    DANGEROUS_OPERATION("危险操作", "Dangerous operation"),

    SYSTEM_ERROR("系统错误", "System error");

    private final String text;

    private final String value;

    JudgeInfoMessageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static JudgeInfoMessageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoMessageEnum anEnum : JudgeInfoMessageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
