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
package com.sencha.gxt.explorer.client.extjs;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.explorer.client.model.Example.Detail;

@Detail(
  name = "Basic Form",
  category = "ExtJS",
  icon = "basicbinding",
  classes = { Ext.class },
  minHeight = BasicFormExtJs.MIN_HEIGHT,
  minWidth = BasicFormExtJs.MIN_WIDTH)
public class BasicFormExtJs implements EntryPoint, IsWidget {

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 640;

  private FlowPanel widget;

  @Override
  public Widget asWidget() {
    widget = new FlowPanel();
    
    // Add a Ext JS form to GXT app
    createExtJsForm(widget.getElement());
    
    return widget;
  }

  @Override
  public void onModuleLoad() {
    RootPanel.get().add(asWidget());
  }

  // https://examples.sencha.com/extjs/6.7.0/examples/kitchensink/?modern#form-login
  private native void createExtJsForm(Element renderToElement) /*-{
    $wnd.Ext.define('KitchenSink.view.forms.LoginController', {
      extend : 'Ext.app.ViewController',
      alias : 'controller.form-login',
      onLogin : function() {
        var form = this.getView();

        if (form.validate()) {
          $wnd.Ext.Msg.alert('Login Success', 'You have been logged in!');
        } else {
          $wnd.Ext.Msg.alert('Login Failure',
              'The username/password provided is invalid.');
        }
      }
    });

    $wnd.Ext.define('KitchenSink.view.forms.Login', {
      extend : 'Ext.form.Panel',
      xtype : 'form-login',
      controller : 'form-login',
      title : 'Login',

      bodyPadding : 20,
      width : 320,
      autoSize : true,

      items : [ {
        xtype : 'textfield',
        allowBlank : false,
        required : true,
        label : 'User ID',
        name : 'user',
        placeholder : 'user id'
      }, {
        xtype : 'passwordfield',
        allowBlank : false,
        required : true,
        label : 'Password',
        name : 'pass',
        placeholder : 'password'
      }, {
        xtype : 'checkbox',
        boxLabel : 'Remember me',
        name : 'remember'
      } ],

      buttons : [ {
        text : 'Login',
        handler : 'onLogin'
      } ]
    });

    $wnd.Ext.create({
      xtype : 'form-login',
      renderTo : renderToElement
    //width : '100%',
    //height : '100%',
    });

  }-*/;

}
