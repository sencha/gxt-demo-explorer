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
package com.sencha.gxt.explorer.client.tips;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.Side;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.tips.ToolTip;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;

@Detail(
    name = "Tool Tips",
    category = "Tips",
    icon = "tooltips",
    files = "template.html",
    minWidth = ToolTipsExample.MIN_WIDTH,
    preferredHeight = ToolTipsExample.PREFERRED_HEIGHT
)
public class ToolTipsExample implements IsWidget, EntryPoint {

  public interface Renderer extends ToolTipConfig.ToolTipRenderer<Object>, XTemplates {
    @Override
    @XTemplate(source = "template.html")
    public SafeHtml renderToolTip(Object data);
  }

  protected static final int MIN_WIDTH = 360;
  protected static final int PREFERRED_HEIGHT = -1;

  private Renderer renderer = GWT.create(Renderer.class);
  private ButtonBar buttonBar;

  @Override
  public Widget asWidget() {
    if (buttonBar == null) {
      ToolTipConfig config1 = new ToolTipConfig("Information", "Prints the current document");

      ToolTipConfig config2 = new ToolTipConfig();
      config2.setTitle("Information");
      config2.setBody("Prints the current document");
      config2.setCloseable(true);

      ToolTipConfig config3 = new ToolTipConfig();
      config3.setTitle("Information");
      config3.setBody("Prints the current document");
      config3.setTrackMouse(true);

      ToolTipConfig config4 = new ToolTipConfig();
      config4.setTitle("Information");
      config4.setBody("Prints the current document");
      config4.setMouseOffsetX(0);
      config4.setMouseOffsetY(0);
      config4.setAnchor(Side.LEFT);

      ToolTipConfig config5 = new ToolTipConfig();
      config5.setBody("Prints the current document");
      config5.setTitle("Template Tip");
      config5.setMouseOffsetX(0);
      config5.setMouseOffsetY(0);
      config5.setAnchor(Side.LEFT);
      config5.setRenderer(renderer);
      config5.setCloseable(true);
      config5.setMaxWidth(415);

      TextButton btn1 = new TextButton("Basic");
      btn1.setToolTipConfig(config1);

      TextButton btn2 = new TextButton("Closable");
      btn2.setToolTipConfig(config2);

      TextButton btn3 = new TextButton("Mouse Tracking");
      btn3.setToolTipConfig(config3);

      TextButton btn4 = new TextButton("Anchor");
      btn4.setToolTipConfig(config4);

      TextButton btn5 = new TextButton("Custom");
      btn5.setToolTipConfig(config5);

      final List<ToolTip> tooltips = Arrays.asList(new ToolTip[] {btn2.getToolTip(), btn5.getToolTip()});
      final Set<ToolTip> openTooltips = new HashSet<ToolTip>(tooltips.size());
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
      buttonBar.add(btn1);
      buttonBar.add(btn2);
      buttonBar.add(btn3);
      buttonBar.add(btn4);
      buttonBar.add(btn5);
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
