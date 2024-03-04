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
package com.sencha.gxt.explorer.client.thumbs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;

public interface ExampleThumbs extends ClientBundle {

  public static ExampleThumbs THUMBS = GWT.create(ExampleThumbs.class);

  @Source("Accordion-Layout.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource accordionlayout();

  @Source("Accordion-Layout-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource accordionlayoutuibinder();

  @Source("Accordion-Window.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource accordionwindow();

  @Source("Advanced-Tabs.png")  // FIXME: DSB: NEED ICON FOR ADVANCED BOTTOM TABS
  @ImageOptions(width = 50, height = 50)
  ImageResource advancedbottomtabs();

  @Source("Advanced-Combo-Box.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource advancedcombobox();

  @Source("Advanced-Forms.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource advancedforms();

  @Source("Advanced-ListView.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource advancedlistview();

  @Source("Advanced-Tabs.png")  // FIXME: DSB: ICON SHOULD SHOW ADVANCED TABS ON TOP
  @ImageOptions(width = 50, height = 50)
  ImageResource advancedtabs();

  @Source("Advanced-Toolbar.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource advancedtoolbar();

  @Source("Aggregation-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource aggregationgrid();

  @Source("Fx.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource animation();

  @Source("Fx-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource animationuibinder();

  @Source("Area-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource areachart();

  @Source("Area-Renderer-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource arearendererchart();

  @Source("Async-Json-Tree.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource asyncjsontree();

  @Source("Async-Tree.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource asynctree();

  @Source("Async-TreeGrid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource asynctreegrid();

  @Source("Bar-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource barchart();

  @Source("Bar-Renderer-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource barrendererchart();

  @Source("Basic-Binding.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource basicbinding();

  @Source("Basic-Binding-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource basicbindinguibinder();

  @Source("Basic-DND.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource basicdnd();

  @Source("Basic-Draw.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource basicdraw();

  @Source("Basic-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource basicgrid();

  @Source("Basic-Grid-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource basicgriduibinder();

  @Source("Basic-Simple-Grid-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource basicsimplegriduibinder();

  @Source("Basic-Tabs.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource basictabs();

  @Source("Basic-Tabs-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource basictabsuibinder();

  @Source("Basic-Toolbar.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource basictoolbar();

  @Source("Basic-Toolbar-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource basictoolbaruibinder();

  @Source("Basic-Tree.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource basictree();

  @Source("Basic-TreeGrid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource basictreegrid();

  @Source("Basic-Tree-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource basictreeuibinder();

  @Source("BorderLayout.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource borderlayout();

  @Source("Border-Layout-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource borderlayoutuibinder();

  @Source("BorderLayout-(UiBinder-Dynamic-Attribute).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource borderlayoutuibinderdynamicattribute();

  @Source("Button-Aligning.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource buttonaligning();

  @Source("Button-Aligning-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource buttonaligninguibinder();

  @Source("Buttons.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource buttons();

  @Source("Card-Layout.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource cardlayout();

  @Source("Card-Layout-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource cardlayoutuibinder();

  @Source("Cell-Action-Tree.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource cellactiontree();

  @Source("Cell-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource cellgrid();

  @Source("Center-Layout.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource centerlayout();

  @Source("Center-Layout-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource centerlayoutuibinder();

  @Source("Checkbox-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource checkboxgrid();

  @Source("CheckBox-Tree.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource checkboxtree();

  @Source("Column-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource columnchart();

  @Source("Column-Renderer-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource columnrendererchart();

  @Source("Combo-Box.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource combobox();

  @Source("Context-Menu-Tree.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource contextmenutree();

  @Source("Customized-Tree.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource customizedtree();

  @Source("Dashboard.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource dashboard();

  @Source("DateCell-ListView.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource datecelllistview();

  @Source("DatePicker.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource datepicker();

  @Source("DatePicker-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource datepickeruibinder();

  @Source("Dialog.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource dialog();

  @Source("Draggable.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource draggable();

  @Source("Draggable-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource draggableuibinder();

  @Source("Dual-List-Field.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource duallistfield();

  @Source("DualListField-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource duallistfielduibinder();

  @Source("Dynamic-Line-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource dynamiclinechart();

  @Source("Editable-TreeGrid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource editortreegrid();

  @Source("Fast-Tree.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource fasttree();

  @Source("File-Upload.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource fileupload();

  @Source("Filter-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource filterchart();

  @Source("Filter-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource filtergrid();

  @Source("Filter-Tree.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource filtertree();

  @Source("Forms-Example.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource formsexample();

  @Source("Forms-Example-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource formsexampleuibinder();

  @Source("Gauge-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource gaugechart();

  @Source("Grid-Binding.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource gridbinding();

  @Source("Grid-to-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource gridtogrid();

  @Source("Grouped-Bar-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource groupedbarchart();

  @Source("Grouping-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource groupinggrid();

  @Source("HBoxLayout.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource hboxlayout();

  @Source("HBoxLayout-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource hboxlayoutuibinder();

  @Source("Hello-World.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource helloworld();

  @Source("Hello-World-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource helloworlduibinder();

  @Source("Horizontal-Layouot.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource horizontallayout();

  @Source("Horizontal-Layout-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource horizontallayoutuibinder();

  @Source("HtmlLayout.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource htmllayoutcontainer();

  @Source("Image-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource imagechart();

  @Source("Info.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource notification();

  @Source("Inline-Editable-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource inlineeditablegrid();

  @Source("Json-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource jsongrid();

  @Source("Line-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource linechart();

  @Source("Line-Gap-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource linegapchart();

  @Source("List-Property.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource listproperty();

  @Source("List-to-List.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource listtolist();

  @Source("ListView.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource listview();

  @Source("List-View-Binding.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource listviewbinding();

  @Source("Live-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource livechart();

  @Source("Live-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource livegrid();

  @Source("Live-Group-Summary.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource livegroupsummary();

  @Source("LocalStorage-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource localstoragegrid();

  @Source("Logos.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource logos();

  @Source("Menu-Bar.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource menubar();

  @Source("Menu-Bar-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource menubaruibinder();

  @Source("Message-Box.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource messagebox();

  @Source("Mixed-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource mixedchart();

  @Source("Overflow-Toolbar.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource overflowtoolbar();

  @Source("Overflow-Toolbar-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource overflowtoolbaruibinder();

  @Source("Overview.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource overview();

  @Source("Paging-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource paginggrid();

  @Source("Paging-Grid-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource paginggriduibinder();

  @Source("Pie-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource piechart();

  @Source("Pie-Renderer-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource pierendererchart();

  @Source("Portal-Layout.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource portallayout();

  @Source("Portal-Layout-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource portallayoutuibinder();

  @Source("Radar-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource radarchart();

  @Source("Reordering-Tree.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource reorderingtree();

  @Source("Reordering-TreeGrid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource reorderingtreegrid();

  @Source("Request-Factory-Binding.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource requestfactorybinding();

  @Source("RequestFactory-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource requestfactorygrid();

  @Source("Resizable.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource resizable();

  @Source("Rotate-Text.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource rotatetext();

  @Source("Row-Editable-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource roweditablegrid();

  @Source("RowExpander-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource rowexpandergrid();

  @Source("Row-Numberer-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource rownumberergrid();

  @Source("Scatter-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource scatterchart();

  @Source("Scatter-Renderer-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource scatterrendererchart();

  @Source("Slider.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource slider();

  @Source("Slider-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource slideruibinder();

  @Source("Stacked-Bar-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource stackedbarchart();

  @Source("Status-Toolbar.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource statustoolbar();

  @Source("Status-Toolbar-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource statustoolbaruibinder();

  @Source("Styled-Combo-Box.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource styledcombobox();

  @Source("Templates.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource templates();

  @Source("Themed-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource themedchart();

  @Source("Converter-Example.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource bindingconverter();

  @Source("Buttons.png")  // FIXME: DSB: NEED ICON FOR TOOL BUTTONS EXAMPLE
  @ImageOptions(width = 50, height = 50)
  ImageResource toolbuttons();

  @Source("Tooltip-Chart.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource tooltipchart();

  @Source("ToolTips.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource tooltips();

  @Source("ToolTips-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource tooltipsuibinder();

  @Source("TreeGrid-to-TreeGrid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource treegridtotreegrid();

  @Source("Tree-to-Tree.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource treetotree();

  @Source("VBox-Layout.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource vboxlayout();

  @Source("VBox-Layout-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource vboxlayoutuibinder();

  @Source("Vertical-Layout.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource verticallayout();

  @Source("Vertical-Layout-(UiBinder).png")
  @ImageOptions(width = 50, height = 50)
  ImageResource verticallayoutuibinder();

  @Source("Xml-Grid.png")
  @ImageOptions(width = 50, height = 50)
  ImageResource xmlgrid();
}
