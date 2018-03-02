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
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.BeforeLoadEvent;
import com.sencha.gxt.data.shared.loader.BeforeLoadEvent.BeforeLoadHandler;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.examples.resources.client.ExampleService;
import com.sencha.gxt.examples.resources.client.ExampleServiceAsync;
import com.sencha.gxt.examples.resources.client.model.Post;
import com.sencha.gxt.examples.resources.client.model.PostProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.RefreshEvent;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

import static com.sencha.gxt.widget.core.client.Dialog.PredefinedButton.YES;

@Detail(
    name = "Paging Grid",
    category = "Grid",
    icon = "paginggrid",
    classes = {
        Post.class,
        PostProperties.class,
        ExampleService.class,
        ExampleServiceAsync.class,
        PagingGridConfirmableLoader.class
    },
    maxHeight = PagingGridExample.MAX_HEIGHT,
    maxWidth = PagingGridExample.MAX_WIDTH,
    minHeight = PagingGridExample.MIN_HEIGHT,
    minWidth = PagingGridExample.MIN_WIDTH
)
public class PagingGridExample implements IsWidget, EntryPoint {

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 550;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final ExampleServiceAsync service = GWT.create(ExampleService.class);

      RpcProxy<PagingLoadConfig, PagingLoadResult<Post>> rpxProxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<Post>>() {
        @Override
        public void load(PagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<Post>> callback) {
          service.getPosts(loadConfig, callback);
        }
      };

      PostProperties properties = GWT.create(PostProperties.class);

      ListStore<Post> store = new ListStore<Post>(new ModelKeyProvider<Post>() {
        @Override
        public String getKey(Post item) {
          return "" + item.getId();
        }
      });

      final PagingGridConfirmableLoader loader = new PagingGridConfirmableLoader(rpxProxy);
      loader.setRemoteSort(true);
      loader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, Post, PagingLoadResult<Post>>(store));

      IdentityValueProvider<Post> identity = new IdentityValueProvider<Post>();
      final CheckBoxSelectionModel<Post> selectionModel = new CheckBoxSelectionModel<Post>(identity) {
        @Override
        protected void onRefresh(RefreshEvent event) {
          // this code selects all rows when paging if the header checkbox is selected
          if (isSelectAllChecked()) {
            selectAll();
          }
          super.onRefresh(event);
        }
      };

      ColumnConfig<Post, String> forumColumn = new ColumnConfig<Post, String>(properties.forum(), 130, "Forum");
      ColumnConfig<Post, String> usernameColumn = new ColumnConfig<Post, String>(properties.username(), 100, "Username");
      ColumnConfig<Post, String> subjectColumn = new ColumnConfig<Post, String>(properties.subject(), 150, "Subject");
      ColumnConfig<Post, Date> dateColumn = new ColumnConfig<Post, Date>(properties.date(), 100, "Date");

      dateColumn.setCell(new DateCell(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));

      List<ColumnConfig<Post, ?>> columns = new ArrayList<ColumnConfig<Post, ?>>();
      // The selection model provides the first column config
      columns.add(selectionModel.getColumn());
      columns.add(forumColumn);
      columns.add(usernameColumn);
      columns.add(subjectColumn);
      columns.add(dateColumn);

      ColumnModel<Post> cm = new ColumnModel<Post>(columns);

      Grid<Post> grid = new Grid<Post>(store, cm) {
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
      grid.setSelectionModel(selectionModel);
      grid.getView().setAutoExpandColumn(subjectColumn);
      grid.setLoadMask(true);
      grid.setLoader(loader);
      grid.setColumnReordering(true);

      final CheckBox warnLoad = new CheckBox();
      warnLoad.setBoxLabel("Warn before loading new data");
      warnLoad.setValue(false);

      final PagingToolBar toolBar = new PagingToolBar(50);
      toolBar.setBorders(false);
      toolBar.bind(loader);

      VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
      verticalLayoutContainer.add(grid, new VerticalLayoutData(1, 1));
      verticalLayoutContainer.add(toolBar, new VerticalLayoutData(1, -1));
      verticalLayoutContainer.add(warnLoad, new VerticalLayoutData(-1, -1, new Margins(0, 0, 3, 3)));

      // If the warn checkbox is active, then present a warning and stop any load
      // after the first if the user clicks cancel
      loader.addBeforeLoadHandler(new BeforeLoadHandler<PagingLoadConfig>() {
        boolean initialLoad = true;

        @Override
        public void onBeforeLoad(BeforeLoadEvent<PagingLoadConfig> event) {
          if (!initialLoad && warnLoad.getValue()) {
            event.setCancelled(true);
            ConfirmMessageBox confirm = new ConfirmMessageBox("Confirm", "Are you sure you want to do that?");
            confirm.addDialogHideHandler(new DialogHideEvent.DialogHideHandler() {
              @Override
              public void onDialogHide(DialogHideEvent event) {
                if (event.getHideButton() == YES) {
                  loader.loadData(loader.getLastLoadConfig());
                }
              }
            });
            confirm.setWidth("300");
            confirm.show();
          }
          initialLoad = false;
        }
      });

      panel = new ContentPanel();
      panel.setHeading("Paging Grid");
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
