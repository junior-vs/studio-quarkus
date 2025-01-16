package org.acme;

import io.smallrye.mutiny.Uni;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/situacao-cadastral")
public class SituacaoCadastralController {

    final AgenciaRepository agenciaRepository;
    final  AgenciaService agenciaService;

    SituacaoCadastralController(AgenciaRepository agenciaRepository, AgenciaService agenciaService) {
        this.agenciaRepository = agenciaRepository;
        this.agenciaService = agenciaService;
    }

    @POST
    @Transactional
    public RestResponse<Void> cadastrar(AgenciaRequest agencia, @Context UriInfo uriInfo) {
        this.agenciaService.cadastrar(agencia);
        return RestResponse.created(uriInfo.getAbsolutePathBuilder().build());
    }


    @GET
    public List<Agencia> buscarTodos() {
        return this.agenciaRepository.findAll().stream().toList();
    }

    @GET
    @Path("{cnpj}")
    public RestResponse<Uni<Agencia>> buscarPorCnpj(String cnpj) {
        Uni<Agencia> agencia = this.agenciaRepository.findByCnpj(cnpj);
        if (agencia != null) {
            return RestResponse.ok(agencia);
        }
        return RestResponse.noContent();
    }

}
