const state = {
    boardDetailData: [],
    comment: [],
    isMessage: false
};
const getters = {
    getBoardDetailData: state => state.boardDetailData,
    getCommentData: state => state.comment,
    getIsMessage: statet => state.isMessage
};
const mutations = {
    setBoardDetailData(state, result) {
        state.boardDetailData = result;
    },
    setCommentData(state, result) {
        state.comment = result;
    },
    setIsMessage(state, result) {
        state.isMessage = result;
    }
};
const actions = {
    changeBoardDetailData({ commit }, bno) {
        axios
            .get("/api/board", { params: { bno: bno } })
            .then(res => {
                commit("setBoardDetailData", res.data.board_detail);
            })
            .catch(err => {});
    },
    changeCommentData({ commit }, bno) {
        axios
            .get("/api/board/comment", { params: { bno: bno } })
            .then(res => {
                let comment = res.data.comment_list;

                if (comment == null) commit("setIsMessage", false);
                else commit("setIsMessage", true);
                commit("setCommentData", comment);
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