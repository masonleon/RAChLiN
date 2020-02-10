package io.seeport.rachlin.aiswebserver.AisDecodeMessageStore.action;

import com.jsonAPI.AisDecodeMessageStore.model.JSONQueryResult;
import com.opensymphony.xwork2.ActionSupport;


public class MapAction extends ActionSupport {
  private static final long serialVersionUID = 1L;


  // The model class that retrieves the data from the built-in query.
  private JSONQueryResult queryResult;

  public String execute() {
    queryResult = JSONQueryResult.getInstance();
    return SUCCESS;
  }

  public JSONQueryResult getQueryResult() {
    return queryResult;
  }
}