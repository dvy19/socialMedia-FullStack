package com.example.socialmediaf.posts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class GetAllPostState {
    object Idle : GetAllPostState()
    object Loading: GetAllPostState()
    data class Success(val posts: List<PostResponse>) : GetAllPostState()
    data class Error(val message: String) : GetAllPostState()
}

sealed class CreatePostState{
    object Idle: CreatePostState()
    object Loading: CreatePostState()
    data class Success(  val post:PostResponse): CreatePostState()
    data class Error( val message :String): CreatePostState()
}


class PostViewModel(
    private val repository: PostRepository
) : ViewModel(){

    private val _getAllPostState = MutableStateFlow<GetAllPostState>(GetAllPostState.Idle)
    val getAllPostState: StateFlow<GetAllPostState> = _getAllPostState.asStateFlow()

    private val _createPost= MutableStateFlow<CreatePostState>(CreatePostState.Idle)
    val createPostState: StateFlow<CreatePostState> = _createPost.asStateFlow()

    fun fetchAllPosts(){

        viewModelScope.launch{

            _getAllPostState.value = GetAllPostState.Loading

            Log.d("m", getAllPostState.value.toString())

            try{

               // Log.d("POST_API", "Before API call")

                val response = repository.get_all_posts()

                /*
                Log.d("POST_API", "Code = ${response.code()}")
                Log.d("POST_API", "Successful = ${response.isSuccessful}")
                Log.d("POST_API", "Body = ${response.body()}")
                Log.d("POST_API", "ErrorBody = ${response.errorBody()?.string()}")

                Log.d("POST_API", "After API call")

                Log.d("m", getAllPostState.value.toString())

                 */

                if(response.body()!=null && response.isSuccessful){
                    Log.d("m", getAllPostState.value.toString())


                    _getAllPostState.value =
                        GetAllPostState.Success(
                            response.body()!!
                        )

                }

                // api error
                else {

                    Log.d("m", getAllPostState.value.toString())


                    _getAllPostState.value =
                        GetAllPostState.Error(

                            response.message()
                                ?: "Failed to fetch posts"
                        )
                }

            }

            // network/parsing error
            catch (e: Exception) {

                Log.d("m", getAllPostState.value.toString())


                _getAllPostState.value =
                    GetAllPostState.Error(
                               e.message
                            ?: "Unknown Error"
                    )
            }
        }}

    fun createAPost( request: PostRequest){

        viewModelScope.launch{

            _createPost.value = CreatePostState.Loading

            Log.d("m", createPostState.value.toString())

            try{

                Log.d("POST_API", "Before API call")

                val response = repository.create_post(request)


                Log.d("POST_API", "Code = ${response.code()}")
                Log.d("POST_API", "Successful = ${response.isSuccessful}")
                Log.d("POST_API", "Body = ${response.body()}")
                Log.d("POST_API", "ErrorBody = ${response.errorBody()?.string()}")

                Log.d("POST_API", "After API call")

                Log.d("m", createPostState.value.toString())

                if(response.body()!=null && response.isSuccessful){
                    Log.d("m", createPostState.value.toString())


                    _createPost.value =
                        CreatePostState.Success(
                            response.body()!!
                        )

                }

                // api error
                else {

                    Log.d("m", createPostState.value.toString())


                    _createPost.value =
                        CreatePostState.Error(

                            response.message()
                                ?: "Failed to fetch posts"
                        )
                }

            }

            // network/parsing error
            catch (e: Exception) {

                Log.d("m", createPostState.value.toString())


                _createPost.value =
                    CreatePostState.Error(
                        e.message
                            ?: "Unknown Error"
                    )
            }
        }}




}

