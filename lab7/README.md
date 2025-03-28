# Lab7

Este guião prático tem como objetivo testar a performance de uma API utilizando o k6.

## 7.1

Neste primeiro exercício executamos um teste básico para validar o funcionamento da API.

Para rodar o teste executamos o seguinte comando:

```bash
k6 run test.js
```

No fim, observamos as informações retornadas, incluindo o tempo médio de resposta, o número de requisições e a taxa de falhas.

## 7.2

Aqui, criámos um teste com um padrão de carga variável para simular diferentes cenários de uso.

Configuração do teste:

```js
export const options = {
    stages: [
        { duration: '5s', target: 20 }, // Aumento de 0 a 20 VUs
        { duration: '10s', target: 20 }, // Manutenção em 20 VUs
        { duration: '5s', target: 0 } // Redução de 20 para 0 VUs
    ],
    thresholds: {
        http_req_failed: ['rate<0.01'], // Taxa de falhas menor que 1%
        http_req_duration: ['p(95)<200'], // 95% das requisições abaixo de 200ms
    },
};
```

Executámos o teste e os resultados foram analisados:
- Tempo médio;
- Tempo mínimo;
- Máximo das requisições;
- Número total de requisições;
- Número de requisições falhas.

## Conclusão

Os testes realizados, durante o guião, permitiram avaliar o desempenho da API sob diferentes condições de carga. Foi possível identificar métricas importantes e validar se os thresholds definidos foram atingidos.
