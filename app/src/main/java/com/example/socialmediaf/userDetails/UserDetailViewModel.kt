package com.example.socialmediaf.userDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class UserDetailState {
    object Idle : UserDetailState()
    object Loading : UserDetailState()
    data class Success(val userData: UserData) : UserDetailState()
    data class Error(val message: String) : UserDetailState()

}
class UserDetailViewModel(
    private val repository: DetailRepository
) : ViewModel(){

    private val _userDetailState = MutableStateFlow<UserDetailState>(UserDetailState.Idle)
    val userDetailState: StateFlow<UserDetailState> = _userDetailState.asStateFlow()

    fun createUserProfile(request : UserDetailRequest){

        viewModelScope.launch{

            _userDetailState.value = UserDetailState.Loading

            try{

                val response=repository.create_profile(request)

                Log.d("m",response.toString())

                if(response.isSuccessful && response.body()!=null){

                    Log.d("m",response.toString())


                    _userDetailState.value = UserDetailState.Success(
                        response.body()!!.data
                    )
                }
                else{
                    Log.d("m",response.errorBody().toString())
                    Log.d("m",response.message())
                    Log.d("m",response.toString())
                    Log.d("m",response.code().toString())


                    _userDetailState.value = UserDetailState.Error(
                        response.message() ?: "Failed to create profile"
                    )
                }

            }
            catch (e: Exception){
                _userDetailState.value = UserDetailState.Error(
                    e.message ?: "Unknown Error"
                )
            }
        }



    }

    fun getUserProfile(){

        viewModelScope.launch{

            _userDetailState.value = UserDetailState.Loading

            try{
                val response=repository.get_profile()

                Log.d("response ", response.toString())
                Log.d("m body", response.body().toString())
                Log.d("m message", response.message())
                Log.d("m", response.code().toString())


                if(response.body()!=null && response.isSuccessful){

                    _userDetailState.value = UserDetailState.Success(
                        response.body()!!
                    )
                }
                else{
                    _userDetailState.value = UserDetailState.Error(
                        response.message() ?: "Failed to fetch profile"
                    )
                }

                }
            catch(e: Exception){

                _userDetailState.value = UserDetailState.Error(
                    e.message ?: "Unknown Error"
                )


            }
        }
    }


}
