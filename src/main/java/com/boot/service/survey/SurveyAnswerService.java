package com.boot.service.survey;

import com.boot.model.survey.Question;
import com.boot.model.survey.SurveyAnswer;
import com.boot.model.survey.SurveyDetail;
import com.boot.model.survey.SurveyDirectory;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface SurveyAnswerService {

    void saveAnswer(SurveyAnswer surveyAnswer,
              Map<String, Map<String, Object>> quMaps);

    long getCountByIp(Long surveyId, String ipAddr);

    int getquestionAnswer(Long surveyAnswerId, Question question);

    List<SurveyAnswer> getAnswerBySurveyId(Long surveyId);

    long getAnswerCountByUser(Long surveyId, Long userId);
}
