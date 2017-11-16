package com.sencha.gxt.examples.resources.client.model;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.model.RadarData;

/**
 *
 */
public interface RadarDataProperties extends PropertyAccess<RadarData> {

  ValueProvider<RadarData, Double> data();

  ValueProvider<RadarData, String> name();

  @Path("name")
  ModelKeyProvider<RadarData> nameKey();
}
