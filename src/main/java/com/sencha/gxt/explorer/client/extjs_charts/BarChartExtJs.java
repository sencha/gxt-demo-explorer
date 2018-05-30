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
package com.sencha.gxt.explorer.client.extjs_charts;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.explorer.client.extjs.Ext;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@Detail(
  name = "Simple Bar Chart",
  category = "Ext JS Interop",
  icon = "basicbinding",
  classes = { Ext.class },
  minHeight = BarChartExtJs.MIN_HEIGHT,
  minWidth = BarChartExtJs.MIN_WIDTH)
public class BarChartExtJs implements EntryPoint, IsWidget {

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 640;

  private FlowPanel chartPanel1;
  private FlowPanel chartPanel2;

  /**
   * Define the Ext Class
   */
  @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
  public static class NameValueModelDefine {
    @JsProperty
    public Object extend;
    @JsProperty
    public Object[] fields;
    @JsProperty
    public Object constructor;
  }

  /**
   * Define the Ext class Constructor
   */
  @JsFunction
  public static interface Constructor {
    Object onConstructor(Object config);
  }

  /**
   * Verify entity was added: > Ext.data.schema.Schema.instances.default.entities
   */
  @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
  public static class NameValueModel {
    @JsProperty
    public String name;
    @JsProperty
    public Double value;
  }

  @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
  public static class Axes {
    @JsProperty
    String type;
    @JsProperty
    String position;
    @JsProperty
    Object title;
    @JsProperty
    String fields;
  }

  @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
  public static class Series {
    @JsProperty
    String type;
    @JsProperty
    Object subStyle;
    @JsProperty
    String xField;
    @JsProperty
    String yField;
  }

  @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
  public static class ChartConfig {
    @JsProperty
    String xtype;
    @JsProperty
    Object renderTo;
    @JsProperty
    int width;
    @JsProperty
    int height;
    @JsProperty
    Object store;
    @JsProperty
    Object[] axes;
    @JsProperty
    Object series;
  }

  @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
  public static class Title {
    @JsProperty
    String text;
    @JsProperty
    int fontSize;
  }

  @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
  public static class SubStyle {
    @JsProperty
    String[] fill;
    @JsProperty
    String stroke;
  }

  @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
  public static class Field {
    @JsProperty
    String name;
    @JsProperty
    String type;
  }

  @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
  public static class StoreConfig {
    @JsProperty
    Object model;
    @JsProperty
    Object fields;
    @JsProperty
    Object[] data;
    @JsProperty
    Object autoLoad;
    @JsProperty
    Object proxy;
  }

  @JsType
  public static class Proxy {
    @JsProperty
    Object type;
    @JsProperty
    Object url;
    @JsProperty
    Object reader;
  }

  @JsType
  public static class Reader {
    @JsProperty
    Object type;
    @JsProperty
    Object rootProperty;
  }

  @Override
  public Widget asWidget() {
    chartPanel1 = new FlowPanel();
    chartPanel2 = new FlowPanel();

    drawChart1JsInterop();
    drawChart2JSNI(chartPanel2.getElement());

    CssFloatLayoutContainer container = new CssFloatLayoutContainer();

    // First Chart
    container.add(new HTML("JsInterop Example"), new CssFloatData(1, new Margins(10, 10, 4, 10)));
    container.add(chartPanel1, new CssFloatData(1, new Margins(0, 10, 10, 10)));

    // Second Chart
    container.add(new HTML("JSNI Example"), new CssFloatData(1, new Margins(10, 10, 4, 10)));
    container.add(chartPanel2, new CssFloatData(1, new Margins(0, 10, 10, 10)));

    return container;
  }

  @Override
  public void onModuleLoad() {
    RootPanel.get().add(asWidget());
  }

