import Vue from "vue";
import axios from "./plugins/axios";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import "./plugins";

Vue.config.productionTip = false;
Vue.prototype.$Axios = axios;

new Vue({
    router,
    store,
    vuetify,
    beforeCreate() {
        this.$store.dispatch('member/getMemberInfo');
    },
    render: h => h(App)
}).$mount("#app");
