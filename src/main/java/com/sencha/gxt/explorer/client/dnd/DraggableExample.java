package com.sencha.gxt.explorer.client.dnd;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.fx.client.Draggable;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;

@Detail(
    name = "Draggable",
    category = "Drag & Drop",
    icon = "draggable",
    minHeight = DraggableExample.MIN_HEIGHT,
    minWidth = DraggableExample.MIN_WIDTH, 
    classes = { TestData.class }
)
public class DraggableExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = -1;
  protected static final int MIN_WIDTH = 480;

  private HorizontalLayoutContainer panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      ContentPanel cp1 = new ContentPanel() {
        private Draggable draggable;
        @Override
        protected void onAfterFirstAttach() {
          super.onAfterFirstAttach();

          draggable = new Draggable(this);
        }

        @Override
        protected void onAttach() {
          super.onAttach();
          draggable.setContainer(getParent().getParent());
        }
      };
      cp1.setBodyStyleName("pad-text");
      cp1.setHeading("Draggable — Proxy Drag");
      cp1.add(new Label(TestData.DUMMY_TEXT_SHORT));

      ContentPanel cp2 = new ContentPanel() {
        private Draggable draggable;
        @Override
        protected void onAfterFirstAttach() {
          super.onAfterFirstAttach();

          draggable = new Draggable(this, getHeader());
          draggable.setUseProxy(false);
        }

        @Override
        protected void onAttach() {
          super.onAttach();
          draggable.setContainer(getParent().getParent());
        }
      };
      cp2.setBodyStyleName("pad-text");
      cp2.setHeading("Draggable — Direct Drag");
      cp2.add(new Label("Drags can only be started from the header."));

      ContentPanel cp3 = new ContentPanel() {
        private Draggable draggable;
        @Override
        protected void onAfterFirstAttach() {
          super.onAfterFirstAttach();

          draggable = new Draggable(this, getHeader());
          draggable.setConstrainHorizontal(true);
        }

        @Override
        protected void onAttach() {
          super.onAttach();
          draggable.setContainer(getParent().getParent());
        }
      };
      cp3.setBodyStyleName("pad-text");
      cp3.setHeading("Draggable — Constrain");
      cp3.add(new Label("Can only be dragged vertically."));

      panel = new HorizontalLayoutContainer();
      panel.add(cp1, new HorizontalLayoutData(0.33, -1, new Margins(10)));
      panel.add(cp2, new HorizontalLayoutData(0.33, -1, new Margins(10)));
      panel.add(cp3, new HorizontalLayoutData(0.33, -1, new Margins(10)));
    }

    return panel;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
