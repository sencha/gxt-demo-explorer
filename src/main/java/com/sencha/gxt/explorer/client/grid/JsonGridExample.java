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
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.HttpProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.loader.JsonReader;
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
    name = "Json Grid",
    category = "Grid",
    icon = "jsongrid",
    maxHeight = JsonGridExample.MAX_HEIGHT,
    maxWidth = JsonGridExample.MAX_WIDTH,
    minHeight = JsonGridExample.MIN_HEIGHT,
    minWidth = JsonGridExample.MIN_WIDTH
)
public class JsonGridExample implements IsWidget, EntryPoint {

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 480;

  private ContentPanel panel;

  public interface EmailAutoBeanFactory extends AutoBeanFactory {
    AutoBean<RecordResult> items();

    AutoBean<ListLoadConfig> loadConfig();
  }

  public interface Email {
    String getName();

    String getEmail();

    String getPhone();

    String getState();

    String getZip();
  }

  /**
   * Defines the structure of the root JSON object being returned by the server.
   * This class is needed as we cannot return a list of objects. Instead, we
   * return a single object with a single property that contains the data
   * records.
   */
  public interface RecordResult {
    List<Email> getRecords();
  }

  class DataRecordJsonReader extends JsonReader<ListLoadResult<Email>, RecordResult> {
    public DataRecordJsonReader(AutoBeanFactory factory, Class<RecordResult> rootBeanType) {
      super(factory, rootBeanType);
    }

    @Override
    protected ListLoadResult<Email> createReturnData(Object loadConfig, RecordResult incomingData) {
      return new ListLoadResultBean<Email>(incomingData.getRecords());
    }
  }

  interface EmailProperties extends PropertyAccess<Email> {
    @Path("name")
    ModelKeyProvider<Email> key();

    ValueProvider<Email, String> name();

    ValueProvider<Email, String> email();

    ValueProvider<Email, String> phone();

    ValueProvider<Email, String> state();

    ValueProvider<Email, String> zip();
  }

  @Override
  public Widget asWidget() {
    if (panel == null) {
      EmailAutoBeanFactory factory = GWT.create(EmailAutoBeanFactory.class);

      DataRecordJsonReader jsonReader = new DataRecordJsonReader(factory, RecordResult.class);

      String path = "data/data.json";
      RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, path);
      HttpProxy<ListLoadConfig> proxy = new HttpProxy<ListLoadConfig>(builder);

      final ListLoader<ListLoadConfig, ListLoadResult<Email>> loader = new ListLoader<ListLoadConfig, ListLoadResult<Email>>(proxy, jsonReader);
      loader.useLoadConfig(factory.create(ListLoadConfig.class).as());

      EmailProperties properties = GWT.create(EmailProperties.class);

      ListStore<Email> store = new ListStore<Email>(properties.key());
      loader.addLoadHandler(new LoadResultListStoreBinding<ListLoadConfig, Email, ListLoadResult<Email>>(store));

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
      grid.setLoader(loader);
      grid.setLoadMask(true);
      grid.getView().setEmptyText("Please hit the load button.");
      grid.getView().setAutoExpandColumn(emailColumn);

      panel = new ContentPanel();
      panel.setHeading("Json Grid");
      panel.add(grid);
      panel.setButtonAlign(BoxLayoutPack.CENTER);
      panel.addButton(new TextButton("Load Json", new SelectHandler() {
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
