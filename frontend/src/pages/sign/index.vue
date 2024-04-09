<template>
  <div class="container d-flex justify-content-center">
    <div class="col-sm-8">
      <div class="card card-container">
        <img src="@/assets/login.png" class="card-img-top" />
        <div class="card-body">
          <h5 class="card-title">회원 가입</h5>
          <Form @submit.prevent="onSave">
            <div>
              <div class="form-group">
                <label for="email">ID(email)</label>
                <input type="email" class="form-control"
                       v-model="signInfo.account"
                >
              </div>
              <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control"
                       v-model="signInfo.password"
                >
              </div>
              <div class="form-group">
                <label for="username">Name</label>
                <input type="text" class="form-control"
                       v-model="signInfo.name"
                >
              </div>
              <div class="form-group">
                <label for="userage">Age</label>
                <input type="text" class="form-control"
                       v-model="signInfo.age"
                >
              </div>
              <div class="form-group">
                <button class="btn btn-primary btn-block" type="submit">
                  <span
                    class="spinner-border spinner-border-sm"
                  ></span>
                  Sign Up
                </button>
              </div>
            </div>
          </Form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {ref} from "vue";
import {useToast} from "@/composables/toast";
import {useRouter} from "vue-router";
import {useStore} from "vuex";
export default {
  setup() {
    const signInfo = ref({
      account : '',
      password :'',
      name:'',
      age:0
    });
    const router = useRouter();
    const {
          toastMessage,
          toastAlertType,
          showToast,
          triggerToast
    } = useToast();
    const store = useStore();
    const onSave =  () =>{
      const data = {
        account : signInfo.value.account,
        password : signInfo.value.password,
        name : signInfo.value.name,
        age : signInfo.value.age
      };
      store.dispatch('auth/signUp',data)
      .then(
          (res)=>{
              console.log(res);
            router.push({name:"Home"})
          } ,
          (err)=>{
              console.log(err.response.data.code);
              console.log(err.response.data.message);
              console.log(err.response.data.status);
              triggerToast(err.response.data.message, 'danger');
          }
      );
    }

    return {
      signInfo,
      onSave,
      showToast,
      toastMessage,
      toastAlertType
    }
  }
}
</script>

<style scoped>
.error-feedback {
  color: red;
}
</style>
