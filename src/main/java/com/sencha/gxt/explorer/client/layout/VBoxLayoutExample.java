package com.sencha.gxt.explorer.client.layout;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;

@Detail(
    name = "Vertical Box Layout",
    category = "Layouts",
    icon = "vboxlayout",
    minHeight = VBoxLayoutExample.MIN_HEIGHT,
    minWidth = VBoxLayoutExample.MIN_WIDTH,
    classes = { Utils.class }
)
public class VBoxLayoutExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 480;

  private String button1Text = "Button 1";
  private String button2Text = "Button 2";
  private String button3Text = "Button 3";
  private String button4Text = "Button Long 4";
  private ContentPanel lccenter;
  private ToggleGroup toggleGroup = new ToggleGroup();
  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      BoxLayoutData vBoxData = new BoxLayoutData(new Margins(5, 5, 5, 5));
      vBoxData.setFlex(1);

      VBoxLayoutContainer lcwest = new VBoxLayoutContainer();
      if (!Theme.BLUE.isActive() && !Theme.GRAY.isActive()) {
        lcwest.addStyleName("x-toolbar-mark");
      }
      lcwest.setPadding(new Padding(5));
      lcwest.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);
      lcwest.add(createToggleButton("Spaced", new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (event.getValue()) {
            createVBoxLayoutSpaced();
          }
        }
      }), vBoxData);

      lcwest.add(createToggleButton("Multi-Spaced", new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (event.getValue()) {
            createVBoxLayoutMultiSpaced();
          }
        }
      }), vBoxData);

      lcwest.add(createToggleButton("Align: left", new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (event.getValue()) {
            createVBoxLayoutAlignLeft();
          }
        }
      }), vBoxData);

      lcwest.add(createToggleButton("Align: center", new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (event.getValue()) {
            createVBoxLayoutAlignCenter();
          }
        }
      }), vBoxData);

      lcwest.add(createToggleButton("Align: right", new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (event.getValue()) {
            createVBoxLayoutAlignRight();
          }
        }
      }), vBoxData);

      lcwest.add(createToggleButton("Align: stretch", new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (event.getValue()) {
            createVBoxLayoutAlignStretch();
          }
        }
      }), vBoxData);

      lcwest.add(createToggleButton("Align: stretchmax", new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (event.getValue()) {
            createVBoxLayoutAlignStretchmax();
          }
        }
      }), vBoxData);

      lcwest.add(createToggleButton("Flex: All even", new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (event.getValue()) {
            createVBoxLayoutAlignAllEven();
          }
        }
      }), vBoxData);

      lcwest.add(createToggleButton("Flex: ratio", new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (event.getValue()) {
            createVBoxLayoutAlignFlexRatio();
          }
        }
      }), vBoxData);

      lcwest.add(createToggleButton("Flex + Stretch", new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (event.getValue()) {
            createVBoxLayoutFlexStretch();
          }
        }
      }), vBoxData);

      lcwest.add(createToggleButton("Pack: start", new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (event.getValue()) {
            createVBoxLayoutPackStart();
          }
        }
      }), vBoxData);

      lcwest.add(createToggleButton("Pack: center", new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (event.getValue()) {
            createVBoxLayoutPackCenter();
          }
        }
      }), vBoxData);

      lcwest.add(createToggleButton("Pack: end", new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (event.getValue()) {
            createVBoxLayoutPackEnd();
          }
        }
      }), vBoxData);

      String html = "<p style=\"padding:10px;color:#556677;font-size:11px;\">Select a configuration on the left</p>";

      lccenter = new ContentPanel();
      lccenter.setHeaderVisible(false);
      lccenter.add(new HTML(html));

      BorderLayoutData west = new BorderLayoutData(150);
      west.setMargins(new Margins(5));

      MarginData center = new MarginData();

      BorderLayoutContainer borderLayoutContainer = new BorderLayoutContainer();
      borderLayoutContainer.setWestWidget(lcwest, west);
      borderLayoutContainer.setCenterWidget(lccenter, center);

      panel = new ContentPanel();
      panel.setHeading("Vertical Box Layout");
      panel.setWidget(borderLayoutContainer);
    }

    return panel;
  }

  private void createVBoxLayoutPackEnd() {
    BoxLayoutData layoutData = new BoxLayoutData(new Margins(0, 0, 5, 0));
    BoxLayoutData layoutData2 = new BoxLayoutData(new Margins(0));

    VBoxLayoutContainer c = new VBoxLayoutContainer();
    c.setPadding(new Padding(5));
    c.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
    c.setPack(BoxLayoutPack.END);
    c.add(new TextButton(button1Text), layoutData);
    c.add(new TextButton(button2Text), layoutData);
    c.add(new TextButton(button3Text), layoutData);
    c.add(new TextButton(button4Text), layoutData2);

    addToCenter(c);
  }

  private void createVBoxLayoutPackCenter() {
    BoxLayoutData layoutData = new BoxLayoutData(new Margins(0, 0, 5, 0));
    BoxLayoutData layoutData2 = new BoxLayoutData(new Margins(0));

    VBoxLayoutContainer c = new VBoxLayoutContainer();
    c.setPadding(new Padding(5));
    c.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
    c.setPack(BoxLayoutPack.CENTER);
    c.add(new TextButton(button1Text), layoutData);
    c.add(new TextButton(button2Text), layoutData);
    c.add(new TextButton(button3Text), layoutData);
    c.add(new TextButton(button4Text), layoutData2);

    addToCenter(c);
  }

  private void createVBoxLayoutPackStart() {
    BoxLayoutData layoutData = new BoxLayoutData(new Margins(0, 0, 5, 0));
    BoxLayoutData layoutData2 = new BoxLayoutData(new Margins(0));

    VBoxLayoutContainer c = new VBoxLayoutContainer();
    c.setPadding(new Padding(5));
    c.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
    c.setPack(BoxLayoutPack.START);
    c.add(new TextButton(button1Text), layoutData);
    c.add(new TextButton(button2Text), layoutData);
    c.add(new TextButton(button3Text), layoutData);
    c.add(new TextButton(button4Text), layoutData2);

    addToCenter(c);
  }

  private void createVBoxLayoutFlexStretch() {
    BoxLayoutData flex = new BoxLayoutData(new Margins(0, 0, 5, 0));
    flex.setFlex(1);

    BoxLayoutData flex2 = new BoxLayoutData(new Margins(0));
    flex2.setFlex(3);

    VBoxLayoutContainer c = new VBoxLayoutContainer();
    c.setPadding(new Padding(5));
    c.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);
    c.add(new TextButton(button1Text), flex);
    c.add(new TextButton(button2Text), flex);
    c.add(new TextButton(button3Text), flex);
    c.add(new TextButton(button4Text), flex2);

    addToCenter(c);
  }

  private void createVBoxLayoutAlignFlexRatio() {
    BoxLayoutData flex = new BoxLayoutData(new Margins(0, 0, 5, 0));
    flex.setFlex(1);

    BoxLayoutData flex2 = new BoxLayoutData(new Margins(0));
    flex2.setFlex(3);

    VBoxLayoutContainer c = new VBoxLayoutContainer();
    c.setPadding(new Padding(5));
    c.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
    c.add(new TextButton(button1Text), flex);
    c.add(new TextButton(button2Text), flex);
    c.add(new TextButton(button3Text), flex);
    c.add(new TextButton(button4Text), flex2);

    addToCenter(c);
  }

  private void createVBoxLayoutAlignAllEven() {
    BoxLayoutData flex = new BoxLayoutData(new Margins(0, 0, 5, 0));
    flex.setFlex(1);

    BoxLayoutData flex2 = new BoxLayoutData(new Margins(0));
    flex2.setFlex(1);

    VBoxLayoutContainer c = new VBoxLayoutContainer();
    c.setPadding(new Padding(5));
    c.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
    c.add(new TextButton(button1Text), flex);
    c.add(new TextButton(button2Text), flex);
    c.add(new TextButton(button3Text), flex);
    c.add(new TextButton(button4Text), flex2);

    addToCenter(c);
  }

  private void createVBoxLayoutAlignStretchmax() {
    VBoxLayoutContainer c = new VBoxLayoutContainer();
    c.setPadding(new Padding(5));
    c.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCHMAX);
    c.add(new TextButton(button1Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button2Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button3Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button4Text), new BoxLayoutData(new Margins(0)));

    addToCenter(c);
  }

  private void createVBoxLayoutAlignStretch() {
    VBoxLayoutContainer c = new VBoxLayoutContainer();
    c.setPadding(new Padding(5));
    c.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);
    c.add(new TextButton(button1Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button2Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button3Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button4Text), new BoxLayoutData(new Margins(0)));

    addToCenter(c);
  }

  private void createVBoxLayoutAlignRight() {
    VBoxLayoutContainer c = new VBoxLayoutContainer();
    c.setPadding(new Padding(5));
    c.setVBoxLayoutAlign(VBoxLayoutAlign.RIGHT);
    c.add(new TextButton(button1Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button2Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button3Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button4Text), new BoxLayoutData(new Margins(0)));

    addToCenter(c);
  }

  private void createVBoxLayoutAlignCenter() {
    VBoxLayoutContainer c = new VBoxLayoutContainer();
    c.setPadding(new Padding(5));
    c.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
    c.add(new TextButton(button1Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button2Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button3Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button4Text), new BoxLayoutData(new Margins(0)));

    addToCenter(c);
  }

  private void createVBoxLayoutAlignLeft() {
    VBoxLayoutContainer c = new VBoxLayoutContainer();
    c.setPadding(new Padding(5));
    c.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);
    c.add(new TextButton(button1Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button2Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button3Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button4Text), new BoxLayoutData(new Margins(0)));

    addToCenter(c);
  }

  private void createVBoxLayoutMultiSpaced() {
    BoxLayoutData flex = new BoxLayoutData(new Margins(0, 0, 5, 0));
    flex.setFlex(1);

    VBoxLayoutContainer c = new VBoxLayoutContainer();
    c.setPadding(new Padding(5));
    c.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);
    c.add(new TextButton(button1Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new Label(), flex);
    c.add(new TextButton(button2Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new Label(), flex);
    c.add(new TextButton(button3Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new Label(), flex);
    c.add(new TextButton(button4Text), new BoxLayoutData(new Margins(0)));

    addToCenter(c);
  }

  private void createVBoxLayoutSpaced() {
    BoxLayoutData flex = new BoxLayoutData(new Margins(0, 0, 5, 0));
    flex.setFlex(1);

    VBoxLayoutContainer c = new VBoxLayoutContainer();
    c.setPadding(new Padding(5));
    c.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);
    c.add(new TextButton(button1Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new Label(), flex);
    c.add(new TextButton(button2Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button3Text), new BoxLayoutData(new Margins(0, 0, 5, 0)));
    c.add(new TextButton(button4Text), new BoxLayoutData(new Margins(0)));

    addToCenter(c);
  }

  private void addToCenter(Widget c) {
    lccenter.clear();
    lccenter.add(c);
    lccenter.forceLayout();
  }

  private ToggleButton createToggleButton(String name, ValueChangeHandler<Boolean> handler) {
    ToggleButton button = new ToggleButton(name);
    button.addValueChangeHandler(handler);
    button.setAllowDepress(false);
    toggleGroup.add(button);

    return button;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
