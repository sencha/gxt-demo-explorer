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
package com.sencha.gxt.explorer.client.html;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.Anchor;
import com.sencha.gxt.core.client.Style.AnchorAlignment;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.button.IconButton.IconConfig;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

@Detail(
  name = "Material Icons",
  category = "HTML",
  icon = "buttons",
  files = { "material-button-styles.gss" },
  minHeight = MatericalDesignButtonExample.MIN_HEIGHT,
  minWidth = MatericalDesignButtonExample.MIN_WIDTH)
public class MatericalDesignButtonExample implements IsWidget, EntryPoint {

  public interface MaterialButtonResources extends ClientBundle {
    public static MaterialButtonResources INSTANCE = GWT.create(MaterialButtonResources.class);

    public interface MaterialButtonStyles extends CssResource {
      String toolButton();

      String toolButtonOver();
    }

    @Source({ "material-button-styles.gss" })
    MaterialButtonStyles styles();
  }

  protected static final int MIN_HEIGHT = 410;
  protected static final int MIN_WIDTH = 515;
  
  private CenterLayoutContainer centerLayoutContainer;

  

  @Override
  public Widget asWidget() {
    if (centerLayoutContainer == null) {
      // only needed once
      MaterialButtonResources.INSTANCE.styles().ensureInjected();
      
      // attach material resources
      LinkElement link = Document.get().createLinkElement();
      link.setHref("https://fonts.googleapis.com/icon?family=Material+Icons");
      link.setRel("stylesheet");
      Document.get().getElementsByTagName("head").getItem(0).appendChild(link);

      ToolButton button1 = createMaterialButton("menu", "blue", 36);
      ToolButton button2 = createMaterialButton("more_vert", "green", 36);
      ToolButton button3 = createMaterialButton("error_outline", "red", 36);

      FlowLayoutContainer toolbar = new FlowLayoutContainer();
      toolbar.getElement().getStyle().setBackgroundColor("white");
      toolbar.setBorders(true);
      toolbar.setHeight("38px");
      toolbar.add(button1);
      toolbar.add(button2);
      toolbar.add(button3);
      
      centerLayoutContainer = new CenterLayoutContainer();
      centerLayoutContainer.add(toolbar);
    }
    return centerLayoutContainer;
  }

  /**
   * Sizing: https://google.github.io/material-design-icons/#sizing
   */
  public ToolButton createMaterialButton(String iconName, String color, int size) {
    // define the styles for the button
    String toolStyle = MaterialButtonResources.INSTANCE.styles().toolButton();
    String toolOverStyle = MaterialButtonResources.INSTANCE.styles().toolButtonOver();

    // setup the tool button config
    IconConfig toolConfig = new IconConfig(toolStyle, toolOverStyle);

    // instantiate the button for use
    final ToolButton button = new ToolButton(toolConfig);
    button.setPixelSize(size, size);
    button.addStyleName("material-icons");
    button.addStyleName("md-" + size);
    button.getElement().getStyle().setFontSize(size, Unit.PX);
    button.getElement().getStyle().setColor(color);
    button.getElement().setInnerText(iconName);
    button.addSelectHandler(new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        fireSelectEvent(button);
      }
    });

    return button;
  }

  protected void fireSelectEvent(ToolButton button) {
    Menu menu = new Menu();
    menu.add(new MenuItem("Item 1"));
    menu.add(new MenuItem("Item 2"));
    menu.add(new MenuItem("Item 3"));
    menu.add(new MenuItem("Item 4"));
    menu.add(new MenuItem("Item 5"));

    // Align Menu to button
    AnchorAlignment alignTo = new AnchorAlignment(Anchor.TOP_LEFT, Anchor.BOTTOM_LEFT, true);
    menu.show(button.getElement(), alignTo, 0, 0);
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinHeight(MIN_HEIGHT).setMinWidth(MIN_WIDTH).doStandalone();
  }

}
