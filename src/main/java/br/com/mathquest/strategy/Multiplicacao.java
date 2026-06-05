package br.com.mathquest.strategy;

public class Multiplicacao implements OperacaoMatematica {

    @Override
    public int calcular(int numero1, int numero2) {
        return numero1 * numero2;
    }

    @Override
    public String getNome() {
        return "Multiplicacao";
    }

    @Override
    public String getSimbolo() {
        return "x";
    }
}
