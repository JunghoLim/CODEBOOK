import Axios from "axios";
import router from "@/router";

const state = {
    boardData: []
};
const getters = {
    getHeaders: state => state.headers,
    getBoardData: state => state.boardData
};
const mutations = {
    setBoardData: function(state, result) {
        state.boardData = result;
    }
};
const actions = {
    changeBoardData({ commit }, result) {
        commit("setBoardData", result);
    },
    pageNext({ dispatch }, params) {
        if (!params.category && params.searchText) {
            router.push("/search?searchText=" + params.searchText + "&page=" + params.page).catch(() => {});
            axios
                .get("/api/board/list", { params: { 'page': params.page, 'searchText': params.searchText } })
                .then(res => {
                    let result = res.data.board_list;
                    dispatch("changeBoardData", result);
                })
                .catch(() => {});
        } else {
            router.push("/board-list?category=" + params.category + "&page=" + params.page);
            axios
                .get("/api/board/list", { params: { 'page': params.page, 'category': params.category } })
                .then(res => {
                    let result = res.data.board_list;
                    dispatch("changeBoardData", result);
                })
                .catch(() => {});
        }
    }
};

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
};