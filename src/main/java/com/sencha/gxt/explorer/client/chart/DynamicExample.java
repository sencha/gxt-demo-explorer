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
package com.sencha.gxt.explorer.client.chart;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Chart.Position;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.LineSeries;
import com.sencha.gxt.chart.client.chart.series.Primitives;
import com.sencha.gxt.chart.client.chart.series.SeriesHighlighter;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.DrawFx;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.examples.resources.client.model.MapValueProvider;
import com.sencha.gxt.examples.resources.client.model.ModelItem;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.error.DefaultEditorError;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
    name = "Dynamic Line Chart",
    category = "Charts",
    icon = "dynamiclinechart",
    classes = {
        MapValueProvider.class,
        ModelItem.class
    },
    minHeight = DynamicExample.MIN_HEIGHT,
    minWidth = DynamicExample.MIN_WIDTH
)
public class DynamicExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 640;

  private final NumericAxis<ModelItem> axis = new NumericAxis<ModelItem>();

  private Widget widget;

  public Widget asWidget() {
    if (widget == null) {
      widget = constructUi();
    }

    return widget;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

  private Widget constructUi() {
    final ListStore<ModelItem> store = new ListStore<ModelItem>(new ModelKeyProvider<ModelItem>() {
      @Override
      public String getKey(ModelItem item) {
        return String.valueOf(item.getKey());
      }
    });

    for (int i = 0; i < 12; i++) {
      ModelItem item = new ModelItem("item" + i);
      item.put("first", Math.random() * 100);
      store.add(item);
    }

    LineSeries<ModelItem> series = createLine("first");

    final ListStore<LineSeries<ModelItem>> fieldStore = new ListStore<LineSeries<ModelItem>>(
        new ModelKeyProvider<LineSeries<ModelItem>>() {
          @Override
          public String getKey(LineSeries<ModelItem> item) {
            return item.getYField().getPath();
          }
        });
    fieldStore.add(series);

    TextSprite title = new TextSprite("Number of Hits");
    title.setFontSize(18);

    PathSprite odd = new PathSprite();
    odd.setOpacity(1);
    odd.setFill(new Color("#ddd"));
    odd.setStroke(new Color("#bbb"));
    odd.setStrokeWidth(0.5);

    axis.addField(series.getYField());
    axis.setPosition(Position.LEFT);
    axis.setTitleConfig(title);
    axis.setMinorTickSteps(1);
    axis.setDisplayGrid(true);
    axis.setGridOddConfig(odd);

    Legend<ModelItem> legend = new Legend<ModelItem>();
    legend.setItemHighlighting(true);
    legend.setItemHiding(true);
    legend.getBorderConfig().setStrokeWidth(0);

    final Chart<ModelItem> chart = new Chart<ModelItem>();
    chart.setStore(store);
    chart.setShadowChart(false);
    chart.setAnimated(true);
    chart.addSeries(series);
    chart.addAxis(axis);
    chart.setLegend(legend);
    chart.setDefaultInsets(30);

    TextButton regenerate = new TextButton("Reload Data");
    regenerate.addSelectHandler(new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        for (int i = 0; i < store.size(); i++) {
          ModelItem item = store.get(i);
          for (String field : item.keySet()) {
            item.put(field, Math.random() * 100);
          }
        }
        chart.redrawChart();
      }
    });

    final ComboBox<LineSeries<ModelItem>> comboBox = new ComboBox<LineSeries<ModelItem>>(fieldStore,
        new LabelProvider<LineSeries<ModelItem>>() {
          @Override
          public String getLabel(LineSeries<ModelItem> item) {
            return item.getYField().getPath();
          }
        });
    comboBox.setTriggerAction(TriggerAction.ALL);

    final RegExp regex = RegExp.compile("\\s");

    final TextField fieldInput = new TextField();
    fieldInput.setValue("second");
    fieldInput.setAllowBlank(false);
    fieldInput.addValidator(new MaxLengthValidator(20));
    fieldInput.addValidator(new Validator<String>() {
      @Override
      public List<EditorError> validate(Editor<String> editor, String value) {
        if (regex.test(value)) {
          List<EditorError> errors = new ArrayList<EditorError>();
          errors.add(new DefaultEditorError(editor, "Field name cannot contain spaces.", ""));
          return errors;
        }
        return null;
      }
    });

    TextButton add = new TextButton("Add");
    add.addSelectHandler(new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        String field = fieldInput.getValue();
        if (fieldInput.isCurrentValid() && field.length() > 0 && fieldStore.findModelWithKey(field) == null
            && fieldStore.size() < 10) {
          for (int i = 0; i < store.size(); i++) {
            ModelItem item = store.get(i);
            item.put(field, Math.random() * 100);
          }
          LineSeries<ModelItem> series = createLine(field);
          fieldStore.add(series);
          axis.addField(series.getYField());
          chart.addSeries(series);
          chart.redrawChart();
        }
      }
    });

    TextButton remove = new TextButton("Remove");
    remove.addSelectHandler(new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        String field = comboBox.getText();
        LineSeries<ModelItem> series = fieldStore.findModelWithKey(field);
        if (field.length() > 0 && series != null && fieldStore.size() > 0) {
          for (int i = 0; i < store.size(); i++) {
            ModelItem item = store.get(i);
            item.remove(field);
          }
          fieldStore.remove(series);
          axis.removeField(series.getYField());
          chart.removeSeries(series);
          chart.redrawChart();
        }
        comboBox.clear();
      }
    });

    ToolBar toolBar = new ToolBar();
    toolBar.setHorizontalSpacing(5);
    toolBar.add(regenerate);
    toolBar.add(fieldInput);
    toolBar.add(add);
    toolBar.add(comboBox);
    toolBar.add(remove);

    VerticalLayoutContainer layout = new VerticalLayoutContainer();
    layout.add(toolBar, new VerticalLayoutData(1, -1));
    layout.add(chart, new VerticalLayoutData(1, 1));

    ContentPanel panel = new ContentPanel();
    panel.setHeading("Dynamic Line Chart");
    panel.add(layout);

    return panel;
  }

  private LineSeries<ModelItem> createLine(String field) {
    MapValueProvider valueProvider = new MapValueProvider(field);

    Color color = new RGB((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
    
    Sprite marker = Primitives.circle(0, 0, 6);
    marker.setFill(color);
    
    LineSeries<ModelItem> series = new LineSeries<ModelItem>();
    series.setYAxisPosition(Position.LEFT);
    series.setStroke(color);
    series.setStrokeWidth(3);
    series.setShowMarkers(true);
    series.setMarkerConfig(marker);
    series.setYField(valueProvider);
    series.setLineHighlighter(new SeriesHighlighter() {
      @Override
      public void highlight(Sprite sprite) {
        DrawFx.createStrokeWidthAnimator(sprite, 6).run(250);
      }

      @Override
      public void unHighlight(Sprite sprite) {
        DrawFx.createStrokeWidthAnimator(sprite, 3).run(250);
      }
    });

    return series;
  }

}
