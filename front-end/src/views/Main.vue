<template>
  <v-container>
    <div>
      <v-card>
        <v-img
          src="https://images.unsplash.com/photo-1521737852567-6949f3f9f2b5?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1930&q=80"
          gradient="to top, rgba(25,32,72,.7), rgba(25,32,72,.0)"
          :aspect-ratio="16 / 9"
          height="420px"
          dark
        >
          <v-card-text class="fill-height d-flex align-end">
            <v-row class="flex-column">
              <v-col
                cols="12"
                md="10"
                lg="8"
                xl="7"
              >
                <h2
                  class="text-h1 font-weight-medium py-6 pl-4"
                  style="line-height: 1.2"
                >
                  The CodeBook is written by us together.
                </h2>
              </v-col>
            </v-row>
          </v-card-text>
        </v-img>
      </v-card>
    </div>

    <v-row>
      <v-col
        cols="12"
        lg="12"
        xl="8"
      >
        <div>
          <div class="pt-16">
            <h2 class="text-h4 font-weight-bold pb-4">
              <v-icon class="pb-1">
                mdi-crown-outline
              </v-icon>
              주간 인기 게시글
            </h2>

            <v-divider />

            <v-row>
              <v-col
                v-for="board in weeklyBestBoards"
                :key="board.bno"
                cols="12"
                md="6"
                lg="4"
              >
                <router-link
                  :to="`/board-detail/${board.bno}`"
                  class="text-decoration-none"
                >
                  <v-hover
                    v-slot:default="{ hover }"
                    open-delay="50"
                    close-delay="50"
                  >
                    <div>
                      <v-card
                        rounded="xl"
                        flat
                        :color="hover ? 'white' : 'transparent'"
                        :elevation="hover ? 12 : 0"
                        hover
                      >
                        <v-img
                          :src="`/api/file/img/${board.mainPicture}`"
                          :aspect-ratio="16 / 9"
                          gradient="to top, rgba(25,32,72,.4), rgba(25,32,72,.0)"
                          height="200px"
                          class="elevation-2"
                          style="border-radius: 16px"
                        />

                        <v-card-text>
                          <div class="text-h5 font-weight-bold primary--text">
                            {{ board.title }}
                          </div>

                          <div class="d-flex align-center">
                            <v-avatar
                              color="accent"
                              size="36"
                            >
                              <v-img
                                :src="`/api/member/profile/img/${board.picturePath}`"
                              />
                            </v-avatar>

                            <div class="pl-2">
                              {{ board.nickname }}
                            </div>
                          </div>
                        </v-card-text>
                      </v-card>
                    </div>
                  </v-hover>
                </router-link>
              </v-col>
            </v-row>
          </div>

          <div class="pt-16">
            <h2 class="text-h4 font-weight-bold pb-4">
              <v-icon class="pb-1">
                mdi-crown
              </v-icon>
              월간 인기 게시글
            </h2>

            <v-divider />

            <v-row>
              <v-col
                v-for="board in monthlyBestBoards"
                :key="board.bno"
                cols="6"
                lg="4"
              >
                <v-card
                  flat
                  dark
                >
                  <v-img
                    :src="`/api/file/img/${board.mainPicture}`"
                    :aspect-ratio="16 / 9"
                    gradient="to top, rgba(25,32,72,.4), rgba(25,32,72,.0)"
                    height="600px"
                    class="elevation-2 fill-height"
                  >
                    <div
                      class="d-flex flex-column justify-space-between fill-height"
                    >
                      <v-card-text>
                        <v-btn
                          color="accent"
                          :to="`/board-detail/${board.bno}`"
                        >
                          보러가기
                        </v-btn>
                      </v-card-text>

                      <v-card-text>
                        <div
                          class="text-h5 py-3 font-weight-bold"
                          style="line-height: 1.2"
                        >
                          {{ (board.title).length >= 20 ? (board.title).substr(0,30)+"..." : board.title }}
                        </div>

                        <div class="d-flex align-center">
                          <v-avatar
                            color="accent"
                            size="36"
                          >
                            <v-img
                              :src="`/api/member/profile/img/${board.picturePath}`"
                            />
                          </v-avatar>

                          <div class="pl-2">
                            {{ board.nickname }}
                          </div>
                        </div>
                      </v-card-text>
                    </div>
                  </v-img>
                </v-card>
              </v-col>
            </v-row>
          </div>
        </div>
      </v-col>

      <v-col>
        <div class="pt-16">
          <h3 class="text-h4 font-weight-bold pb-4">
            <v-icon class="pb-1">
              mdi-bell-outline
            </v-icon>
            최신 게시글
          </h3>

          <v-divider />

          <div>
            <v-row
              v-for="board in latestBoards"
              :key="board.bno"
              class="py-2"
            >
              <v-col
                cols="12"
                md="6"
                lg="5"
              >
                <v-card
                  height="100%"
                  flat
                  class="mt-0"
                >
                  <v-img
                    :src="`/api/file/img/${board.mainPicture}`"
                    :aspect-ratio="16 / 9"
                    height="100%"
                  />
                </v-card>
              </v-col>

              <v-col>
                <div>
                  <v-btn
                    depressed
                    color="accent"
                    small
                    :to="`/board-detail/${board.bno}`"
                  >
                    보러가기
                  </v-btn>

                  <h3 class="text-h5 font-weight-bold primary--text py-3">
                    {{ (board.title).length >= 20 ? (board.title).substr(0,30)+"..." : board.title }}
                  </h3>

                  <div class="d-flex align-bottom mt-4">
                    <v-avatar
                      color="accent"
                      size="24"
                    >
                      <v-img
                        :src="`/api/member/profile/img/${board.picturePath}`"
                      />
                    </v-avatar>

                    <div class="pl-2">
                      {{ board.nickname }}
                    </div>
                  </div>
                </div>
              </v-col>
            </v-row>
          </div>
        </div>

        <div class="mt-10">
          <h3 class="text-h5 font-weight-medium pb-4">
            <v-icon class="pb-1">
              mdi-fire
            </v-icon>
            Most Followed
          </h3>

          <v-divider />

          <div class="pt-4">
            <div
              v-for="member in mostFollowedMember"
              :key="member.nickname"
              class="d-flex align-center mb-6"
            >
              <v-avatar
                color="accent"
                size="64"
              >
                <v-img
                  :src="`/api/member/profile/img/${member.picturePath}`"
                />
              </v-avatar>

              <div class="pl-2">
                <div class="text-h6">
                  {{ member.nickname }}
                </div>
                <div class="text-subtitle-1">
                  {{ member.follower }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "Main",
  data:() => ({
    weeklyBestBoards:[],
    monthlyBestBoards:[],
    latestBoards:[],
    mostFollowedMember:[]
  }),
  computed: {
    width() {
      switch (this.$vuetify.breakpoint.name) {
        case "xs":
          return 50;
        case "sm":
          return 100;
        case "md":
          return 500;
        case "lg":
          return 600;
        case "xl":
          return 800;
      }
      return 0;
    }
  },
  created(){
    axios
        .get("/api/board/main/list")
        .then(res => {
          this.weeklyBestBoards = res.data.weeklyBestBoards;
          this.monthlyBestBoards = res.data.monthlyBestBoards;
          this.latestBoards = res.data.latestBoards;
          this.mostFollowedMember = res.data.mostFollowedMember;
        })
  }
};
</script>

<style></style>
