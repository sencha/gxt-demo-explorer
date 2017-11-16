package com.sencha.gxt.examples.resources.client;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.BindingPropertySet;
import com.sencha.gxt.core.client.BindingPropertySet.PropertyName;

public class Utils {

  public enum Theme {
    BLUE("Blue Theme", false), GRAY("Gray Theme", false), NEPTUNE("Neptune Theme", true), TRITON("Triton Theme", true);

    private final String title;
    private final boolean touch;

    private Theme(String title, boolean touch) {
      this.title = title;
      this.touch = touch;
    }

    public String getTitle() {
      return title;
    }

    public boolean isActive() {
      ActiveTheme theme = GWT.create(ActiveTheme.class);
      switch (this) {
      case BLUE:
        return theme.isBlue();
      case GRAY:
        return theme.isGray();
      case NEPTUNE:
        return theme.isNeptune();
      case TRITON:
        return theme.isTriton();
      }
      return false;
    }

    public boolean isTouch() {
      return touch;
    }

    @Override
    public String toString() {
      return getTitle();
    }
  }

  @PropertyName("gxt.theme")
  public interface ActiveTheme extends BindingPropertySet {
    @PropertyValue(value = "gray", warn = false)
    boolean isGray();

    @PropertyValue(value = "blue", warn = false)
    boolean isBlue();

    @PropertyValue(value = "neptune", warn = false)
    boolean isNeptune();

    @PropertyValue(value = "triton", warn = false)
    boolean isTriton();
  }

}
