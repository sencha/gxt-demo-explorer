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
package com.sencha.gxt.explorer.client.extjs_charts;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.explorer.client.extjs.Ext;
import com.sencha.gxt.explorer.client.model.Example.Detail;

@Detail(
  name = "Simple Bar Chart",
  category = "ExtJS",
  icon = "basicbinding",
  classes = { Ext.class },
  minHeight = BarChartExtJs.MIN_HEIGHT,
  minWidth = BarChartExtJs.MIN_WIDTH)
public class BarChartExtJs implements EntryPoint, IsWidget {

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 640;

  private FlowPanel widget;
  

  @Override
  public Widget asWidget() {
    widget = new FlowPanel();

    drawChar(widget.getElement());

    return widget;
  }

  @Override
  public void onModuleLoad() {
    RootPanel.get().add(asWidget());
  }

  private native void drawChar(Element renderToElement) /*-{
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
