<template>
  <v-container
    id="dashboard"
    fluid
    tag="section"
    class="pa-10"
  >
    <v-row>
      <v-col
        cols="12"
        sm="6"
        lg="3"
      >
        <material-stats-card
          color="info"
          icon="mdi-account"
          title="등록 회원수"
          :value="memberCnt"
        />
      </v-col>

      <v-col
        cols="12"
        sm="6"
        lg="3"
      >
        <material-stats-card
          color="primary"
          icon="mdi-account-alert"
          title="정지 회원 수"
          :value="blockedMemberCnt"
        />
      </v-col>

      <v-col
        cols="12"
        sm="6"
        lg="3"
      >
        <material-stats-card
          color="success"
          icon="mdi-clipboard-text"
          title="일일 개시글 수"
          :value="todayBoardCnt"
        />
      </v-col>

      <v-col
        cols="12"
        sm="6"
        lg="3"
      >
        <material-stats-card
          color="orange"
          icon="mdi-clipboard-text-multiple"
          title="주간 개시글 수"
          :value="weeklyBoardCnt"
        />
      </v-col>

      <v-col
        cols="12"
      >
        <material-card
          color="error"
          class="px-5 py-3"
        >
          <template v-slot:heading>
            <v-row>
              <v-icon size="34">
                mdi-account-check
              </v-icon>
              <div
                class="text-h3 font-weight-light ml-3"
              >
                회원 관리
              </div>
            </v-row>
          </template>
          <v-card-text>
            <v-data-table
              :headers="member_headers"
              :items="items"
              hide-default-footer
            />
            <v-pagination
              v-model="memberPage"
              :length="memberPagination"
              @input="pageNext"
            />
          </v-card-text>
        </material-card>
      </v-col>

      <v-col
        cols="12"
      >
        <material-card
          color="warning"
          class="px-5 py-3"
        >
          <template v-slot:heading>
            <v-row>
              <v-icon size="34">
                mdi-clipboard-check
              </v-icon>
              <div
                class="text-h3 font-weight-light ml-3"
              >
                게시글 관리
              </div>
            </v-row>
          </template>
          <v-card-text>
            <v-data-table
              :headers="board_headers"
              :items="boards"
              hide-default-footer
            />
            <v-pagination
              v-model="boardPage"
              :length="boardPagination"
              @input="pageNext"
            />
          </v-card-text>
        </material-card>
      </v-col>

      <v-col
        cols="12"
      >
        <material-card class="px-5 py-3">
          <template v-slot:heading>
            <v-tabs
              v-model="tabs"
              background-color="transparent"
              slider-color="white"
            >
              <span
                class="subheading font-weight-light mx-3"
                style="align-self: center"
              >Tasks:</span>
              <v-tab class="mr-3">
                <v-icon class="mr-2">
                  mdi-code-tags
                </v-icon>
                건의
              </v-tab>
              <v-tab>
                <v-icon class="mr-2">
                  mdi-cloud
                </v-icon>
                공지
              </v-tab>
            </v-tabs>
          </template>

          <v-tabs-items
            v-model="tabs"
            class="transparent"
          >
            <v-tab-item
              v-for="n in 3"
              :key="n"
            >
              <v-card-text>
                <template v-for="(task, i) in tasks[tabs]">
                  <v-row
                    :key="i"
                    align="center"
                  >
                    <v-col cols="10">
                      <div
                        class="font-weight-light"
                        v-text="task.text"
                      />
                    </v-col>

                    <v-col
                      cols="2"
                      class="text-right"
                    >
                      <v-icon class="mx-1">
                        mdi-pencil
                      </v-icon>
                      <v-icon
                        color="error"
                        class="mx-1"
                        @click="alert"
                      >
                        mdi-close
                      </v-icon>
                    </v-col>
                  </v-row>
                </template>
              </v-card-text>
            </v-tab-item>
          </v-tabs-items>
        </material-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import MaterialStatsCard from '@/components/MaterialStatsCard'
import MaterialCard from '@/components/MaterialCard'
  export default {
    name: 'AdminDashboard',
    components:{
      MaterialStatsCard,
      MaterialCard,
    },
    data () {
      return {
        memberCnt: 0,
        blockedMemberCnt: 0,
        todayBoardCnt: 0,
        weeklyBoardCnt: 0,
        member_headers: [
          {
            sortable: false,
            text: '닉네임',
            value: 'nickname',
          },
          {
            sortable: false,
            text: '이메일',
            value: 'email',
          },
          {
            sortable: false,
            text: '게시글수',
            value: 'boardCnt',
            align: 'right',
          },
          {
            sortable: false,
            text: '팔로워',
            value: 'follower',
            align: 'right',
          },
          {
            sortable: false,
            text: '상태',
            value: 'status',
            align: 'right',
          },
        ],
        board_headers: [
          {
            sortable: false,
            text: '번호',
            value: 'bno',
          },
          {
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
            align: 'right',
          },
          {
            sortable: false,
            text: '추천수',
            value: 'recommend',
            align: 'right',
          },
          {
            sortable: false,
            text: '댓글수',
            value: 'comment_cnt',
            align: 'right',
          },
          {
            sortable: false,
            text: '작성일자',
            value: 'writedate',
            align: 'right',
          },
        ],
        members: [
        ],
        boards: [
        ],
        tabs: 0,
        tasks: {
          0: [
            {
              text: 'Sign contract for "What are conference organizers afraid of?"',
              value: true,
            },
            {
              text: 'Lines From Great Russian Literature? Or E-mails From My Boss?',
              value: false,
            },
            {
              text: 'Flooded: One year later, assessing what was lost and what was found when a ravaging rain swept through metro Detroit',
              value: false,
            },
            {
              text: 'Create 4 Invisible User Experiences you Never Knew About',
              value: true,
            },
          ],
          1: [
            {
              text: 'Flooded: One year later, assessing what was lost and what was found when a ravaging rain swept through metro Detroit',
              value: true,
            },
            {
              text: 'Sign contract for "What are conference organizers afraid of?"',
              value: false,
            },
          ],
          2: [
            {
              text: 'Lines From Great Russian Literature? Or E-mails From My Boss?',
              value: false,
            },
            {
              text: 'Flooded: One year later, assessing what was lost and what was found when a ravaging rain swept through metro Detroit',
              value: true,
            },
            {
              text: 'Sign contract for "What are conference organizers afraid of?"',
              value: true,
            },
          ],
        },
      }
    },

    methods: {
      alert(){
        alert('clcik');
      },
      pageNext(page){
      let params = {'page': page, 'category': this.category}
      this.$store.dispatch("board/pageNext", params);
    }
    },
  }
</script>
