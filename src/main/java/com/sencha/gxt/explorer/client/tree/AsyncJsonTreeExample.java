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
package com.sencha.gxt.explorer.client.tree;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.HttpProxy;
import com.sencha.gxt.data.client.writer.UrlEncodingWriter;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.loader.ChildTreeStoreBinding;
import com.sencha.gxt.data.shared.loader.JsonReader;
import com.sencha.gxt.data.shared.loader.TreeLoader;
import com.sencha.gxt.examples.resources.client.images.ExampleImages;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.tree.Tree;

@Detail(
    name = "Async Json Tree",
    category = "Tree",
    icon = "asyncjsontree",
    minHeight = AsyncJsonTreeExample.MIN_HEIGHT,
    minWidth = AsyncJsonTreeExample.MIN_WIDTH
)
public class AsyncJsonTreeExample implements IsWidget, EntryPoint {

  public interface JsonTreeAutoBeanFactory extends AutoBeanFactory {
    AutoBean<RecordResult> items();
  }

  /**
   * Defines the structure of our JSON records.
   */
  public interface Record {
    Integer getId();

    String getName();

    boolean isFolder();
  }

  /**
   * Defines the structure of the root JSON object being returned by the server. This class is needed as we cannot
   * return a list of objects. Instead, we return a single object with a single property that contains the data records.
   */
  public interface RecordResult {
    List<Record> getRecords();
  }

  private class RecordKeyProvider implements ModelKeyProvider<Record> {
    @Override
    public String getKey(Record item) {
      return item.getId().toString();
    }
  }

  private class DataRecordJsonReader extends JsonReader<List<Record>, RecordResult> {
    public DataRecordJsonReader(AutoBeanFactory factory, Class<RecordResult> rootBeanType) {
      super(factory, rootBeanType);
    }

    @Override
    protected List<Record> createReturnData(Object loadConfig, RecordResult incomingData) {
      return incomingData.getRecords();
    }
  }

  protected static final int MIN_HEIGHT = 240;
  protected static final int MIN_WIDTH = 320;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      JsonTreeAutoBeanFactory factory = GWT.create(JsonTreeAutoBeanFactory.class);

      DataRecordJsonReader reader = new DataRecordJsonReader(factory, RecordResult.class);

      RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, GWT.getModuleBaseURL() + "jsontreeloader");

      HttpProxy<Record> jsonProxy = new HttpProxy<Record>(requestBuilder);
      jsonProxy.setWriter(new UrlEncodingWriter<Record>(factory, Record.class));

      TreeLoader<Record> loader = new TreeLoader<Record>(jsonProxy, reader) {
        @Override
        public boolean hasChildren(Record parent) {
          return parent.isFolder();
        }
      };

      TreeStore<Record> store = new TreeStore<Record>(new RecordKeyProvider());
      loader.addLoadHandler(new ChildTreeStoreBinding<Record>(store));

      final Tree<Record, String> tree = new Tree<Record, String>(store, new ValueProvider<Record, String>() {
        @Override
        public String getValue(Record object) {
          return object.getName();
        }

        @Override
        public void setValue(Record object, String value) {
        }

        @Override
        public String getPath() {
          return "name";
        }
      });
      tree.setLoader(loader);
      tree.getStyle().setLeafIcon(ExampleImages.INSTANCE.music());

      ToolBar buttonBar = new ToolBar();
      buttonBar.add(new TextButton("Expand All", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          tree.expandAll();
        }
      }));
      buttonBar.add(new TextButton("Collapse All", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          tree.collapseAll();
        }
      }));

      VerticalLayoutContainer vlc = new VerticalLayoutContainer();
      vlc.add(buttonBar, new VerticalLayoutData(1, -1));
      vlc.add(tree, new VerticalLayoutData(1, 1));

      panel = new ContentPanel();
      panel.setHeading("Async Json Tree");
      panel.add(vlc);
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
