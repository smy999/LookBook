import axios from 'axios';

let request = axios.create({
    baseURL: "https://j5A502.p.ssafy.io/api",
    headers:{
        'Authorization' : `Bearer ${window.sessionStorage.getItem('token')}`
    }
});

export const userAPI = {
    login: async (userEmail, userPassword)=>{
        return await request.post(`/auth/login`,{
            userEmail, userPassword
        }).then(function(response) {
            // accessToken은 header에 refreshToken은 sessionStorage에 넣어주기
            window.sessionStorage.setItem('token', response.data.accessToken);
            window.sessionStorage.setItem('refreshToken', response.data.refreshToken);
            return response;
        })
    },
    userinfo:async ()=>{
        return await request.get(`/users/me`,{
            
        }).then(function(response){
            // console.log(response);
            return response;
        }).catch(function(e){
            return e.response;
        })
    },
    checkNickName:async (userNickname)=>{
        return await request.get(`/users/nickname/${userNickname}`,{
            userNickname
        }).then(function(response){
            return response;
        })
    },
    join:async (userEmail, userPassword, userName, userNickname) => {
        return await request.post(`/users`, {
            userEmail, userPassword, userName, userNickname
        }).then(function(response){
            return response;
        })
    },
    checkEmail:async (userEmail)=>{
        return await request.get(`users/email/${userEmail}`,{
            userEmail
        }).then(function(response){
            return response;
        })
    },
    loginGoogle: async (email)=>{
        return await request.post(`/google/login`,{
            email
        }).then((response)=>{
            if(response.status===200){
                window.sessionStorage.setItem('token', response.data.accessToken);
                window.sessionStorage.setItem('refreshToken', response.data.refreshToken);
                return response;
            }
        }).catch((err)=>{
                console.log(err);
                window.sessionStorage.setItem('email', email);
                return false;
        })
    },
    loginKakao: async(code)=>{
        return await request.get('/kakao/login',{
            params:{
                code: code
            }
        }).then((response)=>{
            return response;
            // console.log(response);
        }).catch((error)=>{
            console.log(error);
        })
    },
    reissue: async () => {
        return await request.post('auth/reissue',{
            refreshToken : window.sessionStorage.getItem('refreshToken')
        }).then((response)=>{
            window.sessionStorage.setItem('token', response.data.accessToken);
            window.sessionStorage.setItem('refreshToken', response.data.refreshToken);
            return response.data;
        }).catch((error)=>{
            return error.response;
        })
    },
    changeProfile:async(userProfileUrl)=>{
        return await request.put('users/profile',{
            userProfileUrl
        }).then((response)=>{
            console.log(response);
        })
    }
}

export const bookAPI={
    detail: async(bookIsbn)=>{
        return await request.get(`/books/${bookIsbn}`,{
            bookIsbn
        }).then((response)=>{
            // console.log(response);
            return response.data;
        }).catch((err)=>{
            console.log(err);
        })
    },
    addBookGrade: async (bookIsbn, bookGrade) => {
        return await request.post('/bookgrade',{
            bookIsbn, bookGrade
        }).then((response) => {
            // console.log(response);
        })
    },
    modifyBookGrade: async (bookIsbn, bookGrade) => {
        return await request.put('/bookgrade',{
            bookIsbn, bookGrade
        }).then((response) => {
            // console.log(response);
        })
    },
    search : async(searchKey, searchWord)=>{
        return await request.get('/books',{
            params:{
                searchKey: searchKey,
                searchWord: searchWord
            }
        }).then((response) => {
            return response.data;
        })
    },
    category : async(categoryId) => {
        return await request.get(`/books/category/${categoryId}`,{
            categoryId
        }).then(response => {
            return response.data;
        })
    }
}

export const reviewAPI = {
    write: async (bookIsbn, reviewContent)=>{
        return await request.post('/reviews',{
            bookIsbn, reviewContent
        }).then((response)=>{
            return response.data.status;
        })
    },
    like: async (reviewId) => {
        return await request.post(`/reviewlikes/${reviewId}`,{

        }).then((response) => {
            // console.log(response);
        })
    },
    likeremove : async (reviewId) => {
        return await request.delete(`/reviewlikes/${reviewId}`,{

        })
    },
    myreviews : async () => {
        return await request.get('/reviews',{

        }).then((response) =>{
            return response.data;
        })
    },
    deletemyreivew: async (reviewId)=>{
        return await request.delete(`/reviews/${reviewId}`,{

        }).then((response) =>{
            return response;
        })
    }
}

export const likeAPI={
    addlike : async (bookIsbn) => {
        return await request.post(`/likes/${bookIsbn}`,{

        })
    },
    removelike : async (bookIsbn) => {
        return await request.delete(`/likes/${bookIsbn}`,{

        })
    },
    mylikelist : async () => {
        return await request.get('/likes', {

        }).then((response)=>{
            // console.log(response);
            return response.data;
        }).catch((err)=>{
            console.log(err);
        })
    }
}