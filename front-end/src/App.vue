<template>
  <v-app>
    <router-view :key="$route.fullPath" />
  </v-app>
</template>

<script>

export default {
  name: 'App',

  data: () => ({
    //
  }),
};
</script>
