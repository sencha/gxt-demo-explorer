package com.sencha.gxt.examples.resources.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.examples.resources.client.model.FileModel;

/**
 * Async <code>FileService<code> interface.
 */
public interface FileServiceAsync {

  public void getFolderChildren(FileModel model, AsyncCallback<List<FileModel>> children);

  // public void getFolderChildren(RemoteSortTreeLoadConfig loadConfig,
  // AsyncCallback<List<FileModel>> children);

}
