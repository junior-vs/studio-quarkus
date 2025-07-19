package org.acme.security.openid.connect.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.quarkus.oidc.client.filter.OidcClientFilter;
import io.smallrye.mutiny.Uni;

/**
 * 1. Registre um filtro de cliente OIDC com o cliente REST para obter e propagar os tokens.
 * 2. Register the REST client with the OIDC client filter.
 */
@RegisterRestClient //2
@OidcClientFilter //1
@Path("/")
public interface RestClientWithOidcClientFilter {

    @GET
    @Produces("text/plain")
    @Path("userName")
    Uni<String> getUserName();

    @GET
    @Produces("text/plain")
    @Path("adminName")
    Uni<String> getAdminName();
}