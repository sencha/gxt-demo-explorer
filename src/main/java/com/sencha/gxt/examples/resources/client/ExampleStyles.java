package com.sencha.gxt.examples.resources.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public class ExampleStyles {

  private static Bundle instance = GWT.create(Bundle.class);
  private static boolean injected;
  
  static interface Bundle extends ClientBundle {

    @Source("Examples.gss")
    Styles styles();
  }
  
  public static Styles get() {
    if (!injected) {
      StyleInjector.inject(instance.styles().getText(), true);
      injected = true;
    }
    return instance.styles();
  }
  
  public interface Styles extends CssResource {

    String text();
    
    String textLarge();
    
    String paddedText();
  }

}

