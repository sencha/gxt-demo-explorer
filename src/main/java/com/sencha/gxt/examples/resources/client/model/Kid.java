package com.sencha.gxt.examples.resources.client.model;

import java.util.Date;

public class Kid {
  private static int nextId = 0;
  private final int id = nextId++;
  private String name;
  private Integer age;
  private Date bday;

  public Kid(String name, Integer age, Date bday) {
    setName(name);
    setAge(age);
    setBday(bday);
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Date getBday() {
    return bday;
  }

  public void setBday(Date bday) {
    this.bday = bday;
  }
}