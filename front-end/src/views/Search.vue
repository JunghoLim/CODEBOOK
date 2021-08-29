<template>
  <v-container>
    <v-row>
      <v-col
        cols="12"
      >
        <material-card
          icon="mdi-clipboard-text"
          title="게시글"
          class="px-5 py-3 text-center"
        >
          <v-divider />
          <v-card-text>
            <v-data-table
              :headers="board_headers"
              :items="boardData"
              hide-default-footer
            />
          </v-card-text>
          <template class="mt-10">
            <div class="text-center">
              <v-pagination
                v-model="boardPage"
                :length="boardPagination"
                @input="pageNext"
              />
            </div>
          </template>
        </material-card>
      </v-col>
      <v-col
        cols="12"
      >
        <material-card
          icon="mdi-account"
          title="사용자"
          class="px-5 py-3 text-center"
        >
          <v-divider />
          <template
            v-for="profile in profiles"
          >
            <profile-card
              :key="profile.email"
              :email="profile.email"
              :nickname="profile.nickname"
              :about-me="profile.aboutMe"
              :avatar="'/api/member/profile/img/'+profile.picturePath"
            />
          </template>
        </material-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name:'Search',
  components:{
    MaterialCard:() => import('@/components/MaterialCard'),
    ProfileCard: () => import('@/components/ProfileCard')
  },
  data: () => ({
    searchText: '',
    memberPage: 1,
    boardPage: 1,
    memberPagination: 1,
    boardPagination: 1,
    board_headers: [
          {
            sortable: false,
            text: '번호',
            value: 'bno',
          },
          {
            sortable: false,
            text: '제목',
            value: 'title',
          },
          {
            sortable: false,
            text: '작성자',
            value: 'nickname',
          },
          {
            sortable: false,
            text: '조회수',
            value: 'views',
            align: 'center',
          },
          {
            sortable: false,
            text: '추천수',
            value: 'recommends',
            align: 'center',
          },
          {
            sortable: false,
            text: '댓글수',
            value: 'comments',
            align: 'center',
          },
          {
            sortable: false,
            text: '등록일',
            value: 'writeDate',
            align: 'center',
          },
          {
            sortable: false,
            text: '카테고리',
            value: 'category',
            align: 'right',
          },
        ],
        boards: [
          {
            bno: 1,
            nickname: 'jungho',
            title: '첫번째 개시글',
            views: 0,
            recommends: 0,
            comments: 0,
            writeDate: '2020-20-20',
            category: 'free'
          }
        ],
        profiles:[]
  }),
  computed:{
    boardData: {
      get() {
        return this.$store.getters["board/getBoardData"];
      }
    }
  },
  created(){
    this.searchText = this.$route.query.searchText
    axios
      .get("/api/board/list", { params: { 'page': this.boardPage, 'searchText': this.searchText } })
      .then(res => {
        let result = res.data.board_list;
        this.boardPagination = res.data.pagination;
        this.$store.dispatch("board/changeBoardData", result);
      })
      .catch(err => {});

    axios
      .get("/api/member/profile", { params: {'searchText': this.searchText } })
      .then(res => {
        let result = res.data.profiles;
        this.profiles = result;
      })
      .catch(err => {});
  },
  methods:{
    pageNext(boardPage){
      let params = {'page': boardPage, 'searchText': this.searchText}
      this.$store.dispatch("board/pageNext", params);
    },
  }
}
</script>

<style>

</style>
