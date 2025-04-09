# Lab8: Clean code and static analysis

## 8.1 Local analysis

### Procedimento de execução:

1 - Executar o comando:
```bash
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
```

2 - Aceder ao link:
http://localhost:9000

3- Criar o projeto no SonarQube:
- Nome: **EuroMillions**;
- Key: **EuroMillions**;
- Branch: **main**;
- Modo de análise: *Locally*;

4- Gerar o token de acesso.

5- Execução da análise com Maven:
```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=EuroMillions \
  -Dsonar.projectName='EuroMillions' \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=TOKEN
```

---

### g) Análise de resultados e exemplos de problemas encontrados

Com base na análise realizada pelo SonarQube ao projeto **EuroMillions**, foram encontrados os seguintes indicadores e problemas:

Dados retirados do SonarQube:
- 0 **problemas de Security**;
- 0 **problemas de Reliability**;
- 35 **problemas de Maintainability**;
- 1 **Security Hotspot**.


| **Issue**          | **Problem description**                                           | **How to solve**                                        |
|--------------------|-------------------------------------------------------------------|---------------------------------------------------------|
| Security           | Nenhum problema encontrado.                                       | —                                                       |
| Reliability        | Nenhum problema encontrado.                                       | —                                                       |
| Maintainability    | Métodos com demasiada responsabilidade (funções grandes/confusas) | Aplicar refatoração como *Extract Method*              |
| Maintainability    | Nomes de variáveis pouco descritivos                              | Usar nomes mais claros e significativos                |
| Maintainability    | Possível código repetido em diferentes métodos                    | Extrair lógica comum para métodos auxiliares           |
| Security Hotspot   | Potencial risco de exposição a dados externos                     | Validar ou sanitizar inputs externos                   |

---

---

### h) Ferramentas externas utilizadas no SonarQube

Durante a análise do código, o SonarQube pode integrar-se com ferramentas externas especializadas na deteção de problemas de estilo, bugs e práticas menos corretas. As mais comuns são:

- **Checkstyle** – Ferramenta que analisa o estilo do código (formatação, convenções de nomes, estrutura, ...) com base em regras configuráveis.
- **PMD** – Analisa más práticas de programação, como código redundante, variáveis não utilizadas e estruturas de controlo mal formadas.
- **SpotBugs** – Foca-se na deteção de bugs reais (ex.: null pointer, erros de concorrência) através da análise de bytecode Java.

Estas ferramentas ajudam a aumentar a cobertura de análise do SonarQube, fornecendo resultados mais completos e específicos sobre a qualidade do código.

---
