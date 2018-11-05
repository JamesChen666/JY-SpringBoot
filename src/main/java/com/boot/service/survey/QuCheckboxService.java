package com.boot.service.survey;

import com.boot.model.survey.AnCheckbox;
import com.boot.model.survey.QuCheckbox;
import com.boot.model.survey.Question;
import com.boot.model.survey.SurveyReport;

import java.util.List;

public interface QuCheckboxService {
    List<QuCheckbox> findByQuId(Long id);

    void save(QuCheckbox quCheckbox);

    void delete(Long id);

    void findGroupStats(Question question);

    List<SurveyReport> findStatsDataChart(Question question);

    List<AnCheckbox> findAnswer(Long belongAnswerId, Long quId);
}
