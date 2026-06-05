# Math Quest: O Desafio Matemático

O **Math Quest** é um jogo educativo desenvolvido em Java que une raciocínio lógico e arquitetura de software de alto nível. O objetivo do jogador é percorrer mundos matemáticos, vencer desafios em fases progressivas e derrotar o chefe final.

## 🚀 Sobre o Projeto
Este projeto foi desenvolvido com foco em **Programação Orientada a Objetos (POO)** e a aplicação de **Padrões de Projeto (Design Patterns)** clássicos, visando um código limpo, modular e de fácil manutenção.

O sistema permite jogar tanto via console (Terminal) quanto via interface gráfica (Java Swing).

## 🛠️ Padrões de Projeto Implementados
Para atingir um alto grau de desacoplamento e robustez, foram implementados 4 padrões de projeto fundamentais:

| Padrão | Categoria | Objetivo no Projeto |
| :--- | :--- | :--- |
| **Strategy** | Comportamental | Definir e trocar dinamicamente as operações matemáticas (Soma, Subtração, etc.). |
| **Factory Method** | Criacional | Centralizar a criação e configuração das fases (Normais vs. Chefe). |
| **Singleton** | Criacional | Garantir uma instância única para o motor do jogo (`JogoService`), protegendo o estado da pontuação. |
| **Facade** | Estrutural | Simplificar a inicialização do sistema, ocultando a complexidade de instanciamento da classe `Main`. |

## 🏗️ Arquitetura do Sistema
O projeto segue uma organização modular, facilitando a navegação e a manutenção:

```text
src/main/java/br/com/mathquest/
+-- facade      # Interface unificada para inicialização
+-- factory     # Lógica de criação de fases
+-- gui         # Interface gráfica (Swing)
+-- model       # Entidades de negócio (Jogador, Fase, Pergunta)
+-- service     # Motor do jogo (Singleton)
+-- strategy    # Algoritmos de cálculo

💻 Tecnologias Utilizadas
Java 17+

Maven (Gerenciamento de dependências)

Java Swing (Interface Gráfica)

Arquitetura Orientada a Objetos

👥 Autores
Silas Novaes de Luna Gomes

Lucas Emanuel Costa

🚀 Como Executar
Clone este repositório.

Abra o projeto no IntelliJ IDEA.

Aguarde o Maven carregar as dependências.

Para versão console: Execute br.com.mathquest.Main.java.

Para versão gráfica: Execute br.com.mathquest.MainGUI.java.

Desenvolvido como projeto acadêmico focado em excelência de software.

