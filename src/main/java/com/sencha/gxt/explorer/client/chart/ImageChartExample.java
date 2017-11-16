package com.sencha.gxt.explorer.client.chart;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Chart.Position;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.BarSeries;
import com.sencha.gxt.chart.client.chart.series.Series.LabelPosition;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelConfig;
import com.sencha.gxt.chart.client.chart.series.SeriesRenderer;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.ImageSprite;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.images.ExampleImages;
import com.sencha.gxt.examples.resources.client.model.Data;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
    name = "Image Chart",
    category = "Charts",
    icon = "imagechart",
    classes = { Data.class, TestData.class },
    minHeight = ImageChartExample.MIN_HEIGHT,
    minWidth = ImageChartExample.MIN_WIDTH
)
public class ImageChartExample implements IsWidget, EntryPoint {

  public interface DataPropertyAccess extends PropertyAccess<Data> {
    ValueProvider<Data, Double> data1();

    ValueProvider<Data, Double> data2();

    ValueProvider<Data, Double> data3();

    ValueProvider<Data, String> name();

    @Path("id")
    ModelKeyProvider<Data> nameKey();
  }

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 640;

  private static final DataPropertyAccess dataAccess = GWT.create(DataPropertyAccess.class);

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final ListStore<Data> store = new ListStore<Data>(dataAccess.nameKey());
      store.addAll(TestData.getData(5, 0, 100));

      TextSprite title = new TextSprite("Number of Visits");
      title.setFontSize(18);
      
      NumericAxis<Data> axis = new NumericAxis<Data>();
      axis.setPosition(Position.LEFT);
      axis.addField(dataAccess.data1());
      axis.addField(dataAccess.data2());
      axis.addField(dataAccess.data3());
      axis.setTitleConfig(title);
      axis.setDisplayGrid(true);
      axis.setMinimum(0);
      axis.setMaximum(100);

      title = new TextSprite("Month of the Year");
      title.setFontSize(18);
      
      CategoryAxis<Data, String> catAxis = new CategoryAxis<Data, String>();
      catAxis.setPosition(Position.BOTTOM);
      catAxis.setField(dataAccess.name());
      catAxis.setTitleConfig(title);

      final BarSeries<Data> bar = new BarSeries<Data>();
      bar.setYAxisPosition(Position.LEFT);
      bar.addYField(dataAccess.data1());
      bar.addYField(dataAccess.data2());
      bar.addYField(dataAccess.data3());
      bar.addColor(new RGB(241, 251, 150));
      bar.addColor(new RGB(190, 220, 248));
      bar.addColor(new RGB(248, 190, 196));
      bar.setColumn(true);
      
      ImageSprite image = new ImageSprite(ExampleImages.INSTANCE.userKid());
      image.setX(-8);
      image.setY(0);
      
      SeriesRenderer<Data> imageRenderer = new SeriesRenderer<Data>() {
        @Override
        public void spriteRenderer(Sprite sprite, int index, ListStore<Data> store) {
          switch (index % 3) {
            case 1:
              ((ImageSprite) sprite).setResource(ExampleImages.INSTANCE.user());
              break;
            case 2:
              ((ImageSprite) sprite).setResource(ExampleImages.INSTANCE.userFemale());
              break;
            default:
              break;
          }
          sprite.redraw();
        }
      };
      
      SeriesLabelConfig<Data> config = new SeriesLabelConfig<Data>();
      config.setLabelPosition(LabelPosition.END);
      config.setSpriteConfig(image);     
      config.setSpriteRenderer(imageRenderer);
      
      List<String> legendTitles = new ArrayList<String>();
      legendTitles.add("Children");
      legendTitles.add("Men");
      legendTitles.add("Women");
      
      bar.setLabelConfig(config);
      bar.setLegendTitles(legendTitles);

      SeriesRenderer<Data> legendImageRenderer = new SeriesRenderer<Data>() {
        @Override
        public void spriteRenderer(Sprite sprite, int index, ListStore<Data> store) {
          switch (index % 3) {
            case 1:
              ((ImageSprite) sprite).setResource(ExampleImages.INSTANCE.user());
              break;
            case 2:
              ((ImageSprite) sprite).setResource(ExampleImages.INSTANCE.userFemale());
              break;
            default:
              break;
          }
          ((ImageSprite) sprite).setY(-8);
          sprite.redraw();
        }
      };

      final Legend<Data> legend = new Legend<Data>();
      legend.setItemHighlighting(true);
      legend.setItemHiding(true);
      legend.setMarkerRenderer(legendImageRenderer);
      legend.getBorderConfig().setStrokeWidth(0);

      final Chart<Data> chart = new Chart<Data>();
      chart.setStore(store);
      chart.setShadowChart(false);
      chart.addAxis(axis);
      chart.addAxis(catAxis);
      chart.addSeries(bar);
      chart.setLegend(legend);
      chart.setDefaultInsets(30);

      TextButton regenerate = new TextButton("Reload Data");
      regenerate.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          store.clear();
          store.addAll(TestData.getData(5, 0, 100));
          chart.redrawChart();
        }
      });

      ToggleButton animation = new ToggleButton("Animate");
      animation.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          chart.setAnimated(event.getValue());
        }
      });
      animation.setValue(true, true);

      ToggleButton shadow = new ToggleButton("Shadow");
      shadow.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          chart.setShadowChart(event.getValue());
          chart.redrawChart();
        }
      });
      shadow.setValue(false);

      ToolBar toolBar = new ToolBar();
      toolBar.add(regenerate);
      toolBar.add(animation);
      toolBar.add(shadow);

      VerticalLayoutContainer layout = new VerticalLayoutContainer();
      layout.add(toolBar, new VerticalLayoutData(1, -1));
      layout.add(chart, new VerticalLayoutData(1, 1));

      panel = new ContentPanel();
      panel.setHeading("Image Chart");
      panel.add(layout);
    }

    return panel;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
