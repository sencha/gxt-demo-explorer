package com.sencha.gxt.explorer.client.model;

import java.util.ArrayList;
import java.util.List;

import com.sencha.gxt.data.shared.TreeStore;

/**
 * Model object for storing info about a source file that can be used to show
 * how an example is put together.
 * 
 */
public class Source implements TreeStore.TreeNode<Source> {
  private static int nextId = 1;
  public enum FileType {
    JAVA, CSS, XML, JSON, FOLDER, HTML
  }
  private Integer id;
  private String name;
  private String url;
  private FileType type;
  private List<Source> children = new ArrayList<Source>();

  public FileType getType() {
    return type;
  }

  public void setType(FileType type) {
    this.type = type;
  }
  
  public Source(String name, String url, FileType type) {
    this.id = nextId++;
    this.name = name;
    this.url = url;
    this.type = type;
  }

  public Source(String name) {
    this(name, "", FileType.FOLDER);
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Source> getChildren() {
    return children;
  }

  public void addChild(Source child) {
    getChildren().add(child);
  }

  @Override
  public Source getData() {
    return this;
  }
}
