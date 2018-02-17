package com.sencha.gxt.explorer.client.grid;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.cell.core.client.form.CheckBoxCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.data.shared.Converter;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Plant;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.form.PropertyEditor;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.CellSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
  name = "Inline Editable Grid",
  category = "Grid",
  icon = "inlineeditablegrid",
  classes = { Plant.class, TestData.class },
  maxHeight = InlineEditingGridExample.MAX_HEIGHT,
  maxWidth = InlineEditingGridExample.MAX_WIDTH,
  minHeight = InlineEditingGridExample.MIN_HEIGHT,
  minWidth = InlineEditingGridExample.MIN_WIDTH)
public class InlineEditingGridExample implements EntryPoint, IsWidget {

  // Used to show the converter feature
  public enum Light {
    MOSTLYSHADY("Mostly Shady"), MOSTLYSUNNY("Mostly Sunny"), SHADE("Shade"), SUNNY("Sunny"), SUNORSHADE(
        "Sun or Shade");
    static Light parseString(String object) throws ParseException {
      if (Light.MOSTLYSUNNY.toString().equals(object)) {
        return Light.MOSTLYSUNNY;
      } else if (Light.SUNORSHADE.toString().equals(object)) {
        return Light.SUNORSHADE;
      } else if (Light.MOSTLYSHADY.toString().equals(object)) {
        return Light.MOSTLYSHADY;
      } else if (Light.SHADE.toString().equals(object)) {
        return Light.SHADE;
      } else if (Light.SUNNY.toString().equals(object)) {
        return Light.SUNNY;
      } else {
        throw new ParseException(object.toString() + " could not be parsed", 0);
      }
    }

    private String text;

    Light(String text) {
      this.text = text;
    }

    @Override
    public String toString() {
      return text;
    }
  }

  public interface PlaceProperties extends PropertyAccess<Plant> {
    ValueProvider<Plant, Date> available();

    @Path("id")
    ModelKeyProvider<Plant> key();

    ValueProvider<Plant, String> light();

    ValueProvider<Plant, String> name();

    ValueProvider<Plant, Boolean> indoor();

    ValueProvider<Plant, Double> price();
  }

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 480;

  private static final PlaceProperties properties = GWT.create(PlaceProperties.class);

