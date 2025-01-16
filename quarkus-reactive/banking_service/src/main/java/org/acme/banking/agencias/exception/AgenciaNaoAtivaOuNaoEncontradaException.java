package org.acme.banking.agencias.exception;

import org.acme.banking.agencias.domain.http.SituacaoCadastral;

public class AgenciaNaoAtivaOuNaoEncontradaException extends RuntimeException {

    @Override
    public String getMessage() {
        return "O status da agência é " + SituacaoCadastral.INATIVO + " ou não foi encontrada";
    }
}
