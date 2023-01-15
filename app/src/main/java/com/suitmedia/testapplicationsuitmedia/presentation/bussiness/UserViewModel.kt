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
        loadingErrorState(loading = true, error = false, e = null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.getListUsers(page)
                viewModelScope.launch(Dispatchers.Main) {
                    _listUsers.postValue(response)
                    loadingErrorState(loading = false, error = false, e = null)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    loadingErrorState(loading = false, error = true, e = e)
                }
            }
        }
    }

    private fun loadingErrorState(loading: Boolean, error: Boolean, e: Exception?) {
        _loadingState.postValue(loading)
        _errorState.postValue(Pair(error, e))
    }

}