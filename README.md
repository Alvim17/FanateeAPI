# Introdução #
A solução para o desafio proposto consiste em duas aplicações distintas (FanateeConsole e FanateeAPI), ambas desenvolvidas em Scala.
Para compilar e rodar localmente as duas aplicações, é necessário ter instalado:
- JDK 11
- SBT 1.5.5 ou superior (https://www.scala-sbt.org/1.x/docs/Setup.html)

## FanateeConsole ##
Navegar ao diretório raiz da pasta "FanateeConsole" e rodar os comandos conforme exemplos abaixo.
Exemplo de navegação ao diretório raiz:
```shell
$ cd C:\Users\Alvim\FanateeConsole
```
Inicialização do SBT:
```shell
$ sbt
```
Para rodar os testes unitários
```shell
$ test
```
Para compilar e rodar a aplicação, passando como parâmetro o nome do arquivo .csv com os custos das rotas (este arquivo deve estar na pasta raiz):
```shell
$ run "exemplo.csv"
```

## Interagindo com o sistema via console ##
Inicialmente o sistema irá solicitar uma rota a ser calculada, no formato "TER-MAR"
```shell
Please enter the route:
```
Após fornecida uma rota de entrada, o sistema retorna a rota de menor custo (com o respectivo custo):
```shell
Best route: TER - MAR - Cost: 10
```
Em seguida, o usuário pode fazer uma nova consulta ou sair do sistema, através de uma interação intuitiva com o console.

## FanateeAPI ##
Foi utilizado framework Play (https://www.playframework.com/) para desenvolvimento da API REST.
Obs: o arquivo .csv com os custos das rotas se encontra no diretório raiz e sua localização/nome não devem ser alterados.
Navegar ao diretório raiz da pasta "FanateeAPI" e rodar os comandos conforme exemplos abaixo.
Exemplo de navegação ao diretório raiz:
```shell
$ cd C:\Users\Alvim\FanateeAPI
```
Inicialização do SBT:
```shell
$ sbt
```
Para rodar os testes unitários
```shell
$ test
```
Para compilar e rodar a aplicação, passando como parâmetro o número da porta onde a aplicação irá subir (se não for passado nenhum parâmetro, será usada a porta 9000 como padrão).
Exemplo usando porta 5000.
```shell
$ run 5000
```
Exemplo usando porta padrão (9000):
```shell
$ run
```

## Interagindo com a API via browser e alguma ferramenta que faça HTTP POST ##
Acessando http://localhost:9000/ já é possível verificar que a aplicação está rodando.

A API possui um método GET (para cálculo de rota ótima) e outro método POST (para cadastro de nova rota), que podem ser usados conforme exemplos abaixo.

1. Método GET (melhorTrajeto):
- http://localhost:9000/melhorTrajeto/TER/NET
    Calcula a rota de menor custo entre "TER" e "NET", retornando o seguinte texto:

    ```shell
    (35,List(TER, MAR, LUA, NET))
    ```
    Este retorno indica que a rota ótima tem custo 35 e o caminho é : TER -> MAR -> LUA -> NET.
    
- http://localhost:9000/melhorTrajeto/TER/TER
    Calcula a rota de menor custo entre "TER" e "TER", retornando o seguinte texto:

    ```shell
    (0,List(TER))
    ```
    Como origem e destino são iguais, este retorno indica que a rota ótima tem custo zero e o caminho é o próprio planeta de origem/destino.
    
- http://localhost:9000/melhorTrajeto/TER/PlanetaInexistenteNaBase
    Calcula a rota de menor custo entre "TER" e "PlanetaInexistenteNaBase", retornando o seguinte texto:

    ```shell
    (0,List())
    ```
    Como se trata de um caminho impossível, este retorno indica que a rota ótima tem custo zero mas o caminho é vazio.

2. Método POST (cadastrarRota):
Utilizando uma ferramenta para efetuar requisições POST, é possível cadastrar uma nova rota no arquivo .csv, inserido no Body da requisição um string no formato descrito a seguir.
- http://localhost:9000/cadastrarRota
 Body da requisição (plain text):
    ```shell
    SOL,MAR,9
    ```
    Este método insere uma nova rota de "SOL" para "MAR", com custo 9.