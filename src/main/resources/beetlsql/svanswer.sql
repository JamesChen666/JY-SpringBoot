
countByIp
===
select count(*) from sv_survey_answer x where x.survey_id=#{surveyId} and x.ip_addr=#{ipAddr}

countByUserId
===
select count(*) from sv_survey_answer x left join sys_loginaccount l on x.user_id = l.id
 where x.survey_id=#{surveyId} and l.id=#{userId}

getAnswerBySurveyId
===
select
  id as id,
        addr as addr,
        bg_an_date as bgAnDate,
        city as city,
        complete_item_num as completeItemNum,
        complete_num as completeNum,
        data_source as dataSource,
        end_an_date as endAnDate,
        handle_state as handleState,
        ip_addr as ipAddr,
        is_complete as isComplete,
        is_effective as isEffective,
        pc_mac as pcMac,
        qu_num as quNum,
        survey_id as surveyId,
        total_time as totalTime,
        user_id as userId
 from sv_survey_answer x
 where x.survey_id=#{surveyId}

fbAnswerList
===
SELECT
   af.id as id, af.answer as answer, l.RealName as realName, l.UserName as userName
   FROM sv_an_fillblank af
   left join sv_survey_answer sa on af.belong_answer_id = sa.id
   left join sys_loginaccount l on sa.user_id = l.id
   where af.belong_id=#{surveyId} and af.qu_id = #{quId}
@if(!isEmpty(realName)){
 and RealName = #{realName}
@}
@if(!isEmpty(userName)){
and UserName = #{userName}
@}

mfbAnswerList
===
SELECT
  af.id as id, af.answer as answer, l.RealName as realName, l.UserName as userName
   FROM t_an_dfillblank af
   left join sv_survey_answer sa on af.belong_answer_id = sa.id
   left join sys_loginaccount l on sa.user_id = l.id
   where af.belong_id=#{surveyId} and af.qu_item_id = #{quItemId}
@if(!isEmpty(realName)){
and RealName = #{realName}
@}
@if(!isEmpty(userName)){
and UserName = #{userName}
@}