/**
 * Sencha GXT 1.0.0-SNAPSHOT - Sencha for GWT
 * Copyright (c) 2006-2018, Sencha Inc.
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
package com.sencha.gxt.explorer.client.html;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;

@Detail(
  name = "WYSIWYG HTML Editor",
  category = "HTML",
  icon = "animation",
  minHeight = FroalaEditorBasicExample.MIN_HEIGHT,
  minWidth = FroalaEditorBasicExample.MIN_WIDTH)
public class FroalaEditorBasicExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 600;
  protected static final int MIN_WIDTH = 900;

  private ContentPanel panel;
  private TextArea textArea;

  /**
   * https://www.froala.com/wysiwyg-editor
   */
  @Override
  public Widget asWidget() {
    if (panel == null) {
      // Use the GWT text area, it's simpler to use. 
      textArea = new TextArea();
      textArea.setValue(
          "<p>The <a href=\"https://www.froala.com/wysiwyg-editor\">Froala Editor</a> is a lightweight WYSIWYG HTML Editor written in Javascript that enables rich text editing capabilities for your applications.</p><p><br></p><p>"
              + "<img src=\"https://www.sencha.com/wp-content/uploads/2015/03/built-in-support-for-rpc-requestfactory-and-json.png\" style=\"width: 300px;\" class=\"fr-fic fr-dib fr-fir\"></p>");

      TextButton buttonGet = new TextButton("Display HTML Info");
      buttonGet.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          Info.display("Your HTML is", getValue());
        }
      });

      // In this layout, I use this container to fill the parent container 100% x 100%
      // This sizes the textarea 100% x 100%
      VerticalLayoutContainer wrapper = new VerticalLayoutContainer();
      wrapper.add(textArea, new VerticalLayoutData(1, 1));

      panel = new ContentPanel();
      panel.setHeading("Froala WYSIWYG HTML Editor");
      panel.add(wrapper, new MarginData(20));
      panel.addButton(buttonGet);
      panel.addAttachHandler(new Handler() {
        @Override
        public void onAttachOrDetach(AttachEvent event) {
          if (panel.isAttached()) {
            Scheduler.get().scheduleDeferred(new ScheduledCommand() { 
              @Override
              public void execute() {
                // Figure out a specific size to assign to the froala div
                // The specific height will ensure it scrolls to the height given
                int height = panel.getBodyWrap().getOffsetHeight();
                // This offsets for margins...
                height -= 160;
                initFroala(height);
              }
            });
          }
        }
      });

    }

    return panel;
  }

  private native String getValue() /*-{
    var value = $wnd.$('textarea').froalaEditor('html.get');
    console.log(value);
    return value;
  }-*/;

  private native void initFroala(int height) /*-{
    // froala.license.key is inserted from build system
    $wnd.$('textarea').froalaEditor({
      // Provide a height so it scrolls to that height
      'height' : height
    });
  }-*/;
  
  /**
   * Example of how to update the editor
   */
  private native void updateFroalaEditorHeight() /*-{
    var editor = $wnd.$('textarea').data('froala.editor');
    editor.opts.height = 100;
    editor.size.refresh();
  }-*/;

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinHeight(MIN_HEIGHT).setMinWidth(MIN_WIDTH).setMaxWidth(1000.0).doStandalone();
  }

}
