package com.sencha.gxt.explorer.client.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public class NamedModel {
  public static final ModelKeyProvider<NamedModel> KEY = new ModelKeyProvider<NamedModel>() {
    @Override
    public String getKey(NamedModel item) {
      return item.getId().replaceAll("[^a-zA-Z0-9_]","_");
    }
  };
  public interface NamedModelProperties extends PropertyAccess<NamedModel> {
    ValueProvider<NamedModel, String> name();
  }

  private String name;

  protected NamedModel(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  public String getId() {
    if (getName().equals("% Columns")) {
      return "percentcolumns";
    }
    return getName().replaceAll(" ", "").toLowerCase();
  }
}
