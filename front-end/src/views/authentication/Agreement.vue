<template>
  <v-container>
    <v-card
      elevation="3"
      tile
    >
      <v-app-bar color="white">
        <v-toolbar-title>회원 약관</v-toolbar-title>
        <v-spacer />
        <v-checkbox
          v-model="checked"
          label="동의 합니다."
          color="primary"
          hide-details
          class="mr-6"
        />
        <v-btn
          color="primary"
          :disabled="!checked"
          @click="agreementCheck(checked)"
        >
          다음
        </v-btn>
      </v-app-bar>
      <v-sheet
        id="scrolling-techniques-7"
        class="overflow-y-auto"
        max-height="400"
      >
        <v-container style="height: 1000px;">
          이 사이트는 포트폴리오용 사이트 임으로 회원약관은 존재하지 않습니다.
        </v-container>
      </v-sheet>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name:'Agreement',
  data: () => ({
    checked: false
  }),
  methods:{
    agreementCheck(data){
      this.$store.dispatch('app/matchAgreement', data);
      this.$router.push('/sign-up')
    }
  }
}
</script>

<style>

</style>
