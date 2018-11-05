package com.boot.service.survey;

import com.boot.model.survey.QuestionLogic;

import java.util.List;

public interface QuestionLogicService {
    List<QuestionLogic> findByCkQuId(Long id);

    void save(QuestionLogic questionLogic);
}
