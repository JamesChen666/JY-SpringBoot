package com.boot.service.survey.impl;

import com.boot.model.survey.AnRadio;
import com.boot.model.survey.QuRadio;
import com.boot.model.survey.Question;
import com.boot.model.survey.SurveyReport;
import com.boot.service.impl.BaseServiceImpl;
import com.boot.service.survey.QuRadioService;
import org.beetl.sql.core.DefaultNameConversion;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuRadioServiceImpl extends BaseServiceImpl implements QuRadioService {
    @Override
    public List<QuRadio> findByQuId(Long id) {
        Map map = new HashMap();
        map.put("quId",id);
        map.put("visibility",1);
        List<QuRadio> select = SQLManager.select("quradio.findByQuId", QuRadio.class, map);
        return select;
    }

    @Override
    public void save(QuRadio quRadio) {
        SQLManager.insert(QuRadio.class, quRadio, true);
    }

    @Override
    public void delete(Long id) {
        SQLManager.deleteById(QuRadio.class, id);
    }

    @Override
    public void findGroupStats(Question question) {

        Map map = new HashMap();
        map.put("quId", question.getId());
        List<Map<String,Object>>  list=SQLManager.select("quradio.findGroupStats", Map.class, map);
        List<QuRadio> quRadios=question.getQuRadios();

        int count=0;
        for (QuRadio quRadio : quRadios) {
            Long quRadioId=quRadio.getId();
            for (Map<String, Object> stringObjectMap  : list) {
                if(quRadioId.equals(stringObjectMap.get("quItemId"))){
                    int anCount=Integer.parseInt(stringObjectMap.get("count").toString());
                    count+=anCount;
                    quRadio.setAnCount(anCount);
                    break;
                }
            }
        }
        question.setAnCount(count);
    }

    @Override
    public List<SurveyReport> findStatsDataChart(Question question) {
        List<SurveyReport> crosses=new ArrayList<>();

        Map map = new HashMap();
        map.put("quId", question.getId());
        List<Map<String,Object>>  list=SQLManager.select("quradio.findStatsDataChart", Map.class, map);

        List<QuRadio> quRadios=question.getQuRadios();
        for (QuRadio quRadio : quRadios) {
            Long quItemId=quRadio.getId();
            String optionName=quRadio.getOptionName();
            SurveyReport dataCross=new SurveyReport();
            dataCross.setOptionName(optionName);
            for (Map<String,Object> objectMap : list) {
                Long anQuItemId=Long.valueOf(objectMap.get("quItemId").toString());
                int count=Integer.parseInt(objectMap.get("count").toString());
                if(quItemId.equals(anQuItemId)){
                    dataCross.setCount(count);
                    break;
                }
            }
            crosses.add(dataCross);
        }
        return crosses;
    }

    @Override
    public AnRadio findAnswer(Long belongAnswerId, Long quId){
        Map map = new HashMap();
        map.put("belongAnswerId", belongAnswerId);
        map.put("quId", quId);
        return SQLManager.selectSingle("quradio.findAnswer", map, AnRadio.class);
    }
}
