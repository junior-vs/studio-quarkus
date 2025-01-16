package org.acme.banking.agencias.controller;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.faulttolerance.api.RateLimit;
import io.smallrye.mutiny.Uni;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.acme.banking.agencias.domain.Agencia;
import org.acme.banking.agencias.service.AgenciaService;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.jboss.resteasy.reactive.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/agencias")
public class AgenciaController {

    private static final Logger log = LoggerFactory.getLogger(AgenciaController.class);
    final AgenciaService agenciaService;

    AgenciaController(AgenciaService agenciaService) {
        this.agenciaService = agenciaService;
    }

    @POST
    @NonBlocking
    @Transactional
    @CircuitBreaker(
            requestVolumeThreshold = 5,
            failureRatio = 0.5,
            delay = 2000,
            successThreshold = 2

    )
    @Fallback(
            fallbackMethod = "fallbackCadastrar"
    )
    public Uni<RestResponse<Void>> cadastrar(Agencia agencia, @Context UriInfo uriInfo) {
        return this.agenciaService.cadastrar(agencia).replaceWith(RestResponse.created(uriInfo.getAbsolutePathBuilder().build()));
    }

    public Uni<Void> fallbackCadastrar(Agencia agencia, @Context UriInfo uriInfo) {
        log.info("Erro na chamada do serviço de cadastro de agência, retornando fallback");
        return Uni.createFrom().voidItem();
    }

    @GET
    @Path("{id}")
    @RateLimit(value = 100, window = 10)
    public Uni<RestResponse<Agencia>> buscarPorId(Long id) {
        return this.agenciaService.buscarPorId(id).map(RestResponse::ok);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Uni<RestResponse<Object>> deletar(Long id) {
        return this.agenciaService.deletar(id)
                .replaceWith(RestResponse.noContent());

    }

    @PUT
    @Transactional
    public RestResponse<Void> alterar(Agencia agencia) {
        this.agenciaService.alterar(agencia);
        return RestResponse.ok();
    }
}
