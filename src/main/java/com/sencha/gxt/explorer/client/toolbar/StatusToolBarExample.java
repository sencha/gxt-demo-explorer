package com.sencha.gxt.explorer.client.toolbar;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.regexp.shared.SplitResult;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.DelayedTask;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Util;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Status;
import com.sencha.gxt.widget.core.client.Status.BoxStatusAppearance;
import com.sencha.gxt.widget.core.client.Status.StatusAppearance;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
    name = "Status Tool Bar",
    category = "Tool Bar & Menu",
    icon = "statustoolbar",
    minHeight = StatusToolBarExample.MIN_HEIGHT,
    minWidth = StatusToolBarExample.MIN_WIDTH
)
public class StatusToolBarExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 480;

  private DelayedTask task = new DelayedTask() {
    @Override
    public void onExecute() {
      status.clearStatus("Not writing");
    }
  };

  private Status charCount;
  private Status wordCount;
  private Status status;
  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      status = new Status();
      status.setText("Not writing");
      status.setWidth(150);

      charCount = new Status(GWT.<StatusAppearance> create(BoxStatusAppearance.class));
      charCount.setWidth(100);
      charCount.setText("0 Chars");

      wordCount = new Status(GWT.<StatusAppearance> create(BoxStatusAppearance.class));
      wordCount.setWidth(100);
      wordCount.setText("0 Words");

      VerticalLayoutData data = new VerticalLayoutData(1, 1);
      data.setMargins(new Margins(5));

      TextArea textArea = new TextArea();
      textArea.addKeyUpHandler(new KeyUpHandler() {
        @Override
        public void onKeyUp(KeyUpEvent event) {
          status.setBusy("writing...");
          TextArea t = (TextArea) event.getSource();
          String value = t.getCurrentValue();
          int length = value != null ? value.length() : 0;
          charCount.setText(length + (length == 1 ? " Char" : " Chars"));

          if (value != null) {
            int wc = getWordCount(value);
            wordCount.setText(wc + (wc == 1 ? " Word" : " Words"));
          }

          task.delay(1000);
        }
      });

      ToolBar toolBar = new ToolBar();
      toolBar.setBorders(false);
      toolBar.add(status);
      toolBar.add(new FillToolItem());
      toolBar.add(charCount);
      toolBar.add(new LabelToolItem(Util.NBSP_SAFE_HTML));
      toolBar.add(wordCount);
      toolBar.setLayoutData(new VerticalLayoutData(1, -1));

      VerticalLayoutContainer form = new VerticalLayoutContainer();
      form.add(textArea, new VerticalLayoutData(1, 1, new Margins(5)));
      form.add(toolBar);

      panel = new ContentPanel();
      panel.setHeading("Status Tool Bar");
      panel.add(form);
    }

    return panel;
  }

  public int getWordCount(String v) {
    SplitResult result = RegExp.compile("\\b", "g").split(v);
    return result.length() / 2;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
