package org.acme.banking.agencias.service;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.acme.banking.agencias.domain.Agencia;
import org.acme.banking.agencias.domain.Endereco;
import org.acme.banking.agencias.exception.AgenciaNaoAtivaOuNaoEncontradaException;
import org.acme.banking.agencias.repository.AgenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.MeterRegistry;
import io.smallrye.mutiny.Uni;

class AgenciaServiceTest {

    AgenciaRepository agenciaRepository;
    MeterRegistry meterRegistry;
    AgenciaService agenciaService;

    @BeforeEach
    void setUp() {
        agenciaRepository = mock(AgenciaRepository.class);
        meterRegistry = mock(MeterRegistry.class);
        agenciaService = new AgenciaService(agenciaRepository, meterRegistry);
    }

    @Test
    void testAlterarSuccess() {
        Agencia agencia = getAgencia();

        // Simulate repository update returns non-null (success)
        when(agenciaRepository.update(anyString(), any(), any(), any(), any()))
                .thenReturn(Uni.createFrom().item(1));

        Uni<Void> result = agenciaService.alterar(agencia);

        assertDoesNotThrow(() -> result.await().indefinitely());
        verify(agenciaRepository).update(anyString(), eq(agencia.getNome()), eq(agencia.getRazaoSocial()),
                eq(agencia.getCnpj()), eq(agencia.getId()));
    }

    private Agencia getAgencia() {
        return new Agencia(1L, "Agencia Teste", "Razao Social Teste", "12345678901234",
                new Endereco(1, "Rua Teste", "Logradouro Teste", "Complemento Teste", 123));
    }

    @Test
    void testAlterarFailure() {
        Agencia agencia = getAgencia();

        // Simulate repository update returns null (failure)
        when(agenciaRepository.update(anyString(), any(), any(), any(), any()))
                .thenReturn(Uni.createFrom().nullItem());

        Uni<Void> result = agenciaService.alterar(agencia);

        Exception exception = assertThrows(AgenciaNaoAtivaOuNaoEncontradaException.class, () -> {
            result.await().indefinitely();
        });
        assertNotNull(exception);
    }
}