
find
===
SELECT
       id as id,
        answer_input_row as answerInputRow,
        answer_input_width as answerInputWidth,
        belong_id as belongId,
        cell_count as cellCount,
        check_type as checkType,
        contacts_attr as contactsAttr,
        contacts_field as contactsField,
        copy_from_id as copyFromId,
        create_date as createDate,
        hv as hv,
        is_required as isRequired,
        keywords as keywords,
        order_by_id as orderById,
        param_int01 as paramInt01,
        param_int02 as paramInt02,
        parent_qu_id as parentQuId,
        qu_name as quName,
        qu_title as quTitle,
        qu_tag as quTag,
        qu_type as quType,
        rand_order as randOrder,
        tag as tag,
        visibility as visibility,
        yesno_option as yesno_option
FROM sv_question  where belong_id=#{belongId} and tag=#{tag} order by order_by_id asc


findOne
===
SELECT
       id as id,
        answer_input_row as answerInputRow,
        answer_input_width as answerInputWidth,
        belong_id as belongId,
        cell_count as cellCount,
        check_type as checkType,
        contacts_attr as contactsAttr,
        contacts_field as contactsField,
        copy_from_id as copyFromId,
        create_date as createDate,
        hv as hv,
        is_required as isRequired,
        keywords as keywords,
        order_by_id as orderById,
        param_int01 as paramInt01,
        param_int02 as paramInt02,
        parent_qu_id as parentQuId,
        qu_name as quName,
        qu_title as quTitle,
        qu_tag as quTag,
        qu_type as quType,
        rand_order as randOrder,
        tag as tag,
        visibility as visibility,
        yesno_option as yesno_option
FROM sv_question  where id=#{id}

