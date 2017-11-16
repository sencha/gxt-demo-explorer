package com.sencha.gxt.explorer.client.tree;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.examples.resources.client.model.BaseDto;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.event.BeforeExpandItemEvent;
import com.sencha.gxt.widget.core.client.event.BeforeExpandItemEvent.BeforeExpandItemHandler;
import com.sencha.gxt.widget.core.client.event.ExpandItemEvent;
import com.sencha.gxt.widget.core.client.event.ExpandItemEvent.ExpandItemHandler;
import com.sencha.gxt.widget.core.client.tree.Tree;
import com.sencha.gxt.widget.core.client.tree.Tree.TreeNode;

@Detail(
    name = "Fast Tree",
    category = "Tree",
    icon = "fasttree",
    classes = BaseDto.class,
    minHeight = FastTreeExample.MIN_HEIGHT,
    minWidth = FastTreeExample.MIN_WIDTH
)
public class FastTreeExample implements IsWidget, EntryPoint {

  class KeyProvider implements ModelKeyProvider<BaseDto> {
    @Override
    public String getKey(BaseDto item) {
      return item.getId().toString();
    }
  }

  protected static final int MIN_HEIGHT = 240;
  protected static final int MIN_WIDTH = 320;

  private int counter = 0;
  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final TreeStore<BaseDto> store = new TreeStore<BaseDto>(new KeyProvider());

      final Tree<BaseDto, String> tree = new Tree<BaseDto, String>(store, new ValueProvider<BaseDto, String>() {
        @Override
        public String getValue(BaseDto object) {
          return object.getName();
        }

        @Override
        public void setValue(BaseDto object, String value) {
        }

        @Override
        public String getPath() {
          return "name";
        }
      }) {
        protected boolean hasChildren(BaseDto model) {
          return true;
        }
      };
      tree.addBeforeExpandHandler(new BeforeExpandItemHandler<BaseDto>() {
        @Override
        public void onBeforeExpand(BeforeExpandItemEvent<BaseDto> event) {
          TreeNode<BaseDto> node = tree.findNode(event.getItem());
          if (store.getChildCount(node.getModel()) != 0) {
            return;
          }
          List<BaseDto> list = new ArrayList<BaseDto>();
          for (int i = 0; i < 500; i++) {
            BaseDto m = createModel("Tree Item " + i);
            list.add(m);
          }
          tree.getStore().add(event.getItem(), list);
        }
      });
      tree.addExpandHandler(new ExpandItemHandler<BaseDto>() {
        @Override
        public void onExpand(ExpandItemEvent<BaseDto> event) {
          panel.setHeading("FastTree - This tree is handling " + store.getAllItemsCount() + " children");
        }
      });
      
      BaseDto model = createModel("Fast Tree");
      store.add(model);

      panel = new ContentPanel();
      panel.setHeading("Fast Tree");
      panel.add(tree);
    }

    return panel;
  }

  private BaseDto createModel(String n) {
    return new BaseDto(counter++, n);
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
