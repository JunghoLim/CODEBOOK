<template>
  <v-list
    dense
    nav
    expand
  >
    <template v-for="(item, index) in items">
      <drawer-list-group
        v-if="item.items"
        :key="`group-${index}`"
        :item="item"
      />
      <drawer-list-item
        v-else
        :key="`item-${index}`"
        :item="item"
      />
    </template>
  </v-list>
</template>

<script>
import DrawerListItem from "./DrawerListItem";
import DrawerListGroup from "./DrawerListGroup";
export default {
  name: "DrawerList",
  components: { DrawerListItem, DrawerListGroup },
  props: {
    items: {
      type: Array,
      default: () => []
    }
  }
};
</script>

<style></style>
