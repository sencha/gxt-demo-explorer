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
package com.sencha.gxt.explorer.client.chart;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.RoundNumberProvider;
import com.sencha.gxt.chart.client.chart.series.Primitives;
import com.sencha.gxt.chart.client.chart.series.ScatterSeries;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelConfig;
import com.sencha.gxt.chart.client.chart.series.SeriesRenderer;
import com.sencha.gxt.chart.client.draw.DrawFx;
import com.sencha.gxt.chart.client.draw.HSV;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.CircleSprite;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite.TextAnchor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Data;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.fx.client.animation.Animator;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.menu.CheckMenuItem;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
    name = "Scatter Renderer Chart",
    category = "Charts",
    icon = "scatterrendererchart",
    classes = {
        Data.class,
        DrawFx.class,
        TestData.class
    },
    minHeight = ScatterRendererExample.MIN_HEIGHT,
    minWidth = ScatterRendererExample.MIN_WIDTH
)
public class ScatterRendererExample implements IsWidget, EntryPoint {

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

  private static final RGB DEFAULT_MIN_COLOR = new RGB(250, 20, 20);
  private static final RGB DEFAULT_MAX_COLOR = new RGB(127, 0, 220);

  private ScatterSeries<Data> series = new ScatterSeries<Data>();
  private ValueProvider<Data, Double> radiusField;
  private ValueProvider<Data, Double> colorField;
  private ValueProvider<Data, Double> grayField;
  private double maxRadius = 50;
  private boolean boundMinRGB;
  private boolean boundMaxRGB;
  private RGB lastSelectedColorFrom;
  private RGB lastSelectedColorTo;
  private RGB minColor = DEFAULT_MIN_COLOR;
  private RGB maxColor = DEFAULT_MAX_COLOR;
  private RGB minGray = new RGB(20, 20, 20);
  private RGB maxGray = new RGB(220, 220, 220);
  private ArrayList<RGB> colors = new ArrayList<RGB>();
  private ArrayList<RGB> grays = new ArrayList<RGB>();
  private Chart<Data> chart;
  private ContentPanel panel;

