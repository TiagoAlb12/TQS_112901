**Nota**: Resposta à alínea 'c'. As alíneas anteriores encontram-se implementadas no ```lab3_2_cars```, como suposto.


## 3.3 - Integration Test

### Alínea c): Vantagens e Desvantagens de usar uma Base de Dados (real)

**Vantagens:**
- **Maior fidelidade** → Testa o ambiente real de produção.
- **Verifica conexões e queries SQL** → Evita problemas ao passar para produção.
- **Deteta problemas de concorrência** → Testa como múltiplas transações.
- **Menos imprevistos em produção** → Simula um ambiente realista.

**Desvantagens:**
- **Testes mais lentos** → Comparado ao H2, o MySQL pode ser mais demorado.
- **Maior complexidade** → Requer configuração extra (ex: Docker, permissões).
- **Possível persistência de dados** → Se a bd não for limpa, pode afetar os testes.
- **Dependência externa** → É preciso garantir que a bd está sempre disponível.
