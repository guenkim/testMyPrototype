import {computed, toRefs} from 'vue';
import { useStore } from 'vuex';
export const getAuth = () => {
    const store = useStore();
    const loggedUserid = computed(() =>
        {
            return store.state.auth.initialState.userId;
        }
    );

    return toRefs({
        loggedUserid
    });
}