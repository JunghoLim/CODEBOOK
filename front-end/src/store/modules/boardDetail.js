const state = {
  boardDetailData: [],
  comment: [],
  isMessage: false
};
const getters = {
  getBoardDetailData: state => state.boardDetailData,
  getCommentData: state => state.comment,
  getIsMessage: state => state.isMessage
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
  },
  clearState(state) {
    state.boardDetailData = [];
    state.comment = [];
    state.isMessage = false;
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
        console.log(res);

        if (comment == null) commit("setIsMessage", false);
        else commit("setIsMessage", true);
        commit("setCommentData", comment);
      })
      .catch(err => {});
  },
  sendComment({ commit }, commentInfo) {
    let bno = commentInfo.bno;
    let comment = commentInfo.comment;
    let isLogin = commentInfo.isLogin;
    let email = commentInfo.email;
    let param = { comment: comment, bno: bno, email: email };
    let token = localStorage.getItem("codebook-bearer");

    if (isLogin) {
      if (comment == null) {
        alert("한 글자 이상 써주세요!");
      } else {
        axios
          .post("/api/comment", param, {
            headers: { "codebook-bearer": token }
          })
          .then(res => {
            this.email = email;
            this.commentComponent = { comment: comment };
          })
          .catch(err => {
            console.log(err);
          });
      }
    } else {
      alert("로그인을 해주세요!");
      this.$router.push("/sign-in");
    }
  },
  deleteComment({ commit }, deleteInfo) {
    let token = localStorage.getItem("codebook-bearer");
    let cno = deleteInfo.cno;
    let email = deleteInfo.email;
    let param = { cno: cno, email: email };
    let bno = deleteInfo.bno;
    axios
      .post("/api/comment/delete", param, {
        headers: { "codebook-bearer": token }
      })
      .then(res => {})
      .catch(err => {
        console.log(err);
      });
  }
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
