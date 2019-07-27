package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.DatabaseConnectionInterface;

import java.sql.SQLException;

public interface DatabaseInserterInterface {
    
    void attachConnection(DatabaseConnectionInterface conn) throws SQLException;

    /**
     * Parses an AIS message and writes to the messageData table. This is the top-level table in the star schema. All
     * other relevant tables must be written to first, or this will fail due to foreign key constraint issues.
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeMessageData() throws SQLException;

    /**
     * Parses an AIS message and writes to the vesselSignature table. This will fail if vesselType has not been written
     * to first due to foreign key constraint issues.
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeVesselSignature() throws SQLException;

    /**
     * Parses an AIS message and writes to the voyageData table.
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeVoyageData() throws SQLException;

    /**
     * Parses an AIS message and writes to the vesselData table.
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeVesselData() throws SQLException;

    /**
     * Parses an AIS message and writes to the vesselType table.
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeVesselType() throws SQLException;

    /**
     * Parses an AIS message and writes to the navigationStatus table.
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeNavigationStatus() throws SQLException;

    /**
     * Parses an AIS message and writes to the navigationData table. This will fail if vesselType has not been written
     * to first due to foreign key constraint issues.
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeNavigationData() throws SQLException;

    /**
     * Parses an AIS message and writes to the geospatialData table.
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeGeospatialData() throws SQLException;

    /**
     * Parses an AIS message and writes to the maneuverIndicator table.
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeManeuverIndicator() throws SQLException;
}
