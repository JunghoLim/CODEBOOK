import Axios from "axios";

const state = {
  headers: [
    {
      text: "번호",
      align: "start",
      sortable: false,
      value: "bno"
    },
    { text: "제목", value: "title" },
    { text: "날짜", value: "write_date" },
    { text: "작성자", value: "email" },
    { text: "조회수", value: "views" },
    { text: "추천수", value: "recommend" },
    { text: "댓글수", value: "comment_c" },
    { text: "신고수", value: "report_co" }
  ],
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
  }
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
