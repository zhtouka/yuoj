package com.yupi.yuojbackendmodel.model.codesandbox;


import lombok.Data;

@Data
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗时间
     */
    private Long time;

    /**
     * 消耗空间
     */
    private Long memory;

}
