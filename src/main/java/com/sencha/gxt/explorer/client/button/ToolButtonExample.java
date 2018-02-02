package com.sencha.gxt.explorer.client.button;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.MarginData;

@Detail(
  name = "Tool Buttons",
  category = "Button",
  icon = "toolbuttons",
  minHeight = ToolButtonExample.MIN_HEIGHT,
  minWidth = ToolButtonExample.MIN_WIDTH,
  classes = { Utils.class, ToolButton.class })
public class ToolButtonExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 100;
  protected static final int MIN_WIDTH = 510;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      ToolButton[] buttons = new ToolButton[] { new ToolButton(ToolButton.CLOSE), new ToolButton(ToolButton.MINIMIZE),
          new ToolButton(ToolButton.MAXIMIZE), new ToolButton(ToolButton.RESTORE), new ToolButton(ToolButton.GEAR),
          new ToolButton(ToolButton.PIN), new ToolButton(ToolButton.UNPIN), new ToolButton(ToolButton.RIGHT),
          new ToolButton(ToolButton.LEFT), new ToolButton(ToolButton.DOWN), new ToolButton(ToolButton.REFRESH),
          new ToolButton(ToolButton.MINUS), new ToolButton(ToolButton.PLUS), new ToolButton(ToolButton.QUESTION),
          new ToolButton(ToolButton.SEARCH), new ToolButton(ToolButton.SAVE), new ToolButton(ToolButton.PRINT) };

      panel = new ContentPanel();
      panel.setHeading("Tool Buttons");
      for (ToolButton toolButton : buttons) {
        panel.getHeader().addTool(toolButton);
        // space out the tool buttons for Triton theme
        if (Theme.TRITON.isActive()) {
          toolButton.getElement().setPadding(new Padding(0, 0, 0, 4));
        }
      }

      HTML text = new HTML(
          "Tool Buttons are predefined icons. Check out ToolButton.class in the the source tab for a full list of the available tool buttons.");
      text.getElement().getStyle().setOverflowY(Style.Overflow.AUTO);
      text.getElement().getStyle().setFontSize(13, Style.Unit.PX);
      panel.add(text, new MarginData(20));
    }

    return panel;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinHeight(MIN_HEIGHT).setMinWidth(MIN_WIDTH).doStandalone();
  }

}
