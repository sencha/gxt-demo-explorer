package com.sencha.gxt.examples.resources.client.model;

import java.io.Serializable;
import java.util.Date;

public class Stock implements Serializable {

  private Integer id;
  private Double change;
  private Date date = new Date();
  private String industry = getType();
  private Double last;
  private String name;
  private Double open;
  private String symbol;
  private boolean split = Boolean.valueOf(Math.random() > .5);
  
  private static int COUNTER = 0;

  public Stock() {
    this.id = Integer.valueOf(COUNTER++);
  }

  public Stock(String name, double open, double change, double pctChange, Date date, String industry) {
    this();
    this.name = name;
    this.open = open;
    this.change = change;
    this.date = date;
    this.industry = industry;
  }

  public Stock(String name, String symbol, double open, double last, Date date) {
    this();
    this.name = name;
    this.symbol = symbol;
    this.change = last - open;
    this.open = open;
    this.last = last;
    this.date = date;
  }

  public Double getChange() {
    return change;
  }

  public Integer getId() {
    return id;
  }

  public String getIndustry() {
    return industry;
  }

  public Double getLast() {
    return last;
  }

  public Date getLastTrans() {
    return date;
  }

  public String getName() {
    return name;
  }

  public Double getOpen() {
    return open;
  }

  /**
   * Read-only property, based on other values
   * 
   * @return the percent change
   */
  public double getPercentChange() {
    return getChange() / getOpen();
  }

  public String getSymbol() {
    return symbol;
  }

  public boolean isSplit() {
    return split;
  }

  public void setChange(Double change) {
    this.change = change;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  public void setLast(Double last) {
    this.last = last;
  }

  public void setLastTrans(Date date) {
    this.date = date;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setOpen(Double open) {
    this.open = open;
  }

  public void setSplit(boolean split) {
    this.split = split;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String toString() {
    return getName();
  }
  
  private String getType() {
    double r = Math.random();
    if (r <= .25) {
      return "Auto";
    } else if (r > .25 && r <= .50) {
      return "Media";
    } else if (r > .5 && r <= .75) {
      return "Medical";
    } else {
      return "Tech";
    }
  }
}
