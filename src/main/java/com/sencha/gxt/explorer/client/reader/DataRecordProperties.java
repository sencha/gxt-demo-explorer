package com.sencha.gxt.explorer.client.reader;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface DataRecordProperties extends PropertyAccess<DataRecord> {
  @Path("name")
  ModelKeyProvider<DataRecord> key();

  LabelProvider<DataRecord> name();
}
