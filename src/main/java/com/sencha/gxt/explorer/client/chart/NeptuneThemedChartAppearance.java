package com.sencha.gxt.explorer.client.chart;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.Gradient;
import com.sencha.gxt.explorer.client.chart.ThemedChartExample.ThemedChartAppearance;

public class NeptuneThemedChartAppearance implements ThemedChartAppearance {

  @Override
  public Gradient barColor() {
    Gradient g = new Gradient(0);
    g.addStop(0, new Color("#add2ed"));
    return g;
  }

  @Override
  public Color lineColor() {
    return new Color("#157FCC");
  }

  @Override
  public Color markerColor() {
    return lineColor();
  }

}
