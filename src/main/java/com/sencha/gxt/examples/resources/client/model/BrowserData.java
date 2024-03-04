/**
 * Sencha GXT 1.1.0-SNAPSHOT - Sencha for GWT
 * Copyright (c) 2006-2021, Sencha Inc.
 *
 * licensing@sencha.com
 * http://www.sencha.com/products/gxt/license/
 *
 * ================================================================================
 * Commercial License
 * ================================================================================
 * This version of Sencha GXT is licensed commercially and is the appropriate
 * option for the vast majority of use cases.
 *
 * Please see the Sencha GXT Licensing page at:
 * http://www.sencha.com/products/gxt/license/
 *
 * For clarification or additional options, please contact:
 * licensing@sencha.com
 * ================================================================================
 *
 *
 *
 *
 *
 *
 *
 *
 * ================================================================================
 * Disclaimer
 * ================================================================================
 * THIS SOFTWARE IS DISTRIBUTED "AS-IS" WITHOUT ANY WARRANTIES, CONDITIONS AND
 * REPRESENTATIONS WHETHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, MERCHANTABLE QUALITY,
 * FITNESS FOR A PARTICULAR PURPOSE, DURABILITY, NON-INFRINGEMENT, PERFORMANCE AND
 * THOSE ARISING BY STATUTE OR FROM CUSTOM OR USAGE OF TRADE OR COURSE OF DEALING.
 * ================================================================================
 */
package com.sencha.gxt.examples.resources.client.model;

public class BrowserData {

  private double IE;
  private double Firefox;
  private double Chrome;
  private double Safari;
  private double Opera;
  private double Other;
  private String date;

  public BrowserData() {
  }

  public BrowserData(double iE, double firefox, double chrome, double safari, double opera, double other, String date) {
    super();
    IE = iE;
    this.Firefox = firefox;
    this.Chrome = chrome;
    this.Safari = safari;
    this.Opera = opera;
    this.Other = other;
    this.date = date;
  }

  public double getChrome() {
    return Chrome;
  }

  public String getDate() {
    return date;
  }

  public double getFirefox() {
    return Firefox;
  }

  public double getIE() {
    return IE;
  }

  public double getOpera() {
    return Opera;
  }

  public double getOther() {
    return Other;
  }

  public double getSafari() {
    return Safari;
  }

  public void setChrome(double chrome) {
    this.Chrome = chrome;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setFirefox(double firefox) {
    this.Firefox = firefox;
  }

  public void setIE(double iE) {
    IE = iE;
  }

  public void setOpera(double opera) {
    this.Opera = opera;
  }

  public void setOther(double other) {
    this.Other = other;
  }

  public void setSafari(double safari) {
    this.Safari = safari;
  }

}
