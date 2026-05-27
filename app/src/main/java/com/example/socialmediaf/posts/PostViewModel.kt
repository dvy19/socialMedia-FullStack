package com.example.socialmediaf.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class GetAllPostState {
    object Idle : GetAllPostState()
    object Loading: GetAllPostState()
    data class Success(val posts: List<PostData>) : GetAllPostState()
    data class Error(val message: String) : GetAllPostState()
}


class PostViewModel(
    private val repository: PostRepository
) : ViewModel(){

    private val _getAllPostState = MutableStateFlow<GetAllPostState>(GetAllPostState.Idle)
    val getAllPostState: StateFlow<GetAllPostState> = _getAllPostState.asStateFlow()

    fun fetchAllPosts(token: String){

        viewModelScope.launch{

            _getAllPostState.value = GetAllPostState.Loading

            try{
                val response=repository.get_all_posts(token)

                if(response.body()!=null && response.isSuccessful){

                    _getAllPostState.value =
                        GetAllPostState.Success(
                            response.body()!!
                        )

                }

                // api error
                else {

                    _getAllPostState.value =
                        GetAllPostState.Error(

                            response.message()
                                ?: "Failed to fetch posts"
                        )
                }

            }

            // network/parsing error
            catch (e: Exception) {

                _getAllPostState.value =
                    GetAllPostState.Error(

                        e.message
                            ?: "Unknown Error"
                    )
            }
        }



    }


}

