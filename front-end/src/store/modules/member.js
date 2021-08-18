import Vue from "vue";

const state = {
  agreement: false,
  isLogin: false,
  isLoginError: false,
  member: {
    email: "",
    nickname: ""
  },
  token: ""
};
const getters = {
  getAgreement: state => state.agreement,
  getMember: state => state.member,
  getToken: state => state.token,
  getStatus: state => state.isLogin
};
const mutations = {
  setAgreement(state, data) {
    state.agreement = data;
  }
};
const actions = {
  matchAgreement({ commit }, value) {
    commit("setAgreement", value);
  },
  signIn({ dispatch }, memberInfo) {
    axios
      .post("/api/member", memberInfo)
      .then(response => {
        let token = response.headers["codebook-bearer"];
        localStorage.setItem("codebook-bearer", token);
        dispatch("getMemberInfo");
      })
      .catch(() => {
        alert("아이디 혹은 비밀번호가 잘못 되었습니다.");
      });
  },
  getMemberInfo({ commit, state }) {
    let token = localStorage.getItem("codebook-bearer");
    let config = { headers: { "codebook-bearer": token } };
    axios
      .get("/api/member/data", config)
      .then(res => {
        state.token = token;
        state.isLogin = true;
        state.member.email = res.data.email;
        state.member.nickname = res.data.nickname;
      })
      .catch(() => {
        alert("로그인 기한이 만료되었습니다. 다시 로그인 해주세요.");
      });
    commit;
  },
  signUp({ commit }, userData) {
    axios
      .post("/api/member/new", userData)
      .then(res => {
        console.log(res);
      })
      .catch(err => {});
  }
};
export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
