package com.aisMessageListener.AisDecodeMessageStore.jdbc;

import dk.tbsalling.aismessages.ais.exceptions.UnsupportedMessageType;
import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.types.AISMessageType;
import dk.tbsalling.aismessages.nmea.exceptions.InvalidMessage;
import dk.tbsalling.aismessages.nmea.messages.NMEAMessage;

import java.time.Instant;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.sql.Types.NULL;

public class AbstractMessageData implements MessageDataInterface {
    private AISMessage message;
    private Instant timeRecieved;
    private boolean isMultiPart;
    private int geospatialDataId;
    private int navigationDataId;
    private int voyageDataId;
    private int vesselSignatureId;
    private int vesselDataId;

    public AbstractMessageData(AISMessage message) {
        this.message = message;

        this.timeRecieved = this.message.getMetadata().getReceived();
        this.isMultiPart = FALSE;
        this.geospatialDataId = NULL;
        this.navigationDataId = NULL;
        this.voyageDataId = NULL;
        this.vesselSignatureId = NULL;
        this.vesselDataId = NULL;
    }

    public void processMessage() {
        AISMessageType messageType = message.getMessageType();

        if (messageType != null) {
            switch (messageType) {

                //Message Type 1
                case PositionReportClassAScheduled:


                    break;

                //Message Type 2
                case PositionReportClassAAssignedSchedule:


                    break;

                //Message Type 3
                case PositionReportClassAResponseToInterrogation:


                    break;

                //Message Type 5
                case ShipAndVoyageRelatedData:


                    break;

                //Unsupported Message
                default:
                    throw new UnsupportedMessageType(messageType.getCode());
            }
        } else {
            //isInvalid Message field
            throw new InvalidMessage("Invalid NMEA message");
        }

    }

    public boolean isValidType() {
        return this.getTypeId() > -1;
    }

    public int getMMSI() {
        return this.message.getSourceMmsi().getMMSI();
    }

    public int getTypeId() {
        return this.message.getMessageType().getCode();
    }

    public String getRawNMEA() {
        NMEAMessage[] nmeaSentence = this.message.getNmeaMessages();
        if (nmeaSentence.length > 1) {
            this.isMultiPart = TRUE;
            StringBuilder messages = new StringBuilder();

            for (int i = 0; i < nmeaSentence.length; i++) {
                messages.append(nmeaSentence[i].getRawMessage());

                if (i != nmeaSentence.length - 1) {
                    messages.append(" | ");
                }
            }
            return messages.toString();
        } else {
            return nmeaSentence[0].getRawMessage();
        }
    }
}
