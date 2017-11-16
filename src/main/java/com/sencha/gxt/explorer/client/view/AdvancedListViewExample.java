package com.sencha.gxt.explorer.client.view;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
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
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Rectangle;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
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
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.StoreFilterField;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
    name = "Advanced List View",
    category = "Templates & Lists",
    icon = "advancedlistview",
    classes = Photo.class,
    files = {"ListViewExample.gss"},
    minHeight = AdvancedListViewExample.MIN_HEIGHT,
    minWidth = AdvancedListViewExample.MIN_WIDTH
)
public class AdvancedListViewExample implements IsWidget, EntryPoint {

  interface DetailRenderer extends XTemplates {
    @XTemplate(source = "AdvancedListViewDetail.html")
    SafeHtml render(Photo photo, Style style);
  }

  @FormatterFactories(@FormatterFactory(factory = ShortenFactory.class, name = "shorten"))
  interface Renderer extends XTemplates {
    @XTemplate(source = "ListViewExample.html")
    SafeHtml renderItem(Photo photo, Style style);
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

  protected static final int MIN_HEIGHT = 200;
  protected static final int MIN_WIDTH = 200;

  private Dialog chooser;
  private ContentPanel details;

  private Image image;
  private DetailRenderer renderer;
  private Style style;
  private SimpleComboBox<String> comboSort;
  private ListStore<Photo> store;
  private ListView<Photo, Photo> view;
  private VerticalLayoutContainer panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final Resources resources = GWT.create(Resources.class);
      resources.css().ensureInjected();

      style = resources.css();
      
      renderer = GWT.create(DetailRenderer.class);

      final ExampleServiceAsync rpcService = GWT.create(ExampleService.class);

      RpcProxy<Object, List<Photo>> proxy = new RpcProxy<Object, List<Photo>>() {
        @Override
        public void load(Object loadConfig, AsyncCallback<List<Photo>> callback) {
          rpcService.getPhotos(callback);
        }
      };

      ModelKeyProvider<Photo> keyProvider = new ModelKeyProvider<Photo>() {
        @Override
        public String getKey(Photo item) {
          return item.getName();
        }
      };

      store = new ListStore<Photo>(keyProvider);
      store.addSortInfo(new StoreSortInfo<Photo>(new Comparator<Photo>() {
        @Override
        public int compare(Photo o1, Photo o2) {
          String v = comboSort.getCurrentValue();
          if (v.equals("Name")) {
            return o1.getName().compareToIgnoreCase(o2.getName());
          } else if (v.equals("File Size")) {
            return o1.getSize() < o2.getSize() ? -1 : 1;
          } else {
            return o1.getDate().compareTo(o2.getDate());
          }
        }
      }, SortDir.ASC));

      Loader<Object, List<Photo>> loader = new Loader<Object, List<Photo>>(proxy);
      loader.addLoadHandler(new ListStoreBinding<Object, Photo, List<Photo>>(store));
      loader.load();

      StoreFilterField<Photo> filterField = new StoreFilterField<Photo>() {
        @Override
        protected boolean doSelect(Store<Photo> store, Photo parent, Photo item, String filter) {
          String name = item.getName().toLowerCase();
          if (name.indexOf(filter.toLowerCase()) != -1) {
            return true;
          }
          return false;
        }

        @Override
        protected void onFilter() {
          super.onFilter();
          view.getSelectionModel().select(0, false);
        }
      };
      filterField.setWidth(100);
      filterField.bind(store);

      ToolBar toolbar = new ToolBar();
      toolbar.setEnableOverflow(false);
      toolbar.add(new LabelToolItem("Filter:"));
      toolbar.add(filterField);
      toolbar.add(new SeparatorToolItem());
      toolbar.add(new LabelToolItem("Sort By:"));

      comboSort = new SimpleComboBox<String>(new StringLabelProvider<String>());
      comboSort.setTriggerAction(TriggerAction.ALL);
      comboSort.setEditable(false);
      comboSort.setForceSelection(true);
      comboSort.setWidth(120);
      comboSort.add("Name");
      comboSort.add("File Size");
      comboSort.add("Last Modified");
      comboSort.setValue("Name");
      comboSort.addSelectionHandler(new SelectionHandler<String>() {
        @Override
        public void onSelection(SelectionEvent<String> event) {
          store.applySort(false);
        }
      });
      toolbar.add(comboSort);

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

