package com.sencha.gxt.explorer.client.extjs_data;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.explorer.client.extjs.Ext;
import com.sencha.gxt.explorer.client.model.Example.Detail;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@Detail(
  name = "Model Defintions",
  category = "Ext JS Interop",
  icon = "basicbinding",
  classes = { Ext.class },
  minHeight = ModelDefinitionExtJs.MIN_HEIGHT,
  minWidth = ModelDefinitionExtJs.MIN_WIDTH)
public class ModelDefinitionExtJs implements EntryPoint, IsWidget {

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 640;

  /**
   * Define the Ext Config properties of the object
   */
  @JsType
  public static class PersonConfig {
    @JsProperty
    public String name = "Brandon";
    @JsProperty
    int age = 1;
    @JsProperty
    public String gender = "M";
  }

  /**
   * Define the Ext class Constructor
   */
  @JsFunction
  public static interface Constructor {
    Object onConstructor(Object config);
  }

  /**
   * Define the Ext class
   */
  @JsType
  public static class PersonDefine {
    @JsProperty
    public Object config;

    @JsProperty
    public Constructor constructor;
  }

  /**
   * Define the a Ext Class as a Model with data
   */
  @JsType(isNative = true, namespace = "com.example")
  public static class Person {
    @JsProperty
    public String name;
    @JsProperty
    public int age;
  }

  @Override
  public void onModuleLoad() {
    RootPanel.get().add(asWidget());
  }

  @Override
  public Widget asWidget() {
    FlowPanel fp = new FlowPanel();

    defineUsingJsInterop(fp);
    defineUsingJSNI(fp);

    return fp;
  }

  public void defineUsingJsInterop(FlowPanel fp) {
    PersonConfig config = new PersonConfig();

    PersonDefine personDefine = new PersonDefine();
    personDefine.config = config;

    // Define the model
    // Verify it exists in: 
    // Browser Dev Tools: > Ext.data.schema.Schema.instances.default.entities
    Ext.define("com.example.Person", personDefine);

    // Instantiate the model 
    Person jacky = new Person();
    jacky.name = "Jacky";
    jacky.age = 35;

    HTML htmlJsInterop = new HTML("JsInterop: jacky.name=" + jacky.name + " jacky.age=" + jacky.age);

    fp.add(htmlJsInterop);
  }

  private void defineUsingJSNI(FlowPanel fp) {
    HTML htmlJSNI = new HTML();
    fp.add(htmlJSNI);

    defineUsingJsni(htmlJSNI.getElement());
  }

  /**
   * JSNI Example
   */
  public native void defineUsingJsni(Element htmlElement) /*-{
    var jacky = new $wnd.com.example.Person({
      name : "Jacky",
      age : 35
    });

    var str = "JSNI: jacky.name=" + jacky.name + " jacky.age=" + jacky.age;

    htmlElement.innerHTML = str;
  }-*/;

}
