
-- List of distinct vessels, sorted largest to smallest
select distinct vs.mmsi, vs.imo, vs.name, vs.call_sign,
        (vd.to_bow + vd.to_stern) loa , (vd.to_port + vd.to_starboard) beam,
       vt.ais_vessel_code, vt.vessel_group, vt.ais_ship_cargo_classification, vt.note
from vessel_signature vs
    left join message_data md on (vs.vessel_signature_id = md.vessel_signature_id )
    left join vessel_data vd using (vessel_data_id)
    left join vessel_type vt on vs.vessel_type_id = vt.vessel_type_id
-- where md.message_type_id = '1' or md.message_type_id = '2' or md.message_type_id = '3' or md.message_type_id = '5'
group by vt.ais_vessel_code, vt.vessel_group, vt.ais_ship_cargo_classification, vt.note,
                vs.mmsi, vs.imo, vs.name, vs.call_sign,
                loa , beam
order by loa desc nulls last;

-- Count of distinct vessels
select count (distinct mmsi) from vessel_signature
    left join message_data md on vessel_signature.vessel_signature_id = md.vessel_signature_id
where md.message_type_id = '1' or md.message_type_id = '2' or md.message_type_id = '3' or md.message_type_id = '5';



select distinct mt.name, mt.description, vs.*  from vessel_signature vs
    left join message_data md on vs.vessel_signature_id = md.vessel_signature_id
    left join message_type mt using (message_type_id)
where vs.mmsi = '538002221' ;