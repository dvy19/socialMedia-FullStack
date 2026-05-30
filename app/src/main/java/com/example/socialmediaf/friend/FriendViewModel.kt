package com.example.socialmediaf.friend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmediaf.userDetails.UserData
import com.example.socialmediaf.userDetails.UserDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class FriendProfileState{
    object Idle: FriendProfileState()
    object Loading: FriendProfileState()
    data class Success(val userData: UserData): FriendProfileState()
    data class Error(val message: String): FriendProfileState()
}
class FriendViewModel(
    private val repository: FriendRepository
) : ViewModel(){

    private val _friendDetailState = MutableStateFlow<FriendProfileState>(FriendProfileState.Idle)
    val friendDetailState: StateFlow<FriendProfileState> = _friendDetailState.asStateFlow()

    fun getUserProfile(
        id: Int
    ) {

        viewModelScope.launch {

            _friendDetailState.value = FriendProfileState.Loading


            val response =
                repository.getUserProfile( id)

            if (response.isSuccessful && response.body()!=null) {

                _friendDetailState.value= FriendProfileState.Success(
                    response.body()!!
                )
            }
        }
    }

}