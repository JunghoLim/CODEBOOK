const state = {
  boardDetailData: []
};
const getters = {
  getBoardDetailData: state => state.boardDetailData
};
const mutations = {
  setBoardDetailData(state, result) {
    console.log(result);
    state.boardDetailData = result;
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
