package com.info.mybook.viewmodels

// UserViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.info.mybook.models.User
import com.info.mybook.models.Users
import com.info.mybook.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _userList = MutableLiveData<List<Users>>()
    val userList: LiveData<List<Users>> get() = _userList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val users = withContext(Dispatchers.IO) {
                    userRepository.getUsers()
                }
                _userList.postValue(users)
            } catch (e: Exception) {
                _error.postValue(e.message)
            } finally {
                _loading.postValue(false)
            }
        }
    }
}
