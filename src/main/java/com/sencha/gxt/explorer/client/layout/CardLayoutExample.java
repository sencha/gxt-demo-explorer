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
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.examples.resources.client.ExampleStyles;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

@Detail(
    name = "Card Layout",
    category = "Layouts",
    icon = "cardlayout",
    minHeight = CardLayoutExample.MIN_HEIGHT,
    minWidth = CardLayoutExample.MIN_WIDTH
)
public class CardLayoutExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 150;
  protected static final int MIN_WIDTH = 425;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      TextButton button1 = new TextButton("Card 1");
      TextButton button2 = new TextButton("Card 2");
      TextButton button3 = new TextButton("Card 3");
      TextButton button4 = new TextButton("Card 4");
      
      final Label label1 = new Label("Pellentesque elementum a nisl a cursus. Maecenas quis nibh nisl. Etiam interdum a leo eget ultricies. Vivamus eu diam tincidunt, convallis lacus non, euismod eros.");
      final Label label2 = new Label("Fusce tristique blandit finibus. Integer eget vestibulum est. Duis maximus quis eros sit amet bibendum. Cras et nisi a ante semper vestibulum. In hac habitasse platea dictumst. Phasellus tempus dui vel velit sollicitudin, quis cursus turpis luctus. Aenean convallis varius cursus. Nunc ut eros quis neque semper aliquam id nec.");
      final Label label3 = new Label("Quisque nec facilisis lectus. Morbi nunc augue, commodo et cursus ut, viverra non dolor. Nam id magna rhoncus, porta libero condimentum, facilisis magna. Donec convallis ex id libero mollis, vel dignissim augue ultrices. Integer pulvinar sapien eget sapien lobortis, vel lobortis tellus pulvinar. Vestibulum non feugiat mi. Morbi pellentesque molestie vestibulum. Mauris gravida libero sit amet nunc varius mattis. Nam a justo ac nulla blandit ullamcorper ac sit amet lectus. Nulla imperdiet et eros et.");
      final Label label4 = new Label("Nam sit amet mauris quis mi ornare sollicitudin. Duis vitae nisi pellentesque, consequat nisi nec, mattis lorem. Vivamus et pretium ex. Mauris posuere in ante rutrum hendrerit. Ut malesuada libero turpis, sit amet sodales dui blandit nec. Integer vitae lorem congue, consequat risus vel, blandit nulla. Nulla sagittis nibh felis.Sed non auctor nulla, et dapibus orci. Vestibulum dapibus erat eu laoreet interdum. Morbi mollis orci elit, vel condimentum ante fermentum sed. Nunc dapibus arcu diam, non tristique ligula mollis nec. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Proin et vulputate magna, vitae tristique felis.");
      
      label1.addStyleName(ExampleStyles.get().text());
      label2.addStyleName(ExampleStyles.get().text());
      label3.addStyleName(ExampleStyles.get().text());
      label4.addStyleName(ExampleStyles.get().text());

      final SimpleContainer card1 = new SimpleContainer();
      final SimpleContainer card2 = new SimpleContainer();
      final SimpleContainer card3 = new SimpleContainer();
      final SimpleContainer card4 = new SimpleContainer();
      
      card1.add(label1);
      card2.add(label2);
      card3.add(label3);
      card4.add(label4);

      final CardLayoutContainer cardLayout = new CardLayoutContainer();
      cardLayout.add(card1);
      cardLayout.add(card2);
      cardLayout.add(card3);
      cardLayout.add(card4);
      cardLayout.setActiveWidget(card1);

      panel = new ContentPanel();
      panel.setHeading("Card Layout");
      panel.add(cardLayout, new MarginData(20));
      panel.addButton(new LabelToolItem("Switch Cards:"));
      panel.addButton(button1);
      panel.addButton(button2);
      panel.addButton(button3);
      panel.addButton(button4);

      button1.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          cardLayout.setActiveWidget(card1);
        }
      });

      button2.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          cardLayout.setActiveWidget(card2);
        }
      });

      button3.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          cardLayout.setActiveWidget(card3);
        }
      });

      button4.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          cardLayout.setActiveWidget(card4);
        }
      });
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
