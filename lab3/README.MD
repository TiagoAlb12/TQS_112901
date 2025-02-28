# Lab 03: Integration Tests with Spring Boot & MySQL

##Descrição
Este projeto tem como principal objetivo explorar **testes de integração** utilizando **Spring Boot, JUnit 5, Mockito e uma base de dados MySQL**.  
Através de diferentes testes, garantimos a **correta comunicação entre as camadas** da aplicação, incluindo a API e a base de dados.

---

## 🔹 Funcionalidades e Fluxo de Desenvolvimento
- Implementação de uma **API REST para gestão de carros**, com Spring Boot;
- Testes unitários, com as dependências graças à utilização do **Mockito**;
- Testes de integração, **H2**;
- Adaptação dos testes para **usar uma bd real (MySQL)**; necessária a implementação de um **Docker compose**;
- Configuração de **Docker Compose** para facilitar o setup do ambiente.

---

## Tecnologias Utilizadas
- **Java 17+**
- **Spring Boot**
- **JUnit 5**
- **Mockito**
- **Hamcrest**
- **Maven**
- **MySQL**
- **Docker Compose**

---

## Executar os Testes
Para executar os testes, no exercicio 3.3, temos de iniciar o **docker-compose**, executando ```docker compose up d-```. Podemos verificar se está up com ```docker compose ps -a```. 
Executamos os testes como normalmente: ```mvn clean``` seguido de ```mvn test```.
No fim, executamos ```docker compose down -v```.
