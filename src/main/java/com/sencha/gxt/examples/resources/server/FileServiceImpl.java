/**
 * Sencha GXT 1.0.0-SNAPSHOT - Sencha for GWT
 * Copyright (c) 2006-2018, Sencha Inc.
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
package com.sencha.gxt.examples.resources.server;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.sencha.gxt.examples.resources.client.FileService;
import com.sencha.gxt.examples.resources.client.model.FileModel;
import com.sencha.gxt.examples.resources.client.model.FolderModel;

public class FileServiceImpl extends RelativeRemoteServiceServlet implements FileService {

  private File root;
  private FilenameFilter filter;
  private HashMap<File, String> idMap = new HashMap<File, String>();
  private int counter = 0;
  
  public FileServiceImpl() {
    URL rootUrl = getClass().getClassLoader().getResource("com/sencha");
    try {
      // %20 will be converted to a space
      root = new File(rootUrl.toURI());
    } catch (URISyntaxException e) {
      // fallback
      root = new File(rootUrl.getFile());
    }
    filter = new FilenameFilter() {
      public boolean accept(File dir, String name) {
        return !name.startsWith(".");
      }
    };
  }
  
  @Override
  public List<FileModel> getFolderChildren(FileModel folder) {
    File[] files = null;
    if (folder == null) {
      files = root.listFiles(filter);
    } else {
      File f = new File(folder.getPath());
      files = f.listFiles(filter);
    }

    List<FileModel> models = new ArrayList<FileModel>();
    for (File f : files) {
      FileModel m = null;
      if (f.isDirectory()) {
        m = new FolderModel(f.getName(), f.getAbsolutePath());
      } else {
        m = new FileModel(f.getName(), f.getAbsolutePath());
        m.setSize(f.length());
        m.setLastModified(new Date(f.lastModified()));
      }

      if (idMap.containsKey(f)) {
        m.setId(idMap.get(f));
      } else {
        String id = String.valueOf(counter++);
        idMap.put(f, id);
        m.setId(id);
      }

      models.add(m);
    }

    Collections.sort(models, new Comparator<FileModel>() {
      public int compare(FileModel o1, FileModel o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });
    return models;
  }

}
