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
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBean.PropertyName;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.ScriptTagProxy;
import com.sencha.gxt.data.client.loader.StorageReadProxy;
import com.sencha.gxt.data.client.loader.StorageWriteProxy;
import com.sencha.gxt.data.client.loader.StorageWriteProxy.Entry;
import com.sencha.gxt.data.client.writer.UrlEncodingWriter;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.JsonReader;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

@Detail(
    name = "Local Storage Grid",
    icon = "localstoragegrid",
    category = "Grid",
    maxHeight = LocalStorageGridExample.MAX_HEIGHT,
    maxWidth = LocalStorageGridExample.MAX_WIDTH,
    minHeight = LocalStorageGridExample.MIN_HEIGHT,
    minWidth = LocalStorageGridExample.MIN_WIDTH
)
public class LocalStorageGridExample implements IsWidget, EntryPoint {

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 640;

  private ContentPanel panel;

  interface TestAutoBeanFactory extends AutoBeanFactory {
    static TestAutoBeanFactory instance = GWT.create(TestAutoBeanFactory.class);

    AutoBean<ForumCollection> dataCollection();

    AutoBean<ForumListLoadResult> dataLoadResult();

    AutoBean<ForumLoadConfig> loadConfig();
  }

  public interface Forum {
    @PropertyName("topic_title")
    public String getTitle();

    @PropertyName("topic_id")
    public String getTopicId();

    public String getAuthor();

    @PropertyName("forumid")
    public String getForumId();

    @PropertyName("post_text")
    public String getExcerpt();

    @PropertyName("post_id")
    public String getPostId();

    @PropertyName("post_time")
    public Date getDate();
  }

  interface ForumCollection {
    String getTotalCount();

    List<Forum> getTopics();
  }

  interface ForumLoadConfig extends PagingLoadConfig {
    String getQuery();

    void setQuery(String query);

    @Override
    @PropertyName("start")
    public int getOffset();

    @Override
    @PropertyName("start")
    public void setOffset(int offset);
  }

  interface ForumListLoadResult extends PagingLoadResult<Forum> {
    void setData(List<Forum> data);

    @Override
    @PropertyName("start")
    public int getOffset();

    @Override
    @PropertyName("start")
    public void setOffset(int offset);
  }

  interface ForumProperties extends PropertyAccess<Forum> {
    @Path("topicId")
    ModelKeyProvider<Forum> key();

    ValueProvider<Forum, String> title();

    ValueProvider<Forum, String> excerpt();

    ValueProvider<Forum, String> author();

    ValueProvider<Forum, Date> date();
  }

