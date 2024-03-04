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
package com.sencha.gxt.explorer.client.databinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Util;
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.examples.resources.client.model.Kid;
import com.sencha.gxt.examples.resources.client.model.Person;
import com.sencha.gxt.explorer.client.databinding.ListPropertyBindingExample.Driver;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DoubleField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

public class PersonEditor implements IsWidget, Editor<Person> {

  interface KidProperties extends PropertyAccess<Kid> {
    @Path("id")
    ModelKeyProvider<Kid> key();

    ValueProvider<Kid, String> name();

    ValueProvider<Kid, Integer> age();
  }

  private static final KidProperties props = GWT.create(KidProperties.class);
  private final Driver driver;

  // Editor wired fields
  TextField name = new TextField();
  TextField company = new TextField();
  TextField location = new TextField();
  DoubleField income = new DoubleField();
  ListStore<Kid> kidStore = new ListStore<Kid>(props.key());
  ListStoreEditor<Kid> kids = new ListStoreEditor<Kid>(kidStore);

  private VerticalLayoutContainer container;

  public PersonEditor(Driver driver) {
    super();

    driver.initialize(this);
    this.driver = driver;
  }

  @Override
  public Widget asWidget() {
    if (container == null) {
      final CheckBoxSelectionModel<Kid> selection = new CheckBoxSelectionModel<Kid>();
      selection.setSelectionMode(SelectionMode.MULTI);

      ColumnConfig<Kid, String> nameColumn = new ColumnConfig<Kid, String>(props.name(), 200, "Name");
      ColumnConfig<Kid, Integer> ageColumn = new ColumnConfig<Kid, Integer>(props.age(), 100, "Age");

      List<ColumnConfig<Kid, ?>> columns = new ArrayList<ColumnConfig<Kid, ?>>();
      columns.add(selection.getColumn());
      columns.add(nameColumn);
      columns.add(ageColumn);

      final Grid<Kid> grid = new Grid<Kid>(kidStore, new ColumnModel<Kid>(columns));
      grid.setBorders(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? true : false);
      grid.setSelectionModel(selection);
      grid.getView().setForceFit(true);
      grid.getView().setAutoExpandColumn(nameColumn);

      GridInlineEditing<Kid> inlineEditor = new GridInlineEditing<Kid>(grid);
      inlineEditor.addEditor(nameColumn, new TextField());
      inlineEditor.addEditor(ageColumn, new IntegerField());

      FieldLabel kidsContainer = new FieldLabel();
      kidsContainer.setText("Kids");
      kidsContainer.setLabelAlign(LabelAlign.TOP);
      kidsContainer.setWidget(grid);

      TextButton deleteBtn = new TextButton("Delete Kid(s)", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          for (Kid entityToDelete : selection.getSelectedItems()) {
            kidStore.remove(entityToDelete);
          }
        }
      });

      TextButton createBtn = new TextButton("Add Kid", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          Kid newRow = new Kid("— new kid —", 0, new Date());
          kidStore.add(newRow);
        }
      });

      TextButton saveBtn = new TextButton("Save", new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          Person p = driver.flush();
          if (!driver.hasErrors()) {
            new MessageBox(p.getName(), p.getName() + " has " + p.getKids().size() + " kids").show();
          }
        }
      });


      ButtonBar buttons = new ButtonBar();
      buttons.add(deleteBtn);
      buttons.add(createBtn);
      BoxLayoutData flex = new BoxLayoutData();
      flex.setFlex(1);
      buttons.add(new LabelToolItem(Util.NBSP_SAFE_HTML), flex);
      buttons.add(saveBtn);

      container = new VerticalLayoutContainer();
      container.add(new FieldLabel(name, "Name"), new VerticalLayoutData(1, -1));
      container.add(new FieldLabel(company, "Company"), new VerticalLayoutData(1, -1));
      container.add(new FieldLabel(location, "Location"), new VerticalLayoutData(1, -1));
      container.add(new FieldLabel(income, "Income"), new VerticalLayoutData(1, -1));
      container.add(kidsContainer, new VerticalLayoutData(1, 1));
      container.add(buttons, new VerticalLayoutData(1, -1));
    }

    return container;
  }

}
