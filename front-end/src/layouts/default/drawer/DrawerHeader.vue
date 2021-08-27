<template>
  <v-list>
    <v-list-item class="px-2">
      <v-list-item-avatar
        size="60"
      >
        <v-img
          :src="picturePath"
        />
      </v-list-item-avatar>
    </v-list-item>

    <v-list-item link>
      <v-list-item-content>
        <v-list-item-title class="text-h4">
          {{ nickname }}
        </v-list-item-title>
        <v-list-item-subtitle>{{ email }}</v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>
  </v-list>
</template>

<script>
export default {
  name: 'DrawerHeader',
  props:{
    nickname:{
      type:String,
      default: ''
    },
    email:{
      type:String,
      default: ''
    },
    picturePath:{
      type:String,
      default: ''
    }
  }
}
</script>

<style>

</style>
