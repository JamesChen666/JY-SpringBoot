list
===
SELECT * FROM Portal_AdvertPosition

findOne
===
SELECT * FROM Portal_AdvertPosition WHERE id = #{id}

deleteList
===
delete from portal_advertposition where id in (#{id})

query
===
SELECT pa.*,pan.*,pan.Id ids from portal_advert pa,portal_advertposition pan where pa.PositionId = pan.Id
