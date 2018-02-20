package com.sencha.gxt.explorer.client.databinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.loader.ChildTreeStoreBinding;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.TreeLoader;
import com.sencha.gxt.examples.resources.client.images.ExampleImages;
import com.sencha.gxt.examples.resources.shared.ExampleRequestFactory;
import com.sencha.gxt.examples.resources.shared.FolderProxy;
import com.sencha.gxt.examples.resources.shared.FolderRequest;
import com.sencha.gxt.examples.resources.shared.MusicProxy;
import com.sencha.gxt.examples.resources.shared.MusicRequest;
import com.sencha.gxt.examples.resources.shared.NamedProxy;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.tree.Tree;

@Detail(
    name = "Request Factory Binding",
    icon = "requestfactorybinding",
    category = "Data Binding",
    classes = {
        NamedProxy.class,
        FolderProxy.class,
        MusicProxy.class,
        ExampleRequestFactory.class,
        MusicRequest.class,
        FolderRequest.class
    },
    minHeight = RequestFactoryBindingExample.MIN_HEIGHT,
    minWidth = RequestFactoryBindingExample.MIN_WIDTH
)
public class RequestFactoryBindingExample implements Editor<MusicProxy>, IsWidget, EntryPoint {

  interface Driver extends RequestFactoryEditorDriver<MusicProxy, RequestFactoryBindingExample> {
  }

  protected static final int MIN_HEIGHT = 240;
  protected static final int MIN_WIDTH = 640;

  private final ExampleRequestFactory rf = GWT.create(ExampleRequestFactory.class);

  interface NamedProxyProperties extends PropertyAccess<NamedProxy> {
    ModelKeyProvider<NamedProxy> stableId();

    ValueProvider<NamedProxy, String> name();
  }

  private NamedProxyProperties properties = GWT.create(NamedProxyProperties.class);

  // This is a custom data proxy, designed to serve as the interface between the
  // client's needs and the server's capabilities. If the server and client were
  // in complete agreement, it would be possible to write this as a
  // RequestFactoryProxy impl
  private final DataProxy<NamedProxy, List<NamedProxy>> proxy = new DataProxy<NamedProxy, List<NamedProxy>>() {
    @Override
    public void load(final NamedProxy loadConfig, final Callback<List<NamedProxy>, Throwable> callback) {
      Receiver<List<? extends NamedProxy>> receiver = new Receiver<List<? extends NamedProxy>>() {
        @Override
        public void onSuccess(List<? extends NamedProxy> response) {
          if (response.size() == 0) {
            // assuming that only folders OR music will be returned.
            return;
          }
          callback.onSuccess(new ArrayList<NamedProxy>(response));
        }
      };
      if (loadConfig == null) {
        rf.folder().getRootFolder().fire(new Receiver<FolderProxy>() {
          @Override
          public void onSuccess(FolderProxy response) {
            callback.onSuccess(Collections.<NamedProxy>singletonList(response));
          }
        });
      } else {
        FolderRequest req = rf.folder();
        req.getChildren().using((FolderProxy) loadConfig).to(receiver);
        req.getSubFolders().using((FolderProxy) loadConfig).to(receiver);
        req.fire();
      }
    }
  };

  private final TreeLoader<NamedProxy> loader = new TreeLoader<NamedProxy>(proxy) {
    @Override
    public boolean hasChildren(NamedProxy parent) {
      return parent instanceof FolderProxy;
    }
  };

  private final TreeStore<NamedProxy> treeStore = new TreeStore<NamedProxy>(properties.stableId());

  private Driver driver = GWT.create(Driver.class);

  /*
   * Fields (i.e. sub-editors) that the editor driver will bind to. If using UI binder, these will be created and
   * configured in xml instead of in code.
   */
  TextField name;
  TextField author;
  TextField genre;

  private TextButton save;
  private ContentPanel panel;

