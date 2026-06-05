package br.com.mathquest.model;

public class Jogador {

    private final String nome;
    private int pontuacao;

    public Jogador(String nome) {
        this.nome = nome;
        this.pontuacao = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void adicionarPontos(int pontos) {
        pontuacao += pontos;
    }
}
