package com.sencha.gxt.explorer.client.app.ui;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Frame;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.IconProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.examples.resources.client.images.ExampleImages;
import com.sencha.gxt.explorer.client.app.ui.Page.SourceProperties;
import com.sencha.gxt.explorer.client.model.Example;
import com.sencha.gxt.explorer.client.model.Source;
import com.sencha.gxt.explorer.client.model.Source.FileType;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.tree.Tree;

public class SourceContainer extends SimpleContainer {

  private final Example example;

  public SourceContainer(Example example) {
    super();
    this.example = example;
  }

  @Override
  protected void onShow() {
    super.onShow();

    // lazy initialize the subcontainer only when shown
    if (getWidgetCount() == 0) {
      final ContentPanel sourcePanel = new ContentPanel();
      sourcePanel.setBodyStyleName("explorer-example-code");

      if (GXT.isTouch()) {
        sourcePanel.getElement().getStyle().setOverflow(Overflow.SCROLL);
      } else {
        sourcePanel.getElement().getStyle().setOverflow(Overflow.VISIBLE);
      }

      final Frame f = new Frame();

      MarginData centerData = new MarginData();
      centerData.setMargins(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? new Margins(5) : Theme.NEPTUNE.isActive() ? new Margins(0, 0, 0, 8) : new Margins(10));
      sourcePanel.setHeading("Source Code");
      sourcePanel.add(f);

      ContentPanel west = new ContentPanel();
      west.addStyleName("explorer-example-files");

      BorderLayoutData westData = new BorderLayoutData(300);
      westData.setMargins(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? new Margins(5, 0, 5, 5) : Theme.NEPTUNE.isActive() ? new Margins(0) : new Margins(10, 0, 10, 0));
      westData.setCollapseHeaderVisible(true);
      westData.setCollapsible(true);
      west.setHeading("Select File");

      SourceProperties sourceProperties = GWT.create(SourceProperties.class);

      final TreeStore<Source> sources = new TreeStore<Source>(sourceProperties.key());
      sources.addSubTree(0, example.getSources());

      Tree<Source, String> tree = new Tree<Source, String>(sources, sourceProperties.nameLabel()) {
        @Override
        protected void onAfterFirstAttach() {
          super.onAfterFirstAttach();
          Source item = example.getSources().get(0).getChildren().get(0);
          getSelectionModel().select(item, false);
        }
      };

      tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      tree.getSelectionModel().addBeforeSelectionHandler(new BeforeSelectionHandler<Source>() {
        @Override
        public void onBeforeSelection(BeforeSelectionEvent<Source> event) {
          Source m = event.getItem();
          if (m.getType() == FileType.FOLDER) {
            event.cancel();
          }
        }
      });

      tree.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Source>() {
        @Override
        public void onSelectionChanged(SelectionChangedEvent<Source> event) {
          List<Source> sels = event.getSelection();
          if (sels.size() > 0) {
            Source m = sels.get(0);
            if (m.getType() != FileType.FOLDER) {
              sourcePanel.setHeading(m.getName());
              f.setUrl(m.getUrl());
            }
          }
        }
      });

      tree.setIconProvider(new IconProvider<Source>() {
        @Override
        public ImageResource getIcon(Source model) {
          switch (model.getType()) {
            case CSS:
              return ExampleImages.INSTANCE.css();
            case XML:
              return ExampleImages.INSTANCE.xml();
            case JSON:
              return ExampleImages.INSTANCE.json();
            case FOLDER:
              return ExampleImages.INSTANCE.folder();
            case HTML:
              return ExampleImages.INSTANCE.html();
            case JAVA:
            default:
              return ExampleImages.INSTANCE.java();
          }
        }
      });
      tree.setAutoExpand(true);
      west.add(tree);

      BorderLayoutContainer subcontainer = new BorderLayoutContainer();
      subcontainer.addStyleName("explorer-example-source");
      subcontainer.setWestWidget(west, westData);
      subcontainer.setCenterWidget(sourcePanel, centerData);

      add(subcontainer);
    }
  }

}
