package com.sencha.gxt.examples.resources.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.SortInfoBean;
import com.sencha.gxt.data.shared.loader.FilterConfigBean;
import com.sencha.gxt.data.shared.loader.PagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.examples.resources.client.model.Post;
import com.sencha.gxt.examples.resources.shared.PostRequest.PostPagingLoadResultBean;

public class PostService {
  private List<Post> posts;

  public PostService() {
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

  PagingLoadResult<Post> getPosts(PagingLoadConfigBean config) {
    return getPosts(config.getOffset(), config.getLimit(), config.getSortInfo(), null);
  }

  public PostPagingLoadResultBean getPosts(int offset, int limit, List<SortInfoBean> sortInfo, List<FilterConfigBean> filterConfig) {
    try {
      // load data
    
    List<Post> posts = new ArrayList<Post>(this.posts);

    // filter out results - this is just an example and is done in java for the sake of making
    // a simple example. This should almost certainly be done as part of the persistence tool
    // instead
    if (filterConfig != null && filterConfig.size() != 0) {
      item: for (int i = posts.size() - 1; i >= 0; i--) {
        Post p = posts.get(i);
        for (FilterConfigBean f : filterConfig) {
          if (f.getField().equals("forum")) {
            if (!p.getForum().contains(f.getValue())) {
              posts.remove(i);
              continue item;
            }
          } else if (f.getField().equals("username")) {
            if (!p.getUsername().contains(f.getValue())) {
              posts.remove(i);
              continue item;
            }
          } else if (f.getField().equals("subject")) {
            if (!p.getSubject().contains(f.getValue())) {
              posts.remove(i);
              continue item;
            }
          } else if (f.getField().equals("date")) {
            Date when = new Date(Long.parseLong(f.getValue()));
            if ("before".equals(f.getComparison())) {
              if (!p.getDate().before(when)) {
                posts.remove(i);
                continue item;
              }
            } else if ("after".equals(f.getComparison())) {
              if (!p.getDate().after(when)) {
                posts.remove(i);
                continue item;
              }
            } else if ("on".equals(f.getComparison())) {
              when = new DateWrapper(when).resetTime().asDate();
              Date value = new DateWrapper(p.getDate()).resetTime().asDate();
              if (!when.equals(value)) {
                posts.remove(i);
                continue item;
              }
            }
          }
        }
      }
    }

    //sort - again, this is just an example, and should properly be done in the persistence tool
    if (sortInfo.size() > 0) {
      SortInfo sort = sortInfo.get(0);
      if (sort.getSortField() != null) {
        final String sortField = sort.getSortField();
        if (sortField != null) {
          Collections.sort(posts, sort.getSortDir().comparator(new Comparator<Post>() {
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

    // get current page
    ArrayList<Post> sublist = new ArrayList<Post>();
    int start = offset;
    int actualLimit = posts.size();
    if (limit > 0) {
      actualLimit = Math.min(start + limit, actualLimit);
    }
    for (int i = offset; i < actualLimit; i++) {
      sublist.add(posts.get(i));
    }
    
    // wrap it up, send it back
    return new PostPagingLoadResultBean(sublist, posts.size(), offset);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex);
    }
  }

  private String getValue(NodeList fields, int index) {
    NodeList list = fields.item(index).getChildNodes();
    if (list.getLength() > 0) {
      return list.item(0).getNodeValue();
    } else {
      return "";
    }
  }
}
