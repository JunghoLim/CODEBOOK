<template>
  <v-list-item
    link
    :to="item.to"
    active-class="primary white--text"
    class="py-1"
    @click="showItems(item.title)"
  >
    <v-list-item-icon>
      <v-icon>{{ item.icon }}</v-icon>
    </v-list-item-icon>

    <v-list-item-content>
      <v-list-item-title>
        {{ item.title }}
      </v-list-item-title>
    </v-list-item-content>
  </v-list-item>
</template>

<script>
export default {
  name: "DrawerListItem",

  props: {
    item: {
      type: Object,
      default: () => []
    }
  },
  methods: {
    showItems(title) {
      if (title == "로그아웃") {
        this.$store.dispatch("member/logout");
      }
    }
  }
};
</script>

<style></style>
