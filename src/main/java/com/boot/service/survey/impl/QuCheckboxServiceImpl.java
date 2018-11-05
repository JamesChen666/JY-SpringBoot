package com.boot.service.impl;

import com.boot.model.survey.*;
import com.boot.service.survey.QuCheckboxService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuCheckboxServiceImpl extends BaseServiceImpl implements QuCheckboxService {
    @Override
    public List<QuCheckbox> findByQuId(Long id) {
        Map map = new HashMap();
        map.put("quId",id);
        map.put("visibility",1);
        List<QuCheckbox> select = SQLManager.select("qucheckbox.findByQuId", QuCheckbox.class, map);
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
        map.put("quId", question.getId());
        List<Map<String,Object>>  list=SQLManager.select("qucheckbox.findGroupStats", Map.class, map);
        List<QuCheckbox> quCheckboxs=question.getQuCheckboxs();

        int count=0;
        for (QuCheckbox quCheckbox : quCheckboxs) {

            Long quCheckboxId=quCheckbox.getId();
            for (Map<String,Object> objectMap : list) {
                if(quCheckboxId.equals(objectMap.get("quItemId"))){
                    int anCount=Integer.parseInt(objectMap.get("count").toString());
                    count+=anCount;
                    quCheckbox.setAnCount(anCount);
                    break;
                }
            }
        }
        question.setAnCount(count);
    }

    @Override
    public List<SurveyReport> findStatsDataChart(Question question) {
        List<SurveyReport> reports=new ArrayList<>();

        Map map = new HashMap();
        map.put("quId", question.getId());
        List<Map<String,Object>>  list=SQLManager.select("qucheckbox.findStatsDataChart", Map.class, map);

        List<QuCheckbox> quCheckboxs=question.getQuCheckboxs();
        for (QuCheckbox quCheckbox : quCheckboxs) {
            Long quItemId=quCheckbox.getId();
            String optionName=quCheckbox.getOptionName();
            SurveyReport surveyReport = new SurveyReport();
            surveyReport.setOptionName(optionName);
            for (Map<String,Object> objectMap : list) {
                Long anQuItemId=Long.valueOf(objectMap.get("quItemId").toString());
                int count=Integer.parseInt(objectMap.get("count").toString());
                if(quItemId.equals(anQuItemId)){
                    surveyReport.setCount(count);
                    break;
                }
            }
            reports.add(surveyReport);
        }
        return reports;
    }

    @Override
    public List<AnCheckbox> findAnswer(Long belongAnswerId, Long quId){
        Map map = new HashMap();
        map.put("belongAnswerId", belongAnswerId);
        map.put("quId", quId);
        return SQLManager.select("qucheckbox.findAnswer", AnCheckbox.class, map);
    }
}
