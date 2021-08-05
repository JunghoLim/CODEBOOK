<template>
  <v-container
    app
  >
    <v-row justify="center">
      <v-col cols="auto">
        <v-card width="460">
          <v-card-text class="text-center px-12 py-16">
            <div class="text-h4 font-weight-black mb-13">
              회원가입
            </div>
            <validation-observer
              v-slot="{invalid}"
              ref="observer"
            >
              <v-form>
                <validation-provider
                  v-slot="{errors}"
                  :rules="{ required: true, email: true, max: 40}"
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
                  :rules="{ required: true}"
                  name="Name"
                >
                  <v-text-field
                    v-model="name"
                    label="Name"
                    clearable
                    prepend-icon="mdi-account-circle"
                    :error-messages="errors"
                  />
                </validation-provider>
                <validation-provider
                  v-slot="{ errors }"
                  name="Password"
                  :rules="{ required: true, min:10, password }"
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
                  type="submit"
                  block
                  x-large
                  color="primary mt-6"
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
  name:'SignUp',
  data: () => ({
    email: null,
    name: null,
    password: null,
    password_confirm: null,
  })
}
</script>

<style>
.pre-formatted {
  white-space: pre;
}
</style>
