<template>
  <v-container class="fill-height">
    <v-row justify="center">
      <v-col cols="auto">
        <v-card width="460">
          <v-card-text class="text-center px-12 py-16">
            <div class="text-h4 font-weight-black mb-13">
              로그인
            </div>
            <validation-observer
              v-slot="{ invalid }"
              ref="observer"
            >
              <v-form
                @submit.prevent="signIn({ email: email, password: password })"
              >
                <validation-provider
                  v-slot="{ errors }"
                  :rules="{ email: 30, required: true }"
                  name="Email"
                >
                  <v-text-field
                    v-model="email"
                    type="email"
                    clearable
                    label="Email"
                    prepend-icon="mdi-email"
                    :error-messages="errors"
                  />
                </validation-provider>

                <validation-provider
                  v-slot="{ errors }"
                  :rules="{ required: true, password }"
                  name="Email"
                >
                  <v-text-field
                    v-model="password"
                    class="pre-formatted"
                    type="password"
                    clearable
                    prepend-icon="mdi-lock"
                    label="Password"
                    :error-messages="errors"
                  />
                </validation-provider>
                <v-btn
                  type="submit"
                  block
                  x-large
                  class="mt-3"
                  rounded
                  color="primary"
                  :disabled="invalid"
                >
                  로그인
                </v-btn>
                <div class="mt-3">
                  <router-link
                    class="text-decoration-none"
                    to="/"
                  >
                    홈
                  </router-link>|
                  <router-link
                    class="text-decoration-none"
                    to="/agreement"
                  >
                    회원가입
                  </router-link>
                </div>
              </v-form>
            </validation-observer>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "SignIn",
  data: () => ({
    email: null,
    password: null
  }),
  methods: {
    signIn(data) {
      this.$store.dispatch("app/signIn", data);
      this.$router.push("/");
    }
  }
};
</script>

<style>
.pre-formatted {
  white-space: pre;
}
</style>
