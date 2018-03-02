/**
 * Sencha GXT 1.0.0-SNAPSHOT - Sencha for GWT
 * Copyright (c) 2006-2018, Sencha Inc.
 *
 * licensing@sencha.com
 * http://www.sencha.com/products/gxt/license/
 *
 * ================================================================================
 * Commercial License
 * ================================================================================
 * This version of Sencha GXT is licensed commercially and is the appropriate
 * option for the vast majority of use cases.
 *
 * Please see the Sencha GXT Licensing page at:
 * http://www.sencha.com/products/gxt/license/
 *
 * For clarification or additional options, please contact:
 * licensing@sencha.com
 * ================================================================================
 *
 *
 *
 *
 *
 *
 *
 *
 * ================================================================================
 * Disclaimer
 * ================================================================================
 * THIS SOFTWARE IS DISTRIBUTED "AS-IS" WITHOUT ANY WARRANTIES, CONDITIONS AND
 * REPRESENTATIONS WHETHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, MERCHANTABLE QUALITY,
 * FITNESS FOR A PARTICULAR PURPOSE, DURABILITY, NON-INFRINGEMENT, PERFORMANCE AND
 * THOSE ARISING BY STATUTE OR FROM CUSTOM OR USAGE OF TRADE OR COURSE OF DEALING.
 * ================================================================================
 */
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
