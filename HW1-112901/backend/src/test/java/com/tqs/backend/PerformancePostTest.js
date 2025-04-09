import http from 'k6/http';
import { check } from 'k6';

export const options = {
    vus: 20,
    duration: '10s',
};

const RESTAURANT_IDS = [1, 2, 3];
const BASE_DATE = '2025-04-10';

export default function () {
    const restaurantId = RESTAURANT_IDS[__VU % RESTAURANT_IDS.length];
    const type = __ITER % 2 === 0 ? 'ALMOCO' : 'JANTAR';

    const date = new Date(BASE_DATE);
    date.setDate(date.getDate() + __VU + __ITER);
    const formattedDate = date.toISOString().split('T')[0];

    const payload = JSON.stringify({
        restaurantId: restaurantId,
        date: formattedDate,
        type: type,
    });

    const headers = { 'Content-Type': 'application/json' };
    const res = http.post('http://localhost:8081/api/reservations', payload, { headers });

    check(res, {
        'status (2xx/4xx)': (r) => r.status >= 200 && r.status < 500,
    });
}