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
