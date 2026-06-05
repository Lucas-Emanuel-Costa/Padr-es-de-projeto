package br.com.mathquest.model;

import br.com.mathquest.strategy.OperacaoMatematica;

public class Fase {

    private final int nivel;
    private final boolean chefe;
    private final OperacaoMatematica operacao;

    public Fase(int nivel, boolean chefe, OperacaoMatematica operacao) {
        this.nivel = nivel;
        this.chefe = chefe;
        this.operacao = operacao;
    }

    public String getTitulo() {
        if (chefe) {
            return "Chefe final - Mundo da " + operacao.getNome();
        }

        return "Fase " + nivel + " - Mundo da " + operacao.getNome();
    }

    public int getQuantidadePerguntas() {
        return chefe ? 5 : 3;
    }

    public int getAcertosNecessarios() {
        return chefe ? 4 : 2;
    }

    public int getLimiteNumeros() {
        return chefe ? 50 : nivel * 10;
    }

    public boolean isChefe() {
        return chefe;
    }

    public OperacaoMatematica getOperacao() {
        return operacao;
    }
}
