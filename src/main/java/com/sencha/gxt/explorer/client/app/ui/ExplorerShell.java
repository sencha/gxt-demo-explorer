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
package com.sencha.gxt.explorer.client.app.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.state.client.BorderLayoutStateHandler;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;

public class ExplorerShell implements IsWidget {

  private BorderLayoutContainer borderLayoutContainer = new BorderLayoutContainer();

  public interface Resources extends ClientBundle {
    @NotStrict
    @Source("explorerStyles.gss")
    CssResource explorerStyles();

    @Source("hd-bg.gif")
    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    ImageResource classicBg();

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("square.gif")
    ImageResource squareBg();
  }

  @Inject
  public ExplorerShell(ExampleListView listView, ExampleDetailView detailView) {
    Resources resource = GWT.create(Resources.class);
    resource.explorerStyles().ensureInjected();

    BorderLayoutData northData = new BorderLayoutData(42);

    SimpleContainer themeComboContainer = new SimpleContainer();
    themeComboContainer.getElement().getStyle().setMargin(3, Unit.PX);
    themeComboContainer.setResize(false);
    themeComboContainer.add(getThemeCombo());

    HTML title = new HTML("Sencha GXT Explorer");
    title.addStyleName("demo-title");

    String slinks = "";
    slinks += "&nbsp;&nbsp;&nbsp;<a href=\"https://github.com/sencha/gxt-demo-explorer/\" target=\"_blank\">Explorer Source</a>";
    slinks += "&nbsp;&nbsp;&nbsp;<a href=\"http://docs.sencha.com/gxt/latest/\" target=\"_blank\">Documentation</a>";
    slinks += "&nbsp;&nbsp;&nbsp;<a href=\"https://www.sencha.com/products/evaluate/\" target=\"_blank\">Try it free</a>";
    slinks += "&nbsp;&nbsp;&nbsp;<a href=\"https://www.sencha.com/company/contact/\" target=\"_blank\">Contact Sales</a>";

    HTML links = new HTML(slinks);
    links.addStyleName("demo-links");

    BoxLayoutData spacerFlex = new BoxLayoutData();
    spacerFlex.setFlex(1);

    HBoxLayoutContainer menuContainer = new HBoxLayoutContainer(HBoxLayoutAlign.MIDDLE);
    menuContainer.setEnableOverflow(false);
    menuContainer.setStateful(false);
    menuContainer.add(title, new BoxLayoutData(new Margins(0, 15, 0, 0)));
    menuContainer.add(links, spacerFlex);
    menuContainer.add(themeComboContainer, new BoxLayoutData(new Margins(0, 10, 0, 15)));

    menuContainer.setId("demo-header");
    menuContainer.addStyleName("x-small-editor");

    BorderLayoutData westData = new BorderLayoutData(260);
    westData.setMargins(Theme.NEPTUNE.isActive() || Theme.TRITON.isActive() ? new Margins(0) : new Margins(5, 0, 5, 5));
    westData.setCollapsible(true);
    westData.setCollapseHeaderVisible(true);

    ContentPanel westContainer = new ContentPanel();
    westContainer.setHeading("Navigation");
    westContainer.add(listView.asWidget());

    MarginData centerData = new MarginData();
    centerData.setMargins(Theme.TRITON.isActive() ? new Margins(0, 0, 0, 10)
        : Theme.NEPTUNE.isActive() ? new Margins(0, 0, 0, 8) : new Margins(5));

    SimpleContainer centerContainer = new SimpleContainer();

    centerContainer.add(detailView.asWidget());

    borderLayoutContainer.setNorthWidget(menuContainer, northData);
    borderLayoutContainer.setWestWidget(westContainer, westData);
    borderLayoutContainer.setCenterWidget(centerContainer, centerData);

    // State Manager, init state
    borderLayoutContainer.setStateful(true);
    borderLayoutContainer.setStateId("explorerLayout");

    // State Manager, load previous state
    BorderLayoutStateHandler state = new BorderLayoutStateHandler(borderLayoutContainer);
    state.loadState();
  }

  private ComboBox<Theme> getThemeCombo() {
    ListStore<Theme> themes = new ListStore<Theme>(new ModelKeyProvider<Theme>() {
      @Override
      public String getKey(Theme item) {
        return item.name();
      }
    });

    Theme activeTheme = null;

    for (Theme theme : Theme.values()) {
      if (theme.isActive()) {
        // save off the active theme
        activeTheme = theme;
      }

      if (theme.isTouch()) {
        // touch enabled themes work on all devices (including non-touch desktop
        // browsers)
        themes.add(theme);
      } else if (!theme.isTouch() && (!GXT.isTouch() || GXT.isMSEdge())) {
        // non-touch enabled themes only work on non-touch desktop browsers or
        // MSEdge (both desktop & touch)
        themes.add(theme);
      }
    }

    ComboBox<Theme> themeCombo = new ComboBox<Theme>(themes, new StringLabelProvider<Theme>());
    themeCombo.setTriggerAction(TriggerAction.ALL);
    themeCombo.setForceSelection(true);
    themeCombo.setEditable(false);
    themeCombo.getElement().getStyle().setFloat(Float.RIGHT);
    themeCombo.setWidth(125);
    themeCombo.setValue(activeTheme);
    themeCombo.addSelectionHandler(new SelectionHandler<Theme>() {
      @Override
      public void onSelection(SelectionEvent<Theme> event) {
        switch (event.getSelectedItem()) {
        case BLUE:
          Window.Location.assign(GWT.getHostPageBaseURL() + "explorer-blue.html" + Window.Location.getHash());
          break;
        case GRAY:
          Window.Location.assign(GWT.getHostPageBaseURL() + "explorer-gray.html" + Window.Location.getHash());
          break;
        case NEPTUNE:
          Window.Location.assign(GWT.getHostPageBaseURL() + "explorer-neptune.html" + Window.Location.getHash());
          break;
        case TRITON:
          Window.Location.assign(GWT.getHostPageBaseURL() + "index.html" + Window.Location.getHash());
          break;
        default:
          assert false : "Unsupported theme enum";
        }
      }
    });

    return themeCombo;
  }

  @Override
  public Widget asWidget() {
    return borderLayoutContainer;
  }

  public AcceptsOneWidget getDisplay() {
    return borderLayoutContainer;
  }

}
