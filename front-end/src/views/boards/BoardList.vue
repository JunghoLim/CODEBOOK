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
        @click:row="show_detail"
      />
    </v-card>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
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
        return this.$store.getters["board/getBoardData"];
      }
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
  },
  methods: {
    show_detail(value) {
      this.$router.push("/board-detail?bno=" + value.bno);
    }
  }
};
</script>

<style></style>
