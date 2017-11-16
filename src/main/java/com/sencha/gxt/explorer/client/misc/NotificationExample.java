package com.sencha.gxt.explorer.client.misc;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.IntegerPropertyEditor;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinNumberValidator;
import com.sencha.gxt.widget.core.client.info.DefaultInfoConfig;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.info.InfoConfig.InfoPosition;

@Detail(
    name = "Notification",
    category = "Miscellaneous",
    icon = "notification",
    preferredHeight = NotificationExample.PREFERRED_HEIGHT,
    preferredWidth = NotificationExample.PREFERRED_WIDTH,
    classes = { Utils.class }
)
public class NotificationExample implements IsWidget, EntryPoint {

  protected static final int PREFERRED_HEIGHT = -1;
  protected static final int PREFERRED_WIDTH = 400;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final TextField title = new TextField();
      title.setValue("Notification Title");

      final TextField text = new TextField();
      text.setValue("This is the notification text");

      final SimpleComboBox<InfoPosition> positionCombo = new SimpleComboBox<InfoPosition>(
          new StringLabelProvider<InfoPosition>());
      positionCombo.add(InfoPosition.TOP_LEFT);
      positionCombo.add(InfoPosition.TOP_RIGHT);
      positionCombo.add(InfoPosition.BOTTOM_LEFT);
      positionCombo.add(InfoPosition.BOTTOM_RIGHT);
      positionCombo.setEditable(false);
      positionCombo.setTriggerAction(TriggerAction.ALL);
      positionCombo.setValue(InfoPosition.TOP_RIGHT);

      final NumberField<Integer> display = new NumberField<Integer>(new IntegerPropertyEditor());
      display.setValue(2500);
      display.addValidator(new EmptyValidator<Integer>());
      display.addValidator(new MinNumberValidator<Integer>(100));

      TextButton showButton = new TextButton("Show Notification");
      showButton.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          if (display.isCurrentValid()) {
            DefaultInfoConfig config = new DefaultInfoConfig(title.getValue(), text.getValue());
            config.setPosition(positionCombo.getValue());
            config.setDisplay(display.getValue());
            Info.display(config);
          } else {
            AlertMessageBox d = new AlertMessageBox("Alert", "Please increase the Display (ms) to 100+.");
            d.show();
          }
        }
      });

      CssFloatLayoutContainer container = new CssFloatLayoutContainer();
      container.add(new FieldLabel(title, "Title"), new CssFloatData(1, new Margins(0, 0, 10, 0)));
      container.add(new FieldLabel(text, "Text"), new CssFloatData(1, new Margins(0, 0, 10, 0)));
      container.add(new FieldLabel(positionCombo, "Position"), new CssFloatData(1, new Margins(0, 0, 10, 0)));
      container.add(new FieldLabel(display, "Display (ms)"), new CssFloatData(1));


      panel = Theme.BLUE.isActive() || Theme.GRAY.isActive() ? new FramedPanel() : new ContentPanel();
      panel.setHeading("Notification");
      panel.add(container, new MarginData(10));
      panel.addButton(showButton);
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
