list
===
SELECT eb.*,eef.Title as PlaceName FROM Election_Booth eb
left join election_election_field eef on eb.PlaceId = eef.Id

findOne
===
SELECT * FROM Election_Booth WHERE id = #{id}
