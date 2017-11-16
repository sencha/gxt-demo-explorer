package com.sencha.gxt.explorer.client.animation;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Overflow;
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
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

@Detail(
    name = "Animations",
    category = "Animation",
    icon = "animation",
    preferredHeight = AnimationExample.PREFERRED_HEIGHT,
    preferredWidth = AnimationExample.PREFERRED_WIDTH
)
public class AnimationExample implements IsWidget, EntryPoint {

  protected static final int PREFERRED_HEIGHT = 320;
  protected static final int PREFERRED_WIDTH = 435;

  private ContentPanel cp;
  private FlowLayoutContainer panel;
  private ArrayList<TextButton> toggleableButtons;
  private Fx fx = new Fx();

  @Override
  public Widget asWidget() {
    if (panel == null) {
      TextButton slide = new TextButton("Slide In / Out");
      slide.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          slideFx(event);
        }
      });

      TextButton fade = new TextButton("Fade In / Out");
      fade.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          fadeFx();
        }
      });

      TextButton move = new TextButton("Move");
      move.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          moveFx();
        }
      });

      TextButton blink = new TextButton("Blink");
      blink.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          blinkFx();
        }
      });

      TextButton event = new TextButton("Event");
      event.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          eventFx();
        }
      });

      TextButton reset = new TextButton("Reset");
      reset.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent ev) {
          resetFx();
        }
      });

      ButtonBar buttonBar = new ButtonBar();
      buttonBar.add(slide);
      buttonBar.add(fade);
      buttonBar.add(move);
      buttonBar.add(blink);
      buttonBar.add(event);
      buttonBar.add(reset);

      cp = new ContentPanel();
      cp.setHeading("Animation");
      cp.setPixelSize(200, 200);
      cp.setPosition(115, 70);

      panel = new FlowLayoutContainer();
      panel.getElement().getStyle().setOverflow(Overflow.VISIBLE);
      panel.add(buttonBar);
      panel.add(cp);

      toggleableButtons = new ArrayList<TextButton>();
      toggleableButtons.add(move);
      toggleableButtons.add(blink);
      toggleableButtons.add(event);
    }

    return panel;
  }

  private void slideFx(SelectEvent event) {
    if (cp.isVisible()) {
      setEnabled(toggleableButtons, false);
      cp.getElement().<FxElement> cast().slideOut(Direction.UP, fx);
    } else {
      setEnabled(toggleableButtons, true);
      cp.getElement().<FxElement> cast().slideIn(Direction.DOWN, fx);
    }
  }

  private void fadeFx() {
    setEnabled(toggleableButtons, !cp.getElement().isVisible());
    cp.getElement().<FxElement> cast().fadeToggle(fx);
  }

  private void moveFx() {
    Rectangle bounds = cp.getElement().getBounds();
    cp.getElement().<FxElement> cast().setXY(bounds.getX() + 50, bounds.getY() + 50, fx);
  }

  private void blinkFx() {
    cp.getElement().<FxElement> cast().blink(fx, 50);
  }

  private void eventFx() {
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

  private void resetFx() {
    setEnabled(toggleableButtons, true);

    cp.setPosition(115, 70);
    cp.getElement().show();
    cp.expand();
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setPreferredHeight(PREFERRED_HEIGHT)
        .setPreferredWidth(PREFERRED_WIDTH)
        .doStandalone();
  }

  private void setEnabled(List<TextButton> buttons, boolean enabled) {
    for (TextButton button : buttons) {
      button.setEnabled(enabled);
    }
  }

}