# Lab4 - Testes Automatizados na Web com Selenium e Docker

Este guião prático tem como principal objetivo a **automação de testes da camada web**, utilizando o **Selenium WebDriver**, e, no ultimo exercício, executando testes em navegadores dentro de **Docker Containers**.

---

## Objetivos deste guião

✔ **Escrever testes de UI (User Interface) automatizados** - com recurso ao Selenium WebDriver.
✔ **Utilizar o padrão Page Object** - para melhorar a organização e reutilização do código.  
✔ **Executar testes em diferentes navegadores** - de forma isolada e controlada usando Docker.

---

## Conceitos Importantes utilizados neste Guião

### **Selenium WebDriver**
O **Selenium WebDriver** permite interagir com um navegador como se fosse um utilizador real. Podemos realizar, por exemplo, as seguintes ações:
- Abrir uma página
- Clicar em botões e links
- Preencher formulários
- Validar o conteúdo das páginas

### **Locators e Identificação de Elementos**
A escolha dos *locators* influencia bastante a estabilidade do teste. Neste laboratório, explorámos:
- `By.id()` e `By.cssSelector()` como estratégias mais estáveis.  
- `By.xpath()` quando necessário, mas sempre tendo em atenção as dependências frágeis no HTML.  

### **Page Object Pattern**
O **Page Object Pattern** é importante visto que melhora a organização dos testes ao separar a lógica de interação da lógica dos testes. Este padrão é caracterizado por:
- Reduzir a duplicação de código.  
- Facilitar a manutenção e a escalabilidade dos testes.  
- Tornar os testes mais legíveis e fáceis de modificar.  

### **Utilização de Docker para Testes**
Em vez de instalar navegadores localmente, utilizámos **Docker** para executar os testes. Vantagens:
- Ter um ambiente isolado e controlado.  
- Podemos testar diferentes versões do mesmo navegador.  
- Evitar problemas de compatibilidade de versões.  

---
