package com.sencha.gxt.examples.resources.client.model;

import java.util.List;

@SuppressWarnings("serial")
public class FolderDto extends BaseDto {

  private List<BaseDto> children;

  protected FolderDto() {

  }

  public FolderDto(Integer id, String name) {
    super(id, name);
  }

  public List<BaseDto> getChildren() {
    return children;
  }

  public void setChildren(List<BaseDto> children) {
    this.children = children;
  }

  public void addChild(BaseDto child) {
    getChildren().add(child);
  }
}
