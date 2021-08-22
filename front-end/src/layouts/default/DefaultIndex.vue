<template>
  <v-app class="grey lighten-5">
    <default-app-bar @drawer="drawer = !drawer" />
    <default-nav-bar v-model="drawer" />
    <default-view />
  </v-app>
</template>

<script>
import DefaultView from "./DefaultView";
import DefaultAppBar from "./DefaultAppBar";
import DefaultNavBar from "./DefaultNavBar";
export default {
  name: "DefaultIndex",
  components: {
    DefaultView,
    DefaultAppBar,
    DefaultNavBar
  },
  data: () => ({
    drawer: false
  })
};
</script>

<style></style>
