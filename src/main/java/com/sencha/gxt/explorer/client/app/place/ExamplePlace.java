/**
 * Sencha GXT 1.0.0-SNAPSHOT - Sencha for GWT
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
package com.sencha.gxt.explorer.client.app.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ExamplePlace extends Place {

  public static class Tokenizer implements PlaceTokenizer<ExamplePlace> {
    @Override
    public ExamplePlace getPlace(String token) {
      return new ExamplePlace(token);
    }

    @Override
    public String getToken(ExamplePlace place) {
      return place.getExampleId();
    }
  }

  private String exampleId;

  public ExamplePlace(String exampleId) {
    this.exampleId = exampleId;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ExamplePlace) {
      return exampleId.equals(((ExamplePlace) obj).exampleId);
    }
    return false;
  }

  public String getExampleId() {
    return exampleId;
  }

  @Override
  public int hashCode() {
    return exampleId.hashCode();
  }

}
