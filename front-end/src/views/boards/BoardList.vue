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
              @click="board_detail(board.bno)"
            >
              <td>{{ board.bno }}</td>
              <td>{{ board.title }}</td>
              <td>{{ board.writedate }}</td>
              <td>{{ board.nickname }}</td>
              <td>{{ board.views }}</td>
              <td>{{ board.recommend }}</td>
              <td>{{ board.comment_c }}</td>
              <td>{{ board.report_c }}</td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
      <template class="mt-10">
        <div class="text-center">
          <v-pagination
            v-model="page"
            :length="pagination"
            @input="next"
          />
        </div>
      </template>
    </v-card>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  name: "BoardList",
  data() {
    return { page: 1, pagination: 1 };
  },

  // data() {
  //   return {
  //     pagination: {
  //       page: 1,
  //       total: 2,
  //       perpage: 3,
  //       visible: 10
  //     }
  //   };
  // },
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
      .get("/api/board", { params: { page: 1 } })
      .then(res => {
        let result = res.data.board_list;

        let pagination = res.data.pagination;
        console.log(res.data.board_list);
        this.pagination = pagination;
        this.$store.dispatch("board/changeBoardData", result);
      })
      .catch(err => {});
  },
  methods: {
    show_detail(value) {
      this.$router.push("/board-detail?bno=" + value.bno);
    },
    next(page) {
      this.$router.push("/board-list?page=" + page);
      axios
        .get("/api/board", { params: { page: this.page } })
        .then(res => {
          var result = res.data.board_list;

          this.$store.dispatch("board/changeBoardData", result);
        })
        .catch(err => {});
    },
    board_detail(bno) {
      console.log(bno);
    }
  }
};
</script>

<style></style>
