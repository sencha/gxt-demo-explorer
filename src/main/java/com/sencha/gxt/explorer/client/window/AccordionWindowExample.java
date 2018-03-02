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
package com.sencha.gxt.explorer.client.window;

import static com.sencha.gxt.core.client.Style.Anchor.CENTER;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.AnchorAlignment;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.data.shared.IconProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.examples.resources.client.ExampleStyles;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.examples.resources.client.images.ExampleImages;
import com.sencha.gxt.examples.resources.client.model.NameImageModel;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.AccordionLayoutAppearance;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.ExpandMode;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.tree.Tree;

@Detail(
    name = "Accordion Window",
    category = "Windows",
    icon = "accordionwindow",
    preferredWidth = AccordionWindowExample.PREFERRED_WIDTH,
    preferredHeight = AccordionWindowExample.PREFERRED_HEIGHT,
    preferredMargin = AccordionWindowExample.PREFERRED_MARGIN,
    classes = { Utils.class, TestData.class }
)
public class AccordionWindowExample implements IsWidget, EntryPoint {

  protected static final int PREFERRED_WIDTH = 1;
  protected static final int PREFERRED_HEIGHT = 1;
  // keep margins for window shadow
  protected static final int PREFERRED_MARGIN = 2;

  private SimpleContainer container;
  private Window window;

  @Override
  public Widget asWidget() {
    if (container == null) {
      AccordionLayoutAppearance appearance = GWT.<AccordionLayoutAppearance> create(AccordionLayoutAppearance.class);

      final NameImageModel modelFamily = newItem("Family", null);
      final NameImageModel modelFriends = newItem("Friends", null);

      TreeStore<NameImageModel> store = new TreeStore<NameImageModel>(NameImageModel.KP);
      store.add(modelFamily);
      store.add(modelFamily, newItem("John", "user"));
      store.add(modelFamily, newItem("Olivia", "user-girl"));
      store.add(modelFamily, newItem("Noah", "user-kid"));
      store.add(modelFamily, newItem("Emma", "user-kid"));
      store.add(modelFamily, newItem("Liam", "user-kid"));
      store.add(modelFriends);
      store.add(modelFriends, newItem("Mason", "user"));
      store.add(modelFriends, newItem("Sophia", "user-girl"));
      store.add(modelFriends, newItem("Isabella", "user-girl"));
      store.add(modelFriends, newItem("Jacob", "user"));

      Tree<NameImageModel, String> tree = new Tree<NameImageModel, String>(store,
          new ValueProvider<NameImageModel, String>() {
            @Override
            public String getValue(NameImageModel object) {
              return object.getName();
            }

            @Override
            public void setValue(NameImageModel object, String value) {
            }

            @Override
            public String getPath() {
              return "name";
            }
          }) {
        @Override
        protected void onAfterFirstAttach() {
          super.onAfterFirstAttach();
          
          setExpanded(modelFamily, true);
          setExpanded(modelFriends, true);
        }
      };
      tree.setIconProvider(new IconProvider<NameImageModel>() {
        public ImageResource getIcon(NameImageModel model) {
          if (null == model.getImage()) {
            return null;
          } else if ("user-girl" == model.getImage()) {
            return ExampleImages.INSTANCE.userFemale();
          } else if ("user-kid" == model.getImage()) {
            return ExampleImages.INSTANCE.userKid();
          } else {
            return ExampleImages.INSTANCE.user();
          }
        }
      });

      ContentPanel cp1 = new ContentPanel(appearance);
      cp1.setHeading("Online Users");
      cp1.add(tree);
      if (Theme.BLUE.isActive() || Theme.GRAY.isActive()) {
        cp1.getHeader().addStyleName(ThemeStyles.get().style().borderTop());
      }

      ContentPanel cp2 = new ContentPanel(appearance);
      cp2.setBodyStyleName(ExampleStyles.get().paddedText());
      cp2.setHeading("Settings");
      cp2.add(new Label(TestData.DUMMY_TEXT_SHORT));

      ContentPanel cp3 = new ContentPanel(appearance);
      cp3.setBodyStyleName(ExampleStyles.get().paddedText());
      cp3.setHeading("Stuff");
      cp3.add(new Label(TestData.DUMMY_TEXT_SHORT));

      ContentPanel cp4 = new ContentPanel(appearance);
      cp4.setBodyStyleName(ExampleStyles.get().paddedText());
      cp4.setHeading("More Stuff");
      cp4.add(new Label(TestData.DUMMY_TEXT_SHORT));

      AccordionLayoutContainer accordion = new AccordionLayoutContainer();
      accordion.setExpandMode(ExpandMode.SINGLE_FILL);
      accordion.add(cp1);
      accordion.add(cp2);
      accordion.add(cp3);
      accordion.add(cp4);
      accordion.setActiveWidget(cp1);

      container = new SimpleContainer() {

        @Override
        public void setVisible(boolean visible) {
          super.setVisible(visible);
          if (visible) {
            window.show();
            Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
              @Override
              public void execute() {
                // since this is deferred, check that window is actually visible
                if (window.isVisible()) {
                  window.alignTo(container.getElement(), new AnchorAlignment(CENTER, CENTER), 0, 0);
                }
              }
            });
          } else {
            window.hide();
          }
        }
      };

      window = new Window();
      window.setResizable(false);
      window.setHeading("Accordion Window");
      window.setWidth(275);
      window.setHeight(350);
      window.add(accordion);
      window.setClosable(false);
      window.getDraggable().setContainer(container);
    }
    return container;
  }

  private NameImageModel newItem(String text, String iconStyle) {
    return new NameImageModel(text, iconStyle);
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
            .setPreferredWidth(PREFERRED_WIDTH)
            .setPreferredHeight(PREFERRED_HEIGHT)
            .setPreferredMargin(PREFERRED_MARGIN)
        .doStandalone();
  }

}
