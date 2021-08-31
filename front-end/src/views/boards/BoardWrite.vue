<template>
  <div id="app">
    <button @click="handleSavingContent">
      전송
    </button>
    <vue-editor v-model="content" />
  </div>
</template>

<script>
import { VueEditor } from "vue2-editor";

export default {
  components: {
    VueEditor
  },

  data() {
    return {
      content: "<h3>Initial Content</h3>"
    };
  },

  methods: {
    handleSavingContent: function() {
      // You have the content to save
      console.log(this.content);
    }
  }
};
</script>
