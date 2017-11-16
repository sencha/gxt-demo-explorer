package com.sencha.gxt.examples.resources.client.model;

public class RadarData {
  private String name;
  private double data;

  public RadarData(String name, double data) {
    this.name = name;
    this.data = data;
  }

  public double getData() {
    return data;
  }

  public String getName() {
    return name;
  }

  public void setData(double data) {
    this.data = data;
  }

  public void setName(String name) {
    this.name = name;
  }
}
