package com.sencha.gxt.explorer.client.dnd;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.core.client.gestures.ScrollGestureRecognizer;
import com.sencha.gxt.core.client.gestures.ScrollGestureRecognizer.ScrollDirection;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.dnd.core.client.DND.Feedback;
import com.sencha.gxt.dnd.core.client.TreeDragSource;
import com.sencha.gxt.dnd.core.client.TreeDropTarget;
import com.sencha.gxt.examples.resources.client.images.ExampleImages;
import com.sencha.gxt.examples.resources.client.model.BaseDtoProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Category;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.explorer.client.model.ExampleModel;
import com.sencha.gxt.explorer.client.model.NamedModel;
import com.sencha.gxt.explorer.client.model.NamedModel.NamedModelProperties;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.tree.Tree;

@Detail(
    name = "Tree to Tree",
    category = "Drag & Drop",
    icon = "treetotree",
    classes = {
        BaseDtoProperties.class,
        NamedModel.class,
        ExampleModel.class
    },
    minHeight = TreeToTreeExample.MIN_HEIGHT,
    minWidth = TreeToTreeExample.MIN_WIDTH
)
public class TreeToTreeExample implements EntryPoint, IsWidget {

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 480;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      ExampleModel examples = GWT.create(ExampleModel.class);

      NamedModelProperties properties = GWT.create(NamedModelProperties.class);

      TreeStore<NamedModel> sourceStore = new TreeStore<NamedModel>(NamedModel.KEY);
      TreeStore<NamedModel> targetStore = new TreeStore<NamedModel>(NamedModel.KEY);

      sourceStore.addSubTree(0, examples.getModels());
      targetStore.add(new Category("My Files"));

      Tree<NamedModel, String> sourceTree = new Tree<NamedModel, String>(sourceStore, properties.name());

      Tree<NamedModel, String> targetTree = new Tree<NamedModel, String>(targetStore, properties.name()) {
        protected boolean hasChildren(NamedModel model) {
          return (model instanceof Category) || super.hasChildren(model);
        }
      };

      if (GXT.isTouch() && !GXT.isSafari()) {
        sourceTree.addGestureRecognizer(new ScrollGestureRecognizer(sourceTree.getElement(), ScrollDirection.BOTH));
        targetTree.addGestureRecognizer(new ScrollGestureRecognizer(targetTree.getElement(), ScrollDirection.BOTH));
      }

      sourceTree.getStyle().setLeafIcon(ExampleImages.INSTANCE.text());
      targetTree.getStyle().setLeafIcon(ExampleImages.INSTANCE.text());

      new TreeDragSource<NamedModel>(sourceTree);
      new TreeDragSource<NamedModel>(targetTree);

      final TreeDropTarget<NamedModel> target = new TreeDropTarget<NamedModel>(targetTree);
      final TreeDropTarget<NamedModel> source = new TreeDropTarget<NamedModel>(sourceTree);

      target.setFeedback(Feedback.APPEND);
      source.setFeedback(Feedback.APPEND);

      SimpleComboBox<Feedback> typeCombo = new SimpleComboBox<Feedback>(new LabelProvider<Feedback>() {
        @Override
        public String getLabel(Feedback item) {
          return item.toString().substring(0, 1) + item.toString().substring(1).toLowerCase();
        }
      });
      typeCombo.setTriggerAction(TriggerAction.ALL);
      typeCombo.setEditable(false);
      typeCombo.add(Feedback.APPEND);
      typeCombo.add(Feedback.INSERT);
      typeCombo.add(Feedback.BOTH);
      typeCombo.setValue(Feedback.APPEND);
      typeCombo.addValueChangeHandler(new ValueChangeHandler<Feedback>() {
        @Override
        public void onValueChange(ValueChangeEvent<Feedback> event) {
          target.setFeedback(event.getValue());
          source.setFeedback(event.getValue());
        }
      });

      ToolBar toolbar = new ToolBar();
      toolbar.add(new LabelToolItem("Feedback: "));
      toolbar.add(typeCombo);

      HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();
      hlc.add(sourceTree, new HorizontalLayoutData(.5, 1));
      hlc.add(targetTree, new HorizontalLayoutData(.5, 1));

      VerticalLayoutContainer vlc = new VerticalLayoutContainer();
      vlc.add(toolbar, new VerticalLayoutData(1, -1));
      vlc.add(hlc, new VerticalLayoutData(1, 1));

      panel = new ContentPanel();
      panel.setHeading("Tree to Tree");
      panel.setWidget(vlc);
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
