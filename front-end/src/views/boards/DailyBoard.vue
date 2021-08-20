<template>
  <v-container>
    <v-card>
      <v-row>
        <v-spacer />
        <v-col
          cols="auto"
          class="mr-5"
        >
          <v-text-field
            label="search"
            prepend-icon="mdi-magnify"
          />
        </v-col>
      </v-row>
      <v-form class="outline-none " />

      <v-data-table
        :headers="headers"
        :items="boardData"
        :items-per-page="5"
        class="elevation-1"
        loading
        loading-text="Loading... Please wait"
        @click:row="show_detail"
      />
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "DailyBoard",
  data(){

  },
};
</script>

<style></style>
