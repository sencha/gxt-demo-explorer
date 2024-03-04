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
package com.sencha.gxt.explorer.client.treegrid;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.images.ExampleImages;
import com.sencha.gxt.examples.resources.client.model.BaseDto;
import com.sencha.gxt.examples.resources.client.model.FolderDto;
import com.sencha.gxt.examples.resources.client.model.MusicDto;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.CellSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.editing.ClicksToEdit;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

@Detail(
  name = "Editable Tree Grid",
  category = "Tree Grid",
  icon = "editortreegrid",
  classes = { BaseDto.class, FolderDto.class, MusicDto.class, TestData.class },
  maxHeight = EditableTreeGridExample.MAX_HEIGHT,
  maxWidth = EditableTreeGridExample.MAX_WIDTH,
  minHeight = EditableTreeGridExample.MIN_HEIGHT,
  minWidth = EditableTreeGridExample.MIN_WIDTH)
public class EditableTreeGridExample implements IsWidget, EntryPoint {

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

      ColumnConfig<BaseDto, String> cc1 = new ColumnConfig<BaseDto, String>(new ValueProvider<BaseDto, String>() {
        @Override
        public String getPath() {
          return "name";
        }

        @Override
        public String getValue(BaseDto object) {
          return object.getName();
        }

        @Override
        public void setValue(BaseDto object, String value) {
          object.setName(value);
        }
      });
      cc1.setHeader(SafeHtmlUtils.fromSafeConstant("Name"));

      ColumnConfig<BaseDto, String> cc2 = new ColumnConfig<BaseDto, String>(new ValueProvider<BaseDto, String>() {
        @Override
        public String getPath() {
          return "author";
        }

        @Override
        public String getValue(BaseDto object) {
          return object instanceof MusicDto ? ((MusicDto) object).getAuthor() : null;
        }

        @Override
        public void setValue(BaseDto object, String value) {
          if (object instanceof MusicDto) {
            ((MusicDto) object).setAuthor(value);
          }
        }
      });
      cc2.setHeader(SafeHtmlUtils.fromSafeConstant("Author"));

      ColumnConfig<BaseDto, String> cc3 = new ColumnConfig<BaseDto, String>(new ValueProvider<BaseDto, String>() {
        @Override
        public String getPath() {
          return "genre";
        }

        @Override
        public String getValue(BaseDto object) {
          return object instanceof MusicDto ? ((MusicDto) object).getGenre() : null;
        }

        @Override
        public void setValue(BaseDto object, String value) {
          if (object instanceof MusicDto) {
            ((MusicDto) object).setGenre(value);
          }
        }
      });
      cc3.setHeader("Genre");
      cc3.setCell(new TextCell());

      List<ColumnConfig<BaseDto, ?>> columns = new ArrayList<ColumnConfig<BaseDto, ?>>();
      columns.add(cc1);
      columns.add(cc2);
      columns.add(cc3);
      ColumnModel<BaseDto> cm = new ColumnModel<BaseDto>(columns);

      final TreeGrid<BaseDto> tree = new TreeGrid<BaseDto>(store, cm, cc1);
      tree.setExpandOnDoubleClick(false);
      tree.setSelectionModel(new CellSelectionModel<BaseDto>());
      tree.getStyle().setLeafIcon(ExampleImages.INSTANCE.music());
      tree.getView().setAutoExpandColumn(cc1);

      // EDITING//
      final GridInlineEditing<BaseDto> editing = new GridInlineEditing<BaseDto>(tree);
      editing.setClicksToEdit(ClicksToEdit.TWO);
      editing.addEditor(cc1, new TextField());
      editing.addEditor(cc2, new TextField());
      editing.addEditor(cc3, new TextField());
      // EDITING//

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

      SimpleComboBox<ClicksToEdit> clicksToEdit = new SimpleComboBox<ClicksToEdit>(new LabelProvider<ClicksToEdit>() {
        @Override
        public String getLabel(ClicksToEdit item) {
          return item == ClicksToEdit.ONE ? "1" : "2";
        }
      });
      clicksToEdit.setWidth(50);
      clicksToEdit.setEditable(false);
      clicksToEdit.setForceSelection(true);
      clicksToEdit.setTriggerAction(TriggerAction.ALL);
      clicksToEdit.addValueChangeHandler(new ValueChangeHandler<ClicksToEdit>() {
        @Override
        public void onValueChange(ValueChangeEvent<ClicksToEdit> event) {
          editing.setClicksToEdit(event.getValue());
          tree.setExpandOnDoubleClick(event.getValue() == ClicksToEdit.ONE);
        }
      });

      clicksToEdit.add(ClicksToEdit.ONE);
      clicksToEdit.add(ClicksToEdit.TWO);
      clicksToEdit.setValue(ClicksToEdit.TWO);

      buttonBar.add(new SeparatorToolItem());
      buttonBar.add(new LabelToolItem("Click to edit:"));
      buttonBar.add(clicksToEdit);

      VerticalLayoutContainer vlc = new VerticalLayoutContainer();
      vlc.add(buttonBar, new VerticalLayoutData(1, -1));
      vlc.add(tree, new VerticalLayoutData(1, 1));

      panel = new ContentPanel();
      panel.setHeading("Editable Tree Grid");
      panel.add(vlc);
    }

    return panel;
  }

  @Override
  public void onModuleLoad() {
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
