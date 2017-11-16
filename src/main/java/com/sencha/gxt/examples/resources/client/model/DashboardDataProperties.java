package com.sencha.gxt.examples.resources.client.model;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.model.Data;

/**
 *
 */
public interface DashboardDataProperties extends PropertyAccess<Data> {
  @Path("data1")
  ValueProvider<Data, Double> price();

  @Path("data2")
  ValueProvider<Data, Double> revenue();

  @Path("data3")
  ValueProvider<Data, Double> growth();

  @Path("data4")
  ValueProvider<Data, Double> product();

  @Path("data5")
  ValueProvider<Data, Double> market();

  ValueProvider<Data, String> name();

  @Path("id")
  ModelKeyProvider<Data> nameKey();
}
