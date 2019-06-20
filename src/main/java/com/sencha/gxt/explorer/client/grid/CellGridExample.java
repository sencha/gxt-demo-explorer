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

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.ProgressBarCell;
import com.sencha.gxt.cell.core.client.ResizeCell;
import com.sencha.gxt.cell.core.client.SliderCell;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.cell.core.client.form.DateCell;
import com.sencha.gxt.core.client.Style.Side;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.resources.CommonStyles;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Plant;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.theme.base.client.colorpalette.ColorPaletteBaseAppearance;
import com.sencha.gxt.widget.core.client.ColorPaletteCell;
import com.sencha.gxt.widget.core.client.ColorPaletteCell.ColorPaletteAppearance;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.CellSelectionEvent;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent.ColumnWidthChangeHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;

@Detail(
  name = "Cell Grid",
  category = "Grid",
  icon = "cellgrid",
  classes = { Plant.class, TestData.class },
  maxHeight = CellGridExample.MAX_HEIGHT,
  maxWidth = CellGridExample.MAX_WIDTH,
  minHeight = CellGridExample.MIN_HEIGHT,
  minWidth = CellGridExample.MIN_WIDTH)
public class CellGridExample implements IsWidget, EntryPoint {

  private static final String[] COLORS = new String[] { "161616", "002241", "006874", "82a700", "bbc039", "f3f1cd" };

  interface PlaceProperties extends PropertyAccess<Plant> {
    ValueProvider<Plant, Date> available();

    @Path("name")
    ModelKeyProvider<Plant> key();

    ValueProvider<Plant, String> name();

    ValueProvider<Plant, Integer> difficulty();

    ValueProvider<Plant, Double> progress();

    ValueProvider<Plant, String> color();

    ValueProvider<Plant, String> light();
  }

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 900;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 900;

  private static final PlaceProperties properties = GWT.create(PlaceProperties.class);

  private ListStore<Plant> store;
  private ContentPanel panel;

  public class CellColumnResizer<M, T> implements ColumnWidthChangeHandler {

    private Grid<M> grid;
    private ColumnConfig<M, T> column;
    private ResizeCell<T> cell;

    public CellColumnResizer(Grid<M> grid, ColumnConfig<M, T> column, ResizeCell<T> cell) {
      this.grid = grid;
      this.column = column;
      this.cell = cell;
    }

    @Override
    public void onColumnWidthChange(ColumnWidthChangeEvent event) {
      if (column == event.getColumnConfig()) {
        int w = event.getColumnConfig().getWidth();
        int rows = store.size();

        int col = grid.getColumnModel().indexOf(column);

        cell.setWidth(w - 20);

        ListStore<M> store = grid.getStore();

        for (int i = 0; i < rows; i++) {
          M p = grid.getStore().get(i);

          // option 1
          // could be better for force fit where all columns are resized
          // would need to run deferred using DelayedTask to ensure only run once
          // grid.getStore().update(p);

          // option 2
          Element parent = grid.getView().getCell(i, col);
          if (parent != null) {
            parent = parent.getFirstChildElement();
            SafeHtmlBuilder sb = new SafeHtmlBuilder();
            cell.render(new Context(i, col, store.getKeyProvider().getKey(p)), column.getValueProvider().getValue(p),
                sb);
            parent.setInnerSafeHtml(sb.toSafeHtml());
          }
        }
      }
    }
  }

