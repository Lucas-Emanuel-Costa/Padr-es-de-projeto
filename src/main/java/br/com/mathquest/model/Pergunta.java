package br.com.mathquest.model;

import br.com.mathquest.strategy.OperacaoMatematica;

public class Pergunta {

    private final int numero1;
    private final int numero2;
    private final OperacaoMatematica operacao;
    private final int respostaCorreta;

    public Pergunta(int numero1, int numero2, OperacaoMatematica operacao) {
        this.numero1 = numero1;
        this.numero2 = numero2;
        this.operacao = operacao;
        this.respostaCorreta = operacao.calcular(numero1, numero2);
    }

    public String getEnunciado() {
        return numero1 + " " + operacao.getSimbolo() + " " + numero2 + " = ?";
    }

    public int getRespostaCorreta() {
        return respostaCorreta;
    }

    public boolean verificarResposta(int respostaJogador) {
        return respostaJogador == respostaCorreta;
    }
}
