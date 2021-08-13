import Vue from "vue";
import VueRouter from "vue-router";
import DefaultIndex from "@/layouts/default/DefaultIndex";
import store from "../store";

Vue.use(VueRouter);

//최상위 컴포넌트인 DefaultIndex 컴포넌트를 제외한 모든 컴포넌트는 Lazy Loading을 사용합니다.
const routes = [
  {
    path: "/",
    component: DefaultIndex,
    children: [
      {
        name: "Agreement",
        path: "/agreement",
        component: () =>
          import(
            /* webpackChunkName: "authentication-agreement" */
            "@/views/authentication/Agreement"
          )
      },
      {
        name: "SignUp",
        path: "/sign-up",
        component: () =>
          import(
            /* webpackChunkName: "views-sign-up" */
            "@/views/authentication/SignUp"
          )
      },
      {
        name: "SignIn",
        path: "/sign-in",
        component: () =>
          import(
            /* webpackChunkName : "views-sign-in" */
            "@/views/authentication/SignIn"
          )
      },
      {
        path: "/",
        name: "Main",
        component: () =>
          import(/*webpackChunkName : "views-main" */ "@/views/Main")
      },
      {
        path: "/board-list",
        name: "BoardList",
        component: () =>
          import(
            /*webpackChunkName : "boards-board-list" */ "@/views/boards/BoardList"
          )
      },
      {
        path: "/board-detail",
        name: "BoardDetail",
        component: () =>
          import(
            /*webpackChunkName : "boards-board-list-detail" */ "@/views/boards/BoardDetail"
          )
      }
    ]
  }
];
const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {
  if (to.name === "SignUp") {
    if (store.getters["app/getAgreement"]) {
      next();
    } else {
      next("/agreement");
    }
  } else {
    next();
  }
});
export default router;
