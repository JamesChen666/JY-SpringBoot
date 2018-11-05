package com.boot.service.survey;

import com.boot.model.survey.*;

import java.util.List;
import java.util.Map;

public interface SurveyStatsService {

    SurveyStats findBySurvey(Long surveyId);

    void saveOrUpdate(SurveyStats surveyStats);

    List<Question> findFrequency(SurveyDirectory survey);

    void questionReports(Question question);

    List<SurveyReport> findDataChart(Question question);

    void questionDateCross(Question question);
}