  public Widget asWidget() {
    if (panel == null) {
      // set up colors
      colors.add(new RGB(250, 20, 20));
      colors.add(new RGB(20, 250, 20));
      colors.add(new RGB(20, 20, 250));
      colors.add(new RGB(127, 0, 240));
      colors.add(new RGB(213, 70, 121));
      colors.add(new RGB(44, 153, 201));
      colors.add(new RGB(146, 6, 157));
      colors.add(new RGB(49, 149, 0));
      colors.add(new RGB(249, 153, 0));

      grays.add(new RGB(20, 20, 20));
      grays.add(new RGB(80, 80, 80));
      grays.add(new RGB(120, 120, 120));
      grays.add(new RGB(180, 180, 180));
      grays.add(new RGB(220, 220, 220));
      grays.add(new RGB(250, 250, 250));

      final ListStore<Data> store = new ListStore<Data>(dataAccess.nameKey());
      store.addAll(TestData.getData(12, 0, 100));

      Sprite marker = Primitives.circle(0, 0, 20);
      marker.setFill(RGB.BLUE);

      TextSprite textConfig = new TextSprite();
      textConfig.setFill(new RGB("#333"));
      textConfig.setTextAnchor(TextAnchor.MIDDLE);

      SeriesLabelConfig<Data> labelConfig = new SeriesLabelConfig<Data>();
      labelConfig.setSpriteConfig(textConfig);
      labelConfig.setLabelContrast(true);
      labelConfig.setValueProvider(dataAccess.data3(), new RoundNumberProvider<Double>());

      series.setXField(dataAccess.data1());
      series.setYField(dataAccess.data2());
      series.setMarkerConfig(marker);
      series.setLabelConfig(labelConfig);

      chart = new Chart<Data>();
      chart.setStore(store);
      chart.setDefaultInsets(30);
      chart.setShadowChart(false);
      chart.addSeries(series);

      TextButton regenerate = new TextButton("Reload Data");
      regenerate.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          store.clear();
          store.addAll(TestData.getData(12, 0, 100));
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

      CheckMenuItem xAxisMenuItem1 = new CheckMenuItem("data1");
      xAxisMenuItem1.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          series.setXField(dataAccess.data1());
          series.drawSeries();
        }
      });
      xAxisMenuItem1.setGroup("x-axis");
      xAxisMenuItem1.setChecked(true);

      CheckMenuItem xAxisMenuItem2 = new CheckMenuItem("data2");
      xAxisMenuItem2.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          series.setXField(dataAccess.data2());
          series.drawSeries();
        }
      });
      xAxisMenuItem2.setGroup("x-axis");

      CheckMenuItem xAxisMenuItem3 = new CheckMenuItem("data3");
      xAxisMenuItem3.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          series.setXField(dataAccess.data3());
          series.drawSeries();
        }
      });
      xAxisMenuItem3.setGroup("x-axis");

      CheckMenuItem yAxisMenuItem1 = new CheckMenuItem("data1");
      yAxisMenuItem1.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          series.setYField(dataAccess.data1());
          series.drawSeries();
        }
      });
      yAxisMenuItem1.setGroup("y-axis");

      CheckMenuItem yAxisMenuItem2 = new CheckMenuItem("data2");
      yAxisMenuItem2.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          series.setYField(dataAccess.data2());
          series.drawSeries();
        }
      });
      yAxisMenuItem2.setGroup("y-axis");
      yAxisMenuItem2.setChecked(true);

      CheckMenuItem yAxisMenuItem3 = new CheckMenuItem("data3");
      yAxisMenuItem3.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          series.setYField(dataAccess.data3());
          series.drawSeries();
        }
      });
      yAxisMenuItem3.setGroup("y-axis");

      CheckMenuItem colorMenuItem1 = new CheckMenuItem("data1");
      colorMenuItem1.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          colorField = dataAccess.data1();
          grayField = null;
          refresh();
        }
      });
      colorMenuItem1.setGroup("color");

      CheckMenuItem colorMenuItem2 = new CheckMenuItem("data2");
      colorMenuItem2.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          colorField = dataAccess.data2();
          grayField = null;
          refresh();
        }
      });
      colorMenuItem2.setGroup("color");

      CheckMenuItem colorMenuItem3 = new CheckMenuItem("data3");
      colorMenuItem3.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          colorField = dataAccess.data3();
          grayField = null;
          refresh();
        }
      });
      colorMenuItem3.setGroup("color");

      Menu colorFromMenu = new Menu();
      for (int i = 0; i < colors.size(); i++) {
        final int index = i;
        CheckMenuItem colorFromMenuItem = new CheckMenuItem(colors.get(index).toString());
        colorFromMenuItem.addSelectionHandler(new SelectionHandler<Item>() {
          @Override
          public void onSelection(SelectionEvent<Item> event) {
            minColor = colors.get(index);
            if (lastSelectedColorFrom == null || !lastSelectedColorFrom.equals(minColor)) {
              lastSelectedColorFrom = minColor;
              boundMinRGB = true;
            } else {
              lastSelectedColorFrom = null;
              minColor = DEFAULT_MIN_COLOR;
              boundMinRGB = false;
            }
            refresh();
          }
        });
        colorFromMenuItem.setGroup("colorFrom");
        colorFromMenu.add(colorFromMenuItem);
      }

      Menu colorToMenu = new Menu();
      for (int i = 0; i < colors.size(); i++) {
        final int index = i;
        CheckMenuItem colorToMenuItem = new CheckMenuItem(colors.get(index).toString());
        colorToMenuItem.addSelectionHandler(new SelectionHandler<Item>() {
          @Override
          public void onSelection(SelectionEvent<Item> event) {
            maxColor = colors.get(index);
            if (lastSelectedColorTo == null || !lastSelectedColorTo.equals(maxColor)) {
              lastSelectedColorTo = maxColor;
              boundMaxRGB = true;
            } else {
              lastSelectedColorTo = null;
              maxColor = DEFAULT_MAX_COLOR;
              boundMaxRGB = false;
            }
            refresh();
          }
        });
        colorToMenuItem.setGroup("colorTo");
        colorToMenu.add(colorToMenuItem);
      }

      CheckMenuItem grayMenuItem1 = new CheckMenuItem("data1");
      grayMenuItem1.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          grayField = dataAccess.data1();
          colorField = null;
          refresh();
        }
      });
      grayMenuItem1.setGroup("gray");

      CheckMenuItem grayMenuItem2 = new CheckMenuItem("data2");
      grayMenuItem2.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          grayField = dataAccess.data2();
          colorField = null;
          refresh();
        }
      });
      grayMenuItem2.setGroup("gray");

      CheckMenuItem grayMenuItem3 = new CheckMenuItem("data3");
      grayMenuItem3.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          grayField = dataAccess.data3();
          colorField = null;
          refresh();
        }
      });
      grayMenuItem3.setGroup("gray");

      Menu grayFromMenu = new Menu();
      for (int i = 0; i < grays.size(); i++) {
        final int index = i;
        CheckMenuItem grayFromMenuItem = new CheckMenuItem(grays.get(index).toString());
        grayFromMenuItem.addSelectionHandler(new SelectionHandler<Item>() {
          @Override
          public void onSelection(SelectionEvent<Item> event) {
            minGray = grays.get(index);
            refresh();
          }
        });
        grayFromMenuItem.setGroup("grayFrom");
        grayFromMenu.add(grayFromMenuItem);
      }

      Menu grayToMenu = new Menu();
      for (int i = 0; i < grays.size(); i++) {
        final int index = i;
        CheckMenuItem grayToMenuItem = new CheckMenuItem(grays.get(index).toString());
        grayToMenuItem.addSelectionHandler(new SelectionHandler<Item>() {
          @Override
          public void onSelection(SelectionEvent<Item> event) {
            maxGray = grays.get(index);
            refresh();
          }
        });
        grayToMenuItem.setGroup("grayTo");
        grayToMenu.add(grayToMenuItem);
      }

      CheckMenuItem radiusMenuItem1 = new CheckMenuItem("data1");
      radiusMenuItem1.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          radiusField = dataAccess.data1();
          refresh();
        }
      });
      radiusMenuItem1.setGroup("radius");

      CheckMenuItem radiusMenuItem2 = new CheckMenuItem("data2");
      radiusMenuItem2.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          radiusField = dataAccess.data2();
          refresh();
        }
      });
      radiusMenuItem2.setGroup("radius");

      CheckMenuItem radiusMenuItem3 = new CheckMenuItem("data3");
      radiusMenuItem3.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          radiusField = dataAccess.data3();
          refresh();
        }
      });
      radiusMenuItem3.setGroup("radius");

      CheckMenuItem maximumRadiusMenuItem1 = new CheckMenuItem("20");
      maximumRadiusMenuItem1.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          maxRadius = 20;
          refresh();
        }
      });
      maximumRadiusMenuItem1.setGroup("maximumRadius");

      CheckMenuItem maximumRadiusMenuItem2 = new CheckMenuItem("30");
      maximumRadiusMenuItem2.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          maxRadius = 30;
          refresh();
        }
      });
      maximumRadiusMenuItem2.setGroup("maximumRadius");

      CheckMenuItem maximumRadiusMenuItem3 = new CheckMenuItem("40");
      maximumRadiusMenuItem3.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          maxRadius = 40;
          refresh();
        }
      });
      maximumRadiusMenuItem3.setGroup("maximumRadius");

      CheckMenuItem maximumRadiusMenuItem4 = new CheckMenuItem("50");
      maximumRadiusMenuItem4.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          maxRadius = 50;
          refresh();
        }
      });
      maximumRadiusMenuItem4.setGroup("maximumRadius");

      CheckMenuItem maximumRadiusMenuItem5 = new CheckMenuItem("60");
      maximumRadiusMenuItem5.addSelectionHandler(new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          maxRadius = 60;
          refresh();
        }
      });
      maximumRadiusMenuItem5.setGroup("maximumRadius");

      Menu maximumRadiusMenu = new Menu();
      maximumRadiusMenu.add(maximumRadiusMenuItem1);
      maximumRadiusMenu.add(maximumRadiusMenuItem2);
      maximumRadiusMenu.add(maximumRadiusMenuItem3);
      maximumRadiusMenu.add(maximumRadiusMenuItem4);
      maximumRadiusMenu.add(maximumRadiusMenuItem5);

      MenuItem maximumRadius = new MenuItem("Max Radius");
      maximumRadius.setSubMenu(maximumRadiusMenu);

      Menu radiusMenu = new Menu();
      radiusMenu.add(radiusMenuItem1);
      radiusMenu.add(radiusMenuItem2);
      radiusMenu.add(radiusMenuItem3);
      radiusMenu.add(maximumRadius);

      Menu yAxisMenu = new Menu();
      yAxisMenu.add(yAxisMenuItem1);
      yAxisMenu.add(yAxisMenuItem2);
      yAxisMenu.add(yAxisMenuItem3);

      TextButton yAxis = new TextButton("Y Axis");
      yAxis.setMenu(yAxisMenu);

      Menu xAxisMenu = new Menu();
      xAxisMenu.add(xAxisMenuItem1);
      xAxisMenu.add(xAxisMenuItem2);
      xAxisMenu.add(xAxisMenuItem3);

      TextButton xAxis = new TextButton("X Axis");
      xAxis.setMenu(xAxisMenu);

      MenuItem colorFrom = new MenuItem("Color From");
      colorFrom.setSubMenu(colorFromMenu);

      TextButton radius = new TextButton("Radius");
      radius.setMenu(radiusMenu);

      MenuItem colorTo = new MenuItem("Color To");
      colorTo.setSubMenu(colorToMenu);

      Menu colorMenu = new Menu();
      colorMenu.add(colorMenuItem1);
      colorMenu.add(colorMenuItem2);
      colorMenu.add(colorMenuItem3);
      colorMenu.add(colorFrom);
      colorMenu.add(colorTo);

      TextButton color = new TextButton("Color");
      color.setMenu(colorMenu);

      MenuItem grayFrom = new MenuItem("Gray From");
      grayFrom.setSubMenu(grayFromMenu);

      MenuItem grayTo = new MenuItem("Gray To");
      grayTo.setSubMenu(grayToMenu);

      Menu grayMenu = new Menu();
      grayMenu.add(grayMenuItem1);
      grayMenu.add(grayMenuItem2);
      grayMenu.add(grayMenuItem3);
      grayMenu.add(grayFrom);
      grayMenu.add(grayTo);

      TextButton grayscale = new TextButton("GrayScale");
      grayscale.setMenu(grayMenu);

      ToolBar toolBar = new ToolBar();
      toolBar.add(regenerate);
      toolBar.add(animation);
      toolBar.add(shadow);
      toolBar.add(xAxis);
      toolBar.add(yAxis);
      toolBar.add(color);
      toolBar.add(grayscale);
      toolBar.add(radius);
      toolBar.setPack(BoxLayoutPack.CENTER);

      VerticalLayoutContainer layout = new VerticalLayoutContainer();
      layout.add(toolBar, new VerticalLayoutData(1, -1));
      layout.add(chart, new VerticalLayoutData(1, 1));

      panel = new ContentPanel();
      panel.setHeading("Scatter Renderer Chart");
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

  private SeriesRenderer<Data> color(final ValueProvider<Data, Double> fieldName, final RGB minColor,
      final RGB maxColor, final double minValue, final double maxValue) {
    final HSV min = new HSV(minColor);
    final HSV max = new HSV(maxColor);
    return new SeriesRenderer<Data>() {
      @Override
      public void spriteRenderer(Sprite sprite, int index, ListStore<Data> store) {
        double value = fieldName.getValue(store.get(index));
        double delta1 = delta(minValue, maxValue, min.getHue(), max.getHue(), value);
        double delta2 = delta(minValue, maxValue, min.getSaturation(), max.getSaturation(), value);
        double delta3 = delta(minValue, maxValue, min.getValue(), max.getValue(), value);
        RGB rgb = new RGB(new HSV(delta1, delta2, delta3));
        if (boundMinRGB || boundMaxRGB) {
          rgb.setRed(ensureRGBBounded(rgb.getRed(), minColor.getRed(), maxColor.getRed()));
          rgb.setGreen(ensureRGBBounded(rgb.getGreen(), minColor.getGreen(), maxColor.getGreen()));
          rgb.setBlue(ensureRGBBounded(rgb.getBlue(), minColor.getBlue(), maxColor.getBlue()));
        }
        if (chart.isAnimated()) {
          createFillAnimator(sprite, rgb).run(500);
        } else {
          sprite.setFill(rgb);
          sprite.redraw();
        }
      }
    };
  }

  private int ensureRGBBounded(int rgbColor, int minColor, int maxColor) {
    // if both are bounded, make sure to use the actual min/max rgb since the min color could have a higher max value
    // in a single channel
    int min = boundMinRGB && boundMaxRGB ? Math.min(minColor, maxColor) : minColor;
    int max = boundMinRGB && boundMaxRGB ? Math.max(minColor, maxColor) : maxColor;
    if (boundMinRGB && rgbColor < min) {
      return min;
    } else if (boundMaxRGB && rgbColor > max) {
      return max;
    }
    return rgbColor;
  }

  private Animator createFillAnimator(final Sprite sprite, RGB color) {
    if (!(sprite.getFill() instanceof RGB)) {
      return null;
    }

    RGB origin = (RGB) sprite.getFill();
    final int originR = origin.getRed();
    final int originG = origin.getGreen();
    final int originB = origin.getBlue();
    final int deltaR = color.getRed() - originR;
    final int deltaG = color.getGreen() - originG;
    final int deltaB = color.getBlue() - originB;

    return new Animator() {
      @Override
      protected void onUpdate(double progress) {
        sprite.setFill(new RGB(originR + (int) (deltaR * progress), originG + (int) (deltaG * progress), originB
            + (int) (deltaB * progress)));
        sprite.redraw();
      }
    };
  }

  private Animator createRadiusAnimator(final CircleSprite sprite, double radius) {
    final double origin = sprite.getRadius();
    final double delta = radius - origin;
    return new Animator() {
      @Override
      protected void onUpdate(double progress) {
        sprite.setRadius(origin + (delta * progress));
        sprite.redraw();
      }
    };
  }

  private double delta(double x, double y, double a, double b, double theta) {
    return a + (b - a) * (y - theta) / (y - x);
  }

  private SeriesRenderer<Data> grayscale(final ValueProvider<Data, Double> fieldName, final RGB minColor,
      final RGB maxColor, final double minValue, final double maxValue) {
    return new SeriesRenderer<Data>() {
      @Override
      public void spriteRenderer(Sprite sprite, int index, ListStore<Data> store) {
        double value = fieldName.getValue(store.get(index));
        double ans = delta(minValue, maxValue, minColor.getGreen(), maxColor.getGreen(), value);
        if (chart.isAnimated()) {
          createFillAnimator(sprite, new RGB((int) Math.round(ans), (int) Math.round(ans), (int) Math.round(ans))).run(
              500);
        } else {
          sprite.setFill(new RGB((int) Math.round(ans), (int) Math.round(ans), (int) Math.round(ans)));
          sprite.redraw();
        }
      }
    };
  }

  private SeriesRenderer<Data> radius(final ValueProvider<Data, Double> fieldName, final double minRadius,
      final double maxRadius, final double minValue, final double maxValue) {
    return new SeriesRenderer<Data>() {
      @Override
      public void spriteRenderer(Sprite sprite, int index, ListStore<Data> store) {
        double scale = delta(maxValue, minValue, maxRadius, minRadius, fieldName.getValue(store.get(index)));
        scale = maxRadius - scale + minRadius;
        if (chart.isAnimated()) {
          createRadiusAnimator((CircleSprite) sprite, scale).run(500);
        } else {
          ((CircleSprite) sprite).setRadius(scale);
          sprite.redraw();
        }
      }
    };
  }

  private void refresh() {
    series.setRenderer(new SeriesRenderer<Data>() {
      @Override
      public void spriteRenderer(Sprite sprite, int index, ListStore<Data> store) {
        if (colorField != null) {
          color(colorField, minColor, maxColor, 0, 100).spriteRenderer(sprite, index, store);
        }
        if (grayField != null) {
          grayscale(grayField, minGray, maxGray, 0, 100).spriteRenderer(sprite, index, store);
        }
        if (radiusField != null) {
          radius(radiusField, 10, maxRadius, 0, 100).spriteRenderer(sprite, index, store);
        }
      }
    });

    if (radiusField != null) {
      series.setShadowRenderer(radius(radiusField, 10, maxRadius, 0, 100));
    }
    series.drawSeries();
  }

}
