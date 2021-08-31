<template>
  <v-container>
    <v-card>
      <v-col cols="12">
        <div class="text-right">
          <v-btn
            small
            color="primary"
            :to="`/board-write/${this.$route.params.category}`"
          >
            글 작성
          </v-btn>
        </div>
      </v-col>
      <v-col
        cols="12"
        class="pa-5"
      >
        <v-data-table
          :headers="headers"
          :items="boardData"
          hide-default-footer
          @click:row="board_detail"
        />
        <div class="text-center pt-2">
          <v-pagination
            v-model="page"
            :length="pagination"
            @input="pageNext"
          />
        </div>
      </v-col>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "BoardList",
  data() {
    return {
      pagination: 1,
      category: '',
      headers:[
        { text: '번호', value: 'bno' },
        { text: '제목', value: 'title' },
        { text: '작성자', value: 'nickname' },
        { text: '조회수', value: 'views' },
        { text: '추천수', value: 'recommend' },
        { text: '댓글수', value: 'comment_cnt' },
        { text: '작성일자', value: 'writedate' },
      ],
      boardData: []
    };
  },
  computed: {
    page:{
      get(){
        return Number(this.$route.params.page);
      },
      set(){
        return Number(this.$route.params.page);
      }
    }
  },
  beforeCreate() {
    let category = this.$route.params.category;
    axios
      .get("/api/board/list", { params: { 'page': this.$route.params.page, 'category':category } })
      .then(res => {
        this.boardData = res.data.board_list;
        this.pagination = res.data.pagination;
        this.category = res.data.category;
      })
      .catch(() => {});
  },
  methods: {
    pageNext(page){
      let params = {'page': page, 'category': this.category}
      this.$store.dispatch("board/pageNext", params);
    },
    board_detail(row) {
      this.$router.push("/board-detail/"+row.bno);
    }
  }
};
</script>

<style></style>
