package br.com.mathquest.strategy;

public interface OperacaoMatematica {

    int calcular(int numero1, int numero2);

    String getNome();

    String getSimbolo();
}
