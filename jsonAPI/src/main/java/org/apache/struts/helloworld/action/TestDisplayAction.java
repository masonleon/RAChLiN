package org.apache.struts.helloworld.action;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts.helloworld.model.JSONQueryResult;

public class TestDisplayAction extends ActionSupport {
  private static final long serialVersionUID = 1L;

  /**
   * The model class that stores the message to display in the view.
   */
  private JSONQueryResult queryResult;

  /*
   * Creates the MessageStore model object and
   * returns success.  The MessageStore model
   * object will be available to the view.
   * (non-Javadoc)
   * @see com.opensymphony.xwork2.ActionSupport#execute()
   */
  public String execute() {
    queryResult = JSONQueryResult.getInstance();
    return SUCCESS;
  }

  public JSONQueryResult getQueryResult() {
    return queryResult;
  }
}