  private void drawChart1JsInterop() {
    Field field0 = new Field();
    field0.name = "name";
    field0.type = "string";

    Field field1 = new Field();
    field1.name = "value";
    field1.type = "float";

    Field[] fields = new Field[2];
    fields[0] = field0;
    fields[1] = field1;

    NameValueModelDefine nameModelDefinition = new NameValueModelDefine();
    nameModelDefinition.extend = "Ext.data.Model";
    nameModelDefinition.fields = fields;

    // To verify it works see that it's in this object: > Ext.data.schema.Schema.instances.default.entities
    Ext.define("com.example.NameValueModel", nameModelDefinition);

    NameValueModel nvm0 = new NameValueModel();
    nvm0.name = "A";
    nvm0.value = 1.01;
    NameValueModel nvm1 = new NameValueModel();
    nvm1.name = "B";
    nvm1.value = 2.02;
    NameValueModel nvm2 = new NameValueModel();
    nvm2.name = "C";
    nvm2.value = 3.03;
    NameValueModel nvm3 = new NameValueModel();
    nvm3.name = "D";
    nvm3.value = 4.04;
    NameValueModel nvm4 = new NameValueModel();
    nvm4.name = "E";
    nvm4.value = 5.05;

    NameValueModel[] datas = new NameValueModel[4];
    datas[0] = nvm0;
    datas[1] = nvm1;
    datas[2] = nvm2;
    datas[3] = nvm3;
    datas[4] = nvm4;

    StoreConfig storeConfig = new StoreConfig();
    storeConfig.model = "com.example.NameValueModel"; // Same as JsType above
    storeConfig.data = datas;

    Object store = Ext.create("Ext.data.Store", storeConfig);
    Title title1 = new Title();
    title1.text = "Values";
    title1.fontSize = 15;

    Title title2 = new Title();
    title2.text = "Chart 1";
    title2.fontSize = 15;

    Axes axes1 = new Axes();
    axes1.type = "numeric";
    axes1.position = "left";
    axes1.title = title1;
    axes1.fields = "value";

    Axes axes2 = new Axes();
    axes2.type = "category";
    axes2.position = "bottom";
    axes2.title = title2;
    axes2.fields = "name";

    Axes[] axes = new Axes[2];
    axes[0] = axes1;
    axes[1] = axes2;

    String[] fills = new String[1];
    fills[0] = "#388FAD";

    SubStyle subStyle = new SubStyle();
    subStyle.fill = fills;
    subStyle.stroke = "#1F6D91";

    Series series = new Series();
    series.type = "bar";
    series.subStyle = subStyle;
    series.xField = "name";
    series.yField = "value";

    ChartConfig chartConfig = new ChartConfig();
    chartConfig.xtype = "cartesian";
    chartConfig.renderTo = chartPanel1.getElement();
    chartConfig.width = 500;
    chartConfig.height = 300;
    chartConfig.store = store;
    chartConfig.axes = axes;
    chartConfig.series = series;

    Ext.create(chartConfig);

    // Debug the chart configuration
    log(chartConfig);
  }

  /**
   * This is an easy way to inspect the chart object configuration.
   */
  private native void log(ChartConfig chartConfig) /*-{
    console.log("chartConfig=", chartConfig);
  }-*/;

  /**
   * JSNI Example
   */
  private native void drawChart2JSNI(Element renderToElement) /*-{
    $wnd.Ext.create({
      xtype : 'cartesian',
      renderTo : renderToElement,
      width : 500,
      height : 300,
      store : {
        fields : [ 'name', 'value' ],
        data : [ {
          name : 'A',
          value : 1.01
        }, {
          name : 'B',
          value : 2.02
        }, {
          name : 'C',
          value : 3.03
        }, {
          name : 'D',
          value : 4.04
        }, {
          name : 'E',
          value : 5.05
        } ]
      },

      axes : [ {
        type : 'numeric',
        position : 'left',
        title : {
          text : 'Values',
          fontSize : 15
        },
        fields : 'value'
      }, {
        type : 'category',
        position : 'bottom',
        title : {
          text : 'Chart 2',
          fontSize : 15
        },
        fields : 'name'
      } ],

      series : {
        type : 'bar',
        subStyle : {
          fill : [ '#388FAD' ],
          stroke : '#1F6D91'
        },
        xField : 'name',
        yField : 'value'
      }
    });
  }-*/;

  /**
   * Test the Ext JS model definition Verify it exists in: 
   * Browser Dev Tools: > Ext.data.schema.Schema.instances.default.entities
   */
  private native void testDefineModel() /*-{
    $wnd.Ext.define('com.example.NameValueModel', {
      extend : 'Ext.data.Model',
      fields : [ {
        name : 'name',
        mapping : 'name'
      }, {
        name : 'value',
        mapping : 'value'
      } ]
    });
  }-*/;

}
