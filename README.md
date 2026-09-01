# ORM, Serialização de Objetos e Interface Gráfica com Java

Projeto desenvolvido como atividade prática da disciplina de **SOFTWARE PARA PERSISTÊNCIA DE DADOS**, ministrada pelo professor MARCELO AKIRA INUZUKA, no Instituto de Informática - UFG, no semestre 2026.2.

O projeto foi construído em **três etapas evolutivas**:

1. **ORM com ORMLite** - mapeamento de objetos Java para banco de dados SQLite com operações CRUD
2. **Serialização de dados** - exportação e importação de objetos nos formatos JSON e XML, com classes utilitárias dedicadas
3. **Interface gráfica JavaFX** - tela de cadastro com operações CRUD, busca por nome e seletor de datas no formato brasileiro

---

## Tecnologias

- Java 17
- Maven
- [ORMLite](https://ormlite.com/) - mapeamento objeto-relacional
- [SQLite](https://www.sqlite.org/) - banco de dados embutido
- [Gson](https://github.com/google/gson) - serialização JSON
- [JAXB](https://docs.oracle.com/javase/tutorial/jaxb/) - serialização XML
- [JavaFX 17](https://openjfx.io/) - interface gráfica

---

## Estrutura do projeto

```
src/
├── main/
│   ├── java/
│   │   ├── model/
│   │   │   ├── Database.java              # Gerencia a conexão com o banco SQLite
│   │   │   ├── Human.java                 # Entidade mapeada para o banco, XML e JSON
│   │   │   ├── HumanList.java             # Wrapper para serialização XML de listas
│   │   │   ├── HumanJsonSerializer.java   # Serialização/desserialização JSON via Gson
│   │   │   ├── HumanXmlSerializer.java    # Serialização/desserialização XML via JAXB
│   │   │   └── HumanRepository.java       # Repositório CRUD e importação/exportação
│   │   ├── view/
│   │   │   ├── AppView.java               # Inicializa e exibe a janela JavaFX
│   │   │   └── HumanFX.java               # Representação da entidade para a TableView
│   │   ├── controller/
│   │   │   └── AppController.java         # Controla interações da interface com o banco
│   │   └── Main.java                      # Testes de serialização via console
│   └── resources/
│       └── view/
│           └── app.fxml                   # Layout GUI
```

---

## Entidade

A entidade `Human` possui os seguintes atributos:

| Atributo   | Tipo     | Descrição                       |
|------------|----------|---------------------------------|
| `id`       | `int`    | Identificador gerado pelo banco |
| `fullName` | `String` | Nome completo                   |
| `income`   | `double` | Renda mensal                    |
| `birthday` | `Date`   | Data de nascimento              |

---

## Funcionalidades

### Interface gráfica (JavaFX)

| Ação | Descrição |
|---|---|
| Adicionar | Habilita os campos para inserção de novo registro |
| Salvar | Persiste o registro no banco e exibe na tabela |
| Atualizar | Edita o registro selecionado na tabela |
| Deletar | Remove o registro selecionado |
| Cancelar | Descarta a operação em andamento e limpa os campos |
| Buscar | Filtra registros pelo nome (busca parcial) |
| Limpar Busca | Restaura a listagem completa |

### CRUD via repositório

| Método | Descrição |
|---|---|
| `create(Human)` | Persiste um objeto no banco |
| `loadFromId(int)` | Busca um registro pelo ID |
| `loadAll()` | Retorna todos os registros |
| `update(Human)` | Atualiza um registro existente |
| `delete(Human)` | Remove um registro do banco |
| `searchByName(String)` | Busca registros com nome parcialmente correspondente |

### Exportação

| Método | Descrição |
|---|---|
| `dumpData(String formato)` | Retorna todos os registros como string JSON ou XML |
| `dumpFile(String formato, File arquivo)` | Salva todos os registros em arquivo JSON ou XML |

### Importação

| Método | Descrição |
|---|---|
| `createFromJSON(String json)` | Cria um objeto a partir de uma string JSON |
| `createFromXML(String xml)` | Cria um objeto a partir de uma string XML |
| `importData(String formato, String data)` | Importa múltiplos objetos de uma string JSON ou XML |
| `importFile(String formato, File arquivo)` | Importa objetos de um arquivo JSON ou XML |

---

## Pré-requisitos

- Java 17+
- Maven 3.6+
- IntelliJ IDEA (recomendado)

---

## Como executar

### Interface gráfica (JavaFX)

1. Clone o repositório:
```bash
git clone https://github.com/Kelvin-de-Oliveira/data-serialization.git
```

2. Acesse a pasta do projeto:
```bash
cd data-serialization
```

3. Execute:
```bash
mvn javafx:run
```

---

### Testes de serialização via console

Execute a classe `Main.java` pelo menu **Run → Run 'Main'**.

A execução realiza os seguintes testes em sequência:

| Teste | O que valida |
|---|---|
| `dumpData("json")` | Exporta todos os registros como JSON no console |
| `dumpData("xml")` | Exporta todos os registros como XML no console |
| `dumpFile("json", ...)` | Gera o arquivo `humans.json` na raiz do projeto |
| `dumpFile("xml", ...)` | Gera o arquivo `humans.xml` na raiz do projeto |
| `createFromJSON(...)` | Cria um registro a partir de uma string JSON |
| `createFromXML(...)` | Cria um registro a partir de uma string XML |
| `importData("json", ...)` | Importa múltiplos registros de uma string JSON |
| `importFile("xml", ...)` | Importa registros do arquivo `humans.xml` gerado |
| Verificação final | Lista todos os registros presentes no banco |

---

## Arquivos gerados

O repositório inclui os arquivos abaixo, gerados durante os testes:

| Arquivo | Descrição |
|---|---|
| `human.db` | Banco de dados SQLite com os registros persistidos |
| `humans.json` | Exportação dos registros em formato JSON |
| `humans.xml` | Exportação dos registros em formato XML |

### Gerando os arquivos do zero

Caso queira reproduzir a execução a partir de um estado limpo, apague os três arquivos antes de rodar:

**Linux/Mac:**
```bash
rm human.db humans.json humans.xml
```

**Windows:**
```bash
del human.db humans.json humans.xml
```

Na próxima execução, o banco e os arquivos serão recriados automaticamente.

---

## Dependências (pom.xml)

```xml
<dependencies>
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.46.0.0</version>
    </dependency>
    <dependency>
        <groupId>com.j256.ormlite</groupId>
        <artifactId>ormlite-core</artifactId>
        <version>6.1</version>
    </dependency>
    <dependency>
        <groupId>com.j256.ormlite</groupId>
        <artifactId>ormlite-jdbc</artifactId>
        <version>6.1</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>2.0.13</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>2.0.13</version>
    </dependency>
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.11.0</version>
    </dependency>
    <dependency>
        <groupId>javax.xml.bind</groupId>
        <artifactId>jaxb-api</artifactId>
        <version>2.3.1</version>
    </dependency>
    <dependency>
        <groupId>org.glassfish.jaxb</groupId>
        <artifactId>jaxb-runtime</artifactId>
        <version>2.3.9</version>
    </dependency>
    <dependency>
        <groupId>com.sun.istack</groupId>
        <artifactId>istack-commons-runtime</artifactId>
        <version>4.1.2</version>
    </dependency>
    <dependency>
        <groupId>jakarta.activation</groupId>
        <artifactId>jakarta.activation-api</artifactId>
        <version>1.2.2</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>17.0.6</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>17.0.6</version>
    </dependency>
</dependencies>
```

---

## Autor

**Kelvin de Oliveira**
[![GitHub](https://img.shields.io/badge/GitHub-kelvin--de--oliveira-181717?logo=github)](https://github.com/kelvin-de-oliveira)

---

## Referências

- [Tutorial ORM - marceloakira](https://github.com/marceloakira/tutorials/tree/main/orm)
- [Tutorial Serialização - marceloakira](https://github.com/marceloakira/tutorials/tree/main/serializacao_xml_json)
- [Tutorial JavaFX CRUD - marceloakira](https://github.com/marceloakira/tutorials/tree/main/javafx-crud)
- [Documentação ORMLite](https://ormlite.com/javadoc/ormlite-core/doc-files/ormlite.html)
- [Documentação Gson](https://github.com/google/gson/blob/main/UserGuide.md)
- [Documentação JAXB](https://docs.oracle.com/javase/tutorial/jaxb/)
- [Documentação JavaFX](https://openjfx.io/openjfx-docs/)
- [JavaFX com Maven - OpenJFX](https://openjfx.io/openjfx-docs/#IDE-Intellij)