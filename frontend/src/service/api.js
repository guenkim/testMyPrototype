import axios from "axios";

const AxiosInst = axios.create({
        baseURL: 'http://localhost:8082/api/',
        headers: {
            "Content-Type": "application/json",
        }
    })
export default AxiosInst;