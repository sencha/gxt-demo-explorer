package com.sencha.gxt.explorer.client.chart;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.Gradient;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.Stop;
import com.sencha.gxt.explorer.client.chart.ThemedChartExample.ThemedChartAppearance;

public class GrayThemedChartAppearance implements ThemedChartAppearance {

  @Override
  public Gradient barColor() {
    Gradient gradient = new Gradient(90);
    gradient.addStop(new Stop(0, RGB.LIGHTGRAY));
    gradient.addStop(new Stop(100, RGB.DARKGRAY));
    return gradient;
  }

  @Override
  public Color lineColor() {
    return RGB.LIGHTGRAY;
  }

  @Override
  public Color markerColor() {
    return RGB.GRAY;
  }

}
