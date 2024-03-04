/**
 * Sencha GXT 1.1.0-SNAPSHOT - Sencha for GWT
 * Copyright (c) 2006-2021, Sencha Inc.
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
package com.sencha.gxt.explorer.client.layout;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

@Detail(
    name = "Vertical Box Layout (UiBinder)",
    category = "Layouts",
    icon = "vboxlayoutuibinder",
    files = "VBoxLayoutUiBinderExample.ui.xml",
    minHeight = VBoxLayoutUiBinderExample.MIN_HEIGHT,
    minWidth = VBoxLayoutUiBinderExample.MIN_WIDTH,
    classes = { Utils.class }
)
public class VBoxLayoutUiBinderExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 480;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  interface MyUiBinder extends UiBinder<Widget, VBoxLayoutUiBinderExample> {
  }

  @UiField(provided = true)
  String toolbarStyle = !Theme.BLUE.isActive() && !Theme.GRAY.isActive() ? "x-toolbar-mark" : "noop";

  @UiField
  VBoxLayoutContainer buttonBox;
  @UiField
  CardLayoutContainer layout;
  @UiField
  ToggleButton spaced, multiSpaced, alignLeft, alignCenter, alignRight, alignStretch, alignStretchMax, flexAllEven,
      flexRatio, flexStretch, packStart, packCenter, packEnd;

  private Widget widget;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      widget = uiBinder.createAndBindUi(this);

      ToggleGroup toggleGroup = new ToggleGroup();
      toggleGroup.add(spaced);
      toggleGroup.add(multiSpaced);
      toggleGroup.add(alignLeft);
      toggleGroup.add(alignCenter);
      toggleGroup.add(alignRight);
      toggleGroup.add(alignStretch);
      toggleGroup.add(alignStretchMax);
      toggleGroup.add(flexAllEven);
      toggleGroup.add(flexRatio);
      toggleGroup.add(flexStretch);
      toggleGroup.add(packStart);
      toggleGroup.add(packCenter);
      toggleGroup.add(packEnd);
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

  @UiHandler({
      "spaced", "multiSpaced", "alignLeft", "alignCenter", "alignRight", "alignStretch", "alignStretchMax",
      "flexAllEven", "flexRatio", "flexStretch", "packStart", "packCenter", "packEnd"})
  public void buttonClicked(SelectEvent event) {
    ToggleButton button = (ToggleButton) event.getSource();

    int index = buttonBox.getWidgetIndex(button);
    layout.setActiveWidget(layout.getWidget(index + 1));
  }

}
