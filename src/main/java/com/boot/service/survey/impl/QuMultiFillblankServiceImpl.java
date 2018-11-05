package com.boot.service.survey.impl;

import com.boot.model.survey.AnDFillblank;
import com.boot.model.survey.QuCheckbox;
import com.boot.model.survey.QuMultiFillblank;
import com.boot.model.survey.Question;
import com.boot.service.impl.BaseServiceImpl;
import com.boot.service.survey.QuMultiFillblankService;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuMultiFillblankServiceImpl extends BaseServiceImpl implements QuMultiFillblankService {
    @Override
    public List<QuMultiFillblank> findByQuId(Long id) {
        Map map = new HashMap();
        map.put("quId",id);
        map.put("visibility",1);
        List<QuMultiFillblank> select = SQLManager.select("qumultifillblank.findByQuId", QuMultiFillblank.class, map);
        return select;
    }

    @Override
    public void save(QuMultiFillblank quMultiFillblank) {
        SQLManager.insert(QuMultiFillblank.class, quMultiFillblank, true);
    }

    @Override
    public void delete(Long id) {
        SQLManager.deleteById(QuCheckbox.class, id);
    }

    @Override
    public void findGroupStats(Question question) {
        Map map = new HashMap();
        map.put("quId", question.getId());
        List<Map<String,Object>>  list=SQLManager.select("qumultifillblank.findGroupStats", Map.class, map);
        List<QuMultiFillblank> quMultiFillblanks=question.getQuMultiFillblanks();

        for (QuMultiFillblank quMultiFillblank : quMultiFillblanks) {
            Long quMultiFillblankId=quMultiFillblank.getId();
            for (Map<String, Object> objectMap : list) {
                if(quMultiFillblankId.equals(objectMap.get("quItemId"))){
                    quMultiFillblank.setAnCount(Integer.parseInt(objectMap.get("quItemId").toString()));
                    break;
                }
            }
        }
    }

    @Override
    public List<AnDFillblank> findAnswer(Long belongAnswerId, Long quId){
        Map map = new HashMap();
        map.put("belongAnswerId", belongAnswerId);
        map.put("quId", quId);
        return SQLManager.select("qumultifillblank.findAnswer", AnDFillblank.class, map);
    }
}
