import Vue from "vue";

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
};
const mutations = {
    setAgreement(state, data) {
        state.agreement = data;
    }
};
const actions = {
    matchAgreement({ commit }, value) {
        commit("setAgreement", value);
    }
};
export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
};
