package com.boot.service.survey.impl;

import cn.hutool.core.lang.Dict;
import com.boot.model.survey.SurveyDetail;
import com.boot.model.survey.SurveyDirectory;
import com.boot.service.impl.BaseServiceImpl;
import com.boot.service.survey.SurveyDetailService;
import org.springframework.stereotype.Service;

@Service
public class SurveyDetailServiceImpl  extends BaseServiceImpl implements SurveyDetailService {


    @Override
    public SurveyDetail getSurveyDetail(Long surveyId, SurveyDirectory surveyDirectory) {

        SurveyDetail surveyDetail = SQLManager.selectSingle("surveydetail.findOne",
                Dict.create().set("surveyId", surveyId), SurveyDetail.class);
        surveyDirectory.setSurveyDetail(surveyDetail);

        return surveyDetail;
    }

    @Override
    public void saveOrUpdate(SurveyDetail surveyDetail) {
        if (surveyDetail.getId() != null && surveyDetail.getId() >  0l) {
            SQLManager.updateById(surveyDetail);
        } else {
            SQLManager.insert(SurveyDetail.class, surveyDetail, true);
        }
    }
}
