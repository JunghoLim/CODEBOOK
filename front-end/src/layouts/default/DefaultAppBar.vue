<template>
  <v-app-bar
    app
    class="blue lighten-1"
    dark
    clipped-left
  >
    <v-app-bar-nav-icon @click="setDrawer(!drawer)" />

    <router-link to="/">
      <v-toolbar-title class="pl-0">
        <v-img
          src="@/assets/logo_codebook.png"
          height="50"
          min-height="50"
          width="200"
          min-width="200"
          class="mb-1"
          position="left"
        />
      </v-toolbar-title>
    </router-link>
    <v-spacer />
    <template
      v-if="!$vuetify.breakpoint.smAndDown"
    >
      <v-text-field
        v-model="searchText"
        color="secondary"
        hide-details
        style="max-width: 240px;"
        placeholder="Search"
        @keyup.enter="searchBtn(searchText)"
      >
        <template
          v-slot:append-outer
        >
          <v-btn
            color="white"
            class="mt-n2"
            elevation="1"
            fab
            small
            outlined
            @click="searchBtn(searchText)"
          >
            <v-icon>mdi-magnify</v-icon>
          </v-btn>
        </template>
      </v-text-field>
    </template>

    <div class="mx-3" />

    <template v-if="!$vuetify.breakpoint.mobile">
      <v-btn
        class="ml-2"
        min-width="0"
        text
        to="/"
      >
        <v-icon>mdi-view-dashboard</v-icon>
        HOME
      </v-btn>
    </template>

    <template v-if="loginStatus">
      <v-btn
        class="ml-2"
        min-width="0"
        text
        @click="logout()"
      >
        <v-icon>mdi-account</v-icon>
        Logout
      </v-btn>
    </template>
    <template v-else>
      <v-btn
        class="ml-2"
        min-width="0"
        text
        to="/sign-in"
      >
        <v-icon>mdi-account</v-icon>
        Login
      </v-btn>
    </template>
  </v-app-bar>
</template>

<script>
 import { mapState, mapMutations } from 'vuex'
export default {
  name: "DefaultAppBar",
  data:() => ({
    notifications: [
        'Mike John Responded to your email',
        'You have 5 new tasks',
        'You\'re now friends with Andrew',
        'Another Notification',
        'Another one',
      ],
      searchText: ''
  }),
  computed:{
    ...mapState('app',['drawer']),
    loginStatus:{
      get(){
        return this.$store.getters["member/getStatus"]
      }
    },
  },
  methods:{
    ...mapMutations({
        setDrawer: 'app/setDrawer',
      }),
    logout(){
      this.$store.dispatch("member/logout");
    },
    searchBtn(text){
      this.$router.push('/search/'+text+"/1");
    }
  }
};
</script>

<style></style>
