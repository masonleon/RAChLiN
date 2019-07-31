package com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData;

import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.PositionReport;

public class ClassAPositionReportData extends AbstractMessageData {

    private final PositionReport positionReport;

    public ClassAPositionReportData(AISMessage message) {
        super(message);

        if (!(message instanceof PositionReport)) {
            throw new IllegalArgumentException("Message is not a position report!");
        }
        this.positionReport = (PositionReport) message;
    }

    @Override
    public Float getLat() {
        return positionReport.getLatitude();
    }

    @Override
    public Float getLong() {
        return positionReport.getLongitude();
    }

    @Override
    public Boolean getAccuracy() {
        return positionReport.getPositionAccuracy();
    }

    @Override
    public Integer getNavStatusId() {
        return positionReport.getNavigationStatus().getCode();
    }

    @Override
    public Integer getManeuverIndicatorId() {
        return positionReport.getSpecialManeuverIndicator().getCode();
    }

    @Override
    public Float getSpeedOverGround() {
        return positionReport.getSpeedOverGround();
    }

    @Override
    public Float getCourseOverGround() {
        return positionReport.getCourseOverGround();
    }

    // TODO: internal implementations of both of these are integers?
    @Override
    public Float getHeading() {
        return Float.valueOf(String.valueOf(positionReport.getTrueHeading()));
    }

    @Override
    public Float getRateOfTurn() {
        return Float.valueOf(String.valueOf(positionReport.getRateOfTurn()));
    }
}
