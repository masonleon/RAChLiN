package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.MessageDataInterface;
import com.aisMessageListener.AisDecodeMessageStore.jdbc.util.CoordinateUtil;

import org.postgresql.geometric.PGpoint;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dk.tbsalling.aismessages.ais.exceptions.UnsupportedMessageType;

import static java.sql.Types.NULL;

class ClassAPositionReportInserter extends AbstractDatabaseInserter {

    ClassAPositionReportInserter(MessageDataInterface message) {
        super(message);
    }

}


