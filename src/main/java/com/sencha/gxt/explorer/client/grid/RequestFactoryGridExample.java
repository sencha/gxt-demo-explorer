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
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.data.shared.loader.RequestFactoryProxy;
import com.sencha.gxt.examples.resources.shared.ExampleRequestFactory;
import com.sencha.gxt.examples.resources.shared.PostProxy;
import com.sencha.gxt.examples.resources.shared.PostRequest;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.filters.DateFilter;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

@Detail(
    name = "Request Factory Grid",
    category = "Grid",
    icon = "requestfactorygrid",
    classes = {
        PostProxy.class,
        ExampleRequestFactory.class,
        PostRequest.class,
        ExampleRequestFactory.class
    },
    maxHeight = RequestFactoryGridExample.MAX_HEIGHT,
    maxWidth = RequestFactoryGridExample.MAX_WIDTH,
    minHeight = RequestFactoryGridExample.MIN_HEIGHT,
    minWidth = RequestFactoryGridExample.MIN_WIDTH
)
public class RequestFactoryGridExample implements EntryPoint, IsWidget {

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 550;

  private ContentPanel panel;

  interface PostProxyProperties extends PropertyAccess<PostProxy> {
    ModelKeyProvider<PostProxy> id();

    ValueProvider<PostProxy, String> username();

    ValueProvider<PostProxy, String> forum();

    ValueProvider<PostProxy, String> subject();

    ValueProvider<PostProxy, Date> date();
  }

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final ExampleRequestFactory requestFactory = GWT.create(ExampleRequestFactory.class);
      requestFactory.initialize(new SimpleEventBus());

      RequestFactoryProxy<FilterPagingLoadConfig, PagingLoadResult<PostProxy>> proxy = new RequestFactoryProxy<FilterPagingLoadConfig, PagingLoadResult<PostProxy>>() {
        @Override
        public void load(FilterPagingLoadConfig loadConfig, Receiver<? super PagingLoadResult<PostProxy>> receiver) {
          PostRequest request = requestFactory.post();
          List<SortInfo> sortInfo = createRequestSortInfo(request, loadConfig.getSortInfo());
          List<FilterConfig> filterConfig = createRequestFilterConfig(request, loadConfig.getFilters());
          request.getPosts(loadConfig.getOffset(), loadConfig.getLimit(), sortInfo, filterConfig).to(receiver);
          request.fire();
        }
      };

      final PagingLoader<FilterPagingLoadConfig, PagingLoadResult<PostProxy>> loader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<PostProxy>>(proxy);
      loader.useLoadConfig(new FilterPagingLoadConfigBean());
      loader.setRemoteSort(true);

      PostProxyProperties properties = GWT.create(PostProxyProperties.class);

      ListStore<PostProxy> store = new ListStore<PostProxy>(properties.id());
      loader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, PostProxy, PagingLoadResult<PostProxy>>(store));

      ColumnConfig<PostProxy, String> forumColumn = new ColumnConfig<PostProxy, String>(properties.forum(), 130, "Forum");
      ColumnConfig<PostProxy, String> usernameColumn = new ColumnConfig<PostProxy, String>(properties.username(), 100, "Username");
      ColumnConfig<PostProxy, String> subjectColumn = new ColumnConfig<PostProxy, String>(properties.subject(), 150, "Subject");
      ColumnConfig<PostProxy, Date> dateColumn = new ColumnConfig<PostProxy, Date>(properties.date(), 100, "Date");

      dateColumn.setCell(new DateCell(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));

      List<ColumnConfig<PostProxy, ?>> columns = new ArrayList<ColumnConfig<PostProxy, ?>>();
      columns.add(forumColumn);
      columns.add(usernameColumn);
      columns.add(subjectColumn);
      columns.add(dateColumn);

      ColumnModel<PostProxy> cm = new ColumnModel<PostProxy>(columns);

      Grid<PostProxy> gridView = new Grid<PostProxy>(store, cm) {
        @Override
        protected void onAfterFirstAttach() {
          super.onAfterFirstAttach();
          Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
              loader.load();
            }
          });
        }
      };
      gridView.getView().setAutoExpandColumn(subjectColumn);
      gridView.setLoadMask(true);
      gridView.setLoader(loader);

      // Create the filters, and hook them to the loader and grid
      GridFilters<PostProxy> filters = new GridFilters<PostProxy>(loader);
      filters.initPlugin(gridView);
      filters.setLocal(false); // be sure to be remote, or it will affect the local cached data only
      filters.addFilter(new DateFilter<PostProxy>(properties.date()));
      filters.addFilter(new StringFilter<PostProxy>(properties.subject()));
      filters.addFilter(new StringFilter<PostProxy>(properties.forum()));
      filters.addFilter(new StringFilter<PostProxy>(properties.username()));

      final PagingToolBar toolBar = new PagingToolBar(50);
      toolBar.setBorders(false);
      toolBar.bind(loader);

      VerticalLayoutContainer con = new VerticalLayoutContainer();
      con.add(gridView, new VerticalLayoutData(1, 1));
      con.add(toolBar, new VerticalLayoutData(1, -1));

      panel = new ContentPanel();
      panel.setHeading("Request Factory Grid");
      panel.add(con);
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
