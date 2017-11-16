package com.sencha.gxt.explorer.client.utils;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.dom.XDOM;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

@Detail(
  name = "XDOM Utils",
  category = "Utils",
  icon = "animation",
  minHeight = XDOMExample.MIN_HEIGHT,
  minWidth = XDOMExample.MIN_WIDTH)
public class XDOMExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 400;
  protected static final int MIN_WIDTH = 400;

  private ContentPanel panel;

  /**
   * There are more XDOM utility methods available...
   * @see http://docs.sencha.com/gxt/latest/javadoc/gxt-4.0.2/index.html
   */
  @Override
  public Widget asWidget() {
    if (panel == null) {
      HTML xdomViewportWidth = createViewportWidth();
      
      HTML xdomScrollBarWidth = createScrollBarWidth();

      HTML xdomActiveElement = createActiveElement();

      VerticalLayoutContainer vlc = new VerticalLayoutContainer();
      vlc.add(xdomViewportWidth, new VerticalLayoutData(1, -1, new Margins(20)));
      vlc.add(xdomScrollBarWidth, new VerticalLayoutData(1, -1, new Margins(20)));
      vlc.add(xdomActiveElement, new VerticalLayoutData(1, -1, new Margins(20)));

      panel = new ContentPanel();
      panel.setHeading("Safe HTML Utils");
      panel.add(vlc, new MarginData(20));
    }

    return panel;
  }

  private HTML createScrollBarWidth() {
    int width = XDOM.getScrollBarWidth();
    String s = "XDOM.getScrollBarWidth() width: " + width;
    SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(s);
    HTML html = new HTML(safeHtml);
    return html;
  }

  private HTML createViewportWidth() {
    int width = XDOM.getViewportWidth();
    int height = XDOM.getViewportHeight();

    String s = "Viewport width: " + width + " height: " + height;
    SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(s);
    HTML html = new HTML(safeHtml);
    return html;
  }

  private HTML createActiveElement() {
    XElement activeElement = XDOM.getActiveElement().cast();

    String s = "XDOM.getActiveElement() tag: " + activeElement.getTagName();
    SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(s);
    HTML html = new HTML(safeHtml);
    return html;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinHeight(MIN_HEIGHT).setMinWidth(MIN_WIDTH).doStandalone();
  }

}
