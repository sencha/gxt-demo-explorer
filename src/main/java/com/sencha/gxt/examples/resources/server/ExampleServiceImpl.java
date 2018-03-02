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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import com.sencha.gxt.examples.resources.client.ExampleService;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.BaseDto;
import com.sencha.gxt.examples.resources.client.model.FolderDto;
import com.sencha.gxt.examples.resources.client.model.MusicDto;
import com.sencha.gxt.examples.resources.client.model.Photo;
import com.sencha.gxt.examples.resources.client.model.Post;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.examples.resources.server.data.Folder;
import com.sencha.gxt.examples.resources.server.data.Music;

public class ExampleServiceImpl extends RelativeRemoteServiceServlet implements ExampleService {

  private static final long MILLIS_IN_WEEK = 1 * 1000 * 60 * 60 * 24 * 7;

  private List<Post> posts;
  private List<Photo> photos;
  private List<Stock> stocks;

  @Override
  public List<BaseDto> getMusicFolderChildren(FolderDto folder) {
    Folder f = null;
    if (folder == null) {
      f = Folder.getRootFolder();
    } else {
      f = Folder.findFolder(folder.getId());
    }
    List<BaseDto> children = new ArrayList<BaseDto>();
    for (Folder sub : f.getSubFolders()) {
      children.add(new FolderDto(sub.getId(), sub.getName()));
    }
    for (Music m : f.getChildren()) {
      children.add(new MusicDto(m.getId(), m.getName(), m.getGenre(), m.getAuthor()));
    }
    return children;
  }

  @Override
  public FolderDto getMusicRootFolder() {
    Folder root = Folder.getRootFolder();
    FolderDto rootDto = new FolderDto(root.getId(), root.getName());
    processFolder(root, rootDto);
    return rootDto;
  }

  @Override
  public List<Photo> getPhotos() {
    if (photos == null) {
      loadPhotos();
    }
    return photos;
  }

  @Override
  public PagingLoadResult<Post> getPosts(PagingLoadConfig config) {
    if (posts == null) {
      loadPosts();
    }

    if (config.getSortInfo().size() > 0) {
      SortInfo sort = config.getSortInfo().get(0);
      if (sort.getSortField() != null) {
        final String sortField = sort.getSortField();
        if (sortField != null) {
          Collections.sort(posts, sort.getSortDir().comparator(new Comparator<Post>() {
            @Override
            public int compare(Post p1, Post p2) {
              if (sortField.equals("forum")) {
                return p1.getForum().compareTo(p2.getForum());
              } else if (sortField.equals("username")) {
                return p1.getUsername().compareTo(p2.getUsername());
              } else if (sortField.equals("subject")) {
                return p1.getSubject().compareTo(p2.getSubject());
              } else if (sortField.equals("date")) {
                return p1.getDate().compareTo(p2.getDate());
              }
              return 0;
            }
          }));
        }
      }
    }

    ArrayList<Post> sublist = new ArrayList<Post>();
    int start = config.getOffset();
    int limit = posts.size();
    if (config.getLimit() > 0) {
      limit = Math.min(start + config.getLimit(), limit);
    }
    for (int i = config.getOffset(); i < limit; i++) {
      sublist.add(posts.get(i));
    }
    return new PagingLoadResultBean<Post>(sublist, posts.size(), config.getOffset());
  }

  @Override
  public PagingLoadResult<Stock> getStocks(FilterPagingLoadConfig config) {
    if (stocks == null) {
      stocks = TestData.getStocks();
    }

    ArrayList<Stock> temp = new ArrayList<Stock>();
    ArrayList<Stock> remove = new ArrayList<Stock>();
    for (Stock s : stocks) {
      temp.add(s);
    }

    if (config.getSortInfo().size() > 0) {
      SortInfo sort = config.getSortInfo().get(0);
      if (sort.getSortField() != null) {
        final String sortField = sort.getSortField();
        if (sortField != null) {
          Collections.sort(temp, sort.getSortDir().comparator(new Comparator<Stock>() {
            @Override
            public int compare(Stock s1, Stock s2) {
              if ("name".equals(sortField)) {
                return s1.getName().compareTo(s2.getName());
              } else if ("lastTrans".equals(sortField)) {
                return s1.getLastTrans().compareTo(s2.getLastTrans());
              } else if ("split".equals(sortField)) {
                Boolean b1 = s1.isSplit();
                Boolean b2 = s2.isSplit();
                return b1.compareTo(b2);
              } else if ("industry".equals(sortField)) {
                return s1.getIndustry().compareTo(s2.getIndustry());
              } else if ("symbol".equals(sortField)) {
                return s1.getSymbol().compareTo(s2.getSymbol());
              } else if ("last".equals(sortField)) {
                return s1.getLast().compareTo(s2.getLast());
              } else if ("change".equals(sortField)) {
                return s1.getChange().compareTo(s2.getChange());
              }
              return 0;
            }
          }));
        }
      }
    }

    List<FilterConfig> filters = config.getFilters();
    for (FilterConfig f : filters) {
      String type = f.getType();
      String test = f.getValue();
      String path = f.getField();
      String comparison = f.getComparison();

      String safeTest = test == null ? "" : test.toString();

      for (Stock s : stocks) {
        String value = getStockValue(s, path);
        String safeValue = value == null ? null : value.toString();

        if (safeTest.length() == 0 && (safeValue == null || safeValue.length() == 0)) {
          continue;
        } else if (safeValue == null) {
          remove.add(s);
          continue;
        }

        if ("string".equals(type)) {
          if (safeValue.toLowerCase().indexOf(safeTest.toLowerCase()) == -1) {
            remove.add(s);
          }
        } else if ("date".equals(type)) {
          if (isDateFiltered(safeTest, comparison, safeValue)) {
            remove.add(s);
          }
        } else if ("boolean".equals(type)) {
          if (isBooleanFiltered(safeTest, comparison, safeValue)) {
            remove.add(s);
          }
        } else if ("list".equals(type)) {
          if (isListFiltered(safeTest, safeValue)) {
            remove.add(s);
          }
        } else if ("numeric".equals(type)) {
          if (isNumberFiltered(safeTest, comparison, safeValue)) {
            remove.add(s);
          }
        }
      }
    }

    for (Stock s : remove) {
      temp.remove(s);
    }

    ArrayList<Stock> sublist = new ArrayList<Stock>();
    int start = config.getOffset();
    int limit = temp.size();
    if (config.getLimit() > 0) {
      limit = Math.min(start + config.getLimit(), limit);
    }
    for (int i = config.getOffset(); i < limit; i++) {
      sublist.add(temp.get(i));
    }
    return new PagingLoadResultBean<Stock>(sublist, temp.size(), config.getOffset());
  }

