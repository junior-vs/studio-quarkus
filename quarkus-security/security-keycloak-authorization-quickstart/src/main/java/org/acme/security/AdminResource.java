package org.acme.security;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/admin")
public class AdminResource {

    @GET
    public String manage() {
        return "granted";
    }
}
