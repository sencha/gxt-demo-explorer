package com.sencha.gxt.explorer.client.app.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.explorer.client.model.Example;
import com.sencha.gxt.explorer.client.model.Source;
import com.sencha.gxt.widget.core.client.TabPanel;

/**
 * Displays an example and the associated source code.
 */
public class Page extends TabPanel {

  public interface SourceProperties extends PropertyAccess<Source> {
    @Path("id")
    ModelKeyProvider<Source> key();

    @Path("name")
    ValueProvider<Source, String> nameLabel();
  }

  public Page(final Example example) {
    super(GWT.<TabPanelAppearance> create(TabPanelBottomAppearance.class));
    setBodyBorder(false);

    add(new ExampleContainer(example), "Demo");

    if (!example.getSources().isEmpty()) {
      add(new SourceContainer(example), "Source");
    }
  }

  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);
    Widget activeWidget = getActiveWidget();
    if (activeWidget != null) {
      activeWidget.setVisible(visible);
    }
  }
}
