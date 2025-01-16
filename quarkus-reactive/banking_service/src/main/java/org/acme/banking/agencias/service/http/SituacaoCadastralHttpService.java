package org.acme.banking.agencias.service.http;

import org.acme.banking.agencias.domain.http.AgenciaHttp;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/situacao-cadastral")
@RegisterRestClient(configKey = "situacao-cadastral-api")
public interface SituacaoCadastralHttpService {

    @GET
    @Path("{cnpj}")
    Uni<AgenciaHttp> buscarPorCnpj(String cnpj);
}
