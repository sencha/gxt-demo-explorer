package com.sencha.gxt.examples.resources.client.model;

public class State {

  private String abbr;
  private String name;
  private String slogan;

  public State() {
  }

  public State(String abbr, String name, String slogan) {
    this();
    setAbbr(abbr);
    setName(name);
    setSlogan(slogan);
  }

  public String getSlogan() {
    return slogan;
  }

  public void setSlogan(String slogan) {
    this.slogan = slogan;
  }

  public String getAbbr() {
    return abbr;
  }

  public void setAbbr(String abbr) {
    this.abbr = abbr;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
