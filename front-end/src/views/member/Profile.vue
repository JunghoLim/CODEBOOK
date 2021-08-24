<template>
  <v-container
    id="user-profile"
    tag="section"
  >
    <v-row justify="center">
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
            <v-form>
              <v-container class="py-0">
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
                    <v-text-field
                      class="purple-input"
                      label="Nickname"
                      :placeholder="memberInfo.nickname"
                    />
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
        <material-card
          icon="mdi-clipboard-text"
          title="내 게시물"
          class="px-5 py-3"
        >
          <v-simple-table>
            <thead>
              <tr>
                <th class="primary--text">
                  ID
                </th>
                <th class="primary--text">
                  Name
                </th>
                <th class="primary--text">
                  Country
                </th>
                <th class="primary--text">
                  City
                </th>
                <th class="text-right primary--text">
                  Salary
                </th>
              </tr>
            </thead>

            <tbody>
              <tr>
                <td>1</td>
                <td>Dakota Rice</td>
                <td>Niger</td>
                <td>Oud-Turnhout</td>
                <td class="text-right">
                  $36,738
                </td>
              </tr>

              <tr>
                <td>2</td>
                <td>Minverva Hooper</td>
                <td>Curaçao</td>
                <td>Sinaas-Waas</td>
                <td class="text-right">
                  $23,789
                </td>
              </tr>

              <tr>
                <td>3</td>
                <td>Sage Rodriguez</td>
                <td>Netherlands</td>
                <td>Baileux</td>
                <td class="text-right">
                  $56,142
                </td>
              </tr>

              <tr>
                <td>4</td>
                <td>Philip Chaney</td>
                <td>Korea, South</td>
                <td>Overland Park</td>
                <td class="text-right">
                  $38,735
                </td>
              </tr>

              <tr>
                <td>5</td>
                <td>Doris Greene</td>
                <td>Malawi</td>
                <td>Feldkirchen in Kärnten</td>
                <td class="text-right">
                  $63,542
                </td>
              </tr>

              <tr>
                <td>6</td>
                <td>Mason Porter</td>
                <td>Chile</td>
                <td>Gloucester</td>
                <td class="text-right">
                  $78,615
                </td>
              </tr>
            </tbody>
          </v-simple-table>
        </material-card>
      </v-col>

      <v-col
        cols="12"
        md="4"
      >
        <material-card
          class="v-card-profile "
          avatar="https://demos.creative-tim.com/vue-material-dashboard/img/marc.aba54d65.jpg"
        >
          <v-card-text class="text-center">
            <h4 class="text-h3 font-weight-light mb-3 black--text">
              {{ memberInfo.nickname }}
            </h4>

            <p class="font-weight-light grey--text">
              {{ memberInfo.aboutMe }}
            </p>

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
              value="+245"
              sub-icon="mdi-clock"
              sub-text="Just Updated"
            />
          </v-card-text>
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
  }),
  computed:{
    buttonText() {
      return this.selectedFile ? this.selectedFile.name : this.defaultButtonText
    },
    memberInfo:{
      get(){
        return this.$store.getters["member/getMember"]
      }
    }
  },
  methods: {
    onButtonClick() {
      this.isSelecting = true
      window.addEventListener('focus', () => {
        this.isSelecting = false
      }, { once: true })

      this.$refs.uploader.click()
    },
    onFileChanged(e) {
      this.selectedFile = e.target.files[0]

      console.log(this.selectedFile);
    }
  }
}
</script>

<style>

</style>
