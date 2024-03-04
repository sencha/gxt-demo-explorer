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
package com.sencha.gxt.explorer.client.tips;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.tips.ToolTip;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;

@Detail(
    name = "Tool Tips (UiBinder)",
    category = "Tips",
    icon = "tooltipsuibinder",
    files = {
        "ToolTipsUiBinderExample.ui.xml",
        "template.html"
    },
    minWidth = ToolTipsUiBinderExample.MIN_WIDTH,
    preferredHeight = ToolTipsUiBinderExample.PREFERRED_HEIGHT
)
public class ToolTipsUiBinderExample implements IsWidget, EntryPoint {

  public interface Renderer extends ToolTipConfig.ToolTipRenderer<Object>, XTemplates {
    @Override
    @XTemplate(source = "template.html")
    public SafeHtml renderToolTip(Object data);
  }

  interface MyUiBinder extends UiBinder<Widget, ToolTipsUiBinderExample> {
  }

  protected static final int MIN_WIDTH = 360;
  protected static final int PREFERRED_HEIGHT = -1;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  @UiField(provided = true)
  ButtonBar buttonBar;

  @UiField
  TextButton btn1;
  @UiField
  TextButton btn2;
  @UiField
  TextButton btn3;
  @UiField
  TextButton btn4;
  @UiField
  TextButton btn5;

  @Override
  public Widget asWidget() {
    if (buttonBar == null) {
      final List<ToolTip> tooltips = new ArrayList<ToolTip>(2);
      final Set<ToolTip> openTooltips = new HashSet<ToolTip>(2);
      buttonBar = new ButtonBar() {
        @Override
        public void setVisible(boolean visible) {
          super.setVisible(visible);
          if (visible) {
            for (ToolTip tooltip : tooltips) {
              if (openTooltips.contains(tooltip)) {
                tooltip.setVisible(true);
              }
            }
          } else {
            openTooltips.clear();
            for (ToolTip tooltip : tooltips) {
              if (tooltip.isVisible()) {
                openTooltips.add(tooltip);
                tooltip.setVisible(false);
              }
            }
          }
        }
      };
      uiBinder.createAndBindUi(this);
      tooltips.add(btn2.getToolTip());
      tooltips.add(btn5.getToolTip());
    }

    return buttonBar;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinWidth(MIN_WIDTH)
        .setPreferredHeight(PREFERRED_HEIGHT)
        .doStandalone();
  }

}
