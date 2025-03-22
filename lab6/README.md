# Lab 6 - Testes de Integração com Spring Boot, REST Assured e Testcontainers

Este guião prático inclui a realização de vários exercícios focados em testes de integração com diferentes tecnologias:
- Spring Boot;
- Flyway;
- Testcontainers;
- REST Assured.

---

## Estrutura dos exercícios

### 6.1 – Testes com Testcontainers + Flyway
- Criou-se um repositório `CarRepository` com entidade `Car`;
- Usa-se a biblioteca **Testcontainers** para a base de dados PostgreSQL;
- Os dados foram migrados com **Flyway** (`V001__init.sql`);
- Por fim, testamos operações CRUD em `CarRepository`.

### 6.2 – Testes com dados populados pelo Flyway
- Foi testada a inserção de dados diretamente da base de dados inicializada por Flyway;
- Verifica-se, assim, se os dados estavam realmente disponíveis na bd após a migração.

### 6.3 – Testes de API externa com REST Assured
- Criado projeto sem Spring Boot;
- Testada a API pública [JSONPlaceholder](https://jsonplaceholder.typicode.com/todos)

### 6.4 – Testes de integração do Controller com MockMvc + REST Assured
- Usa-se `@WebMvcTest` para carregar o controller;
- E `MockBean` para simular a camada de serviço;
- Nos Testes usa-se **RestAssuredMockMvc**.

### 6.5 – Testes de integração completos com REST Assured + Testcontainers + Flyway
- Aqui já se cria um Projeto Spring Boot completo;
- Os testes têm `@SpringBootTest(webEnvironment = RANDOM_PORT)`;
- A bd PostgreSQL é inicializada num container com Testcontainers;
- Os dados são carregados via **Flyway**;
- Usa-se **REST Assured** para testar os endpoints reais

---

## Correr os testes

```bash
mvn clean test
```