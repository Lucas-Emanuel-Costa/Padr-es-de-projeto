# Math Quest

Math Quest é um jogo educativo desenvolvido em Java para praticar operações matemáticas de forma simples e interativa. O jogador escolhe um mundo matemático, responde perguntas, avança por fases e tenta derrotar o chefe final.

O projeto possui duas formas de execução:

- Versão console, pela classe `Main`.
- Versão gráfica, pela classe `MainGUI`, usando Java Swing.

## Objetivo do projeto

O objetivo principal é demonstrar o uso de padrões de projeto em uma aplicação Java com uma proposta prática: um jogo de matemática dividido em fases.

Durante o jogo, o jogador pode escolher entre os mundos de:

- Soma
- Subtração
- Multiplicação
- Divisão

Cada mundo usa uma operação matemática diferente, mas mantém a mesma estrutura de jogo: fases normais, perguntas, pontuação e chefe final.

## Padrões de projeto utilizados

### Strategy

O padrão Strategy foi usado para representar as operações matemáticas do jogo.

Arquivos principais:

- `OperacaoMatematica`
- `Soma`
- `Subtracao`
- `Multiplicacao`
- `Divisao`

Esse padrão foi escolhido porque o jogo precisa trocar o comportamento do cálculo de acordo com o mundo selecionado pelo jogador. Em vez de colocar vários `if` ou `switch` espalhados pelo código para decidir se a conta é soma, subtração, multiplicação ou divisão, cada operação fica em sua própria classe.

Com isso, o jogo pode usar uma interface comum:

```java
operacao.calcular(numero1, numero2);
```

Assim, a lógica principal não precisa saber exatamente qual operação está sendo executada. Ela apenas usa a estratégia escolhida.

Motivos para usar Strategy:

- Facilita adicionar novas operações no futuro.
- Evita excesso de condicionais na lógica do jogo.
- Deixa cada operação matemática com sua própria responsabilidade.
- Torna o código mais organizado e fácil de manter.

### Factory Method

O padrão Factory Method foi usado para criar as fases do jogo.

Arquivos principais:

- `FabricaFase`
- `FabricaFaseMatematica`

Esse padrão foi escolhido porque a criação de fases possui regras próprias. O jogo tem fases normais e uma fase chefe, e cada uma delas precisa ser criada com informações específicas.

Em vez de criar fases diretamente em várias partes do código com:

```java
new Fase(...);
```

o projeto usa uma fábrica:

```java
fabrica.criarFaseNormal(1);
fabrica.criarFaseChefe();
```

Motivos para usar Factory Method:

- Centraliza a criação dos objetos `Fase`.
- Separa a lógica de criação da lógica principal do jogo.
- Facilita alterar como as fases são criadas.
- Deixa o código mais flexível para futuras fases especiais.

## Explicação do funcionamento

Ao iniciar o jogo, o jogador informa seu nome e escolhe o mundo que deseja jogar. Cada mundo corresponde a uma estratégia matemática diferente.

Depois disso, o jogo cria as fases usando a fábrica de fases. O jogador precisa responder perguntas geradas aleatoriamente. Ao acertar, ganha pontos e avança na fase. Caso alcance a quantidade mínima de acertos, passa para a próxima fase.

Depois das 10 fases normais, o jogador enfrenta o chefe final. A fase chefe possui mais dificuldade e pontuação maior.

Na versão gráfica, o jogo também possui:

- Tela inicial com escolha do mundo.
- Personagens simples desenhados como bonequinhos de palito.
- Mensagens de incentivo.
- Falas do rival.
- Pontuação em tempo real.
- Tela final de vitória ou derrota.

## Estrutura do projeto

```text
src/main/java
└── br
    └── com
        └── mathquest
            ├── Main.java
            ├── MainGUI.java
            ├── factory
            │   ├── FabricaFase.java
            │   └── FabricaFaseMatematica.java
            ├── gui
            │   └── MathQuestFrame.java
            ├── model
            │   ├── Fase.java
            │   ├── Jogador.java
            │   └── Pergunta.java
            ├── service
            │   └── JogoService.java
            └── strategy
                ├── Divisao.java
                ├── Multiplicacao.java
                ├── OperacaoMatematica.java
                ├── Soma.java
                └── Subtracao.java
```

## Como executar no IntelliJ IDEA

1. Abra o IntelliJ IDEA.
2. Clique em `File > Open`.
3. Selecione a pasta do projeto.
4. Aguarde o IntelliJ carregar o projeto Maven.
5. Para rodar a versão gráfica, abra:

```text
src/main/java/br/com/mathquest/MainGUI.java
```

6. Clique no botão verde ao lado do método `main`.
7. Escolha `Run 'MainGUI.main()'`.

## Como executar a versão console

Para rodar a versão no terminal, execute a classe:

```text
src/main/java/br/com/mathquest/Main.java
```

No IntelliJ, basta abrir o arquivo `Main.java` e clicar no botão verde ao lado do método `main`.

## Tecnologias utilizadas

- Java 17
- Maven
- Java Swing
- Programação orientada a objetos
- Padrões de projeto Strategy e Factory Method

## Autor

Lucas Emanuel Costa
