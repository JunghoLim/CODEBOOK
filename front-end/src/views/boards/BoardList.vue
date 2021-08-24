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

      <!-- <v-data-table
        :headers="headers"
        :items="boardData"
        :items-per-page="5"
        class="elevation-1"
        @click:row="show_detail"
      /> -->
      <v-simple-table>
        <template v-slot:default>
          <thead>
            <tr>
              <th
                v-for="(item, index) in headers"
                :key="index"
                class="text-left"
              >
                {{ item.text }}
              </th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="board in boardData"
              :key="board.name"
            >
              <td>{{ board.bno }}</td>
              <td>{{ board.title }}</td>
              <td>{{ board.write_date }}</td>
              <td>{{ board.email }}</td>
              <td>{{ board.views }}</td>
              <td>{{ board.recommend }}</td>
              <td>{{ board.comment_c }}</td>
              <td>{{ board.report_c }}</td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
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
      .get("/api/board")
      .then(res => {
        var result = res.data.board_list;
        console.log(result);
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
