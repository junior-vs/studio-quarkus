package com.studio.quarkus.web;



import io.quarkus.vertx.ConsumeEvent;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped      
public class GreetingService {
    @ConsumeEvent
    public String consumeNormal(String name) {
        return name.toUpperCase();
    }
}
