import Vue from "vue";
import axios from "./plugins/axios";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import "./plugins";
import './plugins/chartist'
// Basic Use - Covers most scenarios
import VueEditor from "vue2-editor";

Vue.config.productionTip = false;
Vue.prototype.$Axios = axios;
Vue.use(VueEditor);

new Vue({
    router,
    store,
    vuetify,
    beforeCreate() {
        this.$store.dispatch('member/getMemberInfo');
    },
    render: h => h(App)
}).$mount("#app");