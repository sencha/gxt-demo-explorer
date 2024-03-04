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
package com.sencha.gxt.explorer.client.grid;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.TextAreaInputCell.TextAreaAppearance;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Util;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.StartEditEvent;
import com.sencha.gxt.widget.core.client.event.StartEditEvent.StartEditHandler;
import com.sencha.gxt.widget.core.client.event.ViewReadyEvent;
import com.sencha.gxt.widget.core.client.event.ViewReadyEvent.ViewReadyHandler;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.IntegerSpinnerField;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.grid.editing.ClicksToEdit;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.CheckMenuItem;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

/**
 * An example that illustrates a way to implement word wrap in {@link Grid} column headers and cells.
 * <p></p>
 * This class demonstrates many capabilities of word wrap in grids, including setting heading text, setting cell text,
 * setting column width, setting header height, getting header height and toggling force fit. Most applications will not
 * have need for all of these functions. The basic word wrap column header support can be found in
 * {@link WordWrapColumnHeader}.
 * <p></p>
 * This class generally uses the term <i>heading</i> to refer to the text of a column heading and the term <i>header</i>
 * to refer to the visual elements that display the heading. In some cases, this terminology is relaxed to make the
 * example more usable.
 */
@Detail(
  name = "Word Wrap Grid",
  category = "Grid",
  icon = "basicgrid",
  classes = { WordWrapColumnHeader.class, Utils.class, TestData.class },
  maxHeight = WordWrapGridExample.MAX_HEIGHT,
  maxWidth = WordWrapGridExample.MAX_WIDTH,
  minHeight = WordWrapGridExample.MIN_HEIGHT,
  minWidth = WordWrapGridExample.MIN_WIDTH)
public class WordWrapGridExample implements IsWidget, EntryPoint {

  /**
   * A value provider that supports a spreadsheet-like table with an arbitrary number of columns.
   */
  public static class ColumnValueProvider implements ValueProvider<Row, String> {
    private static int nextId;

    private final int columnIndex;
    private final String path = "Column" + Integer.toString(nextId++);

    public ColumnValueProvider(int columnIndex) {
      this.columnIndex = columnIndex;
    }

    @Override
    public String getPath() {
      return path;
    }

    @Override
    public String getValue(Row row) {
      return row.getColumn(columnIndex);
    }

    @Override
    public void setValue(Row row, String value) {
      row.setValue(columnIndex, value);
    }
  }

  /**
   * An array of column widths that can be used with {@link GridView#setForceFit(boolean)} to save column widths before
   * invoking setForceFit(true) and restore them after invoking setForceFit(false).
   */
  public class ColumnWidths {
    private int[] columnWidths;

    public void restore(Grid<Row> grid) {
      ColumnModel<Row> columnModel = grid.getColumnModel();
      for (int columnIndex = 0; columnIndex < columnWidths.length; columnIndex++) {
        columnModel.setColumnWidth(columnIndex, columnWidths[columnIndex]);
      }
    }

