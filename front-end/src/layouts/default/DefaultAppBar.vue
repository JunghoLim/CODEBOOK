<template>
  <v-app-bar
    app
    color="blue"
    dark
    clipped-left
  >
    <v-app-bar-nav-icon @click="$emit('drawer')" />

    <v-toolbar-title>
      <v-icon
        large
        color="primary"
      >
        mdi-feather
      </v-icon>CODEBOOK
    </v-toolbar-title>
  </v-app-bar>
</template>

<script>
export default {
  name: "DefaultAppBar"
};
</script>

<style></style>
