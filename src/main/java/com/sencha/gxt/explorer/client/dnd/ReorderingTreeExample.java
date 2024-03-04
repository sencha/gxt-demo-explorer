/**
 * Sencha GXT 1.1.0-SNAPSHOT - Sencha for GWT
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
package com.sencha.gxt.explorer.client.dnd;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.core.client.gestures.ScrollGestureRecognizer;
import com.sencha.gxt.core.client.gestures.ScrollGestureRecognizer.ScrollDirection;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.TreeStore.TreeNode;
import com.sencha.gxt.dnd.core.client.DND.Feedback;
import com.sencha.gxt.dnd.core.client.DndDragStartEvent;
import com.sencha.gxt.dnd.core.client.DndDragStartEvent.DndDragStartHandler;
import com.sencha.gxt.dnd.core.client.TreeDragSource;
import com.sencha.gxt.dnd.core.client.TreeDropTarget;
import com.sencha.gxt.examples.resources.client.images.ExampleImages;
import com.sencha.gxt.examples.resources.client.model.BaseDtoProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.explorer.client.model.ExampleModel;
import com.sencha.gxt.explorer.client.model.NamedModel;
import com.sencha.gxt.explorer.client.model.NamedModel.NamedModelProperties;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.tree.Tree;

@Detail(
    name = "Reordering Tree",
    category = "Drag & Drop",
    icon = "reorderingtree",
    classes = {
        BaseDtoProperties.class,
        ExampleModel.class
    },
    minHeight = ReorderingTreeExample.MIN_HEIGHT,
    minWidth = ReorderingTreeExample.MIN_WIDTH
)
public class ReorderingTreeExample implements EntryPoint, IsWidget {

  protected static final int MIN_HEIGHT = 240;
  protected static final int MIN_WIDTH = 320;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      ExampleModel examples = GWT.create(ExampleModel.class);
      NamedModelProperties props = GWT.create(NamedModelProperties.class);

      final TreeStore<NamedModel> sourceStore = new TreeStore<NamedModel>(NamedModel.KEY);
      sourceStore.addSubTree(0, examples.getModels());

      final Tree<NamedModel, String> sourceTree = new Tree<NamedModel, String>(sourceStore, props.name());
      sourceTree.getStyle().setLeafIcon(ExampleImages.INSTANCE.text());
      sourceTree.getElement().getStyle().setBackgroundColor("white");
      if (GXT.isTouch() && !GXT.isSafari()) {
        sourceTree.addGestureRecognizer(new ScrollGestureRecognizer(sourceTree.getElement(), ScrollDirection.BOTH));
      }

      TreeDragSource<NamedModel> source = new TreeDragSource<NamedModel>(sourceTree);
      source.addDragStartHandler(new DndDragStartHandler() {
        @Override
        public void onDragStart(DndDragStartEvent event) {
          @SuppressWarnings("unchecked")
          List<TreeNode<?>> draggingSelection = (List<TreeNode<?>>) event.getData();
          NamedModel firstItemInStore = sourceStore.getChild(0);

          if (draggingSelection != null) {
            NamedModel m = null;
            for (TreeNode<?> node : draggingSelection) {
              m = (NamedModel) node.getData();

              if (m.equals(firstItemInStore)) {
                event.setCancelled(true);
                event.getStatusProxy().setStatus(false);
                Info.display("Drag Prevented",
                    "See the source code for an example of how to cancel a drag operation under program control.");
                return;
              }
            }
          }
        }
      });

      TreeDropTarget<NamedModel> target = new TreeDropTarget<NamedModel>(sourceTree);
      target.setAllowSelfAsSource(true);
      target.setFeedback(Feedback.BOTH);

      panel = new ContentPanel();
      panel.setHeading("Reordering Tree");
      panel.setWidget(sourceTree);
    }

    return panel;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
