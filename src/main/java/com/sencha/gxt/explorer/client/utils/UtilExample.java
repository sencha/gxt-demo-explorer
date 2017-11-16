package com.sencha.gxt.explorer.client.utils;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Util;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

@Detail(
  name = "Util Utils",
  category = "Utils",
  icon = "animation",
  minHeight = UtilExample.MIN_HEIGHT,
  minWidth = UtilExample.MIN_WIDTH)
public class UtilExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 400;
  protected static final int MIN_WIDTH = 400;

  private ContentPanel panel;

  /**
   * http://docs.sencha.com/gxt/latest/javadoc/
   */
  @Override
  public Widget asWidget() {
    if (panel == null) {
      HTML equality = createEquality();
      
      HTML isint = createIsInt();
      
      HTML isempty = createIsEmpty();

      VerticalLayoutContainer vlc = new VerticalLayoutContainer();
      vlc.add(equality, new VerticalLayoutData(1, -1, new Margins(20)));
      vlc.add(isint, new VerticalLayoutData(1, -1, new Margins(20)));
      vlc.add(isempty, new VerticalLayoutData(1, -1, new Margins(20)));

      panel = new ContentPanel();
      panel.setHeading("Util Utils");
      panel.add(vlc, new MarginData(20));
    }

    return panel;
  }

  private HTML createIsInt() {
    String s = "Util.isInteger(\"101\"): " + Util.isInteger("101");
    SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(s);
    HTML html = new HTML(safeHtml);
    return html;
  }

  private HTML createEquality() {
    String a = "A";
    String b = null;
    
    String s = "Util.equalWithNull: " + Util.equalWithNull(a, b);
    SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(s);
    HTML html = new HTML(safeHtml);
    return html;
  }
  
  private HTML createIsEmpty() {
    String s = "Util.isEmptyString(\"\"): " + Util.isEmptyString("");
    SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(s);
    HTML html = new HTML(safeHtml);
    return html;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinHeight(MIN_HEIGHT).setMinWidth(MIN_WIDTH).doStandalone();
  }

}
