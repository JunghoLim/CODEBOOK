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
              style="border-bottom:1px solid #f1f3f5;"
              class="mb-15"
            >
              <div class="text-left">
                <div>
                  <p class="text-h5">
                    title
                  </p>
                </div>
                <div>
                  <v-row>
                    <v-col cols="auto">
                      이름
                    </v-col>
                    <v-col>
                      날짜
                    </v-col>
                  </v-row>
                </div>
              </div>
            </v-container>
            <v-container>
              <v-row justify="center">
                <!-- 내용 오는 곳 -->
                dasfsdfas
              </v-row>
            </v-container>
          </v-card>
        </v-container>
      </v-col>
    </v-row>
    <v-row justify="center">
      <div class="text-h5">
        총 A개의 댓글이 달렸습니다.
      </div>
    </v-row>
    <v-row justify="center">
      <v-card
        height="200px"
        width="600px"
      >
        댓글이다!
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "BoardDetail",

  computed: {
    ...mapState("boardDetail", ["boardDetail"]),
    boardDetailData: {
      get() {
        return this.$store.getters.getBoardDetailData;
      }
    }
  },
  created: function() {
    var bno = this.$route.query.bno;
    axios
      .get("http://localhost:3000/board-detail", { params: { bno: bno } })
      .then(res => {
        this.$store.dispatch("boardDetail/changeBoardDetailData", res.data);
      })
      .catch(err => {});
  }
};
</script>

<style></style>
