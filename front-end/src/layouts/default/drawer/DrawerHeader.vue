<template>
  <v-list-item>
    <v-list-item-content>
      <router-link to="/">
        <v-img
          width="60"
          class="mx-auto mb-6"
          :src="require('@/assets/logo.png')"
        />
      </router-link>
      <v-list-item-title class="text-h6 text-center">
        CODEBOOK
      </v-list-item-title>
    </v-list-item-content>
  </v-list-item>
</template>

<script>
export default {
  name: 'DrawerHeader'
}
</script>

<style>

</style>
