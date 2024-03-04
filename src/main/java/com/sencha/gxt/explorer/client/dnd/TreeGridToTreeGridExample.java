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
package com.sencha.gxt.explorer.client.dnd;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.dnd.core.client.DND.Feedback;
import com.sencha.gxt.dnd.core.client.TreeGridDragSource;
import com.sencha.gxt.dnd.core.client.TreeGridDropTarget;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.BaseDto;
import com.sencha.gxt.examples.resources.client.model.BaseDtoProperties;
import com.sencha.gxt.examples.resources.client.model.FolderDto;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

@Detail(
    name = "Tree Grid to Tree Grid",
    category = "Drag & Drop",
    icon = "treegridtotreegrid",
    classes = {
        BaseDtoProperties.class,
        BaseDto.class,
        FolderDto.class,
        TestData.class
    },
    minHeight = TreeGridToTreeGridExample.MIN_HEIGHT,
    minWidth = TreeGridToTreeGridExample.MIN_WIDTH
)
public class TreeGridToTreeGridExample implements EntryPoint, IsWidget {

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 480;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      BaseDtoProperties properties = GWT.create(BaseDtoProperties.class);

      FolderDto rootFolder = TestData.getMusicRootFolder();
      
      TreeStore<BaseDto> topStore = new TreeStore<BaseDto>(BaseDtoProperties.key);
      topStore.addSubTree(0, rootFolder.getChildren());

      TreeStore<BaseDto> bottomStore = new TreeStore<BaseDto>(BaseDtoProperties.key);

      TreeGrid<BaseDto> topTree = createTreeGrid(properties, topStore);

      new TreeGridDragSource<BaseDto>(topTree);
      final TreeGridDropTarget<BaseDto> topTarget = new TreeGridDropTarget<BaseDto>(topTree);
      topTarget.setFeedback(Feedback.APPEND);

      TreeGrid<BaseDto> bottomTree = createTreeGrid(properties, bottomStore);

      new TreeGridDragSource<BaseDto>(bottomTree);
      final TreeGridDropTarget<BaseDto> bottomTarget = new TreeGridDropTarget<BaseDto>(bottomTree);
      bottomTarget.setFeedback(Feedback.APPEND);

      SimpleComboBox<Feedback> typeCombo = new SimpleComboBox<Feedback>(new LabelProvider<Feedback>() {
        @Override
        public String getLabel(Feedback item) {
          return item.toString().substring(0, 1) + item.toString().substring(1).toLowerCase();
        }
      });
      typeCombo.setTriggerAction(TriggerAction.ALL);
      typeCombo.setEditable(false);
      typeCombo.add(Feedback.APPEND);
      typeCombo.add(Feedback.INSERT);
      typeCombo.add(Feedback.BOTH);
      typeCombo.setValue(Feedback.APPEND);
      typeCombo.addValueChangeHandler(new ValueChangeHandler<Feedback>() {
        @Override
        public void onValueChange(ValueChangeEvent<Feedback> event) {
          topTarget.setFeedback(event.getValue());
          bottomTarget.setFeedback(event.getValue());
        }
      });

      ToolBar toolbar = new ToolBar();
      toolbar.add(new LabelToolItem("Feedback: "));
      toolbar.add(typeCombo);

      VerticalLayoutContainer vlc = new VerticalLayoutContainer();
      vlc.add(toolbar, new VerticalLayoutData(1, -1));
      vlc.add(topTree, new VerticalLayoutData(1, .5));
      vlc.add(bottomTree, new VerticalLayoutData(1, .5));

      panel = new ContentPanel();
      panel.setHeading("Tree Grid to Tree Grid");
      panel.setWidget(vlc);
    }

    return panel;
  }

  protected TreeGrid<BaseDto> createTreeGrid(BaseDtoProperties props, TreeStore<BaseDto> store) {
    ColumnConfig<BaseDto, String> nameColumn = new ColumnConfig<BaseDto, String>(props.name());
    nameColumn.setHeader("Name");

    ColumnConfig<BaseDto, String> authorColumn = new ColumnConfig<BaseDto, String>(BaseDtoProperties.author);
    authorColumn.setHeader("Author");

    ColumnConfig<BaseDto, String> genreColumn = new ColumnConfig<BaseDto, String>(BaseDtoProperties.genre);
    genreColumn.setHeader("Genre");

    @SuppressWarnings("unchecked")
    List<ColumnConfig<BaseDto, ?>> columns = Arrays.<ColumnConfig<BaseDto, ?>> asList(nameColumn, authorColumn, genreColumn);

    TreeGrid<BaseDto> treeGrid = new TreeGrid<BaseDto>(store, new ColumnModel<BaseDto>(columns), nameColumn);
    treeGrid.getView().setAutoExpandColumn(nameColumn);

    return treeGrid;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
