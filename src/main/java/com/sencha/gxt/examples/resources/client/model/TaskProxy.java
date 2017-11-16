package com.sencha.gxt.examples.resources.client.model;

public interface TaskProxy {

  public void setId(int id);

  public int getId();

  public void setProject(String project);

  public String getProject();

  public void setTaskId(int taskId);

  public int getTaskId();

  public void setDescription(String description);

  public String getDescription();

  public void setEstimate(double estimate);

  public double getEstimate();

  public void setRate(double rate);

  public double getRate();

  public void setDue(String due);

  public String getDue();

}
