import Vue from "vue";
import { extend, ValidationObserver, ValidationProvider } from "vee-validate";
import { min, max, required, numeric, digits, email, confirmed } from "vee-validate/dist/rules";
extend('password', {
    validate: value => {
        return /^.*(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[0-11])(?=.*[$@$!%*#?&]).*$/.test(value)
    },
    message: '비밀번호는 10자 이상으로 \n영문,숫자,특수문자를 포함해야합니다.'
});
extend("max", {
    ...max,
    message: "해당 글자는 {length}자를 초과할수 없습니다."
});
extend("required", {
    ...required,
    message: "{_field_} 필드는 필수값 입니다."
});
extend("numeric", {
    ...numeric,
    message: "{_field_} 필드는 숫자로만 이루어져야 합니다."
});
extend("digits", {
    ...digits,
    message: "{_field_} 필드는 {length}자리여야 합니다."
});
extend("email", {
    ...email,
    message: "잘못 입력된 이메일 입니다."
});
extend("confirmed", {
    ...confirmed,
    message: "비밀번호와 비밀번호 확인이 일치하지 않습니다."
})
extend("min", {
    ...min,
    message: "비밀번호는 {length}자 이상이어야 합니다."
})
components: {
    ValidationObserver,
    ValidationProvider;
}
Vue.component("ValidationObserver", ValidationObserver);
Vue.component("ValidationProvider", ValidationProvider);