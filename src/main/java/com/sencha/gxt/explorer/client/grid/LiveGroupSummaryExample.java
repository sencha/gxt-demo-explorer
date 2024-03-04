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
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.NumberCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Task;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.form.DoubleField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GroupSummaryView;
import com.sencha.gxt.widget.core.client.grid.SummaryColumnConfig;
import com.sencha.gxt.widget.core.client.grid.SummaryRenderer;
import com.sencha.gxt.widget.core.client.grid.SummaryType;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;

@Detail(
  name = "Live Group Summary",
  category = "Grid",
  icon = "livegroupsummary",
  classes = { Task.class, TestData.class },
  maxHeight = LiveGroupSummaryExample.MAX_HEIGHT,
  maxWidth = LiveGroupSummaryExample.MAX_WIDTH,
  minHeight = LiveGroupSummaryExample.MIN_HEIGHT,
  minWidth = LiveGroupSummaryExample.MIN_WIDTH)
public class LiveGroupSummaryExample implements EntryPoint, IsWidget {

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 640;

  private ContentPanel panel;

  interface TaskProperties extends PropertyAccess<Task> {
    @Path("taskId")
    ModelKeyProvider<Task> key();

    ValueProvider<Task, String> description();

    ValueProvider<Task, String> project();

    ValueProvider<Task, String> due();

    ValueProvider<Task, Double> estimate();

    ValueProvider<Task, Double> rate();
  }

  @Override
  public Widget asWidget() {
    if (panel == null) {
      List<Task> tasks = TestData.getTasks();

      TaskProperties properties = GWT.create(TaskProperties.class);

      final ListStore<Task> store = new ListStore<Task>(properties.key());
      // Summaries can't be updated to latest value without calling commit or this set to true
      store.setAutoCommit(true);
      store.addAll(tasks);

      SummaryColumnConfig<Task, String> descColumn = new SummaryColumnConfig<Task, String>(properties.description(), 125, "Task");
      final SummaryColumnConfig<Task, String> projectColumn = new SummaryColumnConfig<Task, String>(properties.project(), 125, "Project");
      SummaryColumnConfig<Task, String> dueColumn = new SummaryColumnConfig<Task, String>(properties.due(), 90, "Due");
      SummaryColumnConfig<Task, Double> estimateColumn = new SummaryColumnConfig<Task, Double>(properties.estimate(), 75, "Estimate");
      SummaryColumnConfig<Task, Double> rateColumn = new SummaryColumnConfig<Task, Double>(properties.rate(), 100, "Rate");
      SummaryColumnConfig<Task, Task> costColumn = new SummaryColumnConfig<Task, Task>(new IdentityValueProvider<Task>(), 100, "Cost");

      descColumn.setSummaryType(new SummaryType.CountSummaryType<String>());
      descColumn.setSummaryRenderer(new SummaryRenderer<Task>() {
        @Override
        public SafeHtml render(Number value, Map<ValueProvider<? super Task, ?>, Number> data) {
          return SafeHtmlUtils
              .fromTrustedString(value.intValue() > 1 ? "(" + value.intValue() + " Tasks)" : "(1 Task)");
        }
      });

      estimateColumn.setSummaryType(new SummaryType.SumSummaryType<Double>());
      estimateColumn.setSummaryRenderer(new SummaryRenderer<Task>() {
        @Override
        public SafeHtml render(Number value, Map<ValueProvider<? super Task, ?>, Number> data) {
          return SafeHtmlUtils.fromTrustedString(value + " hours");
        }
      });
      estimateColumn.setCell(new AbstractCell<Double>() {
        @Override
        public void render(Context context, Double value, SafeHtmlBuilder sb) {
          sb.appendHtmlConstant(value + " hours");
        }
      });

      rateColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
      rateColumn.setCell(new NumberCell<Double>(NumberFormat.getCurrencyFormat()));
      rateColumn.setSummaryType(new SummaryType.AvgSummaryType<Double>());
      rateColumn.setSummaryFormat(NumberFormat.getCurrencyFormat());

      costColumn.setCellClassName("cost-column");
      costColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
      costColumn.setCell(new AbstractCell<Task>() {
        @Override
        public void render(Context context, Task value, SafeHtmlBuilder sb) {
          sb.appendHtmlConstant(NumberFormat.getCurrencyFormat().format(value.getRate() * value.getEstimate()));
        }
      });
      costColumn.setSummaryFormat(NumberFormat.getCurrencyFormat());
      costColumn.setSummaryType(new SummaryType<Task, Double>() {
        @Override
        public <M> Double calculate(List<? extends M> m, ValueProvider<? super M, Task> valueProvider) {
          double value = 0;
          for (int i = 0; i < m.size(); i++) {
            Task t = valueProvider.getValue(m.get(i));
            value = value + (t.getRate() * t.getEstimate());
          }
          return value;
        }
      });
      costColumn.setComparator(new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
          return Double.valueOf(o1.getRate() * o1.getEstimate()).compareTo(o2.getRate() * o2.getEstimate());
        }
      });

      List<ColumnConfig<Task, ?>> columns = new ArrayList<ColumnConfig<Task, ?>>();
      columns.add(descColumn);
      columns.add(projectColumn);
      columns.add(dueColumn);
      columns.add(estimateColumn);
      columns.add(rateColumn);
      columns.add(costColumn);

      ColumnModel<Task> cm = new ColumnModel<Task>(columns);

      final GroupSummaryView<Task> groupSummaryView = new GroupSummaryView<Task>();
      groupSummaryView.setAutoExpandColumn(descColumn);

      Grid<Task> grid = new Grid<Task>(store, cm);
      grid.setView(groupSummaryView);
      grid.getView().setShowDirtyCells(false);

      GridEditing<Task> editing = new GridInlineEditing<Task>(grid);
      editing.addEditor(estimateColumn, new DoubleField());
      editing.addEditor(rateColumn, new DoubleField());

      Scheduler.get().scheduleFinally(new ScheduledCommand() {
        @Override
        public void execute() {
          groupSummaryView.groupBy(projectColumn);
        }
      });

      panel = new ContentPanel();
      panel.setHeading("Live Group Summary");
      panel.add(grid);
    }

    return panel;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMaxHeight(MAX_HEIGHT).setMaxWidth(MAX_WIDTH).setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH).doStandalone();
  }

}
