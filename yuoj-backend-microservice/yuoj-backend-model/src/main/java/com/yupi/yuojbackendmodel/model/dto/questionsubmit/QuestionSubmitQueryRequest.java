package com.yupi.yuojbackendmodel.model.dto.questionsubmit;


import com.yupi.yuojbackendcommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 提交状态
     */
    private Integer state;

    /**
     * 用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;

}
