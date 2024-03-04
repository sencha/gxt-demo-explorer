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
package com.sencha.gxt.explorer.client.dnd;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.dnd.core.client.DndDragStartEvent;
import com.sencha.gxt.dnd.core.client.DndDropEvent;
import com.sencha.gxt.dnd.core.client.DragSource;
import com.sencha.gxt.dnd.core.client.DropTarget;
import com.sencha.gxt.examples.resources.client.ExampleStyles;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

@Detail(
    name = "Basic DND",
    category = "Drag & Drop",
    icon = "basicdnd",
    minHeight = BasicDndExample.MIN_HEIGHT,
    minWidth = BasicDndExample.MIN_WIDTH
)
public class BasicDndExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 400;
  protected static final int MIN_WIDTH = 400;

  private ContentPanel widget;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      final FlowLayoutContainer dropContainer = new FlowLayoutContainer();
      dropContainer.setBorders(true);

      DropTarget target = new DropTarget(dropContainer) {
        @Override
        protected void onDragDrop(DndDropEvent event) {
          super.onDragDrop(event);
          HTML html = (HTML) event.getData();
          dropContainer.add(html);
        }
      };
      target.setGroup("test");
      target.setOverStyle("drag-ok");

      final FlowLayoutContainer dragContainer = new FlowLayoutContainer();

      addSources(dragContainer);

      TextButton reset = new TextButton("Reset");
      reset.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          dropContainer.clear();
          dragContainer.clear();
          addSources(dragContainer);
        }
      });

      HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();
      hlc.add(dropContainer, new HorizontalLayoutData(0.5, 1, new Margins(10, 0, 10, 10)));
      hlc.add(dragContainer, new HorizontalLayoutData(0.5, 1));

      widget = new ContentPanel();
      widget.setHeading("Basic DND");
      widget.add(hlc);
      widget.addButton(reset);
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

  private void addSources(FlowLayoutContainer container) {
    for (int i = 1; i <= 5; i++) {
      final SafeHtmlBuilder builder = new SafeHtmlBuilder();
      String div = "<div style=\""
          + "font-size: 1.25em; "
          + "background-color: " + ThemeStyles.get().backgroundColorLight() + "; "
          + "border: 1px dashed " + ThemeStyles.get().borderColor() + "; "
          + "margin: 10px; "
          + "padding: 10px; "
          + "cursor: default; "
          + "\" "
          + "class=\"" + ExampleStyles.get().paddedText() + "\""
          + ">";
      builder.appendHtmlConstant(div);
      builder.appendHtmlConstant("Drag Me #" + i);
      builder.appendHtmlConstant("</div>");

      final HTML html = new HTML(builder.toSafeHtml());
      container.add(html);

      DragSource source = new DragSource(html) {
        @Override
        protected void onDragStart(DndDragStartEvent event) {
          super.onDragStart(event);
          // by default drag is allowed
          event.setData(html);
          event.getStatusProxy().update(builder.toSafeHtml());
        }
      };
      // group is optional
      source.setGroup("test");
    }
  }

}
