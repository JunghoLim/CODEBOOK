<template>
  <v-navigation-drawer
    v-model="drawer"
    v-bind="$attrs"
    clipped
    app
  >
    <drawer-header
      v-if="loginStatus"
      :picture-path="memberInfo.picturePath"
      :email="memberInfo.email"
      :nickname="memberInfo.nickname"
    />
    <v-divider />
    <drawer-list :items="items" />
  </v-navigation-drawer>
</template>

<script>
import DrawerHeader from "./drawer/DrawerHeader";
import DrawerList from "./drawer/DrawerList";
export default {
  name: "DefaultNavBar",
  components: {
    DrawerHeader,
    DrawerList
  },
  computed:{
    drawer: {
        get () {
          return this.$store.state.app.drawer
        },
        set (val) {
          this.$store.commit('app/setDrawer', val)
        },
    },
    items:{
      get(){
        if(this.$store.getters["member/getStatus"]){
          if(this.$store.getters["member/getRole"] == 'ADMIN'){
            return this.$store.getters["app/getAdminItems"]
          }
          return this.$store.getters["app/getMemberItems"]
        }
        return this.$store.getters["app/getPublicItems"]
      }
    },
    memberInfo:{
      get(){
        return this.$store.getters["member/getMember"]
      }
    },
    loginStatus:{
      get(){
        return this.$store.getters["member/getStatus"]
      }
    }
  }
};
</script>

<style></style>
