package org.acme;

import java.net.URI;
import java.util.Set;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/extension-programmatic")
public class ExtensionProgrammatic {

    private final ExtensionsService extensionsService;

    public ExtensionProgrammatic() {
        // extensionsService =
        // QuarkusRestClientBuilder.newBuilder().baseUri(URI.create("https://stage.code.quarkus.io/api")).build(ExtensionsService.class);
        extensionsService = RestClientBuilder.newBuilder()
                .baseUri(URI.create("https://stage.code.quarkus.io/api"))
                .build(ExtensionsService.class);
    }

    @GET
    @Path("/id/{id}")
    public Set<Extension> id(String id) {
        return extensionsService.getById(id);
    }

}
