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
package com.sencha.gxt.examples.resources.client.images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ExampleImages extends ClientBundle {

  public ExampleImages INSTANCE = GWT.create(ExampleImages.class);

  @Source("add16.gif")
  ImageResource add16();

  @Source("add24.gif")
  ImageResource add24();

  @Source("add32.gif")
  ImageResource add32();
  
  @Source("table.png")
  ImageResource table();

  @Source("application_side_list.png")
  ImageResource side_list();
  
  @Source("list.gif")
  ImageResource list();

  @Source("application_form.png")
  ImageResource form();

  @Source("connect.png")
  ImageResource connect();

  @Source("user_add.png")
  ImageResource user_add();

  @Source("user_delete.png")
  ImageResource user_delete();

  @Source("accordion.gif")
  ImageResource accordion();

  @Source("add.gif")
  ImageResource add();

  @Source("delete.gif")
  ImageResource delete();

  @Source("calendar.gif")
  ImageResource calendar();

  @Source("calendar_triton.png")
  ImageResource calendarTriton();

  @Source("menu-show.gif")
  ImageResource menu_show();

  @Source("list-items.gif")
  ImageResource list_items();

  @Source("album.gif")
  ImageResource album();

  @Source("text.png")
  ImageResource text();

  @Source("plugin.png")
  ImageResource plugin();
  
  @Source("music.png")
  ImageResource music();
  
  
  @Source("user.png")
  ImageResource user();
  
  @Source("user_kid.png")
  ImageResource userKid();
  
  @Source("user_female.png")
  ImageResource userFemale();
  
  @Source("css.png")
  ImageResource css();
  
  @Source("java.png")
  ImageResource java();
  
  @Source("text.png")
  ImageResource json();
  
  @Source("html.png")
  ImageResource html();
  
  @Source("xml.png")
  ImageResource xml();
  
  @Source("folder.png")
  ImageResource folder();
}
