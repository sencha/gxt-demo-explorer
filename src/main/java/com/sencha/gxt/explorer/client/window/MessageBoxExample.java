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
package com.sencha.gxt.explorer.client.window;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Format;
import com.sencha.gxt.core.client.util.Params;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.box.MultiLinePromptMessageBox;
import com.sencha.gxt.widget.core.client.box.ProgressMessageBox;
import com.sencha.gxt.widget.core.client.box.PromptMessageBox;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;

@Detail(
    name = "Message Box",
    category = "Windows",
    icon = "messagebox",
    minWidth = MessageBoxExample.MIN_WIDTH,
    preferredHeight = MessageBoxExample.PREFERRED_HEIGHT
)
public class MessageBoxExample implements IsWidget, EntryPoint {

  protected static final int MIN_WIDTH = 515;
  protected static final int PREFERRED_HEIGHT = -1;

  private DialogHideHandler hideHandler = new DialogHideHandler() {
    @Override
    public void onDialogHide(DialogHideEvent event) {
      String message = Format.substitute("The '{0}' button was pressed", event.getHideButton());
      Info.display("MessageBox", message);
    }
  };

  private ButtonBar buttonBar;

  @Override
  public Widget asWidget() {
    if (buttonBar == null) {

      TextButton buttonConfirm = new TextButton("Confirm");
      buttonConfirm.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          createConfirm();
        }
      });

      TextButton buttonPrompt = new TextButton("Prompt");
      buttonPrompt.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          createPrompt();
        }
      });

      TextButton buttonMultiPrompt = new TextButton("Multiline Prompt");
      buttonMultiPrompt.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          createMultiPrompt();
        }
      });

      TextButton buttonYesNoCancel = new TextButton("Yes/No/Cancel");
      buttonYesNoCancel.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          createYesNoCancel();
        }
      });

      TextButton buttonProgress = new TextButton("Progress");
      buttonProgress.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          createProgress();
        }
      });

      TextButton buttonWait = new TextButton("Wait");
      buttonWait.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          createWait();
        }
      });

      TextButton buttonAlert = new TextButton("Alert");
      buttonAlert.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          createAlert();
        }
      });

      buttonBar = new ButtonBar();
      buttonBar.add(buttonConfirm);
      buttonBar.add(buttonPrompt);
      buttonBar.add(buttonMultiPrompt);
      buttonBar.add(buttonYesNoCancel);
      buttonBar.add(buttonProgress);
      buttonBar.add(buttonWait);
      buttonBar.add(buttonAlert);
    }

    return buttonBar;
  }

  private void createAlert() {
    AlertMessageBox messageBox = new AlertMessageBox("Message Box — Alert", "Access Denied");
    messageBox.addDialogHideHandler(hideHandler);
    messageBox.show();
  }

  private void createWait() {
    final AutoProgressMessageBox messageBox = new AutoProgressMessageBox("Message Box — Wait", "Saving your data, please wait...");
    messageBox.setProgressText("Saving...");
    messageBox.auto();
    messageBox.show();

    Timer timer = new Timer() {
      @Override
      public void run() {
        Info.display("Message", "Your fake data was saved");
        messageBox.hide();
      }
    };
    timer.schedule(5000);
  }

  private void createProgress() {
    final ProgressMessageBox messageBox = new ProgressMessageBox("Message Box — Progress", "Loading items...");
    messageBox.setProgressText("Initializing...");
    messageBox.show();

    final Timer timer = new Timer() {
      float i;

      @Override
      public void run() {
        messageBox.updateProgress(i / 100, "{0}% Complete");
        i += 5;
        if (i > 100) {
          cancel();
          Info.display("Message", "Items were loaded");
        }
      }
    };
    timer.scheduleRepeating(500);

    messageBox.addDialogHideHandler(new DialogHideHandler() {
      @Override
      public void onDialogHide(DialogHideEvent event) {
        timer.cancel();
        hideHandler.onDialogHide(event);
      }
    });
  }

  private void createYesNoCancel() {
    MessageBox messageBox = new MessageBox("Message Box — Yes/No/Cancel", "");
    messageBox.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.NO, PredefinedButton.CANCEL);
    messageBox.setIcon(MessageBox.ICONS.question());
    messageBox.setMessage("You are closing a tab that has unsaved changes. Would you like to save your changes?");
    messageBox.addDialogHideHandler(new DialogHideHandler() {
      @Override
      public void onDialogHide(DialogHideEvent event) {
        String msg = Format.substitute("The '{0}' button was pressed", event.getHideButton());

        switch (event.getHideButton()) {
          case YES:
            Info.display("MessageBox", "Yes: " + msg);
            break;
          case NO:
            Info.display("MessageBox", "No: " + msg);
            break;
          case CANCEL:
            Info.display("MessageBox", "Canceled: " + msg);
            break;
        }
      }
    });
    messageBox.show();
  }

  private void createMultiPrompt() {
    final MultiLinePromptMessageBox messageBox = new MultiLinePromptMessageBox("Message Box — Multiline Prompt",
        "Please enter your address:");
    messageBox.addDialogHideHandler(new DialogHideHandler() {
      @Override
      public void onDialogHide(DialogHideEvent event) {
        if (event.getHideButton() == PredefinedButton.OK) {
          String v = Format.ellipse(messageBox.getValue(), 80);
          String msg = Format.substitute("You entered '{0}'", new Params(v));
          Info.display("MessageBox", msg);
        } else {
          Info.display("MessageBox", "Canceled");
        }
      }
    });
    messageBox.show();
  }

  private void createPrompt() {
    final PromptMessageBox messageBox = new PromptMessageBox("Message Box — Prompt", "Please enter your name:");
    // First option: Listening for the hide event and then figuring which button was pressed.
    messageBox.addDialogHideHandler(new DialogHideHandler() {
      @Override
      public void onDialogHide(DialogHideEvent event) {
        if (event.getHideButton() == PredefinedButton.OK) {
          Info.display("MessageBox", "You entered " + messageBox.getValue());
        } else {
          Info.display("MessageBox", "Canceled");
        }
      }
    });

    // Second option: Listen for a button click
    messageBox.getButton(PredefinedButton.OK).addSelectHandler(new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        // Do something
      }
    });
    messageBox.show();
  }

  private void createConfirm() {
    ConfirmMessageBox messageBox = new ConfirmMessageBox("Message Box — Confirm", "Are you sure you want to do that?");
    messageBox.addDialogHideHandler(hideHandler);
    messageBox.show();
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinWidth(MIN_WIDTH)
        .setPreferredHeight(PREFERRED_HEIGHT)
        .doStandalone();
  }

}
