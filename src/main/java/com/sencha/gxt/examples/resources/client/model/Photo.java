package com.sencha.gxt.examples.resources.client.model;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.sencha.gxt.core.client.util.Format;

public class Photo implements Serializable {

  private String name;
  private long size;
  private Date date;
  private String path;

  public Date getDate() {
    return date;
  }

  public String getName() {
    return name;
  }

  public String getPath() {
    return path;
  }
  
  public SafeUri getPathUri() {
    return UriUtils.fromString(getPath());
  }

  public String getShortName() {
    return Format.ellipse(name, 15);
  }

  public long getSize() {
    return size;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setSize(long size) {
    this.size = size;
  }

}
