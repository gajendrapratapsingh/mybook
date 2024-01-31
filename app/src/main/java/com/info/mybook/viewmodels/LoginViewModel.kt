package com.info.mybook.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.info.mybook.models.LoginResponse
import com.info.mybook.models.Users
import com.info.mybook.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    sealed class LoginViewState {
        object Loading : LoginViewState()
        data class Success(val loginResponse: LoginResponse) : LoginViewState()
        data class Error(val errorMessage: String) : LoginViewState()
    }

    private val _loginViewState = MutableLiveData<LoginViewState>()
    val loginViewState: LiveData<LoginViewState> get() = _loginViewState

//    private val _loading = MutableLiveData<Boolean>()
//    val loading: LiveData<Boolean> get() = _loading

    fun loginUsers(username: String, password: String) {
        _loginViewState.value = LoginViewState.Loading
        viewModelScope.launch {
            try {
                val logResp = withContext(Dispatchers.IO) {
                    userRepository.loginUsers(username, password)
                }
                Log.d("login response value", logResp.toString())
                _loginViewState.postValue(LoginViewState.Success(logResp!!))
            } catch (e: Exception) {
                Log.d("login response error", e.message.toString())
                _loginViewState.postValue(LoginViewState.Error(e.message ?: "Something went wrong, try again!!"))
            }
        }
    }
}

//
//@HiltViewModel
//class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
//
//    private val _loginResp = MutableLiveData<LoginResponse?>()
//    val loginresp : LiveData<LoginResponse?> get() = _loginResp
//
//    private val _loading = MutableLiveData<Boolean>()
//    val loading: LiveData<Boolean> get() = _loading
//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String> get() = _error
//
//    fun loginUsers(username : String, password : String) {
//        _loading.value = true
//        viewModelScope.launch {
//            try {
//                val logResp = withContext(Dispatchers.IO) {
//                    userRepository.loginUsers(username, password)
//                }
//                Log.d("login response value", logResp.toString())
//                _loginResp.postValue(logResp)
//            } catch (e: Exception) {
//                Log.d("login response error", e.message.toString())
//                _error.postValue(e.message)
//            } finally {
//                _loading.postValue(false)
//            }
//        }
//    }
//}