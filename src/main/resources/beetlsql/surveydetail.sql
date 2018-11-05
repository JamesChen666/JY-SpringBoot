findOne
===
SELECT
   id as id,
        an_item_least_num as anItemLeastNum,
        an_item_most_num as anItemMostNum,
        dir_id as dirId,
        effective as effective,
        effective_ip as effectiveIp,
        effective_time as effectiveTime,
        end_num as endNum,
        end_time as endTime,
        end_type as endType,
        mail_only as mailOnly,
        refresh as refresh,
        refresh_num as refreshNum,
        rule as rule,
        rule_code as ruleCode,
        show_answer_da as showAnswerDa,
        show_share_survey as showShareSurvey,
        survey_qu_num as surveyQuNum,
        survey_note as surveyNote,
        yn_end_num as ynEndNum,
        yn_end_time as yn_end_time,
        beg_time as begTime,
        to_group as toGroup,
        is_force as isForce
 FROM sv_survey_detail WHERE dir_id = #{surveyId}


