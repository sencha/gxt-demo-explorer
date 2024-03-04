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
package com.sencha.gxt.explorer.client.app.ui;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.resources.CommonStyles;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.explorer.client.ExplorerApp;
import com.sencha.gxt.explorer.client.app.place.ExamplePlace;
import com.sencha.gxt.explorer.client.model.Example;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.explorer.client.model.ExampleModel;
import com.sencha.gxt.explorer.client.model.NamedModel;
import com.sencha.gxt.theme.base.client.listview.ListViewCustomAppearance;
import com.sencha.gxt.widget.core.client.ListView;

@Detail(
    name = ExplorerApp.OVERVIEW,
    category = ExplorerApp.OVERVIEW_CATEGORY,
    icon = "overview",
    classes = ExampleContainer.class,
    preferredHeight = OverviewExample.PREFERRED_HEIGHT,
    preferredMargin = OverviewExample.PREFERRED_MARGIN,
    preferredWidth = OverviewExample.PREFERRED_WIDTH
)
public class OverviewExample implements IsWidget, EntryPoint {

  public interface Renderer extends XTemplates {
    @XTemplate(source = "template.html")
    public SafeHtml renderItem(Example items);
  }

  protected static final int PREFERRED_HEIGHT = 1;
  protected static final int PREFERRED_WIDTH = 1;
  protected static final int PREFERRED_MARGIN = 0;

  private ListStore<Example> overviewStore = new ListStore<Example>(NamedModel.KEY);
  private ListView<Example, Example> overviewView;
  private PlaceController placeController;

  @Override
  public Widget asWidget() {
    if (overviewView == null) {
      final Renderer r = GWT.create(Renderer.class);

      ListViewCustomAppearance<Example> appearance = new ListViewCustomAppearance<Example>(".sample-box", "sample-over",
          "none") {
        @Override
        public void renderEnd(SafeHtmlBuilder builder) {
          String markup = new StringBuilder("<div class=\"").append(CommonStyles.get().clear()).append("\"></div>").toString();
          builder.appendHtmlConstant(markup);
        }

        @Override
        public void renderItem(SafeHtmlBuilder builder, SafeHtml content) {
          builder.appendHtmlConstant("<div class='sample-box'>");
          builder.append(content);
          builder.appendHtmlConstant("</div>");
        }
      };

      overviewView = new ListView<Example, Example>(overviewStore, new IdentityValueProvider<Example>() {
        @Override
        public void setValue(Example object, Example value) {
        }
      }, appearance);
      overviewView.setCell(new SimpleSafeHtmlCell<Example>(new AbstractSafeHtmlRenderer<Example>() {
        @Override
        public SafeHtml render(Example object) {
          return r.renderItem(object);
        }
      }));
      overviewView.addStyleName("overview-page");
      overviewView.setBorders(false);
      overviewView.getSelectionModel().addSelectionHandler(new SelectionHandler<Example>() {
        @Override
        public void onSelection(SelectionEvent<Example> event) {
          if (event.getSelectedItem() != null) {
            placeController.goTo(new ExamplePlace(event.getSelectedItem().getId()));
            overviewView.getSelectionModel().deselectAll();
          }
        }
      });
    }

    return overviewView;
  }

  public void loadData(ExampleModel exampleModel) {
    overviewStore.addAll(exampleModel.getExamplesAsList());
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setPreferredHeight(PREFERRED_HEIGHT)
        .setPreferredMargin(PREFERRED_MARGIN)
        .setPreferredWidth(PREFERRED_WIDTH)
        .doStandalone();
  }

  public void setPlaceController(PlaceController placeController) {
    this.placeController = placeController;
  }

}
