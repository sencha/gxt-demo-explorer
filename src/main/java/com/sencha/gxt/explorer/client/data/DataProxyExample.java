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
package com.sencha.gxt.explorer.client.data;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoadResultBean;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.container.MarginData;

@Detail(
  name = "ListLoader and DataProxy",
  category = "Data",
  icon = "basicbinding",
  minHeight = DataProxyExample.MIN_HEIGHT,
  minWidth = DataProxyExample.MIN_WIDTH)
public class DataProxyExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 330;
  protected static final int MIN_WIDTH = 330;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      ListStore<Company> store = new ListStore<Company>(new ModelKeyProvider<Company>() {
        @Override
        public String getKey(Company item) {
          return item.getId();
        }
      });

      // Other data proxies can be used... Such as the RPC proxy...
      DataProxy<ListLoadConfig, ListLoadResult<Company>> proxy = new DataProxy<ListLoadConfig, ListLoadResult<Company>>() {
        @Override
        public void load(ListLoadConfig loadConfig, Callback<ListLoadResult<Company>, Throwable> callback) {
          // Data could be loaded from anywhere...
          ListLoadResult<Company> result = new ListLoadResultBean<Company>(getCompanies());
          callback.onSuccess(result);
        }
      };

      final ListLoader<ListLoadConfig, ListLoadResult<Company>> loader = new ListLoader<ListLoadConfig, ListLoadResult<Company>>(proxy);
      loader.addLoadHandler(new LoadResultListStoreBinding<ListLoadConfig, Company, ListLoadResult<Company>>(store));
      
      final ListView<Company, String> listView = new ListView<Company, String>(store, new ValueProvider<Company, String>() {
        @Override
        public String getValue(Company object) {
          return object.getName();
        }

        @Override
        public void setValue(Company object, String value) {
          object.setName(value);
        }

        @Override
        public String getPath() {
          return "name";
        }
      });
      listView.setLoader(loader);
      listView.addAttachHandler(new Handler() {
        @Override
        public void onAttachOrDetach(AttachEvent event) {
          // When it's attached, ask the loader to get the data
          if (listView.isAttached()) {
            loader.load();
          }
        }
      });

      panel = new ContentPanel();
      panel.setHeading("DataProxy with a ListLoader and ListView Example");
      panel.add(listView, new MarginData(20));
    }

    return panel;
  }

  private List<Company> getCompanies() {
    // Data could be loaded from anywhere
    final List<Company> companies = new ArrayList<Company>();
    for (int i = 0; i < 40; i++) {
      Company company = new Company("" + i, "Company " + i);
      companies.add(company);
    }
    return companies;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinHeight(MIN_HEIGHT).setMinWidth(MIN_WIDTH).doStandalone();
  }

  public static class Company {
    private String id;
    private String name;

    public Company(String id, String name) {
      this.id = id;
      this.name = name;
    }

    public String getId() {
      return id;
    }

    public String getName() {
      return name;
    }
    
    public void setName(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      String s = "Company {";
      s += "id:" + id + ",";
      s += "name:" + name;
      s += "}";
      return s;
    }
  }

}
