<template>
  <div class="form-signin w-100 m-auto">
    <h1 class="h3 mb-3 fw-normal">Please sign in</h1>
    <div class="form-floating">
      <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com"
             v-model="signInfo.account"
      >
      <label for="floatingInput">Email address</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" id="floatingPassword" placeholder="Password"
             v-model="signInfo.password"
      >
      <label for="floatingPassword">Password</label>
    </div>

    <div class="checkbox mb-3">
      <label>
        <input type="checkbox" value="remember-me"> Remember me
      </label>
    </div>
    <button class="w-100 btn btn-lg btn-primary" @click="onSave">Sign in</button>
    <p class="mt-5 mb-3 text-muted">&copy; 2017–2022</p>
  </div>

</template>

<script>
import {ref} from "vue";
import {useToast} from "@/composables/toast";
import {useRouter} from "vue-router";
import {useStore} from "vuex";

export default {
  setup() {
    const router = useRouter();
    const signInfo = ref({
      account: '',
      password: ''
    });
    const {
      toastMessage,
      toastAlertType,
      showToast,
      triggerToast
    } = useToast();
    const store = useStore();
    const onSave = () => {
      const data = {
        account: signInfo.value.account,
        password: signInfo.value.password
      };

      store.dispatch('auth/signIn', data)
        .then(
            (res) => {
              console.log(res);
              router.push({
                name: 'Home'
              });
            },
            (err) => {
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
.form-signin {
  max-width: 330px;
  padding: 15px;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>
