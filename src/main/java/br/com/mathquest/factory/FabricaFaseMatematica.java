package br.com.mathquest.factory;

import br.com.mathquest.model.Fase;
import br.com.mathquest.strategy.OperacaoMatematica;

public class FabricaFaseMatematica implements FabricaFase {

    private final OperacaoMatematica operacao;

    public FabricaFaseMatematica(OperacaoMatematica operacao) {
        this.operacao = operacao;
    }

    @Override
    public Fase criarFaseNormal(int nivel) {
        return new Fase(nivel, false, operacao);
    }

    @Override
    public Fase criarFaseChefe() {
        return new Fase(11, true, operacao);
    }
}
