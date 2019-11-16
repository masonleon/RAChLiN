package io.seeport.harborpilot.jdbc.messageData;

import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.PositionReport;

/**
 * Extension of message to support Class A position reports (i.e message types 1, 2, 3).
 */
public final class ClassAPositionReportData extends AbstractMessageData {

  private static final int DEFAULT_MANEUVER_INDICATOR_ID = 0;
  private static final int DEFAULT_NAV_STATUS_ID = 15;

  private final PositionReport positionReport;

  /**
   * Constructor initializes a ClassAPositionReportData message using an AIS message.
   *
   * @param message an AISMessage object
   * @throws IllegalArgumentException if the AIS message isn't a {@link PositionReport}.
   */
  public ClassAPositionReportData(AISMessage message) throws IllegalArgumentException {
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
    try {
      return positionReport.getNavigationStatus().getCode();
    } catch (NullPointerException e) {
        return DEFAULT_NAV_STATUS_ID;
    }
  }

  @Override
  public int getManeuverIndicatorId() {
    try {
      return positionReport.getSpecialManeuverIndicator().getCode();
    } catch (NullPointerException e) {
        return DEFAULT_MANEUVER_INDICATOR_ID;
    }

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
