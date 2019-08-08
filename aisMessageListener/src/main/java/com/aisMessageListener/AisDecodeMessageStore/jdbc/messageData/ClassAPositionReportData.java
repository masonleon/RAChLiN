package com.aisMessageListener.AisDecodeMessageStore.jdbc.messageData;

import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.PositionReport;

/**
 * TODO java doc
 */
public class ClassAPositionReportData extends AbstractMessageData {

  private final PositionReport positionReport;

  /**
   * TODO java doc
   */
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
  public int getNavStatusId() {
    int nav_status = 15;
    try {
      nav_status = positionReport.getNavigationStatus().getCode();
    } catch (NullPointerException e) {
    }
    return nav_status;
  }

  @Override
  public int getManeuverIndicatorId() {
    int indicator = 0;
    try {
      indicator = positionReport.getSpecialManeuverIndicator().getCode();
    } catch (NullPointerException e) {
    }
    return indicator;

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
