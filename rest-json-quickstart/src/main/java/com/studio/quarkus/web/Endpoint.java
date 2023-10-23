package com.studio.quarkus.web;

import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;

import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Flow;

@Path("rest")
public class Endpoint {

    @Path("hello")
    @GET
    public String hello() {
        return "Hello, World!";
    }

    @GET
    @Path("/reactive")
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<String> reactiveHello() {
        return ReactiveStreams.of("h", "e", "l", "l", "o")
                .map(String::toUpperCase)
                .toList()
                .run()
                .thenApply(list -> list.toString());
    }

    @GET
    @Path("/mutiny")
    @Produces(MediaType.TEXT_PLAIN)
    public Multi<String> helloMutiny() {
        return Multi.createFrom().items("h", "e", "l", "l", "o");
    }

    @GET
    @Path("/integers")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Flow.Publisher<Long> longPublisher() {
        return Multi.createFrom()
                .ticks().every(Duration.ofMillis(500));
    }
}