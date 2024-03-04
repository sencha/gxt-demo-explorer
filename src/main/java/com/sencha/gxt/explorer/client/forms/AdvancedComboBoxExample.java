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
package com.sencha.gxt.explorer.client.forms;

import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBean.PropertyName;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.data.client.loader.JsoReader;
import com.sencha.gxt.data.client.loader.ScriptTagProxy;
import com.sencha.gxt.data.client.writer.UrlEncodingWriter;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.loader.BeforeLoadEvent;
import com.sencha.gxt.data.shared.loader.BeforeLoadEvent.BeforeLoadHandler;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

@Detail(
    name = "Combo Box Advanced",
    category = "Forms",
    icon = "advancedcombobox",
    files = {"AdvancedComboBox.gss"},
    preferredHeight = AdvancedComboBoxExample.PREFERRED_HEIGHT,
    preferredWidth = AdvancedComboBoxExample.PREFERRED_WIDTH
)
public class AdvancedComboBoxExample implements IsWidget, EntryPoint {

  interface Bundle extends ClientBundle {
    @Source("AdvancedComboBox.gss")
    ExampleStyle css();
  }

  interface ExampleStyle extends CssResource {
    String searchItem();
  }

  interface ExampleTemplate extends XTemplates {
    @XTemplate("<div class='{style.searchItem}'><h3><span>{post.date:date(\"M/d/yyyy\")}<br />by {post.author}</span>{post.title}</h3>{post.excerpt}</div>")
    SafeHtml render(Forum post, ExampleStyle style);
  }

  interface TestAutoBeanFactory extends AutoBeanFactory {
    static TestAutoBeanFactory instance = GWT.create(TestAutoBeanFactory.class);

    AutoBean<ForumCollection> dataCollection();

    AutoBean<ForumListLoadResult> dataLoadResult();

    AutoBean<ForumLoadConfig> loadConfig();
  }

  public interface Forum {
    @PropertyName("topic_title")
    public String getTitle();

    @PropertyName("topic_id")
    public String getTopicId();

    public String getAuthor();

    @PropertyName("forumid")
    public String getForumId();

    @PropertyName("post_text")
    public String getExcerpt();

    @PropertyName("post_id")
    public String getPostId();

    @PropertyName("post_time")
    public Date getDate();
  }

  interface ForumCollection {
    String getTotalCount();

    List<Forum> getTopics();
  }

  interface ForumLoadConfig extends PagingLoadConfig {
    String getQuery();

    void setQuery(String query);

    @Override
    @PropertyName("start")
    public int getOffset();

    @Override
    @PropertyName("start")
    public void setOffset(int offset);
  }

  interface ForumListLoadResult extends PagingLoadResult<Forum> {
    void setData(List<Forum> data);

    @Override
    @PropertyName("start")
    public int getOffset();

    @Override
    @PropertyName("start")
    public void setOffset(int offset);
  }

  interface ForumProperties extends PropertyAccess<Forum> {
    ModelKeyProvider<Forum> topicId();

    LabelProvider<Forum> title();
  }

  protected static final int PREFERRED_HEIGHT = -1;
  protected static final int PREFERRED_WIDTH = 575;

  private ComboBox<Forum> combo;
  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      String url = "//www.sencha.com/forum/topics-remote.php";

      ScriptTagProxy<ForumLoadConfig> proxy = new ScriptTagProxy<ForumLoadConfig>(url);
      proxy.setWriter(new UrlEncodingWriter<ForumLoadConfig>(TestAutoBeanFactory.instance, ForumLoadConfig.class));

      JsoReader<ForumListLoadResult, ForumCollection> reader = new JsoReader<ForumListLoadResult, ForumCollection>(
          TestAutoBeanFactory.instance, ForumCollection.class) {
        @Override
        protected ForumListLoadResult createReturnData(Object loadConfig, ForumCollection records) {
          PagingLoadConfig cfg = (PagingLoadConfig) loadConfig;
          ForumListLoadResult result = TestAutoBeanFactory.instance.dataLoadResult().as();
          result.setData(records.getTopics());
          result.setOffset(cfg.getOffset());
          result.setTotalLength(Integer.parseInt(records.getTotalCount()));

          return result;
        }
      };

      PagingLoader<ForumLoadConfig, ForumListLoadResult> loader = new PagingLoader<ForumLoadConfig, ForumListLoadResult>(
          proxy, reader);
      loader.useLoadConfig(TestAutoBeanFactory.instance.loadConfig().as());
      loader.addBeforeLoadHandler(new BeforeLoadHandler<ForumLoadConfig>() {
        @Override
        public void onBeforeLoad(BeforeLoadEvent<ForumLoadConfig> event) {
          String query = combo.getText();
          if (query != null && !query.equals("")) {
            event.getLoadConfig().setQuery(query);
          }
        }
      });

      ForumProperties properties = GWT.create(ForumProperties.class);

      ListStore<Forum> store = new ListStore<Forum>(properties.topicId());

      loader.addLoadHandler(new LoadResultListStoreBinding<ForumLoadConfig, Forum, ForumListLoadResult>(store));

      final Bundle bundle = GWT.create(Bundle.class);
      bundle.css().ensureInjected();

      final ExampleTemplate template = GWT.create(ExampleTemplate.class);

      ListView<Forum, Forum> listView = new ListView<Forum, Forum>(store, new IdentityValueProvider<Forum>());
      listView.setCell(new AbstractCell<Forum>() {
        @Override
        public void render(Context context, Forum value, SafeHtmlBuilder sb) {
          sb.append(template.render(value, bundle.css()));
        }
      });

      ComboBoxCell<Forum> comboCell = new ComboBoxCell<Forum>(store, properties.title(), listView) {
        @Override
        protected PagingToolBar createPagingToolBar(int pageSize) {
          PagingToolBar pagingToolbar = new PagingToolBar(pageSize);
          pagingToolbar.setEnableOverflow(false);
          return pagingToolbar;
        }
      };

      combo = new ComboBox<Forum>(comboCell);
      combo.setLoader(loader);
      combo.setHideTrigger(true);
      combo.setPageSize(10);
      combo.addBeforeSelectionHandler(new BeforeSelectionHandler<Forum>() {
        @Override
        public void onBeforeSelection(BeforeSelectionEvent<Forum> event) {
          event.cancel();
          Forum forum = event.getItem();
          String url = "//sencha.com/forum/showthread.php?t=" + forum.getTopicId() + "&p=" + forum.getPostId();
          Window.open(url, null, "resizable=yes");
        }
      });

      panel = new ContentPanel();
      panel.setHeading("Combo Box — Advanced — Search the Sencha Forums");
      panel.add(combo, new MarginData(10));
    }

    return panel;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setPreferredHeight(PREFERRED_HEIGHT)
        .setPreferredWidth(PREFERRED_WIDTH)
        .doStandalone();
  }

}
