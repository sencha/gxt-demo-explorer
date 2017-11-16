package com.sencha.gxt.explorer.client.forms;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

@Detail(
  name = "FieldLabel",
  category = "Forms",
  icon = "formsexample",
  preferredHeight = FieldLabelExample.PREFERRED_HEIGHT,
  preferredWidth = FieldLabelExample.PREFERRED_WIDTH)
public class FieldLabelExample implements IsWidget, EntryPoint {

  protected static final int PREFERRED_HEIGHT = -1;
  protected static final int PREFERRED_WIDTH = 500;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      TextField field1 = new TextField();
      TextField field2 = new TextField();
      TextField field3 = new TextField();
      TextField field4 = new TextField();
      TextField field5 = new TextField();
      TextField field6 = new TextField();

      // Example of the field label
      FieldLabel fieldLabel1 = new FieldLabel(field1, "Field 1");
      FieldLabel fieldLabel2 = new FieldLabel(field2, "Field 2");
      FieldLabel fieldLabel3 = new FieldLabel(field3, "Field 3");
      
      fieldLabel1.setLabelAlign(LabelAlign.LEFT);
      fieldLabel1.setLabelPad(5);
      fieldLabel1.setLabelSeparator(":");
      fieldLabel1.setLabelWidth(100);
      fieldLabel1.setLabelWordWrap(false);

      // Write your own custom field label
      Widget customFieldLabel1 = createMyOwnFieldLabel(field4, "Field 4");
      Widget customFieldLabel2 = createMyOwnFieldLabel(field5, "Field 5");
      Widget customFieldLabel3 = createMyOwnFieldLabel(field6, "Field 6");

      VerticalLayoutContainer vbox = new VerticalLayoutContainer();
      vbox.add(new LabelToolItem("FieldLabels"), new VerticalLayoutData(1, -1, new Margins(20, 20, 5, 20)));
      vbox.add(fieldLabel1, new VerticalLayoutData(1, -1, new Margins(20)));
      vbox.add(fieldLabel2, new VerticalLayoutData(1, -1, new Margins(20)));
      vbox.add(fieldLabel3, new VerticalLayoutData(1, -1, new Margins(20)));
      vbox.add(new LabelToolItem("Custom FieldLabels"), new VerticalLayoutData(1, -1, new Margins(40, 20, 5, 20)));
      vbox.add(customFieldLabel1, new VerticalLayoutData(1, -1, new Margins(20)));
      vbox.add(customFieldLabel2, new VerticalLayoutData(1, -1, new Margins(20)));
      vbox.add(customFieldLabel3, new VerticalLayoutData(1, -1, new Margins(20)));

      panel = new ContentPanel();
      panel.setHeading("Example of the FieldLabel");
      panel.add(vbox);
    }

    return panel;
  }

  private Widget createMyOwnFieldLabel(Widget widget, String label) {
    LabelToolItem labelToolItem = new LabelToolItem(label + ":");

    BoxLayoutData flex = new BoxLayoutData();
    flex.setFlex(1);
    
    HBoxLayoutContainer row = new HBoxLayoutContainer(HBoxLayoutAlign.MIDDLE);
    row.setEnableOverflow(false);
    row.add(labelToolItem);
    row.add(widget, flex);

    return row;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setPreferredHeight(PREFERRED_HEIGHT).setPreferredWidth(PREFERRED_WIDTH).doStandalone();
  }

}
