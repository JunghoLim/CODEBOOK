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
                      {{ boardDetailData.write_date }}
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
      <div
        class="text-h5"
        v-bind="boardDetailData"
      >
        총 {{ boardDetailData.comment_c }}개의 댓글이 달렸습니다.
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
  data() {
    return {
      items: []
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
    }
  },
  created: function() {
    var bno = this.$route.query.bno;
    axios
      .get("/api/board-detail", { params: { bno: bno } })
      .then(res => {
        this.$store.dispatch("boardDetail/changeBoardDetailData", res.data);
      })
      .catch(err => {});
  }
};
</script>

<style></style>
