package com.boot.service.survey.impl;

import cn.hutool.core.lang.Dict;
import com.boot.model.survey.Question;
import com.boot.model.survey.QuestionLogic;
import com.boot.model.survey.enums.QuType;
import com.boot.service.impl.BaseServiceImpl;
import com.boot.service.survey.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl extends BaseServiceImpl implements QuestionService {

    @Autowired
    QuRadioService quRadioService;
    @Autowired
    QuCheckboxService quCheckboxService;
    @Autowired
    QuMultiFillblankService quMultiFillblankService;
    @Autowired
    QuestionLogicService questionLogicService;

    @Override
    public List<Question> findDetailsBySurveyId(Long belongId, String tag) {
        List<Question> questions = find(belongId, tag);
        for (Question question : questions) {
            getQuestionOption(question);
        }
        return questions;
    }

    @Override
    public Question findOneById(Long id) {
        return SQLManager.selectSingle("question.findOne",
                Dict.create().set("id", id), Question.class);
    }

    @Override
    public void save(Question question) {
        SQLManager.insert(Question.class, question, true);
    }

    @Override
    public void update(Question question) {
        SQLManager.updateById(question);
    }

    @Override
    public void delete(Long id) {
        SQLManager.deleteById(Question.class, id);
    }

    private void getQuestionOption(Question question) {

        Long quId = question.getId();
        QuType quType = question.getQuType();
        if (quType == QuType.RADIO) {
            question.setQuRadios(quRadioService.findByQuId(quId));
        } else if (quType == QuType.CHECKBOX) {
            question.setQuCheckboxs(quCheckboxService.findByQuId(quId));
        } else if (quType == QuType.MULTIFILLBLANK) {
            question.setQuMultiFillblanks(quMultiFillblankService.findByQuId(quId));
        } else if (quType == QuType.FILLBLANK) {
        }
        List<QuestionLogic> questionLogics = questionLogicService.findByCkQuId(quId);
        question.setQuestionLogics(questionLogics);

    }

    private List<Question> find(Long belongId, String tag) {
        Map map = new HashMap();
        //创建人id
        map.put("belongId", belongId);
        map.put("tag", tag);
        List<Question> select = SQLManager.select("question.find", Question.class, map);
        return select;
    }


}
