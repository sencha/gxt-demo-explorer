package com.sencha.gxt.examples.resources.client.model;

public class Task {
  
  private int id;
  private String project;
  private int taskId;
  private String description;
  private double estimate;
  private double rate;
  private String due;// date?
  
  public Task(int id, String project, int taskId, String desc, double estimate, double rate, String due) {
    setId(id);
    setProject(project);
    setTaskId(taskId);
    setDescription(desc);
    setEstimate(estimate);
    setRate(rate);
    setDue(due);
  }

  public String getDescription() {
    return description;
  }

  public String getDue() {
    return due;
  }

  public double getEstimate() {
    return estimate;
  }

  public int getId() {
    return id;
  }

  public String getProject() {
    return project;
  }

  public double getRate() {
    return rate;
  }

  public int getTaskId() {
    return taskId;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDue(String due) {
    this.due = due;
  }

  public void setEstimate(Double estimate) {
    if (estimate != null) {
      this.estimate = estimate;
    }
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setProject(String project) {
    this.project = project;
  }

  public void setRate(Double rate) {
    if (rate != null) {
      this.rate = rate;
    }
  }

  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }
}
