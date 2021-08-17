import Vue from "vue";

const state = {
    agreement: false,
    isLogin: false,
    isLoginError: false,
    member: {
        email: '',
        nickname: ''
    },
    token: ''
};
const getters = {
    getAgreement: state => state.agreement,
    getMemberInfo: state => state.member,
    getToken: state => state.token
};
const mutations = {
    setAgreement(state, data) {
        state.agreement = data;
    },
    setMemberInfo(state, data) {
        state.member.email = data.member;
        state.member.nickname = data.nickname;
    },
    setToken(state, data) {
        state.token = data.token;
    }
};
const actions = {
    matchAgreement({ commit }, value) {
        commit("setAgreement", value);
    },
    signIn({ commit }, memberInfo) {
        axios
            .post("/member", memberInfo)
            .then(response => {
                let token = response.headers['codebook-bearer'];
                let config = { headers: { 'codebook-bearer': token } }
                console.log("here1");
                axios.get("/member/data", config).then(res => {
                    console.log(res.data.email);
                }).catch(() => {
                    alert('로그인 기한이 만료되었습니다. 다시 로그인 해주세요.');
                })
            })
            .catch(err => {
                alert('아이디 혹은 비밀번호가 잘못 되었습니다.')
            });
        commit;
    }
};
export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
};
