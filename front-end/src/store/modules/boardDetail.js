const state = {
  boardDetailData: []
};
const getters = {
  getBoardDetailData: state => state.boardDetailData
};
const mutations = {
  setBoardDetailData(state, result) {
    state.boardDetailData = result.board_detail;
  }
};
const actions = {
  changeBoardDetailData({ commit }, result) {
    commit("setBoardDetailData", result);
  }
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
