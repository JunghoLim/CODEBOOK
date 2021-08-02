import Vue from 'vue'
import VueRouter from 'vue-router'
import DefaultIndex from '@/views/DefaultIndex'

Vue.use(VueRouter)

//최상위 컴포넌트인 DefaultIndex 컴포넌트를 제외한 모든 컴포넌트는 Lazy Loading을 사용합니다.
const routes = [{
    path: '/',
    component: DefaultIndex,
    children: [{
        path: '/',
        name: 'Home',
        component: () =>
            import ( /* webpackChunckName: "default-layout-home" */ '@/layouts/default/Home')
    }]
}]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router