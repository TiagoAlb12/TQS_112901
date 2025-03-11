# Localizadores usados no teste da alínea a)

## Localizadores Utilizados

No teste implementado, utilizamos os seguintes **localizadores**:
```java
WebElement searchBox = driver.findElement(By.name("search"));
WebElement resultTitle = driver.findElement(By.xpath("//h2[contains(text(), 'Harry Potter and the Sorcerer')]");
WebElement resultAuthor = driver.findElement(By.xpath("//p[contains(text(), 'J.K. Rowling')]");
```

## Notas sobre os localizadores utilizados:
- ```By.name("search")``` → O atributo name é uma escolha confiável, desde que seja único na página.

- ```By.xpath("//h2[contains(text(), 'Harry Potter and the Sorcerer')]")``` → O XPath pode ser instável, pois se a página mudar, o teste poderá falhar.

- ```By.xpath("//p[contains(text(), 'J.K. Rowling')]")``` → Mesma limitação do XPath referid acima.

## Comparações entre os diferentes localizadores

O Selenium permite diferentes formas de selecionar os elementos. 
Iremos então analisá-las:

Tipo de Localizador | Vantagens | Desvantagens
:--------- | :------: | -------:
```By.id("...")``` | Rápido e único na página | Nem sempre está disponível
```By.name("...")``` | Simples e legível | Pode não ser único
```By.className("...")``` | Pode ser reutilizado em vários elementos | Pode mudar facilmente
```By.cssSelector("...")``` | Representa uma melhor alternativa ao XPath | Se o CSS não for bem estruturado, pode não ser tão eficiente
```By.xpath("...")``` | Flexível para busca complexa | Mais lento e pode não funcionar com mudanças no HTML
