package com.sencha.gxt.examples.resources.client.model;

import java.util.HashMap;

public class ModelItem extends HashMap<String, Double> {
  private String key;

  public ModelItem(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
