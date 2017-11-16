package com.sencha.gxt.explorer.client.chart;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.Gradient;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.Stop;
import com.sencha.gxt.explorer.client.chart.ThemedChartExample.ThemedChartAppearance;

public class BlueThemedChartAppearance implements ThemedChartAppearance {

  @Override
  public Gradient barColor() {
    Gradient gradient = new Gradient(90);
    gradient.addStop(new Stop(0, new RGB("#99BBE8")));
    gradient.addStop(new Stop(70, new RGB("#77AECE")));
    gradient.addStop(new Stop(100, new RGB("#77AECE")));
    return gradient;
  }

  @Override
  public Color lineColor() {
    return new RGB("#18428E");
  }

  @Override
  public Color markerColor() {
    return new RGB("#18428E");
  }

}