  private String getStockValue(Stock s, String property) {
    if (property.equals("name")) {
      return s.getName();
    } else if (property.equals("lastTrans")) {
      return String.valueOf(s.getLastTrans().getTime());
    } else if (property.equals("split")) {
      return String.valueOf(s.isSplit());
    } else if (property.equals("last")) {
      return String.valueOf(s.getLast());
    } else if (property.equals("industry")) {
      return s.getIndustry();
    }

    return "";
  }

  private String getValue(NodeList fields, int index) {
    NodeList list = fields.item(index).getChildNodes();
    if (list.getLength() > 0) {
      return list.item(0).getNodeValue();
    } else {
      return "";
    }
  }

  private boolean isBooleanFiltered(String test, String comparison, String value) {
    if (value == null) {
      return true;
    }
    boolean t = Boolean.valueOf(test);
    boolean v = Boolean.parseBoolean(value);

    return t != v;
  }

  private boolean isDateFiltered(String test, String comparison, String value) {
    Date t = new Date(Long.valueOf(test));
    Date v = new Date(Long.valueOf(value));
    if (value == null) {
      return false;
    }
    if ("after".equals(comparison)) {
      return v.before(t);
    } else if ("before".equals(comparison)) {
      return v.after(t);
    } else if ("on".equals(comparison)) {
      t = new DateWrapper(t).resetTime().asDate();
      v = new DateWrapper(v).resetTime().asDate();
      return !v.equals(t);
    }
    return true;
  }

  private boolean isListFiltered(String test, String value) {
    String[] tests = test.split("::");
    for (String test2 : tests) {
      if (test2.equals(value)) {
        return false;
      }
    }
    return true;
  }

  private boolean isNumberFiltered(String test, String comparison, String value) {
    if (value == null) {
      return false;
    }
    double t = Double.valueOf(test);
    double v = Double.valueOf(value);

    if ("gt".equals(comparison)) {
      return t >= v;
    } else if ("lt".equals(comparison)) {
      return t <= v;
    } else if ("eq".equals(comparison)) {
      return t != v;
    }
    return false;
  }

  private void loadPhotos() {
    photos = new ArrayList<Photo>();

    String url = getThreadLocalRequest().getSession().getServletContext().getRealPath("/examples/images/photos");
    // %20 will be converted to a space
    File folder = new File(url);

    File[] pics = folder.listFiles(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return !name.startsWith(".");
      }
    });
    if (pics == null) {
      return;
    }
    Arrays.sort(pics, new Comparator<File>() {
      @Override
      public int compare(File o1, File o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });

    Random random = new Random();
    long lastModified = Long.MIN_VALUE;
    for (File pic : pics) {
      Photo photo = new Photo();
      photo.setName(pic.getName());
      if (lastModified == Long.MIN_VALUE) {
        lastModified = pic.lastModified();
        photo.setDate(new Date(lastModified));
      } else {
        // set the date to a normally distributed value around the initial lastModified date
        // (necessary since all lastModified dates are the same)
        photo.setDate(new Date(lastModified + (long) (random.nextGaussian() * MILLIS_IN_WEEK)));
      }
      photo.setSize(pic.length());
      photo.setPath("examples/images/photos/" + pic.getName());
      photos.add(photo);
    }
  }

  private void loadPosts() {
    posts = new ArrayList<Post>();

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(getClass().getResourceAsStream("posts.xml"));
      doc.getDocumentElement().normalize();

      NodeList nodeList = doc.getElementsByTagName("row");

      for (int s = 0; s < nodeList.getLength(); s++) {
        Node fstNode = nodeList.item(s);
        if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
          Element fstElmnt = (Element) fstNode;
          NodeList fields = fstElmnt.getElementsByTagName("field");
          Post p = new Post();
          p.setForum(getValue(fields, 0));
          p.setDate(sf.parse(getValue(fields, 1)));
          p.setSubject(getValue(fields, 2));
          p.setUsername(getValue(fields, 4));
          posts.add(p);

        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void processFolder(Folder folder, FolderDto folderDto) {
    List<BaseDto> children = new ArrayList<BaseDto>();
    for (Folder f : folder.getSubFolders()) {
      FolderDto sub = new FolderDto(f.getId(), f.getName());
      processFolder(f, sub);
      children.add(sub);
    }
    for (Music m : folder.getChildren()) {
      MusicDto musicDto = new MusicDto(m.getId(), m.getName(), m.getGenre(), m.getAuthor());
      children.add(musicDto);
    }
    folderDto.setChildren(children);
  }

}
