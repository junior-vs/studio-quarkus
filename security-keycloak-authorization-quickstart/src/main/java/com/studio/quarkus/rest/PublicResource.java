package com.studio.quarkus.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/public")
public class PublicResource {

    @GET
    public String serve() {
        return "granted";
    }
}
