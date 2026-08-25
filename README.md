# ORM e Serialização de Objetos com Java

Projeto desenvolvido como atividade prática da disciplina de SOFTWARE PARA PERSISTÊNCIA DE DADOS, ministrada pelo professor MARCELO AKIRA INUZUKA, no Instituto de Informática - UFG, no semestre 2026.2. Demonstra o uso de **ORMLite** para persistência de objetos em banco de dados SQLite e a **serialização e desserialização** de objetos nos formatos JSON e XML.

---

## Tecnologias

- Java 17
- Maven
- [ORMLite](https://ormlite.com/) — mapeamento objeto-relacional
- [SQLite](https://www.sqlite.org/) — banco de dados embutido
- [Gson](https://github.com/google/gson) — serialização JSON
- [JAXB](https://docs.oracle.com/javase/tutorial/jaxb/) — serialização XML

---

## Estrutura do projeto

```
src/main/java/
├── Database.java            # Gerencia a conexão com o banco SQLite
├── Human.java               # Entidade mapeada para o banco e para XML/JSON
├── HumanList.java           # Wrapper necessário para serialização XML de listas
├── HumanJsonSerializer.java # Serialização e desserialização em JSON via Gson
├── HumanXmlSerializer.java  # Serialização e desserialização em XML via JAXB
├── HumanRepository.java     # Repositório com operações CRUD e importação/exportação
└── Main.java                # Classe de testes
```

---

## Entidade

A entidade `Human` possui os seguintes atributos:

| Atributo   | Tipo     | Descrição                        |
|------------|----------|----------------------------------|
| `id`       | `int`    | Identificador gerado pelo banco  |
| `fullName` | `String` | Nome completo                    |
| `income`   | `double` | Renda mensal                     |
| `birthday` | `Date`   | Data de nascimento               |

---

## Funcionalidades

### CRUD básico

| Método | Descrição |
|---|---|
| `create(Human)` | Persiste um objeto no banco |
| `loadFromId(int)` | Busca um registro pelo ID |
| `loadAll()` | Retorna todos os registros |
| `update(Human)` | Atualiza um registro existente |
| `delete(Human)` | Remove um registro do banco |

### Exportação

| Método | Descrição |
|---|---|
| `dumpData(String formato)` | Retorna todos os registros como string em JSON ou XML |
| `dumpFile(String formato, File arquivo)` | Salva todos os registros em um arquivo JSON ou XML |

### Importação

| Método | Descrição |
|---|---|
| `createFromJSON(String json)` | Cria um objeto a partir de uma string JSON |
| `createFromXML(String xml)` | Cria um objeto a partir de uma string XML |
| `importData(String formato, String data)` | Importa múltiplos objetos de uma string JSON ou XML |
| `importFile(String formato, File arquivo)` | Importa objetos de um arquivo JSON ou XML |

---

## Como executar

### Pré-requisitos

- Java 17+
- Maven 3.6+
- IntelliJ IDEA (recomendado)

### Passos

1. Clone o repositório:
```bash
git clone https://github.com/Kelvin-de-Oliveira/data-serialization.git
```

2. Abra o projeto no IntelliJ IDEA via **File → Open** e selecione a pasta raiz do projeto.

3. Aguarde o Maven baixar as dependências automaticamente. Um ícone de elefante aparecerá no canto superior direito do editor — clique em **Reload Maven Project** caso as dependências não sejam baixadas automaticamente.

4. Execute a classe `Main.java`, pelo menu **Run → Run 'Main'**.

---

### Arquivos gerados

O repositório já inclui os arquivos abaixo, gerados durante os testes, para fins de verificação:

| Arquivo | Descrição |
|---|---|
| `human.db` | Banco de dados SQLite com os registros persistidos |
| `humans.json` | Exportação dos registros em formato JSON |
| `humans.xml` | Exportação dos registros em formato XML |

### Gerando os arquivos do zero

Caso queira reproduzir a execução completa a partir de um estado limpo, apague os três arquivos antes de rodar o projeto.

No Linux/Mac:
```bash
rm human.db humans.json humans.xml
```

No Windows:
```bash
del human.db humans.json humans.xml
```

Na próxima execução, o banco de dados e os arquivos serão recriados automaticamente pela classe `Main.java`.

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
</dependencies>
```
---

## Autor
**Kelvin de Oliveira**
[![GitHub](https://img.shields.io/badge/GitHub-kelvin--de--oliveira-181717?logo=github)](https://github.com/kelvin-de-oliveira)
---
## Referências

- [Tutorial ORM — marceloakira](https://github.com/marceloakira/tutorials/tree/main/orm)
- [Tutorial Serialização — marceloakira](https://github.com/marceloakira/tutorials/tree/main/serializacao_xml_json)
- [Documentação ORMLite](https://ormlite.com/javadoc/ormlite-core/doc-files/ormlite.html)
- [Documentação Gson](https://github.com/google/gson/blob/main/UserGuide.md)