  protected ContentPanel panel;
  protected Grid<Plant> grid;

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMaxHeight(MAX_HEIGHT).setMaxWidth(MAX_WIDTH).setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH).doStandalone();
  }

  @Override
  public Widget asWidget() {
    if (panel == null) {
      ColumnConfig<Plant, String> nameColumn = new ColumnConfig<Plant, String>(properties.name(), 220, "Name");
      ColumnConfig<Plant, String> lightColumn = new ColumnConfig<Plant, String>(properties.light(), 120, "Light");
      ColumnConfig<Plant, Date> dateColumn = new ColumnConfig<Plant, Date>(properties.available(), 95, "Date");
      ColumnConfig<Plant, Boolean> indoorColumn = new ColumnConfig<Plant, Boolean>(properties.indoor(), 65, "Indoor");
      ColumnConfig<Plant, Double> priceColumn = new ColumnConfig<Plant, Double>(properties.price(), 75, "Price");

      dateColumn.setCell(new DateCell(DateTimeFormat.getFormat("yyyy MMM dd")));
      
      // Option 1
      indoorColumn.setCell(new CheckBoxCell());
      
      // Another checkbox option 2 - use this instead of an editor
      // indoorColumn.setCell(new InputCheckboxCell() {
      // @Override
      // protected String getStyle(Context context, Boolean value) {
      // return "margin-top: 3px;";
      // }
      //
      // @Override
      // protected String getLabelHtml() {
      // return "";
      // }
      // });
      // indoorColumn.setCellPadding(true);
      // indoorColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

      // Another checkbox option 3 - Use a cell label renderer with an check box editor below
      // indoorColumn.setCell(new SimpleSafeHtmlCell<Boolean>(new AbstractSafeHtmlRenderer<Boolean>() {
      // @Override
      // public SafeHtml render(Boolean object) {
      // return SafeHtmlUtils.fromTrustedString(object ? "True" : "False");
      // }
      // }));

      priceColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
      priceColumn.setCell(new SimpleSafeHtmlCell<Double>(new AbstractSafeHtmlRenderer<Double>() {
        @Override
        public SafeHtml render(Double object) {
          return SafeHtmlUtils.fromString(NumberFormat.getCurrencyFormat().format(object));
        }
      }));

      List<ColumnConfig<Plant, ?>> columns = new ArrayList<ColumnConfig<Plant, ?>>();
      columns.add(nameColumn);
      columns.add(lightColumn);
      columns.add(priceColumn);
      columns.add(dateColumn);
      columns.add(indoorColumn);

      ColumnModel<Plant> columnModel = new ColumnModel<Plant>(columns);

      final ListStore<Plant> store = new ListStore<Plant>(properties.key());
      store.addAll(TestData.getPlants());

      grid = new Grid<Plant>(store, columnModel);
      grid.getView().setAutoExpandColumn(nameColumn);
      grid.setSelectionModel(new CellSelectionModel<Plant>());
      grid.getColumnModel().getColumn(0).setHideable(false);

      // EDITING //
      SimpleComboBox<Light> lightCombo = new SimpleComboBox<Light>(new StringLabelProvider<Light>());
      lightCombo.setClearValueOnParseError(false);
      lightCombo.setPropertyEditor(new PropertyEditor<Light>() {
        @Override
        public Light parse(CharSequence text) throws ParseException {
          return Light.parseString(text.toString());
        }

        @Override
        public String render(Light object) {
          return object == null ? Light.SUNNY.toString() : object.toString();
        }
      });
      lightCombo.setTriggerAction(TriggerAction.ALL);
      lightCombo.add(Light.SUNNY);
      lightCombo.add(Light.MOSTLYSUNNY);
      lightCombo.add(Light.SUNORSHADE);
      lightCombo.add(Light.MOSTLYSHADY);
      lightCombo.add(Light.SHADE);

      Converter<String, Light> lightConverter = new Converter<String, Light>() {
        @Override
        public String convertFieldValue(Light object) {
          return object == null ? "" : object.toString();
        }

        @Override
        public Light convertModelValue(String object) {
          try {
            return Light.parseString(object);
          } catch (ParseException e) {
            return null;
          }
        }
      };

      DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
      DateField dateField = new DateField(new DateTimePropertyEditor(dateFormat));
      dateField.setClearValueOnParseError(false);

      CheckBox checkBox = new CheckBox();
      // TODO checkBox.getElement().getStyle().setBackgroundColor("white");

      final GridEditing<Plant> editing = new GridInlineEditing<Plant>(grid);
      editing.addEditor(nameColumn, new TextField());
      editing.addEditor(lightColumn, lightConverter, lightCombo);
      editing.addEditor(dateColumn, dateField);

      // Another checkbox option 3 - Use this with the true/false label cell renderer above.
      // editing.addEditor(indoorColumn, checkBox);

      // column 5 is not editable

      // EDITING //

      TextButton addButton = new TextButton("Add Plant");
      addButton.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          Plant plant = new Plant();
          plant.setName("New Plant 1");
          plant.setLight("Mostly Shady");
          plant.setPrice(0);
          plant.setAvailable(new DateWrapper().clearTime().asDate());
          plant.setIndoor(false);

          editing.cancelEditing();
          store.add(0, plant);

          int row = store.indexOf(plant);
          editing.startEditing(new GridCell(row, 0));
        }
      });

      ToolBar toolBar = new ToolBar();
      toolBar.add(addButton);

      VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
      verticalLayoutContainer.add(toolBar, new VerticalLayoutData(1, -1));
      verticalLayoutContainer.add(grid, new VerticalLayoutData(1, 1));

      panel = new ContentPanel();
      panel.setHeading("Inline Editable Grid");
      panel.add(verticalLayoutContainer);
      panel.setButtonAlign(BoxLayoutPack.CENTER);
      panel.addButton(new TextButton("Reset", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          store.rejectChanges();
        }
      }));
      panel.addButton(new TextButton("Save", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          store.commitChanges();
        }
      }));
    }

    return panel;
  }

}