  public RequestFactoryBindingExample() {
    // Using a simple, very local, event bus, just for this simple example.
    // Typically this would be an application-wide event bus, so other parts of
    // the app can monitor changes made on the server

    rf.initialize(new SimpleEventBus());

    loader.addLoadHandler(new ChildTreeStoreBinding<NamedProxy>(treeStore));
  }

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final Tree<NamedProxy, String> tree = new Tree<NamedProxy, String>(treeStore, properties.name());
      tree.setLoader(loader);
      tree.getStyle().setLeafIcon(ExampleImages.INSTANCE.music());
      tree.getSelectionModel().addSelectionHandler(new SelectionHandler<NamedProxy>() {
        @Override
        public void onSelection(SelectionEvent<NamedProxy> event) {
          name.clearInvalid();
          author.clearInvalid();
          genre.clearInvalid();

          if (event.getSelectedItem() instanceof MusicProxy) {
            // When a Music object is selected, edit it
            // TODO disallow editing in cases where the last has not been saved?

            MusicProxy music = (MusicProxy) event.getSelectedItem();

            startEdit(music);
            name.setEnabled(true);
            author.setEnabled(true);
            
            // This is disabled because, it will only edit the attribute in the model
            // In order to use this, logic would have to be added to move it to a new parent in the tree
            genre.setEnabled(false);
            save.setEnabled(true);
          } else {
            name.setValue("");
            author.setValue("");
            genre.setValue("");
            name.setEnabled(false);
            author.setEnabled(false);
            genre.setEnabled(false);
            save.setEnabled(false);
          }
        }
      });

      name = new TextField();
      name.setEnabled(false);
      author = new TextField();
      author.setEnabled(false);
      genre = new TextField();
      genre.setEnabled(false);

      // Clicking this save button will check for errors and save the request
      save = new TextButton("Save");
      save.setEnabled(false);
      save.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          RequestContext req = driver.flush();
          if (driver.hasErrors()) {
            new MessageBox("Error", "Please correct the errors before saving.").show();
          } else if (req.isChanged()) {
            save.setEnabled(false);
            req.fire(new Receiver<Void>() {
              @Override
              public void onSuccess(Void response) {
                save.setEnabled(true);
              }

              @Override
              public void onFailure(ServerFailure error) {
                save.setEnabled(true);
              }
            });
          }
        }
      });

      CssFloatLayoutContainer fieldsContainer = new CssFloatLayoutContainer();
      fieldsContainer.add(new FieldLabel(name, "Name"), new CssFloatData(1, new Margins(0, 0, 5, 0)));
      fieldsContainer.add(new FieldLabel(author, "Author"), new CssFloatData(1, new Margins(0, 0, 5, 0)));
      fieldsContainer.add(new FieldLabel(genre, "Genre"), new CssFloatData(1, new Margins(0, 0, 5, 0)));
      fieldsContainer.add(new SimpleContainer(), new CssFloatData(1, new Margins(0, 0, 5, 0)));

      HorizontalLayoutContainer container = new HorizontalLayoutContainer();
      container.add(tree, new HorizontalLayoutData(.5, 1, new Margins(0, 10, 0, 0)));
      container.add(fieldsContainer, new HorizontalLayoutData(.5, 1));

      panel = new ContentPanel();
      panel.setHeading("Request Factory Binding");
      panel.add(container, new MarginData(10));
      panel.addButton(save);

      // Now that all of the sub-editors exist, bind the driver to them
      driver.initialize(rf, this);
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

  private void startEdit(MusicProxy model) {
    MusicRequest req = rf.music();
    req.persist().using(model).to(new Receiver<MusicProxy>() {
      @Override
      public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
        if (driver.setConstraintViolations(violations)) {
          super.onConstraintViolation(violations);
        }
      }

      @Override
      public void onSuccess(MusicProxy response) {
        treeStore.update(response);
        // Must start new edit after flush has been called
        // is this correct? Without this, request is locked and flush does
        // nothing.
        startEdit(response);
      }
    });

    FormPanelHelper.reset(panel);

    driver.edit(model, req);
  }

}
