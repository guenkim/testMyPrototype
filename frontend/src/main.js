import { createApp } from 'vue'
import App from './App.vue'
import router from './router';
import store from './store';
import apiInterceptors from './service/apiinterceptors';
//apiInterceptors(store);
apiInterceptors(router);
createApp(App).use(store).use(router).mount('#app')
