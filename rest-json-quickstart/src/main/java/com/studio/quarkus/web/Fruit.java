package com.studio.quarkus.web;


public class Fruit {

  public String name;
  public String description;

  public Fruit(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }
}