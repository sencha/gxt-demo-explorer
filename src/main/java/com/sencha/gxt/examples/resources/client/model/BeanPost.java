package com.sencha.gxt.examples.resources.client.model;

import java.io.Serializable;
import java.util.Date;


//public class BeanPost implements BeanModelTag, Serializable {
public class BeanPost implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -7341152423566838170L;
  private String username;
  private String forum;
  private Date date;
  private String subject;

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getForum() {
    return forum;
  }

  public void setForum(String forum) {
    this.forum = forum;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subjuct) {
    this.subject = subjuct;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
