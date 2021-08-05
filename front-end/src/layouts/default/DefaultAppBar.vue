<template>
  <v-app-bar
    app
    color="indigo"
    dark
  >
    <v-app-bar-nav-icon @click.stop="$emit('drawer')" />
    <v-toolbar-title>{{ $route.name }}</v-toolbar-title>
  </v-app-bar>
</template>

<script>
export default {
  name: "DefaultAppBar"
};
</script>

<style></style>
