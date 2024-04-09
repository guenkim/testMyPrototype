<template>
  <h3 class="mt-4">놀이터에서 무얼 하지?</h3>
</template>

<script>
import { useCount } from '@/composables/count';

export default {
  setup() {
    const { count } = useCount();

    return {
      count
    }
  }
}
</script>

<style>

</style>