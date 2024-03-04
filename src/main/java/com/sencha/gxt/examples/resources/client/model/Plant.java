/**
 * Sencha GXT 1.0.0-SNAPSHOT - Sencha for GWT
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

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class Plant {

  private DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/y");
  private static int AUTO_ID = 0;

  private int id;
  private String name;
  private String light;
  private double price;
  private Date available;
  private boolean indoor;
  private String color;
  private int difficulty;
  private double progress;

  public Plant() {
    id = AUTO_ID++;

    difficulty = (int) (Math.random() * 100);
    progress = Math.random();

  }

  public Plant(String name, String light, double price, String available, boolean indoor) {
    this();
    setName(name);
    setLight(light);
    setPrice(price);
    setAvailable(df.parse(available));
    setIndoor(indoor);
  }

  public double getProgress() {
    return progress;
  }

  public void setProgress(double progress) {
    this.progress = progress;
  }

  public String getColor() {
    return color;
  }

  public int getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(int difficulty) {
    this.difficulty = difficulty;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Date getAvailable() {
    return available;
  }

  public int getId() {
    return id;
  }

  public String getLight() {
    return light;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public boolean isIndoor() {
    return indoor;
  }

  public void setAvailable(Date available) {
    this.available = available;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setIndoor(boolean indoor) {
    this.indoor = indoor;
  }

  public void setLight(String light) {
    this.light = light;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return name != null ? name : super.toString();
  }

}