  @Override
  public Widget asWidget() {
    if (panel == null) {

      final Storage storage = Storage.getLocalStorageIfSupported();
      if (storage == null) {
        new MessageBox("Not Supported", "Your browser doesn't appear to supprt HTML5 localStorage").show();
        return new HTML("LocalStorage not supported in this browser");
      }

      // Writer to translate load config into string
      UrlEncodingWriter<ForumLoadConfig> writer = new UrlEncodingWriter<ForumLoadConfig>(TestAutoBeanFactory.instance, ForumLoadConfig.class);
      // Reader to translate String results into objects
      JsonReader<ForumListLoadResult, ForumCollection> reader = new JsonReader<ForumListLoadResult, ForumCollection>(
          TestAutoBeanFactory.instance, ForumCollection.class) {
        @Override
        protected ForumListLoadResult createReturnData(Object loadConfig, ForumCollection records) {
          PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
          ForumListLoadResult forumListLoadResult = TestAutoBeanFactory.instance.dataLoadResult().as();
          forumListLoadResult.setData(records.getTopics());
          forumListLoadResult.setOffset(pagingLoadConfig.getOffset());
          forumListLoadResult.setTotalLength(Integer.parseInt(records.getTotalCount()));
          return forumListLoadResult;
        }
      };

      // Proxy to load from server
      String url = "//www.sencha.com/forum/topics-remote.php";
      final ScriptTagProxy<ForumLoadConfig> remoteProxy = new ScriptTagProxy<ForumLoadConfig>(url);
      remoteProxy.setWriter(writer);

      // Proxy to load objects from local storage
      final StorageReadProxy<ForumLoadConfig> localReadProxy = new StorageReadProxy<ForumLoadConfig>(storage);
      localReadProxy.setWriter(writer);

      // Proxy to persist network-loaded objects into local storage
      final StorageWriteProxy<ForumLoadConfig, String> localWriteProxy = new StorageWriteProxy<ForumLoadConfig, String>(storage);
      localWriteProxy.setKeyWriter(writer);

      // Wrapper Proxy to dispatch to either storage or scripttag, and to save results
      DataProxy<ForumLoadConfig, String> proxy = new DataProxy<ForumLoadConfig, String>() {
        @Override
        public void load(final ForumLoadConfig loadConfig, final Callback<String, Throwable> callback) {
          // Storage read is known to be synchronous, so read it first - if null, continue
          localReadProxy.load(loadConfig, new Callback<String, Throwable>() {
            @Override
            public void onFailure(Throwable reason) {
              // ignore failure, go remote
              onSuccess(null);
            }

            @Override
            public void onSuccess(String result) {
              if (result != null) {
                callback.onSuccess(result);
              } else {
                //read from remote and save it
                remoteProxy.load(loadConfig, new Callback<JavaScriptObject, Throwable>() {
                  @Override
                  public void onSuccess(JavaScriptObject result) {
                    //write results to local db
                    String json = new JSONObject(result).toString();
                    Entry<ForumLoadConfig, String> data = new Entry<ForumLoadConfig, String>(loadConfig, json);
                    localWriteProxy.load(data, new Callback<Void, Throwable>() {
                      @Override
                      public void onSuccess(Void result) {
                        // ignore response
                      }

                      @Override
                      public void onFailure(Throwable reason) {
                        // ignore response
                      }
                    });
                    callback.onSuccess(json);
                  }

                  @Override
                  public void onFailure(Throwable reason) {
                    callback.onFailure(reason);
                  }
                });
              }
            }
          });
        }
      };

      PagingLoader<ForumLoadConfig, ForumListLoadResult> loader = new PagingLoader<ForumLoadConfig, ForumListLoadResult>(proxy, reader);
      loader.useLoadConfig(TestAutoBeanFactory.instance.loadConfig().as());

      ForumProperties properties = GWT.create(ForumProperties.class);

      ListStore<Forum> store = new ListStore<Forum>(properties.key());
      loader.addLoadHandler(new LoadResultListStoreBinding<ForumLoadConfig, Forum, ForumListLoadResult>(store));

      ColumnConfig<Forum, String> titleColumn = new ColumnConfig<Forum, String>(properties.title(), 150, "Title");
      ColumnConfig<Forum, String> excerptColumn = new ColumnConfig<Forum, String>(properties.excerpt(), 150, "Excerpt");
      ColumnConfig<Forum, Date> dateColumn = new ColumnConfig<Forum, Date>(properties.date(), 230, "Date");
      ColumnConfig<Forum, String> authorColumn = new ColumnConfig<Forum, String>(properties.author(), 100, "Author");

      titleColumn.setSortable(false);
      excerptColumn.setSortable(false);
      dateColumn.setSortable(false);
      authorColumn.setSortable(false);

      List<ColumnConfig<Forum, ?>> columns = new ArrayList<ColumnConfig<Forum, ?>>();
      columns.add(titleColumn);
      columns.add(excerptColumn);
      columns.add(dateColumn);
      columns.add(authorColumn);

      ColumnModel<Forum> cm = new ColumnModel<Forum>(columns);

      Grid<Forum> grid = new Grid<Forum>(store, cm);
      grid.getView().setAutoExpandColumn(excerptColumn);
      grid.setLoader(loader);
      grid.setLoadMask(true);

      final PagingToolBar toolBar = new PagingToolBar(50);
      toolBar.setBorders(false);
      toolBar.add(new TextButton("Clear Cache", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          storage.clear();
        }
      }));
      toolBar.bind(loader);

      VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
      verticalLayoutContainer.add(grid, new VerticalLayoutData(1, 1));
      verticalLayoutContainer.add(toolBar, new VerticalLayoutData(1, -1));

      panel = new ContentPanel();
      panel.setHeading("Local Storage Grid");
      panel.add(verticalLayoutContainer);

      loader.load();
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
