package com.sencha.gxt.explorer.client.grid;

import com.google.gwt.core.client.Callback;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.examples.resources.client.model.Post;

public class PagingGridConfirmableLoader extends PagingLoader<PagingLoadConfig, PagingLoadResult<Post>> {
  public PagingGridConfirmableLoader(DataProxy<PagingLoadConfig, PagingLoadResult<Post>> proxy) {
    super(proxy);
  }

  @Override
  public void loadData(PagingLoadConfig config) {
    super.loadData(config);
  }
}
