package com.sencha.gxt.examples.resources.client.model;


public interface BrowserProxy {

  public double getIE();

  public String getDate();

  public double getFirefox();

  public double getChrome();
  
  public double getSafari();
  
  public double getOpera();
  
  public double getOther();
  
  public void setIE(double IE);

  public void setDate(String date);

  public void setFirefox(double Firefox);

  public void setChrome(double Chrome);
  
  public void setSafari(double Safari);
  
  public void setOpera(double Opera);
  
  public void setOther(double Other);
}
