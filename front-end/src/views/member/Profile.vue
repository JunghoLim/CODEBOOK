<template>
  <v-container
    id="user-profile"
    tag="section"
  >
    <v-row justify="center">
      <v-col
        cols="12"
        md="4"
      >
        <material-card
          class="v-card-profile"
          :avatar="memberInfo.picturePath"
        >
          <v-card-text class="text-center">
            <h4 class="text-h3 font-weight-light mb-3 black--text">
              {{ memberInfo.nickname }}
            </h4>

            <!-- eslint-disable vue/no-v-html -->
            <p
              class="font-weight-light grey--text"
              v-html="aboutMe"
            />
            <!--eslint-enable-->

            <v-btn
              color="success"
              rounded
              class="mr-0"
              :loading="isSelecting"
              @click="onButtonClick"
            >
              <v-icon class="mr-3">
                mdi-camera
              </v-icon>
              프로필
            </v-btn>
            <input
              ref="uploader"
              class="d-none"
              type="file"
              accept="image/*"
              @change="onFileChanged"
            >
            <material-stats-card
              color="info"
              icon="mdi-star-check"
              title="Followers"
              :value="String(memberInfo.follower)"
            />
          </v-card-text>
        </material-card>
      </v-col>
      <v-col
        cols="12"
        md="8"
      >
        <material-card>
          <template v-slot:heading>
            <div class="text-h3 font-weight-medium">
              프로필
            </div>

            <div class="text-subtitle-1 font-weight-light">
              업데이트 할 프로필 사항이 있나요?
            </div>
          </template>

          <validation-observer
            v-slot="{ invalid }"
            ref="observer"
          >
            <v-form
              @submit.prevent="
                profileUpdate({
                  'email': memberInfo.email,
                  'nickname': memberInfo.nickname,
                  'aboutMe': memberInfo.aboutMe
                })"
            >
              <v-container class="py-3">
                <v-row>
                  <v-col
                    cols="12"
                    md="4"
                  >
                    <v-text-field
                      :label="memberInfo.email"
                      :placeholder="memberInfo.email"
                      disabled
                    />
                  </v-col>

                  <v-col
                    cols="12"
                    md="4"
                  >
                    <validation-provider
                      v-slot="{ errors }"
                      :rules="{ max:20, required:true }"
                      name="Nickname"
                    >
                      <v-text-field
                        v-model="memberInfo.nickname"
                        class="purple-input"
                        label="Nickname"
                        :value="memberInfo.nickname"
                        :placeholder="memberInfo.nickname"
                        :error-messages="errors"
                      />
                    </validation-provider>
                  </v-col>

                  <v-col
                    cols="12"
                  >
                    <validation-provider
                      v-slot="{ errors }"
                      :rules="{ max:300 }"
                      name="aboutMe"
                    >
                      <v-textarea
                        v-model="memberInfo.aboutMe"
                        class="purple-input"
                        label="About Me"
                        :value="memberInfo.aboutMe"
                        :error-messages="errors"
                      />
                    </validation-provider>
                  </v-col>

                  <v-col
                    cols="12"
                    class="text-right"
                  >
                    <v-btn
                      color="success"
                      type="submit"
                      class="mr-0"
                      :disabled="invalid"
                    >
                      수정하기
                    </v-btn>
                  </v-col>
                </v-row>
              </v-container>
            </v-form>
          </validation-observer>
        </material-card>
      </v-col>
      <v-col cols="12">
        <material-card
          icon="mdi-clipboard-text"
          title="내 게시물"
          class="px-5 py-3"
        >
          <v-simple-table>
            <thead>
              <tr>
                <th class="primary--text">
                  카테고리
                </th>
                <th class="primary--text">
                  글 제목
                </th>
                <th class="primary--text">
                  조회수
                </th>
                <th class="primary--text">
                  추천수
                </th>
                <th class="text-right primary--text">
                  작성일
                </th>
              </tr>
            </thead>

            <tbody>
              <tr
                v-for="board in boardData"
                :key="board.name"
                class="text-center"
                @click="board_detail(board.bno)"
              >
                <td class="text-left">
                  {{ board.category }}
                </td>
                <td class="text-left">
                  {{ board.title }}
                </td>
                <td class="text-center">
                  {{ board.recommend }}
                </td>
                <td>{{ board.comment_cnt }}</td>
                <td class="text-right">
                  {{ board.writedate }}
                </td>
              </tr>
            </tbody>
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
        </material-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import MaterialCard from '@/components/MaterialCard'
import MaterialStatsCard from '@/components/MaterialStatsCard'

export default {
  components:{
    MaterialCard,
    MaterialStatsCard
  },
  data: () => ({
    selectedFile: null,
    isSelecting: false,
    aboutMe:'',
    page:1,
    pagination:1
  }),
  computed:{
    buttonText() {
      return this.selectedFile ? this.selectedFile.name : this.defaultButtonText
    },
    memberInfo:{
      get(){
        return this.$store.getters["member/getMember"]
      }
    },
    boardData: {
      get() {
        return this.$store.getters["board/getBoardData"];
      }
    }
  },
  created() {
    let searchText = 'email_'+this.$store.getters["member/getMember"].email;
    axios
      .get("/api/board/list", { params: { 'page': 1, 'searchText':searchText } })
      .then(res => {
        let result = res.data.board_list;
        this.pagination = res.data.pagination;
        this.$store.dispatch("board/changeBoardData", result);
      })
      .catch(err => {});
  },
  beforeMount(){
      this.aboutMe = this.memberInfo.aboutMe;
      this.aboutMe = this.aboutMe.replace(/(?:\r\n|\r|\n)/g, '<br />');
      this.aboutMe = this.aboutMe.split('\n').join('<br />');
  },
  beforeUpdate(){
    this.aboutMe = this.memberInfo.aboutMe;
    this.aboutMe = this.aboutMe.replace(/(?:\r\n|\r|\n)/g, '<br />');
    this.aboutMe = this.aboutMe.split('\n').join('<br />');
  },
  methods: {
    pageNext(page){
      axios
          .get("/api/board/list", { params: { 'page': page, 'searchText': 'email_'+this.$store.getters["member/getMember"].email } })
          .then(res => {
              let result = res.data.board_list;
              this.$store.dispatch("board/changeBoardData", result);
          })
          .catch(() => {});
    },
    onButtonClick() {
      this.isSelecting = true
      window.addEventListener('focus', () => {
        this.isSelecting = false
      }, { once: true })

      this.$refs.uploader.click()
    },
    onFileChanged(e) {
      this.selectedFile = e.target.files[0]
      this.$store.dispatch("member/profileImgUpdate", this.selectedFile);
    },
    profileUpdate(profileForm) {
      const memberNickname = this.$store.getters["member/getMember"].nickname;
      axios
          .get("/api/member/duplicate", {params:{'nickname': profileForm.nickname}})
          .then(res => {
            if(res.data == 1 && profileForm.nickname != memberNickname){
              alert('중복된 닉네임을 가진 유저가 있습니다.')
            }else{
              this.$store.dispatch("member/profileUpdate", profileForm);
            }
          })
    },
    board_detail(bno) {
      this.$router.push("/board-detail/" + bno);
    }
  }
}
</script>

<style>
</style>
