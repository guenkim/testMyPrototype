<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active" v-if="loggedUserid">
        <router-link class="navbar-brand" :to="{ name: 'Home'}">
          [{{loggedUserid}}] 의 놀이터
        </router-link>
      </li>
      <li class="nav-item active" v-else>
        <router-link class="navbar-brand" :to="{ name: 'Home'}">
          다들 놀러와 !!
        </router-link>
      </li>



      <li class="nav-item active" v-if="loggedUserid">
        <router-link class="nav-link" :to="{ name: 'Todos'}">
          할일들
        </router-link>
      </li>
      <li class="nav-item active" v-if="!loggedUserid">
        <router-link class="nav-link" :to="{ name: 'Login'}">
          들어가기
        </router-link>
      </li>
      <li class="nav-item active" v-if="!loggedUserid">
        <router-link class="nav-link" :to="{ name: 'Sign'}">
          참여하기
        </router-link>
      </li>
      <li class="nav-item active" v-if="loggedUserid">
        <a class="nav-link" @click="signOut"> 종료하기 </a>
      </li>
    </ul>
  </nav>
</template>

<script>
import LocalstorageService from "@/service/storage/localstorage.service";
import {getAuth} from "@/composables/auth";
import store from "@/store";

export default {
  setup(){
    const {loggedUserid } = getAuth();

    const signOut = ()=>{
        if(LocalstorageService.getAccessToken()){
          store.dispatch('auth/signOut')
          .then(
              (res) => {
                console.log(res);
              },
              (err) => {
                console.log(err);
                // console.log(err.response.data.code);
                // console.log(err.response.data.message);
                // console.log(err.response.data.status);
                // triggerToast(err.response.data.message, 'danger');
              }
          );
        }
    }
    return{
      signOut,
      loggedUserid
    }
  }
}
</script>

<style>

</style>