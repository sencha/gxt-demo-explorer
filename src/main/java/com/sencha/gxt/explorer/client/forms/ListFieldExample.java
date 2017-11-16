package com.sencha.gxt.explorer.client.forms;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.State;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.examples.resources.client.model.StockProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.ListField;

@Detail(
  name = "List Field",
  category = "Forms",
  icon = "formsexample",
  classes = { Stock.class, StockProperties.class, TestData.class, State.class },
  minHeight = ListFieldExample.MIN_HEIGHT,
  minWidth = ListFieldExample.MIN_WIDTH)
public class ListFieldExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 400;
  protected static final int MIN_WIDTH = 400;

  interface StateProperties extends PropertyAccess<State> {
    ModelKeyProvider<State> abbr();

    LabelProvider<State> name();

    @Path("name")
    ValueProvider<State, String> nameProp();
  }

  private FramedPanel widget;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      StateProperties properties = GWT.create(StateProperties.class);

      ListStore<State> states = new ListStore<State>(properties.abbr());
      states.addAll(TestData.getStates());

      ListView<State, String> listView = new ListView<State, String>(states, properties.nameProp());

      ListField<State, String> listField = new ListField<State, String>(listView);

      FieldLabel fieldLabel = new FieldLabel(listField, "ListField");

      widget = new FramedPanel();
      widget.setHeading("List Field Example");
      widget.add(fieldLabel, new MarginData(30));
    }

    return widget;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinHeight(MIN_HEIGHT).setMinWidth(MIN_WIDTH).doStandalone();
  }

}