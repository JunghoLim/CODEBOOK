<template>
  <v-container>
    <v-row justify="center">
      <v-col
        cols="12"
        sm="12"
        lg="10"
      >
        <v-card class="pa-3 fill-height">
          <div v-bind="boardDetailData">
            <div class="text-left">
              <p class="text-h2 font-weight-black">
                {{ boardDetailData.title }}
              </p>
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
              <v-divider />
            </div>
          </div>
          <!-- eslint-disable vue/no-v-html -->
          <div
            class="py-15"
            v-html="boardDetailData.content"
          />
          <!--eslint-enable-->
          <v-divider />
          <v-row justify="center">
            <v-col cols="12">
              <v-text-field
                v-if="currentEmail"
                v-model="message"
                class="mt-3"
                filled
                rounded
                dense
                single-line
                type="text"
                :append-outer-icon="'mdi-send'"
                @click:append-outer="sendMessage"
                @keyup.enter="sendMessage"
              />
              <div v-if="isMessage">
                <h3 class="text-h3 font-weight-medium py-4">
                  <v-icon large>
                    mdi-forum
                  </v-icon>
                  댓글
                </h3>
                <v-row
                  v-for="(comment, index) in commentData"
                  :key="index"
                >
                  <v-col>
                    <div>
                      <v-row
                        align="center"
                        justify="center"
                      >
                        <v-col
                          cols="12"
                          lg="10"
                        >
                          <div>
                            <p style="float:left;">
                              {{ comment.nickname }}
                            </p>
                            <p
                              style="float:left;"
                              class="ml-3"
                            >
                              {{ comment.commentrecommend }}
                            </p>
                            <v-btn
                              icon
                              style="background-color:white;"
                              height="10"
                              @click="recommendUp(comment.cno)"
                            >
                              <v-icon color="#BDBDBD">
                                mdi-thumb-up
                              </v-icon>
                            </v-btn>
                          </div>
                          <p
                            class="text-h6"
                            style="color:#adb5bd;"
                          >
                            작성일 : {{ comment.writedate }}
                          </p>
                          <p
                            v-html="
                              comment.content.replace(/(?:\r\n|\r|\n)/g, '<br>')
                            "
                          />
                        </v-col>
                        <v-col
                          cols="12"
                          lg="2"
                        >
                          <div v-if="currentEmail == comment.email">
                            <v-btn
                              color="cyan"
                              @click="updateComment(comment.cno)"
                            >
                              수정
                            </v-btn>

                            <v-btn
                              color="error"
                              @click="deleteComment(comment.cno)"
                            >
                              삭제
                            </v-btn>
                          </div>
                        </v-col>
                      </v-row>
                    </div>
                    <v-divider />
                  </v-col>
                </v-row>
              </div>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>

    <v-dialog
      v-model="dialog"
      width="500"
    >
      <v-card>
        <v-container>
          <v-textarea
            v-model="correctionMessage"
            solo
          />
        </v-container>

        <v-card-actions>
          <v-spacer />
          <v-btn
            text
            @click="correction"
          >
            확인
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>
<script>
import { mapState } from "vuex";
export default {
  name: "BoardDetail",
  data() {
    return {
      items: [],
      message: null,
      commentComponent: [],
      dialog: false,
      correctionMessage: null,
      currentCno: null
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
    },
    currentEmail: {
      get() {
        return this.$store.getters["member/getEmail"];
      }
    }
  },
  created: function() {
    let bno = this.$route.params.bno;
    this.$store.dispatch("boardDetail/changeBoardDetailData", bno);
    this.$store.dispatch("boardDetail/changeCommentData", bno);
  },
  methods: {
    sendMessage() {
      let bno = this.$route.params.bno;
      let comment = this.message;
      let isLogin = this.$store.getters["member/getStatus"];
      let email = this.$store.getters["member/getEmail"];
      let commetInfo = {
        bno: bno,
        comment: comment,
        isLogin: isLogin,
        email: email
      };

      this.$store
        .dispatch("boardDetail/sendComment", commetInfo)
        .then(this.$router.go());
    },
    updateComment(cno) {
      this.dialog = true;
      this.currentCno = cno;
    },
    correction() {
      let token = localStorage.getItem("codebook-bearer");
      let comment = this.correctionMessage;
      let currentCno = this.currentCno;
      axios
        .post(
          "/api/comment/update",
          { cno: currentCno, comment: comment },
          {
            headers: { "codebook-bearer": token }
          }
        )
        .then(res => {
          this.correctionMessage = null;
          this.$router.go();
        })
        .catch(err => {
          console.log(err);
        });
    },
    deleteComment(cno) {
      let email = this.$store.getters["member/getEmail"];
      let bno = this.$route.params.bno;
      let param = { cno: cno, email: email, bno: bno };
      this.$store.dispatch("boardDetail/deleteComment", param);
      this.$router.go();
    },
    recommendUp(cno) {
      let token = localStorage.getItem("codebook-bearer");
      let bno = this.$route.params.bno;
      let isLogin = this.$store.getters["member/getStatus"];
      let email = this.$store.getters["member/getEmail"];
      if (isLogin == true) {
        axios
          .post(
            "/api/comment/recommend",
            { cno: cno, email: email, bno: bno },
            { headers: { "codebook-bearer": token } }
          )
          .then(res => {})
          .catch(err => {
            console.log(err);
          });
      }
    }
  }
};
</script>

<style></style>
