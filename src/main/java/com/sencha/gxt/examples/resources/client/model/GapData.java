package com.sencha.gxt.examples.resources.client.model;

public class GapData {
  private String name;
  private double gapless;
  private double gapped;

  public GapData(String name, double gapless, double gapped) {
    this.name = name;
    this.gapless = gapless;
    this.gapped = gapped;
  }

  public double getGapless() {
    return gapless;
  }

  public double getGapped() {
    return gapped;
  }

  public String getName() {
    return name;
  }

  public void setGapless(double gapless) {
    this.gapless = gapless;
  }

  public void setGapped(double gapped) {
    this.gapped = gapped;
  }

  public void setName(String name) {
    this.name = name;
  }
}
