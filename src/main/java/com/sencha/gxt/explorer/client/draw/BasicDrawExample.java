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
package com.sencha.gxt.explorer.client.draw;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.chart.client.draw.Gradient;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.Rotation;
import com.sencha.gxt.chart.client.draw.Scaling;
import com.sencha.gxt.chart.client.draw.path.CurveTo;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.CircleSprite;
import com.sencha.gxt.chart.client.draw.sprite.EllipseSprite;
import com.sencha.gxt.chart.client.draw.sprite.ImageSprite;
import com.sencha.gxt.chart.client.draw.sprite.RectangleSprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.examples.resources.client.images.ExampleImages;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

@Detail(
    name = "Basic Draw",
    category = "Draw",
    icon = "basicdraw",
    minHeight = BasicDrawExample.MIN_HEIGHT,
    minWidth = BasicDrawExample.MIN_WIDTH
)
public class BasicDrawExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 315;
  protected static final int MIN_WIDTH = 315;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      TextSprite text = new TextSprite();
      text.setText("Hello\nWorld!");
      text.setX(10);
      text.setY(25);
      text.setFont("Helvetica");
      text.setFontSize(18);
      text.setFill(RGB.BLACK);

      Scaling scale = new Scaling();
      scale.setX(2);
      scale.setY(2);

      Gradient gradient = new Gradient(21);
      gradient.addStop(0, new Color("#79A933"));
      gradient.addStop(13, new Color("#70A333"));
      gradient.addStop(34, new Color("#559332"));
      gradient.addStop(58, new Color("#277B2F"));
      gradient.addStop(86, new Color("#005F27"));
      gradient.addStop(100, new Color("#005020"));

      CircleSprite circle = new CircleSprite();
      circle.setCenterX(120);
      circle.setCenterY(100);
      circle.setRadius(25);
      circle.setScaling(scale);
      circle.setStroke(new Color("#999"));
      circle.setFill(gradient);
      circle.setStrokeWidth(3);

      Rotation rotate = new Rotation();
      rotate.setDegrees(45);
      rotate.setX(125 + 50 / 2);
      rotate.setY(75 + 50 / 2);

      RectangleSprite rect = new RectangleSprite();
      rect.setX(125);
      rect.setY(75);
      rect.setRotation(rotate);
      rect.setWidth(50);
      rect.setHeight(50);
      rect.setRadius(10);
      rect.setFill(new Color("#bf292f"));

      PathSprite path = new PathSprite();
      path.addCommand(new MoveTo(75, 75));
      path.addCommand(new CurveTo(0, -25, 50, 25, 50, 0, true));
      path.addCommand(new CurveTo(0, -25, -50, 25, -50, 0, true));
      path.setStroke(new Color("#000"));
      path.setStrokeWidth(2);
      path.setFill(new Color("#fc0"));
      path.setFillOpacity(0.25);

      EllipseSprite ellipse = new EllipseSprite();
      ellipse.setCenterX(175);
      ellipse.setCenterY(100);
      ellipse.setRadiusX(25);
      ellipse.setRadiusY(40);
      ellipse.setFillOpacity(0.56);
      ellipse.setStroke(new Color("#000"));
      ellipse.setFill(new Color("#2fb92f"));
      ellipse.setStrokeWidth(5);

      rotate = new Rotation();
      rotate.setDegrees(315);
      rotate.setX(ellipse.getCenterX() + ellipse.getRadiusX());
      rotate.setY(ellipse.getCenterY() + ellipse.getRadiusY());
      ellipse.setRotation(rotate);

      ImageSprite imageSprite = new ImageSprite(ExampleImages.INSTANCE.music());
      imageSprite.setX(50);
      imageSprite.setY(10);

      DrawComponent component = new DrawComponent();
      component.addSprite(text);
      component.addGradient(gradient);
      component.addSprite(circle);
      component.addSprite(rect);
      component.addSprite(path);
      component.addSprite(ellipse);
      component.addSprite(imageSprite);

      VerticalLayoutContainer layout = new VerticalLayoutContainer();
      layout.add(component, new VerticalLayoutData(1, 1));

      panel = new ContentPanel();
      panel.setHeading("Basic Draw");
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
