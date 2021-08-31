<template>
  <v-container>
    <v-card>
      <v-row>
        <v-col
          cols="10"
          offset="1"
        >
          <div
            class="text-center pt-3"
          >
            <v-text-field
              v-model="title"
              label="제목"
              single-line
              style="width:300px"
              outlined
            />
          </div>
          <vue-editor
            id="editor"
            v-model="content"
            style="height:650px"
            class="pb-10"
            use-custom-image-handler
            @imageAdded="handleImageAdded"
          />
        </v-col>
        <v-col cols="12">
          <div
            class="text-center pt-8 pb-3"
          >
            <v-btn
              color="primary"
              @click="handleSavingContent({
                'email': member.email, 'title': title, 'content': content, 'category': category, 'mainPicture': imgUrls[0]
              })"
            >
              완료
            </v-btn>
          </div>
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script>
import { VueEditor } from "vue2-editor";

export default {
  components: {
    VueEditor
  },

  data() {
    return {
      content: "",
      imgUrls: [],
      title:''
    };
  },
  computed:{
    member:{
      get(){
        return this.$store.getters["member/getMember"];
      }
    },
    category:{
      get(){
        return this.$route.params.category;
      }
    }
  },

  methods: {
    handleSavingContent(boardData) {
      if(boardData.content.length == 0){
        alert('게시글이 입력되지 않았습니다.');
            this.$router.go(-1);
            return;
      }
      let token = this.$store.getters["member/getToken"];
      axios
          .post("/api/board", boardData, {headers:{'token': token}})
          .then(() => {
            alert('게시글이 성공적으로 저장되었습니다.');
            this.$router.push('/board-list/'+this.$route.params.category);
          })
          .catch(() => {
            alert('게시글 저장에 실패하였습니다.');
            this.router.go();
          })
    },
    handleImageAdded(file, Editor, cursorLocation) {
      let token = this.$store.getters["member/getToken"];
      const formData = new FormData();
      formData.append("file", file);
      axios
          .post("/api/board/img", formData, {headers:{'codebook-bearer': token, "Content-Type": "multipart/form-data"}})
          .then((result) => {
            let url = '/api/file/img/'+result.data
            this.imgUrls.push(result.data);
            Editor.insertEmbed(cursorLocation, 'image', url);
          })
          .catch((err) => {
            console.log(err);
          })
    }
  }
};
</script>
