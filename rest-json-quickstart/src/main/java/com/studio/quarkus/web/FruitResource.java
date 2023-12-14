package com.studio.quarkus.web;


import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/fruits")
public class FruitResource {

  private Set<Fruit> fruits = Collections.newSetFromMap(
      Collections.synchronizedMap(new LinkedHashMap<>()));

  public FruitResource() {
    fruits.add(new Fruit("Apple", "Winter fruit"));
    fruits.add(new Fruit("Pineapple", "Tropical fruit"));
  }

  @GET
  public Set<Fruit> list() {
    return fruits;
  }

  @POST
  public Set<Fruit> add(Fruit fruit) {
    fruits.add(fruit);
    return fruits;
  }

  @DELETE
  public Set<Fruit> delete(Fruit fruit) {
    fruits.removeIf(existingFruit -> existingFruit.name.contentEquals(fruit.name));
    return fruits;
  }
  @GET
  @Path("/{name}")
  public Uni<Fruit> getOne(String name) {
    return findByName(name);
  }

  private Uni<Fruit> findByName(String name) {
    Optional<Fruit> first = fruits.stream().filter(f -> Objects.equals(f.getName(), name)).findFirst();
    return Uni.createFrom().item(first.orElseThrow());
  }
}