class LocalstorageService{

    setAccessToken(token){
        localStorage.setItem("accessToken",token);
    }
    setRefreshToken(refreshToken){
        localStorage.setItem("refreshToken",refreshToken);
    }

    setUserId(userId){
        localStorage.setItem("userId",userId);
    }

    getAccessToken(){
        return localStorage.getItem("accessToken");
    }
    getRefreshToken(){
        return localStorage.getItem("refreshToken");
    }
    getUserId(){
        return localStorage.getItem("userId");
    }

    removeAccessToken(){
        localStorage.removeItem("accessToken");
    }
    removeRefreshToken(){
        localStorage.removeItem("refreshToken");
    }
    removeUserId(){
        localStorage.removeItem("userId");
    }

    clear(){
        localStorage.clear();
    }
}

export default  new LocalstorageService();