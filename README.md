# BeautyBooker - Aplicação JavaFX

## Descrição

O BeautyBooker é um projeto JavaFX que visa criar uma aplicação para gerenciamento de agendamentos em salões de beleza. Ele utiliza tecnologias modernas, como JavaFX para a interface gráfica e Maven para gerenciamento de dependências e construção do projeto.

## Requisitos

Certifique-se de ter as seguintes ferramentas instaladas em seu ambiente de desenvolvimento:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)

## Configuração do Projeto

1. Clone o repositório:

    ```bash
    git clone https://caminho/do/repo.git
    cd BeautyBooker
    ```

2. Importe o projeto em sua IDE preferida como um projeto Maven.

3. Certifique-se de que as dependências do Maven foram baixadas.

## Dependências Principais

O projeto faz uso das seguintes dependências:

- **JavaFX**: Fornece componentes gráficos para a interface do usuário.
- **ControlsFX**: Biblioteca de controles adicionais para JavaFX.
- **ValidatorFX**: Biblioteca para validação de campos de entrada.
- **BootstrapFX**: Conjunto de estilos Bootstrap para JavaFX.
- **JFoenix**: Biblioteca de componentes JavaFX com um estilo de Material Design.
- **Gson**: Biblioteca para serialização e desserialização de objetos Java em JSON.
- **JJWT (Java JWT)**: Biblioteca para trabalhar com JSON Web Tokens.

## Execução do Projeto

O projeto utiliza o plugin `javafx-maven-plugin` para execução e criação de distribuições. Para executar a aplicação, utilize o seguinte comando Maven:

```bash
mvn clean javafx:run
```

## Construção e Distribuição

Para criar uma distribuição do aplicativo, utilize o seguinte comando:

```bash
mvn clean javafx:jlink
```

Isso criará um diretório `target/app` contendo a distribuição do aplicativo.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.

## Licença

Este projeto é licenciado sob a Licença MIT - consulte o arquivo [LICENSE](LICENSE) para obter detalhes.

## Contato
**Autor:** Vinicius Santos Carneiro  
**GitHub:** [ViniciuCarneiro](https://github.com/ViniciuCarneiro)  
**Homepage:** [https://github.com/ViniciuCarneiro/tcc-api#readme](https://github.com/ViniciuCarneiro/tcc-api#readme)
