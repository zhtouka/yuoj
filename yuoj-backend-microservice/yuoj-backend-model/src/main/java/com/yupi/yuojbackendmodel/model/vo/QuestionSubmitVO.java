package com.yupi.yuojbackendmodel.model.vo;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yupi.yuojbackendmodel.model.codesandbox.JudgeInfo;
import com.yupi.yuojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class QuestionSubmitVO {

    /**
     * id
     */
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 提交用户 id
     */
    private Long userId;

    /**
     * 提交代码
     */
    private String code;

    /**
     * 判题信息 (JSON 对象)
     */
    private JudgeInfo judgeInfo;

    /**
     * 状态 (0-带判题, 1-判题中, 2-成功, 3-失败)
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 提交用户信息
     */
    private UserVO userVO;

    /**
     * 题目信息
     */
    private QuestionVO questionVO;

    private static final long serialVersionUID = 1L;

    private final static Gson GSON = new Gson();

    /**
     * 包装类转对象
     *
     * @param questionSubmitVO
     * @return
     */
    public static QuestionSubmit voToObj(QuestionSubmitVO questionSubmitVO) {
        if (questionSubmitVO == null) {
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitVO, questionSubmit);

        JudgeInfo voJudgeInfo = questionSubmitVO.getJudgeInfo();
        if (voJudgeInfo != null) {
            questionSubmit.setJudgeInfo(GSON.toJson(voJudgeInfo));
        }
        return questionSubmit;
    }

    /**
     * 对象转包装类
     *
     * @param questionSubmit
     * @return
     */
    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);

        questionSubmitVO.setJudgeInfo(GSON.fromJson(questionSubmit.getJudgeInfo(),
                new TypeToken<JudgeInfo>(){}.getType()));
        return questionSubmitVO;
    }
}
