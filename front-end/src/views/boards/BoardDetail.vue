<template>
  <v-container>
    <v-row>
      <v-col cols="12">
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
                <!-- eslint-disable vue/no-v-html -->
                <div v-html="boardDetailData.content" />
                <!--eslint-enable-->
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
          >
            <v-col>
              <v-col>
                <div>
                  <div>
                    <p>{{ comment.nickname }}</p>
                    <p>작성일 : {{ comment.writedate }}</p>
                  </div>
                  <p>{{ comment.content }}</p>
                </div>
              </v-col>
            </v-col>
            <div
              v-if="currentEmail == comment.email"
              class="mt-15"
            >
              <v-btn @click="updateComment(comment.cno)">
                수정
              </v-btn>

              <v-btn @click="deleteComment(comment.cno)">
                삭제
              </v-btn>
            </div>
          </v-row>
        </v-container>
      </v-card>
    </v-row>
    <v-dialog
      v-model="dialog"
      width="500"
    >
      <v-card>
        <v-container>
          <v-text-field v-model="correctionMessage" />
        </v-container>
        <v-divider />

        <v-card-actions>
          <v-spacer />
          <v-btn
            text
            @click="correction()"
          >
            I accept
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

      this.$store.dispatch("boardDetail/sendComment", commetInfo);
    },
    updateComment(cno) {
      console.log(cno);
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
    }
  }
};
</script>

<style></style>
