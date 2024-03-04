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
package com.sencha.gxt.explorer.client.button;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonArrowAlign;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonScale;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.examples.resources.client.Resources;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.CellButtonBase;
import com.sencha.gxt.widget.core.client.button.SplitButton;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

@Detail(
    name = "Buttons",
    category = "Button",
    icon = "buttons",
    files = "ButtonExample.gss",
    minHeight = ButtonExample.MIN_HEIGHT,
    minWidth = ButtonExample.MIN_WIDTH
)
public class ButtonExample implements IsWidget, EntryPoint {

  public interface ExampleStyle extends CssResource {
    String header();

    String section();
  }

  public interface ExampleResources extends ClientBundle {
    @Source("ButtonExample.gss")
    ExampleStyle style();
  }

  enum Category {
    NORMAL("Buttons — Normal", "Normal"), MENU("Buttons — Menu", "Menu"), MENUBOTTOM("Buttons — Menu Bottom (arrow on bottom)",
        "Menu Bottom"), SPLIT("Buttons — Split", "Split"), SPLITBOTTOM("Buttons — Split Bottom (arrow on bottom)", "Split Bottom"), TOGGLE(
        "Buttons — Toggle", "Toggle");

    private String text;
    private String desc;

    Category(String text, String desc) {
      this.text = text;
      this.desc = desc;
    }

    String getText() {
      return text;
    }
  }

  enum Type {
    BOTTOM("Icon and Text (bottom)"), ICON("Icon Only"), LEFT("Icon and Text (left)"), RIGHT("Icon and Text (right)"), TEXT(
        "Text Only"), TOP("Icon and Text (top)");

    private String text;

    Type(String text) {
      this.text = text;
    }

    String getText() {
      return text;
    }
  }

  protected static final int MIN_HEIGHT = 410;
  protected static final int MIN_WIDTH = 515;

  private ContentPanel panel;
  private CardLayoutContainer cardLayoutContainer;
  private ExampleStyle style;
  private Map<Category, FlowLayoutContainer> created = new HashMap<Category, FlowLayoutContainer>();

  private FlowLayoutContainer createButtons(Category cat) {
    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.setSpacing(20);
    verticalPanel.setWidth("400px");

    for (Type type : Type.values()) {
      CellButtonBase<?> small = createButton(cat, type);
      CellButtonBase<?> medium = createButton(cat, type);
      CellButtonBase<?> large = createButton(cat, type);

      configureButton(small, type, ButtonScale.SMALL);
      configureButton(medium, type, ButtonScale.MEDIUM);
      configureButton(large, type, ButtonScale.LARGE);

      HorizontalPanel hp = new HorizontalPanel();
      hp.setSpacing(5);
      hp.add(small);
      hp.add(medium);
      hp.add(large);

      verticalPanel.add(format(type.getText()));
      verticalPanel.add(hp);
    }

    FlowLayoutContainer flowLayoutContainer = new FlowLayoutContainer();
    flowLayoutContainer.getScrollSupport().setScrollMode(ScrollMode.AUTO);
    flowLayoutContainer.add(verticalPanel);

    cardLayoutContainer.add(flowLayoutContainer);

    return flowLayoutContainer;
  }

  @Override
  public Widget asWidget() {
    if (panel == null) {
      ExampleResources bundle = GWT.create(ExampleResources.class);
      style = bundle.style();
      style.ensureInjected();

      cardLayoutContainer = new CardLayoutContainer();

      panel = new ContentPanel();
      panel.add(cardLayoutContainer);

      ToggleGroup group = new ToggleGroup();

      for (Category cat : Category.values()) {
        final ToggleButton button = new ToggleButton(cat.desc);
        button.setData("cat", cat);
        button.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
          @Override
          public void onValueChange(ValueChangeEvent<Boolean> event) {
            if (event.getValue() == true) {
              onClick((Category) button.getData("cat"));
            }
          }
        });

        group.add(button);
        panel.addButton(button);
      }

      ToggleButton normal = (ToggleButton) panel.getButtonBar().getWidget(0);
      normal.setValue(true, true);
    }

    return panel;
  }

  private void onClick(Category cat) {
    FlowLayoutContainer flowLayoutContainer = created.get(cat);
    if (flowLayoutContainer == null) {
      flowLayoutContainer = createButtons(cat);
      created.put(cat, flowLayoutContainer);
    }
    cardLayoutContainer.setActiveWidget(flowLayoutContainer);
    panel.setHeading(cat.getText());
  }

  private void configureButton(CellButtonBase<?> btn, Type type, ButtonScale scale) {
    btn.setScale(scale);
    switch (type) {

      case LEFT:
        btn.setIconAlign(IconAlign.LEFT);
        break;
      case RIGHT:
        btn.setIconAlign(IconAlign.RIGHT);
        break;
      case BOTTOM:
        btn.setIconAlign(IconAlign.BOTTOM);
        break;
      case TOP:
        btn.setIconAlign(IconAlign.TOP);
        break;
      default:
        // do nothing
    }

    switch (type) {
      case ICON:
        setIcon(btn, scale);
        break;
      case TEXT:
        btn.setText("Add User");
        break;
      default: {
        setIcon(btn, scale);
        btn.setText("Add User");
      }
    }
  }

  private CellButtonBase<?> createButton(Category cat, Type type) {
    CellButtonBase<?> btn = null;
    switch (cat) {
      case NORMAL:
        btn = new TextButton();
        break;
      case TOGGLE:
        btn = new ToggleButton();
        break;
      case MENU:
        btn = new TextButton();
        btn.setMenu(createMenu());
        break;
      case MENUBOTTOM:
        btn = new TextButton();
        btn.setMenu(createMenu());
        btn.setArrowAlign(ButtonArrowAlign.BOTTOM);
        break;
      case SPLIT:
        btn = new SplitButton();
        btn.setMenu(createMenu());
        break;
      case SPLITBOTTOM:
        btn = new SplitButton();
        btn.setMenu(createMenu());
        btn.setArrowAlign(ButtonArrowAlign.BOTTOM);
        break;
    }

    return btn;
  }

  private Menu createMenu() {
    Menu menu = new Menu();
    menu.add(new MenuItem("Menu Item 1"));
    menu.add(new MenuItem("Menu Item 2"));
    menu.add(new MenuItem("Menu Item 3"));
    return menu;
  }

  private HTML format(String text) {
    HTML html = new HTML(text);
    html.addStyleName(style.section());

    return html;
  }

  private void setIcon(CellButtonBase<?> btn, ButtonScale scale) {
    switch (scale) {
      case SMALL:
        btn.setIcon(Resources.IMAGES.add16());
        break;
      case MEDIUM:
        btn.setIcon(Resources.IMAGES.add24());
        break;

      case LARGE:
        btn.setIcon(Resources.IMAGES.add32());
        break;
      default:
        // do nothing
    }
  }

  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
