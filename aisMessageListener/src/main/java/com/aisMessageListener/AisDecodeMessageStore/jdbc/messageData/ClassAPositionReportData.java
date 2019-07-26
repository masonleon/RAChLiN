package com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData;

import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.PositionReport;
import dk.tbsalling.aismessages.ais.messages.types.ManeuverIndicator;
import dk.tbsalling.aismessages.ais.messages.types.NavigationStatus;

public class ClassAPositionReportData extends AbstractMessageData {

    private final PositionReport positionReport;

    public ClassAPositionReportData(AISMessage message) {
        super(message);

        if (!(message instanceof PositionReport)) {
            throw new IllegalArgumentException("Message is not a position report!");
        }

        this.positionReport = (PositionReport) message;
    }

    public ClassAPositionReportData(PositionReport positionReport) {
        super(positionReport);

        this.positionReport = positionReport;
    }

    public double getLat() {
        return positionReport.getLatitude();
    }

    public double getLong() {
        return positionReport.getLongitude();
    }

    public int getAccuracy() {
        return positionReport.getPositionAccuracy() ? 1 : 0;
    }

    public NavigationStatus getNavStatus() {
        return positionReport.getNavigationStatus();
    }

    public ManeuverIndicator getManeuverIndicator() {
        return positionReport.getSpecialManeuverIndicator();
    }

    public double getSpeedOverGround() {
        return positionReport.getSpeedOverGround();
    }

    public double getCourseOverGround() {
        return positionReport.getCourseOverGround();
    }

    // TODO: internal implementations of both of these are integers?
    public double getHeading() {
        return positionReport.getTrueHeading();
    }

    public double getRateOfTurn() {
        return positionReport.getRateOfTurn();
    }
}
