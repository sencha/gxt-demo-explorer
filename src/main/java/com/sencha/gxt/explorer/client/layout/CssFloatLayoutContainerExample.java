package com.sencha.gxt.explorer.client.layout;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.Resizable;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.ResizeEndEvent;
import com.sencha.gxt.widget.core.client.event.ResizeEndEvent.ResizeEndHandler;

@Detail(
  name = "CSS Float Layout Container",
  category = "Layouts",
  icon = "vboxlayout",
  minHeight = BorderLayoutExample.MIN_HEIGHT,
  minWidth = BorderLayoutExample.MIN_WIDTH)
public class CssFloatLayoutContainerExample implements EntryPoint, IsWidget {

  protected static final int MIN_HEIGHT = -1;
  protected static final int MIN_WIDTH = -1;

  private String lineColor = "";
  private VerticalLayoutContainer vlc;

  @Override
  public Widget asWidget() {
    if (vlc == null) {
      lineColor = "blue";
      CssFloatLayoutContainer con = new CssFloatLayoutContainer();
      con.setBorders(true);

      CssFloatData layoutData = new CssFloatData(1, new Margins(10));
      con.add(new HTML("Try resizing this container."), layoutData);
      
      con.getElement().getStyle().setBackgroundColor("#FFF");
      con.add(getRowWidget("l1. 100%, 10px margin"), new CssFloatData(1, new Margins(10)));
      con.add(getRowWidget("l1. 150px, no margin"), new CssFloatData(150));

      lineColor = "red";
      con.add(getRowWidget("l2. 100% no margin"), new CssFloatData(1));

      lineColor = "orange";
      con.add(getRowWidget("l3. 50%, 10px margin"), new CssFloatData(.5, new Margins(10)));
      con.add(getRowWidget("l3. 50%, no margin"), new CssFloatData(.5));
      con.add(getRowWidget("l3. 150px, 10px margin"), new CssFloatData(150, new Margins(10)));

      lineColor = "brown";
      CssFloatData layoutData1 = new CssFloatData(300, new Margins(10));
      layoutData1.setClear(true);
      con.add(getRowWidget("l4. 300px, 10px margin, start of new line [CLEAR]"), layoutData1);

      lineColor = "gray";
      CssFloatData layoutData2 = new CssFloatData(300);
      layoutData2.setClear(true);
      con.add(getRowWidget("l5. 300px, no margin, start of new line [CLEAR]"), layoutData2);
      con.add(getRowWidget("l5. 150px, 10px margin"), new CssFloatData(150, new Margins(10)));
      con.add(getRowWidget("l5. 150px, 10px margin"), new CssFloatData(150, new Margins(10)));
      con.add(getRowWidget("l5. 150px, 10px margin"), new CssFloatData(150, new Margins(10)));

      lineColor = "coral";
      CssFloatData layoutData3 = new CssFloatData();
      layoutData3.setClear(true);
      con.add(getRowWidget("l6. start of line, fixed size via -1 (280px) [CLEAR]", 280), layoutData3);
      con.add(getRowWidget("l6. 100%, no margin"), new CssFloatData(1));

      lineColor = "black";
      CssFloatData layoutData4 = new CssFloatData(1);
      layoutData4.setClear(true);
      con.add(getRowWidget("l7. 100%, start of line [CLEAR]"), layoutData4);
      con.add(getRowWidget("l7. fixed size via -1 (200px)", 200));

      lineColor = "purple";
      CssFloatData layoutData5 = new CssFloatData();
      layoutData5.setClear(true);
      con.add(getRowWidget("l8. start of line, fixed size via -1 (280px) [CLEAR]", 280), layoutData5);
      con.add(getRowWidget("l8. 75%, no margin"), new CssFloatData(.75));

      vlc = new VerticalLayoutContainer();
      vlc.add(con, new VerticalLayoutData(1, -1, new Margins(10)));
      
      Resizable resizeable = new Resizable(vlc);
      resizeable.addResizeEndHandler(new ResizeEndHandler() {
        @Override
        public void onResizeEnd(ResizeEndEvent event) {
          vlc.setHeight(-1);
        }
      });
    }

    return vlc;
  }

  private Widget getRowWidget(String text, int width) {
    SimpleContainer w = new SimpleContainer();
    if (width != -1) {
      w.setWidth(width);
    }
    w.add(new Label(text), new MarginData(10));
    w.getElement().getStyle().setProperty("border", "2px solid " + lineColor);
    return w;
  }

  public Widget getRowWidget(String text) {
    return getRowWidget(text, -1);
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setPreferredWidth(800).doStandalone();
  }

}