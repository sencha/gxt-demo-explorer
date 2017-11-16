package com.sencha.gxt.explorer.client.window;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.AnchorAlignment;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

import static com.sencha.gxt.core.client.Style.Anchor.CENTER;

@Detail(
    name = "Hello World",
    category = "Windows",
    icon = "helloworld",
    preferredWidth = HelloWindowExample.PREFERRED_WIDTH,
    preferredHeight = HelloWindowExample.PREFERRED_HEIGHT,
    preferredMargin = HelloWindowExample.PREFERRED_MARGIN
)
public class HelloWindowExample implements IsWidget, EntryPoint {

  protected static final int PREFERRED_WIDTH = 1;
  protected static final int PREFERRED_HEIGHT = 1;
  // keep margins for window shadow
  protected static final int PREFERRED_MARGIN = 2;

  private SimpleContainer container;
  private Window window;

  @Override
  public Widget asWidget() {
    if (container == null) {
      Label label1 = new Label("Hello...");
      label1.addStyleName("pad-text");

      Label label2 = new Label("World...");
      label2.addStyleName("pad-text");

      TabPanel panel = new TabPanel();
      panel.setBorders(false);
      panel.add(label1, new TabItemConfig("Hello World 1"));
      panel.add(label2, new TabItemConfig("Hello World 2"));

      container = new SimpleContainer() {

        @Override
        public void setVisible(boolean visible) {
          super.setVisible(visible);
          if (visible) {
            window.show();
            Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
              @Override
              public void execute() {
                // since this is deferred, check that window is actually visible
                if (window.isVisible()) {
                  window.alignTo(container.getElement(), new AnchorAlignment(CENTER, CENTER), 0, 0);
                }
              }
            });
          } else {
            window.hide();
          }
        }
      };

      window = new Window();
      window.setPixelSize(640, 480);
      window.setResizable(false);
      window.setHeading("Hello World");
      window.add(panel);
      window.setClosable(false);
      window.getDraggable().setContainer(container);
    }

    return container;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setPreferredWidth(PREFERRED_WIDTH)
        .setPreferredHeight(PREFERRED_HEIGHT)
        .setPreferredMargin(PREFERRED_MARGIN)
        .doStandalone();
  }

}
