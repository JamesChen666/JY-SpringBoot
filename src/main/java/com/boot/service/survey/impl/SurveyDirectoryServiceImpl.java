package com.boot.service.survey.impl;

import cn.hutool.core.lang.Dict;
import com.boot.model.survey.SurveyDirectory;
import com.boot.service.impl.BaseServiceImpl;
import com.boot.service.survey.SurveyDirectoryService;
import org.beetl.sql.core.DefaultNameConversion;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.springframework.stereotype.Service;

@Service
public class SurveyDirectoryServiceImpl extends BaseServiceImpl implements SurveyDirectoryService {

    @Override
    public SurveyDirectory getSurveyById(Long surveyId) {
        return SQLManager.selectSingle("survey.findOne",
                Dict.create().set("id", surveyId), SurveyDirectory.class);
    }

    @Override
    public SurveyDirectory getSurveyBySid(String sid) {
        return SQLManager.selectSingle("survey.findBySid",
                Dict.create().set("sid", sid), SurveyDirectory.class);
    }

    @Override
    public void saveOrUpdate(SurveyDirectory surveyDirectory) {
        if (surveyDirectory.getId() != null && surveyDirectory.getId() >  0l) {
            SQLManager.updateById(surveyDirectory);
        } else {
            SQLManager.insert(SurveyDirectory.class , surveyDirectory, true);
        }
    }

    @Override
    public void delete(Long id) {
        SQLManager.deleteById(SurveyDirectory.class, id);
    }
}
