import AxiosInst from "@/service/api";

class TodoService {
    getTodos(searchText,page,limit,sortArr){
        let url = `todos?subject=${searchText}&page=${page}&size=${limit}`;
        if (sortArr.value.length > 0) {
            const sortParameters = sortArr.value.map(sort => sort.value).join(',');
            url += `&sort=${sortParameters}`;
        }
        console.log("url: " + url);

        return  AxiosInst.get(url);
    }

    createTodo(data){
        return  AxiosInst.post('todos', data, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }

    updateTodo(todoId,data){
        return AxiosInst.put(`todos/${todoId}`, data,{
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }

    downLoadFile(fileId){
        return  AxiosInst.get(`file/${fileId}`,{
            responseType:"blob"
        });
    }

    removeFile(fileId){
        return  AxiosInst.delete(`file/${fileId}`);
    }

    deleteTodo(todoId){
        return AxiosInst.delete('todos/' + todoId);
    }

    toggleTodo(todoId, completed){
        return AxiosInst.patch(`todos/${todoId}/` + completed);
    }

    getTodo(todoId){
        return AxiosInst.get(`todos/${todoId}`);
    }

}

export default new TodoService();