    public void save(Grid<Row> grid) {
      ColumnModel<Row> columnModel = grid.getColumnModel();
      int columnCount = columnModel.getColumnCount();
      columnWidths = new int[columnCount];
      for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
        ColumnConfig<Row, Object> columnConfig = columnModel.getColumn(columnIndex);
        columnWidths[columnIndex] = columnConfig.getWidth();
      }
    }
  }

  /**
   * A data model that supports a spreadsheet-like table with an arbitrary number of rows and columns.
   */
  public static class Row {
    private static int nextId;

    private final String[] columns;
    private final String key = "Row" + Integer.toString(nextId++);

    public Row(int columnCount) {
      columns = new String[columnCount];
    }

    public String getColumn(int columnIndex) {
      return columns[columnIndex];
    }

    public String getKey() {
      return key;
    }

    public void setValue(int columnIndex, String value) {
      columns[columnIndex] = value;
    }
  }

  /**
   * A model key provider that supports a spreadsheet-like table with an arbitrary number of rows.
   */
  public class RowKeyProvider implements ModelKeyProvider<Row> {
    @Override
    public String getKey(Row row) {
      return row.getKey();
    }
  }

  /**
   * A value provider that supports a spreadsheet-like table with an arbitrary number of rows.
   */
  public class RowValueProvider {
    public ValueProvider<Row, String> getColumnValueProvider(int columnIndex) {
      return new ColumnValueProvider(columnIndex);
    }
  }

  /**
   * A grid view that limits the width of the items in the column header menu's column sub-menu. Strictly speaking, this
   * is not required for a word wrap grid header because the sub-menu resizes itself to the width of the longest column
   * heading, but this does improve the usability by limiting the sub-menu width.
   */
  public static class WordWrapGridView extends GridView<Row> {
    @Override
    protected Menu createContextMenu(int colIndex) {
      Menu contextMenu = super.createContextMenu(colIndex);
      int lastItem = contextMenu.getWidgetCount() - 1;
      if (lastItem >= 0) {
        Widget widget = contextMenu.getWidget(lastItem);
        if (widget instanceof MenuItem) {
          MenuItem columns = (MenuItem) widget;
          Menu columnMenu = columns.getSubMenu();
          if (columnMenu != null) {
            int cols = columnMenu.getWidgetCount();
            for (int i = 0; i < cols; i++) {
              widget = columnMenu.getWidget(i);
              if (widget instanceof CheckMenuItem) {
                CheckMenuItem menuItem = (CheckMenuItem) widget;
                menuItem.setWidth(200);
              }
            }
          }
        }
      }
      return contextMenu;
    }
  }

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 675;

  private static final int ROW_COUNT = 15;
  private static final int COLUMN_COUNT = 5;
  private static final Margins M1 = new Margins(5, 5, 0, 5);
  private static final Margins M2 = new Margins(5, 5, 5, 5);
  private static final String[] SENTENCES = TestData.DUMMY_TEXT_LONG.replaceAll("\\<[^>]*>", "").split("\\. ");

  private ContentPanel panel;
  private RowValueProvider rvp = new RowValueProvider();
  private ColumnWidths columnWidths = new ColumnWidths();

  private ListStore<Row> ls;
  private ColumnModel<Row> cm;
  private Grid<Row> grid;
  private GridInlineEditing<Row> editing;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      List<ColumnConfig<Row, ?>> columns = new LinkedList<ColumnConfig<Row, ?>>();
      Set<String> columnHeaders = new HashSet<String>(COLUMN_COUNT);

      for (int i = 0; i < COLUMN_COUNT; i++) {
        String headerText = createDummyHeaderText(columnHeaders);
        columnHeaders.add(headerText);

        SafeHtml safeHtml = wrapString(headerText);

        ColumnConfig<Row, String> columnConfig = new ColumnConfig<Row, String>(rvp.getColumnValueProvider(i), 200,
            safeHtml);
        // Use a custom cell renderer to support word wrap in the grid's cells
        columnConfig.setCell(new AbstractCell<String>() {
          @Override
          public void render(Context context, String value, SafeHtmlBuilder sb) {
            if (value == null || value.isEmpty()) {
              sb.append(Util.NBSP_SAFE_HTML);
            } else {
              sb.append(wrapString(value));
            }
          }
        });
        
        // vertical header alignment
        columnConfig.setVerticalHeaderAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
        
        columns.add(columnConfig);
      }

      cm = new ColumnModel<Row>(columns);

      ls = new ListStore<Row>(new RowKeyProvider());
      ls.setAutoCommit(true);

      int columnCount = columns.size();
      for (int i = 0; i < ROW_COUNT; i++) {
        Row row = new Row(columnCount);
        for (int j = 0; j < columnCount; j++) {
          row.setValue(j, createDummyText());
        }
        ls.add(row);
      }

      grid = new Grid<Row>(ls, cm, new WordWrapGridView());
      grid.getView().setColumnHeader(new WordWrapColumnHeader<Row>(grid, cm));
      grid.getView().setColumnLines(true);
      grid.addViewReadyHandler(new ViewReadyHandler() {
        @Override
        public void onViewReady(ViewReadyEvent event) {
          Info.display("onViewReady", "heading width=" + grid.getView().getHeader().getOffsetWidth() + ", height="
              + grid.getView().getHeader().getOffsetHeight());
          grid.getView().getHeader().addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
              Info.display("onResize", "heading width=" + event.getWidth() + ", height=" + event.getHeight());
            }
          });
        }
      });

      // Editing
      editing = new GridInlineEditing<Row>(grid) {
        @Override
        protected void onScroll(ScrollEvent event) {
          // Suppress default action, which may result in canceling edit
        }
      };
      editing.setClicksToEdit(ClicksToEdit.TWO);
      editing.addStartEditHandler(new StartEditHandler<Row>() {
        @Override
        public void onStartEdit(StartEditEvent<Row> event) {
          WordWrapGridExample.this.onStartEdit(event);
        }
      });

      for (ColumnConfig<Row, ?> cc : columns) {
        @SuppressWarnings("unchecked")
        ColumnConfig<Row, String> scc = (ColumnConfig<Row, String>) cc;
        final TextArea ta = new TextArea();
        ta.setPreventScrollbars(true);
        ta.addKeyDownHandler(new KeyDownHandler() {
          @Override
          public void onKeyDown(KeyDownEvent event) {
            // Allow the enter key to end grid inline editing
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
              Scheduler.get().scheduleFinally(new RepeatingCommand() {
                @Override
                public boolean execute() {
                  editing.completeEditing();
                  ta.clear();
                  return false;
                }
              });
            }
          }
        });
        editing.addEditor(scc, ta);
      }

      panel = new ContentPanel();
      panel.setHeading("Word Wrap Grid");
      panel.add(grid);
      panel.addButton(new TextButton("Set Heading Text", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          onSetheadingText(event);
        }
      }));
      panel.addButton(new TextButton("Set Cell Text", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          onSetCellText(event);
        }
      }));
      panel.addButton(new TextButton("Set Column Width", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          onSetColumnWidth(event);
        }
      }));
      panel.addButton(new TextButton("Set Heading Height", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          onSetHeadingHeight(event);
        }
      }));
      panel.addButton(new TextButton("Get Heading Height", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          int headerHeight = grid.getView().getHeader().getOffsetHeight();
          Info.display("getHeight", "height=" + headerHeight);
        }
      }));

      final ToggleButton toggleButton = new ToggleButton("Force Fit");
      toggleButton.setValue(true);
      toggleButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (toggleButton.getValue()) {
            columnWidths.save(grid);
            grid.getView().setForceFit(true);
          } else {
            grid.getView().setForceFit(false);
            columnWidths.restore(grid);
          }
          grid.getView().layout();
        }
      });
      panel.addButton(toggleButton);
      panel.show();

      // save column state and force fit by default
      Scheduler.get().scheduleFinally(new Scheduler.ScheduledCommand() {
        @Override
        public void execute() {
          columnWidths.save(grid);
          grid.getView().setForceFit(true);
          grid.getView().layout();
        }
      });
    }

    return panel;
  }

  protected void onSetColumnWidth(SelectEvent event) {
    final IntegerSpinnerField columnIndex = new IntegerSpinnerField();
    columnIndex.setMinValue(0);
    columnIndex.setMaxValue(COLUMN_COUNT - 1);
    columnIndex.setValue(0);
    columnIndex.setAllowBlank(false);
    columnIndex.setSelectOnFocus(true);

    final IntegerSpinnerField width = new IntegerSpinnerField();
    width.setMinValue(0);
    width.setValue(50);
    width.setAllowBlank(false);
    width.setSelectOnFocus(true);

    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
    vlc.add(new FieldLabel(columnIndex, "Column Index"), new VerticalLayoutData(1, -1, M1));
    vlc.add(new FieldLabel(width, "Column Width"), new VerticalLayoutData(1, -1, M2));

    final Window window = new Window();
    window.setHeading("Set Column Width");
    window.setPixelSize(300, Theme.TRITON.isActive() ? 190 : 150);
    window.setResizable(false);
    window.setModal(true);
    window.setWidget(vlc);
    window.addButton(new TextButton("Cancel", new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        window.hide();
      }
    }));
    window.addButton(new TextButton("OK", new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        if (columnIndex.validate() && width.validate()) {
          window.hide();
          grid.getColumnModel().setColumnWidth(columnIndex.getValue(), width.getValue());
        }
      }
    }));
    window.show();
    window.setFocusWidget(columnIndex);
  }

  protected void onSetCellText(SelectEvent event) {
    final IntegerSpinnerField rowIndex = new IntegerSpinnerField();
    rowIndex.setMinValue(0);
    rowIndex.setMaxValue(ROW_COUNT - 1);
    rowIndex.setValue(0);
    rowIndex.setAllowBlank(false);
    rowIndex.setSelectOnFocus(true);

    final IntegerSpinnerField columnIndex = new IntegerSpinnerField();
    columnIndex.setMinValue(0);
    columnIndex.setMaxValue(COLUMN_COUNT - 1);
    columnIndex.setValue(0);
    columnIndex.setAllowBlank(false);
    columnIndex.setSelectOnFocus(true);

    final TextArea text = new TextArea();

    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
    vlc.add(new FieldLabel(rowIndex, "Row Index"), new VerticalLayoutData(1, -1, M1));
    vlc.add(new FieldLabel(columnIndex, "Column Index"), new VerticalLayoutData(1, -1, M1));
    vlc.add(new FieldLabel(text, "Cell Text"), new VerticalLayoutData(1, 1, M2));

    final Window window = new Window();
    window.setHeading("Set Cell Text");
    window.setPixelSize(300, Theme.TRITON.isActive() ? 285 : 200);
    window.setResizable(false);
    window.setModal(true);
    window.setWidget(vlc);
    window.addButton(new TextButton("Cancel", new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        window.hide();
      }
    }));
    window.addButton(new TextButton("OK", new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        if (rowIndex.validate() && columnIndex.validate()) {
          window.hide();
          String newText = text.getValue();
          if (newText == null) {
            newText = "";
          }
          Row row = ls.get(rowIndex.getValue());
          row.setValue(columnIndex.getValue(), newText);
          ls.update(row);
        }
      }
    }));
    window.show();
    window.setFocusWidget(rowIndex);
  }

  protected void onSetHeadingHeight(SelectEvent event) {
    final IntegerSpinnerField height = new IntegerSpinnerField();
    height.setMinValue(-1);
    height.setValue(50);
    height.setAllowBlank(false);
    height.setSelectOnFocus(true);

    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
    vlc.add(new HTML(
        "<span style='font: 12px tahoma,arial,verdana,sans-serif;'>Sets the size of the heading to a fixed height. If this height is less than the height of the heading text, the heading text will be truncated.<br><br>To restore automatic sizing, specify a value of -1.<br><br></span>"),
        new VerticalLayoutData(1, -1, M1));
    vlc.add(new FieldLabel(height, "Heading Height"), new VerticalLayoutData(1, -1, M2));

    final Window window = new Window();
    window.setHeading("Set Heading Height");
    window.setPixelSize(300, 300);
    window.setResizable(false);
    window.setModal(true);
    window.setWidget(vlc);
    window.addButton(new TextButton("Cancel", new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        window.hide();
      }
    }));
    window.addButton(new TextButton("OK", new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        if (height.validate()) {
          window.hide();
          grid.getView().getHeader().setHeight(height.getValue());
        }
      }
    }));
    window.show();
    window.setFocusWidget(height);
  }

  protected void onSetheadingText(SelectEvent event) {
    final IntegerSpinnerField index = new IntegerSpinnerField();
    index.setMinValue(0);
    index.setMaxValue(COLUMN_COUNT - 1);
    index.setValue(0);
    index.setAllowBlank(false);
    index.setSelectOnFocus(true);

    final TextArea text = new TextArea();

    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
    vlc.add(new FieldLabel(index, "Column Index"), new VerticalLayoutData(1, -1, M1));
    vlc.add(new FieldLabel(text, "Heading Text"), new VerticalLayoutData(1, 1, M2));

    final Window window = new Window();
    window.setHeading("Set Heading Text");
    window.setPixelSize(300, Theme.TRITON.isActive() ? 240 : 200);
    window.setResizable(false);
    window.setModal(true);
    window.setWidget(vlc);
    window.addButton(new TextButton("Cancel", new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        window.hide();
      }
    }));
    window.addButton(new TextButton("OK", new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        if (index.validate()) {
          window.hide();
          SafeHtml newHeading = SafeHtmlUtils.EMPTY_SAFE_HTML;
          String newText = text.getValue();
          if (newText == null || newText.isEmpty()) {
            newHeading = Util.NBSP_SAFE_HTML;
          } else {
            newHeading = wrapString(newText);
          }
          ColumnModel<Row> columnModel = grid.getColumnModel();
          columnModel.setColumnHeader(index.getValue(), newHeading);
        }
      }
    }));
    window.show();
    window.setFocusWidget(index);
  }

  protected void onStartEdit(StartEditEvent<Row> event) {
    GridCell cell = event.getEditCell();

    ColumnConfig<Row, ?> columnConfig = cm.getColumn(cell.getCol());

    Field<Object> editor = (Field<Object>) editing.getEditor(columnConfig);

    Element rowElement = grid.getView().getRow(cell.getRow());
    // Resize the inline editor to the height of the row and style it to match the text
    int height = rowElement.getOffsetHeight() - 1;
    editor.setHeight(height);

    XElement cellElement = grid.getView().getCell(cell.getRow(), cell.getCol()).cast();
    Style style = ((TextAreaAppearance) editor.getCell().getAppearance()).getInputElement(editor.getElement())
        .getStyle();
    String fontSize = cellElement.getComputedStyle("fontSize");
    
    if (fontSize != null) {
      style.setProperty("fontSize", fontSize);
    }
    
    String fontFamily = cellElement.getComputedStyle("fontFamily");
    if (fontFamily != null) {
      style.setProperty("fontFamily", fontFamily);
    }
    
    style.setOverflow(Overflow.HIDDEN);
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMaxHeight(MAX_HEIGHT).setMaxWidth(MAX_WIDTH).setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH).doStandalone();
  }

  private String createDummyHeaderText(Set<String> existingHeaders) {
    String sentence = null;
    while ((sentence = SENTENCES[Random.nextInt(SENTENCES.length)]) != null) {
      if (sentence.length() < 50 && !existingHeaders.contains(sentence)) {
        break;
      }
    }
    return sentence;
  }

  private String createDummyText() {
    int sentenceCount = 1 + Random.nextInt(5);
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < sentenceCount; i++) {
      s.append(SENTENCES[Random.nextInt(SENTENCES.length)]);
      s.append(". ");
    }
    return s.toString();
  }

  private SafeHtml wrapString(String untrustedString) {
    SafeHtmlBuilder sb = new SafeHtmlBuilder();
    sb.appendHtmlConstant("<span style='white-space: normal;'>");
    sb.appendEscaped(untrustedString);
    sb.appendHtmlConstant("</span>");
    return sb.toSafeHtml();
  }

}
