package br.com.mathquest.facade;

import br.com.mathquest.model.Jogador;
import br.com.mathquest.service.JogoService;
import br.com.mathquest.strategy.OperacaoMatematica;

/**
 * Padrão Facade: Esconde a complexidade de inicialização do jogo
 * fornecendo uma interface simples para as classes Main e MainGUI.
 */
public class MathQuestFacade {

    public void iniciarPartida(String nomeJogador, OperacaoMatematica operacao) {
        // Encapsula a criação do jogador
        Jogador jogador = new Jogador(nomeJogador);

        // Utiliza o Singleton para recuperar a instância do serviço e iniciar
        JogoService.getInstancia().iniciarMundo(jogador, operacao);
    }
}