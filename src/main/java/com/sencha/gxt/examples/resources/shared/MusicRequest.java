package com.sencha.gxt.examples.resources.shared;


import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.sencha.gxt.examples.resources.server.data.Music;

@Service(Music.class)
public interface MusicRequest extends RequestContext {
  InstanceRequest<MusicProxy, MusicProxy> persist();
  
  InstanceRequest<MusicProxy, Void> remove();
}
