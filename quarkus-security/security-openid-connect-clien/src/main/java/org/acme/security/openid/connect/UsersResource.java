
package org.acme.security.openid.connect;

import org.jboss.resteasy.reactive.NoCache;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;


@Path("/api/users")
public class UsersResource {

    final SecurityIdentity identity;

    public UsersResource(SecurityIdentity identity) {
        this.identity = identity;
    }

    @GET
    @RolesAllowed("user")
    @Path("/me")
    @NoCache
    public User me() {
        return new User(identity);
    }

    public static class User {

        private final String userName;

        User(SecurityIdentity identity) {
            this.userName = identity.getPrincipal().getName();
        }

        public String getUserName() {
            return userName;
        }
    }
}
