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
package com.sencha.gxt.explorer.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.examples.resources.client.model.Kid;
import com.sencha.gxt.examples.resources.client.model.Person;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
    name = "Templates",
    category = "Templates & Lists",
    icon = "templates",
    minWidth = 210, minHeight = 420,
    files = "template.html",
    classes = {
        Kid.class,
        Person.class
    }
)
public class TemplateExample implements IsWidget, EntryPoint {

  public interface DataRenderer extends XTemplates {
    @XTemplate("<p>Name: {data.name}</p><p>Company: {data.company}</p><p>Location: {data.location}</p>")
    public SafeHtml render(Person data);

    @XTemplate(source = "template.html")
    public SafeHtml renderTemplate(Person data);
  }

  private DataRenderer renderer = GWT.create(DataRenderer.class);
  private VerticalLayoutContainer widget;
  private VerticalLayoutContainer rowLayoutContainer;
  private VerticalLayoutContainer xRowLayoutContainer;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      List<Kid> kids = new ArrayList<Kid>();
      kids.add(new Kid("Noah", 4, new DateWrapper(2011, 1, 1).asDate()));
      kids.add(new Kid("Emma", 2, new DateWrapper(2013, 1, 1).asDate()));
      kids.add(new Kid("Liam", 1, new DateWrapper(2014, 1, 1).asDate()));


      final Person person = new Person("John Doe", "ACME Widget Co.", "Widgets", "Anytown, USA", 43460d);
      person.setKids(kids);

      TextButton apply1 = new TextButton("Apply Template");
      apply1.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          final HTML text = new HTML(renderer.render(person));
          text.addStyleName("pad-text");
          text.setLayoutData(new VerticalLayoutData(1, -1));

          rowLayoutContainer.remove(1);
          rowLayoutContainer.add(text);
          rowLayoutContainer.forceLayout();
        }
      });

      TextButton apply2 = new TextButton("Apply Template");
      apply2.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          final HTML text = new HTML(renderer.renderTemplate(person));
          text.addStyleName("pad-text");
          text.setLayoutData(new VerticalLayoutData(1, -1));

          xRowLayoutContainer.remove(1);
          xRowLayoutContainer.add(text);
          xRowLayoutContainer.forceLayout();
        }
      });

      ToolBar toolbar1 = new ToolBar();
      toolbar1.add(apply1);
      toolbar1.setLayoutData(new VerticalLayoutData(1, -1));

      ToolBar toolbar2 = new ToolBar();
      toolbar2.add(apply2);
      toolbar2.setLayoutData(new VerticalLayoutData(1, -1));

      rowLayoutContainer = new VerticalLayoutContainer();
      rowLayoutContainer.add(toolbar1);
      rowLayoutContainer.add(new HTML());

      xRowLayoutContainer = new VerticalLayoutContainer();
      xRowLayoutContainer.add(toolbar2);
      xRowLayoutContainer.add(new HTML());

      ContentPanel panel = new ContentPanel();
      panel.add(rowLayoutContainer);
      panel.setHeading("Templates — Basic");

      ContentPanel xpanel = new ContentPanel();
      xpanel.add(xRowLayoutContainer);
      xpanel.setHeading("Templates — Advanced");

      widget = new VerticalLayoutContainer();
      widget.add(panel, new VerticalLayoutData(1, 0.4, new Margins(0, 0, 10, 0)));
      widget.add(xpanel, new VerticalLayoutData(1, 0.6, new Margins(10, 0, 0, 0)));
    }

    return widget;
  }

  @Override
  public void onModuleLoad() {
    RootPanel.get().add(asWidget());
  }

}
