<template>
  <div>
    <div class="d-flex justify-content-between mb-3">
      <h2>To-Do List</h2>
      <button
          class="btn btn-primary"
          @click="moveToCreatePage"
      >
        Create Todo
      </button>
    </div>

    <input
        class="form-control"
        type="text"
        v-model="searchText"
        placeholder="Search"
        @keyup.enter="searchTodo"
    >
    <hr/>

    <div v-if="!todos.length">
      There is nothing to display
    </div>
    <TodoList
        :todos="todos"
        @toggle-todo="toggleTodo"
        @delete-todo="deleteTodo"
    />
    <hr/>
    <Pagination
        v-if="todos.length"
        :numberOfPages="numberOfPages"
        :currentPage="currentPage"
        @click="getTodos"
    />
  </div>
</template>

<script>
import {ref, computed, watch} from 'vue';
import TodoList from '@/components/TodoList.vue';
import {useToast} from '@/composables/toast';
import {useRouter} from 'vue-router';
import Pagination from '@/components/Pagination.vue';
import TodoService from "@/service/todo/todo.service"

// import axios from "axios";

export default {
  components: {
    TodoList,
    Pagination
  },
  setup() {

    const router = useRouter();
    const todos = ref([]);
    const error = ref('');
    const numberOfTodos = ref(0);
    let limit = 5;
    const currentPage = ref(1);
    const searchText = ref('');
    const sortParam1 = ref('subject,desc');
    const sortParam2 = ref('id,desc');
    const sortArr = ref([]);
    const numberOfPages = computed(() => {
      return Math.ceil(numberOfTodos.value / limit);
    });

    const {
      toastMessage,
      toastAlertType,
      showToast,
      triggerToast
    } = useToast();

    const getTodos = (page = currentPage.value) => {
      currentPage.value = page;
      sortArr.value = [];
      sortArr.value.push(sortParam1);
      sortArr.value.push(sortParam2);
      TodoService.getTodos(searchText.value, page, limit,sortArr)
          .then(
              (response) => {
                console.log(response);
                numberOfTodos.value = response.data.totalCount;
                todos.value = response.data.content;
              },
              (err) => {
                console.log("error:"+err);
                console.log(err.response.data.code);
                console.log(err.response.data.message);
                console.log(err.response.data.status);
                error.value = err.response.data.message;
                triggerToast(err.response.data.message, 'danger');
              }
          );
    };

    getTodos();


    /**
     const addTodo = async (todo) => {
     // 데이터베이스 투두를 저장
     error.value = '';
     try {
     await axios.post('todos', {
     subject: todo.subject,
     completed: todo.completed,
     });

     getTodos(1);
     } catch (err) {
     console.log(err);
     error.value = 'Something went wrong.';
     triggerToast('Something went wrong', 'danger')
     }
     };
     **/


    const deleteTodo = (id) => {
      error.value = '';
      TodoService.deleteTodo(id).then(
          () => {
            getTodos(1);
          },
          (err) => {
            console.log(err.response.data.code);
            console.log(err.response.data.message);
            console.log(err.response.data.status);
            error.value = err.response.data.message;
            triggerToast(err.response.data.message, 'danger');
          }
      );
    }



    const toggleTodo = (index, checked) => {
      const completed = checked ? "TRUE" : "FALSE";
      error.value = '';
      const id = todos.value[index].id;

      TodoService.toggleTodo(id,completed).then(
          () => {
            todos.value[index].completed = checked;
          },
          (err) => {
            console.log(err.response.data.code);
            console.log(err.response.data.message);
            console.log(err.response.data.status);
            error.value = err.response.data.message;
            triggerToast(err.response.data.message, 'danger');
          }
      );
    };

    const moveToCreatePage = () => {
      router.push({
        name: 'TodoCreate',
      })
    };

    let timeout = null;
    const searchTodo = () => {
      clearTimeout(timeout);
      getTodos(1);
    };

    watch(searchText, () => {
      clearTimeout(timeout);
      timeout = setTimeout(() => {
        getTodos(1);
      }, 2000);
    });

    return {
      searchTodo,
      todos,
      //addTodo,
      deleteTodo,
      toggleTodo,
      searchText,
      error,
      numberOfPages,
      currentPage,
      getTodos,
      toastMessage,
      toastAlertType,
      showToast,
      moveToCreatePage,
    };
  }
}
</script>

<style>
.todo {
  color: gray;
  text-decoration: line-through;
}
</style>