      view = new ListView<Photo, Photo>(store, new IdentityValueProvider<Photo>() {
        @Override
        public void setValue(Photo object, Photo value) {
        }
      }, appearance);
      view.setCell(new SimpleSafeHtmlCell<Photo>(new AbstractSafeHtmlRenderer<Photo>() {
        @Override
        public SafeHtml render(Photo object) {
          return renderer.renderItem(object, style);
        }
      }));
      view.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      view.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Photo>() {
        @Override
        public void onSelectionChanged(SelectionChangedEvent<Photo> event) {
          AdvancedListViewExample.this.onSelectionChange(event);
        }
      });
      view.setBorders(false);

      VerticalLayoutContainer main = new VerticalLayoutContainer();
      main.setBorders(true);
      main.add(toolbar, new VerticalLayoutData(1, -1));
      main.add(view, new VerticalLayoutData(1, 1));

      details = new ContentPanel();
      details.setHeaderVisible(false);
      details.setBodyBorder(true);
      details.getElement().getStyle().setBackgroundColor("white");

      BorderLayoutData eastData = new BorderLayoutData(250);
      eastData.setSplit(true);

      BorderLayoutData centerData = new BorderLayoutData();
      centerData.setMargins(new Margins(0, 5, 0, 0));

      BorderLayoutContainer con = new BorderLayoutContainer();
      con.setCenterWidget(main, centerData);
      con.setEastWidget(details, eastData);

      chooser = new Dialog();
      chooser.setPixelSize(640, 480);
      chooser.setResizable(false);
      chooser.setId("img-chooser-dlg");
      chooser.setHeading("Advanced List View");
      chooser.setModal(true);
      chooser.setBodyBorder(false);
      chooser.setPredefinedButtons(PredefinedButton.OK, PredefinedButton.CANCEL);
      chooser.setHideOnButtonClick(true);
      chooser.addDialogHideHandler(new DialogHideHandler() {
        @Override
        public void onDialogHide(DialogHideEvent event) {
          Photo photo = view.getSelectionModel().getSelectedItem();
          if (photo != null) {
            if (event.getHideButton() == PredefinedButton.OK) {
              image.setUrl(photo.getPath());
              image.setVisible(true);
              panel.forceLayout();
            }
          }
        }
      });
      chooser.add(con);

      TextButton buttonChoose = new TextButton("Choose");
      buttonChoose.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          // show the chooser
          chooser.show();

          // select the first item
          view.getSelectionModel().select(0, false);

          // constrain the chooser to the viewport (for small mobile screen sizes)
          Rectangle bounds = chooser.getElement().getBounds();
          Rectangle adjusted = chooser.getElement().adjustForConstraints(bounds);
          if (adjusted.getWidth() != bounds.getWidth() || adjusted.getHeight() != bounds.getHeight()) {
            chooser.setPixelSize(adjusted.getWidth(), adjusted.getHeight());
          }
        }
      });

      ButtonBar buttonBar = new ButtonBar();
      buttonBar.add(buttonChoose);

      image = new Image();
      image.setVisible(false);

      panel = new VerticalLayoutContainer();
      panel.add(buttonBar, new VerticalLayoutData(1, -1, new Margins(0, 0, 20, 0)));
      panel.add(image, new VerticalLayoutData(1, -1));
    }

    return panel;
  }

  private void onSelectionChange(SelectionChangedEvent<Photo> se) {
    if (se.getSelection().size() > 0) {
      details.getBody().setInnerSafeHtml(renderer.render(se.getSelection().get(0), style));
      chooser.getButton(PredefinedButton.OK).enable();
    } else {
      chooser.getButton(PredefinedButton.OK).disable();
      details.getBody().setInnerSafeHtml(SafeHtmlUtils.EMPTY_SAFE_HTML);
    }
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
