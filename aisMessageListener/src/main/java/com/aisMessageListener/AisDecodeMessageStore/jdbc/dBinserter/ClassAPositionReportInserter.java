package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

import com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData.MessageDataInterface;

/**
 * Extension of the DatabaseInserter to handle specific functionality related to ClassAPositionReport messages (types
 * 1, 2, or 3).
 */
class ClassAPositionReportInserter extends AbstractDatabaseInserter {

    ClassAPositionReportInserter(MessageDataInterface message) {
        super(message);
    }
}


