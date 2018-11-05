package com.boot.service.survey;

import com.boot.model.survey.AnFillblank;
import com.boot.model.survey.QuCheckbox;
import com.boot.model.survey.QuMultiFillblank;
import com.boot.model.survey.Question;

import java.util.List;

public interface QuFillblankService {
    List<QuMultiFillblank> findByQuId(Long id);

    void save(QuCheckbox quCheckbox);

    void delete(Long id);

    void findGroupStats(Question question);

    AnFillblank findAnswer(Long belongAnswerId, Long quId);
}
