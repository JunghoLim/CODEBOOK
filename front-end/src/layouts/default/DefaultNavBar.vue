<template>
  <v-navigation-drawer
    v-bind="$attrs"
    app
  >
    <drawer-header />
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
    }
  }
};
</script>

<style></style>
