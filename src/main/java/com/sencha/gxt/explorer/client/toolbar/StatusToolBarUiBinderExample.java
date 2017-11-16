package com.sencha.gxt.explorer.client.toolbar;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.regexp.shared.SplitResult;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.DelayedTask;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.Status;
import com.sencha.gxt.widget.core.client.Status.BoxStatusAppearance;
import com.sencha.gxt.widget.core.client.Status.StatusAppearance;
import com.sencha.gxt.widget.core.client.form.TextArea;

@Detail(
    name = "Status Tool Bar (UiBinder)",
    category = "Tool Bar & Menu",
    icon = "statustoolbaruibinder",
    files = "StatusToolBarUiBinderExample.ui.xml",
    minHeight = StatusToolBarUiBinderExample.MIN_HEIGHT,
    minWidth = StatusToolBarUiBinderExample.MIN_WIDTH
)
public class StatusToolBarUiBinderExample implements IsWidget, EntryPoint {

  interface MyUiBinder extends UiBinder<Component, StatusToolBarUiBinderExample> {
  }

  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 480;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  private DelayedTask task = new DelayedTask() {
    @Override
    public void onExecute() {
      status.clearStatus("Not writing");
    }
  };

  @UiField
  Status status;
  @UiField(provided = true)
  Status charCount = new Status(GWT.<StatusAppearance> create(BoxStatusAppearance.class));
  @UiField(provided = true)
  Status wordCount = new Status(GWT.<StatusAppearance> create(BoxStatusAppearance.class));

  private Component widget;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      widget = uiBinder.createAndBindUi(this);
    }

    return widget;
  }

  @UiHandler("textArea")
  public void onKeyUp(KeyUpEvent event) {
    status.setBusy("writing...");
    TextArea textArea = (TextArea) event.getSource();
    String value = textArea.getCurrentValue();
    int length = value != null ? value.length() : 0;
    charCount.setText(length + (length == 1 ? " Char" : " Chars"));

    if (value != null) {
      int wc = getWordCount(value);
      wordCount.setText(wc + (wc == 1 ? " Word" : " Words"));
    }

    task.delay(1000);
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
