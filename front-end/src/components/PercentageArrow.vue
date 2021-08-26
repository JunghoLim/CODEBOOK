<template>
  <div>
    <template v-if="Math.sign(data) >= 0">
      <v-icon

        color="green"
        small
      >
        mdi-arrow-up
      </v-icon>
      <span class="green--text">{{ data }}</span>&nbsp;
    </template>
    <template v-else>
      <v-icon
        color="red"
        small
      >
        mdi-arrow-down
      </v-icon>
      <span class="red--text">{{ data }}</span>&nbsp;
    </template>
  </div>
</template>

<script>
export default {
    name: 'PercentageArrow',
    props:{
      data:{
        type:Number,
        default: 0
      }
    }
}
</script>

<style>

</style>
