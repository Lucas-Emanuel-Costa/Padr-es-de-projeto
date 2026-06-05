package br.com.mathquest;

import br.com.mathquest.facade.MathQuestFacade;
import br.com.mathquest.strategy.Divisao;
import br.com.mathquest.strategy.Multiplicacao;
import br.com.mathquest.strategy.OperacaoMatematica;
import br.com.mathquest.strategy.Soma;
import br.com.mathquest.strategy.Subtracao;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Instanciamos o nosso Facade (Fachada)
        MathQuestFacade facade = new MathQuestFacade();

        System.out.println("====================================");
        System.out.println("        BEM-VINDO AO MATH QUEST     ");
        System.out.println("====================================");

        System.out.print("Digite o seu nome: ");
        String nome = scanner.nextLine();

        System.out.println("\nEscolha o mundo que deseja enfrentar:");
        System.out.println("1 - Mundo da Soma");
        System.out.println("2 - Mundo da Subtracao");
        System.out.println("3 - Mundo da Multiplicacao");
        System.out.println("4 - Mundo da Divisao");
        System.out.print("Sua escolha: ");
        int opcao = scanner.nextInt();

        OperacaoMatematica operacao;
        switch (opcao) {
            case 1:
                operacao = new Soma();
                break;
            case 2:
                operacao = new Subtracao();
                break;
            case 3:
                operacao = new Multiplicacao();
                break;
            case 4:
                operacao = new Divisao();
                break;
            default:
                System.out.println("Opcao invalida. Iniciando no Mundo da Soma por padrao.");
                operacao = new Soma();
                break;
        }

        // Delegação limpa para o Facade iniciar toda a complexidade
        facade.iniciarPartida(nome, operacao);

        scanner.close();
    }
}