package org.acme;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.Duration;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    public static void main(String[] args) {
        Uni.createFrom().item("hello")
                .onItem().transform((item -> item + " mutiny"))
                .onItem().transform(String::toUpperCase)
                .onItem().delayIt().by(Duration.ofMillis(100))
                .subscribe().with((item -> System.out.println(">> " + item)));
    }
}
