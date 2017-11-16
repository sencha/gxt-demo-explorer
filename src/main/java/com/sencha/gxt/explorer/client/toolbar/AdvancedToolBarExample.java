package com.sencha.gxt.explorer.client.toolbar;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonArrowAlign;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonScale;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.core.client.gestures.ScrollGestureRecognizer;
import com.sencha.gxt.core.client.gestures.ScrollGestureRecognizer.ScrollDirection;
import com.sencha.gxt.core.client.gestures.TouchEventToGestureAdapter;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.images.ExampleImages;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.ButtonGroup;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
    name = "Advanced Tool Bar",
    category = "Tool Bar & Menu",
    icon = "advancedtoolbar",
    preferredHeight = AdvancedToolBarExample.PREFERRED_HEIGHT,
    preferredWidth = AdvancedToolBarExample.PREFERRED_WIDTH,
    classes = { TestData.class }
)
public class AdvancedToolBarExample implements IsWidget, EntryPoint {

  class SamplePanel extends ContentPanel {
    private VerticalLayoutContainer vlc;
    private ToolBar toolBar;

    public SamplePanel() {
      toolBar = new ToolBar();
      toolBar.setSpacing(2);

      HTML text = new HTML(TestData.DUMMY_TEXT_LONG);
      text.getElement().getStyle().setOverflowY(Overflow.AUTO);
      new TouchEventToGestureAdapter(text, new ScrollGestureRecognizer(text.getElement(), ScrollDirection.VERTICAL));

      vlc = new VerticalLayoutContainer();
      vlc.add(toolBar, new VerticalLayoutData(1, -1));
      vlc.add(text, new VerticalLayoutData(1, 1));

      setPixelSize(500, 250);
      add(vlc);
    }

    public ToolBar getToolBar() {
      return toolBar;
    }
  }

  protected static final int PREFERRED_HEIGHT = 1060;
  protected static final int PREFERRED_WIDTH = 500;

  private FlowLayoutContainer flowLayout;

  @Override
  public Widget asWidget() {
    if (flowLayout == null) {
      flowLayout = new FlowLayoutContainer();
      flowLayout.add(createStandard(), new MarginData(0, 0, 20, 0));
      flowLayout.add(createMulti(), new MarginData(0, 0, 20, 0));
      flowLayout.add(createMulti2(), new MarginData(0, 0, 20, 0));
      flowLayout.add(createMixed());
    }

    return flowLayout;
  }

  private ContentPanel createStandard() {
    Menu menu1 = new Menu();
    menu1.add(new MenuItem("Ribbons are cool"));

    Menu menu2 = new Menu();
    menu2.add(new MenuItem("Copy me"));

    Menu menu3 = new Menu();
    menu3.add(new MenuItem("Ribbons are cool"));

    TextButton btn1 = new TextButton("Cool", ExampleImages.INSTANCE.add16());
    TextButton btn2 = new TextButton("Cut", ExampleImages.INSTANCE.add16());
    TextButton btn3 = new TextButton("Copy", ExampleImages.INSTANCE.add16());
    TextButton btn4 = new TextButton("Paste", ExampleImages.INSTANCE.add16());

    btn1.setMenu(menu1);
    btn2.setMenu(menu2);
    btn3.setMenu(menu3);

    ToggleButton toggleBtn = new ToggleButton("Format");
    toggleBtn.setValue(true);

    SamplePanel panel = new SamplePanel();
    panel.setHeading("Advanced Tool Bar — Standard");
    panel.getToolBar().add(btn1);
    panel.getToolBar().add(btn2);
    panel.getToolBar().add(btn3);
    panel.getToolBar().add(btn4);
    panel.getToolBar().add(toggleBtn);

    return panel;
  }

  private ContentPanel createMulti() {
    Menu menu2 = new Menu();
    menu2.add(new MenuItem("Copy me"));

    Menu menu6 = new Menu();
    menu6.add(new MenuItem("Copy me"));

    TextButton btn1 = new TextButton("Cool", ExampleImages.INSTANCE.add16());
    TextButton btn2 = new TextButton("Cut", ExampleImages.INSTANCE.add16());
    TextButton btn3 = new TextButton("Copy", ExampleImages.INSTANCE.add16());
    TextButton btn4 = new TextButton("Paste", ExampleImages.INSTANCE.add16());
    TextButton btn5 = new TextButton("Cool", ExampleImages.INSTANCE.add16());
    TextButton btn6 = new TextButton("Cut", ExampleImages.INSTANCE.add16());
    TextButton btn7 = new TextButton("Copy", ExampleImages.INSTANCE.add16());
    TextButton btn8 = new TextButton("Paste", ExampleImages.INSTANCE.add16());

    btn2.setMenu(menu2);
    btn6.setMenu(menu6);

    FlexTable table1 = new FlexTable();
    table1.setWidget(0, 0, btn1);
    table1.setWidget(0, 1, btn2);
    table1.setWidget(1, 0, btn3);
    table1.setWidget(1, 1, btn4);

    FlexTable table2 = new FlexTable();
    table2.setWidget(0, 0, btn5);
    table2.setWidget(0, 1, btn6);
    table2.setWidget(1, 0, btn7);
    table2.setWidget(1, 1, btn8);

    ButtonGroup group1 = new ButtonGroup();
    group1.setHeading("Clipboard");
    group1.add(table1);

    ButtonGroup group2 = new ButtonGroup();
    group2.setHeading("Other Bogus Actions");
    group2.add(table2);

    SamplePanel panel = new SamplePanel();
    panel.setHeading("Advanced Tool Bar — Multi Columns");
    panel.getToolBar().add(group1);
    panel.getToolBar().add(group2);

    return panel;
  }

