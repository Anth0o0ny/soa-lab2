import axios from 'axios';

// Настройка базового URL для всех запросов к второму сервису
const routeApiClient = axios.create({
  baseURL: 'https://localhost:8181/soa-backend-route-1.0-SNAPSHOT/api',
});

// Глобальное отключение проверки SSL (только для Node.js)
if (typeof window === 'undefined') {
  process.env.NODE_TLS_REJECT_UNAUTHORIZED = '0';
}

// Добавляем перехватчик ошибок
routeApiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      console.error('Ошибка сервера:', error.response.data);
    } else if (error.request) {
      console.error('Ошибка сети:', error.message);
    } else {
      console.error('Ошибка конфигурации:', error.message);
    }
    return Promise.reject(error);
  }
);

export default routeApiClient;