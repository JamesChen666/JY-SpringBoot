package com.boot.service.survey;

import com.boot.model.survey.Question;

import java.util.List;

public interface QuestionService {

    List<Question> findDetailsBySurveyId(Long surveyId, String tag);

    Question findOneById(Long id);

    void save(Question question);

    void update(Question  question);

    void delete (Long id);
}
