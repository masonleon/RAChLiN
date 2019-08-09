package com.aisMessageListener.AisDecodeMessageStore.jdbc.dBinserter;

/**
 * The result of a database write operation is one of:
 * - SUCCESS: the transaction was successfully committed to the database.
 * - FAILURE: the transaction could not be committed, and was subsequently rolled back.
 * - UNSUPPORTED: the transaction we wanted to write was not committed because the message is unsupported for whatever
 * arbitrary reason. As such, a different transaction was successfully written. e.g we tried to write to vesselData, but
 * there was no actual vessel data in the AIS Message, so NULLs were written instead.
 */
public enum WriteResult {
    FAILURE, SUCCESS, UNSUPPORTED
}