  private ContentPanel createMulti2() {
    Menu menu2 = new Menu();
    menu2.add(new MenuItem("Copy me"));

    Menu menu6 = new Menu();
    menu6.add(new MenuItem("Copy me"));

    TextButton btn1 = new TextButton("Cool", ExampleImages.INSTANCE.add16());
    TextButton btn2 = new TextButton("Cut", ExampleImages.INSTANCE.add16());
    TextButton btn3 = new TextButton("Copy", ExampleImages.INSTANCE.add16());
    TextButton btn4 = new TextButton("Paste", ExampleImages.INSTANCE.add16());
    TextButton btn5 = new TextButton("Cool", ExampleImages.INSTANCE.add16());
    TextButton btn6 = new TextButton("Copy", ExampleImages.INSTANCE.add16());
    TextButton btn7 = new TextButton("Paste", ExampleImages.INSTANCE.add16());
    TextButton btn8 = new TextButton("Cut", ExampleImages.INSTANCE.add16());

    btn2.setMenu(menu2);
    btn6.setMenu(menu6);

    FlexTable table1 = new FlexTable();
    table1.setWidget(0, 0, btn5);
    table1.setWidget(0, 1, btn6);
    table1.setWidget(1, 0, btn7);
    table1.setWidget(1, 1, btn8);

    FlexTable table2 = new FlexTable();
    table2.setWidget(0, 0, btn1);
    table2.setWidget(0, 1, btn2);
    table2.setWidget(1, 0, btn3);
    table2.setWidget(1, 1, btn4);

    ButtonGroup group1 = new ButtonGroup();
    group1.setHeading("Clipboard");
    group1.setHeaderVisible(false);
    group1.add(table1);

    ButtonGroup group2 = new ButtonGroup();
    group2.setHeading("Other Bogus Actions");
    group2.setHeaderVisible(false);
    group2.add(table2);

    SamplePanel panel = new SamplePanel();
    panel.setHeading("Advanced Tool Bar — Multi Columns No Title");
    panel.getToolBar().add(group1);
    panel.getToolBar().add(group2);

    return panel;
  }

  private ContentPanel createMixed() {
    SamplePanel panel = new SamplePanel();
    panel.setHeading("Advanced Tool Bar — Mix and match icon sizes");

    for (int i = 0; i < 2; i++) {
      Menu menu3 = new Menu();
      menu3.add(new MenuItem("Copy me"));

      TextButton btn1 = new TextButton("Paste", ExampleImages.INSTANCE.add32());
      TextButton btn2 = new TextButton("Format", ExampleImages.INSTANCE.add32());
      TextButton btn3 = new TextButton("Copy", ExampleImages.INSTANCE.add16());
      TextButton btn4 = new TextButton("Cut", ExampleImages.INSTANCE.add16());
      TextButton btn5 = new TextButton("Paste", ExampleImages.INSTANCE.add16());

      btn1.setScale(ButtonScale.LARGE);
      btn1.setIconAlign(IconAlign.TOP);
      btn1.setArrowAlign(ButtonArrowAlign.BOTTOM);

      btn2.setScale(ButtonScale.LARGE);
      btn2.setIconAlign(IconAlign.TOP);
      btn2.setArrowAlign(ButtonArrowAlign.BOTTOM);

      btn3.setMenu(menu3);

      FlexTable table = new FlexTable();
      table.setWidget(0, 0, btn1);
      table.getFlexCellFormatter().setRowSpan(0, 0, 3);
      table.setWidget(0, 1, btn2);
      table.getFlexCellFormatter().setRowSpan(0, 1, 3);
      table.setWidget(0, 2, btn3);
      table.setWidget(1, 2, btn4);
      table.setWidget(2, 2, btn5);

      cleanCells(table.getElement());

      ButtonGroup group = new ButtonGroup();
      group.setHeading("Clipboard");
      group.add(table);

      panel.getToolBar().add(group);
    }

    return panel;
  }

  private void cleanCells(Element elem) {
    NodeList<Element> tds = elem.<XElement> cast().select("td");
    for (int i = 0; i < tds.getLength(); i++) {
      Element td = tds.getItem(i);

      if (!td.hasChildNodes() && td.getClassName().equals("")) {
        td.removeFromParent();
      }
    }
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setPreferredHeight(PREFERRED_HEIGHT)
        .setPreferredWidth(PREFERRED_WIDTH)
        .doStandalone();
  }

}
