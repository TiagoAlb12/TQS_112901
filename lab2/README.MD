# Lab 02: Unit Tests with Dependency Mocking

## Descrição
Este projeto tem como principal objetivo explorar o uso de **JUnit 5** e **Mockito** para realizar **testes unitários** com **mocking de dependências**. O objetivo é garantir que os testes sejam **determinísticos**, isolando, então, as dependências externas.

## Funcionalidades e fluxo, suscinto, de desenvolvimento
- Primeiramente implementamos um **portfólio de ações** que interage com um serviço de mercado de ações.
- Desenvolvimento de testes unitários para validar o cálculo do **valor total do portfólio**.
- Uso de **Mockito** para criar mocks da interface de serviço de mercado de ações.
- Testes com **Hamcrest** para verificações avançadas.
- Testes de integração, mas desta vez conectados a uma API, real, de produtos.

## Tecnologias Utilizadas
- **Java**
- **JUnit 5** (Testes Unitários)
- **Mockito** (Mocking de dependências)
- **Hamcrest / AssertJ**
- **Maven** (Gestão de dependências e execução de testes)
- **HTTP Client** (Para integração com APIs externas)

## Executar os testes
#### **Testes unitários:**
-  ```mvn test```
#### **Testes de Integração:**
- ```mvn failsafe:integration-test```
