package com.boot.service.survey.impl;

import com.boot.model.survey.QuestionLogic;
import com.boot.service.impl.BaseServiceImpl;
import com.boot.service.survey.QuestionLogicService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionLogicServiceImpl extends BaseServiceImpl implements QuestionLogicService {
    @Override
    public List<QuestionLogic> findByCkQuId(Long id) {
        Map map = new HashMap();
        map.put("ckQuId",id);
        map.put("visibility",1);
        List<QuestionLogic> select = SQLManager.select("questionlogic.findByQuId", QuestionLogic.class, map);
        return select;
    }

    @Override
    public void save(QuestionLogic questionLogic) {
        SQLManager.insert(QuestionLogic.class, questionLogic, true);
    }
}
