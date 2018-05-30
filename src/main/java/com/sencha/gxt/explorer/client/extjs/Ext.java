package com.sencha.gxt.explorer.client.extjs;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, name = "Ext", namespace = JsPackage.GLOBAL)
public abstract class Ext {

  @JsProperty(name = "isChrome")
  public static native boolean isChrome();

  @JsMethod()
  public static native void define(String className, java.lang.Object data);
    
  @JsMethod()
  public static native java.lang.Object create(java.lang.Object config);
  
  @JsMethod()
  public static native java.lang.Object create(String className, java.lang.Object config);
  
}
