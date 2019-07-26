package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.DatabaseConnectionInterface;

import java.sql.SQLException;

public interface DatabaseInserterInterface {
    
    void attachConnection(DatabaseConnectionInterface conn) throws SQLException;

    void writeMessageData() throws SQLException;

    void writeVesselSignature() throws SQLException;

    void writeVoyageData() throws SQLException;

    void writeVesselData() throws SQLException;

    void writeNavigationData() throws SQLException;

    void writeGeospatialData() throws SQLException;
}
