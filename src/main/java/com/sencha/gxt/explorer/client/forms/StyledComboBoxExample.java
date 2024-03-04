/**
 * Sencha GXT 1.0.0-SNAPSHOT - Sencha for GWT
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
package com.sencha.gxt.explorer.client.forms;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.State;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.theme.base.client.listview.ListViewDefaultAppearance;
import com.sencha.gxt.theme.base.client.listview.ListViewDefaultAppearance.ListViewDefaultResources;
import com.sencha.gxt.theme.base.client.listview.ListViewDefaultAppearance.ListViewDefaultStyle;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.form.ComboBox;

@Detail(
    name = "Combo Box Styled",
    category = "Forms",
    icon = "styledcombobox",
    files = {"com/sencha/gxt/theme/base/client/listview/ListView.gss", "StyledComboBox.gss"},
    classes = { State.class, TestData.class },
    preferredHeight = StyledComboBoxExample.PREFERRED_HEIGHT,
    preferredWidth = StyledComboBoxExample.PREFERRED_WIDTH
)
public class StyledComboBoxExample implements EntryPoint, IsWidget {

  interface CustomListViewResources extends ListViewDefaultResources {
    @Override
    @Source({"com/sencha/gxt/theme/base/client/listview/ListView.gss", "StyledComboBox.gss"})
    CustomListViewStyle css();
  }

  interface CustomListViewStyle extends ListViewDefaultStyle {
  }

  interface StateProperties extends PropertyAccess<State> {
    ModelKeyProvider<State> abbr();

    LabelProvider<State> name();

    @Path("name")
    ValueProvider<State, String> nameValueProvider();
  }

  protected static final int PREFERRED_HEIGHT = -1;
  protected static final int PREFERRED_WIDTH = 300;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      StateProperties properties = GWT.create(StateProperties.class);
      
      ListStore<State> states = new ListStore<State>(properties.abbr());
      states.addAll(TestData.getStates());

      ListViewDefaultResources resources = GWT.create(CustomListViewResources.class);
      ListViewDefaultAppearance<State> appearance = new ListViewDefaultAppearance<State>(resources);

      ListView<State, String> listView = new ListView<State, String>(states, properties.nameValueProvider(), appearance);

      ComboBox<State> combo = new ComboBox<State>(states, properties.name(), listView);
      combo.setEmptyText("Select a state...");
      combo.setTypeAhead(true);
      combo.setTriggerAction(TriggerAction.ALL);

      CssFloatLayoutContainer container = new CssFloatLayoutContainer();
      container.add(combo, new CssFloatData(1));

      panel = new ContentPanel();
      panel.setHeading("Combo Box — Styled");
      panel.add(container, new MarginData(10));
    }

    return panel;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setPreferredHeight(PREFERRED_HEIGHT)
        .setPreferredWidth(PREFERRED_WIDTH)
        .doStandalone();
  }

}
