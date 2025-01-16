package org.acme;

import java.sql.Date;
import java.util.Set;
import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestQuery;
import org.jboss.resteasy.reactive.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/extension")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExtensionsResource {

    @RestClient
    ExtensionsService extensionsService;

    private static Logger logger = LoggerFactory.getLogger(ExtensionsResource.class);

    @GET
    @Path("/id/{id}")
    @Blocking
    public Set<Extension> id(String id) {
        logger.info("Fetching extension by ID: {}", id);
        Set<Extension> byId = extensionsService.getById(id);
        logger.info("Fetched extensions: {}", byId);
        return byId;
    }

    @GET
    @Path("/with-headers/id/{id}")
    @Blocking
    public Set<Extension> idWithHeaders(String id) {
        logger.info("Fetching extension by ID: {}", id);
        Set<Extension> byId = extensionsService.getByIdWithHeaders  (id, "header-value", "my-token");
        logger.info("Fetched extensions: {}", byId);
        return byId;
    }

    @GET
    @Path("/id-async/{id}")
    public CompletionStage<Set<Extension>> idAsync(String id) {
        logger.info("Fetching extension by ID asynchronously: {}", id);
        return extensionsService.getByIdAsync(id);
    }

    @GET
    @Path("/id-uni/{id}")
    public Uni<Set<Extension>> idUni(String id) {
        logger.info("Fetching extension by ID using Uni: {}", id);
        return extensionsService.getByIdAsUni(id)
                .onFailure().retry().atMost(10);
    }

    @GET
    @Path("/properties")
    public RestResponse<Set<Extension>> responseProperties(@RestQuery String id) {
        RestResponse<Set<Extension>> clientResponse = extensionsService.getByIdResponseProperties(id);
        String contentType = clientResponse.getHeaderString("Content-Type");
        int status = clientResponse.getStatus();
        String setCookie = clientResponse.getHeaderString("Set-Cookie");
        var lastModified = clientResponse.getLastModified();

        logger.info("content-Type: {} status: {} Last-Modified: {} Set-Cookie: {}", contentType, status, lastModified,
                setCookie);

        return RestResponse.fromResponse(clientResponse);
    }

}
