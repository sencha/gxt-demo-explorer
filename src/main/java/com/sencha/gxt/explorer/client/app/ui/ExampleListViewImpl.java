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
package com.sencha.gxt.explorer.client.app.ui;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.examples.resources.client.images.ExampleImages;
import com.sencha.gxt.explorer.client.ExplorerApp;
import com.sencha.gxt.explorer.client.app.place.ExamplePlace;
import com.sencha.gxt.explorer.client.model.Category;
import com.sencha.gxt.explorer.client.model.Example;
import com.sencha.gxt.explorer.client.model.ExampleModel;
import com.sencha.gxt.explorer.client.model.NamedModel;
import com.sencha.gxt.explorer.client.model.NamedModel.NamedModelProperties;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.TabPanel.TabPanelAppearance;
import com.sencha.gxt.widget.core.client.TabPanel.TabPanelBottomAppearance;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.StoreFilterField;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.tree.Tree;

public class ExampleListViewImpl implements ExampleListView {

  @Inject
  private ExampleModel model;
  private Presenter presenter;
  private TreeStore<NamedModel> treeStore;
  private ListStore<NamedModel> listStore;

  private VerticalLayoutContainer con;
  private TabPanel tabPanel;

  private Tree<NamedModel, String> tree;
  private ListView<NamedModel, String> list;

  @Inject
  public ExampleListViewImpl(ExampleModel model) {
    NamedModelProperties props = GWT.create(NamedModelProperties.class);

    treeStore = new TreeStore<NamedModel>(NamedModel.KEY);
    
    listStore = new ListStore<NamedModel>(NamedModel.KEY);
    listStore.addSortInfo(new StoreSortInfo<NamedModel>(new Comparator<NamedModel>() {
      @Override
      public int compare(NamedModel o1, NamedModel o2) {
        return o1.getName().compareTo(o2.getName());
      }
    }, SortDir.ASC));

    List<Category> cats = model.getCategories();
    for (int i = 0; i < cats.size(); i++) {
      Category c = cats.get(i);
      if (!ExplorerApp.OVERVIEW_CATEGORY.equalsIgnoreCase(c.getName())) {
        treeStore.add(c);
      }

      List<Example> examples = c.getExamples();
      for (int j = 0; j < examples.size(); j++) {
        if (!ExplorerApp.OVERVIEW.equalsIgnoreCase(examples.get(j).getName())) {
          treeStore.add(c, examples.get(j));
          listStore.add(examples.get(j));
        }
      }
    }

    SelectionChangedHandler<NamedModel> selectHandler = new SelectionChangedHandler<NamedModel>() {
      @Override
      public void onSelectionChanged(SelectionChangedEvent<NamedModel> event) {
        List<NamedModel> sels = event.getSelection();
        if (sels.size() > 0) {
          NamedModel m = sels.get(0);
          if (m instanceof Example) {
            Example ex = (Example) m;
            presenter.selectExample(ex);
          }
        }
      }
    };

    StoreFilterField<NamedModel> filter = new StoreFilterField<NamedModel>() {
      @Override
      protected boolean doSelect(Store<NamedModel> store, NamedModel parent, NamedModel item, String filter) {
        if (item instanceof Category) {
          return false;
        }
        String name = item.getName();
        name = name.toLowerCase();
        if (name.indexOf(filter.toLowerCase()) != -1) {
          return true;
        }
        return false;
      }
    };
    filter.bind(listStore);
    filter.bind(treeStore);
    filter.setEmptyText("Filter...");
    //filter.getElement().getStyle().setProperty("border", "1px solid green");

    list = new ListView<NamedModel, String>(listStore, props.name());
    list.setBorders(false);
    list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    list.getSelectionModel().addSelectionChangedHandler(selectHandler);

    tree = new Tree<NamedModel, String>(treeStore, props.name());
    tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    tree.getStyle().setLeafIcon(ExampleImages.INSTANCE.list());
    tree.getSelectionModel().addSelectionChangedHandler(selectHandler);

    tabPanel = new TabPanel(GWT.<TabPanelAppearance>create(TabPanelBottomAppearance.class));
    tabPanel.setBodyBorder(false);
    tabPanel.add(tree, "Tree");
    tabPanel.add(list, "List");
    //tabPanel.getElement().getStyle().setProperty("border", "1px solid red");

    con = new VerticalLayoutContainer();
    con.add(filter, new VerticalLayoutData(1, -1, new Margins(5)));
    con.add(tabPanel, new VerticalLayoutData(1, 1));
  }

  @Override
  public Widget asWidget() {
    return con;
  }

  @Override
  public void onPlaceChange(PlaceChangeEvent event) {
    Place place = event.getNewPlace();
    if (place instanceof ExamplePlace) {
      ExamplePlace ep = (ExamplePlace) place;
      showExample(model.findExample(ep.getExampleId()));
    }
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  public void showExample(Example e) {
    if (e != null && !ExplorerApp.OVERVIEW.equals(e.getName())) {
      NamedModel example = treeStore.findModel(e);
      tree.setExpanded(example, true, false);
      tree.scrollIntoView(e);
      tree.getSelectionModel().select(e, false);

      list.getSelectionModel().select(e, false);
    } else {
      tree.getSelectionModel().deselectAll();
      list.getSelectionModel().deselectAll();
    }
  }

}
