package com.studio.quarkus.web;


import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Legume {

  public String name;
  public String description;

  public Legume() {
  }

  public Legume(String name, String description) {
    this.name = name;
    this.description = description;
  }
}