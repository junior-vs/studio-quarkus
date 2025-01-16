package org.acme;

import java.util.Set;
import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;
import org.jboss.resteasy.reactive.RestResponse;

import io.quarkus.rest.client.reactive.NotBody;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/extensions")
@RegisterRestClient(configKey = "extensions-api")
@RegisterClientHeaders(RequestUUIDHeaderFactory.class)
@ClientHeaderParam(name = "my-header", value = "constant-header-value")
@ClientHeaderParam(name = "computed-header", value = "{org.acme.rest.client.Util.computeHeader}")
public interface ExtensionsService {

    @GET
    Set<Extension> getById(@RestQuery String id);

    @GET
    CompletionStage<Set<Extension>> getByIdAsync(@RestQuery String id);

    @GET
    Uni<Set<Extension>> getByIdAsUni(@RestQuery String id);

    @GET
    RestResponse<Set<Extension>> getByIdResponseProperties(@RestQuery String id);

    @GET
    @ClientHeaderParam(name = "header-from-properties", value = "${header.value}")
    @ClientHeaderParam(name = "header-from-method-param", value = "Bearer {token}")
    Set<Extension> getByIdWithHeaders(@QueryParam("id") String id,
            @HeaderParam("jaxrs-style-header") String headerValue,
            @NotBody String token);

}
