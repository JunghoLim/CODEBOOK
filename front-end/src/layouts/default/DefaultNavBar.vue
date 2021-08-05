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
import {mapState} from 'vuex';
import DrawerHeader from "./drawer/DrawerHeader";
import DrawerList from "./drawer/DrawerList";
export default {
  name: "DefaultNavBar",
  components: {
    DrawerHeader,
    DrawerList
  },
  computed:{
    ...mapState("app", ["items"]),
    items:{
      get(){
        return this.$store.getters["app/getItems"]
      }
    }
  }
};
</script>

<style></style>
