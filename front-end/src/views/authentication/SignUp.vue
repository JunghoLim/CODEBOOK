<template>
  <v-container app>
    <v-row justify="center">
      <v-col cols="auto">
        <v-card width="460">
          <v-alert
            type="error"
            :value="!alert"
          >
            이미 가입된 이메일 입니다.
          </v-alert>
          <v-card-text class="text-center px-12 py-16">
            <div class="text-h4 font-weight-black mb-13">
              회원가입
            </div>
            <validation-observer
              v-slot="{ invalid }"
              ref="observer"
            >
              <v-form
                @submit.prevent="
                  signUp({
                    email: email,
                    nickname: nickname,
                    password: password
                  })
                "
              >
                <validation-provider
                  v-slot="{ errors }"
                  :rules="{ required: true, email: true, max: 40 }"
                  name="Email"
                >
                  <v-text-field
                    v-model="email"
                    label="Email"
                    clearable
                    prepend-icon="mdi-email"
                    :error-messages="errors"
                  />
                </validation-provider>

                <validation-provider
                  v-slot="{ errors }"
                  :rules="{ required: true }"
                  name="Name"
                >
                  <v-text-field
                    v-model="nickname"
                    label="Nickname"
                    clearable
                    prepend-icon="mdi-account-circle"
                    :error-messages="errors"
                  />
                </validation-provider>
                <validation-provider
                  v-slot="{ errors }"
                  name="Password"
                  :rules="{ required: true, min: 10, password }"
                >
                  <v-text-field
                    v-model="password"
                    class="pre-formatted"
                    type="password"
                    label="Password"
                    clearable
                    prepend-icon="mdi-lock-outline"
                    :error-messages="errors"
                  />
                </validation-provider>
                <validation-provider
                  v-slot="{ errors }"
                  name="Password confirm"
                  :rules="{ confirmed: 'Password', required: true, password }"
                >
                  <v-text-field
                    v-model="password_confirm"
                    class="pre-formatted"
                    type="password"
                    label="Password confirm"
                    clearable
                    prepend-icon="mdi-lock-outline"
                    :error-messages="errors"
                  />
                </validation-provider>
                <v-btn
                  class="mt-5"
                  type="submit"
                  block
                  x-large
                  color="primary"
                  rounded
                  :disabled="invalid"
                >
                  회원가입
                </v-btn>
              </v-form>
            </validation-observer>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "SignUp",
  data: () => ({
    email: null,
    nickname: null,
    password: null,
    password_confirm: null
  }),
  computed:{
    alert:{
      get(){
        return this.$store.getters["member/getDuplicatedEmail"];
      }
    }
  },
  methods: {
    signUp(userData) {
      this.$store.dispatch("member/signUp", userData);
    }
  }
};
</script>

<style>
.pre-formatted {
  white-space: pre;
}
</style>
