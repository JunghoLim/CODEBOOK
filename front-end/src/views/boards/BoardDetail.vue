<template>
  <v-container>
    <v-row>
      <v-col>
        <v-container style="width:620px">
          <v-card
            class="text-center"
            height="1000px"
          >
            <v-container
              v-bind="boardDetailData"
              style="border-bottom:1px solid #f1f3f5;"
              class="mb-15"
            >
              <div class="text-left">
                <div>
                  <p class="text-h5">
                    {{ boardDetailData.title }}
                  </p>
                </div>
                <div>
                  <v-row class="text-caption">
                    <v-col cols="auto">
                      {{ boardDetailData.nickname }}
                    </v-col>
                    <v-spacer />
                    <v-col
                      cols="auto"
                      style="color:#adb5bd;"
                    >
                      {{ boardDetailData.writedate }}
                    </v-col>
                  </v-row>
                </div>
              </div>
            </v-container>
            <v-container>
              <v-row class="ml-2">
                <!-- 내용 오는 곳 -->
                {{ boardDetailData.content }}
              </v-row>
            </v-container>
          </v-card>
        </v-container>
      </v-col>
    </v-row>

    <v-row justify="center">
      <v-card width="600px">
        <v-container style="width: 600px; float:left;">
          <v-text-field
            v-model="message"
            filled
            rounded
            dense
            single-line
            type="text"
            :append-outer-icon="'mdi-send'"
            @click:append-outer="sendMessage"
          />
        </v-container>
        <v-container v-if="isMessage">
          <v-row
            v-for="(comment, index) in commentData"
            :key="index"
            v-bind="commentData"
          >
            <v-col>
              <div>
                <div>
                  <p>
                    {{ comment.nickname }}
                  </p>
                  <p>작성일 : {{ comment.writedate }}</p>
                </div>
                <p>
                  {{ comment.content }}
                </p>
              </div>
            </v-col>
          </v-row>
        </v-container>
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "BoardDetail",
  data() {
    return {
      items: [],
      message: null
    };
  },

  computed: {
    ...mapState("boardDetail", ["boardDetail"]),
    boardDetailData: {
      set() {
        var item = this.$store.getters["boardDetail/getBoardDetailData"];
        this.items = item;
      },
      get() {
        return this.$store.getters["boardDetail/getBoardDetailData"];
      }
    },
    commentData: {
      get() {
        return this.$store.getters["boardDetail/getCommentData"];
      }
    },
    isMessage: {
      set(result) {
        this.$store.dispatch("boardDetail/setIsMessage", result);
      },
      get() {
        return this.$store.getters["boardDetail/getIsMessage"];
      }
    }
  },
  created: function() {
    //집가서 두개로 합치기
    var bno = this.$route.query.bno;
    this.$store.dispatch("boardDetail/changeBoardDetailData", bno);
    this.$store.dispatch("boardDetail/changeCommentData", bno);
    console.log(this.isMessage);
  },
  methods: {
    sendMessage() {
      let isLogin = this.$store.getters["member/getStatus"];

      let email = this.$store.getters["member/getEmail"];
      if (isLogin) {
        let bno = this.boardDetailData.bno;
        let comment = this.message;
        let param = { comment: comment, bno: bno, email: email };
        axios
          .post("/api/input-comment", param)
          .then(res => {
            let successNumber = res.data;
            console.log(successNumber);
            this.isMessage.set(true);
          })
          .catch(err => {});
      } else {
        alert("로그인을 해주세요!");
        this.$router.push("/sign-in");
      }
    }
  }
};
</script>

<style></style>
