const state = {
    agreement: false,
    drawer: false,
    public_items: [
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
                    icon: "mdi-clipboard-list",
                    items: [{
                            title: "일상",
                            to: "/board-list/free/1"
                        },
                        {
                            title: "정보 공유",
                            to: "/board-list/sharing/1"
                        },
                        {
                            title: "스터디/모임",
                            to: "/board-list/gathering/1"
                        }
                    ]
                },
                {
                    title: "기술",
                    icon: "mdi-code-tags",
                    items: [{
                        title: "Tips",
                        to: "/tip"
                    }]
                }
            ]
        },
        {
            title: "건의/공지",
            icon: "mdi-bullhorn",
            items: [{
                    title: "공지사항",
                    icon: "mdi-account-group",
                    to: '/board-list/notice/1'
                },
                {
                    title: "건의사항",
                    icon: "mdi-account-group-outline",
                    to: '/board-list/suggestion/1'
                }
            ]
        }
    ],
    member_items: [
        { title: "메인", icon: "mdi-view-dashboard", to: "/" },
        {
            title: "회원",
            icon: "mdi-account-multiple",
            items: [{
                    title: "로그아웃",
                    icon: "mdi-account-multiple-check",
                    to: "/sign-in"
                },
                {
                    title: "프로필",
                    icon: "mdi-account-multiple-plus",
                    to: "/profile"
                }
            ]
        },
        {
            title: "커뮤니티",
            icon: "mdi-google-circles-communities",
            items: [{
                    title: "자유게시판",
                    icon: "mdi-clipboard-list",
                    items: [{
                            title: "일상",
                            to: "/board-list/free/1"
                        },
                        {
                            title: "정보 공유",
                            to: "/board-list/sharing/1"
                        },
                        {
                            title: "스터디/모임",
                            to: "/board-list/gathering/1"
                        }
                    ]
                },
                {
                    title: "기술",
                    icon: "mdi-code-tags",
                    items: [{
                        title: "Tips",
                        to: "/tip"
                    }]
                }
            ]
        },
        {
            title: "건의/공지",
            icon: "mdi-bullhorn",
            items: [{
                    title: "공지사항",
                    icon: "mdi-account-group",
                    to: '/board-list/notice/1'
                },
                {
                    title: "건의사항",
                    icon: "mdi-account-group-outline",
                    to: '/board-list/suggestion/1'
                }
            ]
        }
    ],
    admin_items: [
        { title: "관리자 페이지", icon: "mdi-view-dashboard", to: "/admin" },
        {
            title: "회원",
            icon: "mdi-account-multiple",
            items: [{
                    title: "로그아웃",
                    icon: "mdi-account-multiple-check",
                    to: "/sign-in"
                },
                {
                    title: "프로필",
                    icon: "mdi-account-multiple-plus",
                    to: "/profile"
                }
            ]
        },
        {
            title: "커뮤니티",
            icon: "mdi-google-circles-communities",
            items: [{
                    title: "자유게시판",
                    icon: "mdi-clipboard-list",
                    items: [{
                            title: "일상",
                            to: "/board-list/free/1"
                        },
                        {
                            title: "정보 공유",
                            to: "/board-list/sharing/1"
                        },
                        {
                            title: "스터디/모임",
                            to: "/board-list/gathering/1"
                        }
                    ]
                },
                {
                    title: "기술",
                    icon: "mdi-code-tags",
                    items: [{
                        title: "Tips",
                        to: "/tip"
                    }]
                }
            ]
        },
        {
            title: "건의/공지",
            icon: "mdi-bullhorn",
            items: [{
                    title: "공지사항",
                    icon: "mdi-account-group",
                    to: '/board-list/notice/1'
                },
                {
                    title: "건의사항",
                    icon: "mdi-account-group-outline",
                    to: '/board-list/suggestion/1'
                }
            ]
        }
    ]
};
const getters = {
    getAgreement: state => state.agreement,
    getPublicItems: state => state.public_items,
    getMemberItems: state => state.member_items,
    getAdminItems: state => state.admin_items,
    getDrawer: state => state.drawer
};
const mutations = {
    setAgreement(state, data) {
        state.agreement = data;
    },
    setDrawer(state, data) {
        state.drawer = data;
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