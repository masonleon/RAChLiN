package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.DatabaseConnectionInterface;

import java.sql.SQLException;

/**
 * TODO java doc
 */
public interface DatabaseInserterInterface {

    /**
     * TODO Java doc
     * @param conn
     * @throws SQLException
     */
    void attachConnection(DatabaseConnectionInterface conn) throws SQLException;

    /**
     * Top level function that begins the transaction, then writes to all tables, then commits the transaction.
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeMessage() throws SQLException;

    /**
     * Parses an AIS message and writes to the messageData table. This is the top-level table in the star schema. All
     * other relevant tables must be written to first, or this will fail due to foreign key constraint issues.
     *
     * <p>MUST BE CALLED IN TRANSACTION</p>
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeMessageData() throws SQLException;

    /**
     * Parses an AIS message and writes to the vesselSignature table. This will fail if vesselType has not been written
     * to first due to foreign key constraint issues.
     *
     * <p>MUST BE CALLED IN TRANSACTION</p>
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeVesselSignature() throws SQLException;

    /**
     * Parses an AIS message and writes to the voyageData table.
     *
     * <p>MUST BE CALLED IN TRANSACTION</p>
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeVoyageData() throws SQLException;

    /**
     * Parses an AIS message and writes to the vesselData table.
     *
     * <p>MUST BE CALLED IN TRANSACTION</p>
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeVesselData() throws SQLException;

    /**
     * Parses an AIS message and writes to the navigationData table. This will fail if vesselType has not been written
     * to first due to foreign key constraint issues.
     *
     * <p>MUST BE CALLED IN TRANSACTION</p>
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeNavigationData() throws SQLException;

    /**
     * Parses an AIS message and writes to the geospatialData table.
     *
     * <p>MUST BE CALLED IN TRANSACTION</p>
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeGeospatialData() throws SQLException;

    @Deprecated
    /**
     * Parses an AIS message and writes to the maneuverIndicator table.
     *
     * <p>MUST BE CALLED IN TRANSACTION</p>
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeManeuverIndicator() throws SQLException;

    @Deprecated
    /**
     * Parses an AIS message and writes to the vesselType table.
     *
     * <p>MUST BE CALLED IN TRANSACTION</p>
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeVesselType() throws SQLException;

    @Deprecated
    /**
     * Parses an AIS message and writes to the navigationStatus table.
     *
     * <p>MUST BE CALLED IN TRANSACTION</p>
     *
     * @return WriteResult
     * @throws SQLException if operation fails due to database access errors.
     */
    WriteResult writeNavigationStatus() throws SQLException;
}
