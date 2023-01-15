package com.suitmedia.testapplicationsuitmedia.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suitmedia.testapplicationsuitmedia.data.network.response.ListUsersResponse
import com.suitmedia.testapplicationsuitmedia.data.repository.UserRepository
import com.suitmedia.testapplicationsuitmedia.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _listUsers = MutableLiveData<Resource<ListUsersResponse>>()
    val listUsers: LiveData<Resource<ListUsersResponse>>
        get() = _listUsers

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private val _errorState = MutableLiveData<Pair<Boolean, Exception?>>()
    val errorState: LiveData<Pair<Boolean, Exception?>>
        get() = _errorState

    fun getListUsers(page: Int) {
        _loadingState.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            _errorState.postValue(Pair(false, null))
            try {
                val response = userRepository.getListUsers(page)
                viewModelScope.launch(Dispatchers.Main) {
                    _listUsers.postValue(response)
                    _loadingState.postValue(false)
                    _errorState.postValue(Pair(false, null))
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _loadingState.postValue(false)
                    _errorState.postValue(Pair(true, e))
                }
            }
        }
    }

}