# Urna-Eletronica-POO
### Projeto da disciplina POO - UFS  
O projeto executa uma simulação de urna eletrônica com o objetivo de encerrar um dos debates mais polêmicos da sociedade atual: Biscoito x Bolacha.

## Integrantes:
Ytalo Santos Aragão
Yuri Dias Pereira

## Professor:
Kalil Araújo Bispo

## IDEs utilizadas:
Visual Studio Code/Codium (O Visual Studio Codium trata-se apenas de uma
compilação alternativa do Visual Studio Code onde os módulos de telemetria são excluídos do
código fonte antes da compilação)

## Informações sobre o Desenvolvimento
- Linguagem utilizada: Java
- Ambiente de desenvolvimento: Windows e Linux Mint
- Distribuição Java: OpenJDK
- Versão do Java: 11

## Requisitos
- Instalação JavaJDK 11 ou superior

## Instruções de Instalação
Baixar o projeto zip e realizar descompatação.

## Instruções de Uso
Com o projeto baixado, executar o shell dentro da pasta src do projeto. Em seguida, rodar o comando:  

javac main/Main.java  

Caso apresente problemas para visualização de acentos ou caracteres especiais, configure a compilação com o seguinte comando:  

`javac -encoding utf-8 main/Main.java`  

Uma vez compilado, basta executar o comando:  

`java main/Main`

A aplicação já irá gerar os arquivos com os dados dos candidatos, mas os eleitores podem ser cadastrados pelo usuário através do módulo de mesário.  
O menu principal apresenta quatro botões: Votar, Módulo Mesário, Resultados das Eleições, Sair.

- Botão Sair: Encerra a aplicação;
- Botão Resultados das Eleições: Mostra os votos computados separados entre os respectivos candidatos e a soma de brancos e nulos. Caso não existam votos computados, uma mensagem de erro é exibida para o usuário.
- Botão Módulo Mesário: Permite o mesário responsável pela votação administrar a eleição, podendo cadastrar novos eleitores, autorizar eleitores cadastrados a votar e reiniciar a contagem de votos;
- Botão Votar: Uma vez autorizado, o eleitor pode votar em seu candidatos através da urna que simula uma versão digital do aparelho convencional que o sistema eleitoral brasileiro faz uso no presente momento.

## Descrição geral:
O modo como a aplicação foi construída permite que outros tipos de eleições sejam
realizadas porém, Biscoito X Bolacha é a eleição prédefinida e realizar outras eleições
exigiria alguns ajustes na interface gráfica.

O algoritmo de criptografia hash consiste em usar a função fornecida pelo professor, sem
alterações de qualquer natureza, para gerar um hash correspondente aos dados dos campos
contidos em cada linha e naõ da linha inteira de modo que o nome do campo seja ignorado no
processo de criptografia.
Exemplo:
    Campo:dado
    Neste caso, apenas a string "dado" será passada para a função geradora de hash.
No arquivo de controle, o hash de cada arquivo é gerado considerando o conteúdo integral do
arquivo, diferente do hash linha a linha mencionado anteriormente

Na execução inicial, um menu principal apresenta a eleição pré-determinada e permite iniciar
os módulos do programa como, por exemplo, a tela de votação e um módulo de mesário capaz de
gerenciar todo o sistema de votação. Dessa forma, o menu principal funciona como uma
introdução ao programa, o módulo de mesário como um painel de administrador e a tela de
votação como o a principal fonte de entrada de dados e onde o objetivo principal deve ser
realmente acançado.