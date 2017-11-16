package com.sencha.gxt.examples.resources.client.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface PostProperties extends PropertyAccess<Post> {
  ValueProvider<Post, Date> date();

  ValueProvider<Post, String> forum();

  ModelKeyProvider<Post> id();

  ValueProvider<Post, String> subject();

  ValueProvider<Post, String> username();
}
