import LocalstorageService from "@/service/storage/localstorage.service";
import AuthService from "@/service/auth/auth.service";

const userId = LocalstorageService.getUserId();
//const initialState = userId ? {status:{loggendIn:true},userId : userId} :{status:{loggendIn:false},userId : ''};
//const initialState = userId ? {loggendIn:true,userId : userId} : {loggendIn:false,userId : ''};
const initialState = userId ? {userId : userId} : {userId : ''};
export default {
    namespaced: true,
    state: {
        initialState
    },
    mutations: {
        loginSuccess(state,payload) {
            state.initialState.userId = payload.account;
        },
        loginFailure(state) {
            state.initialState.userId = '';
        },
        registerSuccess(state){
            state.initialState.userId = '';
        },
        registerFailure(state){
            state.initialState.userId = '';
        },
        signOutSuccess(state){
            state.initialState.userId = '';
        },
        signOutFailure(state){
            state.initialState.userId = LocalstorageService.getUserId();
        }
    },
    actions: {
        signIn({commit},payload){
            return AuthService.signIn(payload).then(
                res =>{
                    commit('loginSuccess',payload);
                    return Promise.resolve(res);
                },
                error =>{
                    commit('loginFailure');
                    return Promise.reject(error);
                }
            );
        },
        signUp({commit},payload){
            return AuthService.signUp(payload).then(
                res =>{
                    commit('registerSuccess');
                    return Promise.resolve(res);
                },
                error =>{
                    commit('registerFailure');
                    return Promise.reject(error);
                }
            );
        },
        signOut({commit}){
            return AuthService.signOut().then(
                res =>{
                    commit('signOutSuccess');
                    return Promise.resolve(res);
                },
                error =>{
                    commit('signOutFailure');
                    return Promise.reject(error);
                }
            );
        }
    },
    getters: {
    },
}