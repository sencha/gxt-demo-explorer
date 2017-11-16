package com.sencha.gxt.explorer.client.statemanager;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.core.client.ToStringValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.images.ExampleImages;
import com.sencha.gxt.examples.resources.client.model.BaseDto;
import com.sencha.gxt.examples.resources.client.model.FolderDto;
import com.sencha.gxt.examples.resources.client.model.MusicDto;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.state.client.CookieProvider;
import com.sencha.gxt.state.client.StateManager;
import com.sencha.gxt.state.client.TreeGridStateHandler;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

@Detail(
  name = "Tree Grid State",
  category = "Tree Grid",
  icon = "basictreegrid",
  classes = { BaseDto.class, FolderDto.class, MusicDto.class, TestData.class },
  maxHeight = TreeGridStateExample.MAX_HEIGHT,
  maxWidth = TreeGridStateExample.MAX_WIDTH,
  minHeight = TreeGridStateExample.MIN_HEIGHT,
  minWidth = TreeGridStateExample.MIN_WIDTH)
public class TreeGridStateExample implements IsWidget, EntryPoint {

  class KeyProvider implements ModelKeyProvider<BaseDto> {
    @Override
    public String getKey(BaseDto item) {
      return (item instanceof FolderDto ? "f-" : "m-") + item.getId().toString();
    }
  }

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 480;

  private ContentPanel panel;
  private TreeGridStateHandler<BaseDto> treeGridState;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      TreeStore<BaseDto> store = new TreeStore<BaseDto>(new KeyProvider());

      FolderDto root = TestData.getMusicRootFolder();
      for (BaseDto base : root.getChildren()) {
        store.add(base);
        if (base instanceof FolderDto) {
          processFolder(store, (FolderDto) base);
        }
      }

      ColumnConfig<BaseDto, String> nameCol = new ColumnConfig<BaseDto, String>(
          new ToStringValueProvider<BaseDto>("name"));
      nameCol.setHeader("Name");

      ColumnConfig<BaseDto, String> authorCol = new ColumnConfig<BaseDto, String>(new ValueProvider<BaseDto, String>() {
        @Override
        public String getValue(BaseDto object) {
          return object instanceof MusicDto ? ((MusicDto) object).getAuthor() : "";
        }

        @Override
        public void setValue(BaseDto object, String value) {
          if (object instanceof MusicDto) {
            ((MusicDto) object).setAuthor(value);
          }
        }

        @Override
        public String getPath() {
          return "author";
        }
      });
      authorCol.setHeader("Author");

      ColumnConfig<BaseDto, String> genreCol = new ColumnConfig<BaseDto, String>(new ValueProvider<BaseDto, String>() {
        @Override
        public String getValue(BaseDto object) {
          return object instanceof MusicDto ? ((MusicDto) object).getGenre() : "";
        }

        @Override
        public void setValue(BaseDto object, String value) {
          if (object instanceof MusicDto) {
            ((MusicDto) object).setGenre(value);
          }
        }

        @Override
        public String getPath() {
          return "genre";
        }
      });
      genreCol.setHeader("Genre");
      genreCol.setCell(new TextCell());

      List<ColumnConfig<BaseDto, ?>> columns = new ArrayList<ColumnConfig<BaseDto, ?>>();
      columns.add(nameCol);
      columns.add(authorCol);
      columns.add(genreCol);

      ColumnModel<BaseDto> cm = new ColumnModel<BaseDto>(columns);

      final TreeGrid<BaseDto> tree = new TreeGrid<BaseDto>(store, cm, nameCol);
      tree.getStyle().setLeafIcon(ExampleImages.INSTANCE.music());
      tree.getView().setAutoExpandColumn(nameCol);

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
      panel.setHeading("Basic Tree Grid");
      panel.add(vlc);
    
      // 2. Stage manager, load previous state
      treeGridState = new TreeGridStateHandler<BaseDto>(tree);
      tree.setStateful(true);
      tree.setStateId("treeGridStateExample");
    }

    // 3. Load previous state
    treeGridState.loadState();

    return panel;
  }

  @Override
  public void onModuleLoad() {
    // 1. State manager, initialize the state options
    StateManager.get().setProvider(new CookieProvider("/", null, null, GXT.isSecure()));

    new ExampleContainer(this).setMaxHeight(MAX_HEIGHT).setMaxWidth(MAX_WIDTH).setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH).doStandalone();
  }

  private void processFolder(TreeStore<BaseDto> store, FolderDto folder) {
    for (BaseDto child : folder.getChildren()) {
      store.add(folder, child);
      if (child instanceof FolderDto) {
        processFolder(store, (FolderDto) child);
      }
    }
  }

}
