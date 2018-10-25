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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ProgressBar;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;

import elemental2.dom.DataTransfer;
import elemental2.dom.DomGlobal;
import elemental2.dom.DragEvent;
import elemental2.dom.Element;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.File;
import elemental2.dom.FileList;
import elemental2.dom.FileReader;
import elemental2.dom.FileReader.OnloadendFn;
import elemental2.dom.FileReader.ResultUnionType;
import elemental2.dom.FormData;
import elemental2.dom.HTMLAnchorElement;
import elemental2.dom.HTMLDocument;
import elemental2.dom.HTMLImageElement;
import elemental2.dom.ProgressEvent;
import elemental2.dom.XMLHttpRequest;

/**
 * https://developer.mozilla.org/en-US/docs/Web/API/HTML_Drag_and_Drop_API/File_drag_and_drop
 * 
 * https://github.com/google/elemental2
 * 
 * <inherits name="elemental2.core.Core"/> <br>
 * <inherits name="elemental2.dom.Dom"/>
 */
@Detail(
  name = "Upload with Progress",
  category = "HTML",
  icon = "animation",
  minHeight = ElementalFileDndUploadExample.MIN_HEIGHT,
  minWidth = ElementalFileDndUploadExample.MIN_WIDTH)
public class ElementalFileDndUploadExample implements EntryPoint, IsWidget {

  protected static final int MIN_HEIGHT = -1;
  protected static final int MIN_WIDTH = -1;

  private HTMLAnchorElement anchorElement;
  private HTMLImageElement imageElement;
  private FlowLayoutContainer widget;
  private ProgressBar progressBar;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      HTMLDocument document = DomGlobal.document;

      imageElement = (HTMLImageElement) document.createElement("img");
      imageElement.setAttribute("style", "width: 100%; height: 100%;");

      Element divElement = document.createElement("div");
      divElement.appendChild(imageElement);
      divElement.setAttribute("style", "border: 1px dashed gray; width: 300px; height: 300px;");
      divElement.addEventListener("dragover", new EventListener() {
        @Override
        public void handleEvent(Event event) {
          event.stopPropagation();
          event.preventDefault();

          DragEvent dragEvent = (DragEvent) event;
          DataTransfer dataTransfer = dragEvent.dataTransfer;
          dataTransfer.dropEffect = "copy";
        }
      });

      divElement.addEventListener("drop", new EventListener() {
        @Override
        public void handleEvent(Event event) {
          GWT.log("drop");
          event.stopPropagation();
          event.preventDefault();

          DragEvent dragEvent = (DragEvent) event;
          DataTransfer dataTransfer = dragEvent.dataTransfer;
          FileList files = dataTransfer.files;
          for (int i = 0; i < files.length; i++) {
            File file = files.item(i);

            upload(file);

            readAndUploadFile(file);
          }
        }
      });

      divElement.addEventListener("dragEnd", new EventListener() {
        @Override
        public void handleEvent(Event event) {
          GWT.log("dragEnd");
        }
      });

      anchorElement = (HTMLAnchorElement) document.createElement("a");

      FlowLayoutContainer flc1 = new FlowLayoutContainer();
      XElement flcEl = flc1.getElement();
      // flcEl.getStyle().setProperty("border", "1px solid purple");

      appendTo(flcEl, divElement);

      FlowLayoutContainer flc2 = new FlowLayoutContainer();
      XElement flcEl2 = flc1.getElement();
      // flcEl2.getStyle().setProperty("border", "1px solid purple");

      appendTo(flcEl2, anchorElement);

      progressBar = new ProgressBar();
      progressBar.setPixelSize(300, 30);
      progressBar.getCell().setProgressText("{0}% Uploaded");

      widget = new FlowLayoutContainer();
      widget.add(new HTML("Drag and Drop Image here."));
      widget.add(flc1);
      widget.add(flc2);
      widget.add(progressBar);
    }

    return widget;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinHeight(MIN_HEIGHT).setMinWidth(MIN_WIDTH).setMaxWidth(1000.0).doStandalone();
  }

  protected void readAndUploadFile(final File file) {
    final FileReader reader = new FileReader();
    reader.onloadend = new OnloadendFn() {
      @Override
      public Object onInvoke(ProgressEvent progress) {
        ResultUnionType result = reader.result;
        String url = result.asString();
        download(file, url);
        return null;
      }
    };
    reader.readAsDataURL(file);
  }

  protected void download(File file, String url) {
    imageElement.src = url;
    anchorElement.innerHTML = "Download";
    anchorElement.setAttribute("download", file.name);
    anchorElement.setAttribute("href", url);
  }

  private native void appendTo(XElement target, Element el) /*-{
    target.appendChild(el);
  }-*/;

  protected void upload(File file) {
    GWT.log("Upload file: " + file.name);

    FormData formData = new FormData();
    formData.append("file", file);

    XMLHttpRequest xhr = new XMLHttpRequest();
    xhr.upload.addEventListener("progress", new EventListener() {
      @Override
      public void handleEvent(Event ev) {
        ProgressEvent event = (ProgressEvent) ev;
        double completed = event.loaded / event.total;

        GWT.log("2 Upload completed=" + completed + " loaded=" + event.loaded + " total=" + event.total);
        progressBar.setValue(completed);
      }
    });

    //xhr.open("POST", "/FileUploadExampleServlet"); // TODO fix the server
    xhr.open("POST", "/");
    xhr.send(formData);
  }

}