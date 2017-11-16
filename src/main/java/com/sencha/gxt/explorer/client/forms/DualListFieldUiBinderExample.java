package com.sencha.gxt.explorer.client.forms;

import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.State;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.form.DualListField;
import com.sencha.gxt.widget.core.client.form.DualListField.Mode;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;

@Detail(
    name = "Dual List Field (UiBinder)",
    category = "Forms",
    icon = "duallistfielduibinder",
    files = "DualListFieldUiBinderExample.ui.xml",
    classes = { State.class, TestData.class },
    minHeight = DualListFieldUiBinderExample.MIN_HEIGHT,
    minWidth = DualListFieldUiBinderExample.MIN_WIDTH
)
public class DualListFieldUiBinderExample implements IsWidget, EntryPoint {

  interface StateProperties extends PropertyAccess<State> {
    ModelKeyProvider<State> abbr();

    LabelProvider<State> name();

    @Path("name")
    ValueProvider<State, String> nameProp();
  }

  interface MyUiBinder extends UiBinder<Widget, DualListFieldUiBinderExample> {
  }

  protected static final int MIN_HEIGHT = 340;
  protected static final int MIN_WIDTH = 420;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  @UiField(provided = true)
  DualListField<State, String> field;

  private Widget widget;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      StateProperties properties = GWT.create(StateProperties.class);

      ListStore<State> states = new ListStore<State>(properties.abbr());
      states.addAll(TestData.getStates());

      ListStore<State> toStates = new ListStore<State>(properties.abbr());

      field = new DualListField<State, String>(states, toStates, properties.nameProp(), new TextCell());
      field.addValidator(new EmptyValidator<List<State>>());
      field.setEnableDnd(true);
      field.setMode(Mode.INSERT);

      widget = uiBinder.createAndBindUi(this);
    }

    return widget;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
