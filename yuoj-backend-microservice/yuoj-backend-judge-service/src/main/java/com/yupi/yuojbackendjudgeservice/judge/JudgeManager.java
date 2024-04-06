package com.yupi.yuojbackendjudgeservice.judge;

import com.yupi.yuojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.yupi.yuojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.yupi.yuojbackendjudgeservice.judge.strategy.JudgeContext;
import com.yupi.yuojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.yupi.yuojbackendmodel.model.codesandbox.JudgeInfo;
import com.yupi.yuojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;


@Service
public class JudgeManager {

    /**
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
