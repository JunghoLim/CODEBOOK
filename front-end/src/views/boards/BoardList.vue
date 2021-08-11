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
import { mapState, mapMutations } from "vuex";
export default {
  name: "BoardList",

  computed: {
    ...mapState("board", ["headers", "boardData"]),
    headers: {
      get() {
        return this.$store.getters["board/getHeaders"];
      }
    },

    boardData: {
      get() {
        this.$store.dispatch("board/changeBoardData");
        return this.$store.getters["board/getBoardData"];
      }
    }
  }
};
</script>

<style></style>
