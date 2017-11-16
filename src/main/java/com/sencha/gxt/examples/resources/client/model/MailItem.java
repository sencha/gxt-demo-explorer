package com.sencha.gxt.examples.resources.client.model;

public class MailItem {

  private String sender;
  private String email;
  private String subject;
  private String body;

  public MailItem() {

  }

  public MailItem(String sender, String email, String subject) {
    this();
    setSender(sender);
    setEmail(email);
    setSubject(subject);
  }

  public MailItem(String sender, String email, String subject, String body) {
    this(sender, email, subject);
    this.body = body;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public String getBody() {
    return body;

  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getSubject() {
    return subject;

  }
}
