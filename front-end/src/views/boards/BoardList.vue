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
      />
    </v-card>
  </v-container>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "BoardList",
  data: () => ({
    items: ["자유", "테크", "?"]
  }),
  computed: {
    ...mapState("board", ["headers", "desserts"]),
    headers: {
      get() {
        return this.$store.getters["board/getHeaders"];
      }
    },
    boardData: {
      get() {
        return this.$store.getters["board/getBoardData"];
      }
    }
  }
};
</script>

<style></style>
