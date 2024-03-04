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
