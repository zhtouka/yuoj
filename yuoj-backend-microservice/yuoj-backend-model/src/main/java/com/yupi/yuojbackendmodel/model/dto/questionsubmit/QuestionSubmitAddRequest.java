package com.yupi.yuojbackendmodel.model.dto.questionsubmit;


import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionSubmitAddRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 提交代码
     */
    private String code;

    private static final long serialVersionUID = 1L;

}
