package com.sencha.gxt.examples.resources.client.model;

public class Movies {

  private int year;
  private double action;
  private double comedy;
  private double drama;
  private double thriller;
  
  public Movies(int year, double action, double comedy, double drama, double thriller) {
    this.year = year;
    this.action = action;
    this.comedy = comedy;
    this.drama = drama;
    this.thriller = thriller;
  }

  public double getAction() {
    return action;
  }

  public double getComedy() {
    return comedy;
  }

  public double getDrama() {
    return drama;
  }

  public double getThriller() {
    return thriller;
  }

  public int getYear() {
    return year;
  }

  public void setAction(double action) {
    this.action = action;
  }

  public void setComedy(double comedy) {
    this.comedy = comedy;
  }

  public void setDrama(double drama) {
    this.drama = drama;
  }

  public void setThriller(double thriller) {
    this.thriller = thriller;
  }

  public void setYear(int year) {
    this.year = year;
  }
  
}
