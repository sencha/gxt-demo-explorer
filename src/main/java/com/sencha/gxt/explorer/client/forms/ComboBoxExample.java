package com.sencha.gxt.explorer.client.forms;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Country;
import com.sencha.gxt.examples.resources.client.model.State;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.info.Info;

@Detail(
    name = "Combo Box",
    category = "Forms",
    icon = "combobox",
    classes = {
        Country.class,
        State.class,
        TestData.class
    },
    preferredHeight = ComboBoxExample.PREFERRED_HEIGHT,
    preferredWidth = ComboBoxExample.PREFERRED_WIDTH
)
public class ComboBoxExample implements IsWidget, EntryPoint {

  interface ComboBoxTemplates extends XTemplates {
    @XTemplate("<img width=\"16\" height=\"11\" src=\"{imageUri}\">&nbsp;{name}")
    SafeHtml country(SafeUri imageUri, String name);

    @XTemplate("<div qtip=\"{slogan}\" qtitle=\"State Slogan\">{name}</div>")
    SafeHtml state(String slogan, String name);
  }

  interface CountryProperties extends PropertyAccess<Country> {
    ModelKeyProvider<Country> abbr();

    LabelProvider<Country> name();
  }

  interface StateProperties extends PropertyAccess<State> {
    ModelKeyProvider<State> abbr();

    LabelProvider<State> name();
  }

  protected static final int PREFERRED_HEIGHT = -1;
  protected static final int PREFERRED_WIDTH = 300;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      StateProperties properties = GWT.create(StateProperties.class);

      CountryProperties countryProps = GWT.create(CountryProperties.class);

      ListStore<State> statesStore1 = new ListStore<State>(properties.abbr());
      statesStore1.addAll(TestData.getStates());

      ListStore<State> statesStore2 = new ListStore<State>(properties.abbr());
      statesStore2.addAll(TestData.getStates());

      ListStore<Country> countries = new ListStore<Country>(countryProps.abbr());
      countries.addAll(TestData.getCountries());

      ComboBox<State> combo1 = new ComboBox<State>(statesStore1, properties.name());
      combo1.setEmptyText("Select a state...");
      combo1.setTypeAhead(true);
      combo1.setTriggerAction(TriggerAction.ALL);

      ComboBox<State> combo2 = new ComboBox<State>(statesStore2, properties.name(),
          new AbstractSafeHtmlRenderer<State>() {
            @Override
            public SafeHtml render(State item) {
              final ComboBoxTemplates comboBoxTemplates = GWT.create(ComboBoxTemplates.class);
              return comboBoxTemplates.state(item.getSlogan(), item.getName());
            }
          });
      combo2.setEmptyText("Select a state...");
      combo2.setTypeAhead(true);
      combo2.setTriggerAction(TriggerAction.ALL);

      ComboBox<Country> combo3 = new ComboBox<Country>(countries, countryProps.name(),
          new AbstractSafeHtmlRenderer<Country>() {
            final ComboBoxTemplates comboBoxTemplates = GWT.create(ComboBoxTemplates.class);
            @Override
            public SafeHtml render(Country item) {
              SafeUri imageUri = UriUtils.fromString(GWT.getHostPageBaseURL() + "examples/images/flags/"
                  + item.getAbbr() + ".png");
              return comboBoxTemplates.country(imageUri, item.getName());
            }
          });
      combo3.setTypeAhead(true);
      combo3.setTriggerAction(TriggerAction.ALL);

      addHandlersForEventObservation(combo1, properties.name());
      addHandlersForEventObservation(combo2, properties.name());
      addHandlersForEventObservation(combo3, countryProps.name());

      CssFloatLayoutContainer container = new CssFloatLayoutContainer();
      container.add(combo1, new CssFloatData(1, new Margins(0, 0, 10, 0)));
      container.add(combo2, new CssFloatData(1, new Margins(0, 0, 10, 0)));
      container.add(combo3, new CssFloatData(1));

      panel = new ContentPanel();
      panel.setHeading("Combo Box");
      panel.add(container, new MarginData(10));
    }

    return panel;
  }

  /**
   * Helper to add handlers to observe events that occur on each combobox
   */
  private <T> void addHandlersForEventObservation(ComboBox<T> combo, final LabelProvider<T> labelProvider) {
    combo.addValueChangeHandler(new ValueChangeHandler<T>() {
      @Override
      public void onValueChange(ValueChangeEvent<T> event) {
        String selected = "New value: "
            + (event.getValue() == null ? "nothing" : labelProvider.getLabel(event.getValue()) + "!");
        Info.display("Value Changed", selected);
      }
    });

    combo.addSelectionHandler(new SelectionHandler<T>() {
      @Override
      public void onSelection(SelectionEvent<T> event) {
        String selected = "You selected "
            + (event.getSelectedItem() == null ? "nothing" : labelProvider.getLabel(event.getSelectedItem()) + "!");
        Info.display("State Selected", selected);
      }
    });
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setPreferredHeight(PREFERRED_HEIGHT)
        .setPreferredWidth(PREFERRED_WIDTH)
        .doStandalone();
  }

}
