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
package com.sencha.gxt.explorer.client.grid;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBean.PropertyName;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.HttpProxy;
import com.sencha.gxt.data.client.loader.XmlReader;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoadResultBean;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

@Detail(
    name = "Xml Grid",
    category = "Grid",
    icon = "xmlgrid",
    maxHeight = XmlGridExample.MAX_HEIGHT,
    maxWidth = XmlGridExample.MAX_WIDTH,
    minHeight = XmlGridExample.MIN_HEIGHT,
    minWidth = XmlGridExample.MIN_WIDTH
)
public class XmlGridExample implements IsWidget, EntryPoint {

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 480;

  private ContentPanel panel;

  interface XmlAutoBeanFactory extends AutoBeanFactory {
    static XmlAutoBeanFactory instance = GWT.create(XmlAutoBeanFactory.class);

    AutoBean<EmailCollection> items();

    AutoBean<ListLoadConfig> loadConfig();
  }

  interface Email {
    @PropertyName("Name")
    String getName();

    @PropertyName("Email")
    String getEmail();

    @PropertyName("Phone")
    String getPhone();

    @PropertyName("State")
    String getState();

    @PropertyName("Zip")
    String getZip();
  }

  interface EmailCollection {
    @PropertyName("record")
    List<Email> getValues();
  }

  interface EmailProperties extends PropertyAccess<Email> {
    ValueProvider<Email, String> name();

    ValueProvider<Email, String> email();

    ValueProvider<Email, String> phone();

    ValueProvider<Email, String> state();

    ValueProvider<Email, String> zip();
  }

  @Override
  public Widget asWidget() {
    if (panel == null) {
      String path = "data/data.xml";
      RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, path);
      HttpProxy<ListLoadConfig> httpProxy = new HttpProxy<ListLoadConfig>(builder);

      XmlReader<ListLoadResult<Email>, EmailCollection> reader = new XmlReader<ListLoadResult<Email>, EmailCollection>(
          XmlAutoBeanFactory.instance, EmailCollection.class) {
        protected ListLoadResult<Email> createReturnData(Object loadConfig, EmailCollection records) {
          return new ListLoadResultBean<Email>(records.getValues());
        }
      };

      ListStore<Email> store = new ListStore<Email>(new ModelKeyProvider<Email>() {
        @Override
        public String getKey(Email item) {
          return item.getEmail() + item.getName();
        }
      });

      final ListLoader<ListLoadConfig, ListLoadResult<Email>> loader = new ListLoader<ListLoadConfig, ListLoadResult<Email>>(httpProxy, reader);
      loader.useLoadConfig(XmlAutoBeanFactory.instance.create(ListLoadConfig.class).as());
      loader.addLoadHandler(new LoadResultListStoreBinding<ListLoadConfig, Email, ListLoadResult<Email>>(store));

      EmailProperties properties = GWT.create(EmailProperties.class);

      ColumnConfig<Email, String> senderColumn = new ColumnConfig<Email, String>(properties.name(), 125, "Sender");
      ColumnConfig<Email, String> emailColumn = new ColumnConfig<Email, String>(properties.email(), 100, "Email");
      ColumnConfig<Email, String> phoneColumn = new ColumnConfig<Email, String>(properties.phone(), 100, "Phone");
      ColumnConfig<Email, String> stateColumn = new ColumnConfig<Email, String>(properties.state(), 55, "State");
      ColumnConfig<Email, String> zipColumn = new ColumnConfig<Email, String>(properties.zip(), 80, "Zip Code");

      List<ColumnConfig<Email, ?>> l = new ArrayList<ColumnConfig<Email, ?>>();
      l.add(senderColumn);
      l.add(emailColumn);
      l.add(phoneColumn);
      l.add(stateColumn);
      l.add(zipColumn);

      ColumnModel<Email> cm = new ColumnModel<Email>(l);

      Grid<Email> grid = new Grid<Email>(store, cm);
      grid.getView().setAutoExpandColumn(emailColumn);
      grid.setLoadMask(true);
      grid.setLoader(loader);
      grid.getView().setEmptyText("Please hit the load button.");

      panel = new ContentPanel();
      panel.setHeading("Xml Grid");
      panel.add(grid);
      panel.setButtonAlign(BoxLayoutPack.CENTER);
      panel.addButton(new TextButton("Load Xml", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          loader.load();
        }
      }));
    }

    return panel;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMaxHeight(MAX_HEIGHT)
        .setMaxWidth(MAX_WIDTH)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
