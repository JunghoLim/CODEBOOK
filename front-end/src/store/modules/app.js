const state = {
    agreement: false,

    items: [
        { title: "메인", icon: "mdi-view-dashboard", to: "/" },
        {
            title: "회원",
            icon: "mdi-account-multiple",
            items: [{
                    title: "로그인",
                    icon: "mdi-account-multiple-check",
                    to: "/sign-in"
                },
                {
                    title: "회원가입",
                    icon: "mdi-account-multiple-plus",
                    to: "/agreement"
                }
            ]
        },
        {
            title: "커뮤니티",
            icon: "mdi-google-circles-communities",
            items: [{
                    title: "자유게시판",
                    icon: "",
                    items: [{
                        title: "일상",
                        to: "/daily-board"
                    }]
                },
                {
                    title: "기술",
                    items: [{
                        title: "Tips",
                        to: "/tip"
                    }]
                }
            ]
        }
    ]
};
const getters = {
    getAgreement: state => state.agreement,
    getItems: state => state.items,
    getEmail: state => state.email,
    getPassword: state => state.password
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
    signIn({ commit }, userInfo) {
        axios //로그인한 아이디 비밀번호가 맞는지 확인
            .post("http://localhost:3000/api/member/new", userInfo)
            .then(res => {
                //성공하면 토큰이 서버에서 나오는 부분
                //토큰을 헤더에 저장시켜서 유저 정보 요청
                // axios
                // .get("http://localhost:3000/api/member/userinfo", config)
                // .then(response => {})
                // .catch(error => {});
                //틀리면
                console.log(JSON.stringify(userInfo) + "      " + "success");
            })
            .catch(err => {
                console.log(err);
                console.log(userInfo);
            });
        //로그인 토큰 받는 방법
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