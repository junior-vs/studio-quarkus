package org.acme.banking.agencias.repository;

import org.acme.banking.agencias.domain.Agencia;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AgenciaRepository implements PanacheRepository<Agencia> {
}
