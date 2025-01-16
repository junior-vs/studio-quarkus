package org.acme.banking.agencias.service;

import org.acme.banking.agencias.domain.Agencia;
import org.acme.banking.agencias.domain.http.AgenciaHttp;
import org.acme.banking.agencias.domain.http.SituacaoCadastral;
import org.acme.banking.agencias.exception.AgenciaNaoAtivaOuNaoEncontradaException;
import org.acme.banking.agencias.repository.AgenciaRepository;
import org.acme.banking.agencias.service.http.SituacaoCadastralHttpService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.micrometer.core.instrument.MeterRegistry;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AgenciaService {

    final AgenciaRepository agenciaRepository;
    final MeterRegistry meterRegistry;

    public AgenciaService(AgenciaRepository agenciaRepository, MeterRegistry meterRegistry) {
        this.agenciaRepository = agenciaRepository;
        this.meterRegistry = meterRegistry;
    }

    @RestClient
    SituacaoCadastralHttpService situacaoCadastralHttpService;

    @WithTransaction
    public Uni<Void> cadastrar(Agencia agencia) {
        Uni<AgenciaHttp> agenciaHttp = situacaoCadastralHttpService.buscarPorCnpj(agencia.getCnpj());
        return agenciaHttp
                .onItem().ifNull().failWith(new AgenciaNaoAtivaOuNaoEncontradaException())
                .onItem().transformToUni(item -> persistirSeAtiva(agencia, item));
    }

    private Uni<Void> persistirSeAtiva(Agencia agencia, AgenciaHttp agenciaHttp) {
        if (agenciaHttp.getSituacaoCadastral().equals(SituacaoCadastral.ATIVO)) {
            this.meterRegistry.counter("name: agencia_adicionada_count").increment();
            Log.info("Agencia com CNPJ " + agencia.getCnpj() + " foi adicionada");
            return agenciaRepository.persist(agencia).replaceWithVoid();
        } else {
            Log.info("Agencia com CNPJ " + agencia.getCnpj() + " não ativa ou não encontrada");
            this.meterRegistry.counter("name: agencia_nao_adicionada_count").increment();
            return Uni.createFrom().failure(new AgenciaNaoAtivaOuNaoEncontradaException());
        }
    }

    @WithSession
    public Uni<Agencia> buscarPorId(Long id) {
        return agenciaRepository.findById(id);
    }

    @WithTransaction
    public Uni<Void> deletar(Long id) {
        Log.info("A agência foi deletada");
        return agenciaRepository.deleteById(id).replaceWithVoid()
                .onFailure().recoverWithNull()
                .onItem().ifNull().failWith(new AgenciaNaoAtivaOuNaoEncontradaException());
    }

    @WithTransaction
    public Uni<Void> alterar(Agencia agencia) {
        Log.info("A agência com CNPJ " + agencia.getCnpj() + " foi alterada");
        return agenciaRepository.update("nome = ?1, razaoSocial = ?2, cnpj = ?3 where id = ?4", agencia.getNome(),
                agencia.getRazaoSocial(), agencia.getCnpj(), agencia.getId())
                .onItem().ifNull().failWith(new AgenciaNaoAtivaOuNaoEncontradaException())
                .replaceWithVoid();
    }
}
