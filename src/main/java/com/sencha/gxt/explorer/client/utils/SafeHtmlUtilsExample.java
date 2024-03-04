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
package com.sencha.gxt.explorer.client.utils;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.shared.ExpandedHtmlSanitizer;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

@Detail(
  name = "Safe HTML Utils",
  category = "Utils",
  icon = "animation",
  minHeight = SafeHtmlUtilsExample.MIN_HEIGHT,
  minWidth = SafeHtmlUtilsExample.MIN_WIDTH)
public class SafeHtmlUtilsExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 400;
  protected static final int MIN_WIDTH = 400;

  private ContentPanel panel;

  /**
   * @see http://docs.sencha.com/gxt/latest/guides/ui/SafeHtml.html
   */
  @Override
  public Widget asWidget() {
    if (panel == null) {
      HTML safeHtmlUtils = createSafeHtmlUtils();
      
      HTML safeHtmlBuilder = createSafeHtmlBuilder();
      
      HTML simpleHtmlSanitizer = createSimpleHtmlSanitizer();
      
      HTML exapndedHtmlSanitizer = createExpandedHtmlSanitizer();
      
      HTML xtemplateExample = createSafeHtmlFromTemplate();

      VerticalLayoutContainer vlc = new VerticalLayoutContainer();
      vlc.add(safeHtmlUtils, new VerticalLayoutData(1, -1, new Margins(20)));
      vlc.add(safeHtmlBuilder, new VerticalLayoutData(1, -1, new Margins(20)));
      vlc.add(simpleHtmlSanitizer, new VerticalLayoutData(1, -1, new Margins(20)));
      vlc.add(exapndedHtmlSanitizer, new VerticalLayoutData(1, -1, new Margins(20)));
      vlc.add(xtemplateExample, new VerticalLayoutData(1, -1, new Margins(20)));

      panel = new ContentPanel();
      panel.setHeading("Safe HTML Utils");
      panel.add(vlc, new MarginData(20));
    }

    return panel;
  }
  
  private HTML createExpandedHtmlSanitizer() {
    // allowed in sanitizer: 
    // "b", "big", "del", "em", "i", "ins", "mark", "s", "small", "strike", "strong", "sub", "sup", "u",
    // "br", "div", "center", "hr", "p", "span",
    // "h1", "h2", "h3", "h4", "h5", "h6",
    // "dd", "dir", "dl", "dt", "li", "ol", "ul",
    // "address", "article", "aside", "blockquote", "cite", "code", "details", "dfn", "footer", "header", "kbd",
    // "main", "pre", "q", "samp", "section", "summary", "tt",
    // "table", "tbody", "td", "tfoot", "th", "thead", "tr",
    // "bdi", "rp", "rt", "ruby", "wbr"
    String s = "<strike>Expanded html santizer example.</strike><script>alert('script is escaped?')</script>";
    SafeHtml safeHtml = ExpandedHtmlSanitizer.sanitizeHtml(s);
    HTML html = new HTML(safeHtml); 
    return html;
  }

  private HTML createSimpleHtmlSanitizer() {
    // allowed in sanitizer: "b", "em", "i", "h1", "h2", "h3", "h4", "h5", "h6", "hr", "ul", "ol", "li"
    String s = "<em>Simple html santizer example.</em><script>alert('script is escaped?')</script>";
    SafeHtml safeHtml = SimpleHtmlSanitizer.sanitizeHtml(s);
    HTML html = new HTML(safeHtml); 
    return html;
  }

  private HTML createSafeHtmlBuilder() {
    SafeHtmlBuilder sb = new SafeHtmlBuilder();
    sb.appendHtmlConstant("<font style='color: purple;'>");
    sb.appendEscaped("Safe html builder example");
    sb.appendHtmlConstant("</font>");
    
    SafeHtml safeHtml = sb.toSafeHtml();
    HTML html = new HTML(safeHtml); 
    return html;
  }

  private HTML createSafeHtmlUtils() {
    String s = "<font style='color: brown;'>Safe html utils example</font>";
    SafeHtml safeHtml = SafeHtmlUtils.fromTrustedString(s);
    HTML html = new HTML(safeHtml);
    return html;
  }
  
  private HTML createSafeHtmlFromTemplate() {
    SampleXTemplates tpl = GWT.create(SampleXTemplates.class);
    SafeHtml safeHtml = tpl.renderTemplate("with variable");
    HTML html = new HTML(safeHtml);
    return html;
  }
  
  public interface SampleXTemplates extends XTemplates {
    // Annotated HTML source with {variable} property
    @XTemplate("<div>XTemplate example, {variable}!</div>")
    SafeHtml renderTemplate(String variable);
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinHeight(MIN_HEIGHT).setMinWidth(MIN_WIDTH).doStandalone();
  }

}
