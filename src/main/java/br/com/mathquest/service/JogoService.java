package br.com.mathquest.service;

import br.com.mathquest.factory.FabricaFase;
import br.com.mathquest.factory.FabricaFaseMatematica;
import br.com.mathquest.model.Fase;
import br.com.mathquest.model.Jogador;
import br.com.mathquest.model.Pergunta;
import br.com.mathquest.strategy.OperacaoMatematica;

import java.util.Random;
import java.util.Scanner;

public class JogoService {

    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    public void iniciarMundo(Jogador jogador, OperacaoMatematica operacao) {
        FabricaFase fabricaFase = new FabricaFaseMatematica(operacao);

        System.out.println();
        System.out.println("====================================");
        System.out.println("MUNDO DA " + operacao.getNome().toUpperCase());
        System.out.println("====================================");

        for (int nivel = 1; nivel <= 10; nivel++) {
            Fase fase = fabricaFase.criarFaseNormal(nivel);

            boolean passou = executarFase(jogador, fase);

            if (!passou) {
                System.out.println();
                System.out.println("Voce nao passou da fase.");
                System.out.println("Fim de jogo!");
                return;
            }
        }

        Fase chefe = fabricaFase.criarFaseChefe();
        boolean venceuChefe = executarFase(jogador, chefe);

        if (venceuChefe) {
            System.out.println();
            System.out.println("Parabens, " + jogador.getNome() + "!");
            System.out.println("Voce derrotou o chefe da " + operacao.getNome() + "!");
            System.out.println("Pontuacao final: " + jogador.getPontuacao());
        } else {
            System.out.println();
            System.out.println("Voce perdeu para o chefe final.");
            System.out.println("Pontuacao final: " + jogador.getPontuacao());
        }
    }

    private boolean executarFase(Jogador jogador, Fase fase) {
        int acertos = 0;

        System.out.println();
        System.out.println("------------------------------------");
        System.out.println(fase.getTitulo());
        System.out.println("Perguntas: " + fase.getQuantidadePerguntas());
        System.out.println("Acertos necessarios: " + fase.getAcertosNecessarios());
        System.out.println("------------------------------------");

        for (int i = 1; i <= fase.getQuantidadePerguntas(); i++) {
            Pergunta pergunta = gerarPergunta(fase);

            System.out.println();
            System.out.println("Pergunta " + i + ":");
            System.out.println(pergunta.getEnunciado());

            int respostaJogador = lerResposta();

            if (pergunta.verificarResposta(respostaJogador)) {
                System.out.println("Correto!");
                acertos++;
                jogador.adicionarPontos(fase.isChefe() ? 20 : 10);
            } else {
                System.out.println("Errado!");
                System.out.println("Resposta correta: " + pergunta.getRespostaCorreta());
            }
        }

        System.out.println();
        System.out.println("Resultado da fase: " + acertos + " acertos.");

        if (acertos >= fase.getAcertosNecessarios()) {
            System.out.println("Voce passou!");
            return true;
        }

        System.out.println("Voce nao atingiu os acertos necessarios.");
        return false;
    }

    private Pergunta gerarPergunta(Fase fase) {
        int numero1 = random.nextInt(fase.getLimiteNumeros()) + 1;
        int numero2 = random.nextInt(fase.getLimiteNumeros()) + 1;

        if (fase.getOperacao().getNome().equals("Divisao")) {
            numero2 = random.nextInt(fase.getLimiteNumeros()) + 1;
            int resultado = random.nextInt(fase.getLimiteNumeros()) + 1;
            numero1 = numero2 * resultado;
        }

        return new Pergunta(numero1, numero2, fase.getOperacao());
    }

    private int lerResposta() {
        while (!scanner.hasNextInt()) {
            System.out.println("Digite apenas numeros:");
            scanner.next();
        }

        return scanner.nextInt();
    }
}
