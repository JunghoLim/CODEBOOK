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
              <th>번호</th>
              <th width="500">
                제목
              </th>
              <th>작성자</th>
              <th>조회수</th>
              <th>추천수</th>
              <th>댓글수</th>
              <th>날짜</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="board in boardData"
              :key="board.name"
              class="text-center"
              @click="board_detail(board.bno)"
            >
              <td>{{ board.bno }}</td>
              <td
                width="500"
                class="text-left"
              >
                {{ board.title }}
              </td>
              <td>{{ board.nickname }}</td>
              <td>{{ board.views }}</td>
              <td>{{ board.recommend }}</td>
              <td>{{ board.comment_cnt }}</td>
              <td>{{ board.writedate }}</td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
      <template class="mt-10">
        <div class="text-center">
          <v-pagination
            v-model="page"
            :length="pagination"
            @input="pageNext"
          />
        </div>
      </template>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "BoardList",
  data() {
    return { pagination: 1, category: '' };
  },
  computed: {
    boardData: {
      get() {
        return this.$store.getters["board/getBoardData"];
      }
    },
    page:{
      get(){
        return Number(this.$route.params.page);
      },
      set(){
        return Number(this.$route.params.page);
      }
    }
  },
  created() {
    let category = this.$route.params.category;
    axios
      .get("/api/board/list", { params: { 'page': this.$route.params.page, 'category':category } })
      .then(res => {
        let result = res.data.board_list;
        this.pagination = res.data.pagination;
        this.category = res.data.category;
        this.$store.dispatch("board/changeBoardData", result);
      })
      .catch(err => {});
  },
  methods: {
    pageNext(page){
      let params = {'page': page, 'category': this.category}
      this.$store.dispatch("board/pageNext", params);
    },
    board_detail(bno) {
      this.$router.push("/board-detail/"+bno);
    }
  }
};
</script>

<style></style>
