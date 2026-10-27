# Hora do Prato API

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.8-brightgreen)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9-blue)](https://maven.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue)](https://www.docker.com/)
[![Swagger](https://img.shields.io/badge/OpenAPI-Swagger-85EA2D)](https://springdoc.org/)

API REST para gestão de tipos de usuário, restaurantes e cardápios, desenvolvida em Java com Spring Boot.

## Sumário

- [Sobre o projeto](#sobre-o-projeto)
- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Funcionalidades](#funcionalidades)
- [Pré-requisitos](#pré-requisitos)
- [Configuração e execução](#configuração-e-execução)
- [Endpoints](#endpoints)
- [Swagger/OpenAPI](#swaggeropenapi)
- [Testes](#testes)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Contribuição](#contribuição)
- [Licença](#licença)

## Sobre o projeto

O Hora do Prato é uma aplicação backend para gestão de restaurantes, usuários e itens do cardápio. A solução foi construída com foco em organização de código, regras de negócio e boa separação de responsabilidades.

O sistema permite:

- cadastro e gestão de tipos de usuário;
- associação de usuários com perfil de cliente ou dono de restaurante;
- cadastro, listagem, atualização e exclusão de restaurantes;
- cadastro, listagem, atualização e exclusão de itens do cardápio;
- documentação automática da API com Swagger/OpenAPI;
- suporte a execução local e com containers Docker;
- testes automatizados com JaCoCo.

## Tecnologias

- Java 21
- Spring Boot 4.0.8
- Spring Web MVC
- Spring Data JPA
- Hibernate ORM
- MySQL
- H2 Database (para testes)
- Maven
- Docker / Docker Compose
- SpringDoc OpenAPI
- JaCoCo

## Arquitetura

A aplicação segue uma arquitetura em camadas, separando responsabilidades por domínio, casos de uso e adaptadores.

```text
src/
├── main/
│   ├── java/
│   │   └── br/com/fiap/horadoprato/horadoprato/
│   │       ├── adapters/
│   │       │   ├── controllers/
│   │       │   ├── gateways/
│   │       │   ├── presenters/
│   │       │   └── persistence/
│   │       ├── config/
│   │       ├── domain/
│   │       │   ├── entities/
│   │       │   └── usecase/
│   │       ├── dto/
│   │       ├── exceptions/
│   │       ├── interfaces/
│   │       └── HoradopratoApplication.java
│   └── resources/
│       ├── application.yaml
│       └── data.sql
└── test/
    └── java/
```

### Camadas principais

- Domain: entidades e regras de negócio
- Use cases: lógicas de cadastro, busca, atualização e exclusão
- Adapters: controllers, gateways e persistência JPA
- DTOs: contratos de entrada e saída da API
- Exceptions: tratamento de erros e conflitos de negócio
- Config: beans e configurações do Spring

## Funcionalidades

### Tipo de usuário

- cadastro de tipos de usuário
- listagem
- busca por ID
- atualização
- exclusão
- associação de usuário ao tipo

### Usuários

- listagem de usuários
- busca por ID
- alteração do tipo do usuário
- validação para impedir alteração quando o usuário é dono de restaurante

### Restaurantes

- cadastro de restaurante
- validação do dono do restaurante
- campos de nome, endereço, tipo de cozinha, horário e dono
- listagem, busca, atualização e exclusão

### Cardápio

- cadastro de itens do cardápio
- nome, descrição, preço, foto, disponibilidade no local
- relacionamento com o restaurante
- listagem por restaurante, busca, atualização e exclusão

## Pré-requisitos

Antes de iniciar, certifique-se de ter instalado:

- Java 21+
- Maven 3.9+
- Docker e Docker Compose (opcional)

## Configuração e execução

### Executar localmente com Maven

```bash
./mvnw clean install
./mvnw spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

### Executar com Docker

No diretório raiz do projeto:

```bash
docker-compose up --build
```

Para encerrar os containers:

```bash
docker-compose down
```

## Configuração do banco

A configuração do banco está em:

```text
src/main/resources/application.yaml
```

Exemplo:

```yaml
spring:
  application:
    name: horadoprato
  datasource:
    url: jdbc:mysql://db:3306/meu_banco?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: java_user
    password: javapassword
  jpa:
    hibernate:
      ddl-auto: update
```

## Endpoints

### Tipo de usuário

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/tipo-usuario` | Cadastra um tipo de usuário |
| GET | `/api/tipo-usuario` | Lista tipos de usuário |
| GET | `/api/tipo-usuario/{id}` | Busca tipo por ID |
| PUT | `/api/tipo-usuario/{id}` | Atualiza tipo de usuário |
| DELETE | `/api/tipo-usuario/{id}` | Exclui um tipo de usuário |

### Usuários

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/usuarios` | Lista usuários |
| GET | `/api/usuarios/{id}` | Busca usuário por ID |
| PUT | `/api/usuarios/{id}/tipo` | Associa um tipo ao usuário |

### Restaurantes

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/restaurantes` | Cadastra restaurante |
| GET | `/api/restaurantes` | Lista restaurantes |
| GET | `/api/restaurantes/{id}` | Busca restaurante por ID |
| PUT | `/api/restaurantes/{id}` | Atualiza restaurante |
| DELETE | `/api/restaurantes/{id}` | Exclui restaurante |

### Itens do cardápio

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/itens-cardapio` | Cadastra item do cardápio |
| GET | `/api/itens-cardapio/restaurante/{restauranteId}` | Lista itens por restaurante |
| GET | `/api/itens-cardapio/{id}` | Busca item por ID |
| PUT | `/api/itens-cardapio/{id}` | Atualiza item do cardápio |
| DELETE | `/api/itens-cardapio/{id}` | Exclui item do cardápio |

## Swagger / OpenAPI

A documentação interativa da API está disponível em:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Exemplos de payload

### Tipo de usuário

```json
{
  "tpUsuario": "DONO_RESTAURANTE",
  "descricaoTipoUsuario": "Dono de Restaurante"
}
```

### Usuário

```json
{
  "tipoUsuarioId": 1
}
```

### Restaurante

```json
{
  "nomeRestaurante": "Cantina Italiana",
  "endereco": "Rua das Flores, 123",
  "tipoCozinha": "Italiana",
  "horarioFuncionamento": "Segunda a Sexta, 10h às 22h",
  "donoId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### Item do cardápio

```json
{
  "nomeItemCardapio": "Pizza Margherita",
  "descricaoItem": "Pizza com molho de tomate, mussarela e manjericão",
  "preco": 59.90,
  "apenasNoLocal": false,
  "caminhoFoto": "/imagens/pizza-margherita.jpg",
  "restauranteId": "550e8400-e29b-41d4-a716-446655440010"
}
```

## Testes

Para executar a suíte de testes e gerar o relatório de cobertura:

```bash
./mvnw test jacoco:report
```

Relatório gerado em:

```text
target/site/jacoco/index.html
```

Cobertura atual validada no projeto:

- Linhas: 93%
- Instruções: 93%
- Meta mínima exigida: 90%

## Estrutura do projeto

```text
.
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
├── README.md
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── target/
└── .gitignore
```

## Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua funcionalidade (`git checkout -b feature/nova-funcionalidade`)
3. Faça o commit (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

## Licença

Este projeto foi desenvolvido para fins acadêmicos e de estudo.

## Observações finais

O sistema atende aos requisitos da fase de expansão do projeto, contemplando:

- gestão de tipos de usuário;
- associação de usuários aos tipos;
- CRUD de restaurantes;
- CRUD de itens do cardápio;
- documentação da API por Swagger;
- execução em Docker;
- testes automatizados e cobertura JaCoCo.
