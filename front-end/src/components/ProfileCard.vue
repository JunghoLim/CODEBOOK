<template>
  <v-card
    class="mx-auto"
    max-width="344"
    outlined
  >
    <v-list-item three-line>
      <v-list-item-content>
        <div class="text-overline mb-4">
          {{ email }}
        </div>
        <v-list-item-title class="text-h3 mb-1">
          {{ nickname }}
        </v-list-item-title>
        <v-list-item-subtitle>
          <!-- eslint-disable vue/no-v-html -->
          <p
            class="font-weight-light grey--text"
            v-html="aboutMe"
          />
          <!--eslint-enable-->
        </v-list-item-subtitle>
      </v-list-item-content>

      <v-list-item-avatar
        size="80"
        color="grey"
      >
        <v-img :src="avatar" />
      </v-list-item-avatar>
    </v-list-item>

    <v-card-actions>
      <v-btn
        color="primary"
        @click="following"
      >
        팔로우
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name:'ProfileCard',
  props:{
    email:{
      type:String,
      default: ''
    },
    nickname:{
      type:String,
      default: ''
    },
    aboutMe:{
      type:String,
      default: ''
    },
    avatar:{
      type:String,
      default: ''
    },
  },
  beforeMount(){
      this.aboutMe = this.aboutMe.replace(/(?:\r\n|\r|\n)/g, '<br />');
      this.aboutMe = this.aboutMe.split('\n').join('<br />');
  },
  methods:{
    following(){
      if(this.$store.getters['member/getStatus']){
        let member = this.$store.getters['member/getMember'];
        let token = this.$store.getters['member/getToken'];
        let fromEmail = member.email;
        let config = { headers: { "codebook-bearer": token } };
        if(fromEmail == this.email){
          alert('자신은 팔로우 하실수 없습니다.');
        }else{
        axios
          .post('/api/member/follow', {'from':fromEmail, 'to':this.email}, config)
          .then(res => {
            if(res.data == 1){
              alert(this.email+' 님을 팔로우 하셨습니다.');
            }else{
              alert(this.email+' 님 팔로우를 취소하셨습니다.');
            }
          })
        }
      }else{
        alert('로그인을 해주세요.');
      }
    }
  }
}
</script>

<style>

</style>
