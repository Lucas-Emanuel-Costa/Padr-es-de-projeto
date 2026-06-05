# Math Quest

Math Quest e um jogo educativo desenvolvido em Java para praticar operacoes matematicas de forma simples e interativa. O jogador escolhe um mundo matematico, responde perguntas, avanca por fases e tenta derrotar o chefe final.

O projeto foi desenvolvido com programacao orientada a objetos e implementa **2 padroes de projeto**, atendendo ao requisito minimo solicitado:

- **Strategy**
- **Factory Method**

O jogo possui duas formas de execucao:

- Versao console, pela classe `Main`.
- Versao grafica, pela classe `MainGUI`, usando Java Swing.

## Objetivo do projeto

O objetivo do projeto e demonstrar, na pratica, como padroes de projeto podem organizar melhor uma aplicacao Java.

A ideia escolhida foi um jogo de matematica chamado **Math Quest**, no qual o jogador passa por fases resolvendo contas. Cada mundo representa uma operacao matematica diferente:

- Soma
- Subtracao
- Multiplicacao
- Divisao

Cada fase possui perguntas, quantidade minima de acertos e pontuacao. Depois das fases normais, o jogador enfrenta uma fase chefe.

## Padroes de projeto implementados

Este projeto implementa dois padroes de projeto principais:

| Padrao | Onde foi aplicado | Motivo |
| --- | --- | --- |
| Strategy | Operacoes matematicas | Permitir trocar o tipo de calculo sem alterar a logica principal do jogo |
| Factory Method | Criacao das fases | Centralizar a criacao de fases normais e fase chefe |

## 1. Strategy

O padrao **Strategy** foi utilizado para representar as operacoes matematicas do jogo.

Arquivos relacionados:

- `br.com.mathquest.strategy.OperacaoMatematica`
- `br.com.mathquest.strategy.Soma`
- `br.com.mathquest.strategy.Subtracao`
- `br.com.mathquest.strategy.Multiplicacao`
- `br.com.mathquest.strategy.Divisao`

### Como funciona

A interface `OperacaoMatematica` define o contrato que todas as operacoes devem seguir:

```java
int calcular(int numero1, int numero2);

String getNome();

String getSimbolo();
```

Cada operacao implementa essa interface de uma forma diferente.

Exemplo:

```java
public class Soma implements OperacaoMatematica {

    @Override
    public int calcular(int numero1, int numero2) {
        return numero1 + numero2;
    }
}
```

Assim, o jogo nao precisa saber se esta fazendo soma, subtracao, multiplicacao ou divisao. Ele apenas chama:

```java
operacao.calcular(numero1, numero2);
```

### Motivo da utilizacao

O Strategy foi escolhido porque o comportamento do calculo muda de acordo com o mundo escolhido pelo jogador.

Sem esse padrao, seria necessario espalhar varios `if` ou `switch` pelo codigo para verificar qual operacao deve ser executada.

Com o Strategy:

- Cada operacao fica em sua propria classe.
- O codigo principal fica mais limpo.
- Fica mais facil adicionar novas operacoes no futuro.
- A manutencao se torna mais simples.

Por exemplo, se fosse necessario criar um novo mundo de potenciacao, bastaria criar uma nova classe implementando `OperacaoMatematica`.

## 2. Factory Method

O padrao **Factory Method** foi utilizado para criar as fases do jogo.

Arquivos relacionados:

- `br.com.mathquest.factory.FabricaFase`
- `br.com.mathquest.factory.FabricaFaseMatematica`

### Como funciona

A interface `FabricaFase` define os metodos que uma fabrica de fases deve possuir:

```java
Fase criarFaseNormal(int nivel);

Fase criarFaseChefe();
```

A classe `FabricaFaseMatematica` implementa essa interface e fica responsavel por criar os objetos `Fase`.

Exemplo:

```java
public Fase criarFaseNormal(int nivel) {
    return new Fase(nivel, false, operacao);
}

public Fase criarFaseChefe() {
    return new Fase(11, true, operacao);
}
```

Com isso, a logica principal do jogo nao precisa criar fases diretamente usando `new Fase(...)`. Ela apenas solicita para a fabrica:

```java
fabricaFase.criarFaseNormal(nivel);
fabricaFase.criarFaseChefe();
```

### Motivo da utilizacao

O Factory Method foi escolhido porque a criacao de fases possui regras proprias.

No jogo existem:

- Fases normais
- Fase chefe

Cada tipo de fase pode ter configuracoes diferentes, como nivel, dificuldade, quantidade de perguntas e pontuacao.

Com o Factory Method:

- A criacao das fases fica centralizada.
- A logica principal do jogo fica mais organizada.
- Fica mais facil alterar a forma como as fases sao criadas.
- Fica mais simples adicionar novos tipos de fase futuramente.

## Explicacao do projeto

Ao iniciar o jogo, o jogador informa seu nome e escolhe um mundo matematico.

Depois disso, o sistema cria a operacao correspondente ao mundo escolhido. Essa operacao e usada como uma estrategia de calculo.

Em seguida, o jogo cria as fases usando uma fabrica. O jogador responde perguntas geradas aleatoriamente. Ao acertar, ganha pontos. Ao atingir a quantidade minima de acertos, passa para a proxima fase.

Depois de completar as 10 fases normais, o jogador enfrenta o chefe final. A fase chefe tem maior dificuldade e vale mais pontos.

Na versao grafica, o jogo tambem possui:

- Tela inicial com nome do jogador e escolha do mundo.
- Personagens simples desenhados como bonequinhos de palito.
- Mensagens de incentivo durante a partida.
- Falas do rival.
- Pontuacao em tempo real.
- Tela final de vitoria ou derrota.

## Estrutura do projeto

```text
src/main/java
+-- br
    +-- com
        +-- mathquest
            +-- Main.java
            +-- MainGUI.java
            +-- factory
            |   +-- FabricaFase.java
            |   +-- FabricaFaseMatematica.java
            +-- gui
            |   +-- MathQuestFrame.java
            +-- model
            |   +-- Fase.java
            |   +-- Jogador.java
            |   +-- Pergunta.java
            +-- service
            |   +-- JogoService.java
            +-- strategy
                +-- Divisao.java
                +-- Multiplicacao.java
                +-- OperacaoMatematica.java
                +-- Soma.java
                +-- Subtracao.java
```

## Como executar no IntelliJ IDEA

1. Abra o IntelliJ IDEA.
2. Clique em `File > Open`.
3. Selecione a pasta do projeto.
4. Aguarde o IntelliJ carregar o projeto Maven.
5. Para rodar a versao grafica, abra:

```text
src/main/java/br/com/mathquest/MainGUI.java
```

6. Clique no botao verde ao lado do metodo `main`.
7. Escolha `Run 'MainGUI.main()'`.

## Como executar a versao console

Para rodar a versao no terminal, execute a classe:

```text
src/main/java/br/com/mathquest/Main.java
```

No IntelliJ, basta abrir o arquivo `Main.java` e clicar no botao verde ao lado do metodo `main`.

## Tecnologias utilizadas

- Java 17
- Maven
- Java Swing
- Programacao orientada a objetos
- Padrao Strategy
- Padrao Factory Method

## Autor

Lucas Emanuel Costa
