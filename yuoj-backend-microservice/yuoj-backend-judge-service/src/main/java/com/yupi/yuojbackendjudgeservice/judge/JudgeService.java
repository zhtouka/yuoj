package com.yupi.yuojbackendjudgeservice.judge;


import com.yupi.yuojbackendmodel.model.entity.QuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {

    QuestionSubmit doJudge(long questionSubmitId);
}
