package com.sencha.gxt.examples.resources.shared;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.sencha.gxt.examples.resources.client.model.Post;

@ProxyFor(Post.class)
public interface PostProxy extends ValueProxy {
  int getId();

  String getUsername();

  String getForum();

  String getSubject();

  Date getDate();
}
