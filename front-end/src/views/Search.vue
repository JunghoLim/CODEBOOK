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
              no-data-text="비슷한 제목을 가진 게시글이 존재하지 않아요!"
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
          <v-row>
            <template
              v-if="profiles.length == 0"
            >
              <v-col cols="12`">
                <v-card outlined>
                  <v-container class="py-8">
                    <div class="d-flex justify-center mb-4">
                      <v-icon size="40">
                        mdi-emoticon-sad-outline
                      </v-icon>
                    </div>

                    <p class="text-h4 text-center my-0 py-0 grey--text text--darken-3">
                      Oops! 비슷한 닉네임을 가진 사용자가 존재하지 않아요!
                    </p>
                  </v-container>
                </v-card>
              </v-col>
            </template>
            <template
              v-for="profile in profiles"
              v-else
            >
              <v-col
                :key="profile.email"
                cols="3"
                md="6"
                sm="12"
              >
                <profile-card
                  :email="profile.email"
                  :nickname="profile.nickname"
                  :about-me="profile.aboutMe"
                  :avatar="'/api/member/profile/img/'+profile.picturePath"
                />
              </v-col>
            </template>
          </v-row>
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
        profiles:[]
  }),
  computed:{
    boardData: {
      get() {
        return this.$store.getters["board/getBoardData"];
      }
    },
    boardPage:{
      get(){
        return Number(this.$route.params.page);
      },
      set(){
        return Number(this.$route.params.page);
      }
    }
  },
  created(){
    this.searchText = this.$route.params.searchText;
    axios
      .get("/api/board/list", { params: { 'page': this.$route.params.page, 'searchText': this.searchText } })
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
