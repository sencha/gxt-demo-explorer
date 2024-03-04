/**
 * Sencha GXT 1.1.0-SNAPSHOT - Sencha for GWT
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
package com.sencha.gxt.explorer.client.tree;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.images.ExampleImages;
import com.sencha.gxt.examples.resources.client.model.BaseDto;
import com.sencha.gxt.examples.resources.client.model.FolderDto;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.tree.Tree;

@Detail(
    name = "Context Menu Tree",
    icon = "contextmenutree",
    category = "Tree",
    classes = {
        BaseDto.class,
        FolderDto.class,
        TestData.class
    },
    minHeight = ContextMenuTreeExample.MIN_HEIGHT,
    minWidth = ContextMenuTreeExample.MIN_WIDTH
)
public class ContextMenuTreeExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 240;
  protected static final int MIN_WIDTH = 320;

  private int count = 1;

  class KeyProvider implements ModelKeyProvider<BaseDto> {
    @Override
    public String getKey(BaseDto item) {
      return (item instanceof FolderDto ? "f-" : "m-") + item.getId().toString();
    }
  }

  private TreeStore<BaseDto> store;
  private FolderDto root = TestData.getMusicRootFolder();
  private ContentPanel panel;
  private Tree<BaseDto, String> tree;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      store = new TreeStore<BaseDto>(new KeyProvider());

      loadStore(store, root);
      
      TextButton buttonReset = new TextButton("Reset");
      buttonReset.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          store.clear();
          loadStore(store, root);
        }
      });
      buttonReset.setLayoutData(new MarginData(4));

      MenuItem insert = new MenuItem();
      insert.setText("Insert Item");
      insert.setIcon(ExampleImages.INSTANCE.add());
      insert.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          BaseDto sel = tree.getSelectionModel().getSelectedItem();
          if (sel != null) {
            FolderDto child = new FolderDto(count * 100, "Add Child " + count++);
            store.add(sel, child);
            tree.setExpanded(sel, true);
          }
        }
      });

      MenuItem remove = new MenuItem();
      remove.setText("Remove Selected");
      remove.setIcon(ExampleImages.INSTANCE.delete());
      remove.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          List<BaseDto> selected = tree.getSelectionModel().getSelectedItems();
          for (BaseDto sel : selected) {
            store.remove(sel);
          }
        }
      });

      Menu contextMenu = new Menu();
      contextMenu.add(insert);
      contextMenu.add(remove);

      tree = new Tree<BaseDto, String>(store, new ValueProvider<BaseDto, String>() {
        @Override
        public String getValue(BaseDto object) {
          return object.getName();
        }

        @Override
        public void setValue(BaseDto object, String value) {
        }

        @Override
        public String getPath() {
          return "name";
        }
      });
      tree.setWidth(300);
      tree.getStyle().setLeafIcon(ExampleImages.INSTANCE.music());
      tree.setContextMenu(contextMenu);

      ToolBar toolBar = new ToolBar();
      toolBar.add(buttonReset);

      VerticalLayoutContainer vlc = new VerticalLayoutContainer();
      vlc.add(toolBar, new VerticalLayoutData(1, -1));
      vlc.add(tree, new VerticalLayoutData(1, 1));

      panel = new ContentPanel();
      panel.setHeading("Context Menu Tree");
      panel.add(vlc);
    }

    return panel;
  }

  private void loadStore(TreeStore<BaseDto> store, FolderDto root) {
    for (BaseDto base : root.getChildren()) {
      store.add(base);
      if (base instanceof FolderDto) {
        processFolder(store, (FolderDto) base);
      }
    }
  }

  private void processFolder(TreeStore<BaseDto> store, FolderDto folder) {
    for (BaseDto child : folder.getChildren()) {
      store.add(folder, child);
      if (child instanceof FolderDto) {
        processFolder(store, (FolderDto) child);
      }
    }
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
