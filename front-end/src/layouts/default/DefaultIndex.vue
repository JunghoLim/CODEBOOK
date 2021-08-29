<template>
  <v-app class="grey lighten-4">
    <default-app-bar />
    <default-nav-bar />
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
};
</script>

<style></style>
