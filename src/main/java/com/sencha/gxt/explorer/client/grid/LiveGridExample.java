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
package com.sencha.gxt.explorer.client.grid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.examples.resources.client.ExampleService;
import com.sencha.gxt.examples.resources.client.ExampleServiceAsync;
import com.sencha.gxt.examples.resources.client.model.Post;
import com.sencha.gxt.examples.resources.client.model.PostProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.LiveGridView;
import com.sencha.gxt.widget.core.client.grid.LiveToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
    name = "Live Grid",
    category = "Grid",
    icon = "livegrid",
    classes = {
        Post.class,
        PostProperties.class,
        ExampleService.class,
        ExampleServiceAsync.class
    },
    maxHeight = LiveGridExample.MAX_HEIGHT,
    maxWidth = LiveGridExample.MAX_WIDTH,
    minHeight = LiveGridExample.MIN_HEIGHT,
    minWidth = LiveGridExample.MIN_WIDTH
)
public class LiveGridExample implements IsWidget, EntryPoint {

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 480;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final ExampleServiceAsync service = GWT.create(ExampleService.class);

      RpcProxy<PagingLoadConfig, PagingLoadResult<Post>> rpcProxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<Post>>() {
        @Override
        public void load(PagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<Post>> callback) {
          service.getPosts(loadConfig, callback);
        }
      };

      ListStore<Post> store = new ListStore<Post>(new ModelKeyProvider<Post>() {
        @Override
        public String getKey(Post item) {
          return "" + item.getId();
        }
      });

      final PagingLoader<PagingLoadConfig, PagingLoadResult<Post>> gridLoader = new PagingLoader<PagingLoadConfig, PagingLoadResult<Post>>(rpcProxy);
      gridLoader.setRemoteSort(true);

      PostProperties properties = GWT.create(PostProperties.class);

      ColumnConfig<Post, String> forumColumn = new ColumnConfig<Post, String>(properties.forum(), 130, "Forum");
      ColumnConfig<Post, String> usernameColumn = new ColumnConfig<Post, String>(properties.username(), 100, "Username");
      ColumnConfig<Post, String> subjectColumn = new ColumnConfig<Post, String>(properties.subject(), 150, "Subject");
      ColumnConfig<Post, Date> dateColumn = new ColumnConfig<Post, Date>(properties.date(), 90, "Date");

      dateColumn.setCell(new DateCell(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));

      List<ColumnConfig<Post, ?>> columns = new ArrayList<ColumnConfig<Post, ?>>();
      columns.add(forumColumn);
      columns.add(usernameColumn);
      columns.add(subjectColumn);
      columns.add(dateColumn);

      final LiveGridView<Post> liveGridView = new LiveGridView<Post>();
      liveGridView.setAutoExpandColumn(subjectColumn);

      ColumnModel<Post> cm = new ColumnModel<Post>(columns);

      Grid<Post> gridView = new Grid<Post>(store, cm) {
        @Override
        protected void onAfterFirstAttach() {
          super.onAfterFirstAttach();
          // After the Grid has been attached to DOM load the data
          Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
              // Important: set the loader limit to the live grid view cache size!!!!
              gridLoader.load(0, liveGridView.getCacheSize());
            }
          });
        }
      };
      gridView.setLoadMask(true);
      gridView.setLoader(gridLoader);
      gridView.setView(liveGridView);

      ToolBar toolBar = new ToolBar();
      toolBar.setBorders(false);
      toolBar.add(new LiveToolItem(gridView));

      VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
      verticalLayoutContainer.add(gridView, new VerticalLayoutData(1, 1));
      verticalLayoutContainer.add(toolBar, new VerticalLayoutData(1, 25));

      panel = new ContentPanel();
      panel.setHeading("Live Grid");
      panel.add(verticalLayoutContainer);
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
