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
