package org.acme.security;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/users")
public class UserResource {

    @Inject
    SecurityIdentity keycloakSecurityContext;

    @GET
    @Path("/me")
    public User me() {
        return new User(keycloakSecurityContext);
    }

    public static class User {

        private final String userName;

        User(SecurityIdentity securityContext) {
            this.userName = securityContext.getPrincipal().getName();
        }

        public String getUserName() {
            return userName;
        }
    }
}
