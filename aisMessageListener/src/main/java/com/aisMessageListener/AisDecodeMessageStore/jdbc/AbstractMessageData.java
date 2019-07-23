package com.aisMessageListener.AisDecodeMessageStore.jdbc;


public interface AbstractMessageData {

  public void checkIsSupported();

  public boolean getIsValidMsg();

  public int getMMSI();

  public int getMsgTypeId();

  public String getRawNMEA();

}

