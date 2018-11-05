package com.boot.service.survey;

import com.boot.model.survey.AnDFillblank;
import com.boot.model.survey.QuCheckbox;
import com.boot.model.survey.QuMultiFillblank;
import com.boot.model.survey.Question;

import java.util.List;

public interface QuMultiFillblankService {
    List<QuMultiFillblank> findByQuId(Long id);

    void save(QuMultiFillblank quMultiFillblank);

    void delete(Long id);

    void findGroupStats(Question question);

    List<AnDFillblank> findAnswer(Long belongAnswerId, Long quId);
}
