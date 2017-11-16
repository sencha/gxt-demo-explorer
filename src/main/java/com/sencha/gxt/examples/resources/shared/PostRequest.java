package com.sencha.gxt.examples.resources.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import com.sencha.gxt.examples.resources.client.model.Post;
import com.sencha.gxt.examples.resources.server.PostService;
import com.sencha.gxt.examples.resources.server.PostServiceLocator;

@Service(value = PostService.class, locator = PostServiceLocator.class)
public interface PostRequest extends RequestContext {
  @ProxyFor(PostPagingLoadResultBean.class)
  public interface PostPagingLoadResultProxy extends ValueProxy, PagingLoadResult<PostProxy> {
    @Override
    public List<PostProxy> getData();
  }
  
  public static class PostPagingLoadResultBean extends PagingLoadResultBean<Post> {
    protected PostPagingLoadResultBean() {
      
    }
    public PostPagingLoadResultBean(List<Post> list, int totalLength, int offset) {
      super(list, totalLength, offset);
    }
  }

  Request<PostPagingLoadResultProxy> getPosts(int offset, int limit, List<? extends SortInfo> sortInfo, List<? extends FilterConfig> filterConfig);
}
