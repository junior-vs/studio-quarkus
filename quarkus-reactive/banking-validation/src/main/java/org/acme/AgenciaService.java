package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AgenciaService {

    private final AgenciaRepository agenciaRepository;


    AgenciaService(AgenciaRepository agenciaRepository) {
        this.agenciaRepository = agenciaRepository;

    }


    public void cadastrar(AgenciaRequest agenciaRequest) {
        Agencia agencia = new Agencia(agenciaRequest.nome(), agenciaRequest.razaoSocial(),
                                      agenciaRequest.cnpj(), agenciaRequest.situacaoCadastral());
        this.agenciaRepository.persist(agencia).await().indefinitely();


    }
}
