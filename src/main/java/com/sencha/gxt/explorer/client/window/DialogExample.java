/**
 * Sencha GXT 1.0.0-SNAPSHOT - Sencha for GWT
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
package com.sencha.gxt.explorer.client.window;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Rectangle;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

@Detail(
    name = "Dialog",
    category = "Windows",
    icon = "dialog",
    minWidth = DialogExample.MIN_WIDTH,
    preferredHeight = DialogExample.PREFERRED_HEIGHT,
    classes = { TestData.class }
)
public class DialogExample implements IsWidget, EntryPoint {

  protected static final int MIN_WIDTH = 192;
  protected static final int PREFERRED_HEIGHT = -1;

  private ButtonBar buttonBar;

  @Override
  public Widget asWidget() {
    if (buttonBar == null) {
      final Dialog simple = new Dialog();
      simple.setHeading("Dialog — Simple");
      simple.setWidth(300);
      simple.setResizable(false);
      simple.setHideOnButtonClick(true);
      simple.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.NO);
      simple.setBodyStyleName("pad-text");
      simple.getBody().addClassName("pad-text");
      simple.add(new Label(TestData.DUMMY_TEXT_SHORT));

      // Layout - west
      BorderLayoutData dataWest = new BorderLayoutData(150);
      dataWest.setMargins(new Margins(0, 5, 0, 0));

      ContentPanel panelWest = new ContentPanel();
      panelWest.setHeading("West");
      panelWest.setLayoutData(dataWest);

      // Layout - center
      ContentPanel panelCenter = new ContentPanel();
      panelCenter.setHeading("Center");

      // Layout - container
      BorderLayoutContainer layoutComplex = new BorderLayoutContainer();
      layoutComplex.setWestWidget(panelWest);
      layoutComplex.setCenterWidget(panelCenter);

      // Layout
      final Dialog complex = new Dialog();
      complex.setHeading("Dialog — Layout");
      complex.setPixelSize(320, 240);
      complex.setResizable(false);
      complex.setBodyBorder(false);
      complex.setHideOnButtonClick(true);
      complex.add(layoutComplex);

      // Auto Size - See the JavaDoc for Window for more information on auto size
      final FlowLayoutContainer flc = new FlowLayoutContainer();
      flc.add(new HTML("Press Add or Remove to modify content"));

      final Dialog autoSize = new Dialog();
      autoSize.setHeading("Dialog — Auto Size");
      autoSize.autoSize();
      autoSize.setResizable(false);
      autoSize.setBodyBorder(false);
      autoSize.setHideOnButtonClick(true);
      autoSize.setShadow(true);
      autoSize.add(flc);
      autoSize.addButton(new TextButton("Add", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          // add content
          addAutoSizeContent(flc);
          autoSize.forceLayout();

          // constrain the dialog to the viewport as it grows
          Rectangle bounds = autoSize.getElement().getBounds();
          Rectangle adjusted = autoSize.getElement().adjustForConstraints(bounds);
          if (adjusted.getWidth() != bounds.getWidth() || adjusted.getHeight() != bounds.getHeight()) {
            autoSize.setPixelSize(adjusted.getWidth(), adjusted.getHeight());
          }

          // keep the dialog centered
          autoSize.center();
        }
      }));
      autoSize.addButton(new TextButton("Remove", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          // remove content
          int lastWidget = flc.getWidgetCount() - 1;
          if (lastWidget > 0) {
            flc.remove(lastWidget);
          }
          autoSize.forceLayout();

          // keep the dialog centered
          autoSize.center();
        }
      }));

      // Add some basic content to begin with
      for (int i = 0; i < 5; i++) {
        addAutoSizeContent(flc);
      }

      TextButton buttonSimple = new TextButton("Simple");
      buttonSimple.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          // show the dialog
          simple.show();

          // constrain the dialog to the viewport (for small mobile screen sizes)
          Rectangle bounds = simple.getElement().getBounds();
          Rectangle adjusted = simple.getElement().adjustForConstraints(bounds);
          if (adjusted.getWidth() != bounds.getWidth() || adjusted.getHeight() != bounds.getHeight()) {
            simple.setPixelSize(adjusted.getWidth(), adjusted.getHeight());
          }
        }
      });

      TextButton buttonLayout = new TextButton("Layout");
      buttonLayout.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          // show the dialog
          complex.show();

          // constrain the dialog to the viewport (for small mobile screen sizes)
          Rectangle bounds = complex.getElement().getBounds();
          Rectangle adjusted = complex.getElement().adjustForConstraints(bounds);
          if (adjusted.getWidth() != bounds.getWidth() || adjusted.getHeight() != bounds.getHeight()) {
            complex.setPixelSize(adjusted.getWidth(), adjusted.getHeight());
          }
        }
      });

      TextButton buttonAutoSize = new TextButton("Auto Size");
      buttonAutoSize.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          // show the dialog
          autoSize.show();

          // constrain the dialog to the viewport (for small mobile screen sizes)
          Rectangle bounds = autoSize.getElement().getBounds();
          Rectangle adjusted = autoSize.getElement().adjustForConstraints(bounds);
          if (adjusted.getWidth() != bounds.getWidth() || adjusted.getHeight() != bounds.getHeight()) {
            autoSize.setPixelSize(adjusted.getWidth(), adjusted.getHeight());
          }
        }
      });

      final List<Dialog> dialogs = Arrays.asList(new Dialog[] {simple, complex, autoSize});
      final Set<Dialog> openDialogs = new HashSet<Dialog>(dialogs.size());
      buttonBar = new ButtonBar() {
        @Override
        public void setVisible(boolean visible) {
          super.setVisible(visible);
          if (visible) {
            for (Dialog dialog : dialogs) {
              if (openDialogs.contains(dialog)) {
                dialog.show();
              }
            }
          } else {
            openDialogs.clear();
            for (final Dialog dialog : dialogs) {
              if (dialog.isVisible()) {
                openDialogs.add(dialog);
                dialog.hide();
              }
            }
          }
        }
      };
      buttonBar.add(buttonSimple);
      buttonBar.add(buttonLayout);
      buttonBar.add(buttonAutoSize);
    }

    return buttonBar;
  }

  private void addAutoSizeContent(FlowLayoutContainer flc) {
    int widgetCount = flc.getWidgetCount();

    SafeHtmlBuilder s = new SafeHtmlBuilder();
    s.appendEscaped(new Date().toString());
    for (int i = 0; i < widgetCount; i++) {
      s.appendHtmlConstant(" *");
    }

    HTML html = new HTML(s.toSafeHtml());
    html.addStyleName("nowrap");

    flc.add(html);
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinWidth(MIN_WIDTH)
        .setPreferredHeight(PREFERRED_HEIGHT)
        .doStandalone();
  }

}
