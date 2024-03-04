/**
 * Sencha GXT 1.0.0-SNAPSHOT - Sencha for GWT
 * Copyright (c) 2006-2021, Sencha Inc.
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
package com.sencha.gxt.explorer.client.model;

import java.util.ArrayList;
import java.util.List;

import com.sencha.gxt.data.shared.TreeStore;

/**
 * Model object for storing info about a source file that can be used to show how an example is put together.
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

  @Override
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
