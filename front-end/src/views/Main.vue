<template>
  <v-container>
    <v-row class="text-center">
      <v-col class="mr-5">
        <v-data-table
          :headers="headers"
          :items="boardData"
          :items-per-page="5"
          class="elevation-1 mb-10"
        />
      </v-col>
      <v-col>
        <v-data-table
          :headers="headers"
          :items="boardData"
          :items-per-page="5"
          class="elevation-1 mb-10"
        />
      </v-col>
      <v-col>
        <v-data-table
          :headers="headers"
          :items="boardData"
          :items-per-page="5"
          class="elevation-1 mb-10"
        />
      </v-col>
    </v-row>
    <v-row class="text-center">
      <v-col class="mr-5">
        <v-data-table
          :headers="headers"
          :items="boardData"
          :items-per-page="5"
          class="elevation-1 mb-10"
        />
      </v-col>
      <v-col>
        <v-data-table
          :headers="headers"
          :items="boardData"
          :items-per-page="5"
          class="elevation-1 mb-10"
        />
      </v-col>
      <v-col>
        <v-data-table
          :headers="headers"
          :items="boardData"
          :items-per-page="5"
          class="elevation-1 mb-10"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Main",
  computed: {
    ...mapState("board", ["headers", "boardData"]),
    headers: {
      get() {
        return this.$store.getters["board/getHeaders"];
      }
    },
    boardData: {
      get() {
        return this.$store.getters["board/getBoardData"];
      }
    },
    width() {
      switch (this.$vuetify.breakpoint.name) {
        case "xs":
          return 50;
        case "sm":
          return 100;
        case "md":
          return 500;
        case "lg":
          return 600;
        case "xl":
          return 800;
      }
      return 0;
    }
  },
  created: function() {
    axios
      .get("http://localhost:3000/board")
      .then(res => {
        var result = res.data.board_list;
        this.$store.dispatch("board/changeBoardData", result);
      })
      .catch(err => {});
  }
};
</script>

<style></style>
