package com.boot.service.survey.impl;

import com.boot.model.survey.AnFillblank;
import com.boot.model.survey.QuCheckbox;
import com.boot.model.survey.QuMultiFillblank;
import com.boot.model.survey.Question;
import com.boot.service.impl.BaseServiceImpl;
import com.boot.service.survey.QuFillblankService;
import com.boot.service.survey.QuMultiFillblankService;
import org.beetl.sql.ext.spring4.SqlManagerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuFillblankServiceImpl extends BaseServiceImpl implements QuFillblankService {
    @Override
    public List<QuMultiFillblank> findByQuId(Long id) {
        Map map = new HashMap();
        map.put("quId",id);
        map.put("visibility",1);
        List<QuMultiFillblank> select = SQLManager.select("qufillblank.findByQuId", QuMultiFillblank.class, map);
        return select;
    }

    @Override
    public void save(QuCheckbox quCheckbox) {
        SQLManager.insert(QuCheckbox.class, quCheckbox, true);
    }

    @Override
    public void delete(Long id) {
        SQLManager.deleteById(QuCheckbox.class, id);
    }

    @Override
    public void findGroupStats(Question question) {
        Map map = new HashMap();
        map.put("quId",question.getId());
        Map<String, Object> objs= SQLManager.selectSingle("qufillblank.findGroupStats",  map, Map.class);

        question.setRowContent(objs.get("emptyCount").toString());//未回答数
        question.setOptionContent(objs.get("blankCount").toString());//回答的项数
        question.setAnCount(Integer.parseInt(objs.get("blankCount").toString()));
    }

    @Override
    public AnFillblank findAnswer(Long belongAnswerId, Long quId){
        Map map = new HashMap();
        map.put("belongAnswerId", belongAnswerId);
        map.put("quId", quId);
        return SQLManager.selectSingle("qufillblank.findAnswer", map, AnFillblank.class);
    }
}
