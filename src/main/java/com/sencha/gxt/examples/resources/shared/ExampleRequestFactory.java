package com.sencha.gxt.examples.resources.shared;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface ExampleRequestFactory extends RequestFactory {
  MusicRequest music();

  FolderRequest folder();

  PostRequest post();
}
