package com.boot.service.survey.impl;

import cn.hutool.core.lang.Dict;
import com.boot.model.survey.SurveyStyle;
import com.boot.service.impl.BaseServiceImpl;
import com.boot.service.survey.SurveyStyleService;
import org.springframework.stereotype.Service;

@Service
public class SurveyStyleServiceImpl extends BaseServiceImpl implements SurveyStyleService {
    @Override
    public SurveyStyle getBySurveyId(Long surveyId) {

        return SQLManager.selectSingle("surveystyle.findByQuId",
                Dict.create().set("surveyId", surveyId), SurveyStyle.class);
    }
}
