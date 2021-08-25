import router from "@/router";

const state = {
    agreement: false,
    isLogin: false,
    isLoginError: false,
    member: {
        email: "",
        nickname: "",
        follower: 0,
        aboutMe: "",
        picturePath: ''
    },
    token: "",
    duplicatedEmail: false
};
const getters = {
    getAgreement: state => state.agreement,
    getMember: state => state.member,
    getToken: state => state.token,
    getStatus: state => state.isLogin,
    getDuplicatedEmail: state => state.duplicatedEmail
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
            .post("/api/auth/member", memberInfo)
            .then(response => {
                let token = response.headers["codebook-bearer"];
                localStorage.setItem("codebook-bearer", token);
                dispatch("getMemberInfo");
                router.push('/');
            })
            .catch(() => {
                alert("아이디 혹은 비밀번호가 잘못 되었습니다.");
            });
    },
    getMemberInfo({ commit, state }) {
        let token = localStorage.getItem("codebook-bearer");
        let config = { headers: { "codebook-bearer": token } };
        if (!token) {
            return;
        }
        axios
            .get("/api/member", config)
            .then(res => {
                if (res.headers['codebook-bearer']) {
                    localStorage['codebook-bearer'] = res.headers['codebook-bearer'];
                }
                state.token = token;
                state.isLogin = true;
                state.member.email = res.data.email;
                state.member.nickname = res.data.profile.nickname;
                state.member.follower = res.data.profile.follower;
                state.member.aboutMe = res.data.profile.aboutMe;
                state.member.picturePath = '/api/member/profile/img/' + res.data.profile.picturePath;
            })
            .catch(() => {
                localStorage.removeItem('codebook-bearer');
                alert("로그인 기한이 만료되어 로그아웃 처리 되었습니다.\n다시 로그인 해주세요.");
            });
        commit;
    },
    signUp({ commit, dispatch, state }, userData) {
        dispatch('emailDuplicateCheck', userData.email)
            .then(() => {
                if (state.duplicatedEmail) {
                    axios
                        .post("/api/member/new", userData)
                        .then(() => {
                            alert('회원가입에 성공했습니다.\n로그인 해주세요.');
                            router.push('/sign-in');
                        })
                        .catch(() => {
                            alert('회원가입에 실패했습니다.\n다시 시도해 주세요.');
                            router.go();
                        });
                }
            });
        commit;
    },
    logout({ commit, dispatch }) {
        dispatch('getMemberInfo');
        let token = localStorage.getItem("codebook-bearer");
        let config = { headers: { "codebook-bearer": token } };
        axios
            .delete("/api/auth/member", config)
            .then(() => {
                localStorage.removeItem('codebook-bearer');
                router.go('/');
            })
            .catch(() => {
                console.log('로그아웃 오류!\n다시 시도해 주세요.');
                router.go();
            });
        commit;
    },
    emailDuplicateCheck({ commit, state }, email) {
        axios
            .get('/api/member/duplicate', { params: { 'email': email } })
            .then(res => {
                let result = res.data;
                if (result == 0) {
                    state.duplicatedEmail = true;
                }
            })
            .catch(() => {
                alert('아이디 중복체크에 실패했습니다.\n다시 시도해 주세요.');
                router.go();
            });
        commit;
    },
    profileUpdate({ commit, dispatch, state }, profileForm) {
        dispatch('getMemberInfo');
        let config = { headers: { "codebook-bearer": state.token } };
        axios
            .post("/api/member/profile", profileForm, config)
            .then(() => {
                alert('프로필이 성공적으로 업데이트 되었습니다.');
                router.go();
            })
            .catch(() => {
                alert('프로필 업데이트에 실패했습니다.\n다시 시도해 주세요.');
            });
        commit;
    },
    profileImgUpdate({ commit, state }, file) {
        let config = { headers: { 'codebook-bearer': state.token, 'Content-Type': 'multipart/form-data' } };
        const formData = new FormData();
        formData.append('file', file);
        axios
            .post("/api/member/profile/img", formData, config)
            .then(() => {
                alert('프로필 사진이 성공적으로 업데이트 되었습니다.');
                router.go();
            })
            .catch(() => {
                alert('프로필 사진 업데이트에 실패했습니다.\n다시 시도해 주세요.');
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
