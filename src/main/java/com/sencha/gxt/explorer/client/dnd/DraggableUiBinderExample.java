package com.sencha.gxt.explorer.client.dnd;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.fx.client.Draggable;
import com.sencha.gxt.widget.core.client.ContentPanel;

@Detail(
    name = "Draggable (UiBinder)",
    category = "Drag & Drop",
    icon = "draggableuibinder",
    files = "DraggableUiBinderExample.ui.xml",
    minHeight = DraggableUiBinderExample.MIN_HEIGHT,
    minWidth = DraggableUiBinderExample.MIN_WIDTH,
    classes = { TestData.class }
)
public class DraggableUiBinderExample implements IsWidget, EntryPoint {

  interface MyUiBinder extends UiBinder<Widget, DraggableUiBinderExample> {
  }

  protected static final int MIN_HEIGHT = -1;
  protected static final int MIN_WIDTH = 480;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  @UiField(provided = true)
  String dummyTextShort = TestData.DUMMY_TEXT_SHORT;

  @UiField(provided = true)
  ContentPanel cp1 = new ContentPanel() {
    @Override
    protected void onAfterFirstAttach() {
      super.onAfterFirstAttach();

      Draggable draggable = new Draggable(this);
      draggable.setContainer(getParent().getParent());
    }
  };
  @UiField(provided = true)
  ContentPanel cp2 = new ContentPanel() {
    @Override
    protected void onAfterFirstAttach() {
      super.onAfterFirstAttach();

      Draggable draggable = new Draggable(this, getHeader());
      draggable.setContainer(getParent().getParent());
      draggable.setUseProxy(false);
    }
  };
  @UiField(provided = true)
  ContentPanel cp3 = new ContentPanel() {
    @Override
    protected void onAfterFirstAttach() {
      super.onAfterFirstAttach();

      Draggable draggable = new Draggable(this, getHeader());
      draggable.setContainer(getParent().getParent());
      draggable.setConstrainHorizontal(true);
    }
  };

  private Widget widget;

  @Override
  public Widget asWidget() {
    if (widget == null) {
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
