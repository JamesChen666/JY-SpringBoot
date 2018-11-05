package com.boot.service.survey;

import com.boot.model.survey.SurveyDirectory;

public interface SurveyDirectoryService {

    SurveyDirectory  getSurveyById(Long id);

    SurveyDirectory  getSurveyBySid(String sid);

    void saveOrUpdate(SurveyDirectory surveyDirectory);

    void delete(Long id);

}
