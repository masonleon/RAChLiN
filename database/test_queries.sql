
-----------------------------------------------FUNCTIONS----------------------------------------------------------------
-- Function to calculate loa (length overall)
DROP FUNCTION IF EXISTS LENGTH_OVERALL_METERS(to_bow NUMERIC, to_stern NUMERIC);

CREATE FUNCTION LENGTH_OVERALL_METERS(IN to_bow NUMERIC(5, 2), IN to_stern NUMERIC(5, 2))
    RETURNS NUMERIC(5, 2) AS
        $BODY$
        DECLARE
            loa_meters NUMERIC(5, 2);
        BEGIN
            loa_meters := to_bow + to_stern;
            RETURN loa_meters;
        END;
        $BODY$
            LANGUAGE plpgsql;

-- Function to calculate beam (width)
DROP FUNCTION IF EXISTS BEAM_OVERALL_METERS(to_port NUMERIC, to_starboard NUMERIC);

CREATE FUNCTION BEAM_OVERALL_METERS(IN to_port NUMERIC(5, 2), IN to_starboard NUMERIC(5, 2))
    RETURNS NUMERIC(5, 2) AS
        $BODY$
        DECLARE
            beam_meters NUMERIC(5, 2);
        BEGIN
            beam_meters := to_port + to_starboard;
            RETURN beam_meters;
        END;
        $BODY$
            LANGUAGE plpgsql;

---------------------------------------------SAMPLE QUERIES-------------------------------------------------------------
-- List of distinct vessels (vessel signatures), sorted largest to smallest
SELECT DISTINCT vs.mmsi,
                vs.imo,
                vs.name,
                vs.call_sign,
                LENGTH_OVERALL_METERS(vd.to_bow, vd.to_stern) loa,
                BEAM_OVERALL_METERS(vd.to_port, vd.to_starboard) beam,
                vt.ais_vessel_code,
                vt.vessel_group,
                vt.ais_ship_cargo_classification,
                vt.note
FROM vessel_signature vs
         LEFT JOIN message_data md USING (vessel_signature_id)
         LEFT JOIN vessel_data vd USING (vessel_data_id)
         LEFT JOIN vessel_type vt USING (vessel_type_id)
-- where md.message_type_id = '1' or md.message_type_id = '2' or md.message_type_id = '3' or md.message_type_id = '5'
GROUP BY vt.ais_vessel_code, vt.vessel_group, vt.ais_ship_cargo_classification, vt.note,
         vs.mmsi, vs.imo, vs.name, vs.call_sign,
         loa, beam
ORDER BY loa DESC NULLS LAST;

-- Number of distinct vessels (vessel signatures)
SELECT count(DISTINCT mmsi)
FROM vessel_signature
         LEFT JOIN message_data md USING (vessel_signature_id)
WHERE md.message_type_id = '1'
   OR md.message_type_id = '2'
   OR md.message_type_id = '3'
   OR md.message_type_id = '5';

-- checking vessel with vessel data but reporting vessel type as 0
SELECT DISTINCT mt.name, mt.description, vs.*
FROM vessel_signature vs
         LEFT JOIN message_data md USING (vessel_signature_id)
         LEFT JOIN message_type mt USING (message_type_id)
WHERE vs.mmsi = '538002221';

-- Number of total messages received
SELECT Count(message_id)
FROM message_data;

-- Number of messages received by type
SELECT mt.supported, COUNT(message_id) num_msg_received, mt.message_type_id,  mt.name, mt.description
FROM message_data
LEFT JOIN message_type mt USING (message_type_id)
GROUP BY mt.supported, mt.message_type_id, mt.name, mt.description
ORDER BY num_msg_received DESC;

-- Number of distinct vessels (vessel signatures) by type

-- List of distinct vessels (vessel signatures) seen within a specific rectangular "geofence"