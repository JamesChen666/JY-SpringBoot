package com.boot.service.survey;

import com.boot.model.survey.AnRadio;
import com.boot.model.survey.QuRadio;
import com.boot.model.survey.Question;
import com.boot.model.survey.SurveyReport;

import java.util.List;

public interface QuRadioService {

    List<QuRadio> findByQuId(Long id);

    void save(QuRadio quRadio);

    void delete(Long id);

    void findGroupStats(Question question);

    List<SurveyReport> findStatsDataChart(Question question);

    AnRadio findAnswer(Long belongAnswerId, Long quId);

}
