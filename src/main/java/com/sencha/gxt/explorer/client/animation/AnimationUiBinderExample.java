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
package com.sencha.gxt.explorer.client.animation;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.Direction;
import com.sencha.gxt.core.client.util.Rectangle;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.fx.client.FxElement;
import com.sencha.gxt.fx.client.animation.AfterAnimateEvent;
import com.sencha.gxt.fx.client.animation.AfterAnimateEvent.AfterAnimateHandler;
import com.sencha.gxt.fx.client.animation.Fx;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

@Detail(
    name = "Animation (UiBinder)",
    category = "Animation",
    icon = "animationuibinder",
    files = "AnimationUiBinderExample.ui.xml",
    preferredHeight = AnimationUiBinderExample.PREFERRED_HEIGHT,
    preferredWidth = AnimationUiBinderExample.PREFERRED_WIDTH
)
public class AnimationUiBinderExample implements IsWidget, EntryPoint {

  interface MyUiBinder extends UiBinder<Widget, AnimationUiBinderExample> {
  }

  protected static final int PREFERRED_HEIGHT = 320;
  protected static final int PREFERRED_WIDTH = 435;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  @UiField
  ContentPanel cp;
  @UiField
  TextButton move;
  @UiField
  TextButton blink;
  @UiField
  TextButton event;

  private Widget widget;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      widget = uiBinder.createAndBindUi(this);
      widget.getElement().getStyle().setOverflow(Overflow.VISIBLE);
    }

    return widget;
  }

  @UiHandler("slide")
  public void onSlide(SelectEvent event) {
    if (cp.isVisible()) {
      cp.getElement().<FxElement> cast().slideOut(Direction.UP);
      setButtonsEnabled(false);
    } else {
      cp.getElement().<FxElement> cast().slideIn(Direction.DOWN);
      setButtonsEnabled(true);
    }
  }

  @UiHandler("fade")
  public void onFade(SelectEvent event) {
    setButtonsEnabled(!cp.isVisible());
    cp.getElement().<FxElement> cast().fadeToggle();
  }

  @UiHandler("move")
  public void onMove(SelectEvent event) {
    Rectangle bounds = cp.getElement().getBounds();
    cp.getElement().<FxElement> cast().setXY(bounds.getX() + 50, bounds.getY() + 50, new Fx());
  }

  @UiHandler("blink")
  public void onBlink(SelectEvent event) {
    cp.getElement().<FxElement> cast().blink();
  }

  @UiHandler("event")
  public void onEvent(SelectEvent event) {
    Fx fx = new Fx();
    fx.addAfterAnimateHandler(new AfterAnimateHandler() {
      @Override
      public void onAfterAnimate(AfterAnimateEvent event) {
        AlertMessageBox mb = new AlertMessageBox("Animate Event Handler", "AfterAnimateEvent was received...");
        mb.setIcon(AlertMessageBox.ICONS.info());
        mb.show();
      }
    });
    cp.getElement().<FxElement> cast().blink(fx, 50);
  }

  @UiHandler("reset")
  public void onReset(SelectEvent ev) {
    cp.setPosition(115, 70);
    cp.expand();
    cp.getElement().show();
    setButtonsEnabled(true);
  }

  private void setButtonsEnabled(boolean enabled) {
    move.setEnabled(enabled);
    blink.setEnabled(enabled);
    event.setEnabled(enabled);
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setPreferredHeight(PREFERRED_HEIGHT)
        .setPreferredWidth(PREFERRED_WIDTH)
        .doStandalone();
  }

}
