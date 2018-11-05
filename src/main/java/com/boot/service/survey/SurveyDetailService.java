package com.boot.service.survey;

import com.boot.model.survey.SurveyDetail;
import com.boot.model.survey.SurveyDirectory;

public interface SurveyDetailService {

    SurveyDetail getSurveyDetail(Long surveyId, SurveyDirectory surveyDirectory);

    void saveOrUpdate(SurveyDetail surveyDetail);

}
