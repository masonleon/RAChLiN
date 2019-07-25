package com.aisMessageListener.AisDecodeMessageStore.jdbc;

import dk.tbsalling.aismessages.ais.messages.AISMessage;
import dk.tbsalling.aismessages.ais.messages.types.ManeuverIndicator;
import dk.tbsalling.aismessages.ais.messages.types.NavigationStatus;

public class ClassAPositionReport extends AbstractMessageData {

  private double lat;
  private double lon;
  private int accuracy;
  private NavigationStatus navStatus;
  private ManeuverIndicator maneuverIndicator;
  private double speedOverGround;
  private double courseOverGround;
  private double heading;
  private double rateOfTurn;

//  public ClassAPositionReport(ClassAPositionReport message) {
//    super(message);
//  }

  public ClassAPositionReport(AISMessage message) {
    super(message);
//    super(message);
//  }
  }

  public double getLat(){

//    if(message.getMessageType().equals())
//    return this.message.getRate
    return 0.0;
  }
}
