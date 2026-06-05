package br.com.mathquest;

import br.com.mathquest.model.Jogador;
import br.com.mathquest.service.JogoService;
import br.com.mathquest.strategy.Divisao;
import br.com.mathquest.strategy.Multiplicacao;
import br.com.mathquest.strategy.OperacaoMatematica;
import br.com.mathquest.strategy.Soma;
import br.com.mathquest.strategy.Subtracao;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JogoService jogoService = new JogoService();

        System.out.println("====================================");
        System.out.println("         MATH QUEST");
        System.out.println("====================================");
        System.out.println("Bem-vindo ao jogo de matematica!");

        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();

        Jogador jogador = new Jogador(nome);

        OperacaoMatematica operacaoEscolhida = escolherOperacao(scanner);

        if (operacaoEscolhida == null) {
            System.out.println("Opcao invalida. O jogo sera encerrado.");
            return;
        }

        jogoService.iniciarMundo(jogador, operacaoEscolhida);

        System.out.println();
        System.out.println("Obrigado por jogar, " + jogador.getNome() + "!");
    }

    private static OperacaoMatematica escolherOperacao(Scanner scanner) {
        System.out.println();
        System.out.println("Escolha o mundo que deseja jogar:");
        System.out.println("1 - Mundo da Soma");
        System.out.println("2 - Mundo da Subtracao");
        System.out.println("3 - Mundo da Multiplicacao");
        System.out.println("4 - Mundo da Divisao");
        System.out.print("Opcao: ");

        int opcao = lerOpcao(scanner);

        switch (opcao) {
            case 1:
                return new Soma();
            case 2:
                return new Subtracao();
            case 3:
                return new Multiplicacao();
            case 4:
                return new Divisao();
            default:
                return null;
        }
    }

    private static int lerOpcao(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Digite apenas numeros.");
            scanner.next();
            System.out.print("Opcao: ");
        }

        return scanner.nextInt();
    }
}
