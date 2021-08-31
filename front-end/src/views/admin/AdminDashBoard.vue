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
        lg="6"
      >
        <material-chart-card
          :data="emailsSubscriptionChart.data"
          :options="emailsSubscriptionChart.options"
          :responsive-options="emailsSubscriptionChart.responsiveOptions"
          color="#E91E63"
          hover-reveal
          type="Bar"
        >
          <template v-slot:reveal-actions>
            <v-tooltip bottom>
              <template v-slot:activator="{ attrs, on }">
                <v-btn
                  v-bind="attrs"
                  color="info"
                  icon
                  v-on="on"
                >
                  <v-icon
                    color="info"
                  >
                    mdi-refresh
                  </v-icon>
                </v-btn>
              </template>

              <span>Refresh</span>
            </v-tooltip>

            <v-tooltip bottom>
              <template v-slot:activator="{ attrs, on }">
                <v-btn
                  v-bind="attrs"
                  light
                  icon
                  v-on="on"
                >
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>
              </template>

              <span>Change Date</span>
            </v-tooltip>
          </template>

          <h4 class="card-title font-weight-light mt-2 ml-2">
            월별 방문자 수
          </h4>

          <p class="d-inline-flex font-weight-light ml-2 mt-1">
            <percentage-arrow :data="-time" />
            전월 대비 방문율
          </p>
        </material-chart-card>
      </v-col>

      <v-col
        cols="12"
        lg="6"
      >
        <material-chart-card
          :data="dailySalesChart.data"
          :options="dailySalesChart.options"
          color="success"
          hover-reveal
          type="Line"
        >
          <template v-slot:reveal-actions>
            <v-tooltip bottom>
              <template v-slot:activator="{ attrs, on }">
                <v-btn
                  v-bind="attrs"
                  color="info"
                  icon
                  v-on="on"
                >
                  <v-icon
                    color="info"
                  >
                    mdi-refresh
                  </v-icon>
                </v-btn>
              </template>

              <span>Refresh</span>
            </v-tooltip>

            <v-tooltip bottom>
              <template v-slot:activator="{ attrs, on }">
                <v-btn
                  v-bind="attrs"
                  light
                  icon
                  v-on="on"
                >
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>
              </template>

              <span>Change Date</span>
            </v-tooltip>
          </template>

          <h4 class="card-title font-weight-light mt-2 ml-2">
            월별 게시글 수
          </h4>

          <p class="d-inline-flex font-weight-light ml-2 mt-1">
            <percentage-arrow :data="time" />
            전월 대비 개시글 작성률
          </p>
        </material-chart-card>
      </v-col>

      <v-col
        cols="12"
        sm="6"
        lg="3"
      >
        <material-stats-card
          color="info"
          icon="mdi-account"
          title="일일 방문자 수"
          value="+245"
        />
      </v-col>

      <v-col
        cols="12"
        sm="6"
        lg="3"
      >
        <material-stats-card
          color="primary"
          icon="mdi-account-multiple"
          title="주간 방문자 수"
          value="75.521"
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
          value="$ 34,245"
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
          value="184"
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
              :headers="headers"
              :items="items"
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
                  mdi-bug
                </v-icon>
                Bugs
              </v-tab>
              <v-tab class="mr-3">
                <v-icon class="mr-2">
                  mdi-code-tags
                </v-icon>
                Website
              </v-tab>
              <v-tab>
                <v-icon class="mr-2">
                  mdi-cloud
                </v-icon>
                Server
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
                    <v-col cols="1">
                      <v-list-item-action>
                        <v-checkbox
                          v-model="task.value"
                          color="secondary"
                        />
                      </v-list-item-action>
                    </v-col>

                    <v-col cols="9">
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
import MaterialChartCard from '@/components/MaterialChartCard'
import MaterialStatsCard from '@/components/MaterialStatsCard'
import MaterialCard from '@/components/MaterialCard'
import PercentageArrow from '@/components/PercentageArrow'
  export default {
    name: 'AdminDashboard',
    components:{
      MaterialChartCard,
      MaterialStatsCard,
      MaterialCard,
      PercentageArrow
    },
    data () {
      return {
        time: -10,
        dailySalesChart: {
          data: {
            labels: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
            series: [
              [3122, 4431, 3206, 7808, 5533, 4532, 3268, 4345, 5685, 6108, 1222, 8952],
            ],
          },
          options: {
            lineSmooth: this.$chartist.Interpolation.cardinal({
              tension: 30,
            }),
            low: 0,
            high: 10000, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
            chartPadding: {
              top: 0,
              right: 0,
              bottom: 0,
              left: 0,
            },
          },
        },
        dataCompletedTasksChart: {
          data: {
            labels: ['12am', '3pm', '6pm', '9pm', '12pm', '3am', '6am', '9am'],
            series: [
              [230, 750, 450, 300, 280, 240, 200, 190],
            ],
          },
          options: {
            lineSmooth: this.$chartist.Interpolation.cardinal({
              tension: 0,
            }),
            low: 0,
            high: 1000, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
            chartPadding: {
              top: 0,
              right: 0,
              bottom: 0,
              left: 0,
            },
          },
        },
        emailsSubscriptionChart: {
          data: {
            labels: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
            series: [
              [542, 443, 320, 780, 553, 453, 326, 434, 568, 610, 756, 895],

            ],
          },
          options: {
            axisX: {
              showGrid: false,
            },
            low: 0,
            high: 1000,
            chartPadding: {
              top: 0,
              right: 0,
              bottom: 0,
              left: 0,
            },
          },
          responsiveOptions: [
            ['screen and (max-width: 640px)', {
              seriesBarDistance: 5,
              axisX: {
                labelInterpolationFnc: function (value) {
                  return value[0]
                },
              },
            }],
          ],
        },
        headers: [
          {
            sortable: false,
            text: 'Nickname',
            value: 'nickname',
          },
          {
            sortable: false,
            text: 'Email',
            value: 'email',
          },
          {
            sortable: false,
            text: 'Posts',
            value: 'posts',
            align: 'right',
          },
          {
            sortable: false,
            text: 'Status',
            value: 'status',
            align: 'right',
          },
        ],
        board_headers: [
          {
            sortable: false,
            text: 'Nickname',
            value: 'nickname',
          },
          {
            sortable: false,
            text: 'Email',
            value: 'email',
          },
          {
            sortable: false,
            text: 'Title',
            value: 'title',
          },
          {
            sortable: false,
            text: 'Views',
            value: 'views',
            align: 'right',
          },
          {
            sortable: false,
            text: 'Recommends',
            value: 'recommends',
            align: 'right',
          },
        ],
        items: [
          {
            nickname: 'jungho',
            email: 'test@testemail.com',
            posts: 0,
            status: 'NONBLOCKED',
          }
        ],
        boards: [
          {
            nickname: 'jungho',
            email: 'test@testemail.com',
            title: '첫번째 개시글',
            views: 0,
            recommends: 0,
          }
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
        list: {
          0: false,
          1: false,
          2: false,
        },
      }
    },

    methods: {
      complete (index) {
        this.list[index] = !this.list[index]
      },
    },
  }
</script>
