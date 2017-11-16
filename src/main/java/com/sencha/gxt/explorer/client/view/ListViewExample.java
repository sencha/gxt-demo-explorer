package com.sencha.gxt.explorer.client.view;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.XTemplates.Formatter;
import com.sencha.gxt.core.client.XTemplates.FormatterFactories;
import com.sencha.gxt.core.client.XTemplates.FormatterFactory;
import com.sencha.gxt.core.client.resources.CommonStyles;
import com.sencha.gxt.core.client.util.Format;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.data.shared.loader.ListStoreBinding;
import com.sencha.gxt.data.shared.loader.Loader;
import com.sencha.gxt.examples.resources.client.ExampleService;
import com.sencha.gxt.examples.resources.client.ExampleServiceAsync;
import com.sencha.gxt.examples.resources.client.model.Photo;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.theme.base.client.listview.ListViewCustomAppearance;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.ListViewSelectionModel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
    name = "Simple List View",
    category = "Templates & Lists",
    icon = "listview",
    files = {
        "ListViewExample.html",
        "ListViewExample.gss",
        "ListViewExampleCourtesy.html"
    },
    classes = Photo.class,
    minHeight = ListViewExample.MIN_HEIGHT,
    minWidth = ListViewExample.MIN_WIDTH
)
public class ListViewExample implements IsWidget, EntryPoint {

  @FormatterFactories(@FormatterFactory(factory = ShortenFactory.class, name = "shorten"))
  interface Renderer extends XTemplates {
    @XTemplate(source = "ListViewExample.html")
    public SafeHtml renderItem(Photo photo, Style style);

    @XTemplate(source = "ListViewExampleCourtesy.html")
    public SafeHtml renderCourtesy(String courtesy, Style style);
  }

  interface Resources extends ClientBundle {
    @Source("ListViewExample.gss")
    Style css();
  }

  static class Shorten implements Formatter<String> {
    private int length;

    public Shorten(int length) {
      this.length = length;
    }

    @Override
    public String format(String data) {
      return Format.ellipse(data, length);
    }
  }

  static class ShortenFactory {
    public static Shorten getFormat(int length) {
      return new Shorten(length);
    }
  }

  interface Style extends CssResource {
    String courtesy();

    String detail();

    String detailInfo();

    String over();

    String select();

    String thumb();

    String thumbWrap();
  }

  protected static final int MIN_HEIGHT = 410;
  protected static final int MIN_WIDTH = 255;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final ExampleServiceAsync rpcService = GWT.create(ExampleService.class);

      RpcProxy<Object, List<Photo>> proxy = new RpcProxy<Object, List<Photo>>() {
        @Override
        public void load(Object loadConfig, AsyncCallback<List<Photo>> callback) {
          rpcService.getPhotos(callback);
        }
      };

      Loader<Object, List<Photo>> loader = new Loader<Object, List<Photo>>(proxy);

      ModelKeyProvider<Photo> keyProvider = new ModelKeyProvider<Photo>() {
        @Override
        public String getKey(Photo item) {
          return item.getName();
        }
      };

      ListStore<Photo> store = new ListStore<Photo>(keyProvider);
      
      loader.addLoadHandler(new ListStoreBinding<Object, Photo, List<Photo>>(store));
      loader.load();

      final Resources resources = GWT.create(Resources.class);
      resources.css().ensureInjected();

      final Style style = resources.css();

      final Renderer renderer = GWT.create(Renderer.class);

      ListViewCustomAppearance<Photo> appearance = new ListViewCustomAppearance<Photo>("." + style.thumbWrap(),
          style.over(), style.select()) {
        @Override
        public void renderEnd(SafeHtmlBuilder builder) {
          String markup = new StringBuilder("<div class=\"").append(CommonStyles.get().clear()).append("\"></div>").toString();
          builder.appendHtmlConstant(markup);
        }

        @Override
        public void renderItem(SafeHtmlBuilder builder, SafeHtml content) {
          builder.appendHtmlConstant("<div class='" + style.thumbWrap() + "' style='border: 1px solid white'>");
          builder.append(content);
          builder.appendHtmlConstant("</div>");
        }
      };

      ListView<Photo, Photo> listView = new ListView<Photo, Photo>(store, new IdentityValueProvider<Photo>() {
        @Override
        public void setValue(Photo object, Photo value) {
        }
      }, appearance);
      listView.setCell(new SimpleSafeHtmlCell<Photo>(new AbstractSafeHtmlRenderer<Photo>() {
        @Override
        public SafeHtml render(Photo object) {
          return renderer.renderItem(object, style);
        }
      }));
      listView.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Photo>() {
        @Override
        public void onSelectionChanged(SelectionChangedEvent<Photo> event) {
          panel.setHeading("Simple List View (" + event.getSelection().size() + " items selected)");
        }
      });
      listView.setBorders(false);

      final ListViewSelectionModel<Photo> selectionModel = listView.getSelectionModel();

      SimpleComboBox<String> comboType = new SimpleComboBox<String>(new StringLabelProvider<String>());
      comboType.setTriggerAction(TriggerAction.ALL);
      comboType.setEditable(false);
      comboType.setWidth(100);
      comboType.add("Multi");
      comboType.add("Simple");
      comboType.setValue("Multi");
      comboType.addSelectionHandler(new SelectionHandler<String>() {
        @Override
        public void onSelection(SelectionEvent<String> event) {
          boolean simple = event.getSelectedItem().equals("Simple");
          selectionModel.deselectAll();
          selectionModel.setSelectionMode(simple ? SelectionMode.SIMPLE : SelectionMode.MULTI);
        }
      });

      ToolBar toolBar = new ToolBar();
      toolBar.add(new LabelToolItem("Selection Mode: "));
      toolBar.add(comboType);

      HTML courtesyLabel = new HTML(renderer.renderCourtesy("Images provided courtesy of NASA", style));

      VerticalLayoutContainer vlc = new VerticalLayoutContainer();
      vlc.add(toolBar, new VerticalLayoutData(1, -1));
      vlc.add(listView, new VerticalLayoutData(1, 1));
      vlc.add(courtesyLabel, new VerticalLayoutData(1, -1));

      panel = new ContentPanel();
      panel.setId("images-view2");
      panel.setHeading("Simple List View (0 items selected)");
      panel.add(vlc);
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
