package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.DatabaseConnectionInterface;

import java.sql.SQLException;

public interface DatabaseInserterInterface {
    
    void attachConnection(DatabaseConnectionInterface conn) throws SQLException;

    WriteResult writeMessageData() throws SQLException;

    WriteResult writeVesselSignature() throws SQLException;

    WriteResult writeVoyageData() throws SQLException;

    WriteResult writeVesselData() throws SQLException;

    WriteResult writeVesselTypeData() throws SQLException;

    WriteResult writeNavigationStatusData() throws SQLException;

    WriteResult writeNavigationData() throws SQLException;

    WriteResult writeGeospatialData() throws SQLException;

    WriteResult writeManeuverIndicatorData() throws SQLException;
}
