const state = {
    headers: [{
            text: "번호",
            align: "start",
            sortable: false,
            value: "bno"
        },
        { text: "제목", value: "calories" },
        { text: "날짜", value: "fat" },
        { text: "작성자", value: "carbs" },
        { text: "조회수", value: "protein" },
        { text: "추천수", value: "iron" }
    ],
    boardData: [{
            bno: "1",
            calories: 159,
            fat: 6.0,
            carbs: 24,
            protein: 4.0,
            iron: "1%"
        },
        {
            bno: "2",
            calories: 237,
            fat: 9.0,
            carbs: 37,
            protein: 4.3,
            iron: "1%"
        },
        {
            bno: "3",
            calories: 262,
            fat: 16.0,
            carbs: 23,
            protein: 6.0,
            iron: "7%"
        },
        {
            bno: "4",
            calories: 305,
            fat: 3.7,
            carbs: 67,
            protein: 4.3,
            iron: "8%"
        },
        {
            bno: "5",
            calories: 356,
            fat: 16.0,
            carbs: 49,
            protein: 3.9,
            iron: "16%"
        }
    ]
};
const getters = {
    getHeaders: state => state.headers,
    getBoardData: state => state.boardData
};
const mutations = {};
const actions = {};

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
};