  @Override
  public Widget asWidget() {
    if (panel == null) {
      // reduce the padding on text element as we have widgets in the cells
      SafeStyles btnPaddingStyle = SafeStylesUtils.fromTrustedString("padding: 1px 3px 0;");
      SafeStyles fieldPaddingStyle = SafeStylesUtils.fromTrustedString("padding: 2px 3px;");

      ColumnConfig<Plant, String> nameColumn = new ColumnConfig<Plant, String>(properties.name(), 100, "Name");
      // IMPORTANT we want the text element (cell parent) to only be as wide as
      // the cell and not fill the cell
      nameColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
      nameColumn.setColumnTextStyle(btnPaddingStyle);

      TextButtonCell button = new TextButtonCell() {
        // Override this to control the row selection
        @Override
        public boolean handlesSelection() {
          return false;
        }
      };
      button.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          Context c = event.getContext();
          int row = c.getIndex();
          Plant p = store.get(row);
          Info.display("Event", "The " + p.getName() + " was clicked.");
        }
      });
      nameColumn.setCell(button);

      DateCell dateCell = new DateCell() {
        // Override this to control the row selection
        @Override
        public boolean handlesSelection() {
          return false;
        }
      };
      dateCell.getDatePicker().addValueChangeHandler(new ValueChangeHandler<Date>() {
        @Override
        public void onValueChange(ValueChangeEvent<Date> event) {
          Info.display("Date Selected",
              "You selected " + DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT).format(event.getValue()));
        }
      });
      dateCell.setPropertyEditor(new DateTimePropertyEditor(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));

      ColumnConfig<Plant, Date> availableColumn = new ColumnConfig<Plant, Date>(properties.available(), 160, "Date");
      availableColumn.setColumnTextStyle(fieldPaddingStyle);
      availableColumn.setCell(dateCell);

      ListStore<String> lights = new ListStore<String>(new ModelKeyProvider<String>() {
        @Override
        public String getKey(String item) {
          return item;
        }
      });
      lights.add("Mostly Shady");
      lights.add("Mostly Sunny");
      lights.add("Shade");
      lights.add("Sunny");
      lights.add("Sun or Shade");

      ColumnConfig<Plant, String> lightColumn = new ColumnConfig<Plant, String>(properties.light(), 130, "Light");
      lightColumn.setColumnTextStyle(fieldPaddingStyle);

      ComboBoxCell<String> lightCombo = new ComboBoxCell<String>(lights, new LabelProvider<String>() {
        @Override
        public String getLabel(String item) {
          return item;
        }
      }) {
        // Override this to control the row selection
        @Override
        public boolean handlesSelection() {
          return false;
        }
      };
      lightCombo.addSelectionHandler(new SelectionHandler<String>() {
        @Override
        public void onSelection(SelectionEvent<String> event) {
          CellSelectionEvent<String> sel = (CellSelectionEvent<String>) event;
          Plant p = store.get(sel.getContext().getIndex());
          Info.display("Lightness Selected", p.getName() + " selected " + event.getSelectedItem());
        }
      });
      lightCombo.setTriggerAction(TriggerAction.ALL);
      lightCombo.setForceSelection(true);
      lightCombo.setWidth(120);
      lightColumn.setCell(lightCombo);

      ColumnConfig<Plant, String> colorColumn = new ColumnConfig<Plant, String>(properties.color(), 150, "Color");
      colorColumn.setColumnTextStyle(fieldPaddingStyle);

      // This next line only works with any appearance that extends from Base
      ColorPaletteBaseAppearance appearance = GWT.create(ColorPaletteAppearance.class);
      appearance.setColumnCount(6);

      ColorPaletteCell colorPalette = new ColorPaletteCell(appearance, COLORS, COLORS) {
        // Override this to control the row selection
        @Override
        public boolean handlesSelection() {
          return false;
        }
      };
      colorPalette.addSelectionHandler(new SelectionHandler<String>() {
        @Override
        public void onSelection(SelectionEvent<String> event) {
          Info.display("Color Selected", "You selected " + event.getSelectedItem());
        }
      });
      colorColumn.setCell(colorPalette);

      // Custom slider tooltip configuration, which displays the tooltip to the right of the control.
      ToolTipConfig tooltipConfig = new ToolTipConfig();
      tooltipConfig.setAnchor(Side.LEFT);
      tooltipConfig.setAnchorArrow(false);
      tooltipConfig.setMouseOffsetX(25);
      tooltipConfig.setMouseOffsetY(0);
      tooltipConfig.setDismissDelay(1000);

      SliderCell slider = new SliderCell() {
        // Override this to control the row selection
        @Override
        public boolean handlesSelection() {
          return false;
        }
      };
      slider.setToolTipConfig(tooltipConfig);
      slider.setWidth(140);

      ColumnConfig<Plant, Integer> difficultyColumn = new ColumnConfig<Plant, Integer>(properties.difficulty(), 150,
          "Durability");
      difficultyColumn.setColumnTextStyle(fieldPaddingStyle);
      difficultyColumn.setCell(slider);

      final ColumnConfig<Plant, Double> progressColumn = new ColumnConfig<Plant, Double>(properties.progress(), 150,
          "Progress");
      progressColumn.setColumnTextStyle(fieldPaddingStyle);
      final ProgressBarCell progress = new ProgressBarCell() {
        // Override this to control the row selection
        @Override
        public boolean handlesSelection() {
          return false;
        }
      };
      progress.setProgressText("{0}% Complete");
      progress.setWidth(140);
      progressColumn.setCell(progress);

      nameColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
      availableColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
      lightColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
      colorColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
      difficultyColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
      progressColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

      List<ColumnConfig<Plant, ?>> columns = new ArrayList<ColumnConfig<Plant, ?>>();
      columns.add(nameColumn);
      columns.add(availableColumn);
      columns.add(lightColumn);
      columns.add(colorColumn);
      columns.add(difficultyColumn);
      columns.add(progressColumn);

      ColumnModel<Plant> cm = new ColumnModel<Plant>(columns);

      List<Plant> plants = new ArrayList<Plant>(TestData.getPlants());
      for (Plant p : plants) {
        p.setColor(COLORS[Random.nextInt(4)]);
      }

      store = new ListStore<Plant>(properties.key());
      store.addAll(plants);

      final Grid<Plant> grid = new Grid<Plant>(store, cm);
      grid.getView().setAutoExpandColumn(nameColumn);
      grid.getView().setTrackMouseOver(false);
      grid.getColumnModel()
          .addColumnWidthChangeHandler(new CellColumnResizer<Plant, Double>(grid, progressColumn, progress));

      panel = new ContentPanel();
      panel.setHeading("Cell Grid");
      panel.add(grid);
      panel.setButtonAlign(BoxLayoutPack.CENTER);
      panel.addButton(new TextButton("Reset", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          store.rejectChanges();
        }
      }));
      panel.addButton(new TextButton("Save", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          store.commitChanges();
        }
      }));
    }
    return panel;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMaxHeight(MAX_HEIGHT).setMaxWidth(MAX_WIDTH).setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH).doStandalone();
  }

}
