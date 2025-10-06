# 🎰 Projeto Cassino CLI (Command Line Interface) em Java

Este projeto é uma simulação de um mini-cassino, implementado puramente em linha de comando (CLI) utilizando Java. Ele permite ao usuário jogar três jogos de azar populares, gerenciando uma banca virtual. O foco principal está na aplicação de conceitos de Orientação a Objetos (POO), design de software modular e implementação de lógicas complexas de jogo e probabilidade.

## 🎲 Jogos Disponíveis

O cassino oferece uma seleção de três jogos clássicos, cada um implementado em um módulo separado:

### 🃏 Blackjack (21)
* **Regras Clássicas:** Implementação das regras básicas do Blackjack, incluindo ações como **Puxar** (Hit), **Parar** (Stand) e **Dobrar** (Double Down).
* **Contagem de Cartas:** O valor da mão é calculado dinamicamente, tratando o Ás como 1 ou 11 para evitar estourar (Bust).
* **Pagamento de Blackjack:** Pagamento de 2.5x para Blackjack natural (21 com duas cartas).
* **Visualização:** Representação visual das cartas no terminal (ASCII art).

### 🎰 Caça-Níquel (Slot Machine)
* **Sistema de Probabilidade:** Utiliza `Enums` para definir símbolos, seus multiplicadores de pagamento e pesos de probabilidade, garantindo uma distribuição realista (e desfavorável ao jogador) dos resultados.
* **Combinações Vencedoras:** Analisa combinações vencedoras em 8 linhas diferentes (3 horizontais, 3 verticais, 2 diagonais).
* **Animação:** Simulação de giro dos rolos com parada sequencial, utilizando controle de terminal e *threads* para um efeito visual atraente.
* **Aparência:** Uso de cores ANSI para destacar a interface e os símbolos.

### 🎲 Bac-Bo (Jogo de Dados)
* **Regras:** Um jogo simples de dados onde o jogador aposta e compara a soma de dois lances de dados com a soma do Dealer.
* **Visualização:** Animação de rolagem de dados em ASCII Art para cada um dos dois lances.
* **Estrutura de Dados:** Uso de `Records` para encapsular e retornar de forma clara os resultados dos lances de dados.

## ✨ Habilidades Técnicas e Conceitos Destacados

Este projeto demonstra proficiência nas seguintes áreas de desenvolvimento em Java:

| Habilidade | Aplicação no Projeto |
| :--- | :--- |
| **Programação Orientada a Objetos (POO)** | Uso extensivo de classes, herança (`Pessoa`, `Jogador`, `Dealer`), abstração (`Pessoa`) e composição (`Jogador` tem uma `Banca`). |
| **Design Modular** | Separação de lógicas de jogo (`Blackjack`, `Caca_Niquel`, `Bac_Bo`) e recursos (`Recursos`) em pacotes distintos. |
| **Gerenciamento Financeiro** | A classe `Banca` encapsula o saldo do jogador, armazenando-o em **centavos** (`int`) para evitar erros de ponto flutuante, garantindo precisão em todas as transações. |
| **Controle de Terminal (CLI)** | Implementação de limpeza de tela (`Control.limparTerminal()`) e uso de códigos ANSI (`CoresANSI`) para melhorar a experiência visual do usuário. |
| **Lógica de Jogo e Probabilidade** | Implementação de sistemas de sorteio complexos (Baralho, Roleta do Caça-Níquel com pesos) e regras de pagamento específicas para cada jogo. |
| **Validação de Entrada** | A classe `Input` centraliza a leitura e validação de dados do usuário (inteiros, strings, valores em moeda), garantindo a robustez do programa. |

## 📐 Estruturas de Dados Utilizadas

O projeto utiliza várias estruturas de dados fundamentais para sua operação:

* **`ArrayList`**: Utilizado primariamente na classe `Baralho` para gerenciar o deck de cartas (adição, embaralhamento e remoção de cartas) e na classe `Mao` para gerenciar as cartas de um jogador.
* **`Enum`**: A classe `Simbolo` (`src/Caca_Niquel/Recursos/Simbolo.java`) é um `Enum` poderoso que armazena a representação, o multiplicador de pagamento, o peso de probabilidade e a cor ANSI de cada símbolo do Caça-Níquel.
* **`Array` Multidimensional (`Simbolo[][]`)**: Usado no Caça-Níquel para representar o grid 3x3 dos rolos e facilitar a análise de combinações vencedoras.
* **`Record`**: Utilizado no Bac-Bo (`ResultadoDados`) para criar uma classe de dados concisa e imutável, ideal para retornar os resultados dos dois dados em um único objeto.

## 🚀 Como Rodar o Projeto

1.  **Pré-requisitos:** Certifique-se de ter o JDK (Java Development Kit) instalado.
2.  **Compilação:** Navegue até o diretório `src` e compile os arquivos:
    ```bash
    javac Cassino.java
    ```
3.  **Execução:** Execute a classe principal:
    ```bash
    java Cassino
    ```
4.  **Interação:** Siga as instruções do terminal para definir sua banca inicial e escolher o jogo.
