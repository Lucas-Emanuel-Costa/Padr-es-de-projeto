package br.com.mathquest.factory;

import br.com.mathquest.model.Fase;

public interface FabricaFase {

    Fase criarFaseNormal(int nivel);

    Fase criarFaseChefe();
}
