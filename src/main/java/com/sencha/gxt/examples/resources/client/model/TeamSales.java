package com.sencha.gxt.examples.resources.client.model;

public class TeamSales {

  private String month;
  private int a;
  private int b;
  private int c;
  
  
  public TeamSales(String month, int a, int b, int c) {
    setMonth(month);
    setAlphaSales(a);
    setBetaSales(b);
    setGammaSales(c);
//    setAvgSales();
  }

  public int getAlphaSales() {
    return a;
  }

  public int getBetaSales() {
    return b;
  }

  public int getGammaSales() {
    return c;
  }

  public String getMonth() {
    return month;
  }
  
//  @Override
//  public void notify(ChangeEvent evt) {
//    super.notify(evt);
//
//    PropertyChangeEvent e = (PropertyChangeEvent) evt;
//    if (!e.getName().equals("avgsales")) {
//      setAvgSales();
//    }
//  }

  public void setAlphaSales(int sales) {
    this.a = sales;
  }

//  public void setAvgSales() {
//    if (get("alphasales") != null && get("gammasales") != null
//        && get("betasales") != null) {
//      double avg = (getAlphaSales() + getBetaSales() + getGammaSales()) / 3.0;
//      set("avgsales", avg);
//    }
//  }

  public void setBetaSales(int sales) {
    this.b = sales;
  }

  public void setGammaSales(int sales) {
    this.c = sales;

  }

  public void setMonth(String month) {
    this.month = month;
  }
}
