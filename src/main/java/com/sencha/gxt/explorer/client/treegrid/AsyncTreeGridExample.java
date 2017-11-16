package com.sencha.gxt.explorer.client.treegrid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.loader.ChildTreeStoreBinding;
import com.sencha.gxt.data.shared.loader.TreeLoader;
import com.sencha.gxt.examples.resources.client.FileService;
import com.sencha.gxt.examples.resources.client.FileServiceAsync;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.examples.resources.client.images.ExampleImages;
import com.sencha.gxt.examples.resources.client.model.FileModel;
import com.sencha.gxt.examples.resources.client.model.FolderModel;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

@Detail(
    name = "Async Tree Grid",
    category = "Tree Grid",
    icon = "asynctreegrid",
    classes = { FileModel.class, Utils.class },
    maxHeight = AsyncTreeGridExample.MAX_HEIGHT,
    maxWidth = AsyncTreeGridExample.MAX_WIDTH,
    minHeight = AsyncTreeGridExample.MIN_HEIGHT,
    minWidth = AsyncTreeGridExample.MIN_WIDTH
)
public class AsyncTreeGridExample implements IsWidget, EntryPoint {

  interface TreeBundle extends ClientBundle {
    @Source("plus.gif")
    ImageResource plus();

    @Source("minus.gif")
    ImageResource minus();

    @Source("folder.gif")
    ImageResource folder();

    @Source("folderOpen.gif")
    ImageResource folderOpen();
  }

  public interface FileModelProperties extends PropertyAccess<FileModel> {
    @Path("id")
    ModelKeyProvider<FileModel> key();

    ValueProvider<FileModel, String> name();

    ValueProvider<FileModel, String> path();

    ValueProvider<FileModel, Date> lastModified();

    ValueProvider<FileModel, Long> size();
  }

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 480;

  private ContentPanel cp;

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMaxHeight(MAX_HEIGHT)
        .setMaxWidth(MAX_WIDTH)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

  @Override
  public Widget asWidget() {
    if (cp == null) {
      TreeBundle bundle = GWT.create(TreeBundle.class);

      final FileServiceAsync service = GWT.create(FileService.class);

      RpcProxy<FileModel, List<FileModel>> proxy = new RpcProxy<FileModel, List<FileModel>>() {
        @Override
        public void load(FileModel loadConfig, AsyncCallback<List<FileModel>> callback) {
          service.getFolderChildren(loadConfig, callback);
        }
      };

      final TreeLoader<FileModel> loader = new TreeLoader<FileModel>(proxy) {
        @Override
        public boolean hasChildren(FileModel parent) {
          return parent instanceof FolderModel;
        }
      };

      FileModelProperties props = GWT.create(FileModelProperties.class);

      TreeStore<FileModel> store = new TreeStore<FileModel>(props.key());
      loader.addLoadHandler(new ChildTreeStoreBinding<FileModel>(store));

      ColumnConfig<FileModel, String> cc1 = new ColumnConfig<FileModel, String>(props.name(), 100, "Name");

      ColumnConfig<FileModel, Date> cc2 = new ColumnConfig<FileModel, Date>(props.lastModified(), 20, "Date");
      cc2.setCell(new DateCell(DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM)));

      ColumnConfig<FileModel, Long> cc3 = new ColumnConfig<FileModel, Long>(props.size(), 20, "Size");

      List<ColumnConfig<FileModel, ?>> l = new ArrayList<ColumnConfig<FileModel, ?>>();
      l.add(cc1);
      l.add(cc2);
      l.add(cc3);

      ColumnModel<FileModel> cm = new ColumnModel<FileModel>(l);

      cp = new ContentPanel() {
        @Override
        protected void onAfterFirstAttach() {
          super.onAfterFirstAttach();
          loader.load();
        }
      };
      cp.setHeading("Async Tree Grid");
      cp.setButtonAlign(BoxLayoutPack.CENTER);

      TreeGrid<FileModel> tree = new TreeGrid<FileModel>(store, cm, cc1);
      tree.setTreeLoader(loader);
      tree.getView().setTrackMouseOver(false);
      tree.getView().setAutoExpandColumn(cc1);
      tree.getView().setForceFit(true);
      tree.getStyle().setJointCloseIcon(bundle.plus());
      tree.getStyle().setJointOpenIcon(bundle.minus());
      tree.getStyle().setNodeCloseIcon(bundle.folder());
      tree.getStyle().setNodeOpenIcon(bundle.folderOpen());
      tree.getStyle().setLeafIcon(ExampleImages.INSTANCE.java());
      cp.setWidget(tree);
    }

    return cp;
  }

}
