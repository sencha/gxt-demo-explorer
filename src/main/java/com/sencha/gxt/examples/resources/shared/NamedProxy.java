package com.sencha.gxt.examples.resources.shared;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;

public interface NamedProxy extends EntityProxy {

  Integer getId();

  String getName();

  @Override
  public EntityProxyId<? extends NamedProxy> stableId();
}
