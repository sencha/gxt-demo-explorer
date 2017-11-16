package com.sencha.gxt.explorer.client.tips;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.tips.QuickTip;

@Detail(
  name = "Quick Tips",
  category = "Tips",
  icon = "tooltips",
  minWidth = QuickTipsExample.MIN_WIDTH,
  preferredHeight = QuickTipsExample.PREFERRED_HEIGHT)
public class QuickTipsExample implements IsWidget, EntryPoint {

  protected static final int MIN_WIDTH = 360;
  protected static final int PREFERRED_HEIGHT = -1;

  private FlowLayoutContainer container;

  @Override
  public Widget asWidget() {
    if (container == null) {
      HTML html = new HTML("<span qtitle='The tips title.' qtip='The quick tips message.' "
          + "qwidth='100px'>Hover over me to see the quick tip.</span>");

      container = new FlowLayoutContainer();
      container.add(html);

      QuickTip.of(container);
    }

    return container;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinWidth(MIN_WIDTH).setPreferredHeight(PREFERRED_HEIGHT).doStandalone();
  }

}
